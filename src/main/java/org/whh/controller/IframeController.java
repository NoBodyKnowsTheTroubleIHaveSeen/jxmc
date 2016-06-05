package org.whh.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.whh.entity.User;

import com.alibaba.fastjson.JSONObject;

@Controller
public class IframeController {
	/**
	 * 加载iframe,带有一个id参数
	 * 
	 * @param controllerName
	 *            控制器对应的模板文件名称
	 * @param pageName
	 *            要加载的页面
	 * @return
	 */
	@RequestMapping("getIframeWithId")
	public String getIframeWithId(String controllerName, String pageName, Long id, Model model, HttpSession session,
			String params) {
		JSONObject jsonObject = JSONObject.parseObject(params);
		Collection<String> keys = jsonObject.keySet();
		for (String key : keys) {
			Object value = jsonObject.get(key);
			model.addAttribute(key, value);
		}
		model.addAttribute("user", (User) session.getAttribute("user"));
		return controllerName + "/" + pageName;
	}
}
