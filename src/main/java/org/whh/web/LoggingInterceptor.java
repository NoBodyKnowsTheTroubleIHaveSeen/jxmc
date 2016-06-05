package org.whh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定义了一个拦截器类，可通过实现HandlerInterceptor接口或者继承HandlerInterceptorAdapter类
 * 实现HandlerInterceptorAdapter的类可以决定是否实现其中的任意方法 preHandle请求处理前 postHandle请求处理后
 * afterCompletion请求处理完成
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		System.out.println("---Before Method Execution---");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		System.out.println("---method executed---");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		System.out.println("---Request Completed---");
	}
}