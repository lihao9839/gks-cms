function showReplyModal(id){
    $("#replyId").val(id);
    $("#replyModal").modal("show");
}

function goSubjectReply(){
    $.ajax({
        type: "get",
        url: "/cms/goSubjectReply",
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

function reply(){
	$.ajax({
        type: "post",
        url: "/cms/reply/reply",
        data: {
            replyId: $("#replyId").val(),
            teacherReply: $("#teacherReply").val()
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '回复成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
                $("#replyModal").modal("hide");
                goSubjectReply();
            }else{
            	new PNotify({
                    title: '回复失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function replyBack(id){
    $.ajax({
        type: "post",
        url: "/cms/reply/replyBack",
        data: {
            id: id
        },
        success: function (data) {
            if(data.resultCode == "000000"){
                new PNotify({
                    title: '撤回成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
                goSubjectReply();
            }else{
                new PNotify({
                    title: '失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function publish(id){
    $.ajax({
        type: "post",
        url: "/cms/reply/publish",
        data: {
            id: id
        },
        success: function (data) {
            if(data.resultCode == "000000"){
                new PNotify({
                    title: '发布成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
                goSubjectReply();
            }else{
                new PNotify({
                    title: '失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function recall(id){
    $.ajax({
        type: "post",
        url: "/cms/reply/recall",
        data: {
            id: id
        },
        success: function (data) {
            if(data.resultCode == "000000"){
                new PNotify({
                    title: '屏蔽成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
                goSubjectReply();
            }else{
                new PNotify({
                    title: '失败',
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