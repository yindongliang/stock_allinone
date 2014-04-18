$(document).ready(
		function() {
			
			$("#login_okbtn").on("click", function(event){
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
			}, "��ʽ�������������룡");

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
			}, "�������Ѿ����ڣ���������");

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
			}, "�����������ѳ�����ɾ��һЩ");

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
			}, "����Ч�ֻ�����");

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

				return jQuery.validator.format("{1}:�������趨��{2}��{3}������Χ��",
						errparams);
			});

			$('#conditonForm').validate({
				rules : {

					dayk__condition_text : {
						"intAndLengthRight" : [ 4 ],
						"rangeCheck" : {
							p1 : [ 0, "��һ������", 3, 10 ],
							p2 : [ 1, "�ڶ�������", 2, 20 ],
							p3 : [ 2, "����������", 0, 99 ],
							p4 : [ 3, "���ĸ�����", -1, 1 ]
						}

					},

					weekk_condition_text : {
						"intAndLengthRight" : [ 4 ],
						"rangeCheck" : {
							p1 : [ 0, "��һ������", 3, 10 ],
							p2 : [ 1, "�ڶ�������", 2, 20 ],
							p3 : [ 2, "����������", 0, 99 ],
							p4 : [ 3, "���ĸ�����", -1, 1 ]
						}

					}

				},

				errorPlacement : function(error, element) {

					error.appendTo(element.parent().parent());
				}
			});

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
						required : "�����������",
						maxlength : "����������ܳ���{0}����"
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
						required : "�������ֻ�����"

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
		}

);