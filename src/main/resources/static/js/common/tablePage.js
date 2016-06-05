//# sourceURL=tablePage.js
$(".firstPage").unbind("click").click(function() {
	var isFirst =  $(".first").val();
	if (isFirst == "false") {
		$(".page").val(0);
		var form = $(this).parents("form");
		form.submit();
	}
})
$(".previousPage").unbind("click").click(function() {
	var page = parseInt($(".page").val());
	if (page > 0) {
		$(".page").val(page - 1);
		var form = $(this).parents("form");
		form.submit();
	}
})
$(".nextPage").unbind("click").click(function() {
	var page = parseInt($(".page").val());
	var totalPage = parseInt($(".totalPages").text());
	if (page < totalPage - 1) {
		$(".page").val(page + 1);
		var form = $(this).parents("form");
		form.submit();
	}
})
$(".lastPage").unbind("click").click(function() {
	var isLast =  $(".last").val();
	var totalPage = parseInt($(".totalPages").text());
	if (isLast == "false") {
		$(".page").val(totalPage-1);
		var form = $(this).parents("form");
		form.submit();
	}
})
/*
 * 重置分页信息
 */
function resetPageInfo() {
	$(".page").val(0);
	$(".pageSize").val(10);
	$(".totalPages").text(0);
	$(".total").text(0);
	$(".pageSizeSpan").text(10);
	$(".change_page").val(1);
}
$(".change_page").on("input", function() {
	return false;
})
$(".change_page").blur(function() {
	var page = parseInt($(".page").val());
	var totalPage = parseInt($(".totalPages").text());
	var change_page = parseInt($(".change_page").val());
	if (isNull(change_page)) {
		$(".page").val(page);
		$(".change_page").val(page + 1);
		return false;
	} else {
		if(change_page - 1 == page)
		{
			return false;
		}
		if (change_page < 1) {
			$(".page").val(0);
			$(".change_page").val(1);
		} else if (change_page >= totalPage - 1) {
			$(".page").val(totalPage-1);
			$(".change_page").val(totalPage);
		} else {
			$(".page").val(change_page -1);
			$(".change_page").val(change_page);
		}
	}
	var form = $(this).parents("form");
	form.submit();
});