function login(){
	$.ajax({
        type: "get",
        url: "/cms/login",
        data: {
        	userName: $("#userName").val(),
        	password: $("#password").val()
        },
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '登录成功',
                    text: '跳转到首页',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	window.location.href= data.resultData.url;
            }else{
            	new PNotify({
                    title: '登录失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function updateUserPassword(){
	$.ajax({
        type: "post",
        url: "/cms/updateUserPassword",
        data: {
        	updateUserName: $("#updateUserName").val(),
        	updatePassword: $("#updatePassword").val(),
        	updateNewPassword: $("#updateNewPassword").val(),
        	updateConfirmNewPassword: $("#updateConfirmNewPassword").val(),
        },
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '修改成功',
                    text: '跳转到首页',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	window.location.href= data.resultData.url;
            }else{
            	new PNotify({
                    title: '修改失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

document.onkeydown = function (event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 13) {
        login();
    }
}
