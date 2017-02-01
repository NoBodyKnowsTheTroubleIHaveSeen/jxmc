package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订阅用户会话信息
 * 
 * @author ASUS-PC
 *
 */
@Entity
@Table(name = "wx_subscriber_call")
public class WxSubscriberCall extends EntityBase {

	private String openId;

	private Long lastCallStratTime;// 最近一次会话开始时间

	private Integer status;// jqr.机器人(输入jqr,进入机器人模式)、114.接入客服(输入114,进入客服模式)

	public static final int STATUS_JQR = 1;

	public static final int STATUS_KF = 2;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getLastCallStratTime() {
		return lastCallStratTime;
	}

	public void setLastCallStratTime(Long lastCallStratTime) {
		this.lastCallStratTime = lastCallStratTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
