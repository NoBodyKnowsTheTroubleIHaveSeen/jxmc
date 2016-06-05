package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "code")
public class Code
{
	@Id
	private Long id;
	private String codeGroup;
	private String codeKey;
	private String codeValue;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCodeGroup()
	{
		return codeGroup;
	}

	public void setCodeGroup(String codeGroup)
	{
		this.codeGroup = codeGroup;
	}

	public String getCodeKey()
	{
		return codeKey;
	}

	public void setCodeKey(String codeKey)
	{
		this.codeKey = codeKey;
	}

	public String getCodeValue()
	{
		return codeValue;
	}

	public void setCodeValue(String codeValue)
	{
		this.codeValue = codeValue;
	}

}
