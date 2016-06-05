package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "partner")
public class Partner extends EntityBase
{
	//姓名
	private String name;
	// 省
	private String province;
	// 市
	private String city;
	// 电话
	private String phone;
	// 微信
	private String weiXin;
	// qq
	private String qq;
	// 邮件
	private String email;
	// 合作级别
	private Integer partnerLevel;
	// 提成百分比
	private Double deductPercentage;
	// 备注
	private String memo;
	// 总销售金额
	private Double totalSell;
	// 标签
	private String tag;
	// 年龄
	private Integer age;
	// 性别
	private String sex;
	// 客户id
	private Long customerId;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getWeiXin()
	{
		return weiXin;
	}

	public void setWeiXin(String weiXin)
	{
		this.weiXin = weiXin;
	}

	public String getQq()
	{
		return qq;
	}

	public void setQq(String qq)
	{
		this.qq = qq;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Integer getPartnerLevel()
	{
		return partnerLevel;
	}

	public void setPartnerLevel(Integer partnerLevel)
	{
		this.partnerLevel = partnerLevel;
	}

	public Double getDeductPercentage()
	{
		return deductPercentage;
	}

	public void setDeductPercentage(Double deductPercentage)
	{
		this.deductPercentage = deductPercentage;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public Double getTotalSell()
	{
		return totalSell;
	}

	public void setTotalSell(Double totalSell)
	{
		this.totalSell = totalSell;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public Long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}

}
