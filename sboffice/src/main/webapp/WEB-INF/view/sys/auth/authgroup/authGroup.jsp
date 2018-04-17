<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sys/auth/authgroup/authgroup/" />

<div class="subCon">
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
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
        <th><s:message code="authGroup.grpNm" /></th>
        <td>
          <div class="sb-select fl">
            <div id="grpNm" class="sb-input"></div>
          </div>
        </td>
        <%-- 사용여부 --%>
        <th><s:message code="cmm.useYn" /></th>
        <td>
          <div class="sb-select">
            <div id="useYn"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <button id="btnSearch" class="btn_blue fr" >
      <s:message code="label.cmm.search" />
    </button>
  </div>

  <%--2단--%>
  <div class="wj-TblWrap mt20">

    <%--left--%>
    <div class="w60 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:500px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.authGroup"/></span>
          <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add"/></button>
          <button id="btnDel" class="btn_skyblue"><s:message code="cmm.del"/></button>
          <button id="btnSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div id="theGrid"></div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
    <%--//left--%>

    <%--right--%>
    <div class="w40 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:500px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 트리--%>
        <div id="treeResrce" style="height:450px;"></div>
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
        {binding:"grpCd", header:"<s:message code='authGroup.grpCd' />", width:70, isReadOnly:true},
        {binding:"grpNm", header:"<s:message code='authGroup.grpNm' />", width:100, isRequired:true},
        {binding:"targetAllFg", header:"<s:message code='authGroup.targetAllFg' />", width:100, dataMap:targetAllFg},
        {binding:"targetOrgn", header:"<s:message code='authGroup.targetOrgn' />", width:100},
        {binding:"targetOrgnNm", header:"<s:message code='authGroup.targetOrgnNm' />", width:100, isReadOnly:true},
        {binding:"remark", header:"<s:message code='cmm.remark' />", width:100},
        {binding:"useYn", header:"<s:message code='cmm.use' />", width:50, dataType:wijmo.DataType.Boolean}
    ];
    var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    grid.isReadOnly  = false;
    var grpNm        = wcombo.genInput("#grpNm");
    var useYn        = wcombo.genCommonBox("#useYn", ${ccu.getCommCode("904")});
    
    <%-- 메뉴 트리 생성 --%>
    var tree = new wijmo.nav.TreeView('#treeResrce', {
      displayMemberPath: 'resrceNm',
      childItemsPath: 'items',
      expandOnClick : true,
      isReadOnly: true,
      showCheckboxes: true
    });
    
    <%-- 트리 체크박스 초기화 --%>
    tree.loadedItems.addHandler(function(s, e) {
      s.collapseToLevel(0);
      <%-- //TODO 느림.. --%>
      for (var nd = tree.getFirstNode(); nd; nd = nd.next()) {
        nd.isChecked = false;
        if(nd.dataItem.authFg == 1) {
          nd.isChecked = true;
        }
      }
    });
    
    tree.checkedItemsChanged.addHandler(function(s, e) {
      //console.log(s.checkedItems);
      console.log(e);
    });
    
    <%-- 그리드 컬럼 특수 기능 처리 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "grpCd" ) {
          //TODO 링크 표시 + cursor-hand
          //wijmo.addClass(e.cell, 'low', item.sales < 1000);
        }
        else if( col.binding == "useYn" ) {
          e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.useYn == true || item.useYn == "Y" ? 'checked' : '') + '>';
        }
      }
    });
    
    <%-- 그리드 선택 이벤트 --%>
    grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
      var ht = grid.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding == "grpCd") {
          var param = {};
          param.grpCd = grid.cells.getCellData(ht.row, ht.col, true);
          if(param.grpCd != '') {
            $.postJSON("${baseUrl}" + "listResrce.sb", param, function(result) {
              tree.itemsSource = result.data.list;
            },
            function(result) {
              s_alert.pop(result.data.msg);
            });
          }
        }
      }
    });
    
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
    }

    <%-- 권한 그룹 추가 --%>
    $("#btnAdd").click(function(e){
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
      
      for(var i=0; i<grid.collectionView.itemsEdited.length; i++){
        grid.collectionView.itemsEdited[i].status = "U";
        paramArr.push(grid.collectionView.itemsEdited[i]);
      }
      for(var i=0; i<grid.collectionView.itemsAdded.length; i++){
        grid.collectionView.itemsAdded[i].status = "I";
        paramArr.push(grid.collectionView.itemsAdded[i]);
      }
      for(var i=0; i<grid.collectionView.itemsRemoved.length; i++){
        grid.collectionView.itemsRemoved[i].status = "D";
        paramArr.push(grid.collectionView.itemsRemoved[i]);
      }
      
      $.postJSONArray("${baseUrl}" + "save.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='msg.save.succ' />");
        grid.collectionView.clearChanges();
      },
      function(result) {
        s_alert.pop(result.data.msg);
      });
    });
    
    <%-- 리스스 정보 저장 --%>
    $("#btnResrceSave").click(function(e){
      var paramArr = new Array();

      $.postJSON("${baseUrl}" + "saveResrce.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='msg.save.succ' />");
      },
      function(result) {
        s_alert.pop(result.data.msg);
      });

    });

  });
</script>
