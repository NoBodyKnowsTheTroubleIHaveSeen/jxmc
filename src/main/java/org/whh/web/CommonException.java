package org.whh.web;


public class CommonException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String message;
	private Integer code;
	
	public CommonException(String message)
	{
		this.message = message;
	}
	public CommonException(String title,String message)
	{
		this.title = title;
		this.message = message;
	}
	public CommonException(Integer code,String message)
	{
		this.code = code;
		this.message =message;
	}
	public CommonException(String title,String message,int code)
	{
		this.title = title;
		this.message = message;
		this.code = code;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
}
