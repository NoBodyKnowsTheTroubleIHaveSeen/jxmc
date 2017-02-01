package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 发送消息到qq群的相关信息
 * 
 * @author ASUS-PC
 *
 */
@Entity
@Table(name = "qq_send_user_info", indexes = { @Index(name = "i_uin_group_uin", columnList = "uin,groupUin") })
public class QqSendUserInfo extends EntityBase {

	private String groupUin;// 群id

	private String uin;// 发送的用户id

	private String content;// 发送的内容

	public String getGroupUin() {
		return groupUin;
	}

	public void setGroupUin(String groupUin) {
		this.groupUin = groupUin;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
