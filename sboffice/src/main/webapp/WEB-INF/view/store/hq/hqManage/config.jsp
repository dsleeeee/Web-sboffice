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
            <%-- 상세정보 탭 --%>
            <li><a id="hqInfoTab" href="javascript:;" ><s:message code="hqManage.hqInfo" /></a></li>
            <%-- 환경설정 탭 --%>
            <li><a id="envSettingTab" href="javascript:;" class="on"><s:message code="hqManage.envSetting" /></a></li>
            <%-- 메뉴관리 탭  --%>
            <li><a id="menuSettingTab" href="javascript:;"><s:message code="hqManage.menuSetting" /></a></li>
          </ul>
        </div>
        <%-- 환경설정 컨텐츠 --%>
        <form id="envForm" name="envForm">
          <div class="oh mt20 mb15">
            <span class="fr"><a href="javascript:;" class="btn_grayS2" id="btnDefault"><s:message code="hqManage.setting.default.env" /></a></span>
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
  
  var envTitle = "[" + selectedHq.hqOfficeCd + "] "+ selectedHq.hqOfficeNm;
  
  $("#envLayer #popTitle").text(envTitle);
  
  getConfigList();
}

<%-- 환경설정 테이블 --%>
function getConfigList(){
  var param = {};
  param.hqOfficeCd = selectedHq.hqOfficeCd;
  
  var envstGrpCd = ${ccu.getCommCodeExcpAll("048")};
  
  $.postJSON("/store/hq/hqManage/config/getConfiglist.sb", param, function(result) {
    
    var innerHtml = "";
    
    var envCnt   = 0;
    var allCnt   = 0;
    var existCnt = 0;
    
    var list = result.data.list;
    
    console.log(list);

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
            
            //console.log("list["+j+"].envstNm : "+ list[j].envstNm);
            envHtml += "      <th>" + list[j].envstCd + "</th>";
            envHtml += "      <td>" + list[j].envstNm + "</td>";
            envHtml += "      <td>";
            
            if(list[j].envstGrpCd == "Y"){
                            
              if(list[j].selEnvstVal != null) {
                //console.log("선택선택 : "+ list[j].selEnvstVal);
                //console.log("디폴트디폴트 : "+ list[j].defltYn);
                //console.log("원래 밸류 : "+ list[j].envstCd);
              }
              
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
    for(var i=0; i<envstGrpCd.length; i++) {
      var envstGrp  = envstGrpCd[i];
      for(var j=0; j<list.length; j++){

        if(envstGrp.value == list[j].envstGrpCd && list[j].dirctInYn == "N") {
          // 선택된 값이 우선
          if(list[j].selEnvstVal != null && (list[j].selEnvstVal == list[j].envstValCd) ) {
            $("#env"+list[j].envstCd).append("<option value='"+ list[j].envstValCd +"' selected>" + list[j].envstValNm +  "</option>");
          } 
          // 그 다음이 디폴트 값
          else if(list[j].defltYn == "Y") {
            $("#env"+list[j].envstCd).append("<option value='"+ list[j].envstValCd +"' selected>" + list[j].envstValNm +  "</option>");
          }
          else {
            $("#env"+list[j].envstCd).append("<option value='"+ list[j].envstValCd +"'>" + list[j].envstValNm +  "</option>");
          }
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

  var paramArr = new Array();
  
  for(var i=0; i<objEnvstCd.length; i++){
    
    var param = {};
    
    param.hqOfficeCd  = selectedHq.hqOfficeCd;
    param.status      = objStatus[i].value;
    param.envstCd     = objEnvstCd[i].value;
    param.envstNm     = objEnvstNm[i].value;
    param.envstGrpCd  = objEnvstGrpCd[i].value;
    param.envstVal    = objEnvstValCd[i].value;
    param.dirctInYn   = objDirctInYn[i].value;
    
    paramArr.push(param);
  }
  
  $.postJSONArray("/store/hq/hqManage/config/save.sb", paramArr, function(result) {
    console.log(result);
    s_alert.pop("<s:message code='cmm.saveSucc' />");
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
  
});

<%-- 기본값 설정 버튼 클릭 --%>
$("#envLayer #btnDefault").click(function(){
  
  objDefaultCd  = document.getElementsByName("defltYn"); // 디폴트 유무 
  
  var loop_cnt = objEnvSetVal.length;
  for(var i = 0; i < loop_cnt; i++)
  {
      if(objDefaultCd[i].value != '')
      {
          objEnvSetVal[i].value = objDefaultCd[i].value;
      }
  }
});

<%-- 상세정보 탭 클릭 --%>
$("#envLayer #hqInfoTab").click(function(){
  /* 
  if(!$("#viewArea").is(":visible")) {
    var msg = "<s:message code='hqManage.confirm.editmode.quit'/>";
    s_alert.popConf(msg, function(){
      showMaster();
    });
  } else {
  */
    showMaster();
  //}
});

<%-- 메뉴권한 탭 클릭 --%>
$("#envLayer #menuSettingTab").click(function(e){
  /* 
  if(!$("#viewArea").is(":visible")) {
    var msg = "<s:message code='hqManage.confirm.editmode.quit'/>";
    s_alert.popConf(msg, function(){
      showMenuAuth();
    });
  } else {
  */
    showMenuAuth();
  //}
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

