$(function() {
	var search = window.location.search;
	var params = search.split("?subUrl=");
	if (params[1] != undefined) {
		var url = params[1];
		var target = "#mainContent";
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ]
		});
		$.get(url, function(data, status, jqXHR) {
			layer.close(index);
			$(target).html(data);
		});
	} else {
		$(".main").click();
	}
})
