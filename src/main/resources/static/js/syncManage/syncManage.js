//# sourceURL=syncManage.js
$(function() {
	$("#getWdAppInfos").submit();
	var obj = {
		operate : function(data) {
			return "<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdateWdAppInfo?id="
					+ data.id
					+ "' value='编辑'>&nbsp;&nbsp;&nbsp;&nbsp;</input><input type='button'  class='deleteWdAppInfo table_btn_red' data-url='deleteWdAppInfo?id="
					+ data.id + "' value='删除'></input>"
		}
	}
	setTableDisplay(obj);
})
