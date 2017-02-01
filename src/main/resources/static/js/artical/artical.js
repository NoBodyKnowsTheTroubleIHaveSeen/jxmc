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
	var title = $("#title").val();
	var chapter = $("#chapter").val();
	var chapterSize = $("#chapterSize").val();
	$(".chapter").text(getByNum(chapter));
	if (chapter == 1) {
		$(".preChapter").css("display", "none");
	} else {
		var preChapter = chapter - 1;
		$(".preChapter").attr("href",
				"/common/artical?chapter=" + preChapter + "&title=" + title);
	}
	var nextChapter = chapter - 0 + 1;
	if (nextChapter > chapterSize) {
		nextChapter = chapterSize;
		$(".nextChapter").css("display", "none");
	} else {
		$(".nextChapter").attr("href",
				"/common/artical?chapter=" + nextChapter + "&title=" + title);
	}
	$(".firstChpater").attr("href",
			"/common/artical?chapter=" + 1 + "&title=" + title);
	$(".lastChapter").attr("href",
			"/common/artical?chapter=" + chapterSize + "&title=" + title);
	for (var i = 1; i <= chapterSize; i++) {
		var chapterText = getByNum(i);
		var li = '<li><a href="/common/artical?chapter=' + i
				+ '&title=' + title + '">第' + chapterText + '章</a></li>';
		if( i %10 == 0)
		{
			li = li + "<li class='divider'></li>"
		}
		$("#chapters").append(li);
	}
})