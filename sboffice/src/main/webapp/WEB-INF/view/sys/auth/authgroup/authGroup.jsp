<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resource/vender/wijmo/js/input/wijmo.input.min.js"></script>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

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
          <div class="sb-select fl w70">
            <div id="grpNm" class="sb-input w80"></div>
          </div>
        </td>
        <%-- 사용여부 --%>
        <th><s:message code="cmm.useYn" /></th>
        <td>
          <div class="sb-select">
            <div id="theComboBox2"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <button id="searchBtn" class="btn_blue fr" >
      <s:message code="label.cmm.search" />
    </button>
  </div>

  <%--2단--%>
  <div class="wj-TblWrap mt20">

    <%--left--%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:700px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.authGroup"/></span>
          <button id="addBtn" class="btn_skyblue"><s:message code="cmm.add"/></button>
          <button id="saveBtn" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div id="theGrid"></div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
    <%--//left--%>

    <%--right--%>
    <div class="w50 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="resrceSaveBtn" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%--위즈모 트리--%>
        <div id="resrceTree"></div>
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
      {id:"A", name:"전체"},
      {id:"P", name:"일부"}
    ], 'id', 'name');
    var rdata = 
      [
        {binding:"no", header:"<s:message code='cmm.no' />", width:30},
        {binding:"grpCd", header:"<s:message code='authGroup.grpCd' />", width:70},
        {binding:"grpNm", header:"<s:message code='authGroup.grpNm' />", width:100},
        {binding:"targetAllFg", header:"<s:message code='authGroup.targetAllFg' />", width:100, dataMap:targetAllFg},
        {binding:"targetOrgn", header:"<s:message code='authGroup.targetOrgn' />", width:100},
        {binding:"targetOrgnNm", header:"<s:message code='authGroup.targetOrgnNm' />", width:100},
        {binding:"remark", header:"<s:message code='cmm.remark' />", width:100},
        {binding:"useYn", header:"<s:message code='cmm.use' />", width:50, dataType:wijmo.DataType.Boolean}
      ];
    
    var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    grid.isReadOnly      = false;
    <%-- USE_YN 변환 --%>
    grid.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType == wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.binding == 'useYn') {
          var item = panel.rows[r].dataItem;
          cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.useYn == "Y" ? 'checked' : '') + '>';
        }
      }
    };
    
    var grpNm        = wcombo.genInput("#grpNm");
    
    <%-- 그리드 링크 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if( col.binding == "grpCd" ) {
          var item = s.rows[e.row].dataItem;
          item.row = e.row;
          item.cl = "row";
          var html = wijmo.format("<a href=\"javascript:;\" class=\"{cl}\" data-value=\"{row}\">{grpCd}</a>", item);
          e.cell.innerHTML = html;
        }
      }
    });
    
    <%-- 그리드 선택 이벤트 --%>
    $(document).on("click",".row",function() {
      var row = $(this).data("value");
      var rowData = grid.rows[row].dataItem;
      showDclzLayer("view", rowData);
    });
    
    <%-- 리스트 조회 --%>
    $("#searchBtn").click(function(e){
      search();
    });
    
    <%-- 엑셀 다운로드 --%>
    $("#excelBtn").click(function( e ){
      var name = "${menuNm}";
      wexcel.down(grid, name, name + ".xlsx");
    });
    
    <%-- 리스트 조회 --%>
    function search() {
      var param = {};
      param.grpNm = grpNm.text;

      $.postJSON("/sys/auth/authgroup/authgroup/list.sb", param, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        var list = result.data.list;
        if(list.length === undefined || list.length == 0) {
          s_alert.pop(result.message);
          return;
        }
        grid.itemsSource = list;
        })
        .fail(function(){
          s_alert.pop("Ajax Fail");
      });
    }

    <%-- 권한 그룹 추가 --%>
    $("#addBtn").click(function(e){
      var newItem = grid.collectionView.addNew();
      newItem.chk = false;
      grid.collectionView.commitNew();
    });

    <%-- 권한 그룹 저장 --%>
    $("#saveBtn").click(function(e){

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
      
      var url = "/sys/auth/authgroup/authgroup/save.sb";
      $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(paramArr),
        success: function(result){
          if (result.status === "OK") {
            s_alert.pop("<s:message code='msg.save.succ' />");
            grid.collectionView.clearChanges();
          } else if (result.status === "FAIL"){
            s_alert.pop(result.data.msg);
          }
        },
        cache: false,
        dataType: "json",
        contentType : 'application/json'
      });
    });

    <%-- 리스스 정보 저장 --%>
    $("#resrceSaveBtn").click(function(e){
    });

  });
</script>
















