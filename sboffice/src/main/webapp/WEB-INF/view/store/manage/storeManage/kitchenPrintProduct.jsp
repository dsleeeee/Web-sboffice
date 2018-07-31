<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="kitchenPrintProductArea" style="display:none;">

  <div class="wj-TblWrap mt20 mb40">

    <%-- 주방프린터 --%>
    <div class="w33 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:500px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.kitchenPrint" /></span>
        </div>
        <div id="kpProductGrid" style="height:400px;"></div> 
      </div> 
    </div>
    
    <%-- 출력상품 --%>
    <div class="w33 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.print.product" /></span>
          <!-- <button class="btn_skyblue">펼침</button> -->
          <%-- 삭제 --%>
          <button type="button" id="btnProdDel" class="btn_skyblue"><s:message code="cmm.delete" /></button>
        </div>
        <div id="productTree1"></div>
      </div>
    </div>
    
    <%-- 미출력상품 --%>
    <div class="w33 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.no.print.product" /></span>
          <%-- 추가 --%>
          <button type="button" id="btnProdAdd" class="btn_skyblue"><s:message code="cmm.add" /></button>
        </div>
        <div id="productTree2"></div>
      </div>
    </div>
  </div>
</div>

<script>

var selectedDataItem;

<%-- 그리드 설정 --%>
var kpProductGridHeader = 
  [
    {binding:"no", header:"<s:message code='cmm.no' />", width:"*"},
    {binding:"prterNo", header:"<s:message code='storeManage.prterNo' />", width:"*"},
    {binding:"prterNm", header:"<s:message code='storeManage.prterNm' />", width:"*"},
    {binding:"cnt", header:"<s:message code='storeManage.product.cnt' />", width:"*"}
  ];
  
var kpProductGrid = wgrid.genGrid("#kpProductGrid", kpProductGridHeader, "${menuCd}", 3, ${clo.getColumnLayout(3)});

kpProductGrid.isReadOnly = true;

<%-- 그리드 포맷 --%>
kpProductGrid.formatItem.addHandler(function(s, e) {
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    var item = s.rows[e.row].dataItem;
    if( col.binding == "prterNo") {
      wijmo.addClass(e.cell, 'wijLink');
    }
  }
});

<%-- 그리드 선택 이벤트 --%>
kpProductGrid.addEventListener(kpProductGrid.hostElement, 'mousedown', function(e) {
  var ht = kpProductGrid.hitTest(e);
  var row = ht.row;
  if( ht.cellType == wijmo.grid.CellType.Cell) {
    var col = ht.panel.columns[ht.col];
    if( col.binding == "prterNo") {
      selectedDataItem = kpProductGrid.rows[ht.row].dataItem;
      getProductList();
    }
  }
});

<%-- 상품 트리 설정 --%>
var productTree1 = new wijmo.nav.TreeView('#productTree1', {
  displayMemberPath: 'prodNm',
  childItemsPath: 'items',
  expandOnClick : true,
  isReadOnly: false,
  showCheckboxes: true
});

var productTree2 = new wijmo.nav.TreeView('#productTree2', {
  displayMemberPath: 'prodNm',
  childItemsPath: 'items',
  expandOnClick : true,
  isReadOnly: false,
  showCheckboxes: true
});

var delView = new wijmo.collections.CollectionView(); <%-- 데이터 삭제용 --%>
var addView = new wijmo.collections.CollectionView(); <%-- 데이터 추가용 --%>

<%-- 트리에 아이템 체그 상태 변경시 CollectionView에 반영 --%>
productTree1.checkedItemsChanged.addHandler(function(s, e) {
  delView.itemsAdded.clear();
  for(var i = 0; i < productTree1.checkedItems.length; i++) {
    if(!delView.contains(productTree1.checkedItems[i])) {
      delView.itemsAdded.push(productTree1.checkedItems[i]);
    }
  }
});

productTree2.checkedItemsChanged.addHandler(function(s, e) {
  addView.itemsAdded.clear();
  for(var i = 0; i < productTree2.checkedItems.length; i++) {
    if(!addView.contains(productTree2.checkedItems[i])) {
      addView.itemsAdded.push(productTree2.checkedItems[i]);
    }
  }
});


<%-- 주방프린터-상품등록 영역 보여줌 --%>
function showKitchenPrintProductLayout(){
  $("#kitchenPrintProductArea").show();
  getPrintList();
}

<%-- 주방프린터 목록 조회 --%>
function getPrintList(){

  kpProductGrid.itemsSource = [];
  
  var param = {};
  param.hqOfficeCd  = selectedStore.hqOfficeCd;
//  param.hqBrandCd   = selectedStore.hqBrandCd;
  param.storeCd     = selectedStore.storeCd;
  
  $.postJSON("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    var list = result.data.list;

    kpProductGrid.itemsSource = new wijmo.collections.CollectionView(list);
    kpProductGrid.itemsSource.trackChanges = true;
  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 주방프린터 출력상품 조회 --%>
function getProductList(){
  
  var param = {};
  param.storeCd     = selectedDataItem.storeCd;
  param.prterNo     = selectedDataItem.prterNo;
  
  productTree1.itemsSource = [];
  productTree2.itemsSource = [];
  
  $.postJSON("/store/manage/storeManage/storeManage/getKitchenPrintProductInfo.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    
    productTree1.itemsSource = result.data.list.printProductList;
    productTree2.itemsSource = result.data.list.noPrintProductList;
  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 삭제버튼 클릭 --%>
$("#btnProdDel").click(function(){
  var paramArr = new Array();
  for(var i = 0; i < delView.itemsAdded.length; i++) {
    delView.itemsAdded[i].status = 'D';
    paramArr.push(delView.itemsAdded[i]);
  }
  
  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }
  
  $.postJSONArray("/store/manage/storeManage/storeManage/saveKitchenPrintProduct.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    delView.clearChanges();
    getProductList();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
});

<%-- 추가버튼 클릭 --%>
$("#btnProdAdd").click(function(){
  var paramArr = new Array();
  for(var i = 0; i < addView.itemsAdded.length; i++) {
    addView.itemsAdded[i].status = 'I';
    paramArr.push(addView.itemsAdded[i]);
  }
  
  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }
  
  $.postJSONArray("/store/manage/storeManage/storeManage/saveKitchenPrintProduct.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    addView.clearChanges();
    getProductList();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
  
});

<%-- 주방프린터-상품등록 레이아웃 보이지 않기--%>
function hideKitchenPrintProductLayout() {
  $("#kitchenPrintProductArea").hide();
}

</script>