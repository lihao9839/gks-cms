package com.audioapp.cms;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if(!registry.hasMappingForPattern("/static/**")){
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        if(!registry.hasMappingForPattern("/vendors/**")){
            registry.addResourceHandler("/vendors/**").addResourceLocations("classpath:/vendors/");
        }
        if(!registry.hasMappingForPattern("/build/**")){
            registry.addResourceHandler("/build/**").addResourceLocations("classpath:/build/");
        }
        if(!registry.hasMappingForPattern("/js/**")){
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        }
        if(!registry.hasMappingForPattern("/images/**")){
            registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        }
        super.addResourceHandlers(registry);
    }
}
