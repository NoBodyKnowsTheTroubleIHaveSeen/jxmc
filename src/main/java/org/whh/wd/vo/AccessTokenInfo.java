package org.whh.wd.vo;

public class AccessTokenInfo {
	private String access_token;
	private Long expire_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpire_in() {
		return expire_in;
	}

	public void setExpire_in(Long expire_in) {
		this.expire_in = expire_in;
	}

}