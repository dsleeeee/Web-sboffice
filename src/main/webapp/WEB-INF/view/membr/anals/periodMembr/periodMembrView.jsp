<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="periodMembrCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('periodMembrCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w5"/>
      <col class="w15"/>
      <col class="w5"/>
      <col class="w25"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회기간 --%>
      <th>
        <s:message code="periodMembr.srchDate"/>
      </th>
      <td>
        <div class="sb-select">
          <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
          <span class="rg">~</span>
          <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
        </div>
      </td>
      <td>
        <button class="btn_blk sb-input w100" style="margin-left: 5px" ng-click="excelDownload()"><s:message
                code="member.excel"/></button>
      </td>
      <td></td>
    </tr>
    </tbody>
  </table>

  <%-- 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="periodMembr.membrNo"/>" binding="membrNo" width="115"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.membrNm"/>" binding="membrNm" width="130"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.membrCardNo"/>" binding="membrCardNo" width="115"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.saleCount"/>" binding="saleCount" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.saleAmt"/>" binding="saleAmt" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.dcAmt"/>" binding="dcAmt" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.membrSavePoint"/>" binding="membrSavePoint"
                             width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.membrUsePoint"/>" binding="membrUsePoint" width="115"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.minRegDt"/>" binding="minRegDt" width="130"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.maxRegDt"/>" binding="maxRegDt" width="130"
                             is-read-only="true" align="center"></wj-flex-grid-column>

        <%--팝업 조회시 필요--%>
        <wj-flex-grid-column header="<s:message code="periodMembr.saleDate"/>" binding="saleDate" width="115"
                             is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.posNo"/>" binding="posNo" width="115"
                             is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMembr.billNo"/>" binding="billNo" width="115"
                             is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/periodMembr/periodMembr.js?ver=2019052801.08"
        charset="utf-8"></script>

<%-- 매출 상세정보 --%>
<c:import url="/WEB-INF/view/membr/anals/dayMembr/dayMembrPurchsView.jsp">
</c:import>

<%-- 회원 상세정보 --%>
<c:import url="/WEB-INF/view/membr/anals/dayMembr/dayMembrDetailView.jsp">
</c:import>