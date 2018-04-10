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
        <th>
          <%-- 그룹명 --%>
          <s:message code="authGroup.grpNm" />
        </th>
        <td colspan="3">
          <div class="sb-select fl w70">
            <div id="grpNm" class="sb-input w80"></div>
          </div>
        </td>
      </tr>
      <tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <button id="searchBtn" class="btn_blue fr" >
      <s:message code="label.cmm.search" />
    </button>
  </div>
  
  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
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
        <div id="theGrid"></div><%--tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요.--%>
        <%--//위즈모 테이블--%>

        <%--페이지 리스트--%>
        <div class="pageNum mt20">
          <ul id="page1" data-size="10"></ul>
        </div>
        <%--//페이지 리스트--%>
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
    var rdata = 
      [
        {binding:"no", header:"<s:message code='cmm.no' />"},
        {binding:"grpCd", header:"<s:message code='authGroup.grpCd' />"},
        {binding:"grpNm", header:"<s:message code='authGroup.grpNm' />"},
        {binding:"targetAllFg", header:"<s:message code='authGroup.targetAllFg' />"},
        {binding:"targetOrgn", header:"<s:message code='authGroup.targetOrgn' />"},
        {binding:"targetOrgnNm", header:"<s:message code='authGroup.targetOrgnNm' />"},
        {binding:"remark", header:"<s:message code='cmm.remark' />"},
        {binding:"useYn", header:"<s:message code='cmm.use' />"}
      ];
    
    var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
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
      search(1);
    });
    
    <%-- 페이징 --%>
    $(document).on("click", ".page1", function() {
      search($(this).data("value"));
    });
    
    <%-- 엑셀 다운로드 --%>
    $("#excelBtn").click(function( e ){
      var name = "${menuNm}";
      wexcel.down(grid, name, name + ".xlsx");
    });
    
    <%-- 리스트 조회 --%>
    function search(index) {
      var param = {};
      param.grpNm = grpNm.text;

      param.listScale = listScaleBox.selectedValue;
      param.curr = index;
      
      $.postJSON("sys/auth/authgroup/authgroup/list.sb", param, function(result) {
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
        page.make("#page1", result.data.page.curr, result.data.page.totalPage);
        })
        .fail(function(){
          s_alert.pop("Ajax Fail");
      });
    }

    <%-- 권한 그룹 추가 --%>
    $("#addBtn").click(function(e){
    });

    <%-- 권한 그룹 저장 --%>
    $("#saveBtn").click(function(e){
    });

    <%-- 리스스 정보 저장 --%>
    $("#resrceSaveBtn").click(function(e){
    });

  });
</script>
















