<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 환경설정 레이어 --%>
<div id="envDim" class="fullDimmed" style="display:none;"></div>
<div id="envLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w80">
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close" id="envClose"></a>
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
            <input type="text" class="sb-input w200px" id="srchConfig"/>
            <%-- 조회버튼 --%>
            <button type="button" class="btn_skyblue ml5" id="btnEnvst" onclick="getConfigList()">
              <s:message code="cmm.search"/></button>

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

  <div id="divDimmed" class="fullDimmed" style="display: none;"></div>
  <div id="divPopup" class="layer" style="display: none;">

    <div class="layer_inner" >
      <div class="title w500px">
        <p id="envTitle" class="tit"></p>
        <a href="#" class="btn_close" id="envRemarkClose"></a>
        <div class="con" id="envRemark">
        </div>
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

<%-- 환경설정 팝업 오픈(본사가 기초관리 - 내정보관리 - 내정보관리에서 오픈) --%>
var hqOfficeCd = "";
function openEnvSettingLayer(cd, nm){
  var envTitle = "[" + cd + "] "+ nm;
  $(".tabType1").hide();
  $("#envLayer").show();
  hqOfficeCd = cd;
  getConfigList();
}

<%-- 환경설정 테이블 --%>
function getConfigList(){
  var param = {};
  if($(".tabType1").is(':visible')){
    param.hqOfficeCd = selectedHq.hqOfficeCd;
  } else {
    param.hqOfficeCd = hqOfficeCd;
  }
  param.envst = $("#srchConfig").val();

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

            if(list[j].remark !== null && list[j].remark !== "" && list[j].remark !== undefined){
              console.log(list[j].envstCd + " : " + list[j].remark);
              envHtml += '  <th style=\"color:blue;\"><a href=\"#\" onclick=\"envRemarkPop(\'' + list[j].envstCd + '\')\">' + list[j].envstCd + (list[j].existFg === "N" ? " <em class=\"imp\">*</em> " : "") + '</a></th>';
              envHtml += '  <td style=\"color:blue;\"><a href=\"#\" onclick=\"envRemarkPop(\'' + list[j].envstCd + '\')\">' + list[j].envstNm + '</a></td>';
            } else {
              envHtml += "      <th>" + list[j].envstCd + (list[j].existFg === "N" ? "<em><span style=\"color:#ff0000;\">*</span></em> " : "") + '</th>';
              envHtml += "      <td>" + list[j].envstNm + "</td>";
            }
            envHtml += "      <td>";

            if(list[j].dirctInYn == "Y"){
              envHtml += "        <input type='text' class='sb-input' name='envstValCd' id='env" + list[j].envstCd + "' >";
            } else {
              envHtml += "        <select class='sb-select' name='envstValCd' id='env" + list[j].envstCd + "' />";
            }

            envHtml += "        <input type='hidden' name='status'      id='status"+ list[j].envstCd      + "' value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            envHtml += "        <input type='hidden' name='envstCd'     id='envstCd"+ list[j].envstCd     + "' value='"+ list[j].envstCd +"'>";
            envHtml += "        <input type='hidden' name='envstNm'     id='envstNm"+ list[j].envstCd     + "' value='"+ list[j].envstNm +"'>";
            envHtml += "        <input type='hidden' name='envstGrpCd'  id='envstGrpCd"+ list[j].envstCd  + "' value='"+ list[j].envstGrpCd +"'>";
            envHtml += "        <input type='hidden' name='defltYn'     id='defltYn"+ list[j].envstCd     + "' value='"+ list[j].defltYn +"'>";
            envHtml += "        <input type='hidden' name='dirctInYn'   id='dirctInYn"+ list[j].envstCd   + "' value='"+ list[j].dirctInYn +"'>";
            envHtml += "        <input type='hidden' name='oldEnvstVal' id='oldEnvstVal"+ list[j].envstCd + "' value='"+ list[j].selEnvstVal +"'>";
            envHtml += "        <input type='hidden' name='targtFg'     id='targtFg"+ list[j].envstCd     + "' value='"+ list[j].targtFg +"'>";
            envHtml += "        <input type='hidden' name='remark'      id='remark"+ list[j].envstCd      + "' value='"+ list[j].remark +"'>";
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

// 매장환경설정 비고설명 팝업
function envRemarkPop(envstCd) {

  var params    = {};
  params.envstCd = $("#" + "envstCd" + envstCd).val();
  params.envstNm = $("#" + "envstNm" + envstCd).val();
  params.remark = $("#" + "remark" + envstCd).val();

  // 선택한 환경설정코드 및 명칭 셋팅
  $("#envTitle").text("[" + params.envstCd + "] " + params.envstNm);
  // 환경설정 설명
  $("#envRemark").html(params.remark);

  $("#divDimmed").css('display', '');
  $("#divPopup").css('display', '');

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
      var x = arrChg[i];
      var param = {};

      if($(".tabType1").is(':visible')){ 
        param.hqOfficeCd = selectedHq.hqOfficeCd;
      } else {
        param.hqOfficeCd = hqOfficeCd;
      }
      param.status      = objStatus[x].value;
      param.envstCd     = objEnvstCd[x].value;
      param.envstNm     = objEnvstNm[x].value;
      param.envstGrpCd  = objEnvstGrpCd[x].value;
      param.envstVal    = objEnvstValCd[x].value;
      param.dirctInYn   = objDirctInYn[x].value;
      param.targtFg     = objTargtFg[x].value;

      params.push(param);
    }

    $.postJSONArray("/store/hq/hqManage/config/save.sb", params, function(result) {
              //console.log(result);
        s_alert.pop("<s:message code='cmm.saveSucc' />");

        // 재조회
        getConfigList();
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
$("#envLayer #envClose").click(function(){

  $("#srchConfig").val('');
  hideEnvSet();
});

// 설명 팝업 닫기
$("#envLayer #divPopup #envRemarkClose").click(function(){

  $("#divDimmed").hide();
  $("#divPopup").hide();
});

function hideEnvSet(){
  $("#envDim").hide();
  $("#envLayer").hide();
}

</script>

