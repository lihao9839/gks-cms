//图片上传
var aboutusDropzone = new Dropzone("#aboutusDropzone",{
    url: "/cms/uploadFile", //必须填写
    method:"post",  //也可用put
    paramName:"file", //默认为file
    maxFiles:1,//一次性上传的文件数量上限
    maxFilesize: 2, //MB
    acceptedFiles: ".jpg,.gif,.png,.jpeg", //上传的类型
    addRemoveLinks: true,
    parallelUploads: 1,
    dictDefaultMessage: "拖动文件至此或者点击上传",
    dictMaxFilesExceeded: "您最多只能上传1个文件！",
    dictResponseError: '文件上传失败!',
    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
    dictFallbackMessage:"浏览器不受支持",
    dictFileTooBig:"文件过大上传文件最大支持.",
    dictRemoveLinks: "删除",
    dictCancelUpload: "取消",
    init:function(){
        this.on("addedfile", function(file) { 
        //上传文件时触发的事件
        });
        this.on("queuecomplete",function(file) {
            //上传完成后触发的方法
        });
        this.on("success",function(file,data) {
            //上传成功触发方法
        	$("#aboutusPicUrl").val(data.resultData.url);
        });
        this.on("error",function(file) {
            //上传失败触发方法
        });
        this.on("removedfile",function(file){
            //删除文件时触发的方法
        });
    }
});
//初始化图片数据
if($("#aboutusPicUrl").val() != null && $("#aboutusPicUrl").val() != ""){
	$("#addAboutus").hide();
	$("#updateAboutus").show();
	var aboutusPicFile = {
			name: "aboutusPicUrl.jpg",
			accepted: true
	};
	aboutusDropzone.emit("addedfile", aboutusPicFile);
	aboutusDropzone.emit("thumbnail", aboutusPicFile, $("#aboutusPicUrl").val());
	aboutusDropzone.emit("success", aboutusPicFile);
	aboutusDropzone.emit("complete", aboutusPicFile);
	aboutusDropzone.options.maxFiles = aboutusDropzone.options.maxFiles;
}

function addOrUpdateSharePic(){
	$.ajax({
        type: "post",
        url: "/cms/addOrUpdatePic",
        data: {
            id: $("#aboutUsId").val(),
        	picUrl: $("#aboutusPicUrl").val(),
        	type: "1"
        },
        success: function (data) {
        	if(data.resultCode == "000000"){
            	new PNotify({
                    title: '成功',
                    text: '',
                    type: 'success',
                    styling: 'bootstrap3'
                });
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