package org.whh.wxpublic;

import org.springframework.stereotype.Component;
import org.whh.util.HttpClientHelper;
import org.whh.util.WxPublicUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 客户接口
 * 
 * @author ASUS-PC
 *
 */
@Component
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
	

	String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	/**
	 * 发送文本消息
	 * 
	 * @param openId
	 * @param text
	 */
	public void sendText(String openId, String text) {
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject params = new JSONObject();
		params.put("touser", openId);
		params.put("msgtype", MSG_TYPE_TEXT);
		JSONObject textParam = new JSONObject();
		textParam.put("content", text);
		params.put("text", textParam);
		String response = HttpClientHelper.post(sendUrl, params.toJSONString());
		System.out.println(response);

	}
	
	/**
	 * 发送图片消息
	 * @param openId
	 * @param mediaId
	 */
	public void sendImage(String openId,String mediaId)
	{
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("msgtype", "image");
		JSONObject image = new JSONObject();
		image.put("media_id", mediaId);
		param.put("image", image);
		String response = HttpClientHelper.post(sendUrl, param);
		System.out.println(response);
	}
	
	public void sendNews(String openId,String mediaId)
	{
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("msgtype", "mpnews");
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		param.put("mpnews", mpnews);
		String response = HttpClientHelper.post(sendUrl, param);
		System.out.println(response);
	}
	/**
	 * 发送图文消息（点击跳转到外链） 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应。
	 * @param openId
	 * @param title
	 * @param description
	 * @param contentUrl
	 * @param picUrl
	 */
	public void sendNews(String openId,String title,String description,String contentUrl,String picUrl)
	{
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("msgtype", "news");
		JSONObject articals = new JSONObject();
		JSONArray articalsArr= new JSONArray();
		JSONObject artical = new JSONObject();
		artical.put("title", title);
		artical.put("description", description);
		artical.put("url", contentUrl);
		artical.put("picurl", picUrl);
		articalsArr.add(artical);
		articals.put("articles", articalsArr);
		param.put("news", articals);
		String response = HttpClientHelper.post(sendUrl, param);
		System.out.println(response);
	}
	
	public void sendWxCard(String openId,String cardId)
	{
		String sendUrl = url + WxPublicUtil.getAccessToken();
		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("msgtype", "wxcard");
		JSONObject wxcard = new JSONObject();
		wxcard.put("card_id", cardId);
		param.put("wxcard", wxcard);
		String response = HttpClientHelper.post(sendUrl, param);
		System.out.println(response);
	}
//
//	public static void main(String[] args) {
//		
////		new MessageSend().sendText("oxjz9srK72sIEPj5xcSDJPDMWJz8", "i came from customer service interface ^_^ ");
////		new MessageSend().sendImage("oxjz9srK72sIEPj5xcSDJPDMWJz8", "_cTL3zLnzUlRiCf6wxYig0tHfeyaUQFCcx18sHQlEI0");
////		new MessageSend().sendNews("oxjz9srK72sIEPj5xcSDJPDMWJz8", "90hC4K9jq_d_P3Ywgl9I2fVtZB_Yi0l0Kkg3SKZrt8A");
////		new MessageSend().sendNews("oxjz9srK72sIEPj5xcSDJPDMWJz8", "fuck","fuck you","http://mmbiz.qpic.cn/mmbiz_jpg/k9VXYZNbAHYE3MU8Nia7Rw9WHuC10sqWVvxbc9tcoSnv4LsYMsCM2tRmmswPqdDdiaL6jI3Sx3Hdp9WSWfvTWqTg/0?wx_fmt=jpeg","http://mmbiz.qpic.cn/mmbiz_jpg/k9VXYZNbAHYE3MU8Nia7Rw9WHuC10sqWVvxbc9tcoSnv4LsYMsCM2tRmmswPqdDdiaL6jI3Sx3Hdp9WSWfvTWqTg/0?wx_fmt=jpeg");
////		new MessageSend().sendWxCard("oxjz9srK72sIEPj5xcSDJPDMWJz8", "p2xQRxGJ63OF8WEjbJ2seLhnyTqA");
//	}
}
