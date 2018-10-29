<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="layerName" value="_storeDetail"/>
<div id="${layerName}Mask" class="fullDimmed" style="display: none;"></div>
<div id="${layerName}Layer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w800px" style="overflow:auto; height:700px;">
      <p class="tit" id="storeTitle"></p>
      <a href="#" class="btn_close ${layerName}Close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <li><s:message code="storeView.storeTitle" /></li>
          </ul>
        </div>
        <div>
        <h3 class="h3_tbl"><s:message code="storeView.basicInfo" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
             <tbody>
                <tr>
                    <th><s:message code="storeView.storeCd" /><em class="imp">*</em></th>
                    <td id="_storeCd"></td>
                    <th><s:message code="storeView.storeNm" /><em class="imp">*</em></th>
                    <td id="_storeNm"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.storeTypeNm" /><em class="imp">*</em></th>
                    <td id="_storeTypeNm"></td>
                    <th><s:message code="storeView.clsFgNmG" /><em class="imp">*</em></th>
                    <td id="_clsFgNmG"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.clsFgNm" /><em class="imp">*</em></th>
                    <td id="_clsFgNm"></td>
                    <th><s:message code="storeView.sysStatFg" /><em class="imp">*</em></th>
                    <td id="_sysStatFgNm"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.posCnt" /><em class="imp">*</em></th>
                    <td id="_posCnt"></td>
                    <th><s:message code="storeView.sysOpenDate" /><em class="imp">*</em></th>
                    <td id="_sysOpenDate"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.sysClosureDate" /><em class="imp">*</em></th>
                    <td id="_sysClosureDate"></td>
                    <th></th>
                    <td></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.bizStoreNm" /><em class="imp">*</em></th>
                    <td id="_bizStoreNm"></td>
                    <th><s:message code="storeView.ownerNm" /><em class="imp">*</em></th>
                    <td id="_ownerNm"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.bizNo" /><em class="imp">*</em></th>
                    <td id="_bizNo"></td>
                    <th><s:message code="storeView.telNo" /><em class="imp">*</em></th>
                    <td id="_telNo"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.mpNo" /></th>
                    <td id="_mpNo"></td>
                    <th><s:message code="storeView.faxNo" /></th>
                    <td id="_faxNo"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.emailAddr" /></th>
                    <td id="_emailAddr"></td>
                    <th><s:message code="storeView.hmpgAddr" /></th>
                    <td ><a id="_hmpgAddr" href="#" class="link"></a></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.address" /><em class="imp">*</em></th>
                    <td id="_address" colspan="3"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.areaNm" /><em class="imp">*</em></th>
                    <td id="_areaNm"></td>
                    <th><s:message code="storeView.vanNm" /><em class="imp">*</em></th>
                    <td id="_vanNm"></td>
                </tr>
                <tr>
                    <th><s:message code="storeView.agencyNm" /><em class="imp">*</em></th>
                    <td id="_adminNm"></td>
                    <th><s:message code="storeView.setComNm" /><em class="imp">*</em></th>
                    <td id="_agencyNm"></td>
                </tr>
            </tbody>
          </table>
          
          <h3 class="h3_tbl"><s:message code="storeView.bigo" /></h3>
          <table class="searchTbl">
              <colgroup>
                  <col class="w15" />
                  <col class="w35" />
                  <col class="w15" />
                  <col class="w35" />
              </colgroup>
              <tbody>
                  <tr>
                      <th><s:message code="storeView.sysRemark" /></th>
                      <td id="_sysRemark" colspan="3"></td>
                  </tr>
                  <tr>
                      <th><s:message code="storeView.hdRemark" /></th>
                      <td id="_hdRemark" colspan="3"></td>
                  </tr>
                  <tr>
                      <th><s:message code="storeView.remark" /></th>
                      <td id="_remark" colspan="3"></td>
                  </tr>
              </tbody>
          </table>
          <h3 class="h3_tbl"><s:message code="storeView.vanConfig.title" /></h3>
          <table class="searchTbl2">
              <colgroup>
                  <col class="w15" />
                  <col class="w40" />
                  <col class="w45" />
              </colgroup> 
              <thead>
                  <tr>
                      <th><s:message code="storeView.vanPart" /></th>
                      <th><s:message code="storeView.van" /></th>
                      <th><s:message code="storeView.vanConfig.vanTermnlNo" /></th>
                  </tr>
              </thead>
              <tbody>
                  <tr>
                      <th><s:message code="storeView.vanBaic" /></th>
                      <td id="_vanNm"></td>
                      <td id="_vanTermnlNo"></td>
                  </tr>
              </tbody>
          </table>
          
          <h3 class="h3_tbl"><s:message code="storeView.cornerTitle" /></h3>
          <table class="searchTbl">
              <colgroup>
                  <col class="w15" />
                  <col class="w35" />
                  <col class="w15" />
                  <col class="w35" />
              </colgroup>   
              <tbody>
                  <tr>
                      <th><s:message code="storeView.cornerUseYnNm" /></th>
                      <td><a href="#" class="link" id="_cornerUseYnNm"></a></td>
                      <th><s:message code="storeView.cashBill" /></th>
                      <td ID="_cashBillYnNm"></td>
                  </tr>
              </tbody>
          </table>
          
          <!-- 코너 사용여부에 따른 코너별 승인 또는 포스별 승인정보를 표시(디자인 기준)
            솔비측 추후 개발 예정사항으로 확인하여 소스 남겨둠. -->
          <div id="cornrApproveArea" style="display:none;">
            <h3 class="h3_tbl pdt5 lh30" style="border:0px;"><s:message code="storeView.cornerApprove" /><span class="fr"><a href="#" class="btn_grayS mr5"><s:message code="cmm.add" /></a><a href="#" class="btn_grayS"><s:message code="cmm.delete" /></a></span></h3>
            <div class="wj-TblWrapBr" style="height: 73px;">
              <div id="caTheGrid"></div>
            </div>
          </div>
          
          <div id="posApproveArea" style="display:none;">
            <h3 class="h3_tbl" style="border:0px;"><s:message code="storeView.posApprove" /></h3>
            <div class="wj-TblWrapBr" style="height: 73px;">
              <div id="paTheGrid"></div>
            </div>
          </div>
            
        </div>
        
        <div class="btnSet">
        <span>
          <a href="#" class="btn_gray  ${layerName}Close">
            <s:message code="cmm.close" />
          </a>
        </span>
      </div>
        
      </div>
    </div>
  </div>
</div>
<script>

  var caData =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"cornrNm", header:"<s:message code='storeView.cornerNm' />",width:"*"},
      {binding:"vanTermnlNo", header:"<s:message code='storeView.vanConfig.vanTermnlNo' />",width:"*"},
      {binding:"ownerNm", header:"<s:message code='storeView.ownerNm' />",width:"*"},
      {binding:"bizNo", header:"<s:message code='storeView.bizNo' />",width:"*"},
      {binding:"telNo", header:"<s:message code='storeView.telNo' />",width:"*"},
      {binding:"useYnNm", header:"<s:message code='storeView.cornerUseYn' />",width:"*"}
    ];
  var paData =
    [
      {binding:"posNo", header:"<s:message code='storeView.posNo' />번호",width:"*"},
      {binding:"", header:"<s:message code='storeView.termnlNo' />",width:"*"},
      {binding:"hwAuthKeyNm", header:"<s:message code='storeView.hwAuthKeyNm' />",width:"*"},
      {binding:"", header:"<s:message code='storeView.firstAuthDt' />",width:"*"},
      {binding:"", header:"<s:message code='storeView.lastAuthDt' />",width:"*"}
    ];
  
  var caGrid  = wgrid.genGrid("#caTheGrid", caData);
  var paGrid  = wgrid.genGrid("#paTheGrid", paData);

  <%-- 상점정보 상세 조회 --%>
  function getStoreDetail(obj) {
    var param = obj;
    
    var param = obj;
    $.postJSON("/base/store/view/dtl/list.sb", param, function(result) {
      showStoreDetailLayter(result.data);
      var storeTitle = param.storeCd+ " " + param.storeNm;
      $("#storeTitle").text(storeTitle)
    },
      function (result) {
        s_alert.pop(result.message);

    });
  }
    
  <%-- 레이어 열기 --%>
  function showStoreDetailLayter(obj) {
    
    <%-- 상점기본정보  --%>
    var storeInfo = obj.storeInfo;
    $("#_storeCd").text(nvl(storeInfo.storeCd,''));
    $("#_storeNm").text(nvl(storeInfo.storeNm,''));
    $("#_storeTypeNm").text(nvl(storeInfo.storeTypeNm,''));
    $("#_clsFgNmG").text(nvl(storeInfo.clsFgNm,''));
    $("#_clsFgNm").text(nvl(storeInfo.clsFgNm,''));
    $("#_sysStatFgNm").text(nvl(storeInfo.sysStatFgNm,''));
    $("#_posCnt").text(nvl(storeInfo.posCnt,''));
    $("#_sysOpenDate").text(nvl(storeInfo.sysOpenDate,''));
    $("#_sysClosureDate").text(nvl(storeInfo.sysClosureDate,''));
    $("#_bizStoreNm").text(nvl(storeInfo.bizStoreNm,''));
    $("#_ownerNm").text(nvl(storeInfo.ownerNm,''));
    $("#_bizNo").text(nvl(storeInfo.bizNo,''));
    $("#_telNo").text(nvl(storeInfo.telNo,''));
    $("#_mpNo").text(nvl(storeInfo.mpNo, ''));
    $("#_faxNo").text(nvl(storeInfo.faxNo,''));
    $("#_emailAddr").text(nvl(storeInfo.emailAddr,''));
    $("#_hmpgAddr").text(nvl(storeInfo.hmpgAddr,''));
    $("#_address").text(nvl(storeInfo.address,''));
    $("#_areaNm").text(nvl(storeInfo.areaNm,''));
    $("#_vanNm").text(nvl(storeInfo.vanNm,''));
    $("#_adminNm").text(nvl(storeInfo.agencyNm,''));
    $("#_agencyNm").text(nvl(storeInfo.agencyNm,''));
    $("#_sysRemark").text(nvl(storeInfo.sysRemark,''));
    $("#_hdRemark").text(nvl(storeInfo.hdRemark,''));
    $("#_remark").text(nvl(storeInfo.remark,''));
    $("#_cornerUseYnNm").text(nvl(storeInfo.cornerUseYnNm,''));
    $("#_cashBillYnNm").text(nvl(storeInfo.cashBillYnNm,''));
    
    <%-- VAN사설정정보  --%>
    var vanConfigList = obj.vanConfigList;
    if(vanConfigList.length >= 0) {
      $("#_vanNm").text(nvl(vanConfigList[0].vanNm,''));
      $("#_vanTermnlNo").text(nvl(vanConfigList[0].vanTermnlNo,''));
    }
    
    <%-- 코너별 승인정보  --%>
    $("#cornrApproveArea").hide();
    var cornrApproveList = obj.cornrApproveList;
    if(cornrApproveList != undefined && cornrApproveList.length > 0) {
      caGrid.itemsSource = new wijmo.collections.CollectionView(cornrApproveList, {
            trackChanges: true
      });
      $("#cornrApproveArea").show();
    }
    
    <%-- 포스별 승인정보  --%>
    $("#posApproveArea").hide();
    var posApproveList = obj.posApproveList;
    if (posApproveList != undefined && posApproveList.length > 0) {
      paGrid.itemsSource = new wijmo.collections.CollectionView(posApproveList, {
            trackChanges: true
      });
      $("#posApproveArea").show(); 
    }
    
    $("#${layerName}Mask, #${layerName}Layer").show();
  }
    
  <%-- 레이어 닫기 버튼 클릭--%>
  $(".${layerName}Close").click(function(e) {
      $("#${layerName}Mask, #${layerName}Layer").hide();
  });
  
  <%-- 코너승인별 팝업 클릭 --%>
  $("#_cornerUseYnNm").click(function(e){
    alert("추후 개발 예정");
  });
</script>
