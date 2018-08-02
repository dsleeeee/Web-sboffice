<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장환경   레이어 팝업 --%>
<div id="storeEnvDim" class="fullDimmed" style="display:none;"></div>
<div id="storeEnvLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w500">
      <p class="tit"><s:message code="storeManage.storeEnv" /></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <div class="mt20">
          <%-- 매장정보 --%>
          <p class="s14 bk mb5"><s:message code="storeManage.storeInfo" /></p>
          <table class="tblType01">
            <colgroup>
              <col class="w30" />
              <col class="w70" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 본사명 --%>
                <th><s:message code="storeManage.hqOfficeNm" /></th>
                <td id="envHqOfficeCd"></td>
              </tr>
              <tr>
                <%-- 매장명 --%>
                <th><s:message code="storeManage.storeNm" /></th>
                <td id="envStoreNm"></td>
              </tr>
              <tr>
                <%-- 용도구분 --%>
                <th><s:message code="storeManage.cls" /></th>
                <td id="envCls"></td>
              </tr>
            </tbody>
          </table>
          
          <%-- 포스 기종--%>
          <div id="posArea" style="display:none;">
            <p class="s14 bk mb5 mt20"><s:message code="storeManage.posKindInfo" /></p>
            <table class="tblType01">
              <colgroup>
                <col class="w30" />
                <col class="w70" />
              </colgroup>
              <tbody id="posKind">
              </tbody>
            </table>
          </div>
          
          <%-- 주방 프린터 기종 --%>
          <div id="printArea" style="display:none;">
            <p class="s14 bk mb5 mt20"><s:message code="storeManage.printKindInfo" /></p>
            <table class="tblType01">
              <colgroup>
                <col class="w30" />
                <col class="w70" />
              </colgroup>
              <tbody id="printKind">
              </tbody>
            </table>
          </div>
          
        </div>
      </div>
      <div class="btnSet">
        <span><a id="btnCopy" href="javascript:;" class="btn_gray"><s:message code="storeManage.copy.storeEnv" /></a></span>
        <span><a id="btnClose" href="javascript:;" class="btn_gray"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  <%-- 매장환경조회 팝업 오픈 --%>
  function openEnvInfoLayer(param) {

    $.postJSON("/store/manage/storeManage/storeManage/getStoreEnvInfo.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      
      var storeInfo = result.data.storeInfo;
      var posInfo =  result.data.posInfo;
      var printInfo =  result.data.printInfo;
      
      if(storeInfo == null){
        s_alert.pop("<s:message code='storeManage.no.store.info' />");
        return;
      }
      
      $("#envHqOfficeCd").text(storeInfo.hqOfficeNm);
      $("#envStoreNm").text(storeInfo.storeNm);
      $("#envCls").text(storeInfo.nmcodeNm);
      
      var posHtml = "";
      if(posInfo.length > 0) {
        for(var i=0; i<posInfo.length; i++){
          posHtml += "<tr>";
          posHtml += "<th>"+posInfo[i].prterNo+"</th>";
          posHtml += "<td>"+posInfo[i].envstValNm+"</td>";
          posHtml += "</tr>";
        }
        $("#posKind").html(posHtml);
        $("#posArea").show();
      }
      
      
      var printHtml = "";
      if(printInfo.length > 0) {
        for(var i=0; i<printInfo.length; i++){
          printHtml += "<tr>";
          printHtml += "<th>"+printInfo[i].prterNo+"</th>";
          printHtml += "<td>"+printInfo[i].envstValNm+"</td>";
          printHtml += "</tr>";
        }
        $("#printKind").html(printHtml);
        $("#printArea").show();
      }
      
      $("#storeEnvDim").show();
      $("#storeEnvLayer").show();
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 사업자번호 사용현황 목록 조회 --%>
  function getBizInfo(param) {
    $.postJSON("/store/hq/hqmanage/master/bizUseList.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;
      theGridBizInfo.itemsSource = list;
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
  
  function getBizInfoDtl(param) {
    $.postJSON("/store/hq/hqmanage/master/bizInfoDtl.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      
      var dtlData = result.data;
      
      $("#bStoreCd").text(dtlData.storeCd);
      $("#bStoreNm").text(dtlData.storeNm);
      $("#bStoreCd").text(dtlData.storeCd);
      $("#bSysStatFg").text(dtlData.sysStatFgNm);
      $("#bOwnerNm").text(dtlData.ownerNm);
      $("#bBizNo").text(bizParam.bizNo1 + "-" + bizParam.bizNo2 + "-" + bizParam.bizNo3);
      $("#bBizStoreNm").text(dtlData.bizStoreNm);
      $("#bArea").text(dtlData.areaNm);
      $("#bTelNo").text(dtlData.telNo);
      $("#bFaxNo").text(dtlData.faxNo);
      $("#bAgency").text(dtlData.agencyCd);
      $("#bClsFg").text(dtlData.clsFgNm);
      $("#bSysOpenDate").text(dtlData.sysOpenDate);
      $("#bAddr").text("(" + dtlData.postNo + ") " + dtlData.addr + " " + dtlData.addrDtl);
      
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 레이어 팝업 닫기 --%>
  $("#storeEnvLayer .btn_close, #storeEnvLayer #btnClose").click(function(){
    $("#posArea").hide();
    $("#printArea").hide();
    
    $("#storeEnvDim").hide();
    $("#storeEnvLayer").hide();
  });

</script>