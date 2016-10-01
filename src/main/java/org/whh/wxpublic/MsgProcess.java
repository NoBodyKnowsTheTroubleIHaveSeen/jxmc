package org.whh.wxpublic;

import org.dom4j.Document;

public interface MsgProcess {
	/**
	 * 处理微信推送的消息
	 * @param msg
	 * @return
	 */
	String process(String toUserName,String originUser,String createTime,Document document);

	String process(String toUserName, String originUser, String createTime, String content);

}