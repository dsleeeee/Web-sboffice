<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/base/store/posfunc/"/>

<div class="subCon">

  <%-- 탭 --%>
  <ul class="subTab mb20">
    <%-- 포스기능 사용관리 탭 --%>
    <li><a href="javascript:;" id="funcManageTab" class="on"><s:message code="posFunc.manage.posFunc" /></a></li>
    <%-- 포스기능 인증관리 탭 --%>
    <li><a href="javascript:;" id="funAuthTab" ><s:message code="posFunc.auth.posFunc" /></a></li>
  </ul>
  
  <div class="wj-TblWrap mt20">
     
    <%-- 왼쪽  --%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr oh mr10 pd20" style="height:500px;">
        <div id="posTotListBox" class="w110 fl"></div>
        <div class="fr mb10">
          <%-- 기능복사버튼 --%>
          <button class="btn_skyblue" id="btnCopyFunc"><s:message code="posFunc.copy.func" /></button>
        </div>
        <%-- 그리드 --%>
        <div id="posFuncGrid"></div> 
      </div> 
    </div>
      
    <!--오른쪽-->
    <div class="w50 fr">
      <div class="wj-TblWrapBr oh ml10 pd20" style="height:500px;">
        <div class="fr mb10">
          <%-- up 버튼 --%>
          <button class="btn_up" id="btnUp"><s:message code="cmm.up" /></button>
          <%-- down 버튼 --%>
          <button class="btn_down" id="btnDown"><s:message code="cmm.down" /></button>
          <%-- 좌표자동세팅 버튼 --%>
          <button class="btn_skyblue" id="btnAutoPosition" style="display:none"><s:message code="posFunc.autoSet.position" /></button>
          <%-- 저장버튼 --%>
          <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save" /></button>
        </div>
        <%-- 그리드 --%>
        <div id="useFuncGrid"></div>
        <div id="funcKeyDiv" style="display:none"></div>
      </div>
    </div>
  </div>
</div>

<script>

$(document).ready(function(){
  
  var posUsageEnv = ${ccu.getEnvCodeExcpAll("202")};
  var posTypeEnv  = ${ccu.getEnvCodeExcpAll("204")};
  
  <%-- header --%>
  var posFuncHeader =
    [
      {binding:"storeCd", header:"<s:message code='posFunc.storeCd' />", visible:false, width:"*"},
      {binding:"posNo", header:"<s:message code='posFunc.posNo' />", visible:false, width:"*"},
      {binding:"fnkeyFg", header:"<s:message code='posFunc.fnkeyFg' />", visible:false, width:"*"},
      {binding:"fnkeyNm", header:"<s:message code='posFunc.fnkeyNm' />", width:"*"},
      {binding:"array", header:"<s:message code='posFunc.array' />", width:"*"},
      {binding:"totCnt", header:"<s:message code='posFunc.func.cnt' />", width:"*"},
      {binding:"regCnt", header:"<s:message code='posFunc.regist.cnt' />", width:"*"},
    ];
  
  var useFuncHeader =
    [
      {binding:"fnkeyNo", header:"<s:message code='posFunc.fnkeyNo' />", isReadOnly:true, width:"*"},
      {binding:"fnkeyNm", header:"<s:message code='posFunc.fnkeyNm' />", isReadOnly:true, width:"*"},
      {binding:"existFgBefore", header:"<s:message code='posFunc.existFgBefore' />", visible:false, width:"*"},
      {binding:"existFg", header:"<s:message code='posFunc.existFg' />", visible:false, width:"*"},
      {binding:"posiAdjYn", header:"<s:message code='posFunc.posiAdjYn' />", visible:false, width:"*"},
      {binding:"dispSeq", header:"<s:message code='posFunc.dispSeq' />", visible:false, width:"*"},
      {binding:"colPosi", header:"<s:message code='posFunc.colPosi' />", visible:false, width:"*"},
      {binding:"rowPosi", header:"<s:message code='posFunc.rowPosi' />", visible:false, width:"*"},
      {binding:"width", header:"<s:message code='posFunc.width' />", visible:false, width:"*"},
      {binding:"height", header:"<s:message code='posFunc.height' />", visible:false, width:"*"},
      {binding:"gChk", header:"<s:message code='cmm.chk' />", dataType:wijmo.DataType.Boolean, width:40}
    ];

  <%-- 그리드 생성 --%>
  var posFuncGrid = wgrid.genGrid("#posFuncGrid", posFuncHeader, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var useFuncGrid = wgrid.genGrid("#useFuncGrid", useFuncHeader, "${menuCd}", 1, ${clo.getColumnLayout(1)});

  posFuncGrid.isReadOnly = true;
  useFuncGrid.isReadOnly = false;
  
  var posTotList = ${posTotList};
  var posTotListBox  = wcombo.genCommonBox("#posTotListBox", posTotList);
  
  var posFuncGridList = ${funcList};
  posFuncGrid.itemsSource = new wijmo.collections.CollectionView(posFuncGridList);

   <%-- up 버튼 클릭 --%>
   $("#btnUp").click(function(e){
     for(var i = 0; i < useFuncGrid.collectionView.itemCount; i++ ){
       if(i > 0 && (useFuncGrid.collectionView.items[i].gChk == true)){
         if(useFuncGrid.collectionView.items[i-1].gChk != true){
           var tmpItem = useFuncGrid.collectionView.items[i-1];
           useFuncGrid.collectionView.items[i-1] = useFuncGrid.collectionView.items[i];
           useFuncGrid.collectionView.items[i] = tmpItem;
           useFuncGrid.collectionView.commitEdit();
           useFuncGrid.collectionView.refresh();
         }
       }
     }
   });

   <%-- down 버튼 클릭 --%>
   $("#btnDown").click(function(e){
     for(var i = useFuncGrid.itemsSource.itemCount-1; i >= 0; i-- ){
       if((i < useFuncGrid.itemsSource.itemCount-1) && (useFuncGrid.collectionView.items[i].gChk == true)){
         if(useFuncGrid.collectionView.items[i+1].gChk != true){
           var tmpItem = useFuncGrid.collectionView.items[i+1];
           useFuncGrid.collectionView.items[i+1] = useFuncGrid.collectionView.items[i];
           useFuncGrid.collectionView.items[i] = tmpItem;
           useFuncGrid.collectionView.commitEdit();
           useFuncGrid.collectionView.refresh();
         } 
       }
     }
   });
  
  <%-- 그리드 포맷 --%>
  posFuncGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "fnkeyNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });
  
  <%--//TODO 좌표자동세팅 버튼 클릭 --%>
  $("#btnAutoPosition").click(function(){
    
  });
  
  <%-- 그리드 선택 이벤트 --%>
  posFuncGrid.addEventListener(posFuncGrid.hostElement, 'click', function(e) {
    var ht = posFuncGrid.hitTest(e);
    if ( ht.cellType == wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "fnkeyNm" ) {
        selectedRow = posFuncGrid.rows[ht.row].dataItem;
        <%-- 위치조정여부 'N'이면 버튼 보여주기 --%>
        if(selectedRow.posiAdjYn == "N"){
          $("#btnAutoPosition").show();
        } else {
          $("#btnAutoPosition").hide();
        }
        
        <%-- 결제메뉴의 경우에는 오른쪽 div에 기능키가 들어감 --%>
        if(selectedRow.fnkeyFg == "56") {
          $("#useFuncGrid").hide();
          $("#funcKeyDiv").show();
        } else {
          $("#useFuncGrid").show();
          $("#funcKeyDiv").hide();
          getPosFuncDetail();
        }
      }
    }
  });
  
  <%-- 포스기능 상세 조회 --%>
  function getPosFuncDetail() {
    var param = {};
    param.storeCd = selectedRow.storeCd;
    param.posNo = selectedRow.posNo;
    param.fnkeyFg = selectedRow.fnkeyFg;

    $.postJSON("${baseUrl}" + "use/getPosConfDetail.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;
      /* 
      if(list.length === undefined || list.length == 0) {
        s_alert.pop(result.message);
        return;
      }
       */
      useFuncGrid.itemsSource = new wijmo.collections.CollectionView(list);
      useFuncGrid.collectionView.trackChanges = true;
      changeColSet(list);
    })
    .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 컬럼 셋팅 변경 --%>
  function changeColSet(list){
    for ( var i = useFuncGrid.itemsSource.itemCount-1; i >= 0; i-- ) {
      var item = useFuncGrid.collectionView.items[i];
      if ( item.posiAdjYn ) {
        item.colPosi = "";
        item.rowPosi = "";
        item.width = "";
        item.height = "";
        //TODO row단위 readonly 바꿔야함
        useFuncGrid.collectionView.commitEdit();
        useFuncGrid.collectionView.refresh();
      }
    }
  }
  
  <%-- 저장버튼 클릭 --%>
  $("#btnSave").click(function(){
    var paramArr = new Array();

    for(var i = 0; i < useFuncGrid.collectionView.itemCount; i ++) {  // dispSeq 재설정
      useFuncGrid.collectionView.editItem(useFuncGrid.collectionView.items[i]);

      useFuncGrid.collectionView.items[i].status = 'U';
      useFuncGrid.collectionView.items[i].dispSeq = (i+1);

      useFuncGrid.collectionView.items[i].storeCd = selectedRow.storeCd;
      useFuncGrid.collectionView.items[i].posNo = selectedRow.posNo;
      useFuncGrid.collectionView.items[i].fnkeyFg = selectedRow.fnkeyFg;
      
      useFuncGrid.collectionView.commitEdit();
      paramArr.push(useFuncGrid.collectionView.items[i]);
    }
    
    $.postJSONArray("${baseUrl}" + "use/savePosConf.sb", paramArr, function(result) {
      s_alert.pop("<s:message code='cmm.saveSucc' />");
      useFuncGrid.collectionView.clearChanges();
      getPosFuncDetail();
    },
    function(result) {
      s_alert.pop(result.data.msg);
    });
  });
  
  <%-- 탭 클릭 --%>
  $("#funAuthTab").click(function(){
    location.href = "${baseUrl}" + "use/getPosAuthInfo.sb";
  });
});

</script>
