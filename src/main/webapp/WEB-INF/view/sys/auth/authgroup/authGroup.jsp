<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sys/auth/authGroup/authGroup/" />

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" >
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <%-- 그룹명 --%>
        <th><s:message code="authGroup.authGrpNm" /></th>
        <td>
          <div class="sb-select w100">
            <div id="grpNm" class="sb-input" onkeyup="fnNxBtnSearch();"></div>
          </div>
        </td>
        <%-- 사용여부 --%>
        <th><s:message code="cmm.useYn" /></th>
        <td>
          <div class="sb-select w100">
            <div id="useYn"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 예외 관리 // TODO 검색조건에 누락됨 --%>
        <th><s:message code="authGroup.exceptMng" /></th>
        <td>
          <div class="sb-select w100">
            <div id="availCombo"></div>
          </div>
        </td>
        <%-- 예외 관리 --%>
        <th><s:message code="authGroup.exceptMng" /></th>
        <td>
          <div class="sb-select w80" style="float: left;">
            <div id="userId"></div>
          </div>
          <a href="#" class="btn_grayS ml5" id="btnExceptMng"><s:message code="authGroup.exceptMng"/></a>
        </td>
      </tr>
    </tbody>
  </table>

  <%--2단--%>
  <div class="wj-TblWrap mt10">

    <%--권한그룹--%>
    <label id="authOrgnTree" style="display: none"></label>
    <%--그룹코드--%>
    <label id="grpCdTree" style="display: none"></label>
    <%--left--%>
    <div class="w60 fl">
      <div class="wj-TblWrapBr mr10 pd10" style="height:500px; overflow-y: hidden;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.authGroup"/></span>
          <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add"/></button>
          <button id="btnDel" class="btn_skyblue"><s:message code="cmm.del"/></button>
          <button id="btnSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div id="theGrid" style="height:450px; overflow-y: auto;"></div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
    <%--//left--%>

    <%--right--%>
    <div class="w40 fr">
      <div class="wj-TblWrapBr pd10" style="height:500px; overflow-y: auto;">
        <div class="updownSet oh mb10">
          <span id="groupInfo" class="fl bk lh30 "></span>
          <span class="fl bk lh30 ml5"><s:message code="authGroup.resrcInfo"/></span>
          <button id="srchAuthOrgn" class="btn_skyblue"><s:message code="authGroup.srchAuthOrgn"/></button>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 트리  // todo height 조정--%>
        <%--<div id="treeResrce" style="height:430px; "></div>--%>
        <div class="theTreeAll_cls" id="treeResrce" style="height:auto;overflow: hidden; "></div>
        <%--//위즈모 트리--%>
      </div>
    </div>
    <%--//right--%>
  </div>
  <%--//2단--%>

</div>

<script>
  $(document).ready(function() {

    <%-- 권한 그룹 --%>
    var targetAllFg  = new wijmo.grid.DataMap([
      {id: "A", name: "<s:message code='authGroup.all'/>"},
      {id: "P", name: "<s:message code='authGroup.part'/>"}
    ], 'id', 'name');
    var rdata = [
        {binding:"authGrpCd", header:"<s:message code='authGroup.authGrpCd' />", width:70, isReadOnly:true},
        {binding:"authGrpNm", header:"<s:message code='authGroup.authGrpNm' />", width:100, isRequired:true},
      <c:if test="${orgnFg == 'MASTER'}">
        {binding:"targetAllFg", header:"<s:message code='authGroup.targetAllFg' />", width:100, dataMap:targetAllFg},
        {binding:"targetOrgn", header:"<s:message code='authGroup.targetOrgn' />", width:100},
        {binding:"targetOrgnNm", header:"<s:message code='authGroup.targetOrgnNm' />", width:100, isReadOnly:true},
      </c:if>
        {binding:"remark", header:"<s:message code='cmm.remark' />", width:100},
        {binding:"useYn", header:"<s:message code='cmm.use' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkAdmin", header:"<s:message code='authGroup.admin' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkDist", header:"<s:message code='authGroup.dist' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkAgency", header:"<s:message code='authGroup.agency' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkHq", header:"<s:message code='authGroup.hq' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkStore", header:"<s:message code='authGroup.store' />", width:50, dataType:wijmo.DataType.Boolean},
        {binding:"chkExStore", header:"<s:message code='authGroup.exStore' />", width:60, dataType:wijmo.DataType.Boolean}
        <%--,{binding:"authOrgn", header:"<s:message code='webMenu.authOrgn' />", width:70, visible:false}--%>
    ];
    var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    grid.isReadOnly  = false;
    grid.imeEnabled = true;
    var grpNm        = wcombo.genInput("#grpNm");
    var useYn        = wcombo.genCommonBox("#useYn", ${ccu.getCommCode("067")});
    var availCombo   = wcombo.genCommonBox("#availCombo", ${availAuthGrp});
    var userId       = wcombo.genInput("#userId");

    <%-- 메뉴 트리 생성 --%>
    var tree = new wijmo.nav.TreeView('#treeResrce', {
      displayMemberPath: 'resrceDisp',
      childItemsPath: 'items',
      expandOnClick : true,
      isReadOnly: true,
      showCheckboxes: true
    });

    var view = new wijmo.collections.CollectionView();
    <%-- 트리 체크박스 초기화 --%>
    tree.loadedItems.addHandler(function(s, e) {
      s.collapseToLevel(0);
      <%-- //TODO 느림.. --%>
      for (var nd = tree.getFirstNode(); nd; nd = nd.next()) {
        //console.log( new Date().getTime());
        if(!isEmpty(nd)){
          nd.isChecked = nd.dataItem.authFg;
        }
      }
      view = new wijmo.collections.CollectionView(tree.checkedItems);
    });

    <%-- 트리에 아이템 체크 상태가 바뀌었을 때 CollectionView에 반영 --%>
    tree.checkedItemsChanged.addHandler(function(s, e) {

      view.itemsAdded.clear();
      view.itemsRemoved.clear();
      for(var i = 0; i < tree.checkedItems.length; i++) {
        if(!view.contains(tree.checkedItems[i])) {
          view.itemsAdded.push(tree.checkedItems[i]);
        }
      }
      var viewNew = new wijmo.collections.CollectionView(tree.checkedItems);
      for(var i = 0; i < view.items.length; i++) {
        if(!viewNew.contains(view.items[i])) {
          view.itemsRemoved.push(view.items[i]);
        }
      }
    });

    <%-- 그리드 컬럼 특수 기능 처리 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "authGrpCd" ) {
          //TODO 링크 표시 + cursor-hand
          wijmo.addClass(e.cell, 'wijLink');
        }
        else if( col.binding == "useYn" ) {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.useYn == true || item.useYn == "Y" ? 'checked' : '') + '>';
        }
        else if (col.binding == "chkAdmin") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkAdmin == true || item.chkAdmin == "Y" ? 'checked' : '') + '/>';
        } else if (col.binding == "chkDist") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkDist == true || item.chkDist == "Y" ? 'checked' : '') + '/>';
        } else if (col.binding == "chkAgency") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkAgency == true || item.chkAgency == "Y" ? 'checked' : '') + '/>';
        } else if (col.binding == "chkHq") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkHq == true || item.chkHq == "Y" ? 'checked' : '') + '/>';
        } else if (col.binding == "chkStore") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkStore == true || item.chkStore == "Y" ? 'checked' : '') + '/>';
        } else if (col.binding == "chkExStore") {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chkExStore == true || item.chkExStore == "Y" ? 'checked' : '') + '/>';
        }

      }
    });

    <%-- 그리드 선택 이벤트 --%>
    grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
      var ht = grid.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding == "authGrpCd") {
          var param = {};
          $("#treeResrce").css("display", "");
          param.authGrpCd = grid.cells.getCellData(ht.row, ht.col, true);
          $("#groupInfo").html('[' + grid.cells.getCellData(ht.row, ht.col, true) + ']' + grid.cells.getCellData(ht.row, ht.col +1, true));

          var arr = ['A','P','C','H','S','I'];
          var authArr = [];
          for(var i=0; i<arr.length; i ++){
            if(grid.cells.getCellData(ht.row, ht.col+i+7, true) === 'Y'){
              authArr.push(arr[i]);
            }
          }

          var authStr = ''
          for(var i=0; i<authArr.length; i ++){
            if(authArr.length-1 === i ) {
              authStr += authArr[i]
            }else{
              authStr += authArr[i] + "|"
            }
          }

          $("#authOrgnTree").text(authStr);
          $("#grpCdTree").text(grid.cells.getCellData(ht.row, ht.col, true));

          if(param.authGrpCd != '') {
            tree.itemsSource = new Array();
            tree.refresh();
            $.postJSON("${baseUrl}" + "listResrce.sb", param, function(result) {
              tree.itemsSource = result.data.list;
              <%-- 트리에서 저장 예외관리 시 키로 사용--%>
              tree.currentAuthGrpCd = param.authGrpCd;
            },
            function(result) {
              s_alert.pop(result.message);
            });
          }
        }
      }
    })

    grid.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "chkAdmin" || col.binding === "chkDist" ||
                col.binding === "chkAgent" || col.binding === "chkHq" || col.binding === "chkStore" || col.binding === "chkExStore") {
          checkedbox(item, col.binding);
        }
      }
      s.collectionView.commitEdit();
    });

    function checkedbox(item, bind){
      item.bind = true;
    }

    <%-- 리스트 조회 --%>
    $("#btnSearch").click(function(e){
      search();
    });

    <%-- 리스트 조회 --%>
    function search() {
      var param = {};
      param.grpNm = grpNm.text;
      param.useYn = useYn.selectedValue;
      wgrid.getGridData("${baseUrl}" + "list.sb", param, grid);
      $("#groupInfo").html('');
      $("#treeResrce").css("display", "none");
    }

    <%-- 권한 그룹 추가 --%>
    $("#btnAdd").click(function(e){
      grid.collectionView.newItemCreator = function() {
        return {
          useYn: 'Y'
        }
      };
      var newItem = grid.collectionView.addNew();
      grid.collectionView.commitNew();
    });
    <%-- 권한 그룹  삭제 --%>
    $("#btnDel").click(function(e){
      for(var selected = 0; selected < grid.selectedItems.length; selected++ ) {
        var rows = grid.selectedRows[selected];
        var item = rows.dataItem;
        if(item == null) {
          grid.collectionView.cancelNew();
        }
        else {
          grid.collectionView.remove(item);
        }
      }
    });

    <%-- 권한 그룹 저장 --%>
    $("#btnSave").click(function(e){

      var paramArr = new Array();
      var gridView = grid.collectionView;

      for(var i = 0; i < gridView.itemsAdded.length; i++) {
        if(gridView.itemsAdded[i].authGrpNm == null || gridView.itemsAdded[i].authGrpNm == ""){
          s_alert.pop("<s:message code='authGroup.emptyAuthGrpNm'/>");
          return;
        }
        if(orgnFg == 'MASTER' && gridView.itemsAdded[i].targetAllFg == null || gridView.itemsAdded[i].targetAllFg == ""){
          s_alert.pop("<s:message code='authGroup.emptyTargetAllFg'/>");
          return;
        }
        gridView.itemsAdded[i].status = 'I';
        gridView.itemsAdded[i].authOrgn = '';
        if(gridView.itemsAdded[i].chkAdmin){
          gridView.itemsAdded[i].authOrgn += "A";
        }
        if(gridView.itemsAdded[i].chkDist){
          gridView.itemsAdded[i].authOrgn += "P";
        }
        if(gridView.itemsAdded[i].chkAgency){
          gridView.itemsAdded[i].authOrgn += "C";
        }
        if(gridView.itemsAdded[i].chkHq){
          gridView.itemsAdded[i].authOrgn += "H";
        }
        if(gridView.itemsAdded[i].chkStore){
          gridView.itemsAdded[i].authOrgn += "S";
        }
        if(gridView.itemsAdded[i].chkExStore){
          gridView.itemsAdded[i].authOrgn += "I";
        }
        paramArr.push(gridView.itemsAdded[i]);
      }

      for(var i = 0; i < gridView.itemsEdited.length; i++) {
        if(gridView.itemsEdited[i].authGrpNm == null || gridView.itemsEdited[i].authGrpNm == ""){
          s_alert.pop("<s:message code='authGroup.emptyAuthGrpNm'/>");
          return;
        }
        if(orgnFg == 'MASTER' && gridView.itemsEdited[i].targetAllFg == null || gridView.itemsEdited[i].targetAllFg == ""){
          s_alert.pop("<s:message code='authGroup.emptyTargetAllFg'/>");
          return;
        }
        gridView.itemsEdited[i].status    = 'U';
        gridView.itemsEdited[i].authOrgn  = "";
        if(gridView.itemsEdited[i].chkAdmin){
          gridView.itemsEdited[i].authOrgn += "A";
        }
        if(gridView.itemsEdited[i].chkDist){
          gridView.itemsEdited[i].authOrgn += "P";
        }
        if(gridView.itemsEdited[i].chkAgency){
          gridView.itemsEdited[i].authOrgn += "C";
        }
        if(gridView.itemsEdited[i].chkHq){
          gridView.itemsEdited[i].authOrgn += "H";
        }
        if(gridView.itemsEdited[i].chkStore){
          gridView.itemsEdited[i].authOrgn += "S";
        }
        if(gridView.itemsEdited[i].chkExStore){
          gridView.itemsEdited[i].authOrgn += "I";
        }
        paramArr.push(gridView.itemsEdited[i]);
      }
      for(var i = 0; i < gridView.itemsRemoved.length; i++) {
        gridView.itemsRemoved[i].status = 'D';
        paramArr.push(gridView.itemsRemoved[i]);
      }
      if(paramArr.length <= 0) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      // return false;

      $.postJSONArray("${baseUrl}" + "save.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        // gridView.clearChanges();
        gridView.refresh();
        search();
      },
      function(result) {
        s_alert.pop(result.message);
      });
    });

    <%-- 리스스 정보 저장 --%>
    $("#btnResrceSave").click(function(e){
      if(isEmpty(tree.currentAuthGrpCd)) {
        s_alert.pop("<s:message code='authGroup.authGroup' /><s:message code='cmm.require.select' />");
        return;
      }

      var paramArr = new Array();
      for(var i = 0; i < view.itemsAdded.length; i++) {
        view.itemsAdded[i].status = 'I';
        view.itemsAdded[i].authGrpCd = tree.currentAuthGrpCd;
        paramArr.push(view.itemsAdded[i]);
      }
      for(var i = 0; i < view.itemsRemoved.length; i++) {
        view.itemsRemoved[i].status = 'D';
        view.itemsRemoved[i].authGrpCd = tree.currentAuthGrpCd;
        paramArr.push(view.itemsRemoved[i]);
      }


      //console.log(paramArr);
      if(paramArr.length <= 0) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      $.postJSONArray("${baseUrl}" + "saveResrce.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        view.clearChanges();
      },
      function(result) {
        s_alert.pop(result.message);
      });

    });

    <%-- 권한구분조회 --%>
    $("#srchAuthOrgn").click(function(e){
      if(isEmpty(tree.currentAuthGrpCd)) {
        s_alert.pop("<s:message code='authGroup.authGroup' /><s:message code='cmm.require.select' />");
        return;
      }

      var param = {};
      param.authGrpCd = $("#grpCdTree").text();
      param.authOrgn  = $("#authOrgnTree").text();

      if(param.authGrpCd != '') {
        tree.itemsSource = new Array();
        tree.refresh();
        $.postJSON("${baseUrl}" + "listResrce.sb", param, function(result) {
                  tree.itemsSource = result.data.list;
                  <%-- 트리에서 저장 예외관리 시 키로 사용--%>
                  tree.currentAuthGrpCd = param.authGrpCd;
                },
                function(result) {
                  s_alert.pop(result.message);
                });
      }

    });

    <%-- 예외관리 팝업 --%>
    $("#btnExceptMng").click(function(e){
      if(isEmpty(userId.text)) {
        s_alert.pop("<s:message code='login.userId' /><s:message code='cmm.require.text' />");
        return;
      }
      <%-- 예외권한 부여 레이어 호출 --%>
      _showAuthExceptLayer(userId.text);
    });

  });
</script>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/sys/auth/authgroup/authGroupExcept.jsp"/>

