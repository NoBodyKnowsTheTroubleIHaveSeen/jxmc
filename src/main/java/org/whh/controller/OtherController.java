package org.whh.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.JokeDao;
import org.whh.entity.Joke;
import org.whh.flush.BaiSiBrowser;
import org.whh.flush.QiuBaiBrowser;
import org.whh.web.CommonMessage;

@Controller
public class OtherController extends ControllerBase {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	QiuBaiBrowser browser;
	@Autowired
	BaiSiBrowser baiSiBrowser;
	@Autowired
	JokeDao jokeDao;

	@RequestMapping("/grab")
	@ResponseBody
	public void grab() {
		logger.debug("");
//		browser.grab();
		baiSiBrowser.grab();
	}

	@RequestMapping("/joke")
	public String index(Model model) {
		return "joke/joke";
	}

	@RequestMapping("/addJoke")
	@ResponseBody
	public CommonMessage addJoke(Joke joke) {
		CommonMessage message = new CommonMessage();
		if (isNull(joke.getContent())) {
			message.setMessage("内容不能为空，添加失败!");
			return message;
		}
		Joke oldJoke = jokeDao.findByContent(joke.getContent());
		if (!isNull(oldJoke)) {
			message.setMessage("内容已存在,不要重复添加!");
			return message;
		}
		joke.setCreateTime(new Date());
		joke.setIsUsed(false);
		jokeDao.save(joke);
		message.setMessage("添加成功!");
		return message;
	}
}
