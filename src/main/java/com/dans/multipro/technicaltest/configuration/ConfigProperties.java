package com.dans.multipro.technicaltest.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    private Environment environment;

    public String getConfigValue(String configKey){
        return environment.getProperty(configKey);
    }
}
