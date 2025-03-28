package bankai.shizuku.ichigo;


import bankai.shizuku.ichigo.conf.BankaiConfigProperties;
import bankai.shizuku.ichigo.env.ApplicationDefaultPropertySourceLocator;
import bankai.shizuku.ichigo.env.DefaultPropertySource;
import bankai.shizuku.ichigo.listener.StartEventListener;
import bankai.shizuku.ichigo.util.SpringUtilInstance;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@DefaultPropertySource(value = "classpath:/application-default-cloud.yml")
@EnableConfigurationProperties(value = {BankaiConfigProperties.class})
public class BankaiBaseAutoConfiguration {

 /**
     * 应用默认属性源加载器
     */
    @Bean
    public ApplicationDefaultPropertySourceLocator applicationDefaultPropertySourceLocator() {
        return new ApplicationDefaultPropertySourceLocator();
    }

    @Bean
    public SpringUtilInstance springUtilInstance(){
        return new SpringUtilInstance();
    }

    @Bean
    public StartEventListener ApplicationListener(){
        return new StartEventListener();
    }
}
