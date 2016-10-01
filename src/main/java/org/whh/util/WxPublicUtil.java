package org.whh.util;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信辅助类
 * 
 * @author ASUS-PC
 */
@Component
public class WxPublicUtil {

	private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static String accessToken = null;

	@Scheduled(fixedRate = 1000 * 7000)
	public void refreshToken()
	{
		getAccessToken();
	}
	
	public static String getAccessToken() {
//		return "gxKeYMck2ZU9yClqzIXRK5bSleshZuzeHr06bKa9bYGFExFgFzUQAVyk8w45KKVxooCtZJXCWvXNH5XIw2kWOejfY2Bma8TcL-WpvoicFpA_woZuERWBtXhOGChWpnL5BUOeAFAXQK";
		return getAccessToken(PropertyUtil.getProperty("appid"), PropertyUtil.getProperty("secret"));
	}

	/**
	 * 获取token
	 * 
	 * @param appId
	 * @param secrect
	 * @return
	 */
	public static String getAccessToken(String appId, String secrect) {
		String response = HttpClientHelper.get(API_URL+"?grant_type=client_credential&appid="+appId+"&secret="+secrect,null);
		JSONObject obj = JSONObject.parseObject(response);
		accessToken = obj.getString("access_token");
		return accessToken;
	}

	public static JSONObject getPublicParam() {
		JSONObject object = new JSONObject();
//		object.put("access_token", accessToken);
		object.put("access_token", getAccessToken());
		return object;
	}
}
