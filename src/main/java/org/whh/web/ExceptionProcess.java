package org.whh.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 *@ControllerAdvice 全局拦截异常类
 *@ExceptionHandler 异常处理方法
 *对于特定的异常可以做定制的处理
 */
@ControllerAdvice
public class ExceptionProcess
{
	//全局异常处理方法
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
    @ResponseBody  
	public ErrorMessage exceptionHandler(Exception ex)
	{
		ex.printStackTrace();
		ErrorMessage message = new ErrorMessage();
		message.setMessage("系统发生异常:"+ex.getMessage());
		message.setCustomException(false);
		return message;
	}
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
    @ResponseBody  
	public ErrorMessage exceptionHandler(CommonException ex)
	{
		ex.printStackTrace();
		ErrorMessage message = new ErrorMessage();
		message.setMessage(ex.getMessage());
		message.setTitle(ex.getTitle());
		message.setCode(ex.getCode());
		message.setCustomException(true);
		return message;
	}
}
