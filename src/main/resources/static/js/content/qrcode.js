//# sourceURL=qrcode.js
$(function() {
	$(".ajaxTable").submit();

	var obj = {
		qrcode : function(data) {
			return "<img style='width:200px;height:200px' src='https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
					+ data.ticket + "'/>";
		}
	}
	setTableDisplay(obj);
})