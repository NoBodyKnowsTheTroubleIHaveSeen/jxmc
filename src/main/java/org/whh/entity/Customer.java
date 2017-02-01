package org.whh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends EntityBase {
	private String name;
	// 省
	private String province;
	// 市
	private String city;
	// 标签
	private String tag;
	// 消费等级
	private Integer consumeLevel;
	// 最后一次消费时间
	private Date lastConsumeTime;
	// 电话
	private String phone;
	// 微信
	private String weiXin;
	// qq
	private String qq;
	// 邮件
	private String email;
	// qq群号,当为群主时
	private Long qqGroupCode;
	// 是否已推荐过
	private Boolean isRecommend;
	// qq号码是否为微信号码
	private Boolean qqIsWeiXin;
	// 是否赠送了
	private Boolean isGive;
	// 是否已添加为好友
	private Boolean isAdd;
	// 添加的方式，如微信，qq,手机通信录，以分号隔开
	private String addType;
	// 备注信息
	private String memo;
	// 由谁添加
	private String addBy;
	// 是否为合作者
	private Boolean isPartner;
	// 性别
	private String sex;
	// 年龄
	private Integer age;
	// 状态
	private Integer status;

	public static final int STATUS_ADDING = 1;// 正在添加

	public static final int STATUS_ADDED = 2;// 已添加

	public static final int STATUS_NO_EXIST = 3;// 用户不存在

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getConsumeLevel() {
		return consumeLevel;
	}

	public void setConsumeLevel(Integer consumeLevel) {
		this.consumeLevel = consumeLevel;
	}

	public Date getLastConsumeTime() {
		return lastConsumeTime;
	}

	public void setLastConsumeTime(Date lastConsumeTime) {
		this.lastConsumeTime = lastConsumeTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getQqGroupCode() {
		return qqGroupCode;
	}

	public void setQqGroupCode(Long qqGroupCode) {
		this.qqGroupCode = qqGroupCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Boolean getQqIsWeiXin() {
		return qqIsWeiXin;
	}

	public void setQqIsWeiXin(Boolean qqIsWeiXin) {
		this.qqIsWeiXin = qqIsWeiXin;
	}

	public Boolean getIsGive() {
		return isGive;
	}

	public void setIsGive(Boolean isGive) {
		this.isGive = isGive;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public Boolean getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(Boolean isPartner) {
		this.isPartner = isPartner;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
