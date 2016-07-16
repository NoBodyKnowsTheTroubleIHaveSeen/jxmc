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
					+ "'></input><input type='hidden' name='isSyncAll' value='false'/><input type='submit'  class='table_btn_red' value='同步'/>	</form>"
					+ "&nbsp;&nbsp;&nbsp;<input type='button' class='ajaxLoad table_btn_blue' data-url='saveOrUpdateWdAppInfo?id="
					+ data.id + "' value='编辑'>";
		}
	}
	setTableDisplay(obj);
	
	$(".syncAll").submit(function(){
		var action = $(this).attr("action");
		var formData = $(this).serialize();
		var form = $(this);
		layer.confirm("确定同步产品到所有的网店?<br>同步一个网店仅需点击下方的某个网店并同步", {
			btn : [ '确定', '取消' ]
		}, function(index) {
			$.post(action, formData, function(data) {
				if (isNull(data.title)) {
					data.title = "提示信息";
				}
				/**
				 * 提示消息
				 */
				layer.alert(data.message, {
					title : data.title,
				}, function(index) {
					form[0].reset();
					var nextAction = form.data("nextAction");
					if (!isNull(nextAction)) {
						var target = form.data("target");
						if (isNull(target)) {
							target = "#mainContent";
						}
					}
					$.post(nextAction, function(data) {
						$(target).html(data);
					});
					layer.close(index);
				});
			})
		})
		return false;
	})
})
