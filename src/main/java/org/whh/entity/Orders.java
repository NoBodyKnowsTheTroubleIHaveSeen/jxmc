package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders extends EntityBase {

	
	private Long appInfoId;
	private String buyerInfo;// 买家信息
	private String orderId;// 订单ID
	private String sellerId;// 卖家id
	private String fPhone;// 分销商手机号
	private Integer isClose;// 订单是否可关闭 0（表示不可关闭）1（表示货到付款订单，可关闭）
	@Lob
	private String items;// 商品数组
	private String returnCode;// 退货单号
	private String status;// 订单状态
							// unpay（未付款）
							// pay（待发货）
							// unship_refunding（未发货，申请退款）
							// shiped_refunding（已发货，申请退款）
							// accept（已确认收货）
							// accept_refunding（已确认收货，申请退款）
							// finish（订单完成）
							// close（订单关闭）
	private String addTime;// 下单时间
	@Lob
	private String discountInfo;// 优惠折扣描述
	private String expressFeeNum;// 运费，和express_fee相同
	private String userPhone;// 买家手机号
	private String discount;// 优惠折扣
	private String price;// 商品总价格，不包含运费
	@Lob
	private String note;// 买家备注
	private String sellerPhone;// 卖家手机号
	@Lob
	private String status2;// 订单状态说明
	private String totalFee;// 分成推广佣金
	private String sellerName;// 卖家微店名称
	@Lob
	private String orderTypeDes;// 订单类型描述
	private Integer argueFlag;// 是否投诉
	private String fShopName;// 分销商店铺名称
	private String modifyPriceEnable;// 是否可以改价
										// 0（表示不可改价）
										// 1（表示可以改价）
	@Lob
	private String sk;// 推广信息
	private String fxFeeValue;// 分销商获取的分成金额
	private String expressNo;// 快递单号
	private String sendTime;// 发货时间
	private String quantity;// 商品总件数
	private String expressFee;// 快递费用
	private String fSellerId;// 分销商ID
	private String confirmExpire;// 担保交易过期时间
	private String orderType;// 订单类型编号，1为货到付款，2为直接交易，3为担保交易
	private String payTime;// 付款时间
	@Lob
	private String statusDesc;// 订单状态详细描述
	@Lob
	private String refundInfo;// 退款信息对象
	@Lob
	private String expressNote;// 订单备注（卖家）
	private String express;// 快递公司名称
	private String tradeNo;// 订单交易号
	private String discountAmount;// 优惠金额，discount_amount目前只适用于使用“店铺优惠券”的订单，其他优惠（限时折扣等）返回均为0
	private String originalTotalPrice;// 订单原价格，包括运费
										// （此字段用于记录订单被创建时的价格,包含运费，减去优惠后的价格）
	private String total;// 订单总价 包含运费
	private String expressType;// 快递公司编号

	
	
	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}

	public String getBuyerInfo() {
		return buyerInfo;
	}

	public void setBuyerInfo(String buyerInfo) {
		this.buyerInfo = buyerInfo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	public String getExpressFeeNum() {
		return expressFeeNum;
	}

	public void setExpressFeeNum(String expressFeeNum) {
		this.expressFeeNum = expressFeeNum;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getOrderTypeDes() {
		return orderTypeDes;
	}

	public void setOrderTypeDes(String orderTypeDes) {
		this.orderTypeDes = orderTypeDes;
	}

	public Integer getArgueFlag() {
		return argueFlag;
	}

	public void setArgueFlag(Integer argueFlag) {
		this.argueFlag = argueFlag;
	}

	public String getfShopName() {
		return fShopName;
	}

	public void setfShopName(String fShopName) {
		this.fShopName = fShopName;
	}

	public String getModifyPriceEnable() {
		return modifyPriceEnable;
	}

	public void setModifyPriceEnable(String modifyPriceEnable) {
		this.modifyPriceEnable = modifyPriceEnable;
	}

	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}

	public String getFxFeeValue() {
		return fxFeeValue;
	}

	public void setFxFeeValue(String fxFeeValue) {
		this.fxFeeValue = fxFeeValue;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(String expressFee) {
		this.expressFee = expressFee;
	}

	public String getfSellerId() {
		return fSellerId;
	}

	public void setfSellerId(String fSellerId) {
		this.fSellerId = fSellerId;
	}

	public String getConfirmExpire() {
		return confirmExpire;
	}

	public void setConfirmExpire(String confirmExpire) {
		this.confirmExpire = confirmExpire;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}

	public String getExpressNote() {
		return expressNote;
	}

	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getOriginalTotalPrice() {
		return originalTotalPrice;
	}

	public void setOriginalTotalPrice(String originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}
}
