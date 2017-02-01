//# sourceURL=showMaterial.js
$(function() {
	var obj = {
		thumb_url : function(data) {
			return "<img style='width:60px;height:60px' src='" + data.thumb_url
					+ "'></img>"
		},
		url : function(data) {
			return "<a class='content' click='showContent()'><div class='hideConent' style='display:none;width:900px;margin-left:-450px;left:50%;background:beige;top:100px;position: absolute'>"
					+ data.content + "</div>查看</a>";
		},
		isUsed : function(data) {
			if (data.isUsed == true) {
				return "<a data-url='/setWxUsed?isUsed=false&mediaId="
						+ data.mediaId + "' class='messageLoad'>是</a>";
			}
			return "<a data-url='/setWxUsed?isUsed=true&mediaId="
					+ data.mediaId + "' class='messageLoad'>否</a>";
		},
		keyword : function(data) {
			var result = "";
			if (data.keyword) {
				result = result + "关键词：" + data.keyword + "<br>";
			}
			if (data.inputCode) {
				result = result + "回复码:" + data.inputCode + "<br>";
			}
			if (data.action) {
				result = result + "动作编号:" + data.action + "<br>";
			}
			return result;
		},
		operate : function(data) {
			var result = "<a href=http://qr.liantu.com/api.php?&w=500&text="
					+ encodeURIComponent(data.url)
					+ ">二维码</a>&nbsp;&nbsp;&nbsp;";
			result = result + "<form class='messageForm' data-next-page='/?subUrl=/showMaterail' action='/createSecene?mediaId=" + data.id
			+ "'><a class='messageLoad'>场景二维码</a></form>";
			if (data.materialStatus == 1) {
				return result + "<span style='color:red'>本期笑话</span>";
			} else if (data.materialStatus == 2) {
				return result + "<span style='color:red'>本期文章</span>";
			}
			return result
					+ "<a data-url='/setTodayJoke?mediaId="
					+ data.mediaId
					+ "' class='messageLoad'>设为本期笑话</a>&nbsp;&nbsp;&nbsp;<a data-url='/setCurrentContent?mediaId="
					+ data.mediaId + "' class='messageLoad'>设为本期文章</a>";
		},
		setKeyword : function(data) {
			var result = "<a class='setKeyword' data-id='" + data.id + "' ";
			if (data.keywords) {
				result = result + "data-keywords='" + data.keywords + "' ";
			}
			if (data.inputCode) {
				result = result + "data-input-code='" + data.inputCode + "' ";
			}
			if (data.action) {
				result = result + "data-action='" + data.action + "' ";
			}
			if (data.menuTitle) {
				result = result + "data-menu-title='" + data.menuTitle + "' ";
			}
			result = result + ">设置</a>";
			return result;
		},
		qrcode:function(data){
			return "<img style='width:200px;height:200px' src='https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + data.qrCodeInfoTicket + "'/>";
		}
	}
	setTableDisplay(obj);
	$(".ajaxTable").submit();
	$(document).off("click", ".content");
	$(document).on("click", ".content", function() {
		$(this).find("div").show();
	})
	$(document).off("mouseleave", ".hideConent");
	$(document).on("mouseleave", ".hideConent", function() {
		$(this).hide();
	})
	$(document).off("click", ".setKeyword");
	$(document).on(
			"click",
			".setKeyword",
			function() {
				var id = $(this).data("id");
				var menuTitle = $(this).data("menuTitle");
				var inputCode = $(this).data("inputCode");
				var action = $(this).data("action");
				var keywords = $(this).data("keywords");
				var param = {
					id : id,
					menuTitle : menuTitle,
					inputCode : inputCode,
					action : action,
					keywords : keywords
				};
				generateSetForm("setKeywordForm", "/setKeyword", "配置", param, [
						"作为菜单时的标题", "回复码", "回复关键词", "动作编号" ], [ "menuTitle",
						"inputCode", "keywords", "action" ], function() {
					$(".ajaxTable").submit();
				});
			})
})