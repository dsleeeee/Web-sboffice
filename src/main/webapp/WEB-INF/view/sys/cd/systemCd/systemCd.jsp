<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/sys/cd/systemCd/systemCd/" />

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
        <th><s:message code="systemCd.nmcodeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchNmcodeCd"></div>
          </div>
        </td>
        <%-- 코드명 --%>
        <th><s:message code="systemCd.nmcodeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchNmcodeNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <div class="w50 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.grpGridNm' /></span>
        <button class="btn_skyblue" id="btnAddLeft" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelLeft" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveLeft" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridLeft" style="height:310px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w50 fr" style="width: 40%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddRight" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelRight" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveRight" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridRight" style="height:310px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
    
</div>

<script>

  $(document).ready(function() {
    
    var srchNmcodeCd = wcombo.genInput("#srchNmcodeCd");
    var srchNmcodeNm = wcombo.genInput("#srchNmcodeNm");
    
    <%-- 대표명칭 그리드 --%>
    var dataLeft =
      [
        { binding:"gChk", header:"<s:message code='systemCd.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        { binding:"nmcodeCd", header:"<s:message code='systemCd.nmcodeCd'/>", width:70},
        { binding:"nmcodeNm", header:"<s:message code='systemCd.nmcodeNm'/>", width:"*"},
        { binding:"nmcodeItem1", header:"<s:message code='systemCd.nmcodeItem1'/>", width:"*"},
        { binding:"nmcodeItem2", header:"<s:message code='systemCd.nmcodeItem2'/>", width:"*"},
        { binding:"useColNm", header:"<s:message code='systemCd.useColNm' />", width:"*"}
      ];
    <%-- 대표명칭 그리드 생성 --%>
    var gridLeft = wgrid.genGrid("#gridLeft", dataLeft, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    
    <%-- 세부명칭 그리드 --%>
    var dataRight =
      [
        {"binding":"gChk", header:"<s:message code='systemCd.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        {"binding":"nmcodeCd", header:"<s:message code='systemCd.nmcodeCd'/>", width:70},
        {"binding":"nmcodeNm", header:"<s:message code='systemCd.nmcodeNm'/>", width:"*"},
        {"binding":"nmcodeItem1", header:"<s:message code='systemCd.nmcodeItem1'/>", width:"*"},
        {"binding":"nmcodeItem2", header:"<s:message code='systemCd.nmcodeItem2'/>", width:"*"}
      ];
    <%-- 세부명칭 그리드 생성 --%>
    var gridRight = wgrid.genGrid("#gridRight", dataRight, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridRight.isReadOnly = false;
    
    <%-- 대표명칭 그리드 포맷 --%>
    gridLeft.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "nmcodeCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    var sRow = -1;
    <%-- 대표명칭 그리드 원클릭 에디팅 및 조회 --%>
    gridLeft.addEventListener(gridLeft.hostElement, 'click', function(e) {
      var ht = gridLeft.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var selectedRow = gridLeft.rows[ht.row].dataItem;
        if ( selectedRow.status != "I" ) {
          if ( sRow != ht.row ) {
            search("right", selectedRow.nmcodeCd);
            sRow = ht.row;
          }
          var col = ht.panel.columns[ht.col];
          <%-- 코드는 수정 불가 --%>
          if ( col.binding == "nmcodeCd" ) {
            gridLeft.isReadOnly = true;
          } else {
            <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
            gridLeft.isReadOnly = false;
            setTimeout(function() {
              gridLeft.startEditing(false, e.row, ht.col, true);
            }, 50);
          }
        } else {
          <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
          gridLeft.isReadOnly = false;
          setTimeout(function() {
            gridLeft.startEditing(false, e.row, ht.col, true);
          }, 50);
        }
      }
    });
    
    <%-- 세부명칭 그리드 원클릭 에디팅 --%>
    gridRight.addEventListener(gridRight.hostElement, 'click', function(e) {
      var ht = gridRight.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        setTimeout(function() {
          gridRight.startEditing(false, e.row, ht.col, true);
        }, 50);
      }
    });
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search("left", "");
    });
    
    <%-- 대표명칭 코드목록 조회 --%>
    function search(type, value) {
      
      if ( type == "left" ) {
        var param = {};
        param.nmcodeGrpCd = "000";
        param.nmcodeCd = srchNmcodeCd.text;
        param.nmcodeNm = srchNmcodeNm.text;
        
        $.postJSON("/sys/cd/systemCd/systemCd/list.sb", param, 
            function(result) {
              if(result.status === "FAIL") {
                s_alert.pop(result.message);
                return;
              }
              
              var list = result.data.list;
              gridLeft.itemsSource = new wijmo.collections.CollectionView(list);
              gridLeft.itemsSource.trackChanges = true;
              
              if ( list.length === undefined || list.length == 0 ) {
                s_alert.pop(result.message);
                return;
              }
              
              <%-- 버튼 Show --%>
              $("#btnAddLeft").show();
              $("#btnDelLeft").show();
              $("#btnSaveLeft").show();
              
            },
            function(){
              s_alert.pop("Ajax Fail");
            }
        );
        
      } else if ( type == "right" ) {
        
        var param = {};
        param.nmcodeGrpCd = value;
        
        $.postJSON("/sys/cd/systemCd/systemCd/list.sb", param, 
            function(result) {
              if(result.status === "FAIL") {
                s_alert.pop(result.message);
                return;
              }
              
              <%-- 버튼 Show --%>
              $("#btnAddRight").show();
              $("#btnDelRight").show();
              $("#btnSaveRight").show();
              
              var list = result.data.list;
              gridRight.itemsSource = new wijmo.collections.CollectionView(list);
              gridRight.itemsSource.trackChanges = true;
              
              if ( list.length === undefined || list.length == 0 ) {
                <%-- 그리드 초기화 --%>
                gridRight.itemsSource = [];
                s_alert.pop(result.message);
                return;
              }
              
            },
            function(){
              s_alert.pop("Ajax Fail");
            }
        );
        
      }
    };
    
    <%-- 대표명칭 저장 버튼 클릭 --%>
    $("#btnSaveLeft").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridLeft.collectionView.itemsEdited.length; i++ ) {
        gridLeft.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridLeft.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridLeft.collectionView.itemsAdded.length; i++ ) {
        gridLeft.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridLeft.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/cd/systemCd/systemCd/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridLeft.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 대표명칭 추가 버튼 클릭 --%>
    $("#btnAddLeft").click(function(e) {
      gridLeft.collectionView.trackChanges = true;
      var newRow = gridLeft.collectionView.addNew();
      newRow.nmcodeGrpCd = "000";
      newRow.status = "I";
      newRow.chk = true;
      
      gridLeft.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridLeft.select(gridLeft.rows.length, 1);
    });
    
    <%-- 세부명칭 추가 버튼 클릭 --%>
    $("#btnAddRight").click(function(e) {
      var selectedRow = gridLeft.selectedRows[0]._data;
      
      gridRight.collectionView.trackChanges = true;
      var newRow = gridRight.collectionView.addNew();
      newRow.nmcodeGrpCd = selectedRow.nmcodeCd;
      newRow.chk = true;
      
      gridRight.collectionView.commitNew();
      <%-- 추가된 Row 선택--%>
      gridRight.select(gridRight.rows.length, 1);
    });
    
    <%-- 세부명칭 저장 버튼 클릭 --%>
    $("#btnSaveRight").click(function(e) {
      
      var paramArr = new Array();
      
      for ( var i = 0; i < gridRight.collectionView.itemsEdited.length; i++ ) {
        gridRight.collectionView.itemsEdited[i].status = "U";
        paramArr.push(gridRight.collectionView.itemsEdited[i]);
      }
      for ( var i = 0; i < gridRight.collectionView.itemsAdded.length; i++ ) {
        gridRight.collectionView.itemsAdded[i].status = "I";
        paramArr.push(gridRight.collectionView.itemsAdded[i]);
      }
      
      if ( paramArr.length <= 0 ) {
        s_alert.pop("<s:message code='cmm.not.modify'/>");
        return;
      }
      
      $.postJSONArray("/sys/cd/systemCd/systemCd/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridRight.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
  });
  
</script>
