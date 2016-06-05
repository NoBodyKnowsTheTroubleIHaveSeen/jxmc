//# sourceURL=register.js
$(function() {
	$(".register_submit").click(
			function() {
				$.post($("#registerForm").attr("action"), $("#registerForm")
						.serialize(), function(data) {
					layer.msg(data.message);
					if (data.code == 0) {
						setTimeout(
								function() {
									var index = parent.layer
											.getFrameIndex(window.name);
									parent.layer.close(index);
								}, 2000)

					}
				});
			})
	$(".loginBtn").click(function() {
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	})
})
