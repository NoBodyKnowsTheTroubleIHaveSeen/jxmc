package org.whh.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.dao.CustomerDao;
import org.whh.entity.Customer;
import org.whh.util.NullUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信客户端自动化接口
 * 
 * @author ASUS-PC
 *
 */
@Controller
public class WeChatAutoController {

	@Autowired
	CustomerDao customerDao;

	private static Object getUserLock = new Object();

	private static Long lastAddTime = null;

	/**
	 *
	 * @param user
	 *            微信客户端用户
	 * @return
	 */
	@RequestMapping("/common/getNextWechatUser")
	@ResponseBody
	public String getNextWechatUser(String user) {
		synchronized (getUserLock) {
			Customer customer = customerDao.findUnknowUser();
			customer.setStatus(Customer.STATUS_ADDING);
			customer.setUpdateTime(new Date());
			customerDao.save(customer);
			return JSONObject.toJSONString(customer);
		}
	}

	/**
	 * 
	 * @param user
	 *            微信客户端用户
	 * @param customerId
	 *            用户id
	 * @param status
	 *            添加/发送状态
	 * @return
	 */
	@RequestMapping("/common/reportWxAddStatus")
	@ResponseBody
	public String reportAddStatus(String user, Long customerId, Integer status) {
		Customer customer = customerDao.findOne(customerId);
		if (!NullUtil.isNull(customer)) {
			customer.setAddBy(user);
			customer.setStatus(status);
			if (status == Customer.STATUS_NO_EXIST) {
				customer.setQqIsWeiXin(false);
			} else {
				customer.setWeiXin(customer.getQq());
				customer.setQqIsWeiXin(true);
			}
			customerDao.save(customer);
			lastAddTime = System.currentTimeMillis();
		}
		return "success,customer:" + customer + ",user:" + user + ",status:" + status + ",customerId:" + customerId;
	}

	@RequestMapping("/common/canAdd")
	@ResponseBody
	public String canAdd(String user) {
		if (lastAddTime == null || System.currentTimeMillis() - lastAddTime > 1000 * 60 * 5L) {
			return 1 + "";
		}
		return -1 + "";
	}
}
