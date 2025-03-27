package bankai.shizuku.ichigo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 启动事件监听器
 */
public class StartEventListener implements ApplicationListener<WebServerInitializedEvent>, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(StartEventListener.class);

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        Environment environment = event.getApplicationContext().getEnvironment();
        String appName = Objects.requireNonNull(environment.getProperty("spring.application.name")).toUpperCase();
        int localPort = event.getWebServer().getPort();
        String profile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
        LOG.info("---[{}]---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", appName, localPort, profile);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
