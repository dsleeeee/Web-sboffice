<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/sys/bill/kind/" />

<div class="subCon">
  
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <div class="w50 fl" style="width: 40%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='kind.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddPrint" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelPrint" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSavePrint" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridPrint" style="height:310px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w50 fr" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='kind.gridMapngNm' /></span>
        <button class="btn_up" id="btnUpMapng" style="display: none;">
          <s:message code="cmm.up" />
        </button>
        <button class="btn_down" id="btnDownMapng" style="display: none;">
          <s:message code="cmm.down" />
        </button>
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
      <div id="gridMapng" style="height:310px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <input type="hidden" id="prtClassCd" />
</div>

<%-- 출력물코드 선택 레이어 --%>
<div id="itemSelTent" class="fullDimmed" style="display: none;"></div>
<div id="itemSelLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w800">
      <p class="tit"><s:message code="kind.gridNm" /></p>
      <a href="javascript:;" class="btn_close itemSelClose"></a>
      <div class="con">
          <%--위즈모 테이블--%>
          <div class="wj-TblWrapBr mt10" style="height: 400px;">
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div id="theGrid" style="width:100%;height:393px;"></div>
          </div>
      </div>
      <%-- 저장 --%>
      <div class="btnSet">
        <span><a href="javascript:;" id="btnSaveItem" class="btn_blue"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  $(document).ready(function() {
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search();
    });
    
    <%-- 출력물종류 그리드 --%>
    var dataPrint =
      [
        { binding:"gChk", header:"<s:message code='kind.chk' />", dataType:wijmo.DataType.Boolean, width:50},
        { binding:"prtClassCd", header:"<s:message code='kind.prtClassCd'/>", width:70, isReadOnly: true },
        { binding:"prtClassNm", header:"<s:message code='kind.prtClassNm'/>", width:"*"},
        { binding:"general", header:"<s:message code='kind.general'/>", dataType:wijmo.DataType.Boolean, width:50},
        { binding:"food", header:"<s:message code='kind.food'/>", dataType:wijmo.DataType.Boolean, width:50}
      ];
    <%-- 출력물종류 그리드 생성 --%>
    var gridPrint = wgrid.genGrid("#gridPrint", dataPrint, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    gridPrint.isReadOnly = false;
    
    <%-- 출력물종류 그리드 포맷 --%>
    gridPrint.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "prtClassCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    <%-- 출력물종류 그리드 클릭 이벤트 --%>
    gridPrint.addEventListener(gridPrint.hostElement, 'click', function(e) {
      var ht = gridPrint.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var selectedRow = gridPrint.rows[ht.row].dataItem;
        if ( selectedRow.status != "I" ) {
          var col = ht.panel.columns[ht.col];
          if ( col.binding == "prtClassCd" ) {
            searchMapng(selectedRow.prtClassCd);
          }
        }
      }
    });
    
    <%-- 출력물종류 코드목록 조회 --%>
    function search() {
      
      var param = {};
        
      $.postJSON("/sys/bill/kind/bill/list.sb", param, 
          function(result) {
            if(result.status === "FAIL") {
              s_alert.pop(result.message);
              return;
            }
            
            var list = result.data.list;
            gridPrint.itemsSource = new wijmo.collections.CollectionView(list);
            gridPrint.itemsSource.trackChanges = true;
            
            <%-- 버튼 Show --%>
            $("#btnAddPrint").show();
            $("#btnDelPrint").show();
            $("#btnSavePrint").show();
            
            if ( list.length === undefined || list.length == 0 ) {
              s_alert.pop(result.message);
              return;
            } else {
              
              param = {};
              param.prtClassCd = list[0].prtClassCd; 
              $("#prtClassCd").val(list[0].prtClassCd);
              
              $.postJSON("/sys/bill/kind/mapng/list.sb", param, 
                  function(result) {
                    if(result.status === "FAIL") {
                      s_alert.pop(result.message);
                      return;
                    }
                    
                    var list = result.data.list;
                    gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
                    gridMapng.itemsSource.trackChanges = true;
                    
                    <%-- 버튼 Show --%>
                    $("#btnUpMapng").show();
                    $("#btnDownMapng").show();
                    $("#btnAddMapng").show();
                    $("#btnDelMapng").show();
                    $("#btnSaveMapng").show();
                    
                    if ( list.length === undefined || list.length == 0 ) {
                      <%-- 그리드 초기화 --%>
                      gridMapng.itemsSource = [];
                    }
                    
                  },
                  function(){
                    s_alert.pop("Ajax Fail");
                  }
              );
            }
            
          },
          function(){
            s_alert.pop("Ajax Fail");
          }
      );
        
    };
    
    <%-- 출력물종류 추가 버튼 클릭 --%>
    $("#btnAddPrint").click(function(e) {
      gridPrint.collectionView.trackChanges = true;
      var newRow = gridPrint.collectionView.addNew();
      newRow.status = "I";
      newRow.general = '0';
      newRow.food = '0';
      newRow.chk = true;
      
      gridPrint.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridPrint.select(gridPrint.rows.length, 1);
    });
    
    <%-- 출력물종류 저장 버튼 클릭 --%>
    $("#btnSavePrint").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridPrint.collectionView.itemsEdited.length; i++ ) {
        gridPrint.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridPrint.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridPrint.collectionView.itemsAdded.length; i++ ) {
        gridPrint.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridPrint.collectionView.itemsAdded[i]);
      }
      for ( var i = 0; i < gridPrint.collectionView.itemsRemoved.length; i++ ) {
        gridPrint.collectionView.itemsRemoved[i].status = "D";
        paramArr.push(gridPrint.collectionView.itemsRemoved[i]);
      }
      
      for ( var i = 0; i < paramArr.length; i++ ) {
        paramArr[i].general = ( paramArr[i].general == true ? "Y" : "N" );
        paramArr[i].food = ( paramArr[i].food == true ? "Y" : "N" );
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/bill/kind/bill/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridPrint.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 출력물매핑 그리드 --%>
    var dataMapng =
      [
        {"binding":"gChk", header:"<s:message code='kind.chk' />", dataType:wijmo.DataType.Boolean, width:50},
        {"binding":"prtCd", header:"<s:message code='kind.prtCd'/>", width:"*"},
      ];
    <%-- 출력물매핑 그리드 생성 --%>
    var gridMapng = wgrid.genGrid("#gridMapng", dataMapng, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridMapng.isReadOnly = false;
    
    <%-- 출력물매핑 목록 조회 --%>
    function searchMapng(value) {
      
      $("#prtClassCd").val(value);
      var param = {};
      param.prtClassCd = value;
      
      $.postJSON("/sys/bill/kind/mapng/list.sb", param, 
          function(result) {
            if(result.status === "FAIL") {
              s_alert.pop(result.message);
              return;
            }
            
            var list = result.data.list;
            gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
            gridMapng.itemsSource.trackChanges = true;
            
            <%-- 버튼 Show --%>
            $("#btnUpMapng").show();
            $("#btnDownMapng").show();
            $("#btnAddMapng").show();
            $("#btnDelMapng").show();
            $("#btnSaveMapng").show();
            
            if ( list.length === undefined || list.length == 0 ) {
              <%-- 그리드 초기화 --%>
              gridMapng.itemsSource = [];
            }
            
          },
          function(){
            s_alert.pop("Ajax Fail");
          }
      );
      
    };
    
    <%-- 출력물매핑 UP 버튼 클릭 --%>
    $("#btnUpMapng").click(function(e) {
      var movedRows = 0;
      for ( var i = 0; i < gridMapng.collectionView.itemCount; i++ ) {
        var item = gridMapng.collectionView.items[i];
        if ( i > 0 && item.chk ) {
          if ( !gridMapng.collectionView.items[i-1].chk ) {
            movedRows = i-1;
            var tmpItem = gridMapng.collectionView.items[movedRows];
            gridMapng.collectionView.items[movedRows] = gridMapng.collectionView.items[i];
            gridMapng.collectionView.items[i] = tmpItem;
            gridMapng.collectionView.commitEdit();
            gridMapng.collectionView.refresh();
          }
        }
      }
      gridMapng.select(movedRows, 1);
    });

    <%-- 출력물매핑 DOWN 버튼 클릭 --%>
    $("#btnDownMapng").click(function(e){
      var movedRows = 0;
      for ( var i = gridMapng.itemsSource.itemCount-1; i >= 0; i-- ) {
        var item = gridMapng.collectionView.items[i];
        if ( ( i < gridMapng.itemsSource.itemCount-1 ) && item.chk ) {
          if ( !gridMapng.collectionView.items[i+1].chk ) {
            movedRows = i+1;
            var tmpItem = gridMapng.collectionView.items[movedRows];
            gridMapng.collectionView.items[movedRows] = gridMapng.collectionView.items[i];
            gridMapng.collectionView.items[i] = tmpItem;
            gridMapng.collectionView.commitEdit();
            gridMapng.collectionView.refresh();
          } 
        }
      }
      gridMapng.select(movedRows, 1);
    });
    
    <%-- 출력물매핑 추가 버튼 클릭 --%>
    $("#btnAddMapng").click(function(e) {
      var selectedRow = gridPrint.selectedRows[0]._data;
      showItemLayer(selectedRow.prtClassCd);
    });
    
    <%-- 출력물매핑 저장 버튼 클릭 --%>
    $("#btnSaveMapng").click(function(e) {
      
      gridMapng.collectionView.trackChanges = true;
      
      <%-- dispSeq 재설정 --%>
      for ( var i = 0; i < gridMapng.collectionView.itemCount; i++ ) {
        gridMapng.collectionView.editItem(gridMapng.collectionView.items[i]);
        gridMapng.collectionView.items[i].prtClassCd = $("#prtClassCd").val();
        gridMapng.collectionView.items[i].dispSeq = ( i + 1 );
        gridMapng.collectionView.commitEdit();
      }
      
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
      
      $.postJSONArray("/sys/bill/kind/mapng/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridMapng.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 출력물코드구성 선택 레이어 --%>
    <%-- 레이어 보이기 --%>
    function showItemLayer(value) {
      
      $("#prtClassCd").val(value);
      
      $("#itemSelTent, #itemSelLayer").show();
      <%-- 자동조회 --%>
      setTimeout(function() {
        var param = {};
        
        $.postJSON("/sys/bill/item/item/list.sb", param, 
            function(result) {
              if(result.status === "FAIL") {
                s_alert.pop(result.message);
                return;
              }
              
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
      }, 50);
      
    }
    <%-- 레이어 감추기 --%>
    $(".itemSelClose").click(function(e) {
      $("#itemSelTent, #itemSelLayer").hide();
    });
    
    <%-- 출력코드구성 그리드 --%>
    var data =
      [
        { binding:"gChk", header:"<s:message code='item.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        { binding:"prtCd", header:"<s:message code='item.prtCd'/>", width:100, isReadOnly: true },
        { binding:"prtNm", header:"<s:message code='item.prtNm'/>", width:100, isReadOnly: true },
        { binding:"samplYn", header:"<s:message code='item.samplYn'/>", dataType:wijmo.DataType.Boolean, isReadOnly: true, width:60},
        { binding:"content", header:"<s:message code='item.content'/>", width:"*", isReadOnly: true },
      ];
    <%-- 출력코드구성 그리드 생성 --%>
    var theGrid = wgrid.genGrid("#theGrid", data, "${menuCd}", 3, ${clo.getColumnLayout(3)});
    theGrid.isReadOnly = false;
    
    <%-- 출력코드구성 그리드 포맷 --%>
    theGrid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
      }
    });
    
    <%-- 저장버튼 클릭 --%>
    $("#btnSaveItem").click(function(e) {
      
      for ( var i = 0; i < theGrid.collectionView.itemCount; i++ ) {
        var item = theGrid.collectionView.items[i];
        if ( item.chk ) {
          var dupCheck = false;
          for ( var j = 0; j < gridMapng.collectionView.itemCount; j++ ) {
            var savedItem = gridMapng.collectionView.items[j];
            if ( savedItem.prtCd == item.prtCd ) {
              dupCheck = true;
              break;
            }
          }
          
          if ( !dupCheck ) {
            gridMapng.collectionView.trackChanges = true;
            var newRow = gridMapng.collectionView.addNew();
            newRow.status = "I";
            newRow.prtClassCd = $("#prtClassCd").val();
            newRow.prtCd = item.prtCd;
            newRow.chk = true;
            
            gridMapng.collectionView.commitNew();
          }
        }
      }
      <%-- 추가된 Row 선택--%>
      gridMapng.select(gridMapng.rows.length, 1);
      $("#itemSelTent, #itemSelLayer").hide();
    });
    
    
  });
  
</script>
