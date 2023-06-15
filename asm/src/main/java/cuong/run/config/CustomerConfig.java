package cuong.run.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cuong.run.interceptor.CustomerInterceptor;

@Configuration
public class CustomerConfig implements WebMvcConfigurer {
	@Autowired
	CustomerInterceptor global;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(global)
		.addPathPatterns("/cart/buy");
	}
}
