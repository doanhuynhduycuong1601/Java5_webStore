package cuong.run.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cuong.run.interceptor.ManagerInterceptor;

@Configuration
public class ManagerConfig implements WebMvcConfigurer {
	@Autowired
	ManagerInterceptor global;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(global)
		.addPathPatterns("/manager/**");
		
	}
}
