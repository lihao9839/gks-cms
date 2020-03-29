function subscribDetail(userName){
	$.ajax({
        type: "get",
        url: "/cms/goSubscribList",
        data: {
        	userName: userName
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function goAppUser(){
	$.ajax({
        type: "get",
        url: "/cms/goAppUser",
        data: {
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