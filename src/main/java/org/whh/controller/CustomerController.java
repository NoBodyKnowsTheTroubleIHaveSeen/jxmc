package org.whh.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.constant.ContactType;
import org.whh.dao.CustomerDao;
import org.whh.dao.PartnerDao;
import org.whh.entity.Code;
import org.whh.entity.Customer;
import org.whh.entity.Partner;
import org.whh.service.CustomerService;
import org.whh.web.CommonException;
import org.whh.web.CommonMessage;

@Controller
public class CustomerController extends ControllerBase {
	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	CustomerDao customerDao;
	@Autowired
	PartnerDao partnerDao;
	@Autowired
	CustomerService customerService;

	/**
	 * code转换
	 */
	private void getCode(Model model) {
		List<Code> consumeLevel = codeDao.findByCodeGroup("consumeLevel");
		List<Code> isRecommend = codeDao.findByCodeGroup("isRecommend");
		List<Code> trueFalse = codeDao.findByCodeGroup("trueFalse");
		model.addAttribute("consumeLevel", consumeLevel);
		model.addAttribute("isRecommend", isRecommend);
		model.addAttribute("trueFalse", trueFalse);
	}

	/**
	 * 客户首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/customer")
	public String index(Model model) {
		return "customer/customer";
	}

	/**
	 * 获得客户列表
	 * 
	 * @param customer
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCustomers")
	public Page<Customer> getCustomers(Customer customer, Boolean isOrderByAdd, Pageable pageable, Date startTime,
			Date endTime) {
		PageRequest request = null;
		if (isOrderByAdd) {
			request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
					new Sort(Direction.ASC, new String[] { "qqIsWeiXin", "isAdd", "createTime" }));
		} else {
			request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
		}
		Page<Customer> result = customerService.findAllByPage(customer, startTime, endTime, request);
		return result;
	}

	/**
	 * 获得客户详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCustomerDetail")
	public String getCustomerDetail(Long id, Model model) {
		Customer customer = customerDao.findOne(id);
		model.addAttribute("customer", customer);
		return "customer/customerDetail";
	}

	@RequestMapping("/saveOrUpdateCustomer")
	public String saveOrUpdateCustomer(Long id, Model model) {
		getCode(model);
		if (!isNull(id)) {
			Customer customer = customerDao.findOne(id);
			model.addAttribute("customer", customer);
		} else {
			model.addAttribute("customer", new Customer());
		}
		return "customer/saveOrUpdateCustomer";
	}

	/**
	 * 保存或更新客户信息
	 * 
	 * @param customer
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping("/doSaveOrUpdateCustomer")
	public String doSaveOrUpdateCustomer(Customer customer) throws CommonException {
		try {
			customerDao.save(customer);
		} catch (Exception e) {
			logger.error("保存customer失败", e);
			throw new CommonException("保存用户信息失败");
		}
		return "customer/customer";
	}

	/**
	 * 删除客户信息
	 * 
	 * @param id
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping("/deleteCustomer")
	public String deleteCustomer(Long id) throws CommonException {
		if (isNull(id)) {
			logger.error("非法请求，删除的用户id为空");
			throw new CommonException("非法请求!");
		}
		customerDao.delete(id);
		return "customer/customer";
	}

	@RequestMapping("/updateAddStatus")
	public String updateAddStatus(Long id, Model model) {
		model.addAttribute("id", id);
		return "customer/updateAddStatus";
	}

	/**
	 * 更新客户添加状态
	 * 
	 * @param id
	 * @param addType
	 * @param number
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doUpdateAddStatus")
	public CommonMessage doUpdateAddStatus(Long id, Integer addType, String number, String addBy) {
		Customer customer = customerDao.findOne(id);
		String addTypeStr = customer.getAddType();
		switch (addType) {
		case ContactType.WEI_XIN:
			if (!isNull(number)) {
				customer.setWeiXin(number);
			}
			if (isNull(addTypeStr)) {
				customer.setAddType("微信");
			} else if (!addTypeStr.contains("微信")) {
				customer.setAddType(customer.getAddType() + ";微信");
			}
			break;
		case ContactType.QQ:
			if (!isNull(number)) {
				customer.setQq(number);
			}
			if (isNull(addTypeStr)) {
				customer.setAddType("qq");
			} else if (!addTypeStr.contains("qq")) {
				customer.setAddType(customer.getAddType() + ";qq");
			}
			break;
		case ContactType.PHONE:
			if (!isNull(number)) {
				customer.setPhone(number);
			}
			if (isNull(addTypeStr)) {
				customer.setAddType("手机");
			} else if (!addTypeStr.contains("手机")) {
				customer.setAddType(customer.getAddType() + ";手机");
			}
			break;

		}
		customer.setIsAdd(true);
		customer.setAddBy(addBy);
		customerDao.save(customer);
		CommonMessage message = new CommonMessage("设置用户添加状态成功");
		return message;
	}

	@ResponseBody
	@RequestMapping("/doUpdteConsumeLevel")
	public CommonMessage doUpdteConsumeLevel(Long id, Integer consumeLevel) {
		Customer customer = customerDao.findOne(id);
		customer.setConsumeLevel(consumeLevel);
		customerDao.save(customer);
		CommonMessage message = new CommonMessage("修改消费等级成功");
		return message;
	}

	@ResponseBody
	@RequestMapping("/getContactInfo")
	public String getContactInfo(Long id, Integer contactType) {
		Customer customer = customerDao.findOne(id);
		switch (contactType) {
		case ContactType.PHONE:
			return customer.getPhone();
		case ContactType.WEI_XIN:
			return customer.getWeiXin();
		case ContactType.QQ:
			return customer.getQq();
		}
		return "";
	}

	@ResponseBody
	@RequestMapping("/updateToPartner")
	public CommonMessage updateToPartner(Long id) {
		Customer customer = customerDao.findOne(id);
		Partner partner = new Partner();
		partner.setWeiXin(customer.getWeiXin());
		partner.setQq(customer.getQq());
		partner.setPhone(customer.getPhone());
		partner.setProvince(customer.getProvince());
		partner.setCity(customer.getCity());
		partner.setAge(customer.getAge());
		partner.setSex(customer.getSex());
		partner.setCreateTime(new Date());
		partner.setDeductPercentage(10D);
		partner.setEmail(customer.getEmail());
		partner.setPartnerLevel(1);
		partner.setTotalSell(0D);
		partner.setCustomerId(id);
		partnerDao.save(partner);
		customer.setIsPartner(true);
		customerDao.save(customer);
		CommonMessage message = new CommonMessage("成功升级为合作者");
		return message;
	}

	@ResponseBody
	@RequestMapping("/restoreToCustomer")
	@Transactional
	public CommonMessage restoreToCustomer(Long id) {
		Customer customer = customerDao.findOne(id);
		customer.setIsPartner(false);
		customerDao.save(customer);
		Partner partner = partnerDao.findByCustomerId(id);
		partnerDao.delete(partner);
		CommonMessage message = new CommonMessage("取消合作，恢复成客户身份");
		return message;
	}

	@ResponseBody
	@RequestMapping("/updateQqIsWeiXin")
	public CommonMessage updateQqIsWeiXin(Long id, Boolean val) {
		Customer customer = customerDao.findOne(id);
		if (val) {
			customer.setQqIsWeiXin(true);
			if (!isNull(customer.getQq())) {
				customer.setWeiXin(customer.getQq());
			}
		} else {
			customer.setQqIsWeiXin(false);
			if (!isNull(customer.getWeiXin()) && customer.getWeiXin().equals(customer.getQq())) {
				customer.setWeiXin(null);
			}
		}

		customerDao.save(customer);
		CommonMessage message = new CommonMessage("更新成功");
		return message;
	}

	@ResponseBody
	@RequestMapping("/updateCustomerName")
	public CommonMessage updateCustomerName(Long id, String name) {
		Customer customer = customerDao.findOne(id);
		customer.setName(name);
		customerDao.save(customer);
		CommonMessage message = new CommonMessage("更新用户名成功");
		return message;
	}
}