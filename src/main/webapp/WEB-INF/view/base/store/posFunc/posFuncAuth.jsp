<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <div id="posFuncAuthArea" style="display:none;">
    <h2 class="h2_tit" id="posFuncAuthTitle"></h2>

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
      <%-- 포스기능 사용관리 --%>
      <li><a id="posUseManage" href="#"><s:message code="posFunc.manage.posFunc" /></a></li>
      <%-- 포스기능 인증관리 --%>
      <li><a id="posAuthManage"  href="#" class="on"><s:message code="posFunc.auth.posFunc" /></a></li>
    </ul>

    <%-- 왼쪽  --%>
    <div class="w30 fl ">
      <div class="wj-TblWrapBr oh mr10 pd20" style="height:560px;">
        <div class="updownSet mb10">
          <span class="fl bk lh30"><s:message code="posFunc.fnkeyFg" /></span>
        </div>
        <%-- 그리드 --%>
        <div id="funcListGrid"></div>
      </div>
    </div>

    <%-- 오른쪽 --%>
    <div class="w70 fr">
      <div class="wj-TblWrapBr oh ml20 pd20" style="height:560px;">
        <div class="updownSet mb10">
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
        <div id="funcAuthGrid"></div>
        <div id="funcKeyDiv" style="display:none"></div>
      </div>
    </div>
  </div>

<%-- grid button --%>
<div id="tplBtnEditMode" style="display:none">
  <button id="btnEnvSetting" class="btn btn-primary btn-sm">
    <span class="glyphicon glyphicon-ok"></span> <s:message code='posFunc.regist.auth' />
  </button>
</div>

<script>

  var selectedFnkey;
  var selectedRow;

  <%-- header --%>
  var funcListHeader =
      [
        {binding:"nmcodeCd", header:"<s:message code='posFunc.fnkeyFg' />", visible:false, width:"*"},
        {binding:"nmcodeNm", header:"<s:message code='posFunc.fnkeyNm' />", width:"*"},
      ];

  var funAuthHeader =
      [
        {binding:"fnkeyNo", header:"<s:message code='posFunc.fnkeyNo' />", isReadOnly:true, width:"*"},
        {binding:"fnkeyNm", header:"<s:message code='posFunc.fnkeyNm' />", isReadOnly:true, width:"*"},
        {binding:"dispSeq", header:"<s:message code='posFunc.dispSeq' />", visible:false, width:"*"},
        {binding:"useYn", header:"<s:message code='posFunc.useYn' />", dataType:wijmo.DataType.Boolean, width:"*"},
        {binding:"authYn", header:"<s:message code='posFunc.authYn' />", dataType:wijmo.DataType.Boolean, width:"*"},
        {binding:"buttons", header:"<s:message code='posFunc.setting.auth' />", width:"*"},
        {binding:"gChk", header:"<s:message code='cmm.chk' />", dataType:wijmo.DataType.Boolean, width:"*"}
      ];

  <%-- 그리드 생성 --%>
  var funcListGrid = wgrid.genGrid("#funcListGrid", funcListHeader);
  var funcAuthGrid = wgrid.genGrid("#funcAuthGrid", funAuthHeader);

  funcListGrid.isReadOnly = true;
  funcAuthGrid.isReadOnly = false;

  <%-- 그리드 포맷 --%>
  funcListGrid.formatItem.addHandler(function(s, e) {
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding === "nmcodeNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  funcAuthGrid.formatItem.addHandler(function(s, e) {
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;

      if( col.binding === "buttons"){
        e.cell.innerHTML = document.getElementById('tplBtnEditMode').innerHTML;
        e.cell.dataItem = item;
      }

    }
  });

  <%-- 그리드 선택 이벤트 --%>
  funcListGrid.addEventListener(funcListGrid.hostElement, 'click', function(e) {
    var ht = funcListGrid.hitTest(e);
    if ( ht.cellType === wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      if( col.binding === "nmcodeNm" ) {
        selectedRow = funcListGrid.rows[ht.row].dataItem;
        getPosFuncAuthDetail();
      }
    }
  });

  <%-- 환경설정 버튼 클릭 --%>
  funcAuthGrid.addEventListener(funcAuthGrid.hostElement, 'click', function(e) {
    var ht = funcAuthGrid.hitTest(e);
    var row = ht.row;
    if( ht.cellType === wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding === "buttons") {
        selectedFnkey = funcAuthGrid.rows[ht.row].dataItem;
        openSetAuthLayer();
      }
    }
  });

  <%-- 포스기능 인증관리 화면 보여주기 --%>
  function showPosFuncAuth(){
    $("#posFuncAuthArea").show();
    getPosAuthList();
    // 선택매장 표시
    $("#posFuncAuthTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);
  }

  <%-- 포스기능 인증 목록 조회 --%>
  function getPosAuthList() {

    var param = {};

    $.postJSON("/base/store/posfunc/auth/getPosFuncList.sb", param,
        function(result) {

          var list = result.data.list;
          funcListGrid.itemsSource = new wijmo.collections.CollectionView(list);
          funcAuthGrid.itemsSource = new wijmo.collections.CollectionView([]);
          funcListGrid.collectionView.trackChanges = true;

          selectedRow = "";
        },
        function (result) {
          s_alert.pop(result.message);
          return;
        }
    );
  }

  <%-- 포스기능인증 목록 상세 조회 --%>
  function getPosFuncAuthDetail() {

    var param = {};

    param.storeCd = selectedStore.storeCd;
    param.fnkeyFg = selectedRow.nmcodeCd;

    $.postJSON("/base/store/posfunc/auth/getPosConfAuthDetail.sb", param,
        function(result) {
          var list = result.data.list;
          funcAuthGrid.itemsSource = new wijmo.collections.CollectionView(list);
          funcAuthGrid.collectionView.trackChanges = true;
        },
        function (result) {
          s_alert.pop(result.message);
          return;
        }
    );
  }


  <%-- 저장버튼 클릭 --%>
  $("#posFuncAuthArea #btnSave").click(function(){

    if(!selectedRow) {
      s_alert.pop("<s:message code='posFunc.require.fnkeyNo' />");
      return;
    }

    var paramArr = new Array();

    for(var i = 0; i < funcAuthGrid.collectionView.itemCount; i ++) {
      funcAuthGrid.collectionView.editItem(funcAuthGrid.collectionView.items[i]);

      funcAuthGrid.collectionView.items[i].status = 'U';
      funcAuthGrid.collectionView.items[i].dispSeq = (i+1);

      funcAuthGrid.collectionView.items[i].storeCd = selectedStore.storeCd;
      funcAuthGrid.collectionView.items[i].fnkeyFg = selectedRow.nmcodeCd;

      funcAuthGrid.collectionView.commitEdit();
      paramArr.push(funcAuthGrid.collectionView.items[i]);
    }

    $.postJSONArray("/base/store/posfunc/use/savePosConf.sb", paramArr,
        function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          funcAuthGrid.collectionView.clearChanges();
          getPosFuncDetail();
        },
        function(result) {
          s_alert.pop(result.message);
        }
    );
  });


  <%-- up 버튼 클릭 --%>
  $("#posFuncAuthArea #btnUp").click(function(e){
    for(var i = 0; i < funcAuthGrid.collectionView.itemCount; i++ ){
      if(i > 0 && (funcAuthGrid.collectionView.items[i].gChk === true)){
        if(funcAuthGrid.collectionView.items[i-1].gChk !== true){
          var tmpItem = funcAuthGrid.collectionView.items[i-1];
          funcAuthGrid.collectionView.items[i-1] = funcAuthGrid.collectionView.items[i];
          funcAuthGrid.collectionView.items[i] = tmpItem;
          funcAuthGrid.collectionView.commitEdit();
          funcAuthGrid.collectionView.refresh();
        }
      }
    }
  });

  <%-- down 버튼 클릭 --%>
  $("#posFuncAuthArea #btnDown").click(function(e){
    for(var i = funcAuthGrid.itemsSource.itemCount-1; i >= 0; i-- ){
      if((i < funcAuthGrid.itemsSource.itemCount-1) && (funcAuthGrid.collectionView.items[i].gChk === true)){
        if(funcAuthGrid.collectionView.items[i+1].gChk !== true){
          var tmpItem = funcAuthGrid.collectionView.items[i+1];
          funcAuthGrid.collectionView.items[i+1] = funcAuthGrid.collectionView.items[i];
          funcAuthGrid.collectionView.items[i] = tmpItem;
          funcAuthGrid.collectionView.commitEdit();
          funcAuthGrid.collectionView.refresh();
        }
      }
    }
  });

  function hidePosFuncAuth(){
    $("#posFuncAuthArea").hide();
  }

  <%-- 탭 클릭 --%>
  $("#posFuncAuthArea #posUseManage").click(function(){
    hidePosFuncAuth();
    showPosFuncList()
  });
</script>
