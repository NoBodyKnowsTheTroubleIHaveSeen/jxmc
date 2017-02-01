package org.whh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 微信订阅者消息
 * 
 * @author ASUS-PC
 *
 */
@Entity
@Table(name = "wx_subscriber_message")
public class WxSubscriberMessage extends EntityBase {

	public static String TYPE_TEXT = "text";// 文本输入

	public static String TYPE_EVENT = "event";// 事件

	public static String TYPE_SUBSCRIBE = "subscribe";// 订阅

	public static String TYPE_SCAN = "scan";// 扫描

	public static String TYPE_CLICK = "click";// 点击菜单

	public static Integer SEND_TYPE_RECEIVE = 1;

	public static Integer SEND_TYPE_SEND = 2;

	private String fromUserName;
	private String toUserName;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext", nullable = true)
	private String content;
	private String msgType;// 消息类型
	private String description;
	private Integer sendType;// 接收/发送

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
}
