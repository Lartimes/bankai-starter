package bankai.shizuku.ichigo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.bankai.config")
public class BankaiConfigProperties {
    private final boolean enabled = true;
    private final boolean importCheckEnabled = true;
    private String someCustomProperty;
}
