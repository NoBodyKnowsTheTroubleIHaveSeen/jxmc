package org.whh.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.util.XMLHelper;
import org.whh.wxpublic.EventProcess;
import org.whh.wxpublic.TextProcess;
import org.whh.wxpublic.WxServerValidateHelper;

/**
 * 对接微信公众号接口
 * 
 * @author Administrator
 *
 */
@Controller
public class WxPublicOpenController {
	Logger logger = LoggerFactory.getLogger(WxPublicOpenController.class);
	@Autowired
	TextProcess textProcess;
	@Autowired
	EventProcess eventProcess;

	@RequestMapping("/common/weChatPublic")
	@ResponseBody
	public String weChatPublic(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		boolean isValid = false;
		try {
			isValid = WxServerValidateHelper.checkSingature(signature, WxServerValidateHelper.TOKEN, timestamp, nonce);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (isValid) {
			if (echostr != null && !echostr.equals("")) {
				return echostr;
			}
			try {
				String response = processStream(request.getInputStream());
				return response;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return "系统繁忙请稍后再试...";
		}
		return "非法请求";
	}

	public String processStream(ServletInputStream inputStream) {
		Document document = XMLHelper.parse(inputStream);
		Element root = document.getRootElement();
		String toUserName = root.element("ToUserName").getText();
		String originUserName = root.element("FromUserName").getText();
		String createTime = root.element("CreateTime").getText();
		String msgType = root.element("MsgType").getText();
		String result = "系统繁忙，请稍后再试...";
		switch (msgType) {
		case "text":
			result = textProcess.process(toUserName, originUserName, createTime, document);
			break;
		case "event":
			result = eventProcess.process(toUserName, originUserName, createTime, document);
			break;
		}
		return result;
	}
	@RequestMapping("/common/getRecommendCode")
	public String getRecommendCode(String ticket,Model model)
	{
		model.addAttribute("ticket","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		return "wxpublic/recommend";
	}
}
