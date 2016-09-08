package org.whh.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信辅助类
 * 
 * @author ASUS-PC
 */
public class WeChatUtil {

	private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/token";

	public static String getAccessToken() {
		return getAccessToken(ConfigPropertyUtil.getProperty("appid"), ConfigPropertyUtil.getProperty("secret"));
	}

	/**
	 * 获取token
	 * 
	 * @param appId
	 * @param secrect
	 * @return
	 */
	public static String getAccessToken(String appId, String secrect) {
		JSONObject param = new JSONObject();
		param.put("grant_type", "client_credential");
		param.put("appid", appId);
		param.put("secret", secrect);
		String response = HttpClientHelper.post(API_URL, param);
		JSONObject obj = JSONObject.parseObject(response);
		String accessToken = obj.getString("access_token");
		return accessToken;
	}

	public static JSONObject getPublicParam() {
		String accessToken = getAccessToken();
		JSONObject object = new JSONObject();
		object.put("access_token", accessToken);
		return object;
	}
}
