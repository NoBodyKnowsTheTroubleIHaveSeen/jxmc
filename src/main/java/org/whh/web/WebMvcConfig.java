package org.whh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类
 * 此处不添加注解@EnableWebMvc，如添加该注解会导致spring boot不使用mvc的自动配置功能
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired 
  LoggingInterceptor loggingInterceptor;
  @Autowired 
  AuthenticationInterceptor authenticationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
	/**
	 * 添加要拦截的路径
	 */
    //拦截所有请求
    //registry.addInterceptor(loggingInterceptor).addPathPatterns("/*");
    //拦截以/request开头的所有请求
    registry.addInterceptor(loggingInterceptor).addPathPatterns("/request/*");
    registry.addInterceptor(authenticationInterceptor).addPathPatterns("/*");
  }
}
