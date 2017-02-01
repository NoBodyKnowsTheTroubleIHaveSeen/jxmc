package org.whh.wxpublic;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WxXMLHelper {

	/**
	 * 创建图文回复文档
	 * 
	 * @param originUserName
	 * @param toUserName
	 * @param title
	 * @param description
	 * @param picUrl
	 * @param url
	 * @return
	 */
	public static Document createNewsDocument(String originUserName, String toUserName, String title,
			String description, String picUrl, String url) {
		Document document = createDcoument(originUserName, toUserName);
		Element msgTypeEle = DocumentHelper.createElement("MsgType");
		msgTypeEle.addCDATA("news");
		Element articalCount = DocumentHelper.createElement("ArticleCount");
		articalCount.setText(1 + "");
		Element articles = DocumentHelper.createElement("Articles");
		Element item = DocumentHelper.createElement("item");
		Element titleEle = DocumentHelper.createElement("Title");
		Element descriptionEle = null;
		Element picUrlEle = DocumentHelper.createElement("PicUrl");
		Element urlEle = DocumentHelper.createElement("Url");

		titleEle.addCDATA(title);
		picUrlEle.addCDATA(picUrl);
		urlEle.addCDATA(url);

		item.add(titleEle);
		item.add(picUrlEle);
		item.add(urlEle);

		if (description != null) {
			descriptionEle = DocumentHelper.createElement("Description");
			descriptionEle.addCDATA(description);
			item.add(descriptionEle);
		}
		articles.add(item);
		Element root = document.getRootElement();
		root.add(msgTypeEle);
		root.add(articalCount);
		root.add(articles);

		return document;
	}

	/**
	 * 创建文本回复文档
	 * 
	 * @param originalUserName
	 * @param toUserName
	 * @param createTime
	 * @param content
	 * @return
	 */
	public static Document createTextDocument(String originalUserName, String toUserName, String content) {
		Document document = createDcoument(originalUserName, toUserName);
		Element msgTypeEle = DocumentHelper.createElement("MsgType");
		msgTypeEle.addCDATA("text");
		Element contentEle = DocumentHelper.createElement("Content");
		contentEle.addCDATA(content);
		Element root = document.getRootElement();
		root.add(msgTypeEle);
		root.add(contentEle);

		return document;
	}
	/**
	 * 创建图片回复文档
	 * @param originalUserName
	 * @param toUserName
	 * @param meidaId
	 * @return
	 */
	public static Document createImageDocument(String originalUserName, String toUserName, String meidaId) {
		Document document = createDcoument(originalUserName, toUserName);
		Element msgTypeEle = DocumentHelper.createElement("MsgType");
		msgTypeEle.addCDATA("image");
		Element imageItem = DocumentHelper.createElement("Image");
		Element mediaItem = DocumentHelper.createElement("MediaId");
		imageItem.add(mediaItem);
		mediaItem.addCDATA(meidaId);
		Element root = document.getRootElement();
		root.add(msgTypeEle);
		root.add(imageItem);
		return document;
	}
	/**
	 * 创建客服回复
	 * @param originalUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static Document createKfDocument(String originalUserName, String toUserName, String content) {
		Document document = createDcoument(originalUserName, toUserName);
		Element msgTypeEle = DocumentHelper.createElement("MsgType");
		msgTypeEle.addCDATA("transfer_customer_service");
		Element contentEle = DocumentHelper.createElement("Content");
		contentEle.addCDATA(content);
		Element root = document.getRootElement();
		root.add(msgTypeEle);
		root.add(contentEle);

		return document;
	}

	/**
	 * 创建回复文档基础信息
	 * 
	 * @param orginalUserName
	 * @param toUserName
	 * @param createTime
	 * @return
	 */
	public static Document createDcoument(String orginalUserName, String toUserName) {
		Long createTime = System.currentTimeMillis();
		Element root = DocumentHelper.createElement("xml");
		Document document = DocumentHelper.createDocument(root);
		Element toUserNameEle = DocumentHelper.createElement("ToUserName");
		toUserNameEle.addCDATA(orginalUserName);

		Element fromUserNameEle = DocumentHelper.createElement("FromUserName");
		fromUserNameEle.addCDATA(toUserName);

		Element createTimeEle = DocumentHelper.createElement("CreateTime");
		createTimeEle.setText(createTime.toString());

		root.add(toUserNameEle);
		root.add(fromUserNameEle);
		root.add(createTimeEle);
		return document;
	}
}
