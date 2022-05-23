$.validator.setDefaults({
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    success: function(element) {
        element.closest('.form-group').removeClass('has-error');
    },
    errorElement: "span",
    errorPlacement: function(error, element) {
        if (element.is(":radio") || element.is(":checkbox")) {
            error.appendTo(element.parent().parent().parent());
        } else {
            error.appendTo(element.parent());
        }
    },
    errorClass: "help-block m-b-none",
    validClass: "help-block m-b-none"
});


//字符验证 
jQuery.validator.addMethod("stringCheck", function(value, element) {
 return this.optional(element) || /^[\u4E00-\u9FA5a-zA-Z0-9_]*$/.test(value);
}, "只能包括中文字、英文字母、数字和下划线");
//只能包含英文字母
jQuery.validator.addMethod("alnum", function(value, element) {
    return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
}, "只能包括英文字母和数字");
//只能包含数字
jQuery.validator.addMethod("justnum", function(value, element) {
    return this.optional(element) || /^[0-9]+$/.test(value);
}, "只能包括数字");
//验证邮箱格式
jQuery.validator.addMethod("emailCheck", function(value, element) {
	 return this.optional(element) || /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(value);
	}, "邮箱格式错误");
//IP地址校验
jQuery.validator.addMethod("ipCheck", function(value, element) {
	 return this.optional(element) || /^(?=(\b|\D))(((\d{1,2})|(1\d{1,2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{1,2})|(2[0-4]\d)|(25[0-5]))(?=(\b|\D))$/.test(value);
	}, "ip地址格式错误");
//只能为正整数
jQuery.validator.addMethod("justInteger", function(value, element) {
    return this.optional(element) || /^[1-9]\d*$/.test(value);
}, "只能为正整数");