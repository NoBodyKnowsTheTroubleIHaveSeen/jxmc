//# sourceURL=login.js
$(function() {

	$(".name").focus();
	$(document).keydown(function(event) {
		// 回车事件
		if (event.keyCode == 13) {
			$(".login__submit").click();
		}
	});
	var animating = false;

	function ripple(elem, e) {
		$(".ripple").remove();
		var elTop = elem.offset().top, elLeft = elem.offset().left, x = e.pageX
				- elLeft, y = e.pageY - elTop;
		var $ripple = $("<div class='ripple'></div>");
		$ripple.css({
			top : y,
			left : x
		});
		elem.append($ripple);
	}

	$(document).on(
			"click",
			".login__submit",
			function(e) {
				if (animating)
					return;
				animating = true;
				ripple($(this), e);
				var that = $(this);
				$(this).addClass("processing");
				$.post($("#loginForm").attr("action"), $("#loginForm")
						.serialize(), function(data) {
					that.removeClass("processing");
					animating = false;
					if (data.code == 0) {
						window.location = "/";
					} else {
						layer.msg(data.message);
					}

				});
			});
	$("#registerA").click(function() {
		layer.open({
			type : 2,
			title : '用户注册',
			shadeClose : false,
			shade : 0.2,
			area : [ '300px', '200px' ],
			content : "register"
		});
		return false;
	})
})
