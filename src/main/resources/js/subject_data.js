function goSubjectDataDetail(subjectId){
	$.ajax({
        type: "get",
        url: "/cms/goSubjectDataDetail",
        data: {
        	subjectId: subjectId
        },
        success: function (data) {
        	$("#pageContent").html(data);
        }
    });
}

function goSubjectDataList(){
    $.ajax({
        type: "get",
        url: "/cms/goSubjectData",
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

