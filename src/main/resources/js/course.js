function goAddCourse(){
	$.ajax({
        type: "get",
        url: "/cms/goAddCourse",
        data: {
            searchSubjectId: $("#searchSubjectId").val()
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function resetSearch(){
    $("#searchSubjectId").val("");
    $("#searchSubjectId").text("");
    goCourse();
}

function goCourse(){
    $.ajax({
        type: "get",
        url: "/cms/goCourse",
        data: {
            searchSubjectId: $("#searchSubjectId").val()
        },
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

function publishCourse(id){
	$.ajax({
        type: "post",
        url: "/cms/publishCourse",
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
            	goCourse();
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

function deleteCourse(id){
	$.ajax({
        type: "post",
        url: "/cms/deleteCourse",
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
            	goCourse();
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

function offlineCourse(id){
	$.ajax({
        type: "post",
        url: "/cms/offlineCourse",
        data: {
        	id: id
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '下架成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goCourse();
            }else{
            	new PNotify({
                    title: '下架失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}

function fallbackCourse(id){
	$.ajax({
        type: "post",
        url: "/cms/fallbackCourse",
        data: {
        	id: id
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '恢复成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	goCourse();
            }else{
            	new PNotify({
                    title: '恢复失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}


//跳转课程详情
function inspectCourse(id){
    $.ajax({
        type: "get",
        url: "/cms/goCourseDetail",
        data: {
            id: id,
            searchSubjectId: $("#searchSubjectId").val()
        },
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

//跳转课程编辑
function editCourse(id){
    $.ajax({
        type: "get",
        url: "/cms/goEditCourse",
        data: {
            id: id,
            searchSubjectId: $("#searchSubjectId").val()
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
