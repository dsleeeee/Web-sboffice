<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/sys/cd/envConfig/envConfig/" />

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
        <%-- 설정코드 --%>
        <th><s:message code="envConfig.envstCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstCd"></div>
          </div>
        </td>
        <%-- 설정명 --%>
        <th><s:message code="envConfig.envstNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstNm"></div>
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
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search("left", "");
    });
    
    var srchEnvstCd = wcombo.genInput("#srchEnvstCd");
    var srchEnvstNm = wcombo.genInput("#srchEnvstNm");
    
    var envstFgNm   = ${ccu.getCommCodeExcpAll("004")};
    var envstGrpCdNm   = ${ccu.getCommCodeExcpAll("048")};
    var targtFg = ${ccu.getCommCodeExcpAll("062")};
    
    var envstFgNmDataMap   = new wijmo.grid.DataMap(envstFgNm, 'value', 'name');
    var envstGrpCdNmDataMap   = new wijmo.grid.DataMap(envstGrpCdNm, 'value', 'name');
    var targtFgDataMap   = new wijmo.grid.DataMap(targtFg, 'value', 'name');
    var dirctInYnDataMap = new wijmo.grid.DataMap([{id:"Y", name:"직접"},{id:"N", name:"선택"}], 'id', 'name');
    var useYnDataMap = new wijmo.grid.DataMap([{id:"Y", name:"사용"},{id:"N", name:"사용안함"}], 'id', 'name');
    var defltYnDataMap = new wijmo.grid.DataMap([{id:"Y", name:"기본"},{id:"N", name:"기본아님"}], 'id', 'name');
    
    <%-- 대표명칭 그리드 --%>
    var dataLeft =
      [
        { binding:"gChk", header:"<s:message code='envConfig.chk'/>", dataType:wijmo.DataType.Boolean, width:40 },
        { binding:"envstCd", header:"<s:message code='envConfig.envstCd'/>", width:70, align:"center" },
        { binding:"envstNm", header:"<s:message code='envConfig.envstNm'/>", maxLength:100 },
        { binding:"envstFg", header:"<s:message code='envConfig.envstFgNm'/>", width:140, dataMap:envstFgNmDataMap },
        { binding:"envstGrpCd", header:"<s:message code='envConfig.envstGrpCdNm'/>", width:100, dataMap:envstGrpCdNmDataMap },
        { binding:"dirctInYn", header:"<s:message code='envConfig.dirctInYn'/>", width:70, dataMap:dirctInYnDataMap },
        { binding:"targtFg", header:"<s:message code='envConfig.targtFgNm'/>", dataMap:targtFgDataMap },
        { binding:"useYn", header:"<s:message code='envConfig.useYn'/>", width:80, dataMap:useYnDataMap },
        { binding:"remark", header:"<s:message code='envConfig.remark'/>", width:200, maxLength:250 }
      ];
    <%-- 대표명칭 그리드 생성 --%>
    var gridLeft = wgrid.genGrid("#gridLeft", dataLeft, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridLeft.isReadOnly = false;
    
    <%-- 대표명칭 그리드 포맷 --%>
    gridLeft.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "envstCd" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    <%-- 대표명칭 그리드 원클릭 에디팅 --%>
    gridLeft.selectionChanged.addHandler(function (s, e) {
      var col = s.columns[e.col];
      <%-- 설정명/비고 만 에디팅 --%>
      if ( col.binding == "envstNm" || col.binding == "remark" ) {
        setTimeout(function() {
          s.startEditing(true, e.row, e.col, true); // quick mode
        }, 50);
        e.cancel = false;
      }
    });
    
    <%-- 대표명칭 그리드 선택 이벤트 --%>
    gridLeft.addEventListener(gridLeft.hostElement, 'click', function(e) {
      var ht = gridLeft.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        // 코드
        if( col.binding == "envstCd" ) {
          selectedRow = gridLeft.rows[ht.row].dataItem;
          search("right", selectedRow.envstCd);
        }
      }
    });
    
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
      
      $.postJSONArray("/sys/cd/envConfig/envConfig/envst/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridLeft.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 세부명칭 그리드 --%>
    var dataRight =
      [
        {"binding":"gChk", header:"<s:message code='envConfig.chk' />", dataType:wijmo.DataType.Boolean, width:40},
        {"binding":"envstValCd", header:"<s:message code='envConfig.envstValCd'/>", width:70},
        {"binding":"envstValNm", header:"<s:message code='envConfig.envstValNm'/>", width:"*"},
        {"binding":"defltYn", header:"<s:message code='envConfig.defltYn'/>", width:80, dataMap:defltYnDataMap},
        {"binding":"useYn", header:"<s:message code='envConfig.useYn'/>", width:80, dataMap:useYnDataMap }
      ];
    <%-- 세부명칭 그리드 생성 --%>
    var gridRight = wgrid.genGrid("#gridRight", dataRight, "${menuCd}", 2, ${clo.getColumnLayout(2)});
    <%-- 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. --%>
    gridRight.isReadOnly = false;
    
    <%-- 세부명칭 그리드 원클릭 에디팅 --%>
    gridRight.selectionChanged.addHandler(function (s, e) {
      var col = s.columns[e.col];
      <%-- 설정명 수정 --%>
      if ( col.binding == "envstValNm" ) {
        setTimeout(function() {
          s.startEditing(false); // quick mode
        }, 50);
      }
    });
    
    <%-- 세부명칭 그리드 선택 이벤트 --%>
    gridRight.addEventListener(gridRight.hostElement, 'click', function(e) {
      var ht = gridRight.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
      }
    });
    
    <%-- 세부명칭 추가 버튼 클릭 --%>
    $("#btnAddRight").click(function(e) {
      var selectedRow = gridLeft.selectedRows[0]._data;
      
      var newRow = gridRight.collectionView.addNew();
      newRow.envstCd = selectedRow.envstCd;
      newRow.chk = true;
      
      gridRight.collectionView.commitNew();
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
      
      $.postJSONArray("/sys/cd/envConfig/envConfig/envstDtl/save.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          gridRight.collectionView.clearChanges();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
      );
      
    });
    
    <%-- 코드목록 조회 --%>
    function search(type, value) {
      <%-- 대표명칭 --%>
      if ( type == "left" ) {
        var param = {};
        param.envstCd = srchEnvstCd.text;
        param.envstNm = srchEnvstNm.text;
        
        $.postJSON("/sys/cd/envConfig/envConfig/envst/list.sb", param, 
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
              gridLeft.itemsSource = new wijmo.collections.CollectionView(list);
              gridLeft.itemsSource.trackChanges = true;
              
              <%-- 버튼 Show --%>
              $("#btnAddLeft").show();
              $("#btnDelLeft").show();
              $("#btnSaveLeft").show();
              
            },
            function(){
              s_alert.pop("Ajax Fail");
            }
        );
      <%-- 세부명칭 --%>  
      } else if ( type == "right" ) {
        
        var param = {};
        param.envstCd = value;
        
        $.postJSON("/sys/cd/envConfig/envConfig/envstDtl/list.sb", param, 
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
              gridRight.itemsSource = new wijmo.collections.CollectionView(list);
              gridRight.itemsSource.trackChanges = true;
              
              <%-- 버튼 Show --%>
              $("#btnAddRight").show();
              $("#btnDelRight").show();
              $("#btnSaveRight").show();
              
            },
            function(){
              s_alert.pop("Ajax Fail");
            }
        );
        
      }
    };
    
  });
  
</script>
