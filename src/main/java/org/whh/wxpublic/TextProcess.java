package org.whh.wxpublic;

import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.ConfigInfoDao;
import org.whh.dao.MaterialDao;
import org.whh.dao.QrCodeInfoDao;
import org.whh.dao.WxKeywordMapDao;
import org.whh.dao.WxSubscribeCallDao;
import org.whh.dao.WxSubscriberMessageDao;
import org.whh.entity.ConfigInfo;
import org.whh.entity.Material;
import org.whh.entity.QrCodeInfo;
import org.whh.entity.WxKeywordMap;
import org.whh.entity.WxSubscriberCall;
import org.whh.entity.WxSubscriberMessage;
import org.whh.robot.TulingUtil;
import org.whh.service.QrcodeInfoService;
import org.whh.util.NullUtil;
import org.whh.util.RandomHelper;
import org.whh.vo.SceneObject;

@Component
public class TextProcess implements MsgProcess {

	@Autowired
	WxKeywordMapDao mapDao;
	@Autowired
	MaterialDao materialDao;
	@Autowired
	QrCodeInfoDao qrCodeInfoDao;
	@Autowired
	MessageSendService messageSend;
	@Autowired
	WxSubscriberMessageDao wxSubscriberMessageDao;
	@Autowired
	WxSubscribeCallDao wxSubscribeCallDao;
	@Autowired
	QrcodeInfoService qrcodeInfoService;
	@Autowired
	ConfigInfoDao configInfoDao;
	@Autowired
	MessageSendService messageSendService;
	/**
	 * 进入首页
	 */
	static final String CODE_INTO_INDEX = "1";
	/**
	 * 本期内容
	 */
	static final String CODE_NOW_CONTENT = "2";
	/**
	 * 随机文章
	 */
	static final String CODE_RANDOM_CONTNET = "3";
	/**
	 * 推荐
	 */
	static final String CODE_RECOMMEND = "4";
	/**
	 * 随机笑话
	 */
	static final String CODE_RANDOM_JOKE = "6";

	static final String CODE_NOW_JOKE = "5";

	static final String CODE_HISTORY = "7";

	static final String CODE_GROUP_QR_CODE = "11";

	/**
	 * 帮助
	 */
	static final String CODE_HELP = "?";
	static final String CODE_HELP_CN = "？";

	private Document getRandomMaterial(List<Material> materials, String fromUserName, String toUserName) {
		Document responseDocument = null;
		int size = materials.size();
		int random = RandomHelper.getRandom(size);
		Material material = materials.get(random);
		responseDocument = WxXMLHelper.createNewsDocument(fromUserName, toUserName, material.getTitle(),
				material.getDigest(), material.getThumb_url(), material.getUrl());
		return responseDocument;
	}

	@Override
	public String process(String toUserName, String originUser, String createTime, Document document) {
		Element root = document.getRootElement();
		String content = root.element("Content").getText();
		return process(toUserName, originUser, createTime, content);
	}

	@Override
	public String process(String toUserName, String originUser, String createTime, String content) {
		Document responseDocument = null;
		WxSubscriberMessage message = new WxSubscriberMessage();
		message.setMsgType(WxSubscriberMessage.TYPE_TEXT);
		message.setContent(content);
		message.setCreateTime(new Date());
		message.setFromUserName(originUser);
		message.setToUserName(toUserName);
		message.setSendType(WxSubscriberMessage.SEND_TYPE_RECEIVE);
		switch (content) {
		case CODE_INTO_INDEX:
			message.setDescription("输入代码：" + CODE_INTO_INDEX);
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, "聚享茶业", "欢迎进入聚享小店",
					"http://wd.geilicdn.com/vshop839209114-1448766724.jpg?w=640&h=330&cp=1",
					"http://weidian.com/?userid=839209114");
			break;
		case CODE_NOW_CONTENT:
			message.setDescription("输入代码：" + CODE_NOW_CONTENT);
			Material material = materialDao.findByMediaId("90hC4K9jq_d_P3Ywgl9I2dJmWTqyeQol8jOH-CsDFxE");
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, material.getTitle(),
					material.getDigest(), material.getThumb_url(), material.getUrl());
			break;
		case CODE_RANDOM_CONTNET:
			message.setDescription("输入代码：" + CODE_RANDOM_CONTNET);
			List<Material> contents = materialDao.findAllUsed();
			responseDocument = getRandomMaterial(contents, originUser, toUserName);
			break;
		case CODE_RANDOM_JOKE:
			message.setDescription("输入代码：" + CODE_RANDOM_JOKE);
			List<Material> jokes = materialDao.findByTypeAndIsUsed(Material.TYPE_JOKE, true);
			responseDocument = getRandomMaterial(jokes, originUser, toUserName);
			break;
		case CODE_NOW_JOKE:
			message.setDescription("输入代码：" + CODE_NOW_JOKE);
			List<Material> nowJokes = materialDao.findByMaterialStatus(Material.MATERIAL_STATUS_CURRENT_JOKE);
			if (nowJokes.size() > 0) {
				Material nowJoke = nowJokes.get(0);
				responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, nowJoke.getTitle(),
						nowJoke.getDigest(), nowJoke.getThumb_url(), nowJoke.getUrl());
			}
			break;
		case CODE_RECOMMEND:
			message.setDescription("输入代码：" + CODE_RECOMMEND);
			SceneObject object = new SceneObject();
			object.setType(SceneObject.TYPE_RECOMMEND);
			object.setValue(originUser);
			QrCodeInfo info = qrcodeInfoService.createAndGetQrCodeInfo(object);
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, "分享给小伙伴,推荐有礼啦",
					"   分享完成即可获得茶具一套或铁观音密茶一盒!",
					"https://mmbiz.qlogo.cn/mmbiz_jpg/S3RO59TkTwg8ra3Xj4ITk1Ixymibia1HgEIicMFRArfwYic38QCR8MXVU20M1rWI2WiaWSe0JgQCU5QYpHugNY5R3Zg/0?wx_fmt=jpeg",
					"https://www.qzjxcy.com/common/getRecommendCode?ticket=" + info.getTicket());
			break;
		case CODE_HELP:
		case CODE_HELP_CN:
			message.setDescription("输入代码：" + CODE_NOW_CONTENT);
			Material help = materialDao.findByMediaId("90hC4K9jq_d_P3Ywgl9I2XQIqtnHGvTiIVNZaXCIVOA");
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, help.getTitle(), help.getDigest(),
					help.getThumb_url(), help.getUrl());
			break;
		case CODE_GROUP_QR_CODE:
			message.setDescription("输入代码：" + CODE_GROUP_QR_CODE);
			ConfigInfo configInfo = configInfoDao.findOne(1L);
			if (configInfo != null && configInfo.getGroupQrCodeMaterailId() != null) {
				String msg = "加入微信群与小伙伴们谈天说地，扫描二维码即可进入哦";
				responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, msg);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				messageSendService.sendImage(originUser, configInfo.getGroupQrCodeMaterailId());
			}
			break;
		case CODE_HISTORY:
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, "往期精彩", "公众号发布的历史精彩内容",
					"https://mmbiz.qlogo.cn/mmbiz_png/S3RO59TkTwgqkD0lndBdfzAUN1VbxQU4fxJopa1QDPznIhN8hX8UW0ZJSEGbic2MtJncZLkZFqJcQfiby9f96jibA/0?wx_fmt=png",
					"https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAwMjk0MDYxNA==&scene=124#wechat_redirect");
			break;
		default:
			break;
		}
		/**
		 * 根据输入码获取内容
		 */
		if (responseDocument == null) {
			Material codeMaterail = materialDao.findByInputCode(content);
			if (codeMaterail != null) {
				message.setDescription("根据输入码获取内容：" + content);
				if (codeMaterail.getType() == Material.TYPE_LOCAL_URL) {
					codeMaterail.setUrl(codeMaterail.getUrl() + "&isSubscriber=true");
				}
				responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, codeMaterail.getTitle(),
						codeMaterail.getDigest(), codeMaterail.getThumb_url(), codeMaterail.getUrl());
				/*
				 * Integer action = codeMaterail.getAction(); if (action != null
				 * && action == Material.ACTION_PUSH_RECOMMEND) { try {
				 * SceneObject object = new SceneObject();
				 * object.setType(SceneObject.TYPE_RECOMMEND);
				 * object.setValue(originUser); QrCodeInfo info =
				 * qrcodeInfoService.createAndGetQrCodeInfo(object);
				 * messageSend.sendNews(originUser, "分享给小伙伴,推荐有礼啦",
				 * "    扫描图文内的二维码或发送二维码,分享给20个好友关注本公众号就能领取奖品啦。\r\n\r\n    完成分享并关注就能获得茶具一套，数量有限，抓紧时间啦！\r\n\r\n   分享后就会感觉自己萌萌哒,不信你试试。"
				 * , "https://www.qzjxcy.com/common/getRecommendCode?ticket=" +
				 * info.getTicket(),
				 * "https://mmbiz.qlogo.cn/mmbiz_jpg/S3RO59TkTwg8ra3Xj4ITk1Ixymibia1HgEIicMFRArfwYic38QCR8MXVU20M1rWI2WiaWSe0JgQCU5QYpHugNY5R3Zg/0?wx_fmt=jpeg"
				 * ); } catch (Exception ex) { ex.printStackTrace(); } }
				 */
			}
		}
		if (responseDocument == null) {
			WxKeywordMap keywordMap = mapDao.findByKeyword(content);
			if (!NullUtil.isNull(keywordMap)) {
				message.setDescription("根据关键词找到内容：" + content);
				String msgType = keywordMap.getMsgType();
				if ("news".equals(msgType)) {
					// url后加 "&isSubscriber=true"表示用户已订阅
					if(!originUser.equals("oxjz9sukXD2TSuxuKJGPbisEL3AY"))
					{
						keywordMap.setUrl(keywordMap.getUrl() + "&isSubscriber=true");
					}
					responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, keywordMap.getTitle(),
							keywordMap.getDescription(), keywordMap.getPicUrl(),
							keywordMap.getUrl());
				} else if ("text".equals(msgType)) {
					responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, keywordMap.getContent());
				}
			}
		}
		if (responseDocument == null) {
			message.setDescription("接入机器人：" + content);
			WxSubscriberCall call = wxSubscribeCallDao.findByOpenId(originUser);
			boolean isFirst = false;// 会话刚开始接入
			if (call == null) {
				isFirst = true;
				call = new WxSubscriberCall();
				call.setCreateTime(new Date());
				call.setLastCallStratTime(System.currentTimeMillis());
				call.setOpenId(originUser);
				call.setStatus(WxSubscriberCall.STATUS_JQR);
			} else {
				Long lastCallStartTime = call.getLastCallStratTime();
				if ((System.currentTimeMillis() - lastCallStartTime) > 30L * 60 * 1000) {
					isFirst = true;
					call.setStatus(WxSubscriberCall.STATUS_JQR);
				}
				call.setLastCallStratTime(System.currentTimeMillis());
			}
			if (content.equals("114")) {
				call.setStatus(WxSubscriberCall.STATUS_KF);
				isFirst = false;
			} else {
				call.setStatus(WxSubscriberCall.STATUS_JQR);
			}
			wxSubscribeCallDao.save(call);
			boolean isKfOnline = KFMananger.isKfOnline();
			/**
			 * 接入机器人
			 */
			responseDocument = TulingUtil.getWxResponse(originUser, toUserName, content, isFirst, call.getStatus(),
					isKfOnline);
			/**
			 * 是否有客服在线
			 */
			if (isKfOnline && call.getStatus() == WxSubscriberCall.STATUS_KF) {
				responseDocument.getRootElement().element("MsgType").setText("transfer_customer_service");
			}
		}
		wxSubscriberMessageDao.save(message);
		String result = "";
		if (!NullUtil.isNull(responseDocument)) {
			WxSubscriberMessage responseMessage = new WxSubscriberMessage();
			responseMessage.setCreateTime(new Date());
			responseMessage.setFromUserName(toUserName);
			responseMessage.setSendType(WxSubscriberMessage.SEND_TYPE_SEND);
			responseMessage.setMsgType(responseDocument.getRootElement().elementText("MsgType"));
			responseMessage.setContent(responseDocument.asXML());
			wxSubscriberMessageDao.save(responseMessage);
			result = responseDocument.asXML();
		}
		return result;
	}
}
