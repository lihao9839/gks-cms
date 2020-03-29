var E = window.wangEditor
var editor = new E("#editor-one");
editor.customConfig.uploadImgServer = '/cms/uploadImage'
editor.customConfig.uploadFileName = 'image' //设置文件上传的参数名称
editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'fontSize',  // 字号
    'fontName',  // 字体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'foreColor',  // 文字颜色
    'backColor',  // 背景颜色
    'list',  // 列表
    'justify',  // 对齐方式
    'emoticon',  // 表情
    'image',  // 插入图片
    'table'  // 表格
];
editor.create();

editor.txt.html($("#subjectIntroduceDetail").val());

//图片上传
var subjectDropzone = new Dropzone("#subjectDropzone",{
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
			if(data != null){
				$("#subjectPicUrl").val(data.resultData.url);
			}
		});
		this.on("error",function(file) {
			//上传失败触发方法
		});
		this.on("removedfile",function(file){
			//删除文件时触发的方法
			$("#subjectPicUrl").val("");
		});
	}
});

//初始化专栏图片
var subjectImgFile = {
	name: "subjectImg.jpg",
	accetped: true
};

subjectDropzone.emit("addedfile", subjectImgFile);
subjectDropzone.emit("thumbnail", subjectImgFile, $("#subjectPicUrl").val());
subjectDropzone.emit("success", subjectImgFile);
subjectDropzone.emit("complete", subjectImgFile);
subjectDropzone.options.maxFiles = subjectDropzone.options.maxFiles;

function syncUserName(){
    var userNameteacherId = $("#realName option:selected").val();
    var userName= userNameteacherId.substring(0,11);
    var teacherId= userNameteacherId.substring(11);
    $("#userName").val(userName);
    $("#teacherId").val(teacherId);
}

//更新专栏
function editSubject(){
    $("#btnEditSubject").attr("disabled","disabled");
    var freeCourseFlag = "0";
    if($("#freeCourseFlag").is(':checked')){
        freeCourseFlag = "1";
    }
    $.ajax({
        type: "post",
        url: "/cms/editSubject",
        data: {
        	id: $("#subjectId").val(),
            subjectName: $("#subjectName").val(),
            teacherId: $("#teacherId").val(),
            userIntroduce: $("#userIntroduce").val(),
            userName: $("#userName").val(),
            subjectIntroduce: $("#subjectIntroduce").val(),
            subjectPicUrl: $("#subjectPicUrl").val(),
            subscribePrice: $("#subscribePrice").val(),
            freeCourseFlag: freeCourseFlag,
            subjectIntroduceDetail: editor.txt.html()
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
                $("#btnEditSubject").removeAttr("disabled");
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