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
                        "file:C:\\Users\\cboulesteix2023\\IdeaProjects\\Enchere\\src\\main\\resources\\static\\uploads");
    }
}