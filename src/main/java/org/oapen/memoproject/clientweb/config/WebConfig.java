package org.oapen.memoproject.clientweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;		

	// Custom resource path
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println(env.getProperty("path.customresources",""));
        registry.addResourceHandler("/customassets/**").addResourceLocations(env.getProperty("path.customresources",""));
    }
	
}
