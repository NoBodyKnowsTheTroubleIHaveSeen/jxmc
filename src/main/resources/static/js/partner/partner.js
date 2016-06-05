//# sourceURL=partner.js
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
	$("#partnerRecordForm").submit();
	$("#querySubmit").unbind("click").click(function() {
		resetPageInfo();
	})
	var obj = {
		operate : function(data) {
			return "<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdatePartner?id="
					+ data.id
					+ "' value='编辑'>&nbsp;&nbsp;&nbsp;&nbsp;</input><input type='button'  class='deletePartner table_btn_red' data-url='deletePartner?id="
					+ data.id + "' value='删除'></input>"
		}
	}
	setTableDisplay(obj);
	$(document).off("click", ".deletePartner");
	$(document).on("click", ".deletePartner", function() {
		var deleteBtn = $(this);
		layer.confirm('确认删除该合作用户', {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function(index) {
			var url = deleteBtn.data("url");
			$.post(url, function(data) {
				layer.msg('成功删除合作用户', {
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
	$("#refreshQuery").unbind("click").click(function() {
		$("#partnerRecordForm").submit();
	})
	$("#hideOrShowQuery").unbind("click").click(function() {
		if ($(".queryDiv").css("display") == "none") {
			$(".queryDiv").slideDown();
			$(this).children("img").attr("src","images/ic_arrow_drop_up_grey600_36dp.png");
		} else {
			$(".queryDiv").slideUp();
			$(this).children("img").attr("src","images/ic_arrow_drop_down_grey600_36dp.png");
		}
	})
	

});