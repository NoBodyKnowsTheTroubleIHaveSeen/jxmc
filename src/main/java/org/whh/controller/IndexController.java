package org.whh.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.whh.dao.UserDao;
import org.whh.entity.User;

@Controller
public class IndexController {
	@Autowired
	UserDao userDao;
	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/")
	public String index(HttpSession session, Model model) {
		logger.info("访问首页");
		User user = (User) session.getAttribute("user");
		String name = user.getName();
		model.addAttribute("name", name);
		return "index";
	}
}
