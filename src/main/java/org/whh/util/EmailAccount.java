package org.whh.util;

public class EmailAccount
{

	//发送邮件地址
	private String senderEmail;
	//密码
	private String password;
	//smtp端口
	private String smtpServer;
	//smtp端口
	private int smtpPort;
	public String getSenderEmail()
	{
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail)
	{
		this.senderEmail = senderEmail;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getSmtpServer()
	{
		return smtpServer;
	}
	public void setSmtpServer(String smtpServer)
	{
		this.smtpServer = smtpServer;
	}
	public int getSmtpPort()
	{
		return smtpPort;
	}
	public void setSmtpPort(int smtpPort)
	{
		this.smtpPort = smtpPort;
	}
}
