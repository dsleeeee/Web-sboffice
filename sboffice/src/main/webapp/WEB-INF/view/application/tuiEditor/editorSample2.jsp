<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resource/vender/wijmo/js/wijmo.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.input.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.chart.min.js"></script>

<link rel="stylesheet" type="text/css" href="/resource/vender/wijmo/css/wijmo.min.css" />


<link rel="stylesheet" type="text/css" href="/resource/vender/tuiEditor/css/normalize.css" media="screen">
<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="/resource/vender/tuiEditor/css/stylesheet.css" media="screen">
<link rel="stylesheet" type="text/css" href="/resource/vender/tuiEditor/css/github-light.css" media="screen">
<link rel="stylesheet" href="/resource/vender/tuiEditor/tui-component-colorpicker/dist/colorpicker.min.css">
<link rel="stylesheet" href="/resource/vender/tuiEditor/codemirror/lib/codemirror.css">
<link rel="stylesheet" href="/resource/vender/tuiEditor/highlightjs/styles/github.css">
<link rel="stylesheet" href="/resource/vender/tuiEditor/tui-chart/dist/chart.min.css">
<link rel="stylesheet" href="/resource/vender/tuiEditor/tui-editor/dist/tui-editor.css">
<link rel="stylesheet" href="/resource/vender/tuiEditor/tui-editor/dist/tui-editor-contents.css">
<script src="/resource/vender/tuiEditor/markdown-it/dist/markdown-it.js"></script>
<script src="/resource/vender/tuiEditor/toMark/dist/toMark.js"></script>
<script src="/resource/vender/tuiEditor/jquery/dist/jquery.js"></script>
<script src="/resource/vender/tuiEditor/tui-code-snippet/dist/tui-code-snippet.min.js"></script>
<script src="/resource/vender/tuiEditor/tui-component-colorpicker/dist/colorpicker.min.js"></script>
<script src="/resource/vender/tuiEditor/codemirror/lib/codemirror.js"></script>
<script src="/resource/vender/tuiEditor/highlightjs/highlight.pack.js"></script>
<script src="/resource/vender/tuiEditor/squire-rte/build/squire.js"></script>
<script src="/resource/vender/tuiEditor/plantuml-encoder/dist/plantuml-encoder.min.js"></script>
<script src="/resource/vender/tuiEditor/raphael/raphael.min.js"></script>
<script src="/resource/vender/tuiEditor/tui-chart/dist/chart.min.js"></script>
<script src="/resource/vender/tuiEditor/tui-editor/dist/tui-editor.min.js"></script>

<style>
.form-control{
    font-family: inherit;
    width: 100%;
}

#editSection, #editSection * {
    font-family: inherit;
}

#editSection button,
#editSection input,
#editSection .te-editor {
    font-size: 12px;
}

#editSection h1,
#editSection h2,
#editSection h3,
#editSection h4,
#editSection h5,
#editSection h6 {
    color: #222;
}

#editSection .tui-popup-body h1 {
    font-size: 28px;
    color: black;
}

#editSection .tui-popup-body h2 {
    font-size: 24px;
    border-bottom: 1px solid #cccccc;
    color: black;
}

#editSection .tui-popup-body h3 {
    font-size: 18px;
}

#editSection .tui-popup-body h4 {
    font-size: 16px;
}

#editSection .tui-popup-body h5 {
    font-size: 14px;
}

#editSection .tui-popup-body h6 {
    color: #777777;
    font-size: 14px;
}

button {
    font-size: 12px
}

.tui-editor-defaultUI {
    margin: 15px 0;
}

.te-md-container,
.te-ww-container {
    width: 100%
}

.te-md-container .CodeMirror,
.tui-editor-contents {
    font-size: 16px;
}

.show-only-text:not(.show-toolbar) .te-toolbar-section,
.show-only-text:not(.show-toolbar) .te-mode-switch-section,
.show-only-text:not(.show-toolbar) .te-markdown-tab-section {
    display: none !important;
}

.show-only-text:not(.show-toolbar) .tui-editor {
    border: 0;
}

.show-only-text:not(.show-toolbar) .te-editor {
    padding: 5px;
}

.tui-editor-contents,
.CodeMirror {
    color: #606c71;
}

.tui-editor-contents .task-list-item {
    background-position: 0 5px;
}

.te-md-container .te-preview {
    overflow-x: hidden;
    overflow-y: auto;
}
</style>
 
Editor Sample
<br>
<br>
<br>
<br>

<div class="container">
  <!-- <div class="page-header"></div> -->
  <div class="jumbotron">
    <div id="editTitleSection">
      <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요." value="${boardData.boardTitle}">
    </div>
    <div id="editSection"></div>
    <button id="btnSave" type="button" class="btn btn_dark btn_md w_lg" >저장</button>
  </div>
</div>

<script>

onload = function()
{
  
  var contents = "${boardData.boardContents}";
  
  $('#editSection').tuiEditor('setHtml', contents);
  
  document.getElementById('btnSave').addEventListener('click', function(){
    
    var param = {};
    param.boardSeqNo = "${boardData.boardSeqNo}";
    param.boardTitle = $('#title').val();
    param.boardContents = $('#editSection').tuiEditor('getHtml').replace("\n", "");
    
    console.log($('#editSection').tuiEditor('getHtml'));
    
    $.ajax({
      type: "POST",
      url: "/editorSampleSave.sb",
      data:  JSON.stringify(param),
      success: function(data){
        alert(result.status);
        $('#title').val("");
        $('#editSection').tuiEditor('reset');
      },
      cache: false,
      dataType: "json",
      contentType : 'application/json'
    })
  });
}
 $('#editSection').tuiEditor({
   previewStyle: 'vertical',
   height: 400,
   initialEditType: 'markdown',
   exts: ['scrollFollow', 'colorSyntax', 'tableExtension', 'plantUML', 'chart']
 });


</script>
