package org.whh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;
@Component
public class EmailHelper
{

	private static List<EmailAccount> accounts = new ArrayList<EmailAccount>();
	
	private static int accountIndex = 0;
	
	public EmailHelper()
	{
		initEmailAccount();
	}
	
	public static boolean sendEmail(String destionationEmail)
	{
		EmailProperty property = new EmailProperty();
		property.setAccount(getAccount());
		property.setToEmail(destionationEmail);
		property.setSubject("互联网时代,茶香也怕巷子深");
		return EmailHelper.sendEmail(property);
	}
	/**
	 * 初始化邮箱账号信息
	 */
	private static void initEmailAccount()
	{
		EmailAccount account1 = new EmailAccount();
		account1.setSenderEmail("whhwkm@163.com");
		account1.setPassword("wy@password=987");
		account1.setSmtpServer("smtp.163.com");
		account1.setSmtpPort(587);
		
//		EmailAccount account2 = new EmailAccount();
//		account2.setSenderEmail("axjxmc@163.com");
//		account2.setPassword("password123");
//		account2.setSmtpServer("smtp.163.com");
//		account2.setSmtpPort(587);
		
		accounts.add(account1);
//		accounts.add(account2);
	}
	private static EmailAccount getAccount()
	{
		int accountSize = accounts.size();
		EmailAccount account = null;
		if (accountIndex  < accountSize - 1)
		{
			account = accounts.get(accountIndex);
			accountIndex++;
		}else {
			account = accounts.get(accountSize - 1);
			accountIndex = 0;
		}
		return account;
	}
	
	/**
	 * 获得邮件的html文本
	 * @return
	 */
	private static String getEmailHtmlText()
	{
		BufferedReader reader = null;
		try
		{
			FileInputStream fis = new FileInputStream(new File("emailTemplate.html"));
			reader = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String tmp = null;
			while ((tmp=reader.readLine())!=null)
			{
				buffer.append(tmp);
			}
			return buffer.toString();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				reader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	private static boolean sendEmail(EmailProperty property) {
		  
        // Create all the needed properties
        Properties connectionProperties = new Properties();
        // SMTP host 
        connectionProperties.put("mail.smtp.host", property.getAccount().getSmtpServer());
        // Is authentication enabled
        connectionProperties.put("mail.smtp.auth", "true");
        // Is StartTLS enabled
        connectionProperties.put("mail.smtp.starttls.enable", "true");
        // SSL Port
        connectionProperties.put("mail.smtp.socketFactory.port", property.getAccount().getSmtpServer());
        // SSL Socket Factory class
        connectionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // SMTP port, the same as SSL port :)
        connectionProperties.put("mail.smtp.port", property.getAccount().getSmtpPort());
         
        System.out.print(property.getToEmail()+",Creating the session...");
         
        // Create the session
        Session session = Session.getDefaultInstance(connectionProperties,
                new javax.mail.Authenticator() {    // Define the authenticator
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(property.getAccount().getSenderEmail(),property.getAccount().getPassword());
                    }
                });
         
        System.out.println("done!");
         
        // Create and send the message
        try {
            // Create the message
            Message message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress(property.getAccount().getSenderEmail()));
            // Set the recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(property.getToEmail()));
            // Set message subject
            message.setSubject(property.getSubject());
            // Set message text
            message.setContent(getEmailHtmlText(),"text/html;charset=utf-8" );
            System.out.print(property.getToEmail()+",Sending message...");
            // Send the message
            Transport.send(message);
            System.out.println("done!");
             
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
