<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/etc/vanCard/vanCard/" />

<div class="subCon">
  
  <div class="searchBar">
    <a href="javascript:;" class="none">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <div class="w70 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd20" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanNm' /></span>
        <button class="btn_skyblue" id="btnAddVan" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelVan" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveVan" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridVan" style="height:210px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w30 fr" style="width: 40%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 610px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanCardNm' /></span>
        <button class="btn_skyblue" id="btnAddMapng" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelMapng" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveMapng" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridMapping" style="height:510px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w70 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10 pd20 mb10" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridCardNm' /></span>
        <button class="btn_skyblue" id="btnAddCard" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelCard" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveCard" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridCard" style="height:210px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  
</div>

<script>

  $(document).ready(function() {
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      searchVan();
      searchCard();
    });
    
    var cardcoCdDataMap = new wijmo.grid.DataMap([{id:"Y", name:"직접"},{id:"N", name:"선택"}], 'id', 'name');
    
    <%-- VAN사 그리드 --%>
    var dataVan =
      [
        { binding:"gChk", header:"<s:message code='vanCard.chk'/>", dataType:wijmo.DataType.Boolean, width:40 },
        { binding:"vanCd", header:"<s:message code='vanCard.vanCd'/>", width:70, align:"center" },
        { binding:"vanNm", header:"<s:message code='vanCard.vanNm'/>", width:"*" },
        { binding:"mainIp", header:"<s:message code='vanCard.mainIp'/>", width:"*" },
        { binding:"mainPort", header:"<s:message code='vanCard.mainPort'/>", width:"*" },
        { binding:"subIp", header:"<s:message code='vanCard.subIp'/>", width:"*" },
        { binding:"subPort", header:"<s:message code='vanCard.subPort'/>", width:"*" },
        { binding:"telNo", header:"<s:message code='vanCard.telNo'/>", width:"*" },
        { binding:"faxNo", header:"<s:message code='vanCard.faxNo'/>", width:"*" }
      ];
    <%-- VAN사 그리드 생성 --%>
    var gridVan = wgrid.genGrid("#gridVan", dataVan, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridVan.isReadOnly = false;
    
    <%-- VAN사 그리드 포맷 --%>
    gridVan.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "vanCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    <%-- VAN사 그리드 선택 이벤트 --%>
    gridVan.addEventListener(gridVan.hostElement, 'click', function(e) {
      var ht = gridVan.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = gridVan.rows[ht.row].dataItem;
        
        if ( selectedRow.status != "I" ) {
          if ( col.binding == "vanCd" ) {
            gridVan.isReadOnly = true;
            searchMapping(selectedRow.vanCd);
          } else {
            gridVan.isReadOnly = false;
          }
        } else {
          gridVan.isReadOnly = false;
        }
        <%-- 그리드 읽기 전용 아닐때만 에디팅 --%>
        if ( !gridVan.isReadOnly ) {
          <%-- 원클릭 에디팅 --%>
          setTimeout(function() {
            gridVan.isReadOnly = false;
            var _cellData = gridVan.getCellData(ht.row, ht.col, true);
            if ( col.dataType !== wijmo.DataType.Boolean ) {
              gridVan.startEditing(true, e.row, ht.col, true); // quick mode
              wijmo.setSelectionRange(gridVan.activeEditor, _cellData.length); // caret position
            }
          }, 50);
        }
        
      }
    });
    
    function searchVan() {
      
      var param = {};
      
      $.postJSON("/sys/etc/vanCard/vanCard/van/list.sb", param, 
        function(result) {
          if(result.status === "FAIL") {
            s_alert.pop(result.message);
            return;
          }
          var list = result.data.list;
          
          if(list.length === undefined || list.length == 0) {
            s_alert.pop(result.message);
            return;
          }
          gridVan.itemsSource = new wijmo.collections.CollectionView(list);
          gridVan.itemsSource.trackChanges = true;
          
          <%-- 버튼 Show --%>
          $("#btnAddVan").show();
          $("#btnDelVan").show();
          $("#btnSaveVan").show();
          
        },
        function(){
          s_alert.pop("Ajax Fail");
        }
      );
    };
    
    <%-- VAN사 그리드 추가 버튼 클릭 --%>
    $("#btnAddVan").click(function(e) {
      gridVan.collectionView.trackChanges = true;
      var newRow = gridVan.collectionView.addNew();
      newRow.status = "I";
      newRow.chk = true;
      
      gridVan.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridVan.select(gridVan.rows.length, 1);
    });
    
    <%-- VAN사 그리드 저장 버튼 클릭 --%>
    $("#btnSaveVan").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridVan.collectionView.itemsEdited.length; i++ ) {
        gridVan.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridVan.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridVan.collectionView.itemsAdded.length; i++ ) {
        gridVan.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridVan.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/etc/vanCard/vanCard/van/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridVan.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.message);
        }
      );
      
    });
    
    <%-- CARD사 그리드 --%>
    var dataCard =
      [
        { binding:"gChk", header:"<s:message code='vanCard.chk'/>", dataType:wijmo.DataType.Boolean, width:40 },
        { binding:"cardcoCd", header:"<s:message code='vanCard.cardcoCd'/>", width:70, align:"center" },
        { binding:"cardcoNm", header:"<s:message code='vanCard.cardcoNm'/>", width:"*" },
        { binding:"bizNo", header:"<s:message code='vanCard.bizNo'/>", width:"*" },
        { binding:"telNo", header:"<s:message code='vanCard.telNo'/>", width:"*" },
        { binding:"faxNo", header:"<s:message code='vanCard.faxNo'/>", width:"*" },
        { binding:"hmpgAddr", header:"<s:message code='vanCard.hmpgAddr'/>", width:"*" }
      ];
    <%-- CARD사 그리드 생성 --%>
    var gridCard = wgrid.genGrid("#gridCard", dataCard, "${menuCd}", 3, ${clo.getColumnLayout(3)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridCard.isReadOnly = false;
    
    <%-- CARD사 그리드 포맷 --%>
    gridCard.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "cardcoCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        } 
      }
    });
    
    <%-- CARD사 그리드 선택 이벤트 --%>
    gridCard.addEventListener(gridCard.hostElement, 'click', function(e) {
      var ht = gridVan.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = gridCard.rows[ht.row].dataItem;
        
        if ( selectedRow.status != "I" ) {
          if ( col.binding == "vanCd" ) {
            gridCard.isReadOnly = true;
            searchMapping(selectedRow.vanCd);
          } else {
            gridCard.isReadOnly = false;
          }
        } else {
          gridCard.isReadOnly = false;
        }
        <%-- 그리드 읽기 전용 아닐때만 에디팅 --%>
        if ( !gridVan.isReadOnly ) {
          <%-- 원클릭 에디팅 --%>
          setTimeout(function() {
            gridCard.isReadOnly = false;
            var _cellData = gridCard.getCellData(ht.row, ht.col, true);
            if ( col.dataType !== wijmo.DataType.Boolean ) {
              gridCard.startEditing(true, e.row, ht.col, true); // quick mode
              wijmo.setSelectionRange(gridCard.activeEditor, _cellData.length); // caret position
            }
          }, 50);
        }
        
      }
    });
    
    function searchCard() {
      
      var param = {};
      
      $.postJSON("/sys/etc/vanCard/vanCard/card/list.sb", param, 
        function(result) {
          if(result.status === "FAIL") {
            s_alert.pop(result.message);
            return;
          }
          var list = result.data.list;
          gridCard.itemsSource = new wijmo.collections.CollectionView(list);
          gridCard.itemsSource.trackChanges = true;
          
          <%-- 버튼 Show --%>
          $("#btnAddCard").show();
          $("#btnDelCard").show();
          $("#btnSaveCard").show();
          
        },
        function(){
          s_alert.pop("Ajax Fail");
        }
      );
    }
    
    <%-- CARD사 그리드 추가 버튼 클릭 --%>
    $("#btnAddCard").click(function(e) {
      gridCard.collectionView.trackChanges = true;
      var newRow = gridCard.collectionView.addNew();
      newRow.status = "I";
      newRow.chk = true;
      
      gridCard.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridCard.select(gridCard.rows.length, 1);
    });
    
    <%-- CARD사 그리드 저장 버튼 클릭 --%>
    $("#btnSaveCard").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridCard.collectionView.itemsEdited.length; i++ ) {
        gridCard.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridCard.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridCard.collectionView.itemsAdded.length; i++ ) {
        gridCard.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridCard.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/etc/vanCard/vanCard/card/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridCard.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.message);
        }
      );
      
    });
    
    var cardCmpnyDataMap = new wijmo.grid.DataMap(${cardCmpnyList}, 'value', 'name');
    
    <%-- VAN/CARD사 매핑 그리드 --%>
    var dataMapping =
      [
        {"binding":"gChk", header:"<s:message code='vanCard.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        {"binding":"vanNm", header:"<s:message code='vanCard.vanNm' />", width:"*"},
        {"binding":"vanCardcoCd", header:"<s:message code='vanCard.vanCardcoCd'/>", width:"*"},
        {"binding":"vanCardcoNm", header:"<s:message code='vanCard.vanCardcoNm'/>", width:"*"},
        {"binding":"cardcoCd", header:"<s:message code='vanCard.cardcoCd'/>", width:"*", dataMap:cardCmpnyDataMap},
      ];
    <%-- VAN/CARD사 매핑 그리드 생성 --%>
    var gridMapping = wgrid.genGrid("#gridMapping", dataMapping, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridMapng.isReadOnly = false;
    
    <%-- VAN/CARD사 매핑 그리드 포맷 --%>
    gridMapng.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "vanCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    <%-- VAN/CARD사 매핑 그리드 선택 이벤트 --%>
    gridMapng.addEventListener(gridMapng.hostElement, 'click', function(e) {
      var ht = gridMapng.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = gridMapng.rows[ht.row].dataItem;
        
        // 코드
        if( col.binding == "vanNm" ) {
          gridMapng.isReadOnly = true;
        } else {
          <%-- 원클릭 에디팅 --%>
          setTimeout(function() {
            gridMapng.isReadOnly = false;
            var _cellData = gridMapng.getCellData(ht.row, ht.col, true);
            if ( col.dataType !== wijmo.DataType.Boolean ) {
              gridMapng.startEditing(true, e.row, ht.col, true); // quick mode
              wijmo.setSelectionRange(gridMapng.activeEditor, _cellData.length); // caret position
            }
          }, 50);
        }
        
      }
    });
    
    function searchMapping(value) {
      
      var param = {};
      param.vanCd = value;
      
      $.postJSON("/sys/etc/vanCard/vanCard/mapng/list.sb", param, 
        function(result) {
          
          <%-- 버튼 Show --%>
          $("#btnAddMapng").show();
          $("#btnDelMapng").show();
          $("#btnSaveMapng").show();
          
          if(result.status === "FAIL") {
            s_alert.pop(result.message);
            return;
          }
          var list = result.data.list;
          gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
          gridMapng.itemsSource.trackChanges = true;
          
          if ( list.length === undefined || list.length == 0 ) {
            <%-- 그리드 초기화 --%>
            gridMapng.itemsSource = new wijmo.collections.CollectionView([]);
            s_alert.pop(result.message);
            return;
          }
          
          
        },
        function(){
          s_alert.pop("Ajax Fail");
        }
      );
    };
    
    <%-- VAN/CARD사 매핑 그리드 추가 버튼 클릭 --%>
    $("#btnAddMapng").click(function(e) {
      
      var selectedVan = gridVan.selectedRows[0]._data;
      
      gridMapng.collectionView.trackChanges = true;
      var newRow = gridMapng.collectionView.addNew();
      newRow.status = "I";
      newRow.chk = true;
      newRow.vanCd = selectedVan.vanCd;
      newRow.vanNm = selectedVan.vanNm;
      
      gridMapng.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridMapng.select(gridMapng.rows.length, 1);
    });
    
    <%-- VAN/CARD사 매핑 그리드 저장 버튼 클릭 --%>
    $("#btnSaveMapng").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridMapng.collectionView.itemsEdited.length; i++ ) {
        gridMapng.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridMapng.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridMapng.collectionView.itemsAdded.length; i++ ) {
        gridMapng.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridMapng.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/etc/vanCard/vanCard/mapng/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridMapng.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.message);
        }
      );
      
    });
    
  });
  
</script>
