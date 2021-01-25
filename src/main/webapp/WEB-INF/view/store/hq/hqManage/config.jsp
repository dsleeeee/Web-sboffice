<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 환경설정 레이어 --%>
<div id="envDim" class="fullDimmed" style="display:none;"></div>
<div id="envLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w65">
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <%-- 상세정보 탭 --%>
            <li><a id="hqInfoTab" href="#" ><s:message code="hqManage.hqInfo" /></a></li>
            <%-- 환경설정 탭 --%>
            <li><a id="envSettingTab" href="#" class="on"><s:message code="hqManage.envSetting" /></a></li>
            <%-- 메뉴관리 탭  --%>
            <li><a id="menuSettingTab" href="#"><s:message code="hqManage.menuSetting" /></a></li>
          </ul>
        </div>
        <%-- 환경설정 컨텐츠 --%>
        <form id="envForm" name="envForm">
          <div class="oh mt20 mb15">
            <span class="fr"><a href="#" class="btn_grayS2" id="btnDefault"><s:message code="hqManage.setting.default.env" /></a></span>
          </div>
          <div class="mt20 sc" style="height:450px;border:0px;" id="contents"></div>
        </form>
      </div>
      <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue" id="btnSave" ><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

<%-- 환경설정 팝업 오픈 --%>
function openEnvLayer(){

  var envTitle = "[" + selectedHq.hqOfficeCd + "] "+ selectedHq.hqOfficeNm;

  $("#envLayer #popTitle").text(envTitle);

  getConfigList();
}

<%-- 환경설정 테이블 --%>
function getConfigList(){
  var param = {};
  param.hqOfficeCd = selectedHq.hqOfficeCd;

  var envstGrpCd = ${ccu.getCommCodeExcpAll("004")};

  $.postJSON("/store/hq/hqManage/config/getConfiglist.sb", param, function(result) {

    var innerHtml = "";

    var envCnt   = 0;
    var allCnt   = 0;
    var existCnt = 0;

    var list = result.data.list;
    for(var i=0; i<envstGrpCd.length; i++) {

      var envCnt    = 0;
      var envHtml   = "";
      var envstGrp  = envstGrpCd[i];

      envHtml += envstGrp.name;

      envHtml += "<div class='mt20 sc'>";
      envHtml += "<table class='tblType01'>";
      envHtml += "  <colgroup>";
      envHtml += "    <col class='w5' />";
      envHtml += "    <col class='w25' />";
      envHtml += "    <col class='w25' />";
      envHtml += "    <col class='w5' />";
      envHtml += "    <col class='w25' />";
      envHtml += "    <col class='w25' />";
      envHtml += "  </colgroup>";
      envHtml += "  <tbody id='evnTable'>";

      var envSub = "";

      for(var j=0; j<list.length; j++){
        if(envstGrp.value == list[j].envstGrpCd) {

          if(envSub == "" || envSub != list[j].envstCd) {

            if(envCnt == 0 || envCnt % 2 == 0) envHtml += "<tr>";

            envHtml += "      <th>" + list[j].envstCd + (list[j].existFg === "N" ? "<em><span style=\"color:#ff0000;\">*</span></em> " : "") + '</th>';
            envHtml += "      <td>" + list[j].envstNm + "</td>";
            envHtml += "      <td>";

            if(list[j].dirctInYn == "Y"){
              envHtml += "        <input type='text' class='sb-input' name='envstValCd' id='env" + list[j].envstCd + "' >";
            } else {
              envHtml += "        <select class='sb-select' name='envstValCd' id='env" + list[j].envstCd + "' />";
            }

            envHtml += "        <input type='hidden' name='status'    value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            envHtml += "        <input type='hidden' name='envstCd'   value='"+ list[j].envstCd +"'>";
            envHtml += "        <input type='hidden' name='envstNm'   value='"+ list[j].envstNm +"'>";
            envHtml += "        <input type='hidden' name='envstGrpCd'value='"+ list[j].envstGrpCd +"'>";
            envHtml += "        <input type='hidden' name='defltYn'   value='"+ list[j].defltYn +"'>";
            envHtml += "        <input type='hidden' name='dirctInYn' value='"+ list[j].dirctInYn +"'>";
            envHtml += "        <input type='hidden' name='oldEnvstVal' value='"+ list[j].selEnvstVal +"'>";
            envHtml += "        <input type='hidden' name='targtFg'   value='"+ list[j].targtFg +"'>";
            envHtml += "      </td>";

            envSub = list[j].envstCd;
            envCnt ++;
            allCnt ++;

            if(list[j].existFg == "Y") existCnt++;

            if(list[j].envstCdCnt == envCnt && (envCnt % 2 == 1) ) {
              envHtml += "      <td></td>";
              envHtml += "      <td></td>";
              envHtml += "      <td></td>";
              envHtml += "  </tr>";
            } else if(envCnt % 2 == 0) {
              envHtml += "  </tr>";
            }
          }
        }
      }

      envHtml += "  </tbody>";
      envHtml += "</table>";
      envHtml += "</div>";
      envHtml += "<br>";

      if(envCnt > 0) innerHtml += envHtml;
    }

    $("#contents").html(innerHtml);

    <%-- select box option --%>
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

      } else {
        if(list[i].selEnvstVal === "" || list[i].selEnvstVal  === null){
          if(list[i].remark !== "" && list[i].remark  !== null){ // 직접입력값 중, 기본값(Remark)이 있으면 셋팅, 없으면 * 로 표시
            $("#env" + list[i].envstCd).val(list[i].remark);
          }else{
            $("#env" + list[i].envstCd).val("*");
          }
        }else{
          $("#env"+list[i].envstCd).val(list[i].selEnvstVal);
        }

        // '기본값으로 셋팅' 클릭시 자동으로 직접입력 기본값 셋팅
        if(list[i].remark !== "" && list[i].remark  !== null){
          $("#env"+list[i].envstCd).attr("defaultVal", list[i].remark);
        }else{
          $("#env"+list[i].envstCd).attr("defaultVal", "*");
        }
      }
    }

    <%-- 등록되지 않은 환경값이 있음--%>
    if(allCnt > existCnt) {
      var msg = "<s:message code='hqManage.no.regist.env'/> "
              + "<s:message code='hqManage.require.regist.env'/> "
              + "<s:message code='hqManage.total.env.count' arguments='"+ allCnt +"'/> "
              + "<s:message code='hqManage.no.regist.env.count' arguments='"+ (allCnt - existCnt) +"'/>"
              ;

      s_alert.pop(msg);
    }
  });
}

<%-- 저장 버튼 클릭 --%>
$("#envLayer #btnSave").click(function(){

  var objStatus = document.getElementsByName("status");
  var objEnvstCd = document.getElementsByName("envstCd");
  var objEnvstNm = document.getElementsByName("envstNm");
  var objEnvstGrpCd = document.getElementsByName("envstGrpCd");
  var objDefault = document.getElementsByName("default");
  var objEnvstValCd = document.getElementsByName("envstValCd");
  var objDirctInYn = document.getElementsByName("dirctInYn");
  var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");
  var objTargtFg = document.getElementsByName("targtFg");

  var chngCnt  = 0; // 변경된 건수
  var arrChg = []; //  변경된 환경변수 배열 Key 값
  var params = [];

  // var paramArr = [];

  for(var i=0; i<objEnvstCd.length; i++) {

    if (objDirctInYn[i].value === "Y" && objEnvstValCd[i].value === "") {
      var msgStr = "<s:message code='hqManage.envSetting' /> "
              + "[ " + objEnvstCd[i].value + "] " + objEnvstNm[i].value
              + " <s:message code='hqManage.require.regist.inputEnv' /> ";

      s_alert.pop(msgStr);
      return false;
    }

    if (objOldEnvstVal[i].value !== $("#env" + objEnvstCd[i].value).val()) {
      arrChg.push(i);
      chngCnt++;
    }
  }

  if (chngCnt === 0) {
    s_alert.pop(messages["cmm.not.modify"]);
    return false;
  }

  s_alert.popConf( messages["cmm.choo.save"], function() {

    var hqScope = agrid.getScope('hqManageCtrl');

    /*
    var param = {};

    param.hqOfficeCd  = selectedHq.hqOfficeCd;
    param.status      = objStatus[i].value;
    param.envstCd     = objEnvstCd[i].value;
    param.envstNm     = objEnvstNm[i].value;
    param.envstGrpCd  = objEnvstGrpCd[i].value;
    param.envstVal    = objEnvstValCd[i].value;
    param.dirctInYn   = objDirctInYn[i].value;
    param.targtFg     = objTargtFg[i].value;

    paramArr.push(param);
    */

    for(var i=0; i<arrChg.length; i++) {

      var param = {};
      param.hqOfficeCd  = selectedHq.hqOfficeCd;
      param.status      = objStatus[i].value;
      param.envstCd     = objEnvstCd[i].value;
      param.envstNm     = objEnvstNm[i].value;
      param.envstGrpCd  = objEnvstGrpCd[i].value;
      param.envstVal    = objEnvstValCd[i].value;
      param.dirctInYn   = objDirctInYn[i].value;
      param.targtFg     = objTargtFg[i].value;

      params.push(param);
    }

    $.postJSONArray("/store/hq/hqManage/config/save.sb", params, function(result) {
              //console.log(result);
        s_alert.pop("<s:message code='cmm.saveSucc' />");
      },
      function(result) {
        s_alert.pop(result.message);
      });
    });
});

<%-- 기본값 설정 버튼 클릭 --%>
$("#envLayer #btnDefault").click(function(){

  var objEnvstCd      = document.getElementsByName("envstCd");
  var objDirctInYn    = document.getElementsByName("dirctInYn");
  var objDefaultCd    = document.getElementsByName("defltYn");

  for(var i=0; i<objEnvstCd.length; i++){

    var defaultVal = $("#env"+objEnvstCd[i].value).attr("defaultVal");

    if(objDirctInYn[i].value == "Y") {
      $("#env"+objEnvstCd[i].value).val(defaultVal);
    } else {
      $("#env"+objEnvstCd[i].value).val(defaultVal).prop("selected", true);
    }
  }

});

<%-- 상세정보 탭 클릭 --%>
$("#envLayer #hqInfoTab").click(function(){
  showMaster();
});

<%-- 메뉴권한 탭 클릭 --%>
$("#envLayer #menuSettingTab").click(function(e){
  showMenuAuth();
});

<%-- 환경설정 화면 보여줌--%>
function showEnvSet(){

  hideMaster();
  hideMenuAuth();

  $("#envLayer").show();
  $("#envDim").show();

  openEnvLayer();
}

<%-- 환경설정 화면 닫기 --%>
$("#envLayer .btn_close").click(function(){
  hideEnvSet();
});

function hideEnvSet(){
  $("#envDim").hide();
  $("#envLayer").hide();
}

</script>

