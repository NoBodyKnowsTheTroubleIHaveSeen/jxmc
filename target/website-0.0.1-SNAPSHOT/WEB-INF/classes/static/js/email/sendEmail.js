//# sourceURL=sendEmail.js
$(function() {
	
	$("#city").citySelect({
		required : false
	});
	
	
	$("#stopTime").datetimepicker({
		timeFormat : "HH:mm:ss",
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		showTime : true,
		showHour : true,
		showMinute : true,
		showMilliSecond : false,
		onSelect : function(d, i) {
			if (d !== i.lastVal) {
				$(this).trigger('input');
			}
		}
	});
	$(document).off("submit", ".messageForm");
	$(document).on("submit", ".messageForm", function() {
		var action = $(this).attr("action");
		var formData = $(this).serialize();
		var form = $(this);
		$.post(action, formData, function(data) {
			layer.alert(data.message, {
			}, function(index) {
				layer.close(index);
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭     
			});
		})
		return false;
	})
})