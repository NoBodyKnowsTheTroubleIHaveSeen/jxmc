package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "email")
public class Email extends EntityBase
{
	private String sender;
	private String password;
	private String serverAddress;
	private Integer serverPort;
	private Boolean enable;
	public String getSender()
	{
		return sender;
	}
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getServerAddress()
	{
		return serverAddress;
	}
	public void setServerAddress(String serverAddress)
	{
		this.serverAddress = serverAddress;
	}
	public Integer getServerPort()
	{
		return serverPort;
	}
	public void setServerPort(Integer serverPort)
	{
		this.serverPort = serverPort;
	}
	public Boolean getEnable()
	{
		return enable;
	}
	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
