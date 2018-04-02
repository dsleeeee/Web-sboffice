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
    <!--위즈모 테이블-->
    <div id="theGrid" class="mt10"></div><!--tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요.-->
    <!--//위즈모 테이블-->
  </div>
  <!-- 수정된 데이터 저장을 위한 tracking 그리드 필요 -->
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="editedGrid" style="display:none;"></div><!-- 수정된 데이터 -->
  </div>
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="addedGrid" style="display:none;"></div><!-- 추가 데이터 -->
  </div>
  <div class="wj-TblWrapBr mt10" style="height: 400px;display:none;">
    <div id="removedGrid" style="display:none;"></div><!-- 삭제된 데이터 -->
  </div>
  
</div>

<script>

$(document).ready(function(){
  
  var memoFgData = new wijmo.grid.DataMap([{id:"1", name:"주문"},{id:"2", name:"메뉴"}], 'id', 'name');
  var useYnData  = new wijmo.grid.DataMap([{id:"Y", name:"Y"},{id:"N", name:"N"}], 'id', 'name');
  
  var rdata = 
    [
      {"binding":"chk","header":"<s:message code='chk' />"},
      {"binding":"kitchnMemoCd","header":"<s:message code='kitchnMemoCd' />","maxLength":3},
      {"binding":"kitchnMemoNm","header":"<s:message code='kitchnMemoNm' />","maxLength":30},
      {"binding":"memoFg","header":"<s:message code='memoFg' />", "dataMap":memoFgData},
      {"binding":"useYn","header":"<s:message code='useYn' />", "dataMap":useYnData}
    ];
  
  var kitchenMemoList = ${kitchenMemoList};
  kitchenMemoList.forEach(function(item){ // 체크박스 초기화
    item.chk = false;
  });
  
  //그리드 div, column data, 화면명, 화면 그리드 순서
  var grid            = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var editGrid        = wgrid.genGrid("#editedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var addGrid         = wgrid.genGrid("#addedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var delGrid         = wgrid.genGrid("#removedGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  
  var kitchenMemo = new wijmo.collections.CollectionView(kitchenMemoList);
  kitchenMemo.trackChanges = true;
  
  grid.itemsSource     = kitchenMemo;
  grid.isReadOnly      = false;
  
  editGrid.itemsSource = kitchenMemo.itemsEdited;
  addGrid.itemsSource  = kitchenMemo.itemsAdded;
  delGrid.itemsSource  = kitchenMemo.itemsRemoved;
  
  <%-- 데이터 수정시 코드는 수정 불가 --%>
  grid.beginningEdit.addHandler(function (s, e) {
    // 조회된 데이터는 kitchnMemoCd 수정 불가능
    if(grid.rows[e.row].dataItem.regId == undefined || grid.rows[e.row].dataItem.regId == ""){
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
      // 자리수 체크
      if (val.length > col.maxLength) {
        s_alert.pop(col.header+"는(은) 최대 "+col.maxLength+ "자리 입력 가능합니다.");
      }
      // 숫자만
      if(val.match(/[^0-9]/)){
        s_alert.pop(col.header+"<s:message code='requireNumber'/>");
        s.setCellData(e.row, e.col, val.replace(/[^0-9]/g,""));
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
    
    for(var i=0; i<editGrid.itemsSource.length; i++){
      var editObj = editGrid.itemsSource[i];
      editObj.status = "U";
      paramArr.push(editObj);
    }
    for(var i=0; i<addGrid.itemsSource.length; i++){
      var addObj = addGrid.itemsSource[i];
      addObj.status = "I";
      paramArr.push(addObj);
    }
    for(var i=0; i<delGrid.itemsSource.length; i++){
      var delObj = delGrid.itemsSource[i];
      delObj.status = "D";
      paramArr.push(delObj);
    }
    
    var url = "/adi/etc/kitchenmemo/kitchenmemo/save.sb";
    $.ajax({
      type: "POST",
      url: url,
      data: JSON.stringify(paramArr),
      success: function(result){
        if (result.status === "OK") {
          s_alert.pop("<s:message code='msg.save.succ' />");
          
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