package org.whh.robot;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whh.entity.WxSubscriberCall;
import org.whh.util.HttpClientHelper;
import org.whh.wxpublic.WxXMLHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TulingUtil {

	static Logger logger = LoggerFactory.getLogger(TulingUtil.class);

	private static String APIAddress = "http://www.tuling123.com/openapi/api";

	private static String APIkey = "9a71099ecba148489125e55c191e2de6";

	private static String defaultImgUrl = "https://www.baidu.com/img/bd_logo1.png";

	public static Document getWxResponse(String originUser, String toUserName, String query, boolean isFirst,
			Integer status, boolean isKfOnlie) {
		Document responseDocument = null;
		String text = "";
		isFirst = true;
		if (isFirst) {
			text = "回复\"114\"可转到客服哦,有空就陪我聊会天呗,我可是天下最聪明的机器人萝卜特007";
			responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, text);
			return responseDocument;
		} else if (status == WxSubscriberCall.STATUS_KF && !isKfOnlie) {
			text = "额额，汗啊!客服们一搬完砖就回去睡觉了。就由聪明伶俐的我来陪你聊聊天好了。^_^";
			responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, text);
			return responseDocument;
		}
		JSONObject result = getResponse(originUser, query);
		logger.info(query + "请求图灵接口返回：" + result);
		Integer code = result.getInteger("code");
		switch (code) {
		case TulingConstant.CODE_TEXT:
			text = result.getString("text");
			responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, text);
			break;
		case TulingConstant.CODE_NEWS:
			String newsTitle = result.getString("text");
			JSONArray array = result.getJSONArray("list");
			JSONObject object = array.getJSONObject(0);
			String icon = object.getString("icon");
			String detailurl = object.getString("detailurl");
			String newsDescription = "打开链接查看详情";
			if (icon == null || icon.equals("")) {
				icon = defaultImgUrl;
			}
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, newsTitle, newsDescription, icon,
					detailurl);
			break;
		case TulingConstant.CODE_LINK:
			String linkTitle = result.getString("text");
			String url = result.getString("url");
			String linkDescription = "打开链接查看详情";
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, linkTitle, linkDescription,
					defaultImgUrl, url);
			break;
		case TulingConstant.CODE_COOKBOOK:
			String cookTitle = result.getString("text");
			JSONArray cookArray = result.getJSONArray("list");
			JSONObject cookObj = cookArray.getJSONObject(0);
			String cookIcon = cookObj.getString("icon");
			String cookDetailUrl = cookObj.getString("detailurl");
			String cookDescription = "打开链接查看详情";
			if (cookIcon == null || cookIcon.equals("")) {
				cookIcon = defaultImgUrl;
			}
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, cookTitle, cookDescription,
					cookIcon, cookDetailUrl);
			break;
		}
		return responseDocument;
	}

	public static JSONObject getResponse(String uesrId, String query) {
		JSONObject params = new JSONObject();
		params.put("key", APIkey);
		params.put("info", query);
		params.put("userid", uesrId);
		String response = HttpClientHelper.post(APIAddress, params);
		JSONObject result = JSONObject.parseObject(response);
		return result;
	}

	// public static void main(String[] args) {
	// Document document = getWxResponse("123", "456", "你好");
	// System.out.println(document.asXML());
	// }
}
