package bankai.shizuku.ichigo.env.bootstrap;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import static org.springframework.cloud.util.PropertyUtils.bootstrapEnabled;
import static org.springframework.cloud.util.PropertyUtils.useLegacyProcessing;

public class BootstrapDefaultEnvironmentApplicationListener extends EnvironmentPostProcessorApplicationListener implements Ordered {
    private static final String BOOTSTRAP_DEFAULT_PROPERTY_SOURCE_NAME = "bootstrap-default";

    public void onApplicationEvent(ApplicationEvent event) {
// ApplicationEnvironmentPreparedEvent environmentPreparedEvent) { //当环境配置准备好，配置文件也已加载完成后，Spring Boot 会发布该事件，通知所有监听该事件的监听器。
// ApplicationPreparedEvent) { //在 ApplicationContext 创建完成且准备好，但还未刷新时发布。
// ApplicationContext 刷新完成，应用已启动。
// ApplicationFailedEvent) { // Event published by a SpringApplication
// when it fails to start.
        if (event instanceof ApplicationEnvironmentPreparedEvent preparedEvent) {
            ConfigurableEnvironment environment = preparedEvent.getEnvironment();
//              1. 如果不是bootstrap return
            if (bootstrapEnabled(environment) || useLegacyProcessing(environment)) {
                return;
            }
            MutablePropertySources propertySources = environment.getPropertySources();
//              2. 如果不是cloud return
            if (!propertySources.contains(BOOTSTRAP_DEFAULT_PROPERTY_SOURCE_NAME)) {
                return;
            }
//              3.加载资源


//              4.释放资源
        }

    }

    @Override
    public int getOrder() {
        return EnvironmentPostProcessorApplicationListener.HIGHEST_PRECEDENCE + 11;
    }
}
