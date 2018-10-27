<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스기능 인증허용대상 설정 레이어 --%>

<div id="funCopyDim" class="fullDimmed" style="display: none;"></div>
<div id="funCopyLayer" class="layer" style="display: none">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w500px">
      <p class="tit"><s:message  code="posFunc.copy.pos.func"/> </p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div>
          <div class="sb-select fl w30">
            <div id="copyPos"></div>
          </div>
          <s:message code="posFunc.copy.comment1" />
        </div>
        <br>
        <div>
          <div class="sb-select fl w30">
            <div id="targetPos"></div>
          </div>
          <s:message code="posFunc.copy.commnet2" />
        </div>

      </div>
      <div class="btnSet fl">
        <span><a href="#" class="btn_blue" id="btnFuncCopy"><s:message code="posFunc.copy" /></a></span>
        <span><a href="#" class="btn_blue" id="btnCancel"><s:message code="cmm.cancel" /></a></span>
      </div>

    </div>
  </div>
</div>

<script>

var copyPosList;
var targetPosList;

<%--  기능복사 레이어 팝업 오픈 --%>
function openCopyFuncLayer(){
  $("#funCopyDim").show();
  $("#funCopyLayer").show();

  if(copyPosList == undefined){
    copyPosList = wcombo.genCommonBox("#copyPos", posList);
    targetPosList = wcombo.genCommonBox("#targetPos", posList);
  }
}


$("#btnFuncCopy").click(function(){

  var copyPosVal = copyPosList.selectedValue;
  var targetPosVal = targetPosList.selectedValue;

  if(copyPosVal == targetPosVal) {
    s_alert.pop("<s:message code='posFunc.unable.copy.pos' />");
    return;
  }

  var param = {};
  param.storeCd = selectedStore.storeCd;
  param.copyPos = copyPosVal;
  param.targetPos = targetPosVal;

  $.postJSONSave("/base/store/posfunc/use/copyPosFunc.sb", param, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc'/>");
        $("#funCopyLayer .btn_close").click();
      },
      function (result) {
        s_alert.pop(result.message);

      }
  );
});


<%-- 레이어팝업 닫기 --%>
$("#funCopyLayer .btn_close, #funCopyLayer #btnCancel").click(function(){
    $("#funCopyDim").hide();
    $("#funCopyLayer").hide();
});

</script>
