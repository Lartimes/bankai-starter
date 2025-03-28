package bankai.shizuku.ichigo.env;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.cloud.commons.ConfigDataMissingEnvironmentPostProcessor;
import org.springframework.core.env.Environment;

import static org.springframework.cloud.util.PropertyUtils.bootstrapEnabled;
import static org.springframework.cloud.util.PropertyUtils.useLegacyProcessing;

public class BankaiConfigDataMissingEnvironmentPostProcessor extends ConfigDataMissingEnvironmentPostProcessor {
    public static final String PREFIX = "spring.cloud.bankai.config";
    public static final int ORDER = ConfigDataEnvironmentPostProcessor.ORDER + 888;


    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    protected boolean shouldProcessEnvironment(Environment environment) {
        if (bootstrapEnabled(environment) || useLegacyProcessing(environment)) {
            return false;
        }
        boolean configEnabled = environment.getProperty(
                PREFIX + ".enabled", Boolean.class, true);
        boolean importCheckEnabled = environment.getProperty(
                PREFIX + ".import-check.enabled", Boolean.class,
                true);
        System.out.println(configEnabled);
        System.out.println(importCheckEnabled);
        return configEnabled && importCheckEnabled;
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    static class ImportExceptionFailureAnalyzer
            extends AbstractFailureAnalyzer<ImportException> {

        @Override
        protected FailureAnalysis analyze(Throwable rootFailure, ImportException cause) {
            String description;
            if (cause.missingPrefix) {
                description = "The spring.config.import property is missing a " + PREFIX
                        + " entry";
            } else {
                description = "No spring.config.import property has been defined";
            }
            String action = "暂不支持,需引入依赖\n" +
                    "\t<dependency>\n" +
                    "\t<groupId>org.springframework.cloud</groupId>\n" +
                    "\t<artifactId>spring-cloud-starter-bootstrap</artifactId>\n" +
                    "\t</dependency>";
            return new FailureAnalysis(description, action, cause);
        }

    }


}
