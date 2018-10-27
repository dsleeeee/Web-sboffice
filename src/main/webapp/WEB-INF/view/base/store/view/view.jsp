<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
        <%-- 매장코드 --%>
        <th><s:message code="storeView.storeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="sStoreCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="storeView.storeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="sStoreNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장형태 --%>
        <th><s:message code="storeView.storeTypeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="sStoreType"></div>
          </div>
        </td>
        <%-- 매장그룹 --%>
        <th><s:message code="storeView.clsFgNmG" /></th>
        <td>
          <div class="sb-select">
            <div id="sClsFg"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장상태 --%>
        <th><s:message code="storeView.sysStatFg" /></th>
        <td>
          <div class="sb-select">
            <div id="sSysStatFg"></div>
          </div>
        </td>
        <th></th>
        <td>
          <div>
          </div>
        </td>
      </tr>
      </tbody>
  </table>

  <div class="mt10 pdb20 oh">
    <button id="searchBtn" class="btn_blue fr" >
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w100px fl"></div>
    <%-- 엑셀 다운로드 --%>
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>

  $(document).ready(function() {
      <%-- 조회조건 --%>
    var storeCd         = wcombo.genInput("#sStoreCd");
    var storeNm         = wcombo.genInput("#sStoreNm");
    //var storeType       = wcombo.genCommonBox("#sStoreType", ${ccu.getCommCode("100")});
    var storeType        = wcombo.genCommonBox("#sStoreType",  [{"name":"전체","value":""},{"name":"단독","value":"1"},{"name":"가맹","value":"2"}]);
    var clsFg                   = wcombo.genCommonBox("#sClsFg", ${ccu.getCommCode("003")});
    var sysStatFg       = wcombo.genCommonBox("#sSysStatFg", ${ccu.getCommCode("009")});
    var listScaleBox    = wcombo.genCommonBox("#listScaleBox", ${ccu.getListScale()});

    <%-- 조회결과 --%>
    var svData =
      [
        {binding:"storeCd", header:"<s:message code='storeView.storeCd' />",width:"*"},
        {binding:"storeNm", header:"<s:message code='storeView.storeNm' />",width:"*"},
        {binding:"storeTypeNm", header:"<s:message code='storeView.storeTypeNm' />",width:"*"},
        {binding:"clsFgNm", header:"<s:message code='storeView.clsFgNmG' />",width:"*"},
        {binding:"bizNo", header:"<s:message code='storeView.bizNo' />",width:"*"},
        {binding:"ownerNm", header:"<s:message code='storeView.ownerNm' />",width:"*"},
        {binding:"telNo", header:"<s:message code='storeView.telNo' />",width:"*"},
        {binding:"mpNo", header:"<s:message code='storeView.mpNo' />",width:"*"},
        {binding:"sms", header:"<s:message code='storeView.sms' />",width:"*"},
        {binding:"address", header:"<s:message code='storeView.busiAdress' />",width:"*"},
        {binding:"sysStatFgNm", header:"<s:message code='storeView.sysStatFgNm' />",width:"*"},
        {binding:"", header:"<s:message code='storeView.touckKey' />",width:"*"},
        {binding:"posVerNo", header:"<s:message code='storeView.posVerNo' />",width:"*"},
        {binding:"sysOpenDate", header:"<s:message code='storeView.sysOpenDate' />",width:"*"},
        {binding:"sysClosureDate", header:"<s:message code='storeView.sysClosureDate' />",width:"*"}
      ];

    var grid  = wgrid.genGrid("#theGrid", svData, "${menuCd}", 1, ${clo.getColumnLayout(1)});

    <%-- 그리드 링크 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if( col.binding == "storeNm" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
        else if( col.binding == "ownerNm" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    <%-- 그리드 선택 이벤트 --%>
    grid.addEventListener(grid.hostElement, 'click', function(e) {
      var ht = grid.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding == "storeNm") {
          var param = {};
          param.storeCd = grid.rows[ht.row].dataItem.storeCd;
          param.storeNm = grid.rows[ht.row].dataItem.storeNm;
          getStoreDetail(param);
        }
        else if( col.binding == "ownerNm") {
          var param = {};
          param.storeCd = grid.rows[ht.row].dataItem.storeCd;
          param.storeNm = grid.rows[ht.row].dataItem.storeNm;
          getVanConfigDetail(param);
        }
      }
    });

    <%-- 리스트 조회 --%>
    $("#searchBtn").click(function(e){
      search(1);
    });

    <%-- 페이징 --%>
    $(document).on("click", ".page", function() {
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

      param.storeCd = storeCd.text;
      param.storeNm = storeNm.text;
      param.storeType = storeType.selectedValue;
      param.clsFg = clsFg.selectedValue;
      param.sysStatFg = sysStatFg.selectedValue;
      param.listScale = listScaleBox.selectedValue;
      param.curr = index;

      wgrid.getGridData("/base/store/view/view/list.sb", param, grid,
        function(result){
          page.make("#page", result.data.page.curr, result.data.page.totalPage);
        },
        function(){
          s_alert.pop("Ajax Fail");
        }
      );
    }
  });
</script>

<%-- 매장 상세정보 --%>
<c:import url="/WEB-INF/view/base/store/view/dtl.jsp">
</c:import>
<%-- 밴사 설정정보 --%>
<c:import url="/WEB-INF/view/base/store/view/vanConfg.jsp">
</c:import>
