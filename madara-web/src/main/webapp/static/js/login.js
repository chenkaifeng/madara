//jquery ajax全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    layerIndex:-1,
    beforeSend: function () {
        this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
    },
    complete: function () {
        layer.close(this.layerIndex);
    },
});

layer.config({
	  extend: 'myskin/style.css', //自定义弹出框样式
	  skin: 'my-green-skin'
});

var vm = new Vue({
	el : '#app',
	data : {
		username : '',
		password : '',
		captcha : '',
		error : false,
		errorMsg : '',
		src : 'static/images/captcha.jpg',
		pwdChangeRequest : {
			oldPassword : null,
			newPassword : null,
			newPasswordConfirm : null
		},
	},
	beforeCreate : function() {
		if (self != top) {
			top.location.href = self.location.href;
		}
	},
	methods : {
		refreshCode : function() {
			this.src = "static/images/captcha.jpg?t=" + $.now();
		},
		login : function(event) {
			var data = "&username=" + vm.username
					+ "&password=" + vm.password + "&captcha=" + vm.captcha;
			$.ajax({
				type : "POST",
				url : "login",
				data : data,
				dataType : "json",
				success : function(result) {
					if (result.code == '00') {// 登录成功
						vm.error = false;
						layer.msg('登录成功', {
							  icon: 1,
							  time: 500
						}, function(){
							parent.location.href = "index";
						});   
					} else if (result.code == '10') {// 修改密码
						vm.webPwdChange(result.msg);
					} else {
						vm.error = true;
						vm.errorMsg = result.msg;
						vm.refreshCode();
					}
				}
			});
		},
		webPwdChange : function(title) {
			vm.pwdChangeRequest.oldPassword = null;
			vm.pwdChangeRequest.newPassword = null;
			vm.pwdChangeRequest.newPasswordConfirm = null;
			// 添加数据验证
			vm.changePwdFormValidator();
			layer.open({
				type : 1,
				skin : 'my-green-skin',
				title : title,
				area : [ '550px', '380px' ],
				shadeClose : false,
				content : jQuery("#changePwdLayer"),
				btn : [ '确认', '取消' ],
				btnAlign : 'c',
				yes : function(index) {
					if (!vm.changePwdValidator()) {
						return;
					}
					var data = "username="
							+ vm.username + "&oldPassword="
							+ vm.pwdChangeRequest.oldPassword + "&newPassword="
							+ vm.pwdChangeRequest.newPassword
							+ "&newPasswordConfirm="
							+ vm.pwdChangeRequest.newPasswordConfirm;
					$.ajax({
						type : "POST",
						url : "./sys/user/changePwdAtFirst",
						data : data,
						dataType : "json",
						success : function(result) {
							if (result.code == "00") {
								layer.close(index);
								layer.msg('密码修改成功,自动跳转至首页', {
									  icon: 1,
									  time: 1000
									}, function(){
										parent.location.href = 'index';
									}); 
							} else {
								layer.alert(result.msg);
							}
						}
					});
				}
			});
		},
		changePwdValidator : function() {
			$("#changePwdForm").bootstrapValidator('validate');// 提交验证
			if ($("#changePwdForm").data('bootstrapValidator').isValid()) {
				return true;
			} else {
				return false;
			}
		},
		changePwdFormValidator : function() {
			var formValidator = $("#changePwdForm").data('bootstrapValidator');
			if (formValidator) {
				formValidator.resetForm();
				formValidator.destroy();
				$('#changePwdForm').data('bootstrapValidator', null);
			}
			$("#changePwdForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					changePwdOldPassword : {
						message : '原密码校验失败',
						validators : {
							notEmpty : {
								message : '原密码不能为空'
							},
							stringLength : { // 长度限制
								min : 8,
								max : 18,
								message : '密码长度必须在8到18位之间'
							},
							regexp : { // 正则表达式
								regexp : /^[a-zA-Z0-9]+$/,
								message : '密码只能包含字母、数字'
							}
						}
					},
					changePwdNewPassword : {
						message : '新密码校验失败',
						validators : {
							notEmpty : {
								message : '新密码不能为空'
							},
							stringLength : { // 长度限制
								min : 8,
								max : 18,
								message : '密码长度必须在8到18位之间'
							},
							regexp : { // 正则表达式
								regexp : /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$/,
								message : '密码必须是字母、数字的组合'
							},
							different : {
								field : 'changePwdOldPassword',
								message : '不能与原密码一致'
							},
							identical : {
								field : 'changePwdNewPassword',
								message : '新密码和确认新密码不一致'
							}
						}
					},
					changePwdNewPasswordConfirm : {
						message : '确认新密码校验失败',
						validators : {
							notEmpty : {
								message : '确认新密码不能为空'
							},
							stringLength : { // 长度限制
								min : 8,
								max : 18,
								message : '密码长度必须在8到18位之间'
							},
							regexp : { // 正则表达式
								regexp : /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$/,
								message : '密码必须是字母、数字的组合'
							},
							different : {
								field : 'changePwdOldPassword',
								message : '不能与原密码一致'
							},
							identical : {
								field : 'changePwdNewPassword',
								message : '新密码和确认新密码不一致'
							}
						}
					}
				}
			});
		}
	}
});