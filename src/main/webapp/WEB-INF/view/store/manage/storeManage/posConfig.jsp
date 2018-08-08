<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="posConfigArea" style="display:none;">
  <div class="s12 tl oh">
    <%-- 포스명칭 --%>
    <span class="bk fl lh30 mr10">
      <s:message code="storeManage.posNm" /> :
    </span>
    <div class="sb-select dkbr fl w150 mr10">
      <select id="sPosNm" class="wj-content"></select>
    </div>

    <%-- 기본값으로 설정 --%>
    <a href="javascript:;" id="btnDefault" class="btn_grayS"><s:message code="storeManage.setting.default.env" /></a>
    <%-- 저장 --%>
    <%-- <a href="javascript:;" id="btnSavePos" class="btn_grayS"><s:message code="cmm.save" /></a> --%>
  </div>

  <div class="mt20 mb20">
    <%-- 테이블 그룹설정 --%>
    <button id="btnSetTabGrp" type="button" class="btn_skyblue"><s:message code="storeManage.setting.tableGroup" /></button>
    <%-- 포스 명칭설정 --%>
    <button id="btnSetPosNm" type="button" class="btn_skyblue"><s:message code="storeManage.setting.posName" /></button>
    <%-- 포스 설정복사 --%>
    <button id="btnCopyPosSetting" type="button" class="btn_skyblue"><s:message code="storeManage.copy.posSetting" /></button>
    <%-- 삭제 --%>
    <button id="btnDeletePos" type="button" class="btn_skyblue"><s:message code="cmm.delete" /></button>
  </div>

  <%-- 포스 환경설정 컨텐츠 --%>
  <div id="posConfigContent" class="mt20"></div>

  <div class="tc mt20">
    <%-- 저장 --%>
    <button type="button" id="btnSavePos"class="btn_blue" ><s:message code="cmm.save" /></button>
  </div>
</div>

<script>

var selectedEnvstFg = "";

<%-- 매장환경, 외식환경의 환경값 조회 --%>
function showPosConfigLayout(envstFg){

  selectedEnvstFg = envstFg;

  $("#posConfigContent").empty();
  $("#posConfigContent").show();
  $("#posConfigArea").show();

  var param = {};

  param.hqOfficeCd  = selectedStore.hqOfficeCd;
//  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;

  <%-- 포스목록 조회 --%>
  <%--
  $.postJSON("/store/manage/storeManage/storeManage/getPosList.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }

    var posList = result.data.list.posList;

    $("#sPosNm").empty();

    for(var i=0; i<posList.length; i++) {
      if(i==0){
        $("#sPosNm").append("<option value='"+ posList[i].posNo +"' selected> " + posList[i].posCdNm +  "</option>");
      } else {
        $("#sPosNm").append("<option value='"+ posList[i].posNo +"' > " + posList[i].posCdNm +  "</option>");
      }
    }
  }
  ,function(){
    s_alert.pop("Ajax Fail");
  });
  --%>

  if(posList != null) {

    console.log("---- posList 2");
    console.log(posList);

    $("#sPosNm").empty();

    for(var i=0; i<posList.length; i++) {
      if(i==0){
        $("#sPosNm").append("<option value='"+ posList[i].posNo +"' selected> " + posList[i].posCdNm +  "</option>");
      } else {
        $("#sPosNm").append("<option value='"+ posList[i].posNo +"' > " + posList[i].posCdNm +  "</option>");
      }
    }

  }

  getPosInfo();
}

<%-- 포스 변경시 --%>
$("#sPosNm").on("change", function(){
  getPosInfo();
});

<%-- 포스환경정보 조회 --%>
function getPosInfo(){

  var param = {};

  param.hqOfficeCd  = selectedStore.hqOfficeCd;
//  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;
  param.posNo       = $("#sPosNm option:selected").val();
  param.envstFg     = selectedEnvstFg;

  var envstGrpCd = ${ccu.getCommCodeExcpAll("048")};

  $.postJSON("/store/manage/storeManage/storeManage/getPosConfigList.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }

    // 환경설정
    var innerHtml = "";

    var envCnt   = 0;
    var allCnt   = 0; // 전체 환경값 갯수
    var existCnt = 0; // 현재 등록된 환경값 갯수

    var list = result.data.list.envGroupList;

    for(var i=0; i<envstGrpCd.length; i++) {

      var storeEnvCnt    = 0;
      var storeEnvHtml   = "";
      var storeEnvstGrp  = envstGrpCd[i];

      storeEnvHtml += "<h3 class='h3_tbl2 lh30'>"+storeEnvstGrp.name+" <button class='open'></button></h3>";
      storeEnvHtml += "<table class='searchTbl'>";
      storeEnvHtml += "  <colgroup>";
      storeEnvHtml += "    <col class='w5' />";
      storeEnvHtml += "    <col class='w25' />";
      storeEnvHtml += "    <col class='w20' />";
      storeEnvHtml += "    <col class='w5' />";
      storeEnvHtml += "    <col class='w25' />";
      storeEnvHtml += "    <col class='w20' />";
      storeEnvHtml += "  </colgroup>";
      storeEnvHtml += "<tbody>";

      var b_env = "";

      for(var j=0; j<list.length; j++) {
        if(storeEnvstGrp.value == list[j].envstGrpCd) {


          if(b_env == "" || b_env != list[j].envstCd ){

            if(storeEnvCnt == 0 || storeEnvCnt % 2 == 0) storeEnvHtml += "<tr>";

            storeEnvHtml += "  <th class='tc'>" + list[j].envstCd + "</th>";
            storeEnvHtml += "  <td>" + list[j].envstNm + "</td>";
            storeEnvHtml += "  <td>";

            if(list[j].dirctInYn == "Y"){ // 직접입력
              storeEnvHtml += "    <input type='text' name='posEnvstValCd' id='env" + list[j].envstCd + "' class='sb-input w100'>";
            } else {  // 값 선택
              storeEnvHtml += "    <select name='posEnvstValCd' id='env" + list[j].envstCd + "'/>";
            }

            storeEnvHtml += "    <input type='hidden' name='posEnvstatus'    value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            storeEnvHtml += "    <input type='hidden' name='posEnvstCd'   value='"+ list[j].envstCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='posEnvstNm'   value='"+ list[j].envstNm +"'>";
            storeEnvHtml += "    <input type='hidden' name='posEnvstGrpCd'value='"+ list[j].envstGrpCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='posEnvDefltYn'   value='"+ list[j].defltYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='posEnvDirctInYn' value='"+ list[j].dirctInYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='posTargtFg'   value='"+ list[j].targtFg +"'>";
            storeEnvHtml += "    <input type='hidden' name='posOldEnvstVal'   value='"+ list[j].selEnvstVal +"'>";
            storeEnvHtml += "  </td>";

            b_env = list[j].envstCd;
            storeEnvCnt ++;
            allCnt ++;

            if(list[j].existFg == "Y") existCnt++;

            if(list[j].envstCdCnt == storeEnvCnt && (storeEnvCnt % 2 == 1)) {
              storeEnvHtml += "  <td class='tc'></td>";
              storeEnvHtml += "  <td></td>";
              storeEnvHtml += "  <td></td>";
              storeEnvHtml += "</tr>";
            } else if(storeEnvCnt % 2 == 0) {
              storeEnvHtml += "</tr>";
            }
          }
        }
      }

      storeEnvHtml += "  </tbody>";
      storeEnvHtml += "</table>";
      storeEnvHtml += "</div>";
      storeEnvHtml += "<br>";

      if(storeEnvCnt > 0) innerHtml += storeEnvHtml;
    }

    $("#posConfigContent").html(innerHtml);

    var envstCd = "";
    var sOption = false;


    for(var i=0; i<list.length; i++){
      if(list[i].dirctInYn == "N") {

        $("#env"+list[i].envstCd).append("<option value='"+ list[i].envstValCd +"' >" + list[i].envstValNm +  "</option>");

        if(i == 0 || envstCd != list[i].envstCd ) {
          envstCd = list[i].envstCd;
          if(list[i].selEnvstVal == list[i].envstValCd){
            sOption = true;
            $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
          }
          else{
            sOption = false;
            $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
          }
        }else if(list[i].selEnvstVal == list[i].envstValCd){
          sOption = true;
          $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
        }else if(sOption == false && list[i].defltYn == "Y") {
          $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
        }

        if(list[i].defltYn == "Y") {
          $("#env"+list[i].envstCd).attr("defaultVal", list[i].envstValCd);
        }

      }
    }

    <%-- 등록되지 않은 환경값이 있음--%>
    if(allCnt > existCnt) {
      var msg = "<s:message code='storeManage.no.regist.env'/> "
              + "<s:message code='storeManage.require.regist.env'/> "
              + "<s:message code='storeManage.total.env.count' arguments='"+ allCnt +"'/> "
              + "<s:message code='storeManage.no.regist.env.count' arguments='"+ (allCnt - existCnt) +"'/>"
              ;

      s_alert.pop(msg);
    }
  }
  ,function(){
      s_alert.pop("Ajax Fail");
  });
}


$("#posConfigArea #btnSavePos").click(function(){

  var posObjStatus       = document.getElementsByName("posEnvstatus");
  var posObjEnvstCd      = document.getElementsByName("posEnvstCd");
  var posObjEnvstNm      = document.getElementsByName("posEnvstNm");
  var posObjEnvstGrpCd   = document.getElementsByName("posEnvstGrpCd");
  var posObjDefault      = document.getElementsByName("posEnvDefltYn");
  var posObjEnvstValCd   = document.getElementsByName("posEnvstValCd");
  var posObjDirctInYn    = document.getElementsByName("posEnvDirctInYn");
  var posObjOldEnvstVal  = document.getElementsByName("posOldEnvstVal");

  <%-- 포스형태[204]가 외식업-후불제인 경우에만 후불제를 선택할 수 있습니다. --%>
  var env101 = $("#env101").val();
  var env204 = $("#env204").val();
  if(env101 == "03") {
    if(env204 != "0") {
      s_alert.pop("<s:message code='storeManage.only.select.postPay' />");
      return;
    }
  }

  var chngCnt  = 0; // 변경된 건수
  var paramArr = new Array();

  for(var i=0; i<posObjEnvstCd.length; i++){
    if(posObjOldEnvstVal[i].value != $("#posConfigContent #env"+posObjEnvstCd[i].value).val()) {
      chngCnt ++;
    }
  }

  if(chngCnt == 0 ){
    s_alert.pop("<s:message code='cmm.not.modify' />");
    return;
  }

  for(var i=0; i<posObjEnvstCd.length; i++){
    var param = {};

    param.hqOfficeCd  = selectedStore.hqOfficeCd;
    param.storeCd     = selectedStore.storeCd;
    param.status      = posObjStatus[i].value;
    param.posNo       = $("#sPosNm option:selected").val();
    param.envstCd     = posObjEnvstCd[i].value;
    param.envstNm     = posObjEnvstNm[i].value;
    param.envstGrpCd  = posObjEnvstGrpCd[i].value;
    param.envstVal    = posObjEnvstValCd[i].value;
    param.dirctInYn   = posObjDirctInYn[i].value;
    param.useYn       = "Y";

    paramArr.push(param);
  }

  $.postJSONArray("/store/manage/storeManage/storeManage/savePosConfig.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    // 저장 완료후에 재로딩
    showPosConfigLayout(selectedEnvstFg);
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });

});

<%-- 기본값으로 설정 버튼 클릭 --%>
$(document).on("click", "#posConfigArea #btnDefault", function(){

  var posObjEnvstCd      = document.getElementsByName("posEnvstCd");
  var posObjEnvstNm      = document.getElementsByName("posEnvstNm");
  var posObjDirctInYn    = document.getElementsByName("poeEnvDirctInYn");

  for(var i=0; i<posObjEnvstCd.length; i++){

    var defaultVal = $("#posConfigContent #env"+posObjEnvstCd[i].value).attr("posEnvDefaultVal");

    if(posObjDirctInYn[i].value == "Y") {
      $("#env"+posObjEnvstCd[i].value).val(defaultVal);
    } else {
      $("#env"+posObjEnvstCd[i].value).val(defaultVal).prop("selected", true);
    }
  }
});

<%-- 테이블 그룹설정 버튼 클릭 --%>
$("#btnSetTabGrp").click(function(){
  openTabGrpLayer();
});

<%-- 포스 명칭설정 버튼 클릭--%>
$("#btnSetPosNm").click(function(){
  openSetPosNmLayer();
});

<%-- 포스 설정복사 버튼 클릭 --%>
$("#btnCopyPosSetting").click(function(){
  openPosCopyLayer();
});

<%-- 삭제 버튼 클릭 --%>
$("#btnDeletePos").click(function(){

  <%-- 01번 포스는 삭제 할 수 없습니다. --%>
  if($("#sPosNm option:selected").val() == "01") {
    s_alert.pop("<s:message code='storeManage.unable.delete.defaultPos' />");
    return;
  }

  var param = {};
  param.hqOfficeCd  = selectedStore.hqOfficeCd;
//  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;
  param.posNo       = $("#sPosNm option:selected").val();

  $.postJSON("/store/manage/storeManage/storeManage/deletePos.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }

    s_alert.pop("<s:message code='cmm.delSucc' />");
    showPosConfigLayout(selectedEnvstFg);
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
});

<%-- 매장환경, 외식환경 레이아웃 보이지 않기 --%>
function hidePosConfigLayout() {
  //$("#posConfigContent").empty();
  $("#posConfigArea").hide();
  $("#posConfigContent").hide();
}

</script>
