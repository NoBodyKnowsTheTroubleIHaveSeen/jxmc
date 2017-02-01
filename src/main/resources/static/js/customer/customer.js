//# sourceURL=customer.js
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
	$("#customerRecordForm").submit();
	$("#querySubmit").unbind("click").click(function() {
		resetPageInfo();
	})

	var obj = {
		name : function(data) {
			if (isNull(data.name)) {
				return "<a href='javascript:void(0)' class='getDetailBtn' data-id='"
						+ data.id + "' id='name_" + data.id + "'>无</a>";
			}
			return "<a href='javascript:void(0)' class='getDetailBtn' data-id='"
					+ data.id
					+ "' id='name_"
					+ data.id
					+ "'>"
					+ data.name
					+ "</a>";
		},
		consumeLevel : function(data) {
			return "<a href='javascript:void(0)' class='updateConsumeLevelBtn' data-id='"
					+ data.id + "'>" + data.consumeLevel + "</a>";
		},
		isAdd : function(data) {
			return "<a href='javascript:void(0)' class='isAddBtn' data-id='"
					+ data.id + "'>" + data.isAdd + "</a>";
		},
		qqIsWeiXin : function(data) {
			if (isNull(data.qqIsWeiXin)) {
				return "<a href='javascript:void(0)' class='qqIsWeiXinBtn' data-id='"
						+ data.id
						+ "' data-val='true'>是</a>/"
						+ "<a href='javascript:void(0)' class='qqIsWeiXinBtn' data-id='"
						+ data.id + "' data-val='false'>否</a>";
			}
			return "<a href='javascript:void(0)' class='qqIsWeiXinBtn' data-id='"
					+ data.id
					+ "' data-val="
					+ !data.qqIsWeiXin_code
					+ ">"
					+ data.qqIsWeiXin + "</a>";
		},
		operate : function(data) {
			return "<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdateCustomer?id="
					+ data.id
					+ "' value='编辑'>&nbsp;&nbsp;&nbsp;&nbsp;</input><input type='button'  class='deleteCustomer table_btn_red' data-url='deleteCustomer?id="
					+ data.id + "' value='删除'></input>"
		}
	}
	setTableDisplay(obj);
	/**
	 * 删除客户
	 */
	$(document).off("click", ".deleteCustomer");
	$(document).on("click", ".deleteCustomer", function() {
		var deleteBtn = $(this);
		layer.confirm('确认删除该用户', {
			btn : [ '确定', '取消' ]
		}, function(index) {
			var url = deleteBtn.data("url");
			$.post(url, function(data) {
				layer.msg('成功删除用户', {
					icon : 1
				});
				$("#mainContent").html(data);
			})
		}, function(index) {
			layer.close(index);
		});
	});
	$("#city").citySelect({
		required : false
	});
	$("#clearForm").unbind("click").click(function() {
		$(this).parents("form")[0].reset();
	})
	/**
	 * 显示或隐藏查询按钮
	 */
	$("#hideOrShowQuery").unbind("click").click(
			function() {
				if ($(".queryDiv").css("display") == "none") {
					$(".queryDiv").slideDown();
					$(this).children("img").attr("src",
							"images/ic_arrow_drop_up_grey600_36dp.png");
				} else {
					$(".queryDiv").slideUp();
					$(this).children("img").attr("src",
							"images/ic_arrow_drop_down_grey600_36dp.png");
				}
			})
	$("#hideOrShowQuery").click();
	/**
	 * 修改客户添加状态
	 */
	$(document).off("click", ".isAddBtn");
	$(document)
			.on(
					"click",
					".isAddBtn",
					function() {
						var isAddBtn = $(this);
						var id = isAddBtn.data("id");
						layer
								.open({
									type : 2,
									title : '设置添加信息',
									shadeClose : false,
									shade : 0.2,
									area : [ '320px', '280px' ],
									content : encodeURI("getIframeWithId?controllerName=customer&pageName=updateAddStatus&params={id:'"
											+ id + "'}"),
									end : function() {
										$("#customerRecordForm").submit();
									}
								});
						layer.close(index);
					});
	/**
	 * 查看客户具体信息
	 */
	$(document).off("click", ".getDetailBtn");
	$(document).on("click", ".getDetailBtn", function() {
		var getDetailiBtn = $(this);
		var id = getDetailiBtn.data("id");
		layer.open({
			type : 2,
			title : '查看客户信息',
			shadeClose : false,
			shade : 0.2,
			area : [ '600px', '300px' ],
			content : encodeURI("getCustomerDetail?id=" + id),
			end : function() {
				$("#customerRecordForm").submit();
			}
		});
	});
	/**
	 * 更新客户消费等级
	 */
	$(document).off("click", ".updateConsumeLevelBtn");
	$(document)
			.on(
					"click",
					".updateConsumeLevelBtn",
					function() {
						var updateConsumeLevelBtn = $(this);
						var id = updateConsumeLevelBtn.data("id");
						layer
								.open({
									type : 2,
									title : '更新客户消费等级',
									shadeClose : false,
									shade : 0.2,
									area : [ '300px', '140px' ],
									content : encodeURI("getIframeWithId?controllerName=customer&pageName=updateConsumeLevel&params={id:'"
											+ id + "'}"),
									end : function() {
										$("#customerRecordForm").submit();
									}
								});
					});
	/**
	 * 更新qq是否为微信
	 */
	$(document).off("click", ".qqIsWeiXinBtn");
	$(document).on("click", ".qqIsWeiXinBtn", function() {
		var id = $(this).data("id");
		var val = $(this).data("val");
		var msg = "";
		if (false == val) {
			msg = "修改为qq号码(!=)不与微信号一致";
			val = false;
		} else {
			msg = "修改为qq号码(=)与微信号一致";
			val = true;
		}
		var param = {
			id : id,
			val : val
		}
		$.post("updateQqIsWeiXin", param, function(data) {
			if (val) {
				layer.msg(data.message, {
					time : 500
				}, function() {
					$("#name_" + id).click();
				});
			} else {
				layer.msg(data.message);
				$("#customerRecordForm").submit();
			}
		})

	});
});