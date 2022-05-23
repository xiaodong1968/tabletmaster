var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
	if(token!=null){
		xhr.setRequestHeader(header, token);
	}
});
$(document).ajaxError(function(event,xhr,options,exc){
	
});
$(document).ajaxComplete(function(event, xhr, settings) {  
		var status = xhr.status;
		if(xhr.status==403){
			layer.msg("对不起！你没有操作权限");
		}
	   if(xhr.status==408){
		   layer.msg("登陆超时，请重新登陆！");
		   if(window.top!=null ){
				window.top.location="signIn";
			}
	   }
});
