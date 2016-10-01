$(function() {

	$(document).ajaxComplete(function(event, jqxhr, settings) {
		if (jqxhr.getResponseHeader("isLoginPage")) {
			$("#mainContent").html("");
			layer.msg("登陆超时，请重新登陆", function() {
				window.location = "/login";
			});
		}
	});

	$(document).ajaxError(
			function(event, jqxhr, settings, thrownError) {
				if (jqxhr.getResponseHeader("isLoginPage")) {
					layer.msg("登陆超时，请重新登陆", function() {
						window.location = "/login";
					});
				}
				var data = $.parseJSON(jqxhr.responseText);
				if (notNull(data.customException)
						&& data.customException == true
						&& notNull(data.message)) {
					layer.msg(data.message, function() {
					});
				} else {
					if (jqxhr.status == 404) {
						layer.alert("状态码：" + data.status + "<br>error:"
								+ data.error + "<br>message:" + data.message
								+ "<br>path:" + data.path);
					} else {
						layer.msg("系统错误！", function() {
						});
						setTimeout(function() {
							layer.closeAll();
						}, 3000)
					}
				}
			})
	/**
	 * 全局发送ajax请求加载到主界面或(id为mainContent的元素中)
	 */
	$(document).on("click", ".ajaxLoad", function() {
		var url = $(this).data("url");
		var target = $(this).attr("target");
		if (target == null || target == undefined) {
			target = "#mainContent";
		}
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ]
		});
		$.get(url, function(data, status, jqXHR) {
			layer.close(index);
			$(target).html(data);
		});
		return false;
	})
	/**
	 * 全局ajax提交表单,返回结果加载到主界面
	 */
	$(document).on("submit", ".ajaxSubmit", function() {
		var action = $(this).attr("action");
		var formData = $(this).serialize();
		var target = $(this).attr("target");
		if (target == null || target == undefined) {
			target = "#mainContent";
		}
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ]
		});
		$.post(action, formData, function(data) {
			layer.close(index);
			$(target).html(data);
		})
		return false;
	})
	$(document).on("click", ".messageLoad", function() {
		var url = $(this).data("url");
		var form = $(this).parents("form");
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ]
		});
		$.get(url, function(data) {
			layer.close(index);
			layer.alert(data.message);
			form.submit();
		})
	})
	$(document).on("submit", ".messageForm", function() {
		var action = $(this).attr("action");
		var formData = $(this).serialize();
		var form = $(this);
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ]
		});
		$.post(action, formData, function(data) {
			layer.close(index);
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
		return false;
	})
	$(document).on("input", ".ajaxTable :input", function() {
		resetPageInfo();
		$(this).parents(".ajaxTable").submit();
	})
	$(document)
			.on(
					"submit",
					".ajaxTable",
					function() {
						var form = $(this);
						form.find("#tableContent").html("");
						var action = $(this).attr("action");
						var formData = $(this).serialize();
						var thLength = $(this).find(".title").length;
						var headIds = new Array();
						var hiddenIds = new Object();
						var dataTypeIds = new Object();
						var codeIds = new Object();
						var classIds = new Object();
						/**
						 * 延迟加载表格时间，默认为0
						 */
						var delayTime = 0;
						for (var i = 1; i <= thLength; i++) {
							var child = "th.title:nth-child(" + i + ")";
							var th = form.find(child);
							var id = th.attr("id");
							var hidden = th.attr("hidden");
							if (hidden == "hidden") {
								hiddenIds[id] = true;
							}
							headIds.push(id);
							var type = th.data("type");
							if (notNull(type)) {
								dataTypeIds[id] = type;
							}
							var code = th.data("code");
							if (notNull(code)) {
								/**
								 * 设置延迟加载时间为200
								 */
								delayTime = 200;
								loadCode(code);
								codeIds[id] = code;
							}
							var cls = th.data("class");
							if (notNull(cls)) {
								classIds[id] = cls;
							}
						}
						/**
						 * 根据延迟时间决定是否延迟执行，延迟目的是等待code加载完毕
						 */
						setTimeout(
								function() {
									/* 加载块 */
									$
											.ajax({
												url : action,
												type : 'post',
												data : formData,
												dataType : 'json',
												/* timeout:15000, */
												beforeSend : function(
														XMLHttpRequest) {
													index = layer.load(0, {
														shade : false
													});
												},
												success : function(data,
														textStatus) {
													layer.close(index);
													/**
													 * 设置分页数据信息
													 */
													setPageInfo(form, data);
													/**
													 * 数据数组
													 */
													var datas = data.content;
													var table = "";
													// 遍历数组数据
													for ( var datasIndex in datas) {
														var data = datas[datasIndex];
														table = table + "<tr>";
														// 遍历表头
														for ( var headIndex in headIds) {
															var headId = headIds[headIndex];
															if (hiddenIds[headId] == true) {
																table = table
																		+ "<td style='display:none'";
															} else {
																table = table
																		+ "<td";
															}
															if (notNull(classIds[headId])) {
																table = table
																		+ " "
																		+ classIds[headId]
																		+ ">";
															} else {
																table = table
																		+ ">";
															}
															if (notNull(dataTypeIds[headId])) {
																var display = getByType(
																		dataTypeIds[headId],
																		data[headId]);
																table = table
																		+ display;
															} else if (codeIds[headId] != undefined) {
																data[headId
																		+ "_code"] = data[headId];
																var display = getByCode(
																		codeIds[headId],
																		data[headId]);
																data[headId] = display;
																if (isFunc(window["tableInfo"][headId])) {
																	display = window["tableInfo"][headId]
																			(data);
																}
																table = table
																		+ display;
															} else if (isFunc(window["tableInfo"][headId])) {
																var display = window["tableInfo"][headId]
																		(data);
																table = table
																		+ display;
															} else {
																if (data[headId] != null
																		&& data[headId] != undefined) {
																	table = table
																			+ data[headId];
																} else {
																	// table =
																	// table +
																	// "-";
																}
															}
															table = table
																	+ "</td>";
														}
														table = table + "</tr>";
													}
													form.find("#tableContent")
															.html(table);

												},
												complete : function(
														XMLHttpRequest,
														textStatus) {
													layer.close(index);
												},
												error : function(
														XMLHttpRequest,
														textStatus, errorThrown) {
													layer.close(index);
												}
											});
								}, delayTime);
						return false;
					})
	function setPageInfo(form, data) {
		var page = parseInt(data.number);
		var pageSize = data.size;
		var total = data.totalElements;
		var totalPages = data.totalPages;
		var first = data.first;
		var last = data.last;
		if (notNull(page)) {
			form.find(".page").val(page);
			form.find(".change_page").val(page + 1);
		}
		if (notNull(pageSize)) {
			form.find(".size").val(pageSize);
		}
		if (notNull(total)) {
			form.find(".total").text(total);
		}
		if (notNull(totalPages)) {
			form.find(".totalPages").text(totalPages);
		}
		$(".first").val(data.first);
		$(".last").val(data.last);
	}
	/**
	 * 全局li切换
	 */
	$.jqtab = function(tabtit, tab_conbox, shijian) {
		$(tab_conbox).find("li").hide();
		$(tabtit).find("li:first").addClass("thistab").show();
		$(tab_conbox).find("li:first").show();
		$(tabtit).find("li").bind(shijian, function() {
			$(tab_conbox).find("input:radio:checked").attr("checked", false);
			$(this).addClass("thistab").siblings("li").removeClass("thistab");
			var activeindex = $(tabtit).find("li").index(this);
			$(tab_conbox).children().eq(activeindex).show().siblings().hide();
			return false;
		});
	};
	/**
	 * 重新设置form表单值
	 * 
	 * @param target
	 *            form选择器
	 * @values 要设值的json对象
	 */
	var resetForm = function(target, values) {
		var $form = $(target);
		if (!$form)
			return;
		$form[0].reset();
		$(target + " input").each(function() {
			var $input = $(this);
			var name = $input.attr('name');
			if (name)
				$input.val(values[name]);
		});
	}
	/**
	 * 初始化表格信息对象
	 */
	window["tableInfo"] = new Object();
	/**
	 * 初始化code信息
	 */
	window["codeInfo"] = new Object();

	$(document).on("click", ".resubmitForm", function() {
		$(this).parents(".ajaxTable").submit();
	})
	$(document).on("click", ".ajaxTableLoad", function() {
		$(".ajaxTable").submit();
	})
})
/**
 * 设置表格显示内容
 * 
 * @param obj
 */
function setTableDisplay(obj) {
	/**
	 * 重置tableInfo对象
	 */
	window["tableInfo"] = new Object();
	for ( var o in obj) {
		window["tableInfo"][o] = obj[o];
	}
}
/**
 * 判断传入对象是否为方法
 * 
 * @param f
 * @returns {Boolean}
 */
function isFunc(f) {
	if (isNull(f)) {
		return false;
	}
	return typeof f == 'function';
}
/**
 * 加载code
 * 
 * @param codeGroup
 * @returns
 */
function loadCode(codeGroup) {
	if (notNull(window["codeInfo"][codeGroup])) {
		return window["codeInfo"][codeGroup];
	} else {
		var param = "codeGroup=" + codeGroup;
		return $.get("getCode", param, function(data) {
			window["codeInfo"][codeGroup] = data;
			return window["codeInfo"][codeGroup];
		});
	}
}
/**
 * 通过code获得显示值
 * 
 * @param codeGroup
 * @param codeKey
 * @returns
 */
function getByCode(codeGroup, codeKey) {
	if (isNull(codeKey)) {
		return "";
	}
	var codes = window["codeInfo"][codeGroup];
	for ( var index in codes) {
		if (codes[index]["codeKey"] == codeKey + "") {
			if (notNull(codes[index]["codeValue"])) {
				return codes[index]["codeValue"];
			} else {
				return codeKey;
			}
		}
	}
	return codeKey;
}
function getByType(type, data) {
	if (isNull(data)) {
		return "";
	}
	if (type = "date") {
		return data.replace("T", " ");
	}
}
function isNull(data) {
	if (data == undefined || data == null || data === "") {
		return true;
	}
	return false
}
function notNull(data) {
	return !isNull(data);
}
function getOffsetDate(isGetTime, day, hour, minute) {
	var offset = 0;
	if (notNull(day)) {
		offset = offset + day * 60 * 60 * 1000 * 24;
	}
	if (notNull(hour)) {
		offset = offset + hour * 60 * 60 * 1000;
	}
	if (notNull(hour)) {
		offset = offset + minute * 60 * 1000;
	}
	var curDate = new Date();
	var date = new Date(curDate.getTime() + offset);
	return getDate(isGetTime, date);
}
function getDateTime() {
	getDate(true);
}
/**
 * 
 * @param isGetTime
 *            是否获取时分秒
 * @param dateParam
 *            日期参数
 * @returns {String}
 */
function getDate(isGetTime, dateParam) {
	var date = dateParam;
	if (isNull(date)) {
		date = new Date();
	}
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	T15: 29
	if (hours >= 0 && hours <= 9) {
		hours = "0" + hours;
	}
	if (minutes >= 0 && minutes <= 9) {
		minutes = "0" + minutes;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if (isGetTime == true) {
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate + " " + hours + seperator2 + minutes + seperator2
				+ seconds;
	} else {
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate;
	}
	return currentdate;
}
function getTodayStartTime() {
	var today = new Date();
	today.setHours(0);
	today.setMinutes(0);
	today.setSeconds(0);
	today.setMilliseconds(0);
	return today;
}

function messageFormSubmit(form,callback) {
	event.preventDefault();
	var action = form.attr("action");
	var formData = form.serialize();
	var index = layer.load(0, {
		shade : [ 0.1, '#fff' ]
	});
	$.post(action, formData, function(data) {
		layer.close(index);
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
		if (isFunc(callback)) {
			callback();
		}
	})
	return false;
}
/**
 * 动态生成form 参数：表单Id,表单action,标题,参数,要显示字段的名称列表,字段上传的key,回调
 */
function generateSetForm(formId, action, title, param, names, keys, callback) {
	$("#" + formId).remove();
	var length = keys.length;
	var height = length * 50 + 120;
	var form = "<form id='"
			+ formId
			+ "' action='"
			+ action
			+ "' style='position: absolute;top: 50%;left: 50%;background:#77c3e8;text-aligin:center; width:400px;height:"
			+ height + "px; margin-top:-" + height / 2
			+ "px;margin-left:-200px;'>";
	form = form + "<br/><h4 style='text-align:center'>" + title + "</h4><br/>"
	form = form + "<table>"
	for ( var i in keys) {
		form = form + "<tr><td style='width: 150px;'>";
		form = form + names[i] + ":</td><td>";
		if(param[keys[i]] != undefined)
		{
			form = form + "<input name='" + keys[i]
			+ "' value='"+param[keys[i]]+"' style='width: 200px;'/></br>";
		}else
		{
			form = form + "<input name='" + keys[i]
			+ "' style='width: 200px;'/></br>";
		}
		form = form + "</td></tr>";
		delete param[keys[i]];
	}
	for ( var i in param) {
		form = form + "<input style='display:none' name='" + i + "' value='"
				+ param[i] + "'/>";
	}
	form = form
			+ "<tr><td></td><td><input type='submit' style='order-radius: 4px;"
			+ "background-color:#5bb75b;background-image:linear-gradient(to bottom, #62c462, #51a351);"
			+ "border: medium none;color: #fff;cursor: pointer;padding: 5px 15px;height: 31px;text-align:left;float:left'/>"
			+ "<input  style='order-radius: 4px;"
			+ "background-color:#5bb75b;background-image:linear-gradient(to bottom, #62c462, #51a351);"
			+ "border: medium none;color: #fff;cursor: pointer;padding: 5px 15px;height: 31px;text-align:left;float:right'"
			+ " class='cancelForm' type='button' value='取消'/></td></tr>"
	form = form + "</table>"
	form = form + "</form>";
	$("body").append(form);
	$(document).off("submit", "#" + formId);
	$(document).on("submit", "#" + formId, function() {
		messageFormSubmit($("#" + formId),callback);
		$("#" + formId).remove();
		return false;
	})
	$(document).off("click", ".cancelForm");
	$(document).on("click", ".cancelForm", function() {
		$(this).parents("form").remove();
	})
}