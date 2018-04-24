<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/pos/confg/func/func/" />

<!--right-->
<div class="contents">
  <!--서브컨텐츠-->
  <div class="subCon">

    <!--2단-->
    <div class="wj-TblWrap mb40">
      <!--left-->
      <div class="w50 fl" style="width: 30%">
        <div class="wj-TblWrapBr mr10 pd20" style="height: 580px;">
          <div class="updownSet oh mb10">
            <span class="fl bk lh30"><s:message code='func.funFg' /></span>
          </div>
          <!--위즈모 테이블-->
          <div id="theGrid1"></div>
          <!--//위즈모 테이블-->
        </div>
      </div>
      <!--//left-->

      <!--right-->
      <div class="w50 fr" style="width: 70%">
        <div class="wj-TblWrapBr ml10 pd20" style="height: 580px;">
          <div class="updownSet oh mb10">
            <span class="fl bk lh30">기능 <span id="funcName"></span></span>
            <button class="btn_up" id="btnUp" style="display: none;">
              <s:message code="cmm.up" />
            </button>
            <button class="btn_down" id="btnDown" style="display: none;">
              <s:message code="cmm.down" />
            </button>
            <button class="btn_skyblue" id="btnAdd" style="display: none;">
              <s:message code="cmm.add" />
            </button>
            <button class="btn_skyblue" id="btnDel" style="display: none;">
              <s:message code="cmm.delete" />
            </button>
            <button class="btn_skyblue" id="btnSave" style="display: none;">
              <s:message code="cmm.save" />
            </button>
          </div>
          <!--위즈모 테이블-->
          <div id="theGrid2" style="height: 495px;"></div>
          <!--//위즈모 테이블-->
        </div>
      </div>
      <!--//right-->
    </div>
    <!--//2단-->

  </div>
  <!--//서브컨텐츠-->
</div>
<!--//right-->

<script>

var funcFgList  = ${fnkeyFgList};
var funcList    = [];
var selectData;

var storeKind   = ${ccu.getCommCodeExcpAll("088")};
var posFg       = ${ccu.getCommCodeExcpAll("035")};
var useYn       = ${ccu.getCommCodeExcpAll("904")};

var storeKindDataMap   = new wijmo.grid.DataMap(storeKind, 'value', 'name');  // 전체는 어떻게 할 것인지
var posFgDataMap     = new wijmo.grid.DataMap(posFg, 'value', 'name');

<%-- 데이터 초기화 --%>
if(funcFgList.length > 0 ){
  funcFgList.forEach(function(item, i){
    item.no = (i+1);
  });
}

<%-- CollectionView --%>
var funcFgCView = new wijmo.collections.CollectionView(funcFgList);

<%-- Header --%>
var hData1 = 
  [
    {binding:"no", header:"<s:message code='func.No' />"},
    {binding:"nmcodeNm", header:"<s:message code='func.funFg' />"}
  ];
  
var hData2 = 
  [
    {binding:"chk", header:"<s:message code='func.chk' />", allowMerging:true, dataType:wijmo.DataType.Boolean},
    {binding:"fnkeyNo", header:"<s:message code='func.fnkeyNo' />", maxLength:2, allowMerging:true},
    {binding:"fnkeyNm", header:"<s:message code='func.fnkeyNm' />", maxLength:20, allowMerging:true},
    {binding:"storeFg", header:"<s:message code='func.storeFg' />", dataMap:storeKindDataMap, allowMerging:true},
    {binding:"posFg", header:"<s:message code='func.posFg' />", dataMap:posFgDataMap, allowMerging:true},
    {binding:"posiAdjYn", header:"<s:message code='func.posiAdjYn' />", allowMerging:true, dataType:wijmo.DataType.Boolean},
    {binding:"colPosi", header:"<s:message code='func.colPosi' />", allowMerging:true},
    {binding:"rowPosi", header:"<s:message code='func.rowPosi' />", allowMerging:true},
    {binding:"width", header:"<s:message code='func.width' />", allowMerging:true},
    {binding:"height", header:"<s:message code='func.height' />", allowMerging:true},
    {binding:"fnkeyFunUseYn0", header:"<s:message code='func.useYn' />", dataType:wijmo.DataType.Boolean},
    {binding:"imgFileNm0", header:"<s:message code='func.imgFileNm' />", maxLength:50},
    {binding:"fnkeyFunUseYn1", header:"<s:message code='func.useYn' />", dataType:wijmo.DataType.Boolean},
    {binding:"imgFileNm1", header:"<s:message code='func.imgFileNm' />", maxLength:50},
    {binding:"useYn", header:"<s:message code='func.useYn' />"},
    {binding:"dispSeq", header:"<s:message code='func.dispSeq' />"}
  ];

<%-- 그리드 생성 --%>
var grid1 = wgrid.genGrid("#theGrid1", hData1, "${menuCd}", 1, ${clo.getColumnLayout(1)});
var grid2 = wgrid.genGrid("#theGrid2", hData2, "${menuCd}", 2, ${clo.getColumnLayout(1)});

grid1.itemsSource     = funcFgCView;
grid1.isReadOnly      = true;

grid2.isReadOnly      = false;
grid2.allowMerging    = wijmo.grid.AllowMerging.ColumnHeaders;

<%-- merge할 헤더 추가 --%>
for (var i = 0; i < 1; i++) {
  var hr = new wijmo.grid.Row();
  grid2.columnHeaders.rows.push(hr);
}

<%-- merge --%>
for (var row = 0; row < grid2.columnHeaders.rows.length; row++) {
  grid2.columnHeaders.rows[row].allowMerging = true;
  for (var col = 0; col < grid2.columns.length; col++) {
    if ((row == 0 && col == 0) || row == 1 && col == 0 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.chk' />");
    }
    if ((row == 0 && col == 1) || row == 1 && col == 1 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.fnkeyNo' />");
    }
    if ((row == 0 && col == 2) || row == 1 && col == 2 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.fnkeyNm' />");
    }
    if ((row == 0 && col == 3) || row == 1 && col == 3 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.storeFg' />");
    }
    if ((row == 0 && col == 4) || row == 1 && col == 4 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.posFg' />");
    }
    if ((row == 0 && col == 5) || row == 1 && col == 5 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.posiAdjYn' />");
    }
    if ((row == 0 && col == 6) || row == 1 && col == 6 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.colPosi' />");
    }
    if ((row == 0 && col == 7) || row == 1 && col == 7 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.rowPosi' />");
    }
    if ((row == 0 && col == 8) || row == 1 && col == 8 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.width' />");
    }
    if ((row == 0 && col == 9) || row == 1 && col == 9 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.height' />");
    }
    if ((row == 0 && col == 10) || row == 0 && col == 11 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.comm' />");
    }
    if ((row == 0 && col == 12) || row == 0 && col == 13 ) {
      grid2.columnHeaders.setCellData(row, col, "<s:message code='func.food' />");
    }
    if ((row == 0 && col == 14) || row == 1 && col == 14 ) {
      grid2.columnHeaders.columns[col].visible = false; // merge컬럼 숨김
    }
    if ((row == 0 && col == 15) || row == 1 && col == 15 ) {
      grid2.columnHeaders.columns[col].visible = false;
    }
  }
}

<%-- 그리드 포맷 --%>
grid1.formatItem.addHandler(function(s, e) {
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    var item = s.rows[e.row].dataItem;
    
    if( col.binding == "nmcodeNm" ) {
      item.row = e.row;
      item.col = e.col;
      item.cls = "func_row"
      var html = wijmo.format("<a href=\"javascript:;\" class=\"{cls}\" data-value=\"{row}\">{nmcodeNm}</a>", item);
      e.cell.innerHTML = html;
    }
  }
});

<%-- 그리드 선택 이벤트 --%>
$(document).on("click",".func_row",function() {
  var row = $(this).data("value");
  selectData = grid1.rows[row].dataItem;
  srchFuncData(selectData);
});

<%-- 그리드2 데이터 조회 --%>
function srchFuncData(rowData) {
  var param = {};
  
  param.fnkeyFg = rowData.nmcodeCd;
  
  //$.postJSON("${baseUrl}" + "funList.sb", param, function(result) {
  $.postJSON("${baseUrl}" + "funcList.sb", JSON.stringify(param), function(result) {

    $("#funcName").text(rowData.nmcodeNm);
    $("button").show();
    
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    var list = result.data.list;
    grid2.itemsSource = new wijmo.collections.CollectionView(list);
    grid2.itemsSource.trackChanges = true;
    grid2.itemsSource.canSort = true;
    
    if(list.length === undefined || list.length == 0) {
      s_alert.pop(result.message);
      return;
    }
    
    <%-- 그리드2 데이터 수정 옵션 --%>
    grid2.beginningEdit.addHandler(function (s, e) {
      
      var col   = s.columns[e.col];
      var item  = s.rows[e.row].dataItem;
      
      <%-- 조회한 데이터의 기능키 번호는 수정 불가 --%>
      if( col.binding == "fnkeyNo") {
        if(item.regId == undefined || item.regId == ""){
          e.cancel = false;
        }else{
          e.cancel = true;
        }
      }
      <%-- 위치조정여부 Y시에만 열위치, 줄위치, 폭, 높이 입력 가능  --%>
      if( col.binding == "colPosi" || col.binding == "rowPosi" || col.binding == "width" || col.binding == "height") {
        if(item.posiAdjYn == "Y" || item.posiAdjYn == true){
          e.cancel = false;
        }else{
          e.cancel = true;
        }
      }
      <%-- 일반 사용 Y일때만 일반 이미지명 입력 가능 --%>
      if( col.binding == "imgFileNm0") {
        if(item.fnkeyFunUseYn0 == "Y" || item.fnkeyFunUseYn0 == true){
          e.cancel = false;
        }else{
          e.cancel = true;
        }
      }
      <%-- 외식 사용 Y일때만 외식 이미지명 입력 가능 --%>
      if( col.binding == "imgFileNm1") {
        if(item.fnkeyFunUseYn1 == "Y" || item.fnkeyFunUseYn1 == true){
          e.cancel = false;
        }else{
          e.cancel = true;
        }
      }
    });
  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 체크박스 초기화 --%>
grid2.formatItem.addHandler(function(s, e) {
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    var item = s.rows[e.row].dataItem;
    
    if( col.binding == "chk") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chk == true || item.chk == "Y" ? 'checked' : '') + '>';
    }
    if( col.binding == "fnkeyFunUseYn0") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.fnkeyFunUseYn0 == true || item.fnkeyFunUseYn0 == "Y" ? 'checked' : '') + '>';
    }
    if( col.binding == "fnkeyFunUseYn1") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.fnkeyFunUseYn1 == true || item.fnkeyFunUseYn1 == "Y" ? 'checked' : '') + '>';
    }
    if( col.binding == "posiAdjYn") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.posiAdjYn == true || item.posiAdjYn == "Y" ? 'checked' : '') + '>';
    }
    if( col.binding == "useYn") {
      e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.useYn == true || item.useYn == "Y" ? 'checked' : '') + '>';
    }
  }
});


<%-- validation --%>
grid2.cellEditEnded.addHandler(function (s, e){
  var col = s.columns[e.col];
  if(col.maxLength){
    var val = s.getCellData(e.row, e.col);
    if(col.binding == "fnkeyNo" || col.binding == "colPosi" || col.binding == "rowPosi" || col.binding == "width" || col.binding == "height") {
      if(val.match(/[^0-9]/)){
        s_alert.pop(col.header+"<s:message code='cmm.require.number'/>");
        s.setCellData(e.row, e.col, val.replace(/[^0-9]/g,""));
      }
    }
  }
});



<%-- up 버튼 클릭 --%>
$("#btnUp").click(function(e){
  for(var i = 0; i < grid2.collectionView.itemCount; i++ ){
    var item = grid2.collectionView.items[i];
    if(i > 0 && item.chk){
      if(!grid2.collectionView.items[i-1].chk){
        var tmpItem = grid2.collectionView.items[i-1];
        grid2.collectionView.items[i-1] = grid2.collectionView.items[i];
        grid2.collectionView.items[i] = tmpItem;
        grid2.collectionView.commitEdit();
        grid2.collectionView.refresh();
      }
    }
  }
});

<%-- down 버튼 클릭 --%>
$("#btnDown").click(function(e){
  for(var i = grid2.itemsSource.itemCount-1; i >= 0; i-- ){
    var item = grid2.collectionView.items[i];
    if((i < grid2.itemsSource.itemCount-1) && item.chk){
      if(!grid2.collectionView.items[i+1].chk){
        var tmpItem = grid2.collectionView.items[i+1];
        grid2.collectionView.items[i+1] = grid2.collectionView.items[i];
        grid2.collectionView.items[i] = tmpItem;
        grid2.collectionView.commitEdit();
        grid2.collectionView.refresh();
      } 
    }
  }
});

<%-- 추가 버튼 클릭 --%>
$("#btnAdd").click(function(e){
  var newItem = grid2.collectionView.addNew();
  grid2.collectionView.commitNew();
});

<%-- 삭제 버튼 클릭 --%>
$("#btnDel").click(function(e){
  for(var i = grid2.itemsSource.itemCount-1; i >= 0; i-- ){
    var item = grid2.itemsSource.items[i];
    if(item.chk){
      grid2.itemsSource.removeAt(i);
    }
  }
});

<%-- 저장 버튼 클릭 --%>
$("#btnSave").click(function(e){
 
  for(var i = 0; i < grid2.collectionView.itemCount; i ++) {
    grid2.collectionView.editItem(grid2.collectionView.items[i]);
    grid2.collectionView.items[i].dispSeq = (i+1);
    grid2.collectionView.commitEdit();
  }
  
  var paramArr = new Array();
  
  for(var i=0; i<grid2.collectionView.itemsEdited.length; i++){
    grid2.collectionView.itemsEdited[i].status = "U";
    paramArr.push(grid2.collectionView.itemsEdited[i]);
  }
  for(var i=0; i<grid2.collectionView.itemsAdded.length; i++){
    grid2.collectionView.itemsAdded[i].status = "I";
    grid2.collectionView.itemsAdded[i].fnkeyFg = selectData.nmcodeCd;
    paramArr.push(grid2.collectionView.itemsAdded[i]);
  }
  for(var i=0; i<grid2.collectionView.itemsRemoved.length; i++){
    grid2.collectionView.itemsRemoved[i].status = "D";
    paramArr.push(grid2.collectionView.itemsRemoved[i]);
  }
  
  for(var i=0; i<paramArr.length; i++){
    paramArr[i].posiAdjYn       = (paramArr[i].posiAdjYn      == true ? "Y":"N");
    paramArr[i].fnkeyFunUseYn0  = (paramArr[i].fnkeyFunUseYn0 == true ? "Y":"N");
    paramArr[i].fnkeyFunUseYn1  = (paramArr[i].fnkeyFunUseYn1 == true ? "Y":"N");
    paramArr[i].useYn           = (paramArr[i].useYn          == true ? "Y":"N");
  }
  
  $.postJSON("${baseUrl}" + "save.sb", JSON.stringify(paramArr), function(result) {
    s_alert.pop("<s:message code='msg.save.succ' />");
    grid2.collectionView.clearChanges();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
});


</script>