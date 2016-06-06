package org.whh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wd_app_info")
public class WdAppInfo extends EntityBase {
	private String appKey;
	private String secret;
	private String userName;
	private String mobile;
	private String memo;
	private String appName;
	private String accessToken;// token
	private Date lastFreshTime;// 最后刷新时间
	private Long expire;// 过期时间

	// 是否为源店铺标志位
	private Boolean isSrc;
	private Long srcWdAppInfoId;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getLastFreshTime() {
		return lastFreshTime;
	}

	public void setLastFreshTime(Date lastFreshTime) {
		this.lastFreshTime = lastFreshTime;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public Long getSrcWdAppInfoId() {
		return srcWdAppInfoId;
	}

	public void setSrcWdAppInfoId(Long srcWdAppInfoId) {
		this.srcWdAppInfoId = srcWdAppInfoId;
	}

	public Boolean getIsSrc() {
		return isSrc;
	}

	public void setIsSrc(Boolean isSrc) {
		this.isSrc = isSrc;
	}

}
