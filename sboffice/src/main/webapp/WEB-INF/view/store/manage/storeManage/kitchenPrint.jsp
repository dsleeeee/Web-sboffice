<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="kitchenPrintArea" style="display:none;">

  <div class="wj-TblWrap mr10">
    <div class="oh mb10">
      <%-- 삭제 --%>
      <span class="fr ml5"><a id="btnkitchenPrintDel" href="javascript:;" class="btn_grayS2"><s:message code="cmm.delete" /></a></span>
      <%-- 추가 --%>
      <span class="fr"><a id="btnkitchenPrintAdd" href="javascript:;" class="btn_grayS2"><s:message code="cmm.add" /></a></span>
    </div>
  </div>
  
  <%-- 주방프린터 그리드 --%>
  <div id="kitchenPrintGrid" style="height:400px;"></div>
  
  <%-- 저장버튼 --%>
  <div class="tc">
    <button type="button" id="btnSaveKitchenPrint" class="btn_blue" ><s:message code="cmm.save" /></button>
  </div> 
</div>

<script>

<%-- 공통코드 --%>
var prterKind   = ${ccu.getEnvCodeExcpAll("206")};
var prterPort   = ${ccu.getEnvCodeExcpAll("207")};
var prterSpeed = ${ccu.getEnvCodeExcpAll("208")};
var useYn         = ${ccu.getCommCodeExcpAll("904")};

var posNoDM;
var prterKindDM   = new wijmo.grid.DataMap(prterKind, 'value', 'name');
var prterPortDM   = new wijmo.grid.DataMap(prterPort, 'value', 'name');
var prterSpeedDM = new wijmo.grid.DataMap(prterSpeed, 'value', 'name');
var useYnDataMap  = new wijmo.grid.DataMap(useYn, 'value', 'name');

<%-- 그리드  설정 --%>
var kitchenPrintHeader = 
  [
    {binding:"posNo", header:"<s:message code='storeManage.kitchenPrint.posNo' />"},
    {binding:"prterNo", header:"<s:message code='storeManage.prterNo' />"},
    {binding:"prterNm", header:"<s:message code='storeManage.prterNm' />"},
    {binding:"prterKindFg", header:"<s:message code='storeManage.prterKind' />", dataMap:prterKindDM},
    {binding:"prterPortFg", header:"<s:message code='storeManage.prterPort' />", dataMap:prterPortDM},
    {binding:"prterSpeedFg", header:"<s:message code='storeManage.prterSpeed' />", dataMap:prterSpeedDM},
    {binding:"prterOutputQty", header:"<s:message code='storeManage.prterOutputQty' />"},
    {binding:"prterNetIp", header:"<s:message code='storeManage.prterNetIp' />"},
    {binding:"prterNetPort", header:"<s:message code='storeManage.prterNetPort' />"},
    {binding:"useYn", header:"<s:message code='storeManage.useYn' />", dataMap:useYnDataMap},
    {binding:"chk", header:"<s:message code='cmm.delete' />", dataType:wijmo.DataType.Boolean}
  ];

var kitchenPrintGrid = wgrid.genGrid("#kitchenPrintGrid", kitchenPrintHeader, "${menuCd}", 2, ${clo.getColumnLayout(2)});

kitchenPrintGrid.isReadOnly = false;

<%-- 체크박스 초기화 --%>
kitchenPrintGrid.formatItem.addHandler(function(s, e) {
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    var item = s.rows[e.row].dataItem;
    
    if( col.binding == "chk") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chk == true || item.chk == "Y" ? 'checked' : '') + '>';
    }
  }
});

<%-- 주방프린터 영역 보여줌 --%>
function showkitchenPrintLayout(){
  
  $("#kitchenPrintArea").show();

  if(posNoDM == undefined) {
    posNoDM = new wijmo.grid.DataMap(posList, 'posNo', 'posNm');
  }
  
  kitchenPrintGrid.columns[0].dataMap = posNoDM;  // posNo dataMap 설정
  
  var param = {};
  param.hqOfficeCd  = selectedStore.hqOfficeCd;
  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;
    
  <%-- 주방프린터 목록 조회 --%>
  $.postJSON("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    
    var list = result.data.list;

    kitchenPrintGrid.itemsSource = new wijmo.collections.CollectionView(list);
    kitchenPrintGrid.itemsSource.trackChanges = true;

  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 추가 버튼 클릭 --%>
$("#btnkitchenPrintAdd").click(function(){
  kitchenPrintGrid.collectionView.newItemCreator = function() {
    return {
      prterKind: '0',
      prterPort: '0',
      prterSpeed: '0'
    }
  };
  var newItem = kitchenPrintGrid.collectionView.addNew();
  kitchenPrintGrid.collectionView.commitNew();
});

<%-- 삭제 버튼 클릭 --%>
$("#btnkitchenPrintDel").click(function(){
  for(var i = kitchenPrintGrid.itemsSource.itemCount-1; i >= 0; i-- ){
    var item = kitchenPrintGrid.itemsSource.items[i];
    if(item.chk){
      kitchenPrintGrid.itemsSource.removeAt(i);
    }
  }
});

<%-- 저장 버튼 클릭 --%>
$("#btnSaveKitchenPrint").click(function(){
  
  var paramArr = new Array();

  // validateion Check
  // 우선은 저장하고 val 체크하자
  
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
  
  console.log(paramArr);
  
  $.postJSONArray("/store/manage/storeManage/storeManage/saveKitchenPrintInfo.sb", paramArr, function(result) {
    console.log(result);
    s_alert.pop("<s:message code='msg.save.succ' />");
    kitchenPrintGrid.collectionView.clearChanges();
  },
  function(result) {
    console.log(result);
    s_alert.pop(result.data.msg);
  });
});


<%-- 주방프린터 설정 레이아웃 보이지 않기--%>
function hideKitchenPrintLayout() {
  $("#kitchenPrintArea").hide();
}


</script>