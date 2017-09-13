package com.epam.javacc.microservices.gateway.config;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.io.File;
import java.util.Arrays;

@Configuration
public class WebConfigurer implements EmbeddedServletContainerCustomizer {

    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (ArrayUtils.contains(env.getActiveProfiles(),"localDev")) {
            String staticPath = env.getProperty("taxi.staticResources");
            if (staticPath != null) {
                container.setDocumentRoot(new File(staticPath));
            }
        }
    }
}
