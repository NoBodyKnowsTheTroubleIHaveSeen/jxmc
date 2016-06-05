package org.whh.wd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whh.dao.OrdersDao;
import org.whh.entity.Orders;
import org.whh.wd.vo.StatusResult;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class OrderHelper extends WdInterfaceBase {

	@Autowired
	OrdersDao orderDao;

	public void syncOrders(Long appInfoId, Date startTime, Date endTime) {
		syncOrders(appInfoId, startTime, endTime, 1);
	}

	/**
	 * 获取订单列表
	 * 
	 * @param appInfoId
	 * @return
	 */
	public void syncOrders(Long appInfoId, Date startTime, Date endTime, Integer pageNum) {
		int defaultPageSize = 200;
		JSONObject params = new JSONObject();
		params.put("page_num", pageNum);
		params.put("page_size", defaultPageSize);
		String param = params.toString();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", param));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.order.list.get", "1.1")));
		String response = post(appInfoId, pairs);
		JSONObject obj = JSONObject.parseObject(response);
		JSONObject result = obj.getJSONObject("result");
		JSONArray orders = result.getJSONArray("orders");
		List<String> orderIds = new ArrayList<String>();
		for (Object order : orders) {
			JSONObject o = (JSONObject) order;
			String orderId = o.getString("order_id");
			orderIds.add(orderId);
		}
		for (String orderId : orderIds) {
			syncOrder(appInfoId, orderId);
		}
		Integer total = result.getInteger("total_num");
		if (total > defaultPageSize) {
			int size = total % defaultPageSize;
			int totalPage;
			if (size == 0) {
				totalPage = total / defaultPageSize;
			} else {
				totalPage = total / defaultPageSize + 1;
			}
			if (pageNum < totalPage) {
				syncOrders(appInfoId, startTime, endTime, pageNum + 1);
			}
		}
	}

	/**
	 * 获取订单详情
	 */
	public void syncOrder(Long appInfoId, String orderId) {
		JSONObject params = new JSONObject();
		params.put("order_id", orderId);
		String param = params.toString();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", param));
		pairs.add(new BasicNameValuePair("public", getPublicParam(appInfoId, "vdian.order.get")));
		String response = post(appInfoId, pairs);
		JSONObject object = JSONObject.parseObject(response);
		JSONObject result = object.getJSONObject("result");
		Orders order = orderDao.getByOrderId(orderId);
		if (order == null) {
			order = new Orders();
			order.setOrderId(orderId);
			order.setCreateTime(new Date());
		}
		order.setAppInfoId(appInfoId);
		order.setAddTime(result.getString("add_time"));
		order.setArgueFlag(result.getInteger("argue_flag"));
		order.setBuyerInfo(result.getString("buyer_info"));
		order.setConfirmExpire(result.getString("confirm_expire"));
		order.setDiscount(result.getString("discount"));
		order.setDiscountAmount(result.getString("discount_amount"));
		order.setDiscountInfo(result.getString("discount_info"));
		order.setExpress(result.getString("express"));
		order.setExpressFee(result.getString("express_fee"));
		order.setExpressFeeNum(result.getString("express_free_num"));
		order.setExpressNo(result.getString("express_no"));
		order.setExpressNote(result.getString("express_note"));
		order.setExpressType(result.getString("express_type"));
		order.setfPhone(result.getString("f_phone"));
		order.setfSellerId(result.getString("f_seller_id"));
		order.setfShopName(result.getString("f_shop_name"));
		order.setFxFeeValue(result.getString("fx_free_value"));
		order.setIsClose(result.getInteger("is_close"));
		order.setItems(result.getString("items"));
		order.setModifyPriceEnable(result.getString("modify_price_enable"));
		order.setNote(result.getString("note"));
		order.setOrderType(result.getString("order_type"));
		order.setOrderTypeDes(result.getString("order_type_des"));
		order.setOriginalTotalPrice(result.getString("original_total_price"));
		order.setPayTime(result.getString("pay_time"));
		order.setPrice(result.getString("price"));
		order.setQuantity(result.getString("quantity"));
		order.setRefundInfo(result.getString("refund_info"));
		order.setReturnCode(result.getString("return_code"));
		order.setSellerId(result.getString("seller_id"));
		order.setSellerName(result.getString("seller_name"));
		order.setSellerPhone(result.getString("seller_phone"));
		order.setSendTime(result.getString("send_time"));
		order.setSk(result.getString("sk"));
		order.setStatus(result.getString("status"));
		order.setStatus2(result.getString("status2"));
		order.setStatusDesc(result.getString("status_desc"));
		order.setTotal(result.getString("total"));
		order.setTotalFee(result.getString("total_free"));
		order.setTradeNo(result.getString("trade_no"));
		order.setUpdateTime(new Date());
		order.setUserPhone(result.getString("user_phone"));
		orderDao.save(order);
	}

	/**
	 * 发货或更新物流信息
	 * 
	 * @param appInfoId
	 * @param orderId
	 * @param expressType
	 * @param expressNo
	 * @return
	 */
	public StatusResult udpateExpress(String orderId, String expressType, String expressNo) {
		Orders order = orderDao.getByOrderId(orderId);
		String updateExpressMethod = "";
		/**
		 * 是否已经填写发货信息
		 */
		if (order.getExpressNo() != null || order.getExpressType() != null) {
			updateExpressMethod = "vdian.order.express.modify";
		} else {
			updateExpressMethod = "vdian.order.deliver";
		}
		JSONObject params = new JSONObject();
		params.put("order_id", orderId);
		params.put("express_type", expressType);
		params.put("express_no", expressNo);
		String param = params.toString();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("param", param));
		pairs.add(new BasicNameValuePair("public", getPublicParam(order.getAppInfoId(), updateExpressMethod)));
		String response = post(order.getAppInfoId(), pairs);
		StatusResult statusResult = JSONObject.parseObject(response, StatusResult.class);
		if (statusResult.getStatus().getStatus_code() == 0) {
			order.setExpressType(expressType);
			order.setExpressNo(expressNo);
			orderDao.save(order);
		}
		return statusResult;
	}
}
