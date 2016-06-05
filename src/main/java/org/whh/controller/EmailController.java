package org.whh.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.CustomerDao;
import org.whh.dao.EmailDao;
import org.whh.entity.Customer;
import org.whh.entity.Email;
import org.whh.service.CustomerService;
import org.whh.service.EmailService;
import org.whh.util.SendEmailThread;
import org.whh.web.CommonException;
import org.whh.web.CommonMessage;

@Controller
public class EmailController extends ControllerBase
{
	private Logger logger = LoggerFactory.getLogger(UserContorller.class);
	@Autowired
	CustomerDao customerDao;
	@Autowired
	EmailDao emailDao;
	@Autowired
	EmailService emailService;
	@Autowired
	SendEmailThread sendEmailThread;
	@Autowired 
	CustomerService customerService;
	
	@RequestMapping("/email")
	public String email()
	{
		return "email/email";
	}

	@RequestMapping("/saveOrUpdateEmail")
	public String saveOrUpdateEmail(Long id, Model model)
	{
		if (!isNull(id))
		{
			Email email = emailDao.findOne(id);
			model.addAttribute("email", email);
		} else
		{
			model.addAttribute("email", new Email());
		}
		return "email/saveOrUpdateEmail";
	}

	@RequestMapping("/doSaveOrUpdateEmail")
	public String doSaveOrUpdateEmail(Email email) throws CommonException
	{
		try
		{
			emailDao.save(email);
			return "email/email";
		} catch (Exception e)
		{
			logger.error("保存邮箱信息失败", e);
			throw new CommonException("保存邮箱信息失败");
		}

	}

	@RequestMapping("/deleteEmail")
	public String deleteEmail(Long id) throws CommonException
	{
		if (isNull(id))
		{
			logger.error("非法请求，删除的邮箱id为空");
			throw new CommonException("非法请求!");
		}
		emailDao.delete(id);
		return "email/email";
	}

	@ResponseBody
	@RequestMapping("/toggleEnableEmail")
	public CommonMessage toggleEnableEmail(Long id, Boolean enable)
	{
		Email email = emailDao.findOne(id);
		email.setEnable(enable);
		emailDao.save(email);
		String resultMsg = "";
		if (enable)
		{
			resultMsg = "email启用成功";
		} else
		{
			resultMsg = "email禁用成功";
		}
		CommonMessage message = new CommonMessage(resultMsg);
		return message;
	}

	@RequestMapping(value = "/getEmail", produces = "application/json")
	@ResponseBody
	public Page<Email> getEmail(Email email, Date startTime, Date endTime,
			Pageable pageable)
	{
		Page<Email> result = emailService.findAllByPage(email, startTime,
				endTime, pageable);
		return result;
	}

	@RequestMapping("/sendEmail")
	public String sendEmail()
	{
		return "email/sendEmail";
	}

	@RequestMapping("/doSendEmail")
	@ResponseBody
	public CommonMessage doSendEmail(Customer customer,Integer sendFrenquent, Integer totalSend,
			Date stopTime)
	{
		if (SendEmailThread.isRunning)
		{
			CommonMessage message = new CommonMessage("已经有邮件发送任务在执行了，请先停止后再重新启动");
			return message;
		}
		
		if (isNull(totalSend))
		{
			totalSend = 400;
		}
		Pageable pageable = new PageRequest(0, totalSend);
		Page<Customer> page = customerService.findAllByPage(customer, null, null, pageable);
		SendEmailThread.manualStop = false;
		sendEmailThread.setSendFrenquent(sendFrenquent);
		sendEmailThread.setStopTime(stopTime);
		sendEmailThread.setPage(page);
		Thread t = new Thread(sendEmailThread);
		t.start();
		long result = page.getTotalElements() < totalSend ? page.getTotalElements():totalSend;
		CommonMessage message = new CommonMessage("启动发送邮件成功，本次将发送"+result+"封邮件");
		return message;
	}
	@RequestMapping("/doStopSendEmail")
	@ResponseBody
	public CommonMessage doStopSendEmail()
	{
		CommonMessage message = new CommonMessage();
		if (!SendEmailThread.isRunning)
		{
			message.setMessage("邮件任务已处于停止状态");
		}else {
			SendEmailThread.manualStop = true;
			message.setMessage("已经下发停止命令，请等待...");
		}
		return message;
	}
	@RequestMapping("/isSendEmailRunning")
	@ResponseBody
	public CommonMessage isSendEmailRunning()
	{
		
		CommonMessage message = new CommonMessage();
		if (SendEmailThread.manualStop == true && SendEmailThread.isRunning)
		{
			message.setCode(-1);
			message.setMessage("任务正在停止中,，共有"+SendEmailThread.totalNumber+"条任务，已执行了"+SendEmailThread.executed+"条,请稍后再试...");
		}
		else if (SendEmailThread.isRunning)
		{
			message.setCode(-2);
			message.setMessage("任务正在执行中，共有"+SendEmailThread.totalNumber+"条任务，已执行了"+SendEmailThread.executed+"条");
		}else {
			message.setCode(0);
		}
		return message;
	}
}
