<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/sys/bill/item/item/" />

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
        <%-- 코드 --%>
        <th><s:message code="item.prtCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchPrtCd"></div>
          </div>
        </td>
        <%-- 코드명 --%>
        <th><s:message code="item.prtNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchPrtNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <div class="w50 fl" style="width: 100%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='item.gridNm' /></span>
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
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="theGrid" style="height:310px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
</div>

<script>

  $(document).ready(function() {
    
    var srchPrtCd = wcombo.genInput("#srchPrtCd");
    var srchPrtNm = wcombo.genInput("#srchPrtNm");
    
    <%-- 출력코드구성 그리드 --%>
    var data =
      [
        { binding:"gChk", header:"<s:message code='item.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        { binding:"prtCd", header:"<s:message code='item.prtCd'/>", width:100},
        { binding:"prtNm", header:"<s:message code='item.prtNm'/>", width:150},
        { binding:"samplYn", header:"<s:message code='item.samplYn'/>", dataType:wijmo.DataType.Boolean, width:80},
        { binding:"content", header:"<s:message code='item.content'/>", width:"*"},
      ];
    <%-- 출력코드구성 그리드 생성 --%>
    var theGrid = wgrid.genGrid("#theGrid", data, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    theGrid.isReadOnly = false;
    
    var contentColumn = theGrid.columns.getColumn("content");
    contentColumn.multiLine = true;
    contentColumn.wordWrap = true;
    
    // auto-size visible rows
    function autoSizeVisibleRows(flex, force) {
      var rng = flex.viewRange;
      for (var r = rng.row; r <= rng.row2; r++) {
          if (force || flex.rows[r].height == null) {
          flex.autoSizeRow(r, false)
        }
      }
    }
    
    <%-- 출력코드구성 그리드 선택 이벤트 --%>
    theGrid.addEventListener(theGrid.hostElement, 'click', function(e) {
      var ht = theGrid.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = theGrid.rows[ht.row].dataItem;
        
        <%-- 원클릭 에디팅 --%>
        setTimeout(function() {
          var _cellData = theGrid.getCellData(ht.row, ht.col, true);
          if ( col.dataType !== wijmo.DataType.Boolean ) {
            theGrid.startEditing(true, e.row, ht.col, true); // quick mode
            wijmo.setSelectionRange(theGrid.activeEditor, _cellData.length); // caret position
          }
        }, 50);
      }
    });
    
    <%-- validation --%>
    theGrid.cellEditEnded.addHandler(function (s, e){
      
      autoSizeVisibleRows(s, true);
      
    });
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search();
    });
    
    <%-- 출력코드구성 목록 조회 --%>
    function search() {
      
      var param = {};
      param.prtCd = srchPrtCd.text;
      param.prtNm = srchPrtNm.text;
      
      $.postJSON("/sys/bill/item/item/list.sb", param, 
          function(result) {
            if(result.status === "FAIL") {
              s_alert.pop(result.message);
              return;
            }
            
            <%-- 버튼 Show --%>
            $("#btnAdd").show();
            $("#btnDel").show();
            $("#btnSave").show();
            
            var list = result.data.list;
            theGrid.itemsSource = new wijmo.collections.CollectionView(list);
            theGrid.itemsSource.trackChanges = true;
            
            if ( list.length === undefined || list.length == 0 ) {
              <%-- 그리드 초기화 --%>
              theGrid.itemsSource = [];
              s_alert.pop(result.message);
              return;
            }
            
          },
          function(){
            s_alert.pop("Ajax Fail");
          }
      );
        
    };
    
    <%-- 출력코드구성 저장 버튼 클릭 --%>
    $("#btnSave").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < theGrid.collectionView.itemsEdited.length; i++ ) {
        theGrid.collectionView.itemsEdited[i].status = "U";
        paramArr.push(theGrid.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < theGrid.collectionView.itemsAdded.length; i++ ) {
        theGrid.collectionView.itemsAdded[i].status = "I";
        paramArr.push(theGrid.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/bill/item/item/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='msg.save.succ' />");
          theGrid.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 출력코드구성 추가 버튼 클릭 --%>
    $("#btnAdd").click(function(e) {
      theGrid.collectionView.trackChanges = true;
      var newRow = theGrid.collectionView.addNew();
      newRow.status = "I";
      newRow.chk = true;
      
      theGrid.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      theGrid.select(theGrid.rows.length, 1);
    });
    
  });
  
</script>