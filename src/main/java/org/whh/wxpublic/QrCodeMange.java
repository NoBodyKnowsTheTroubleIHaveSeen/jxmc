package org.whh.wxpublic;

import org.whh.util.HttpClientHelper;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONObject;

public class QrCodeMange {
	static String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

	/**
	 * 生成永久的二维码
	 * 
	 * @param actionName
	 * @param sceneId
	 * @param sceneStr
	 */
	public static JSONObject generateLimitCode(Integer sceneId) {
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject params = new JSONObject();
		params.put("action_name", "QR_LIMIT_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", sceneId);
		action_info.put("scene", scene);
		params.put("action_info", action_info);
		String response = HttpClientHelper.post(sendUrl, params.toJSONString());
		JSONObject result = JSONObject.parseObject(response);
		String ticket = result.getString("ticket");
		String url = result.getString("url");
		System.out.println(response);
		System.out.println(ticket + "," + url);
		return result;
	}

	public static JSONObject generateLimitSrcCode(String sceneStr) {
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject params = new JSONObject();
		params.put("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_str", sceneStr);
		action_info.put("scene", scene);
		params.put("action_info", action_info);
		String response = HttpClientHelper.post(sendUrl, params.toJSONString());
		JSONObject result = JSONObject.parseObject(response);
		String ticket = result.getString("ticket");
		String url = result.getString("url");
		System.out.println(response);
		System.out.println(ticket + "," + url);
		return result;
	}

	public static void main(String[] args) {
		generateLimitCode(100000);
	}
}
