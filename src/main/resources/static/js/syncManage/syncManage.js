//# sourceURL=syncManage.js
$(function() {
	$("#getWdAppInfos").submit();
	var obj = {
		operate : function(data) {
			var isSrc = data.isSrc;
			var tmp = "toAppInfoId";
			if(isSrc == "是")
			{
				tmp = "srcAppInfoId";
			}
			return "<form class='messageForm' onsubmit='messageFormSubmit($(this))'  action='/doSyncManage' style='display: inline'><input name='"+tmp+"' type='hidden' value='"
					+ data.id
					+ "'></input><input type='hidden' name='isSyncAll' value='false'></input><input type='submit'  class='table_btn_red' value='同步'></input></form>"
					+ "&nbsp;&nbsp;&nbsp;<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdateWdAppInfo?id="
					+ data.id + "' value='编辑'>";
		}
	}
	setTableDisplay(obj);
})
