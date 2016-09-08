package org.whh.wxpublic;

import org.whh.util.HttpClientHelper;
import org.whh.util.WeChatUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 客户接口
 * 
 * @author ASUS-PC
 *
 */
public class MessageSend {
	public static final String MSG_TYPE_TEXT = "text";// 发送文本消息
	public static final String MSG_TYPE_IMAGE = "image";// 发送图片消息
	public static final String MSG_TYPE_VOICE = "voice";// 发送语音消息
	public static final String MSG_TYPE_VIDEO = "video";// 发送视频消息
	public static final String MSG_TYPE_MUSIC = "music";// 发送音乐消息
	public static final String MSG_TYPE_NEWS = "news";// 发送图文消息（点击跳转到外链）
														// 图文消息条数限制在8条以内
	public static final String MSG_TYPE_MPNEWS = "mpnews";// 发送图文消息（点击跳转到图文消息页面）
															// 图文消息条数限制在8条以内
	public static final String MSG_TYPE_WXCARD = "wxcard";// 发送卡券

	/**
	 * 发送文本消息
	 * 
	 * @param openId
	 * @param text
	 */
	public void sendText(String openId, String text) {
		String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ WeChatUtil.getAccessToken();
		JSONObject params = new JSONObject();
		params.put("touser", openId);
		params.put("msgtype", MSG_TYPE_TEXT);
		params.put("text", text);
		String response = HttpClientHelper.post(sendUrl, params);
		System.out.println(response);

	}

}
