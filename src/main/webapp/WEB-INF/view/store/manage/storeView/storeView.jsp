<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

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
        <%-- 본사코드 --%>
        <th><s:message code="storeView.hqOfficeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="sHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="storeView.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="sHqOfficeNm"></div>
          </div>
        </td>
      </tr>
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
        <%-- 사업자번호 --%>
        <th><s:message code="storeView.bizNo" /></th>
        <td>
          <div class="sb-select">
            <div id="sBizNo"></div>
          </div>
        </td>
        <%-- 상태 --%>
        <th><s:message code="storeView.sysStatFg" /></th>
        <td>
          <div class="sb-select">
            <div id="sSysStatFg"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 관리업체 --%>
        <th><s:message code="storeView.agencyNm" /></th>
        <td>
            <div id="sAgencyCd" style="display: none;"></div>
            <div class="sb-select w100">
              <div id="sAgencyNm"></div>
            </div>
        </td>
        <%-- 밴사 --%>
        <th><s:message code="storeView.van" /></th>
        <td>
          <div class="sb-select">
            <div id="sVanCd"></div>
          </div>
        </td>
      </tr>
      <tr>
        <th>
          <s:message code="cmm.search.date" />
        </th>
        <td colspan="3">
          <%-- 조회 일자 --%>
          <div class="sb-select fl w15"><div id="sDateType"></div> </div>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
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
    <div id="listScaleBox" class="w150 fl"></div>

    <%-- 엑셀 다운로드 --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
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
    var hqOfficeCd      = wcombo.genInput("#sHqOfficeCd");
    var hqOfficeNm      = wcombo.genInput("#sHqOfficeNm");
    var storeCd         = wcombo.genInput("#sStoreCd");
    var storeNm         = wcombo.genInput("#sStoreNm");
    var bizNo           = wcombo.genInput("#sBizNo");

    var sysStatFg       = wcombo.genCommonBox("#sSysStatFg", ${ccu.getCommCode("009")});
    var agencyCd        = wcombo.genInput("#sAgencyCd");
    var agencyNm        = wcombo.genInput("#sAgencyNm");
    agencyNm.isReadOnly = true;
    var vanCd           = wcombo.genCommonBox("#sVanCd", ${ccu.getVanList()});
    var dateType        = wcombo.genCommonBox("#sDateType",  [{"name":"<s:message code='cmm.all.day' />","value":""},{"name":"<s:message code='storeView.lastLogin' />","value":"1"},{"name":"<s:message code='storeView.sysOpen' />","value":"2"},{"name":"<s:message code='storeView.sysClose' />","value":"3"}]);
    var startDt         = wcombo.genDateVal("#startDt", "${ufn:addDaysString( ufn:currentDateString() , -7)}");
    var endDt           = wcombo.genDateVal("#endDt", "${ufn:currentDateString()}");
    var listScaleBox    = wcombo.genCommonBox("#listScaleBox", ${ccu.getListScale()});

    <%-- 조회결과 --%>
    var svData =
      [
        {binding:"hqOfficeCd", header:"<s:message code='storeView.hqOfficeCd' />",width:"*"},
        {binding:"hqOfficeNm", header:"<s:message code='storeView.hqOfficeNm' />",width:"*"},
        {binding:"storeCd", header:"<s:message code='storeView.storeCd' />",width:"*"},
        {binding:"storeNm", header:"<s:message code='storeView.storeNm' />",width:"*"},
        {binding:"bizNo", header:"<s:message code='storeView.bizNo' />",width:"*"},
        {binding:"ownerNm", header:"<s:message code='storeView.ownerNm' />",width:"*"},
        {binding:"telNo", header:"<s:message code='storeView.telNo' />",width:"*"},
        {binding:"sms", header:"<s:message code='storeView.sms' />",width:"*", visible:false},
        {binding:"sysStatFgNm", header:"<s:message code='storeView.sysStatFgNm' />",width:"*"},
        {binding:"clsFgNm", header:"<s:message code='storeView.clsFgNm' />",width:"*"},
        {binding:"posCnt", header:"<s:message code='storeView.posCnt' />",width:"*"},
        {binding:"agencyCd", header:"<s:message code='storeView.agencyCd' />",width:"*", visible:false},
        {binding:"agencyNm", header:"<s:message code='storeView.agencyNm' />",width:"*"},
        {binding:"vanNm", header:"<s:message code='storeView.vanNm' />",width:"*"},
        {binding:"", header:"<s:message code='storeView.goods' />",width:"*", visible:false},
        {binding:"cornerUseYnNm", header:"<s:message code='storeView.cornerUseYnNm' />", width:"*", visible:false},
        {binding:"posLastLoginDt", header:"<s:message code='storeView.posLastLoginDt' />",width:"*"},
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
          getVanConfigDetail(param);
        }
      }
    });

    <%-- 매장선택 --%>
    $("#sAgencyNm").click(function(e){
      agencyCd.text = "";
      agencyNm.text = "";
      agencyNm.text = "";
      c_ca.init(function(arr){
        agencyCd.text = arr[0].cd;
        agencyNm.text = arr[0].nm;
      });
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
      param.hqOfficeCd = hqOfficeCd.text;
      param.hqOfficeNm = hqOfficeNm.text;
      param.storeCd = storeCd.text;
      param.storeNm = storeNm.text;
      param.bizNo = bizNo.text;
      param.sysStatFg = sysStatFg.selectedValue;
      param.agencyCd = agencyCd.text;
      param.vanCd = vanCd.selectedValue;
      param.dateType = dateType.selectedValue;
      param.startDt = getDate(startDt);
      param.endDt = getDate(endDt);
      param.listScale = listScaleBox.selectedValue;
      param.curr = index;

      wgrid.getGridData("/store/manage/storeView/storeView/list.sb", param, grid, function(result){
        page.make("#page", result.data.page.curr, result.data.page.totalPage);
        },
        function(){
          s_alert.pop("Ajax Fail");
        }
      );
    }
  });
</script>

<%-- 업체 선택 --%>
<c:import url="/WEB-INF/view/application/layer/cmAgency.jsp">
</c:import>

<%-- 밴사 설정정보 --%>
<c:import url="/WEB-INF/view/base/store/view/vanConfg.jsp">
</c:import>
