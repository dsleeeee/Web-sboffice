<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/base/store/posfunc/"/>

<div class="subCon">

  <%-- 탭 --%>
  <ul class="subTab mb20">
    <%-- 포스기능 사용관리 탭 --%>
    <li><a href="javascript:;" id="funcManageTab" ><s:message code="posFunc.manage.posFunc" /></a></li>
    <%-- 포스기능 인증관리 탭 --%>
    <li><a href="javascript:;" id="funAuthTab" class="on"><s:message code="posFunc.auth.posFunc" /></a></li>
  </ul>

  <div class="wj-TblWrap mt20">

    <%-- 왼쪽  --%>
    <div class="w20 fl">
      <div class="wj-TblWrapBr oh mr10 pd20" style="height:500px;">
        <div class="updownSet oh mt20">
          <span class="fl bk lh30"><s:message code="posFunc.fnkeyFg" /></span>
        </div>
        <%-- 그리드 --%>
        <div id="funcListGrid"></div>
      </div>
    </div>

    <!--오른쪽-->
    <div class="w80 fr">
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

<%-- grid button --%>
<div id="tplBtnEditMode" style="display:none">
  <button id="btnEnvSetting" class="btn btn-primary btn-sm">
    <span class="glyphicon glyphicon-ok"></span> <s:message code='hqBrand.envSetting' />
  </button>
</div>

<script>

var selectedRow;
var selectedFnkey;

$(document).ready(function(){

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
  var funcListGrid = wgrid.genGrid("#funcListGrid", funcListHeader, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var funcAuthGrid = wgrid.genGrid("#useFuncGrid", funAuthHeader, "${menuCd}", 1, ${clo.getColumnLayout(1)});

  funcListGrid.isReadOnly = true;
  funcAuthGrid.isReadOnly = false;

  <%-- 그리드 데이터 조회 --%>
  var param = {};

  $.postJSON("${baseUrl}" + "auth/getPosFuncList.sb", param, function(result) {
    console.log(result);
    if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
    }
    var list = result.data.list;
    funcListGrid.itemsSource = new wijmo.collections.CollectionView(list);
    funcListGrid.collectionView.trackChanges = true;
    //changeColSet(list);
  }
  ,function(){
      s_alert.pop("Ajax Fail");
  });

  <%-- up 버튼 클릭 --%>
  $("#btnUp").click(function(e){
    for(var i = 0; i < funcAuthGrid.collectionView.itemCount; i++ ){
      if(i > 0 && (funcAuthGrid.collectionView.items[i].gChk == true)){
        if(funcAuthGrid.collectionView.items[i-1].gChk != true){
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
  $("#btnDown").click(function(e){
    for(var i = funcAuthGrid.itemsSource.itemCount-1; i >= 0; i-- ){
      if((i < funcAuthGrid.itemsSource.itemCount-1) && (funcAuthGrid.collectionView.items[i].gChk == true)){
        if(funcAuthGrid.collectionView.items[i+1].gChk != true){
          var tmpItem = funcAuthGrid.collectionView.items[i+1];
          funcAuthGrid.collectionView.items[i+1] = funcAuthGrid.collectionView.items[i];
          funcAuthGrid.collectionView.items[i] = tmpItem;
          funcAuthGrid.collectionView.commitEdit();
          funcAuthGrid.collectionView.refresh();
        }
      }
    }
  });

  <%-- 그리드 포맷 --%>
  funcListGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "nmcodeNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  funcAuthGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "buttons"){
        e.cell.innerHTML = document.getElementById('tplBtnEditMode').innerHTML;
        e.cell.dataItem = item;
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  funcListGrid.addEventListener(funcListGrid.hostElement, 'click', function(e) {
    var ht = funcListGrid.hitTest(e);
    if ( ht.cellType == wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "nmcodeNm" ) {
        selectedRow = funcListGrid.rows[ht.row].dataItem;
        getPosFuncAuthDetail();
      }
    }
  });

  <%-- 환경설정 버튼 클릭 --%>
  funcAuthGrid.addEventListener(funcAuthGrid.hostElement, 'click', function(e) {
    var ht = funcAuthGrid.hitTest(e);
    var row = ht.row;
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "buttons") {
        selectedFnkey = funcAuthGrid.rows[ht.row].dataItem;
        openSetAuthLayer();
      }
    }
  });

  <%-- 포스기능인증 목록 조회 --%>
  function getPosFuncAuthDetail() {
    var param = {};

    param.fnkeyFg = selectedRow.nmcodeCd;

    $.postJSON("${baseUrl}" + "auth/getPosConfAuthDetail.sb", param, function(result) {

      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;

      funcAuthGrid.itemsSource = new wijmo.collections.CollectionView(list);
      funcAuthGrid.collectionView.trackChanges = true;
    }
    ,function(){
        s_alert.pop("Ajax Fail");
    });
  }

  <%-- 저장버튼 클릭 --%>
  $("#btnSave").click(function(){

    if(!selectedRow) {  //TODO 확인
        s_alert.pop("<s:message code='posFunc.require.fnkeyNo' />");
        return;
    }

    var paramArr = new Array();

    for(var i = 0; i < funcAuthGrid.collectionView.itemCount; i ++) {
      funcAuthGrid.collectionView.editItem(funcAuthGrid.collectionView.items[i]);

      funcAuthGrid.collectionView.items[i].status = 'U';
      funcAuthGrid.collectionView.items[i].dispSeq = (i+1);

      funcAuthGrid.collectionView.items[i].storeCd = selectedRow.storeCd;
      funcAuthGrid.collectionView.items[i].posNo = selectedRow.posNo;
      funcAuthGrid.collectionView.items[i].fnkeyFg = selectedRow.fnkeyFg;

      funcAuthGrid.collectionView.commitEdit();
      paramArr.push(funcAuthGrid.collectionView.items[i]);
    }

    $.postJSONArray("${baseUrl}" + "use/savePosConf.sb", paramArr, function(result) {
      s_alert.pop("<s:message code='cmm.saveSucc' />");
      funcAuthGrid.collectionView.clearChanges();
      getPosFuncDetail();
    },
    function(result) {
      s_alert.pop(result.message);
    });
  });

  <%-- 탭 클릭 --%>
  $("#funcManageTab").click(function(){
    location.href = "${baseUrl}" + "use/view.sb";
  });
});
</script>

<%-- 인증허용대상 설정 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuthSetting.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
