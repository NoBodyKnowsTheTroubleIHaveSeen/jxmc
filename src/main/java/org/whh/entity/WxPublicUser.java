package org.whh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wx_public_user")
public class WxPublicUser extends EntityBase {
	/**
	 * 参数 描述 openid 用户的唯一标识 nickname 用户昵称 sex 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 * province 用户个人资料填写的省份 city 普通用户个人资料填写的城市 country 国家，如中国为CN headimgurl
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。
	 * 若用户更换头像，原有头像URL将失效。 privilege 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 * unionid 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
	private String openId;
	private String remark;// 备注
	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String privilege;
	private String headImgUrl;
	private String unionId;
	private String recommendOpenId;// 推荐用户opendId
	private String note;
	private Integer recommendCount;// 推荐用户的个数
	private Date subscribeTime;
	private String groupId;
	private String tagIdList;
	private Boolean subscribe;// 是否取消

	private Boolean isReward;// 是否已奖励

	private Date rewardTime;// 奖励时间

	private Date lastSendTime;// 最后发送推送时间

	private Long recommendMediaId;// 扫描素材关注

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(String tagIdList) {
		this.tagIdList = tagIdList;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Boolean getIsReward() {
		return isReward;
	}

	public void setIsReward(Boolean isReward) {
		this.isReward = isReward;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Long getRecommendMediaId() {
		return recommendMediaId;
	}

	public void setRecommendMediaId(Long recommendMediaId) {
		this.recommendMediaId = recommendMediaId;
	}

	public Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}

}
