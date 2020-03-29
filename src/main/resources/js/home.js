function loadPage(url){
	$.ajax({
        type: "get",
        url: url,
        success: function (data) {
            $("#pageContent").html(data);
        }
    });
}

