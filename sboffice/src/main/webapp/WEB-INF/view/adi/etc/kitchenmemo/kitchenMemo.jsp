<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <div class="updownSet oh">
    <span class="fl bk lh30">${menuNm}</span>
    <div class="txtIn">
      <button class="btn_skyblue" id="addBtn">
        <s:message code="label.cmm.add" />
      </button>
      <button class="btn_skyblue" id="deleteBtn">
        <s:message code="label.cmm.delete" />
      </button>
      <button class="btn_skyblue" id="saveBtn">
        <s:message code="label.cmm.save" />
      </button>
    </div>
  </div>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid" class="mt10"></div>
  </div>
<<<<<<< HEAD
=======
  <!-- 수정된 데이터 저장을 위한 tracking 그리드 필요 -->
  <!-- 
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="editedGrid" style="display:none;"></div>수정된 데이터
  </div>
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="addedGrid" style="display:none;"></div>추가 데이터
  </div>
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="removedGrid" style="display:none;"></div>삭제된 데이터
  </div>
   -->
>>>>>>> refs/heads/menu
</div>

<script>
$(document).ready(function(){
  var memoFgData = new wijmo.grid.DataMap([{id:"1", name:"주문"},{id:"2", name:"메뉴"}], 'id', 'name');
  var useYnData  = new wijmo.grid.DataMap([{id:"Y", name:"Y"},{id:"N", name:"N"}], 'id', 'name');
  var rdata = 
    [
      {binding:"chk", header:"<s:message code='chk' />"},
      {binding:"kitchnMemoCd", header:"<s:message code='kitchnMemoCd' />", maxLength:3},
      {binding:"kitchnMemoNm", header:"<s:message code='kitchnMemoNm' />", maxLength:30},
      {binding:"memoFg", header:"<s:message code='memoFg' />", dataMap:memoFgData},
      {binding:"useYn", header:"<s:message code='useYn' />", dataMap:useYnData}
    ];
  
  var kitchenMemoList = ${kitchenMemoList};
<<<<<<< HEAD
  
  <%-- 체크박스 초기화 --%>
=======

  <%-- 데이터 초기화 (체크박스 초기화) --%>
>>>>>>> refs/heads/menu
  kitchenMemoList.forEach(function(item){
    item.chk = false;
  });
  
<<<<<<< HEAD
  <%-- 그리드 div, column data, 화면명, 화면 그리드 순서 --%>
  var grid             = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var kitchenMemo      = new wijmo.collections.CollectionView(kitchenMemoList);
=======
  <%-- 그리드 생성 : 그리드 div, column data, 화면명, 화면 그리드 순서 --%>
  var grid            = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  
  /* 
  var editGrid        = wgrid.genGrid("#editedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var addGrid         = wgrid.genGrid("#addedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var delGrid         = wgrid.genGrid("#removedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  */
  
  var kitchenMemo = new wijmo.collections.CollectionView(kitchenMemoList);
>>>>>>> refs/heads/menu
  kitchenMemo.trackChanges = true;
  
  grid.itemsSource     = kitchenMemo;
  grid.isReadOnly      = false;
  
<<<<<<< HEAD
  <%-- 데이터 수정시 코드는 수정 불가 --%>
=======
  /* 
  editGrid.itemsSource = kitchenMemo.itemsEdited;\
  addGrid.itemsSource  = kitchenMemo.itemsAdded;
  delGrid.itemsSource  = kitchenMemo.itemsRemoved;
  */
  
  <%-- 데이터 수정시 kitchnMemoCd 컬럼은 수정 불가 --%>
>>>>>>> refs/heads/menu
  grid.beginningEdit.addHandler(function (s, e) {
<<<<<<< HEAD
    <%-- 조회된 데이터는 kitchnMemoCd 수정 불가능 --%>
    if(grid.rows[e.row].dataItem.regId == undefined || grid.rows[e.row].dataItem.regId == ""){
=======
    if(grid.rows[e.row].dataItem.regId == undefined || grid.rows[e.row].dataItem.regId == ""){  // insert
>>>>>>> refs/heads/menu
      e.cancel = false;
    }else{
      if(e.col != 1){
        e.cancel = false;
      }else{
        e.cancel = true;
      }
    }
  });
  
  <%-- validation --%>
  grid.cellEditEnded.addHandler(function (s, e){
    var col = s.columns[e.col];
    if(col.maxLength){
      var val = s.getCellData(e.row, e.col);
      if (val.length > col.maxLength) { <%-- 자리수 체크 --%>
        s_alert.pop(col.header+"는(은) 최대 "+col.maxLength+ "자리 입력 가능합니다.");
      }
      if(col.binding == "kitchnMemoCd") { <%-- 숫자만 --%>
        if(val.match(/[^0-9]/)){
          s_alert.pop(col.header+"<s:message code='requireNumber'/>");
          s.setCellData(e.row, e.col, val.replace(/[^0-9]/g,""));
        }
      }
    }
  });
  
  <%-- 추가 --%>
  $("#addBtn").click(function( e ){
    var newItem = grid.collectionView.addNew();
    newItem.chk = false;
    grid.collectionView.commitNew();
  });
  
  <%-- 삭제 --%>
  $("#deleteBtn").click(function( e ){
    for(var i = kitchenMemo.items.length-1; i >= 0; i-- ){
      var item = kitchenMemo.items[i];
      if(item.chk){
        kitchenMemo.removeAt(i);
      }
    }
  });
  
  <%-- 저장 --%>
  $("#saveBtn").click(function( e ){

    var paramArr = new Array();
    
    for(var i=0; i<grid.collectionView.itemsEdited.length; i++){
      grid.collectionView.itemsEdited[i].status = "U";
      paramArr.push(grid.collectionView.itemsEdited[i]);
    }
<<<<<<< HEAD
=======
    
>>>>>>> refs/heads/menu
    for(var i=0; i<grid.collectionView.itemsAdded.length; i++){
      grid.collectionView.itemsAdded[i].status = "I";
      paramArr.push(grid.collectionView.itemsAdded[i]);
    }
<<<<<<< HEAD
    for(var i=0; i<grid.collectionView.itemsRemoved.length; i++){
      grid.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(grid.collectionView.itemsRemoved[i]);
=======

    for(var i=0; i<grid.collectionView.itemsRemoved.length; i++){
      grid.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(grid.collectionView.itemsRemoved[i]);
    }
    
    /* 
    for(var i=0; i<delGrid.itemsSource.length; i++){
      var delObj = delGrid.itemsSource[i];
      delObj.status = "D";
      paramArr.push(delObj);
>>>>>>> refs/heads/menu
    }
    */
    
    var url = "/adi/etc/kitchenmemo/kitchenmemo/save.sb";
    $.ajax({
      type: "POST",
      url: url,
      data: JSON.stringify(paramArr),
      success: function(result){
        if (result.status === "OK") {
          s_alert.pop("<s:message code='msg.save.succ' />");
<<<<<<< HEAD
          grid.collectionView.clearChanges();
=======
          
          grid.collectionView.refresh();
          
          console.log(grid.collectionView.itemsEdited)
          
          /* 
          var cvEdit = new wijmo.collections.CollectionView(editGrid.itemsSource);
          var cvAdd = new wijmo.collections.CollectionView(addGrid.itemsSource);
          var cvDel = new wijmo.collections.CollectionView(delGrid.itemsSource);
          
          for(var i = cvEdit.items.length-1; i >= 0; i-- ){
            cvEdit.removeAt(i);
          }
          for(var i = cvAdd.items.length-1; i >= 0; i-- ){
            cvAdd.removeAt(i);
          }
          for(var i = cvDel.items.length-1; i >= 0; i-- ){
            cvDel.removeAt(i);
          }
           */
>>>>>>> refs/heads/menu
        } else if (result.status === "FAIL"){
          s_alert.pop(result.data.msg);
        }
      },
      cache: false,
      dataType: "json",
      contentType : 'application/json'
    });
  });
});
</script>