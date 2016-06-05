package org.whh.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.whh.base.ObjectBase;
import org.whh.dao.CustomerDao;
import org.whh.entity.Customer;
import org.whh.service.CustomerService;

@Component
public class SendEmailThread extends ObjectBase implements Runnable
{
	private Logger logger = LoggerFactory.getLogger(SendEmailThread.class);
	@Autowired
	CustomerDao customerDao;
	@Autowired
	CustomerService customerService;
	
	public static boolean manualStop = false;
	public static boolean isRunning = false;
	public static Integer totalNumber = 0;
	public static Integer executed = 0;
	private Integer sendFrenquent;
	private Date stopTime;
	private Page<Customer> page;
	
	public SendEmailThread()
	{
	}
	public Integer getSendFrenquent()
	{
		return sendFrenquent;
	}
	public void setSendFrenquent(Integer sendFrenquent)
	{
		this.sendFrenquent = sendFrenquent;
	}
	public Date getStopTime()
	{
		return stopTime;
	}
	public void setStopTime(Date stopTime)
	{
		this.stopTime = stopTime;
	}
	public Page<Customer> getPage()
	{
		return page;
	}
	public void setPage(Page<Customer> page)
	{
		this.page = page;
	}
	@Override
	public void run()
	{
		isRunning = true;
		executed = 0;
		if (isNull(sendFrenquent))
		{
			sendFrenquent = 60;
		}
		List<Customer> customers = page.getContent();
		totalNumber = customers.size();
		for (int i = 0 ; i < customers.size();i++)
		{
			try
			{
				Thread.sleep(1000* sendFrenquent);
				executed = i;
				if (!isNull(stopTime)&&new Date().after(stopTime))
				{
					logger.debug("已到发送邮箱终止时间，发送邮箱线程停止");
					isRunning = false;
					return;
				}
				if (manualStop)
				{
					logger.debug("人工终止发送邮件");
					isRunning = false;
					return;
				}
				boolean isSend = false;
//				boolean isSend = EmailHelper.sendEmail(customer.getQq() + "@qq.com");
				if (isSend)
				{
					customers.get(i).setIsRecommend(true);
					customerDao.save(customers.get(i));
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}catch(Exception e)
			{
				logger.error("发送邮件发生异常",e);
			}
		}
		logger.debug("完成发送所有邮件");
		isRunning = false;
	}}
