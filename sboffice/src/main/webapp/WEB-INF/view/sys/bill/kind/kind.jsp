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
    
</div>

<script>

  $(document).ready(function() {
    var gridPrint;
    <%-- 출력물종류 그리드 --%>
    var dataPrint =
      [
        { binding:"chkGrid", header:"<s:message code='kind.chk' />", dataType:wijmo.DataType.Boolean, width:50},
        { binding:"prtClassCd", header:"<s:message code='kind.prtClassCd'/>", width:70, isReadOnly: true },
        { binding:"prtClassNm", header:"<s:message code='kind.prtClassNm'/>", width:"*"},
        { binding:"general", header:"<s:message code='kind.general'/>", dataType:wijmo.DataType.Boolean, width:50},
        { binding:"food", header:"<s:message code='kind.food'/>", dataType:wijmo.DataType.Boolean, width:50}
      ];
    <%-- 출력물종류 그리드 생성 --%>
    gridPrint = wgrid.genGrid("#gridPrint", dataPrint, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    gridPrint.isReadOnly = false;
    
    <%-- 출력물종류 그리드 포맷 --%>
    gridPrint.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        
        if ( col.binding == "general" || col.binding == "food" ) {
//           var chk = document.createElement('input');
//           chk.type = 'checkbox';
//           chk.checked = gridPrint.rows[e.row].dataItem['chk'];
//           chk.className = "wj-cell-check";
//           e.cell.innerHTML = '';
//           e.cell.appendChild(chk);
        } else if ( col.binding == "content" ) {
          
//           col.multiLine = true;
//           if ( !isEmpty(item.content) ) {
//             var formattedData = item.content.replace(/\n/g, '<br/>');
//             e.panel.setCellData(e.row, e.col, formattedData);
//           }
        }
      }
    });
    
    <%-- 출력물종류 그리드 조회 --%>
    gridPrint.selectionChanged.addHandler(function (s, e) {
      var col = s.columns[e.col];
//       search();
    });
    
    <%-- 출력물매핑 그리드 --%>
    var dataMapng =
      [
        {"binding":"chk", header:"<s:message code='kind.chk' />", dataType:wijmo.DataType.Boolean, width:50},
        {"binding":"prtCd", header:"<s:message code='kind.prtCd'/>", width:"*"},
      ];
    <%-- 출력물매핑 그리드 생성 --%>
    var gridMapng = wgrid.genGrid("#gridMapng", dataMapng, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridMapng.isReadOnly = false;
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search();
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
          s_alert.pop("<s:message code='msg.save.succ' />");
          gridPrint.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 출력물매핑 추가 버튼 클릭 --%>
    $("#btnAddMapng").click(function(e) {
      // 팝업처리 ( TB_CM_PRINT_CODE )
      
    });
    
    <%-- 출력물매핑 저장 버튼 클릭 --%>
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
      
      $.postJSONArray("/sys/bill/kind/mapng/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='msg.save.succ' />");
          gridMapng.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
  });
  
</script>