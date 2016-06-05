package org.whh.util;

public class EmailProperty
{
	private EmailAccount account;
	//收件地址
	private String toEmail;
	//主题
	private String subject;
	
	public String getToEmail()
	{
		return toEmail;
	}
	public void setToEmail(String toEmail)
	{
		this.toEmail = toEmail;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public EmailAccount getAccount()
	{
		return account;
	}
	public void setAccount(EmailAccount account)
	{
		this.account = account;
	}
}
