package org.tedrosberihu.modernprocure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Cors cors;
    private Frontend frontend;

    public static class Cors {
        private String[] allowedOrigins;

        public String[] getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(String[] allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }
    }

    public static class Frontend {
        private String url;
        private String localUrl;
        private String productionUrl;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocalUrl() {
            return localUrl;
        }

        public void setLocalUrl(String localUrl) {
            this.localUrl = localUrl;
        }

        public String getProductionUrl() {
            return productionUrl;
        }

        public void setProductionUrl(String productionUrl) {
            this.productionUrl = productionUrl;
        }
    }

    public Cors getCors() {
        return cors;
    }

    public void setCors(Cors cors) {
        this.cors = cors;
    }

    public Frontend getFrontend() {
        return frontend;
    }

    public void setFrontend(Frontend frontend) {
        this.frontend = frontend;
    }
} 
