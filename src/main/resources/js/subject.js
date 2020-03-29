function goAddSubject(){
	$.ajax({
        type: "get",
        url: "/cms/goAddSubject",
        data: {
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}
//跳转专栏详情页面
function goSubject(){
	$.ajax({
        type: "get",
        url: "/cms/goSubject",
        data: {
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

//跳转编辑专栏页面
function editSubject(id){
    $.ajax({
        type: "get",
        url: "/cms/goEditSubject",
        data: {
            id: id
        },
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

function publishSubject(id){
	$.ajax({
        type: "get",
        url: "/cms/publishSubject",
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
            	goSubject();
            }else{
            	new PNotify({
                    title: '发布失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function clearSubject(id){
	$.ajax({
        type: "get",
        url: "/cms/clearSubject",
        data: {
        	id: id
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '删除成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goSubject()
            }else{
            	new PNotify({
                    title: '删除失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function rollbackSubject(id){
	$.ajax({
        type: "get",
        url: "/cms/rollbackSubject",
        data: {
        	id: id
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goSubject()
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

function submitSubject(id){
	$.ajax({
        type: "get",
        url: "/cms/submitSubject",
        data: {
        	id: id
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goSubject()
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

function inspectSubject(id){
	$.ajax({
        type: "get",
        url: "/cms/goSubjectDetail",
        data: {
        	id: id
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

$(document).ready(function() {
    init_DataTables();
    init_PNotify();
    init_autosize();
    init_autocomplete();
});