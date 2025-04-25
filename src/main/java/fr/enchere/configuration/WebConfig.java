package fr.enchere.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                //CHANGER GLANDRY
                .addResourceLocations(
                        "classpath:/static/images/",
                        "file:C:\\Users\\glandry2023\\Desktop\\Enchere\\src\\main\\resources\\static\\uploads");
    }
}