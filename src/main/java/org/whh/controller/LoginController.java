package org.whh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.UserDao;
import org.whh.entity.User;
import org.whh.service.UserService;
import org.whh.wd.ProductMangeHelper;
import org.whh.web.CommonException;
import org.whh.web.CommonMessage;

@Controller
public class LoginController {

	@Autowired
	UserDao userDao;
	@Autowired
	UserService userService;

	@Autowired
	ProductMangeHelper manageHelper;	
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public CommonMessage doLogin(String name, String password, HttpSession session) {
		CommonMessage message = new CommonMessage();
		try {
			Long userId = userService.login(name, password);
			User user = userDao.findOne(userId);
			session.setAttribute("user", user);
			message.setCode(0);
			message.setMessage("登陆成功");
		} catch (CommonException e) {
			message.setCode(-1);
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequestMapping("/logOut")
	public String logOut(HttpSession session) {
		session.removeAttribute("user");
		return "/login";
	}
}
