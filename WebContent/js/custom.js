//for edit or saving conditon
var userdata_tel = {};
var users = [];
var current_telno;
function inituserdata_tel() {
	var u = {};
	var t = {};

	t["data"] = {};
	t["counter"] = 2;// key for saved parameters
	t["status"] = "unlogin";
	t["userlistname"] = {};
	u["00000000"] = t;

	return u;
}

function getTelInfo(telno) {

}
var dataResearchedFlg=false;
function getSearchResult() {

	if (!islogin()) {
		$("#login_okbtn").unbind("click");
		$("#login_okbtn").on("click", function(event) {
			login(getSearchResult);
		});
		$('#popupLogin').popup('open');
		return;

	}
	var validator=validatconditonForm();
	if(validator.numberOfInvalids()>0){
		return;
	}
	
	var t = {};
	

	var conditions = $("[id$='param_con']");

	for (var i = 0; i < conditions.size(); i++) {

		var jqobj = $(conditions[i]);
		

		
		if (jqobj.hasClass("invisibleofdiv")) {
			continue;

		} else {

			var inputs = jqobj.find($("[name$='_condition_text']"));
			var temp = {};
			if(inputs.val()==""){
				continue;
			}
			temp[inputs.attr("id")] = inputs.val();
			t = $.extend(true, t, temp);
		}

	}
	if($.isEmptyObject(t)){
		return;
	}
	t["tel"] = current_telno;
	
	var myurl = window.location.href;
	var posturl;
	if (myurl.lastIndexOf("/", "http") != myurl.length - 1) {
		posturl = "deal/" + JSON.stringify(t);

	} else {
		posturl = JSON.stringify(t);

	}
	htmlobj = $.ajax({
		url : posturl,
		async : false
	});
	var str = htmlobj.responseText;

	$("#table-custom-2").table("refresh");
	$("#dataResult").html(str);
	$("#table-custom-2").table("refresh");
	if(str.indexOf("tr")>=0){
		$("#msgzone").html($("#resultmsg td").html());
		$("#resultmsg").remove();
	}
	
	dataResearchedFlg=true;

}

function islogin() {

	if (userdata_tel.status == "unlogin") {
		return false;
	}
	return true;
}

function login(callback) {
	if (!$("#loginform").valid()) {
		return false;
	}

	var findflg = false;
	$.each(users, function(index, val) {

		$.each(val, function(key, data) {
			if (key == $("#tel").val()) {

				data.status = "login";
				userdata_tel = data;
				current_telno = $("#tel").val();
				findflg = true;
				return;
			}
		});

		if (findflg)
			return;
	});

	if (!findflg) {
		
		var u = {};
		var t = inituserdata_tel();
		
		current_telno = $("#tel").val();
		
		t["00000000"].status = "login";
		u[$("#tel").val()] = t["00000000"];
		userdata_tel = u[$("#tel").val()];

		users.push(u);
	}

	setOptions();
	controlloginIcon();
	localStorage.users = JSON.stringify(users);
	$("#popupLogin").unbind("popupafterclose");
	
	$("#popupLogin").on("popupafterclose", function(event, ui) {

		callback();
		$("#login_okbtn").unbind("click");
		$("#login_okbtn").on("click", function(event) {
			login(donothing);
		});
	});
	
	$("#popupLogin").popup("close");

	return true;
}
function donothing() {

}

function logout() {

	$.each(users, function(index, val) {

		$.each(val, function(key, data) {
			data.status = "unlogin";
		});

	});
	current_telno = "00000000";
	userdata_tel = inituserdata_tel()["00000000"];
	setOptions("init");
	controlloginIcon();
	localStorage.users = JSON.stringify(users);

}

var systemlistname = {
	"0" : "参数设定",
	"1" : "强市参数(系统)",
	"2" : "弱市参数(系统)"
};

var systemlistname2 = {
	"0" : "自定义参数列表"

};

var systemdata = {
	"1" : {
		"dayk" : "5,8,80,1",
		"weekk" : "5,8,80,1",
		"onlykd" : "0,3,1,5,10,20",
		"onlykw" : "0,3,1,5,10,10",
		"zt" : "100,2",
		"currentk" : "1,1,5,-5,5",
		"duringk" : "0,30,25",
		"bankuai" : "00,30,60"
	},
	"2" : {
		"dayk" : "5,8,50,-1",
		"weekk" : "5,8,50,1",
		"onlykd" : "0,3,1,5,10,20",
		"onlykw" : "0,3,1,5,10,10",
		"zt" : "100,5",
		"currentk" : "1,-1,5,-5,5",
		"duringk" : "0,30,15",
		"bankuai" : "00,30,60"
	}
};

var userdata_temp = {};

function insertSlectBoxOption(id) {
	userdata_temp = userdata_tel.data;
	if (!$("#saveeditor_form").valid()) {
		return false;
	}
	;

	userdata_tel.counter++;

	var temp1 = {};
	$("[name$='_condition_text']").each(function(index, ele) {
		if(!$("#"+ele.id).parent().parent().parent().hasClass("invisibleofdiv")){
			var temp = {};
			temp[ele.id] = ele.value;
			temp1 = $.extend(true, temp1, temp);
		}
		

	});
	// temp1={dayk:"8,5,0,-1",wk:"9,5,6,1"}
	userdata_temp[userdata_tel.counter] = temp1;
	//userdata_temp={2:{dayk:"8,5,0,-1",wk:"9,5,6,1"}}
	jQuery(
			"<option value='" + userdata_tel.counter + "'>"
					+ $("#parametername").val() + "</option>").appendTo(
			"#" + id);
	jQuery(
			"<option value='" + userdata_tel.counter + "'>"
					+ $("#parametername").val() + "</option>").appendTo(
			"#customprams");

	// save data to json
	userdata_tel.data = userdata_temp;
	userdata_tel.userlistname[userdata_tel.counter] = $("#parametername").val();

	if (window.localStorage) {

		localStorage.users = JSON.stringify(users);

	}

	$("#popupSaveoredit").popup("close");
}

function openpopupforDeletePram() {
	var key = $("#customprams").val();
	if (key == 0) {
		return;
	} else {
		$("#popupforDeletePram").popup("open");
	}
}
function openeditsavePopup() {

	if (!islogin()) {
		$("#login_okbtn").unbind("click");
		$("#login_okbtn").on("click", function(event) {
			login(openeditsavePopup);
		});
		$('#popupLogin').popup('open');

	} else {

		$('#popupSaveoredit').popup('open');
	}

}

function deletecustomParams() {

	var key = $("#customprams").val();
	choosefirstOptionOfselectbox("#customprams");
	choosefirstOptionOfselectbox("#parameterSetting");

	$("#customprams option[value=" + key + "]").remove();
	$("#parameterSetting option[value=" + key + "]").remove();

	$.each(userdata_tel.data[key], function(keyc, valc) {

		$("#" + keyc + "_editor").val("");

	});

	delete userdata_tel.data[key];
	delete userdata_tel.userlistname[key];

	if (window.localStorage) {

		localStorage.users = JSON.stringify(users);

	}

}

function getValuessub(d, o) {
	var o2 = {};
	$.each(o, function(k, v) {
		o2[k] = v;
	});
	$.each(d, function(k, v) {
		delete o2[k];
	});
	return o2;
}

function setValues(value) {

	if (value == "0") {
		return;
	}

	var data;
	if (value != "1" && value != "2") {

		data = userdata_tel.data;
	} else {

		data = systemdata;
	}
	
	
	if(value != "1" && value != "2"){
		// hide the  conditon not in the saved data
		var s = getValuessub(data[value], systemdata["1"]);
		

		$.each(s, function(keyc1, valc1) {
			var elem = $("#" + keyc1);
			var jqobj = elem.parent().parent().parent();
			
			var name = jqobj.attr("name");

			var conditonpair = $("[name=" + "'" + name + "'" + "]");

			jqobj.addClass("invisibleofdiv");
			$(conditonpair.get(0)).removeClass("invisibleofdiv");

		});
		
		
		//show the condtion in saved data but not selected.
		$.each(data[value], function(keyc, valc) {

			var elem = $("#" + keyc);
			elem.val(valc);
			var jqobj = elem.parent().parent().parent();
			var name = jqobj.attr("name");
			
			var conditonpair = $("[name=" + "'" + name + "'" + "]");
			$(conditonpair.get(0)).addClass("invisibleofdiv");
			jqobj.removeClass("invisibleofdiv");

		});
	}else{
		$.each(data[value], function(keyc, valc) {
			var elem = $("#" + keyc);
			elem.val(valc);
		});
	}
	return false;

}
function setValuesForeditor(value) {

	if (value == "0") {

		return;
	}

	var data;

	data = userdata_tel.data;

	$.each(data[value], function(keyc, valc) {

		$("#" + keyc + "_editor").val(valc);

	});

	return false;

}

function openpopupforSavePram() {
	var key = $("#customprams").val();
	if (key == 0) {
		return;
	} else {
		$("#popupforSavePram").popup("open");
	}
}

function saveEditor() {
	var temp1 = {};
	$("[name$='_editor_text']").each(function(index, ele) {
		var temp = {};
		if(ele.value!=""){
			temp[ele.id.split("_")[0]] = ele.value;
			temp1 = $.extend(true, temp1, temp);
		}
	});
	userdata_tel.data[$("#customprams").val()] = temp1;

	if (window.localStorage) {

		localStorage.userdata_tel = JSON.stringify(userdata_tel);

	}
	choosefirstOptionOfselectbox("#parameterSetting");

}
// loadingpage
$(document)
		.on(
				"click",
				".show-page-loading-msg",
				function() {

					var $this = $(this), theme = $this.jqmData("theme")
							|| $.mobile.loader.prototype.options.theme, msgText = $this
							.jqmData("msgtext")
							|| $.mobile.loader.prototype.options.text, textVisible = $this
							.jqmData("textvisible")
							|| $.mobile.loader.prototype.options.textVisible, textonly = !!$this
							.jqmData("textonly");
					html = $this.jqmData("html") || "";

					$.mobile.loading("show", {
						text : msgText,
						textVisible : textVisible,
						theme : theme,
						textonly : textonly,
						html : html
					});
				}).on("click", ".hide-page-loading-msg", function() {
			$.mobile.loading("hide");
		});
// loadingpage


Array.prototype.remove = function(from, to) {

	var rest = this.slice((to || from) + 1 || this.length);

	this.length = from < 0 ? this.length + from : from;

	return this.push.apply(this, rest);

};

function choosefirstOptionOfselectbox(id) {
	$(id + " option[value='0']").prop("selected", "selected");

	$(id).parent().children("span").html($(id + " option[value='0']").text());

}

function setOptions(c) {
	if (c == "init") {
		$("#parameterSetting").html("");
		$("#customprams").html("");
		$.each(systemlistname, function(key, value) {
			jQuery("<option value='" + key + "'>" + value + "</option>")
					.appendTo("#parameterSetting");
		});
		$.each(systemlistname2, function(key, value) {
			jQuery("<option value='" + key + "'>" + value + "</option>")
					.appendTo("#customprams");
		});
		choosefirstOptionOfselectbox("#customprams");
		choosefirstOptionOfselectbox("#parameterSetting");
	} else {
		$.each(userdata_tel.userlistname, function(key, value) {

			jQuery("<option value='" + key + "'>" + value + "</option>")
					.appendTo("#customprams");
			jQuery("<option value='" + key + "'>" + value + "</option>")
					.appendTo("#parameterSetting");
		});
	}

}
function controlloginIcon() {

	if (userdata_tel.status == "unlogin") {
		$("#userbtn").show();
		$("#userinfo").hide();

	} else {
		$("#userbtn").hide();
		$("#userinfo").html(current_telno);
		$("#userinfo").show();
	}
}

function addcondtion() {
	$("#condtionpanel").panel("close");

	var checkboxes = $("#condtionpanel").find("[name$='grid-checkbox']");
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked == true) {
			var gridb = $(checkboxes[i]).parent().parent().parent();
			gridb.addClass("invisibleofdiv");
			getConditonOpposit(gridb).removeClass("invisibleofdiv");

		}

	}
}

function getConditonOpposit(obj) {

	var name = $(obj).attr("name");

	var conditonpair = $("[name$=" + "'" + name + "'" + "]");
	for (var i = 0; i < conditonpair.length; i++) {

		if ($(obj).attr("id") == $(conditonpair[i]).attr("id")) {

			if (i == 0) {

				return $(conditonpair[1]);
			} else {

				return $(conditonpair[0]);
			}
		}

	}
}

var gridc;
function setObjectbeforedelete(obj) {
	gridc = $(obj).parent().parent();
}
function deletecondtion() {

	gridc.addClass("invisibleofdiv");

	getConditonOpposit(gridc).removeClass("invisibleofdiv");
	gridc = null;
}

$(function() {
	tableSort($('#table-custom-2'));
});

function tableSort(jqTableObj) {
	jqTableObj.find('thead .sortable').click(

	function() {

		var dataType = $(this).attr('dataType');
		var tableObj = $(this).closest('table');
		var index = tableObj.find('thead th').index(this) + 1;
		var arr = [];
		var row = tableObj.find('tbody tr');

		$.each(row, function(i) {
			arr[i] = row[i];
		});

		if ($(this).hasClass('current')&&!dataResearchedFlg) {
			arr.reverse();
		} else {
			arr.sort(Utils.sortStr(index, dataType));

			tableObj.find('thead th').removeClass('current');
			$(this).addClass('current');
			dataResearchedFlg=false;
		}

		var fragment = document.createDocumentFragment();

		$.each(arr, function(i) {
			fragment.appendChild(arr[i]);
		});

		tableObj.find('tbody').append(fragment);
	});

	var Utils = (function() {
		function sortStr(index, dataType) {
			return function(a, b) {
				
				var aText = $(a).find('td:nth-child(' + index + ')').attr(
						'_order')
						|| $(a).find('td:nth-child(' + index + ')').text();
				var bText = $(b).find('td:nth-child(' + index + ')').attr(
						'_order')
						|| $(b).find('td:nth-child(' + index + ')').text();

				if (dataType != 'text') {
					aText = parseNonText(aText, dataType);
					bText = parseNonText(bText, dataType);

					return aText > bText ? -1 : bText > aText ? 1 : 0;
				} else {
					return aText.localeCompare(bText);
				}
			};
		}

		function parseNonText(data, dataType) {
			switch (dataType) {
			case 'int':
				return parseInt(data) || 0;
			case 'float':
				return parseFloat(data) || 0;
			default:
				return filterStr(data);
			}
		};

		// 过滤中文字符和$
		function filterStr(data) {
			if (!data) {
				return 0;
			}

			return parseFloat(data.replace(
					/^[\$a-zA-z\u4e00-\u9fa5 ]*(.*?)[a-zA-z\u4e00-\u9fa5 ]*$/,
					'$1'));
		}

		return {
			'sortStr' : sortStr
		};
	})();
}
var contionnames=["dayparam","weekparam","kxdparam","kxwparam","ztparam","currentkparam","duringkparam","bankuaiparam"];
function loadconditioncontroll(){
	$.each(contionnames,function(i,v){
		if(i==0){
			return true;
		}
		
		var conditonpair = $("[name$=" + "'" + v + "'" + "]");
		$(conditonpair.get(1)).addClass("invisibleofdiv");
		$(conditonpair.get(0)).removeClass("invisibleofdiv");
		
	});
}

function validatconditonForm(){
	var conditonvalidator=$('#conditonForm').validate({
		rules : {

			dayk_condition_text : {
				"intAndLengthRight" : [ 4 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 3, 30 ],
					p2 : [ 1, "第二个参数", 2, 20 ],
					p3 : [ 2, "第三个参数", 0, 99 ],
					p4 : [ 3, "第四个参数", -1, 1 ]
				}

			},

			weekk_condition_text : {
				"intAndLengthRight" : [ 4 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 3, 20 ],
					p2 : [ 1, "第二个参数", 2, 20 ],
					p3 : [ 2, "第三个参数", 0, 99 ],
					p4 : [ 3, "第四个参数", -1, 1 ]
				}

			},
			// todo 需要不定均线check
			onlykd_condition_text : {
				"intAndLengthRight" : [ 6 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 0, 10 ],
					p2 : [ 1, "第二个参数", 0, 20 ],
					p3 : [ 2, "第三个参数", -1, 1 ],
					p4 : [ 3, "第四个参数", 3, 30 ],
					p5 : [ 4, "第五个参数", 3, 30 ],
					p6 : [ 5, "第六个参数", 3, 30 ]
				},
				"relativecheck":{
					r1:["1-0>0","第二个参数必须大于等于第一个参数"]
				}
			},
			onlykw_condition_text : {
				"intAndLengthRight" : [ 6 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 0, 10 ],
					p2 : [ 1, "第二个参数", 0, 20 ],
					p3 : [ 2, "第三个参数", -1, 1 ],
					p4 : [ 3, "第四个参数", 3, 30 ],
					p5 : [ 4, "第五个参数", 3, 30 ],
					p6 : [ 5, "第六个参数", 3, 30 ]
				},
				"relativecheck":{
					r1:["1-0>0","第一个参数必须大于等于第二个参数"]
				}
			},
			zt_condition_text : {
				"intAndLengthRight" : [ 2 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 1, 100 ],
					p2 : [ 1, "第二个参数", 0, 20 ]
					
				},
				"relativecheck":{
					r1:["0-1>0","第一个参数必须大于等于第二个参数"]
				}
			},
			duringk_condition_text : {
				"intAndLengthRight" : [ 3 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", 0, 20 ],
					p2 : [ 1, "第二个参数", 2, 50 ],
					p3 : [ 2, "第三个参数", 1, 500 ]
					
				}
			},
			currentk_condition_text : {
				"intAndLengthRight" : [ 5 ],
				"rangeCheck" : {
					p1 : [ 0, "第一个参数", -1, 1 ],
					p1 : [ 1, "第二个参数", -1, 1 ],
					p2 : [ 2, "第三个参数", 3, 30 ],
					p3 : [ 3, "第四个参数", -100, 100 ],
					p4 : [ 4, "第五个参数", -100, 100 ]
					
				},
				"relativecheck":{
					r1:["4-3>0","第五个参数必须大于第四个参数"]
				}
			},
			bankuai_condition_text : {
				"intAndLengthInrange" : [10000 ]
			}

		},

		errorPlacement : function(error, element) {

			error.appendTo(element.parent().parent());
		}
	});
	return conditonvalidator;
}

$(document).ready(
		function() {

			$("#login_okbtn").on("click", function(event) {
				login(donothing);
			});

			var conditions = $("[id$='param_con']");

			for (var i = 0; i < conditions.size(); i++) {

				var jqobj = $(conditions[i]);
				var name = jqobj.attr("name");

				var conditonpair = $("[name=" + "'" + name + "'" + "]");
				if (jqobj.hasClass("invisibleofdiv")) {
					$(conditonpair.get(0)).removeClass("invisibleofdiv");

				} else {

					$(conditonpair.get(0)).addClass("invisibleofdiv");
				}

			}

			// validation
			jQuery.validator.addMethod("intAndLengthRight", function(value,
					element, params) {
				return this.optional(element)
						|| function(value, element, params) {
							var arr = value.split(",");

							var reg = /[^0-9|\,|\-| ]/;
							if (reg.test(value)) {
								return false;

							}

							if (arr.length != params[0]) {
								return false;
							} else {
								for (var i = 0; i < arr.length; i++) {
									var t = parseInt(arr[i], 10);

									if (isNaN(t)) {
										return false;
									}
								}

								return true;
							}

						}(value, element, params);
			}, "格式有误，请修正输入！");
			
			jQuery.validator.addMethod("intAndLengthInrange", function(value,
					element, params) {
				return this.optional(element)
						|| function(value, element, params) {
							var arr = value.split(",");

							var reg = /[^0-9|\,|\-| ]/;
							if (reg.test(value)) {
								return false;

							}

							if (arr.length > params[0]) {
								return false;
							} else {
								for (var i = 0; i < arr.length; i++) {
									var t = parseInt(arr[i], 10);

									if (isNaN(t)) {
										return false;
									}
								}

								return true;
							}

						}(value, element, params);
			}, "格式有误，请修正输入！");

			jQuery.validator.addMethod("samename", function(value, element,
					params) {
				return this.optional(element)
						|| function(value, element, params) {
							var flg = true;
							$("#parameterSetting option").each(
									function(index, elem) {

										if ($.trim($(elem).html()) == $
												.trim(element.value)) {
											flg = false;
											return false;
										}
										;

									});
							return flg;

						}(value, element, params);
			}, "参数名已经存在，请重命名");

			jQuery.validator.addMethod("paramcounter", function(value, element,
					params) {
				return this.optional(element)
						|| function(value, element, params) {
							var flg = true;
							var cnt = 0;
							$("#parameterSetting option").each(
									function(index, elem) {
										cnt++;

										if (cnt > 28) {
											flg = false;
											return false;
										}

									});
							return flg;

						}(value, element, params);
			}, "参数名个数已超，请删除一些");

			jQuery.validator.addMethod("validcellno", function(value, element,
					params) {
				return this.optional(element)
						|| function(value, element, params) {

							var reg = /^1[0-9][0-9]\d{8,8}$/;
							if (!reg.test(value)) {
								return false;

							}
							return true;

						}(value, element, params);
			}, "非有效手机号码");

			jQuery.validator.addMethod("rangeCheck", function(value, element,
					params) {

				return this.optional(element)
						|| function(value, element, params) {
							// for conditon value 5,8,10,-1
							var arr = value.split(",");
							var errflg = false;
							$.each(params,
									function(key, val) {

										var arrps = (val + "").split(",");
										var intvalue = parseInt(arr[arrps[0]]);

										if (intvalue < arrps[2]
												|| intvalue > arrps[3]) {
											errflg = true;
											return false;
										}
									});
							return !errflg;

						}(value, element, params);
			}, function(obj) {

				var ele = $.makeArray(arguments).slice(1);

				var errparams;

				var arr = ele[0].value.split(",");

				$.each(obj, function(key, val) {

					var arrps = (val + "").split(",");
					var intvalue = parseInt(arr[arrps[0]]);

					if (intvalue < arrps[2] || intvalue > arrps[3]) {
						errparams = arrps;
						return false;
					}
				});

				return jQuery.validator.format("{1}:请修正设定在{2}到{3}整数范围内",
						errparams);
			});
			
			jQuery.validator.addMethod("relativecheck", function(value, element,
					params) {

				return this.optional(element)
						|| function(value, element, params) {
							// for conditon value 5,8,10,-1
							var arr = value.split(",");
							var errflg = false;
							$.each(params,
									function(key, val) {
										
										var arrps = (val + "").split(",");
										//2-1>0 第3参数的值大于第一参数的值
										var rules=arrps[0].split(">");
										
										//2-1
										var numbers=rules[0].split("-");
										
										//第三参数的值
										var res=parseInt(arr[numbers[0]]);
										
										for(var i=1;i<numbers.length;i++){
											
											res=res-parseInt(arr[numbers[i]]);
										}
										
										

										if (res <0) {
											errflg = true;
											return false;
										}
									});
							return !errflg;

						}(value, element, params);
			}, function(obj) {

				var ele = $.makeArray(arguments).slice(1);

				var errparams;

				var arr = ele[0].value.split(",");

				$.each(obj, function(key, val) {

					
					var arrps = (val + "").split(",");
					//2-1>0 第3参数的值大于第一参数的值
					var rules=arrps[0].split(">");
					
					//2-1
					var numbers=rules[0].split("-");
					
					//第三参数的值
					var res=parseInt(arr[numbers[0]]);
					for(var i=1;i<numbers.length;i++){
						
						res=res-parseInt(arr[numbers[i]]);
					}
					
					if (res <0) {
						errparams = arrps;
						return false;
					}
				});

				return jQuery.validator.format("{1}",
						errparams);
			});


			//validator function
			validatconditonForm();
			var validator = $('#saveeditor_form').validate({
				rules : {

					parametername : {
						required : true,
						maxlength : 15,
						"samename" : true,
						"paramcounter" : true
					}

				},
				messages : {
					parametername : {
						required : "请输入参数名",
						maxlength : "参数名最长不能超过{0}个字"
					}

				},
				errorPlacement : function(error, element) {

					error.appendTo(element.parent().parent());

				}
			});

			var validator = $('#loginform').validate({
				rules : {
					cellno : {
						required : true,
						"validcellno" : true
					}
				},
				messages : {
					cellno : {
						required : "请输入手机号码"

					}

				},
				errorPlacement : function(error, element) {

					error.appendTo(element.parent().parent());

				}
			});
			//localStorage setting
			var a = function() {

				if (window.localStorage) {
					//					localStorage.clear();

					if (localStorage.hasOwnProperty("users")) {

						var userjson = JSON
								.parse(localStorage.getItem("users"));
						$.each(userjson, function(index, detail) {
							users.push(detail);
						});
						var cnt = 0;
						$.each(users, function(index, val) {
							cnt++;
							$.each(val, function(key, data) {
								if (data.status == "login") {
									current_telno = key;
									userdata_tel = data;
								}
							});

							;
						});
						if (cnt > 10) {
							users.remove(0, cnt - 10 - 1);
							localStorage.users = JSON.stringify(users);
						}

					}

				}

				if ($.isEmptyObject(userdata_tel)) {
					var t = inituserdata_tel();
					userdata_tel = t["00000000"];
					current_telno = "00000000";
					if ($.isEmptyObject(users)) {
						users.push(t);
					}
				}

			}();

			setOptions();

			controlloginIcon();
			loadconditioncontroll();
			$('html').bind('keypress', function(e)
					{
					   if(e.keyCode == 13)
					   {
					      return false;
					   }
					});
			$(document).bind('contextmenu', function(e)
					{
					   
					      return false;
					   
					});
		}

);
