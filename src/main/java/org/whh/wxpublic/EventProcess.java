package org.whh.wxpublic;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.WxPublicUserDao;
import org.whh.entity.WxPublicUser;
import org.whh.util.NullUtil;

@Component
public class EventProcess implements MsgProcess {
	@Autowired
	WxPublicUserDao publicUserDao;
	@Autowired
	TextProcess textProcess;

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
			break;
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
			String key = keyEle.getText();// 返回示例,qrscene_oxjz9srK72sIEPj5xcSDJPDMWJz8
			if (!NullUtil.isNull(key)) {
				String recommendOpenId = key.substring(8);
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
			}
		}
		publicUserDao.save(user);
		String welcomeMsg = "没有你的日子里,总是感觉生活缺点什么。 还好,总算把你盼来了。\r\n\r\n\r\n\r\n^_^,好了,很不幸的告诉你,回复以下内容是不会有反应的：\r\n\r\n1.进入小店\r\n\r\n2.本期精彩,一睹为快\r\n\r\n3.随机浏览,发现更多有趣的\r\n\r\n4.推荐好友赢好礼啦\r\n\r\n?.百宝箱";
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
