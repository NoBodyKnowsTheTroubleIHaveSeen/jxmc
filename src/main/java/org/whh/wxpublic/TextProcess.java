package org.whh.wxpublic;

import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.MaterialDao;
import org.whh.dao.QrCodeInfoDao;
import org.whh.dao.WxKeywordMapDao;
import org.whh.entity.Material;
import org.whh.entity.QrCodeInfo;
import org.whh.entity.WxKeywordMap;
import org.whh.util.NullUtil;
import org.whh.util.RandomHelper;

import com.alibaba.fastjson.JSONObject;

@Component
public class TextProcess implements MsgProcess {

	@Autowired
	WxKeywordMapDao mapDao;
	@Autowired
	MaterialDao materialDao;
	@Autowired
	QrCodeInfoDao qrCodeInfoDao;
	@Autowired
	MessageSend messageSend;
	/**
	 * 进入首页
	 */
	private static final String CODE_INTO_INDEX = "1";
	/**
	 * 本期内容
	 */
	private static final String CODE_NOW_CONTENT = "2";
	/**
	 * 随机文章
	 */
	private static final String CODE_RANDOM_CONTNET = "3";
	/**
	 * 推荐
	 */
	private static final String CODE_RECOMMEND = "4";
	/**
	 * 随机笑话
	 */
	private static final String CODE_RANDOM_JOKE = "6";

	private static final String CODE_NOW_JOKE = "5";

	/**
	 * 帮助
	 */
	private static final String CODE_HELP = "?";
	private static final String CODE_HELP_CN = "？";

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
		switch (content) {
		case CODE_INTO_INDEX:
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, "聚享茶业", "欢迎进入聚享小店",
					"http://wd.geilicdn.com/vshop839209114-1448766724.jpg?w=640&h=330&cp=1",
					"http://weidian.com/?userid=839209114");
			break;
		case CODE_NOW_CONTENT:
			Material material = materialDao.findByMediaId("90hC4K9jq_d_P3Ywgl9I2dJmWTqyeQol8jOH-CsDFxE");
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, material.getTitle(),
					material.getDigest(), material.getThumb_url(), material.getUrl());
			break;
		case CODE_RANDOM_CONTNET:
			List<Material> contents = materialDao.findAllUsed();
			responseDocument = getRandomMaterial(contents, originUser, toUserName);
			break;
		case CODE_RANDOM_JOKE:
			List<Material> jokes = materialDao.findByType(Material.TYPE_JOKE);
			responseDocument = getRandomMaterial(jokes, originUser, toUserName);
			break;
		case CODE_NOW_JOKE:
			List<Material> nowJokes = materialDao.findByMaterialStatus(Material.MATERIAL_STATUS_CURRENT_JOKE);
			if (nowJokes.size() > 0) {
				Material nowJoke = nowJokes.get(0);
				responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, nowJoke.getTitle(),
						nowJoke.getDigest(), nowJoke.getThumb_url(), nowJoke.getUrl());
			}
			break;
		case CODE_RECOMMEND:
			QrCodeInfo info = qrCodeInfoDao.findByActionNameAndSceneStr(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE,
					originUser);
			if (info == null) {
				JSONObject code = QrCodeMange.generateLimitSrcCode(originUser);
				info = new QrCodeInfo();
				info.setActionName(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE);
				info.setCreateTime(new Date());
				info.setSceneStr(originUser);
				info.setTicket(code.getString("ticket"));
				info.setUrl(code.getString("url"));
				qrCodeInfoDao.save(info);
			}
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, "分享给小伙伴,推荐有礼啦",
					"    扫描图文内的二维码或发送二维码,分享给20个好友关注本公众号就能领取奖品啦。\r\n\r\n    完成分享并关注就能获得茶具一套，数量有限，抓紧时间啦！\r\n\r\n   分享后就会感觉自己萌萌哒,不信你试试。",
					"https://mmbiz.qlogo.cn/mmbiz_jpg/S3RO59TkTwg8ra3Xj4ITk1Ixymibia1HgEIicMFRArfwYic38QCR8MXVU20M1rWI2WiaWSe0JgQCU5QYpHugNY5R3Zg/0?wx_fmt=jpeg",
					"http://whhwkm.xicp.net/common/getRecommendCode?ticket=" + info.getTicket());
			break;
		case CODE_HELP:
		case CODE_HELP_CN:
			Material help = materialDao.findByMediaId("90hC4K9jq_d_P3Ywgl9I2XQIqtnHGvTiIVNZaXCIVOA");
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, help.getTitle(), help.getDigest(),
					help.getThumb_url(), help.getUrl());
		default:
			break;
		}
		if (responseDocument == null) {
			Material codeMaterail = materialDao.findByInputCode(content);
			responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, codeMaterail.getTitle(),
					codeMaterail.getDigest(), codeMaterail.getThumb_url(), codeMaterail.getUrl());
			Integer action = codeMaterail.getAction();
			if (action == Material.ACTION_PUSH_RECOMMEND) {
				try {
					QrCodeInfo info = qrCodeInfoDao
							.findByActionNameAndSceneStr(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE, originUser);
					if (info == null) {
						JSONObject code = QrCodeMange.generateLimitSrcCode(originUser);
						info = new QrCodeInfo();
						info.setActionName(QrCodeInfo.ACTION_NAME_QR_LIMIT_STR_SCENE);
						info.setCreateTime(new Date());
						info.setSceneStr(originUser);
						info.setTicket(code.getString("ticket"));
						info.setUrl(code.getString("url"));
						qrCodeInfoDao.save(info);
					}
					messageSend.sendNews(originUser, "分享给小伙伴,推荐有礼啦",
							"    扫描图文内的二维码或发送二维码,分享给20个好友关注本公众号就能领取奖品啦。\r\n\r\n    完成分享并关注就能获得茶具一套，数量有限，抓紧时间啦！\r\n\r\n   分享后就会感觉自己萌萌哒,不信你试试。",
							"http://whhwkm.xicp.net/common/getRecommendCode?ticket=" + info.getTicket(),
							"https://mmbiz.qlogo.cn/mmbiz_jpg/S3RO59TkTwg8ra3Xj4ITk1Ixymibia1HgEIicMFRArfwYic38QCR8MXVU20M1rWI2WiaWSe0JgQCU5QYpHugNY5R3Zg/0?wx_fmt=jpeg");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		if (responseDocument == null) {
			WxKeywordMap keywordMap = mapDao.findByKeyword(content);
			if (!NullUtil.isNull(keywordMap)) {
				String msgType = keywordMap.getMsgType();
				if ("news".equals(msgType)) {
					responseDocument = WxXMLHelper.createNewsDocument(originUser, toUserName, keywordMap.getTitle(),
							keywordMap.getDescription(), keywordMap.getPicUrl(), keywordMap.getUrl());
				} else if ("text".equals(msgType)) {
					String response = "hello i am a cute response -_- ";
					responseDocument = WxXMLHelper.createTextDocument(originUser, toUserName, response);
				}
			}
		}
		String result = "";
		if (!NullUtil.isNull(responseDocument)) {
			result = responseDocument.asXML();
		}
		return result;
	}
}
