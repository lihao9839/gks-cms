function goAppMessage(){
	$.ajax({
        type: "get",
        url: "/cms/goMessage",
        async: false,
        data: {
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function addAppMessage(){
    $("#btnAddAppMessage").attr("disabled","disabled");
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
                $("#btnAddAppMessage").removeAttr("disabled");
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