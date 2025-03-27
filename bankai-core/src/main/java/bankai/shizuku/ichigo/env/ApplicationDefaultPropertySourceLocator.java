package bankai.shizuku.ichigo.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认应用属性加载器
 */
public class ApplicationDefaultPropertySourceLocator implements BeanFactoryPostProcessor, Ordered, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ApplicationDefaultPropertySourceLocator.class);
    private final ResourceLoader resourceLoader;
    private final Map<String, PropertySourceLoader> propertySourceLoaderMap;

    public ApplicationDefaultPropertySourceLocator() {
        this.resourceLoader = new DefaultResourceLoader();
        this.propertySourceLoaderMap = buildPropertySourceLoader();
    }

    /**
     * 获取支持的文件配置加载器
     */
    private Map<String, PropertySourceLoader> buildPropertySourceLoader() {
        HashMap<String, PropertySourceLoader> sourceLoaderMap = new HashMap<>();
        List<PropertySourceLoader> propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, Thread.currentThread().getContextClassLoader());
        propertySourceLoaders.forEach(loader -> {
            Arrays.stream(loader.getFileExtensions()).forEach(ext -> sourceLoaderMap.put(ext, loader));
        });
        return sourceLoaderMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void postProcessBeanFactory(@Nullable ConfigurableListableBeanFactory beanFactory) throws BeansException {
        assert beanFactory != null;
        this.doLocate(beanFactory);
    }

    private void doLocate(ConfigurableListableBeanFactory beanFactory) {
        ConfigurableEnvironment environment = beanFactory.getBean(ConfigurableEnvironment.class);
        MutablePropertySources propertySources = environment.getPropertySources();
        List<Resource> resources = this.resolve(beanFactory);
        List<PropertySource<?>> loadPropertySources = this.load(resources);
        loadPropertySources.forEach(propertySources::addLast);
    }

    /**
     * 分解资源
     *
     * @return 资源列表
     */
    private List<Resource> resolve(ConfigurableListableBeanFactory factory) {
        List<String> locations = getResourceLocations(factory);
        return locations
                .stream()
                .map(resourceLoader::getResource)
                .filter(Resource::exists)
                .collect(Collectors.toList());
    }

    /**
     * 获取带有配置注解的资源路径
     *
     */
    private List<String> getResourceLocations(ConfigurableListableBeanFactory factory) {
        List<String> locations = new ArrayList<>();
        Map<String, Object> beans = factory.getBeansWithAnnotation(DefaultPropertySource.class);
        for (Object bean : beans.values()) {
            Class<?> beanClass = ClassUtils.getUserClass(bean);
            DefaultPropertySource propertySource = AnnotationUtils.getAnnotation(beanClass, DefaultPropertySource.class);
            locations.addAll(Arrays.stream(Objects.requireNonNull(propertySource).location()).toList());
        }
        return locations;
    }


    private List<PropertySource<?>> load(List<Resource> resources) {
        ArrayList<PropertySource<?>> sourcesList = new ArrayList<>();
        for (Resource resource : resources) {
            String suffix =  this.getSuffixName(Objects.requireNonNull(resource.getFilename()));
            PropertySourceLoader propertySourceLoader = propertySourceLoaderMap.get(suffix);
            if(propertySourceLoader == null) {
                throw new RuntimeException("No PropertySourceLoader found for suffix: " + suffix);
            }
            String name = "Bankai Default Property Source 'class path resource [" + resource.getFilename() + "]'";
            List<PropertySource<?>> propertySourceList = null;
            try {
                propertySourceList = propertySourceLoader.load(name, resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sourcesList.addAll(propertySourceList);
        }
        return sourcesList;

    }

    private String getSuffixName(String filename) {
        int index = filename.lastIndexOf(".");
        if(index > 0 ){
            return filename.substring(index + 1);
        }
        return null;
    }



    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }


}
