package org.whh.entity;

/**
 * javax.persistence包是Java Persistence Api的规范简称JPA
 */
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * model类需要添加注解 entity和对应的表 id注解指示对应的id
 * 
 * @author acer
 *
 */
@Entity
@Table(name = "users")
public class User extends EntityBase
{
	private String name;

	private String password;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
