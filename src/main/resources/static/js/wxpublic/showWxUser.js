//# sourceURL=showWxUser.js
$(function() {
	var obj = {
		headImgUrl : function(data) {
			return "<img style='width:60px;height:60px' src='" + data.headImgUrl + "'></img>"
		},
		subscribe : function(data) {
			var subscribe = data.subscribe;
			if(subscribe)
			{
				return "是";
			}
			return "否";
		}
	}
	setTableDisplay(obj);
	$(".ajaxTable").submit();
})