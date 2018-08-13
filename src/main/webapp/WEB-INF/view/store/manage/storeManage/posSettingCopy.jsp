<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스 환경설정 포스명칭설정 레이어팝업--%>
<div id="posCopyDim" class="fullDimmed" style="display:none;"></div>
<div id="posCopyLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w600">
      <p class="tit"><s:message code="storeManage.copy.posSetting" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div class="bgGray s14 mt20 pd30 lh30 tc">
          <s:message code="storeManage.posNm" /> :
          <%-- 대상 포스  --%>
          <select id="copyPos" name="copyPos"></select>
          <s:message code="storeManage.copy.posSetting.comment1" />
          <br />
          <s:message code="storeManage.posNm" /> :
          <%-- 타겟 포스  --%>
          <input type="text" id="targetPos" class="sb-input w20" maxlength="2">
          <s:message code="storeManage.copy.posSetting.comment2" />
        </div>
      </div>
      <%--복사하기 버튼 --%>
      <div class="btnSet">
        <span><a href="javascript:;" id="btnCopyPosSetting" class="btn_blue"><s:message code="storeManage.copy" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

<%-- 매장환경조회 팝업 오픈 --%>
function openPosCopyLayer() {

  $("#posCopyDim").show();
  $("#posCopyLayer").show();

  var param = {};
  param.storeCd = selectedStore.storeCd;

  $.postJSON("/store/manage/storeManage/storeManage/getPosList.sb", param, function(result) {
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    var posList = result.data.list.posList;
    for(var i=0; i<posList.length; i++) {
      if(i == 0) {
        $("#copyPos").append("<option value='"+posList[i].posNo+"' selected >"  + posList[i].posCdNm +  "</option>")
      } else {
        $("#copyPos").append("<option value='"+posList[i].posNo+"'  >"  + posList[i].posCdNm +  "</option>")
      }
    }
  }
  ,function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 복사 버튼 클릭 --%>
$("#posCopyLayer #btnCopyPosSetting").click(function(){

  var originalPos = $("#copyPos option:selected").val();
  var targetPos = $("#targetPos").val();

  if(targetPos == null || targetPos == ''){
    s_alert.pop("<s:message code='storeManage.require.target.posNo' />");
    return;
  }
  <%-- 기존 포스번호와 타겟 포스번호는 서로 다른 값이 되어야 합니다. --%>
  if(parseInt(originalPos) ==  parseInt(targetPos)) {
    s_alert.pop("<s:message code='storeManage.require.diff.posNo' />");
    return;
  }

  var param = {};

  param.storeCd     = selectedStore.storeCd;
  param.posNo       = originalPos;
  param.targetPosNo = targetPos;

  $.postJSON("/store/manage/storeManage/storeManage/copyPosSetting.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    $("#posCopyLayer .btn_close").click();
  },
  function(result) {
    s_alert.pop(result.message);
  });
});

<%-- 레이어 팝업 닫기 --%>
$("#posCopyLayer .btn_close, #posCopyLayer #btnClosePosCopy").click(function(){
  $("#posCopyDim").hide();
  $("#posCopyLayer").hide();
});

</script>
