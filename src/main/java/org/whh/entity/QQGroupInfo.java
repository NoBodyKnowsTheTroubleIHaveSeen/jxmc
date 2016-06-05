package org.whh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "qq_group_info")
public class QQGroupInfo extends EntityBase
{
	/**
	 * groupCode: 群号码 groupName: 群名称 groupMemo: 群简介 groupMemberNum: 群成员
	 * groupMaxMemberNum: 群最大容量 groupOwnerNumber: 群主qq号码 lables: 群标签 province: 省
	 * city: 市 geo : 具体位置，机构等信息 //该字段多数群的信息为空 gcate： 所属行业 cityid: 城市id
	 */
	private Long groupCode;

	private String groupName;
	
	@Column(length=1000)
	private String groupMemo;

	private Long groupMemberNum;

	private Long groupMaxMemberNum;

	private Long groupOwnerNumber;
	// 多个群属相标签
	private String labels;
	// Qaddr第一个元素
	private String province;
	// Qaddr第二个元素
	private String city;

	private String geo;
	// 多所属行业的标签
	private String gcate;

	private Long cityid;

	private Integer level;

	private String backUpString;

	public Long getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(Long groupCode)
	{
		this.groupCode = groupCode;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	@Basic
	@Column(name = "group_memo", length = 1000)
	public String getGroupMemo()
	{
		return groupMemo;
	}

	public void setGroupMemo(String groupMemo)
	{
		this.groupMemo = groupMemo;
	}

	public Long getGroupMemberNum()
	{
		return groupMemberNum;
	}

	public void setGroupMemberNum(Long groupMemberNum)
	{
		this.groupMemberNum = groupMemberNum;
	}

	public Long getGroupMaxMemberNum()
	{
		return groupMaxMemberNum;
	}

	public void setGroupMaxMemberNum(Long groupMaxMemberNum)
	{
		this.groupMaxMemberNum = groupMaxMemberNum;
	}

	public Long getGroupOwnerNumber()
	{
		return groupOwnerNumber;
	}

	public void setGroupOwnerNumber(Long groupOwnerNumber)
	{
		this.groupOwnerNumber = groupOwnerNumber;
	}

	public String getLabels()
	{
		return labels;
	}

	public void setLabels(String labels)
	{
		this.labels = labels;
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

	public String getGeo()
	{
		return geo;
	}

	public void setGeo(String geo)
	{
		this.geo = geo;
	}

	public String getGcate()
	{
		return gcate;
	}

	public void setGcate(String gcate)
	{
		this.gcate = gcate;
	}

	public Long getCityid()
	{
		return cityid;
	}

	public void setCityid(Long cityid)
	{
		this.cityid = cityid;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer carLevel)
	{
		this.level = carLevel;
	}

	public String getBackUpString()
	{
		return backUpString;
	}

	public void setBackUpString(String backUpString)
	{
		this.backUpString = backUpString;
	}

}
