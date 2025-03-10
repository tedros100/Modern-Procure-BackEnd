package org.tedrosberihu.modernprocure.security;

import org.tedrosberihu.modernprocure.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 *
 * Created to control CORS from Allowed Hosts
 *
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private AppConfig appConfig;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowedOrigins(appConfig.getCors().getAllowedOrigins())
            .allowCredentials(true);
    }
}
