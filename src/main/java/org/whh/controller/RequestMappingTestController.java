package org.whh.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.whh.entity.User;

/**
 * @controller 控制器
 * @RequestMapping 注解相当于ruby on rails框架中的路由功能
 * @PathVariable 请求路径中的参数
 * @RequestParam 请求中的参数
 */
@Controller
//该类下所有的请求都必须以/request开始
@RequestMapping("/request")
public class RequestMappingTestController
{
	/**
	 * @RequestMapping 用来定义访问的URL，你可以为整个类定义一个
	 * 完整参数@RequestMapping(value="",method ={"",""},headers={},params={"",""})
	 *  
	 * value :String[] 设置访问地址 
	 *	
	 *method: RequestMethod[]设置访问方式，字符数组，查看RequestMethod类，包括GET, HEAD,
	 *POST, PUT, DELETE, OPTIONS, TRACE,常用:RequestMethod.GET，RequestMethod.POST 
	 *	
	 *headers:String[] headers一般结合method = RequestMethod.POST使用 
	 *
	 *params: String[] 访问参数设置，字符数组 例如：userId=id 
	 *可以通过设置参数条件来限制访问地址，例如params="myParam=myValue"表达式，访问地址中参数只有
	 *包含了该规定的值"myParam=myValue"才能匹配得上
	 *类似"myParam"之类的表达式也是支持的，表示当前请求的地址必须有该参数(参数的值可以是任意)
	 */
	//该方法对应的请求为/request/test,不区分get或者post方法
	@RequestMapping("/test")
	/**
	 * 用Map对象，其键值可以在页面上用EL表达式${键值名}得到
	 */
	public String test(Map<String,Object> map)
	{
		map.put("name", "test方法");
		return "request";
	}
	 //仅接受post方法
	@RequestMapping(value="/postTest",method=RequestMethod.POST)
	/**
	 * 用Model类对象来传递，有addAttribute(key, value)方法，其键值可以在页面上用EL表达式${键值名}得到
	 */
	//model对象将值传到view层
	public String postTest(Model model)
	{
		model.addAttribute("name", "postTest方法");
		return "request";
	}
	//仅接受get方法
	@RequestMapping(value="/getTest",method=RequestMethod.GET)
	/**
	 * 直接作为视图返回，该方法返回的是一个字符串
	 */
	@ResponseBody
	public String getTest()
	{
		return "this is a string reponse";
	}
	/**
	 * 仅接受get类型的参数(requset param)带myattr=myvalue的请求
	 */
	@RequestMapping(value="/paramTest",method=RequestMethod.GET,params="myattr=myvalue")
	/**
	 *直接返回视图，该方法以json格式返回 
	 */
	@ResponseBody
	public User paramTest()
	{
		User user = new User();
		user.setId(1L);
		user.setPassword("g@gg.com");
		user.setName("zhangsan");
		return user;
	}
	
	/**
	 * 请求路径中需要包含index这个路径参数
	 */
	@RequestMapping("pathVariable/{index}")
	public String pathVariableTest(@PathVariable String index)
	{
		System.out.println("---------------"+index+"---------------");
		return "request";
	}
	@RequestMapping("requestParamTest")
	/**
	 * @RequestParam 默认要求请求带该参数
	 *value参数指示请求参数的名称，不设置时与变量名称一致
	 *可以通过required=false或者true来要求@RequestParam配置的前端参数是否一定要传 
	 *defaultValue参数可设置默认值
     *
	 * 简单类型，如int, String, 应在变量名前加@RequestParam注解，
	 */
	public String requestParamTest(@RequestParam(required=false,value="test") String indx)
	{
		return "request"; 
	}
	/**
	 * 该方法抛出异常
	 */
	@RequestMapping("/exceptionTest")
	public String exceptionTest()
	{
		String str[] = new String[1];
		str[5] = "aaa";
		return "index";
	}
	//拦截当前controller抛出的异常
	//	@ExceptionHandler
	//	public String handleException(Exception ex)
	//	{
	//		System.out.println("------程序运行发生异常------");
	//		return "request";
	//	}
	@RequestMapping("/redirectTest")
	public String redirectTest(RedirectAttributes attr)
	{
		attr.addFlashAttribute("user", "gg si mi da");
		return "redirect:/";
		
	}
}
