//# sourceURL=customer.js
$(function() {
	$("#randomCbox").unbind("click").click(function() {
		var isCheck = $("#randomCbox").is(':checked');
		if (isCheck) {
			$("#totalCount").val(30 + Math.floor(Math.random() * 50));
		}
	})
	$("#randomCbox").click();
});