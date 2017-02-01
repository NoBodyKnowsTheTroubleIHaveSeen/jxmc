package org.whh.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信辅助类
 * 
 * @author ASUS-PC
 */
public class WeChatUtil {

	private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/token";

	private static Long lastGetAccesTokenTime = null;

	private static String accessToken = null;

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
		if (lastGetAccesTokenTime != null && System.currentTimeMillis() - lastGetAccesTokenTime < 5000 * 1000L) {
			JSONObject param = new JSONObject();
			param.put("grant_type", "client_credential");
			param.put("appid", appId);
			param.put("secret", secrect);
			String response = HttpClientHelper.post(API_URL, param);
			JSONObject obj = JSONObject.parseObject(response);
			accessToken = obj.getString("access_token");
			lastGetAccesTokenTime = System.currentTimeMillis();
		}
		return accessToken;
	}

	public static JSONObject getPublicParam() {
		JSONObject object = new JSONObject();
		object.put("access_token", getAccessToken());
		return object;
	}
}
