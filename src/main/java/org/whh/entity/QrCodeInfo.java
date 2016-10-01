package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "qr_code_info")
public class QrCodeInfo extends EntityBase {

	public static final String ACTION_NAME_QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";
	public static final String ACTION_NAME_QR_LIMIT_SCENE = "QR_LIMIT_SCENE";

	private String actionName;// 生成二维码的类型，永久，临时等
	private String ticket;
	private String url;
	private String sceneStr;// 场景字符串

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSceneStr() {
		return sceneStr;
	}

	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}

}
