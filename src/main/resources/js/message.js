function goMessage(){
	$.ajax({
        type: "get",
        url: "/cms/goMessage",
        data: {
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function goMessageDetail(messageId){
	$.ajax({
        type: "get",
        url: "/cms/goMessageDetail",
        data: {
        	messageId: messageId
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function getMessageList(){
	$.ajax({
        type: "get",
        url: "/cms/getMessageList",
        data: {
        	keyWord: $("#keyWord").val(),
        	status: $("#status").val()
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function goAddMessage(){
	$.ajax({
        type: "get",
        url: "/cms/goAddMessage",
        data: {
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function addMessage(){
	$.ajax({
        type: "post",
        url: "/cms/addMessage",
        data: {
        	title: $("#title").val(),
        	content: $("#content").val(),
        	sendDate: $("#sendDate").val(),
        	allFlag: $("#allFlag").val()
        },
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '新增成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goMessage();
            }else{
            	new PNotify({
                    title: '新增失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function cancelMessage(messageId){
	$.ajax({
        type: "post",
        url: "/cms/cancelMessage",
        data: {
        	messageId: messageId
        },
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '取消成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goMessage();
            }else{
            	new PNotify({
                    title: '取消失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function goEditMessage(messageId){
	$.ajax({
        type: "get",
        url: "/cms/goEditMessage",
        data: {
        	messageId:messageId
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function updateMessage(){
	$.ajax({
        type: "post",
        url: "/cms/updateMessage",
        data: {
        	id: $("#id").val(),
        	title: $("#title").val(),
        	content: $("#content").val(),
        	sendDate: $("#sendDate").val(),
        	allFlag: $("#allFlag").val()
        },
        async: false,
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '更新成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goMessage();
            }else{
            	new PNotify({
                    title: '更新失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

$(document).ready(function() {
    init_DataTables();
    init_PNotify();
    init_autosize();
    init_autocomplete();
});