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
editor.$textElem.attr('contenteditable', false);


