<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스기능 인증허용대상 설정 레이어 --%>
<div id="funCopyDim" class="fullDimmed" style="display:none;"></div>
<div id="funCopyLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w500">
      <p class="tit" id="popTitle"><s:message code="posFunc.copy.func" /></p>
      <a href="javascript:;" class="btn_close"></a>
      <%--<div class="con" style="height:210px;">--%>
        <div  style="height:210px;">
        <div class="mt20">
          <table class="tblType01">
            <colgroup>
              <col class="w70" />
            </colgroup>
            <tbody>
            <tr>
              <td>
                <div class="sb-select w150">
                  <div id="copyPos"></div>
                </div>
                <s:message code="posFunc.copy.comment1" />
              </td>
            </tr>
            <tr>
              <td>
                <div class="sb-select w150">
                  <div id="targetPos"></div>
                </div>
                <s:message code="posFunc.copy.commnet2" />
              </td>
            </tr>
            </tbody>
          </table>
        </div>


        <div class="tr mt50">
          <%-- 저장버튼 --%>
          <button type="button" class="btn_grayS2" id="btnSave" style="display: none;">
            <s:message code="cmm.save" />
          </button>
          <%-- 취소버튼 --%>
          <button type="button" class="btn_grayS2" id="btnCancel" style="display: none;">
            <s:message code="cmm.cancel" />
          </button>
        </div>
      </div>
    </div>
  </div>
</div>

<script>

<%--  select box 생성 --%>
var copyPosList = wcombo.genCommonBox("#copyPos", posList);
var targetPosList = wcombo.genCommonBox("#targetPos", posList);

<%--  기능복사 레이어 팝업 오픈 --%>
function openCopyFuncLayer(){
  $("#funCopyDim").show();
  $("#funCopyLayer").show();
}

<%-- 저장버튼 클릭 --%>
$("#funcCopyLayer #btnSave").click(function(){

  var copyPosVal = copyPosList.selectedValue;
  var targetPosVal = targetPosList.selectedValue;

  if(copyPosVal == targetPosVal) {
    s_alert("<s:message code='posFunc.unable.copy.pos' />");
    return;
  }

  var param = {};
  param.copyPos = copyPosVal;
  param.targetPos = targetPosVal;

  console.log(param);

  $.postJSONSave("/base/store/posfunc/use/copyPosFunc.sb", JSON.stringify(param), function(result) {
    console.log(result);
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    s_alert.pop("<s:message code='cmm.saveSucc'/>");
    $("#funcCopyLayer .btn_close").click();
  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });

});

<%-- 취소버튼 클릭 --%>
$("#funcCopyLayer #btnCancel").click(function(){
  $("#funcCopyLayer .btn_close").click();
});

<%-- 레이어팝업 닫기 --%>
$("#funcCopyLayer .btn_close").click(function(){
    $("#funcCopyDim").hide();
    $("#funcCopyLayer").hide();
});

</script>
