package org.whh.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whh.base.ControllerBase;
import org.whh.dao.OrdersDao;
import org.whh.entity.Orders;
import org.whh.service.OrdersService;
import org.whh.util.CalendarUtil;
import org.whh.wd.OrderHelper;
import org.whh.wd.ProductMangeHelper;
import org.whh.wd.vo.StatusResult;
import org.whh.web.CommonMessage;

@Controller
public class OrderController extends ControllerBase {

	@Autowired
	ProductMangeHelper helper;

	@Autowired
	OrdersDao ordersDao;

	@Autowired
	OrdersService ordersService;

	@Autowired
	OrderHelper orderHelper;

	@RequestMapping("/order")
	public String index() {
		return "order/order";
	}

	@RequestMapping("/getOrders")
	@ResponseBody
	public Page<Orders> getOrders(String nameOrPhone, String status, Pageable pageable, Date startTime, Date endTime) {
		PageRequest request = null;
		request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
				new Sort(Direction.DESC, new String[] { "payTime" }));

		Page<Orders> orders = ordersService.findAllByPage(nameOrPhone, status, CalendarUtil.formateDate(startTime),
				CalendarUtil.formateDate(endTime), request);
		return orders;
	}

	@ResponseBody
	@RequestMapping("/doUpdateExpress")
	public CommonMessage doUpdateExpress(String orderId, String expressNo, String expressType) {
		StatusResult result = orderHelper.udpateExpress(orderId, expressType, expressNo);
		CommonMessage message = new CommonMessage();
		if (result.getStatus().getStatus_code() == 0) {
			message.setMessage("更新快递信息成功");
		} else {
			message.setMessage("更新快递信息失败：" + result.getStatus().getStatus_reason());
		}
		return message;
	}

}
