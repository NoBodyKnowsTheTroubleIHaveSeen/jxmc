//# sourceURL=showWxUser.js
$(function() {
	var obj = {
		headImgUrl : function(data) {
			return "<img style='width:60px;height:60px' src='"
					+ data.headImgUrl + "'></img>"
		},
		subscribe : function(data) {
			var subscribe = data.subscribe;
			if (subscribe) {
				return "是";
			}
			return "否";
		},
		info : function(data) {
			var result = "openId: &nbsp;&nbsp;" + data.openId;
			if (data.recommendOpenId) {
				result = result + "<br/>推荐人: &nbsp;&nbsp;"
						+ data.recommendOpenId;
			}
			if (data.recommendMediaId) {
				result = result
						+ "<br/>由素材关注:&nbsp;&nbsp;<a href='/getMaterialContent?id="
						+ data.recommendMediaId + "' target='_blank'>" + data.recommendMediaId
						+ "</a>";
			}

			return result;
		},
		recommendCount : function(data) {
			if (data.recommendCount) {
				if (data.recommendCount > 20) {
					return "<span style='color:red;font-size:16px'>"
							+ data.recommendCount + "</span>";
				}
			}
			return "";
		},
		area : function(data) {
			return data.country + " " + data.province + " " + data.city;
		},
		isReward : function(data) {
			if (data.isReward_code) {
				return "<a class='setReward' data-val='false' data-id='"
						+ data.id + "'>已奖励" + "</a>";
			} else {
				return "<a class='setReward' data-val='true' data-id='"
						+ data.id + "'>设为已奖励" + "</a>";
			}

		}

	}
	setTableDisplay(obj);
	$(".ajaxTable").submit();
	$("#clearForm").unbind("click").click(function() {
		$(this).parents("form")[0].reset();
	})
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

	$(document).off("click", ".setReward");
	$(document).on("click", ".setReward", function() {
		var id = $(this).data("id");
		var isReward = $(this).data("val");
		var param = {
			id : id,
			isReward : isReward
		};
		$.post("/setReward", param, function(data) {
			if (data) {
				layer.msg(data.message, {
					time : 500
				}, function() {
					$(".ajaxTable").submit();
				});
			} else {
				layer.msg(data.message);
				$(".ajaxTable").submit();
			}
		})
	})
})