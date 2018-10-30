<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="kitchenPrintArea" style="display:none;" ng-controller="kitchenPrintCtrl">

  <div class="wj-TblWrap mr10">
    <div class="oh mb10">
      <%-- 삭제 --%>
      <span class="fr ml5"><a id="btnkitchenPrintDel" href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.delete" /></a></span>
      <%-- 추가 --%>
      <span class="fr"><a id="btnkitchenPrintAdd" href="#" class="btn_grayS2" ng-click="addRow()"><s:message code="cmm.add" /></a></span>
    </div>
  </div>

  <%-- 주방프린터 그리드 --%>
  <%--<div id="kitchenPrintGrid" style="height:400px;"></div>--%>
  <div id="kitchenPrintGrid" style="height: 400px;">
    <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

      <!-- define columns -->
      <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.kitchenPrint.posNo"/>" binding="posNo" data-map="posDataMap" ></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNo"/>" binding="prterNo" ></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNm"/>" binding="prterNm" ></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterKind"/>" binding="prterKind" data-map="prterKindDataMap"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterPort"/>" binding="prterPort" data-map="prterPortDataMap"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterSpeed"/>" binding="prterSpeed" width="*" data-map="prterSpeedDataMap" is-read-only="true" ></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterOutputQty"/>" binding="prterOutputQty" width="*" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetIp"/>" binding="prterNetIp" width="*" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetPort"/>" binding="prterNetPort" width="*" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.useYn"/>" binding="useYn" width="*" is-read-only="true" data-map="useYnFgDataMap"></wj-flex-grid-column>
    </wj-flex-grid>
  </div>

  <%-- 저장버튼 --%>
  <div class="tc">
    <button type="button" id="btnSaveKitchenPrint" class="btn_blue" ><s:message code="cmm.save" /></button>
  </div>
</div>

<script>
<%-- 공통코드 --%>
var prterKind   = ${cnv.getEnvCodeExcpAll("4030")};
var prterPort   = ${cnv.getEnvCodeExcpAll("4031")};
var prterSpeed  = ${cnv.getEnvCodeExcpAll("4032")};
var useYn       = ${ccu.getCommCodeExcpAll("067")};

</script>
<script>

<%-- 저장 버튼 클릭 --%>
$("#btnSaveKitchenPrint").click(function(){

  var paramArr = new Array();

  // 필수값 체크 TODO

  for(var i=0; i<kitchenPrintGrid.itemsSource.itemsEdited.length; i++){
    kitchenPrintGrid.itemsSource.itemsEdited[i].storeCd = selectedStore.storeCd;
    kitchenPrintGrid.itemsSource.itemsEdited[i].status = "U";
    paramArr.push(kitchenPrintGrid.itemsSource.itemsEdited[i]);
  }

  for(var i=0; i<kitchenPrintGrid.itemsSource.itemsAdded.length; i++){
    kitchenPrintGrid.itemsSource.itemsAdded[i].storeCd = selectedStore.storeCd;
    kitchenPrintGrid.itemsSource.itemsAdded[i].status = "I";
    paramArr.push(kitchenPrintGrid.itemsSource.itemsAdded[i]);
  }

  for(var i=0; i<kitchenPrintGrid.itemsSource.itemsRemoved.length; i++){
    kitchenPrintGrid.itemsSource.itemsRemoved[i].storeCd = selectedStore.storeCd;
    kitchenPrintGrid.itemsSource.itemsRemoved[i].status = "D";
    paramArr.push(kitchenPrintGrid.itemsSource.itemsRemoved[i]);
  }

  $.postJSONArray("/store/manage/storeManage/storeManage/saveKitchenPrintInfo.sb", paramArr, function(result) {
    console.log(result);
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    kitchenPrintGrid.collectionView.clearChanges();
  },
  function(result) {
    console.log(result);
    s_alert.pop(result.message);
  });
});

<%-- 주방프린터 설정 레이아웃 보이지 않기--%>
function hideKitchenPrintLayout() {
  $("#kitchenPrintArea").hide();
}

</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/kitchenPrint.js?ver=2018102301" charset="utf-8"></script>
