function showUserAddModal(){
	$("#userAddModal").modal("show");
}

function addUser(){
    $("#btnAddUser").attr("disabled","disabled");
	$.ajax({
        type: "post",
        url: "/cms/addUser",
        data: {
        	userName: $("#userName").val(),
        	realName: $("#realName").val(),
        	roleType: $("#roleType").val()
        },
        success: function (data) {
            if(data.resultCode == "000000"){
            	new PNotify({
                    title: '新增成功',
                    text: '刷新列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
            	$("#userAddModal").modal("hide");
                $("#btnAddUser").removeAttr("disabled");
            	loadUserList();
            }else{
                $("#btnAddUser").removeAttr("disabled");
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

function loadUserList(){
	$.ajax({
        type: "get",
        url: "/cms/goUser",
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

function closePermission(id){
	$.ajax({
        type: "post",
        url: "/cms/closePermission",
        data: {
        	id: id
        },
        success: function (data) {
        	 if(data.resultCode == "000000"){
             	new PNotify({
                     title: '关闭成功',
                     text: '刷新列表',
                     type: 'success',
                     styling: 'bootstrap3'
                 });
             	loadUserList();
             }else{
             	new PNotify({
                     title: '关闭失败',
                     text: data.resultMsg,
                     type: 'error',
                     styling: 'bootstrap3'
                 });
             }
        }
    });
}

function openPermission(id){
	$.ajax({
        type: "post",
        url: "/cms/openPermission",
        data: {
        	id: id
        },
        success: function (data) {
        	 if(data.resultCode == "000000"){
             	new PNotify({
                     title: '开通成功',
                     text: '刷新列表',
                     type: 'success',
                     styling: 'bootstrap3'
                 });
             	loadUserList();
             }else{
             	new PNotify({
                     title: '开通失败',
                     text: data.resultMsg,
                     type: 'error',
                     styling: 'bootstrap3'
                 });
             }
        }
    });
}

function resetPassword(id){
	$.ajax({
        type: "post",
        url: "/cms/resetPassword",
        data: {
        	id: id
        },
        success: function (data) {
        	 if(data.resultCode == "000000"){
             	new PNotify({
                     title: '重置成功',
                     text: '刷新列表',
                     type: 'success',
                     styling: 'bootstrap3'
                 });
             	loadUserList();
             }else{
             	new PNotify({
                     title: '重置失败',
                     text: data.resultMsg,
                     type: 'error',
                     styling: 'bootstrap3'
                 });
             }
        }
    });
}

function getUserInfo(id){
	$.ajax({
        type: "get",
        url: "/cms/getUserInfo",
        data: {
        	id: id
        },
        success: function (data) {
        	 if(data.resultCode == "000000"){
             	$("#idUpdate").val(data.resultData.id);
             	$("#userNameUpdate").val(data.resultData.user.userName);
             	$("#realNameUpdate").val(data.resultData.user.realName);
             	$("#roleTypeUpdate").val(data.resultData.user.roleType);
             	$("#userUpdateModal").modal("show");
             }else{
             	new PNotify({
                     title: '查询用户失败',
                     text: data.resultMsg,
                     type: 'error',
                     styling: 'bootstrap3'
                 });
             }
        }
    });
}

function updateUser(){
	$.ajax({
        type: "post",
        url: "/cms/updateUser",
        data: {
        	id: $("#idUpdate").val(),
        	userName: $("#userNameUpdate").val(),
        	realName: $("#realNameUpdate").val(),
        	roleType: $("#roleTypeUpdate").val()
        },
        success: function (data) {
        	 if(data.resultCode == "000000"){
             	new PNotify({
                     title: '重设成功',
                     text: '刷新列表',
                     type: 'success',
                     styling: 'bootstrap3'
                 });
             	$("#userUpdateModal").modal("hide");
             	loadUserList();
             }else{
             	new PNotify({
                     title: '重设失败',
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