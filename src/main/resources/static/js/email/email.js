//# sourceURL=email.js
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
	$("#getEmail").submit();
	$("#querySubmit").unbind("click").click(function() {
		resetPageInfo();
	})
	var obj = {
		state : function(data) {
			if (data.enable) {
				return "启用中";
			} else {
				return "禁用中";
			}
		},
		operate : function(data) {
			var enableBtn = "";
			if (data.enable) {
				enableBtn = "<input type='button' class='table_btn_orange toggleEnableEmail' data-url='toggleEnableEmail?enable=false&id="
						+ data.id + "' value='禁用'/>&nbsp;&nbsp;&nbsp;&nbsp;";
			} else {
				enableBtn = "<input type='button' class='table_btn_orange toggleEnableEmail' data-url='toggleEnableEmail?enable=true&id="
						+ data.id + "' value='启用'/>&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			return enableBtn
					+ "<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdateEmail?id="
					+ data.id
					+ "' value='编辑'/>&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "<input type='button'  class='deleteEmail table_btn_red' data-url='deleteEmail?id="
					+ data.id + "' value='删除'/>"

		}
	}
	setTableDisplay(obj);
	$("#refreshQuery").unbind("click").click(function() {
		$("#getEmail").submit();
	})
	function toggleEnableEmail()
		{
			var url = $(this).data("url");
			$.get(url, function(data) {
				layer.alert(data.message, function(index) {
					layer.close(index);
					$("#getEmail").submit();
				});
			})
	}
	/**
	 * 未来元素先移除事件
	 */
	$(document).off("click", ".toggleEnableEmail");
	$(document).on("click", ".toggleEnableEmail",function(){
		console.log("1");
		var url = $(this).data("url");
		$.get(url, function(data) {
			layer.alert(data.message, function(index) {
				layer.close(index);
				$("#getEmail").submit();
			});
		})
	});
	$(document).off("click", ".deleteEmail");
	$(document).on("click", ".deleteEmail", function() {
		var deleteBtn = $(this);
		layer.confirm('确认删除该email', {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function(index) {
			var url = deleteBtn.data("url");
			$.post(url, function(data) {
				layer.msg('成功删除邮件地址', {
					icon : 1
				});
				$("#mainContent").html(data);
			})
		}, function(index) {
			layer.close(index);
		});
	});
	$("#sendEmailBtn").unbind("click").click(function() {
		$.get("isSendEmailRunning", function(data) {
			if (data.code != 0) {
				layer.alert(data.message);
			} else {
				layer.open({
					type : 2,
					title : '设置邮件发送选项',
					shadeClose : true,
					scrollbar : false,
					shade : 0.2,
					fix : false, // 不固定
					area : [ '400px', '500px' ],
					content : 'sendEmail',
				});
			}
		})

	});
})
