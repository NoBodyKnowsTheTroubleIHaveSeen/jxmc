//# sourceURL=order.js
$(function() {
	$("#startTime").datepicker({
		changeMonth : true,
		changeYear : true,
		onSelect : function(d, i) {
			if (d !== i.lastVal) {
				$(this).trigger('input');
			}
		}
	});
	$("#endTime").datepicker({
		changeMonth : true,
		changeYear : true,
		onSelect : function(d, i) {
			if (d !== i.lastVal) {
				$(this).trigger('input');
			}
		}
	});
	$("#getOrders").submit();
	var obj = {
		sellerInfo : function(data) {
			var result = "姓名：" + data.sellerName + "<br>号码：" + data.sellerPhone;
			return result;
		},
		orderInfo : function(data) {
			var result = "订单号：" + data.orderId + "<br>";
			result = result + "<span style='color:red'>订单状态：" + data.statusDesc
					+ "</span><br>";
			result = result + "下单时间：" + data.addTime + "<br>";
			result = result + "订单类型：" + data.orderTypeDes + "<br>";
			result = result + "<span style='color:red'>付款时间：" + data.payTime + "</span><br>";
			return result;
		},
		expressInfo : function(data) {
			var result = "快递名称:" + data.express + "<br>";
			result = result + "快递单号：" + data.expressNo + "<br>";
			return "<a href='javascript:void(0)' class='updateExpress' data-id="
					+ data.orderId + ">" + result + "</a>";
		},
		priceInfo : function(data) {
			var result = "快递费用:" + data.expressFee + "<br>";
			result = result + "商品费用:" + data.price + "<br>";
			result = result + "订单总金额：" + data.total + "<br>";
			return result;
		},
		buyerInfo : function(data) {
			var buyerInfo = JSON.parse(data.buyerInfo);
			var result = "买家名称:" + buyerInfo.name + "<br>";
			result = result + "联系方式:" + buyerInfo.phone + "<br>";
			result = result + buyerInfo.province + buyerInfo.city
					+ buyerInfo.region + "<br>";
			result = result + buyerInfo.self_address + "<br>";
			return result;
		}

	}
	setTableDisplay(obj);

	/**
	 * 更新客户消费等级
	 */
	$(document).off("click", ".updateExpress");
	$(document)
			.on(
					"click",
					".updateExpress",
					function() {
						var updateExpressBtn = $(this);
						var orderId = updateExpressBtn.data("id");
						layer
								.open({
									type : 2,
									title : '设置发货信息',
									shadeClose : false,
									shade : 0.2,
									area : [ '300px', '180px' ],
									content : "getIframeWithId?controllerName=order&pageName=updateExpress&params={orderId:'"
											+ orderId + "'}",
									end : function() {
										$("#getOrders").submit();
									}
								});
					});
})
