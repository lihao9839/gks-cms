function resetSearch(){
	$("#startDate").val("");
	$("#endDate").val("");
}

function getDataList(){
	$.ajax({
        type: "post",
        url: "/cms/goTotalData",
        data: {
        	startDate: $("#startDate").val(),
            endDate: $("#endDate").val()
        },
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}
