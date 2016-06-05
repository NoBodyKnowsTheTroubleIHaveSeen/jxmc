package org.whh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.UserDao;
import org.whh.entity.User;
import org.whh.service.UserService;
import org.whh.web.CommonException;
import org.whh.web.CommonMessage;

@Controller
public class RegisterController {
	@Autowired
	UserDao userDao;
	@Autowired
	UserService userService;

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/doRegister")
	@ResponseBody
	public CommonMessage doRegister(String name, String password) throws CommonException {
		CommonMessage message = new CommonMessage();
		try {
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			userService.register(user);
			message.setCode(0);
			message.setMessage("注册成功");
		} catch (CommonException e) {
			message.setCode(-1);
			message.setMessage(e.getMessage());
		}
		return message;
	}
}
