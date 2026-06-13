package es.codeurjc.daw.museum.controller;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class ReactRoutingConfig implements WebMvcConfigurer {

    private static final String SPA_PATH = "/new";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(SPA_PATH, SPA_PATH + "/**")
                .addResourceLocations("classpath:/static" + SPA_PATH + "/")
                .resourceChain(true)
                .addResolver(new SpaResourceResolver(SPA_PATH)); 
    }

    private static class SpaResourceResolver extends PathResourceResolver {
        private final String spaPath;

        public SpaResourceResolver(String spaPath) {
            this.spaPath = spaPath;
        }

        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {

            Resource requestedResource = location.createRelative(resourcePath);

            if (requestedResource.exists() && requestedResource.isReadable()) {
                return requestedResource; 
            } else {
                return new ClassPathResource("/static" + spaPath + "/index.html");
            }
        }
    }
}