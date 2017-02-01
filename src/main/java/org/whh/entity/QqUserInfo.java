package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "qq_user_info")
public class QqUserInfo extends EntityBase {

	private String uin;

	private String groupUin;

	private Boolean canSend;// 是否可以发送
	
	private String groupName;

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getGroupUin() {
		return groupUin;
	}

	public void setGroupUin(String groupUin) {
		this.groupUin = groupUin;
	}

	public Boolean getCanSend() {
		return canSend;
	}

	public void setCanSend(Boolean canSend) {
		this.canSend = canSend;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
