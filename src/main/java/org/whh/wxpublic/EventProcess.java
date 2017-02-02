package org.whh.wxpublic;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.MaterialDao;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.Material;
import org.whh.entity.WxPublicUser;
import org.whh.util.NullUtil;
import org.whh.vo.SceneObject;

import com.alibaba.fastjson.JSONObject;

@Component
public class EventProcess implements MsgProcess {
	@Autowired
	WxPublicUserDao publicUserDao;
	@Autowired
	TextProcess textProcess;

	@Autowired
	MessageSendService messageSendService;
	
	@Autowired
	MaterialDao materialDao;

	@Override
	public String process(String toUserName, String originalUserName, String createTime, Document document) {
		Element root = document.getRootElement();
		String eventType = root.element("Event").getText();
		switch (eventType) {
		case "subscribe":// 订阅
			return procesSubscribe(toUserName, originalUserName, root);
		case "unsubscribe":// 取消订阅
			return procesUnsubscribe(originalUserName);
		case "SCAN":
			return processScan(toUserName,originalUserName,root);
		case "LOCATION":
			break;
		case "CLICK":
			return processClick(toUserName, originalUserName, root);
		case "VIEW":
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public String process(String toUserName, String originUser, String createTime, String content) {
		return null;
	}

	/**
	 * 处理订阅
	 * 
	 * @param toUserName
	 * @param origionalUserName
	 * @param root
	 * @return
	 */
	private String procesSubscribe(String toUserName, String origionalUserName, Element root) {
		Element keyEle = root.element("EventKey");
		WxPublicUser user = publicUserDao.getByOpenId(origionalUserName);
		if (user == null) {
			user = new WxPublicUser();
			user.setCreateTime(new Date());
			user.setOpenId(origionalUserName);
		} else {
			user.setUpdateTime(new Date());
		}
		if (!NullUtil.isNull(keyEle)) {
			String key = keyEle.getText();
			if (!NullUtil.isNull(key)) {
				SceneObject object = JSONObject.parseObject(key.substring(8), SceneObject.class);
				int type = object.getType();
				switch (type) {
				case SceneObject.TYPE_RECOMMEND:
					String recommendOpenId = object.getValue();
					String oldRecommendId = user.getRecommendOpenId();
					if (oldRecommendId == null) {
						user.setRecommendOpenId(recommendOpenId);
						WxPublicUser recommendUser = publicUserDao.getByOpenId(recommendOpenId);
						Integer recommendCount = recommendUser.getRecommendCount();
						recommendUser.setRecommendCount(recommendCount == null ? 1 : recommendCount + 1);
						publicUserDao.save(recommendUser);
					} else {
						if (!recommendOpenId.equals(oldRecommendId)) {
							String note = "用户被二次推荐,推荐openId:" + recommendOpenId;
							if (user.getNote() == null) {
								user.setNote(note);
							} else {
								user.setNote(user.getNote() + ";" + note);
							}
						}
					}
					break;
				case SceneObject.TYPE_SCAN_MATERIAL:
				case SceneObject.TYPE_SCAN_URL:
					String value = object.getValue();
					JSONObject contentObjet = JSONObject.parseObject(value);
					Long mediaId = contentObjet.getLong("mediaId");
					Material material = materialDao.findOne(mediaId);
					user.setRecommendMediaId(mediaId);
					if (NullUtil.isNull(mediaId)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								String title = material.getTitle();
								String description = material.getDigest();
								String contentUrl = material.getUrl();
								String picUrl = material.getThumb_url();
								messageSendService.sendNews(origionalUserName, title, description, contentUrl, picUrl);
							}
						}).start();
					} else {
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								/**
								 * 此处发送关联的素材信息
								 */
								messageSendService.sendNews(origionalUserName, material.getMediaId());
							}
						}).start();
					}
					break;
				}
			}
		}
		publicUserDao.save(user);
		String welcomeMsg = "没有你的日子,总感觉生活少了什么， 终于还是把你盼来了。\r\n\r\n\r\n\r\n^_^,你还不了解我吧，聊聊呗。先告诉你,回复以下内容是不会有反应的：\r\n\r\n1.进入小店\r\n\r\n2.本期精彩内容\r\n\r\n3.随机推荐内容\r\n\r\n4.推荐好友赢好礼啦\r\n\r\n114.找客服?.什么鬼\r\n\r\n默认'聪明的'机器人将会和你聊天，请记住调戏机器人是一件没有道德的事，你看着办喽";
		Document responseDocument = WxXMLHelper.createTextDocument(origionalUserName, toUserName, welcomeMsg);
		return responseDocument.asXML();
	}

	/**
	 * 处理取消订阅
	 * 
	 * @param origionalUserName
	 * @return
	 */
	private String procesUnsubscribe(String origionalUserName) {
		WxPublicUser user = publicUserDao.getByOpenId(origionalUserName);
		if (user != null) {
			user.setUpdateTime(new Date());
			user.setSubscribe(true);
			publicUserDao.save(user);
		}
		return "";
	}
	
	private String processScan(String toUserName, String origionalUserName, Element root) {
		Element keyEle = root.element("EventKey");
		if (!NullUtil.isNull(keyEle)) {
			String key = keyEle.getText();
			if (!NullUtil.isNull(key)) {
				SceneObject object = JSONObject.parseObject(key, SceneObject.class);
				int type = object.getType();
				switch (type) {
				case SceneObject.TYPE_SCAN_MATERIAL:
				case SceneObject.TYPE_SCAN_URL:
					String value = object.getValue();
					JSONObject contentObjet = JSONObject.parseObject(value);
					Long mediaId = contentObjet.getLong("mediaId");
					Material material = materialDao.findOne(mediaId);
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								String title = material.getTitle();
								String description = material.getDigest();
								String contentUrl = material.getUrl();
								String picUrl = material.getThumb_url();
								messageSendService.sendNews(origionalUserName, title, description, contentUrl, picUrl);
							}
						}).start();
					break;
				}
			}
		}
		return "";
	}

	/**
	 * 处理用户点击
	 * 
	 * @param toUserName
	 * @param origionalUserName
	 * @param root
	 * @return
	 */
	private String processClick(String toUserName, String originalUserName, Element root) {
		Element keyEle = root.element("EventKey");
		String text = keyEle.getText();
		return textProcess.process(toUserName, originalUserName, null, text);
	}

}
