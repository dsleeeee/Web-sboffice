<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="storeConfigArea">
  <div id="storeConfigContent"></div>
  
  <div class="tc mt20">
    <%-- 저장 --%>
    <button type="button" id="btnSaveStore"class="btn_blue" ><s:message code="cmm.save" /></button>
  </div>
</div>

<script>

var selectedEnvstFg = "";

<%-- 매장환경, 외식환경의 환경값 조회 --%>
function showStoreConfigLayout(envstFg){
  
  selectedEnvstFg = envstFg;
  
  $("#storeConfigContent").empty();
  $("#storeConfigContent").show();
  $("#storeConfigArea").show();
  
  var param = {};
  param.hqOfficeCd  = selectedStore.hqOfficeCd;
//  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;
  param.envstFg     = envstFg;

  var envstGrpCd = ${ccu.getCommCodeExcpAll("048")};
  
  $.postJSON("/store/manage/storeManage/storeManage/getStoreConfigList.sb", param, function(result) {
    
    var innerHtml = "";

    var envCnt   = 0;
    var allCnt   = 0; // 전체 환경값 갯수
    var existCnt = 0; // 현재 등록된 환경값 갯수
    
    var list = result.data.list;
    
    for(var i=0; i<envstGrpCd.length; i++) {
      
      var storeEnvCnt    = 0;
      var storeEnvHtml   = "";
      var storeEnvstGrp  = envstGrpCd[i];
      
      storeEnvHtml += "<h3 class='h3_tbl2 lh30'>"+storeEnvstGrp.name+" <button class='open'></button>";
      
      if(i == 0){
        // 기본값으로 설정 버튼
        storeEnvHtml += "<span class='fr'><a href='javascript:;' class='btn_grayS' id='btnDefault'><s:message code='storeManage.setting.default.env' /></a></span>";
      }
      storeEnvHtml += "</h3>";
      
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
              storeEnvHtml += "    <input type='text' name='envstValCd' id='env" + list[j].envstCd + "' class='sb-input w100'>";
            } else {  // 값 선택
              storeEnvHtml += "    <select name='envstValCd' id='env" + list[j].envstCd + "'/>";
            }
            
            storeEnvHtml += "    <input type='hidden' name='status'    value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstCd'   value='"+ list[j].envstCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstNm'   value='"+ list[j].envstNm +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstGrpCd'value='"+ list[j].envstGrpCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='defltYn'   value='"+ list[j].defltYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='dirctInYn' value='"+ list[j].dirctInYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='targtFg'   value='"+ list[j].targtFg +"'>";
            storeEnvHtml += "    <input type='hidden' name='oldEnvstVal'   value='"+ list[j].selEnvstVal +"'>";
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
    
    $("#storeConfigContent").html(innerHtml);
    
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
    
  })
  .fail(function(){
      s_alert.pop("Ajax Fail");
  });
}

$("#storeEnvInfoArea #btnSaveStore").click(function(){

  var objStatus       = document.getElementsByName("status");
  var objEnvstCd      = document.getElementsByName("envstCd");
  var objEnvstNm      = document.getElementsByName("envstNm");
  var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
  var objDefault      = document.getElementsByName("defltYn");
  var objEnvstValCd   = document.getElementsByName("envstValCd");
  var objDirctInYn    = document.getElementsByName("dirctInYn");
  var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");
  
  // 봉사료 사용구분이 'Y'일 경우, 봉사료율이 설정되어야 함.
  var env126 = $("#env126").val();
  var env127 = $("#env127").val();
  
  if(env126 == '1' && (env127 == null || env127 == '')) {
    s_alert.pop("<s:message code='storeManage.require.serviceRate' />");
    return;
  }
  
  var chngCnt  = 0; // 변경된 건수
  var paramArr = new Array();
  
  for(var i=0; i<objEnvstCd.length; i++){
    if(objOldEnvstVal[i].value != $("#env"+objEnvstCd[i].value).val()) {
      chngCnt ++;
    }
  }
  
  if(chngCnt == 0 ){
    s_alert.pop("<s:message code='cmm.not.modify' />");
    return;
  }
  
  for(var i=0; i<objEnvstCd.length; i++){
    var param = {};
    param.hqOfficeCd  = selectedStore.hqOfficeCd;
//    param.hqBrandCd   = selectedStore.hqBrandCd;
    param.storeCd     = selectedStore.storeCd;
    param.status      = objStatus[i].value;
    param.envstCd     = objEnvstCd[i].value;
    param.envstNm     = objEnvstNm[i].value;
    param.envstGrpCd  = objEnvstGrpCd[i].value;
    param.envstVal    = objEnvstValCd[i].value;
    param.dirctInYn   = objDirctInYn[i].value;
    
    paramArr.push(param);
  }
  
  console.log(paramArr);
  
  $.postJSONArray("/store/manage/storeManage/storeManage/saveStoreConfig.sb", paramArr, function(result) {

    s_alert.pop("<s:message code='cmm.saveSucc' />");
    
    // 저장 완료후에 재로딩
    showStoreConfigLayout(selectedEnvstFg);
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
  
});

<%-- 기본값으로 설정 버튼 클릭 --%>
$(document).on("click", "#storeConfigArea #btnDefault", function(){
  var objEnvstCd      = document.getElementsByName("envstCd");
  var objEnvstNm      = document.getElementsByName("envstNm");
  var objDirctInYn    = document.getElementsByName("dirctInYn");
 
  for(var i=0; i<objEnvstCd.length; i++){
    
    var defaultVal = $("#env"+objEnvstCd[i].value).attr("defaultVal");
    
    if(objDirctInYn[i].value == "Y") {
      $("#env"+objEnvstCd[i].value).val(defaultVal);
    } else {
      $("#env"+objEnvstCd[i].value).val(defaultVal).prop("selected", true);
    }
  }
});

<%-- 매장환경, 외식환경 레이아웃 보이지 않기 --%>
function hideStoreConfigLayout() {
  //$("#storeConfigContent").empty();
  $("#storeConfigContent").hide();
  $("#storeConfigArea").hide();
}
</script>