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

editor.txt.html($("#courseContent").val());

//图片上传
var coursePicDropzone = new Dropzone("#coursePicDropzone",{
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
                $("#picUrl").val(data.resultData.url);
            }
        });
        this.on("error",function(file) {
            //上传失败触发方法
        });
        this.on("removedfile",function(file){
            //删除文件时触发的方法
            $("#picUrl").val("");
        });
    }
});

//音频上传
var courseAudioDropzone = new Dropzone("#courseAudioDropzone",{
    url: "/cms/uploadFile", //必须填写
    method:"post",  //也可用put
    paramName:"file", //默认为file
    maxFiles:1,//一次性上传的文件数量上限
    maxFilesize: 100, //MB
    acceptedFiles: ".mp3", //上传的类型
    addRemoveLinks: true,
    parallelUploads: 1,
    dictDefaultMessage: "拖动文件至此或者点击上传",
    dictMaxFilesExceeded: "您最多只能上传1个文件！",
    dictResponseError: '文件上传失败!',
    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.mp3。",
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
            if(data != null) {
                $("#audioUrl").val(data.resultData.url);
            }
        });
        this.on("error",function(file) {
            //上传失败触发方法
        });
        this.on("removedfile",function(file){
            //删除文件时触发的方法
            $("#audioUrl").val("");
        });
        this.on('totaluploadprogress', function (progress, byte, bytes) {
            $("#courseAudioDropzone").children(".dz-processing").children(".dz-progress")
                .children(".dz-upload").attr("style", "width:0%");
            var progressIt = setInterval(function () {
                $.get("/cms/uploadFile/percent", {}, function (data) {
                    var percent = data;
                    if (percent >= 100) {
                        clearInterval(progressIt);
                        percent = 100;//不能大于100
                    }
                    $("#courseAudioDropzone").children(".dz-processing").children(".dz-progress")
                        .children(".dz-upload").attr("style", "width:" + percent + "%");
                }, "json");
            }, 1000);
        });
    }
});

//初始化封面
var courseImgFile = {
    name: "courseImg.jpg",
    accetped: true
};

coursePicDropzone.emit("addedfile", courseImgFile);
coursePicDropzone.createThumbnailFromUrl("courseImgFile", $("#picUrl").val());
coursePicDropzone.emit("success", courseImgFile);
coursePicDropzone.emit("complete", courseImgFile);
coursePicDropzone.options.maxFiles = coursePicDropzone.options.maxFiles;

//初始化音频
var courseAudioFile = {
    name: "courseAudio.mp3",
    accetped: true
};

courseAudioDropzone.emit("addedfile", courseAudioFile);
courseAudioDropzone.emit("success", courseAudioFile);
courseAudioDropzone.emit("complete", courseAudioFile);
courseAudioDropzone.options.maxFiles = courseAudioDropzone.options.maxFiles;

//更新课程
function updateCourse(){
    $("#btnUpdateCourse").attr("disabled","disabled");
    var freeFlag = "0";
    if($("#freeFlag").is(':checked')){
        freeFlag = "1";
    }
    $.ajax({
        type: "post",
        url: "/cms/updateCourse",
        data: {
            id: $("#courseId").val(),
            courseType: $("input[name='courseType']:checked").val(),
            freeFlag: freeFlag,
            subjectId: $("#subjectId").val(),
            courseName: $("#courseName").val(),
            picUrl: $("#picUrl").val(),
            audioUrl: $("#audioUrl").val(),
            sort: $("#sort").val(),
            courseContent: editor.txt.html()
        },
        success: function (data) {
            if(data.resultCode == "000000"){
                new PNotify({
                    title: '更新成功',
                    text: '跳转课程列表',
                    type: 'success',
                    styling: 'bootstrap3'
                });
                goCourse();
            }else{
                $("#btnUpdateCourse").removeAttr("disabled");
                new PNotify({
                    title: '更新失败',
                    text: data.resultMsg,
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }
        }
    });
}