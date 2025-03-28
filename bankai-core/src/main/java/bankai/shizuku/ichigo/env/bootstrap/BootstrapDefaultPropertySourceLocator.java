package bankai.shizuku.ichigo.env.bootstrap;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;

import java.util.List;

/**
 * Bootstrap默认属性源加载器
 *
 */
public interface BootstrapDefaultPropertySourceLocator extends PropertySourceLocator {

    /**
     * 附加本地配置
     * @param locations 本地配置列表
     */
    void additionalLocations(List<String> locations);

}
