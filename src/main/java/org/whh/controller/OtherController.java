package org.whh.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.ContentsDao;
import org.whh.dao.JokeInfoDao;
import org.whh.entity.Contents;
import org.whh.entity.JokeInfo;
import org.whh.flush.BaiSiBrowser;
import org.whh.flush.GuoKerBrowser;
import org.whh.flush.GzhAutoSend;
import org.whh.flush.QiuBaiBrowser;
import org.whh.flush.SanWenBrowser;
import org.whh.web.CommonMessage;

import com.alibaba.fastjson.JSONObject;

@Controller
public class OtherController extends ControllerBase {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	QiuBaiBrowser browser;
	@Autowired
	BaiSiBrowser baiSiBrowser;
	@Autowired
	SanWenBrowser sanWenBrowser;
	@Autowired
	GuoKerBrowser guoKerBrowser;

	@Autowired
	ContentsDao contentsDao;

	@Autowired
	JokeInfoDao jokeInfoDao;

	@Autowired
	GzhAutoSend gzhAutoSend;

	@RequestMapping("/grab")
	@ResponseBody
	public String grab() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				logger.debug("开始启动抓取...");
				// browser.grab();
				// baiSiBrowser.grab();
				sanWenBrowser.grab();
				// guoKerBrowser.grab();
			}
		}).start();
		return "success";
	}

	@RequestMapping("/gzhSend")
	@ResponseBody
	public String gzhSend(String page, String id, String userName, String pwd) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				gzhAutoSend.send(page, id, userName, pwd);
			}
		}).start();
		return "success";
	}

	@RequestMapping("/content")
	public String index(Model model) {
		return "content/content";
	}

	private static List<JokeInfo> infos = null;

	@RequestMapping("/getNextContent")
	@ResponseBody
	public String getNextContent() {
		if (infos == null) {
			infos = jokeInfoDao.getNextContent2();
		}
		JSONObject object = new JSONObject();
		object.put("content", infos.get(0).getContent());
		object.put("id", infos.get(0).getId());
		if (isNull(infos.get(0).getThumb())) {

			object.put("thumb", "http://static.qiushibaike.com/images/thumb/missing.png");
		} else {
			object.put("thumb", infos.get(0).getThumb());
		}
		infos.remove(0);
		return object.toJSONString();
	}

	@RequestMapping("/auditContent")
	@ResponseBody
	public String auditContent(Long id, Integer auditStatus) {
		JokeInfo info = jokeInfoDao.findOne(id);
		info.setAuditStatus(auditStatus);
		jokeInfoDao.save(info);
		return "success";
	}

	@RequestMapping("/addContent")
	@ResponseBody
	public CommonMessage addContent(Contents content) {
		CommonMessage message = new CommonMessage();
		if (isNull(content.getContent())) {
			message.setMessage("内容不能为空，添加失败!");
			return message;
		}
		Contents oldJoke = contentsDao.findByContent(content.getContent());
		if (!isNull(oldJoke)) {
			message.setMessage("内容已存在,不要重复添加!");
			return message;
		}
		content.setCreateTime(new Date());
		content.setIsUsed(false);
		contentsDao.save(content);
		message.setMessage("添加成功!");
		return message;
	}
}