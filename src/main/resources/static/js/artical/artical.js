//# sourceURL=artical.js
var arr = [ "", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百" ];
function getByNum(num) {
	if (num <= 10) {
		return arr[num];
	} else if (num < 100) {
		var p1 = num % 10;
		var p2 = parseInt(num / 10);
		if (p2 == 1) {
			p2 = 0;
		}
		return arr[p2] + arr[10] + arr[p1];
	} else if (num <= 999) {
		var p1 = num % 10;
		var p2 = parseInt((num % 100) / 10);
		var p3 = parseInt(num / 100);
		var zero = "";
		if (p2 == 0 || p2 == 10) {
			p2 = 0;
			if (p1 != 0) {
				zero = "零"
			}
		}
		var end = "";
		if (p2 != 0) {
			end = "十";
		}
		return arr[p3] + arr[11] + arr[p2] + zero + end + arr[p1];
	}
}
$(function() {
	var isSubscriber = $("#isSubscriber").val();
	var ticket = $("#ticket").val();
	var title = $("#title").val();
	var chapter = $("#chapter").val();
	var chapterSize = $("#chapterSize").val();
	$(".chapter").text(getByNum(chapter));
	if (!isSubscriber) {
		var url = "/common/getArtical?ticket="+ ticket;
		$(".preChapter").css("display", "none");
		$(".nextChapter").attr("href", url);
		$(".firstChpater").attr("href",url);
		$(".lastChapter").attr("href", url);
		for (var i = 1; i <= chapterSize; i++) {
			var chapterText = getByNum(i);
			var li = '<li><a href="' + url + '">第' + chapterText + '章</a></li>';
			if (i % 10 == 0) {
				li = li + "<li class='divider'></li>"
			}
			$("#chapters").append(li);
		}
		return;
	}
	$(".firstChpater").attr("href",
			"/common/artical?isSubscriber=true&chapter=" + 1 + "&title=" + title + "&ticket="
					+ ticket);
	$(".lastChapter").attr("href",
			"/common/artical?isSubscriber=true&chapter=" + chapterSize + "&title=" + title+ "&ticket="
			+ ticket);
	for (var i = 1; i <= chapterSize; i++) {
		var chapterText = getByNum(i);
		var li = '<li><a href="/common/artical?isSubscriber=true&chapter=' + i + '&title='
				+ title+ "&ticket=" + ticket + '">第' + chapterText + '章</a></li>';
		if (i % 10 == 0) {
			li = li + "<li class='divider'></li>"
		}
		$("#chapters").append(li);
	}
	if (chapter == 1) {
		$(".preChapter").css("display", "none");
	} else {
		var preChapter = chapter - 1;
		$(".preChapter").attr("href",
				"/common/artical?isSubscriber=true&chapter=" + preChapter + "&title=" + title+ "&ticket=" + ticket);
	}
	var nextChapter = chapter - 0 + 1;
	if (nextChapter > chapterSize) {
		nextChapter = chapterSize;
		$(".nextChapter").css("display", "none");
	} else {
		$(".nextChapter").attr("href",
				"/common/artical?isSubscriber=true&chapter=" + nextChapter + "&title=" + title+ "&ticket=" + ticket);
	}
})