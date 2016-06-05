$(function(){
	$("#linkUs").unbind("click").click(function(){
		layer.open({
		    type: 1,
		    skin: 'layui-layer-demo', //样式类名
		    closeBtn: 0, //不显示关闭按钮
		    shift: 2,
		    shadeClose: true, //开启遮罩关闭
		    content: '<div style="padding:20px;width:300px">qq:123456789<br>微信:987654321<br>联系电话：12345678901<br><br>恭喜发财！！！</div>'
		});
		return false;
	})
})
