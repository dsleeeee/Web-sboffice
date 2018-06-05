<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 환경설정 레이어 --%>
<div id="envDim" class="fullDimmed" style="display:none;"></div>
<div id="envLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w65">
      <p id="popTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <%-- 환경설정 탭 --%>
            <li><a href="javascript:;" id="envSettingTab" class="on"><s:message code="hqBrand.envSetting" /></a></li>
            <%-- 분류관리 탭 --%>
            <li><a href="javascript:;" id="classSettingTab" ><s:message code="hqBrand.classSetting" /></a></li>
          </ul>
        </div>
        <%-- 환경설정 컨텐츠 --%>
        <form id="envForm" name="envForm">
          <div class="oh mt20 mb15">
            <span class="fr"><a href="javascript:;" class="btn_grayS2" id="btnDefault"><s:message code="hqBrand.setting.default.env" /></a></span>
          </div>
          <div class="mt20 sc" style="height:450px;border:0px;" id="contents"></div>
        </form>
      </div>
      <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnSave" ><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

<%-- 환경설정 팝업 오픈 --%>
function openEnvLayer(){
  $("#envDim").show();
  $("#envLayer").show();
  
  var envTitle = "[" + selectedBrand.hqBrandCd + "] "+ selectedBrand.hqBrandNm;
  
  $("#popTitle").text(envTitle);
  
  getConfigList();
}

<%-- 환경설정 테이블 --%>
function getConfigList(){
  var param = {};
  param.hqOfficeCd = selectedBrand.hqOfficeCd;
  param.hqBrandCd  = selectedBrand.hqBrandCd;
  
  var envstGrpCd = ${ccu.getCommCodeExcpAll("048")};
  
  $.postJSON("/store/hq/hqbrand/config/list.sb", param, function(result) {
    
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
            
            envHtml += "      <th>" + list[j].envstCd + "</th>";
            envHtml += "      <td>" + list[j].envstNm + "</td>";
            envHtml += "      <td>";
            
            if(list[j].envstGrpCd == "Y"){
              envHtml += "        <input type='text' name='envstValCd' id='env" + list[j].envstCd + "' >";
            } else {
              envHtml += "        <select name='envstValCd' id='env" + list[j].envstCd + "' />";
            }
            
            envHtml += "        <input type='hidden' name='status'    value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            envHtml += "        <input type='hidden' name='envstCd'   value='"+ list[j].envstCd +"'>";
            envHtml += "        <input type='hidden' name='envstNm'   value='"+ list[j].envstNm +"'>";
            envHtml += "        <input type='hidden' name='envstGrpCd'value='"+ list[j].envstGrpCd +"'>";
            envHtml += "        <input type='hidden' name='defltYn'   value='"+ list[j].defltYn +"'>";
            envHtml += "        <input type='hidden' name='dirctInYn' value='"+ list[j].dirctInYn +"'>";
            envHtml += "        <input type='hidden' name='targtFg'   value='"+ list[j].targtFg +"'>";
            envHtml += "      </td>";
            
            envSub = list[j].envstCd;
            envCnt ++;
            allCnt ++;
            
            if(list[j].existFg == "Y") existCnt++;
            
            if(envCnt % 2 == 0) envHtml += "</tr>";
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
    
    <%-- select option --%>
    for(var i=0; i<envstGrpCd.length; i++) {
      var envstGrp  = envstGrpCd[i];
      for(var j=0; j<list.length; j++){
        if(envstGrp.value == list[j].envstGrpCd && list[j].dirctInYn == "N") {
          if(list[j].defltYn == "Y") {
            $("#env"+list[j].envstCd).append("<option value='"+ list[j].envstValCd +"' selected>" + list[j].envstValNm +  "</option>");
          } else {
            $("#env"+list[j].envstCd).append("<option value='"+ list[j].envstValCd +"'>" + list[j].envstValNm +  "</option>");
          }
        }
      }
    }
    
    <%-- 등록되지 않은 환경값이 있음--%>
    if(allCnt > existCnt) {
      var msg = "<s:message code='hqBrand.no.regist.env'/> "
              + "<s:message code='hqBrand.require.regist.env'/> "
              + "<s:message code='hqBrand.total.env.count' arguments='"+ allCnt +"'/> "
              + "<s:message code='hqBrand.no.regist.env.count' arguments='"+ (allCnt - existCnt) +"'/>"
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

  var paramArr = new Array();
  
  for(var i=0; i<objEnvstCd.length; i++){
    
    var param = {};
    
    param.hqOfficeCd  = selectedBrand.hqOfficeCd;
    param.hqBrandCd   = selectedBrand.hqBrandCd;
    param.status      = objStatus[i].value;
    param.envstCd     = objEnvstCd[i].value;
    param.envstNm     = objEnvstNm[i].value;
    param.envstGrpCd  = objEnvstGrpCd[i].value;
    param.envstVal    = objEnvstValCd[i].value;
    param.dirctInYn   = objDirctInYn[i].value;
    
    paramArr.push(param);
  }
  
  $.postJSONArray("/store/hq/hqbrand/config/save.sb", paramArr, function(result) {
    console.log(result);
    s_alert.pop("<s:message code='msg.save.succ' />");
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
  
});

<%-- 기본값 설정 버튼 클릭 --%>
$("#envLayer #btnDefault").click(function(){
  
  objEnvSetVal  = document.getElementsByName("ENV_SET_VAL");
  objDefaultCd  = document.getElementsByName("DEFAULT_CD"); // 디폴트값을 갖고있어야하네 
  
  var loop_cnt = objEnvSetVal.length;
  for(var i = 0; i < loop_cnt; i++)
  {
      if(objDefaultCd[i].value != '')
      {
          objEnvSetVal[i].value = objDefaultCd[i].value;
      }
  }
});

<%-- 분류관리 탭--%>
$("#envLayer #classSettingTab").click(function(){
  $("#envDim").hide();
  $("#envLayer").hide();
  
  openClsLayer();
});

<%-- 레이어팝업 닫기 --%>
$("#envLayer .btn_close").click(function(){
  $("#envDim").hide();
  $("#envLayer").hide();
});

</script>

