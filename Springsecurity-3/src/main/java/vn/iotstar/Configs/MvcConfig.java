package vn.iotstar.Configs;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer{
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/login").setViewName("login");
	}
}
