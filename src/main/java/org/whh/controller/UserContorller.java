package org.whh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.whh.dao.UserDao;
import org.whh.entity.User;

@Controller
public class UserContorller
{

	@Autowired
	UserDao userDao;

	@RequestMapping("/updateUser")
	/**
	 * User对象可以由用户的请求参数中得到。
	 */
	public String updateUser(User user,Model model)
	{
		try
		{
			userDao.save(user);
			model.addAttribute("user",user);
		} catch (Exception e)
		{
			return "error";
		}
		return "user";
	}
	@RequestMapping("/deleteUser")
	public String deleteUser(long id)
	{
		try
		{
			userDao.delete(id);
		} catch (Exception e)
		{
			return "error";
		}
		return "index";
	}
}
