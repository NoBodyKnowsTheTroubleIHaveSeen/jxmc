package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_property")
public class SystemProperty
{
	@Id
	private Long id;
	//code版本号
	private Long codeVersion;
	//code删除版本号
	private Long codeDeleteVersion;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getCodeVersion()
	{
		return codeVersion;
	}
	public void setCodeVersion(Long codeVersion)
	{
		this.codeVersion = codeVersion;
	}
	public Long getCodeDeleteVersion()
	{
		return codeDeleteVersion;
	}
	public void setCodeDeleteVersion(Long codeDeleteVersion)
	{
		this.codeDeleteVersion = codeDeleteVersion;
	}
	
}
