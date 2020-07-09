<%--
  Created by IntelliJ IDEA.
  User: doasys_daniel
  Date: 20-7-9
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="dayDlvrCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('dayDlvrCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w5"/>
      <col class="w30"/>
      <col class="w5"/>
      <col class="w25"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회기간 --%>
      <th>
        <s:message code="dlvrInfo.srchDate"/>
      </th>
      <td>
        <div class="sb-select">
          <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
          <span class="rg">~</span>
          <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
        </div>
      </td>
      <th>
        <s:message code="dlvrInfo.membrNm"/>
      </th>
      <td>
        <input type="text" id="membrNm" class="sb-input w40" ng-model="membrNm" maxlength="15"/>
      </td>
      <td></td>
    </tr>
    </tbody>
  </table>

  <%-- 그리드 --%>
  <%-- 페이지 스케일  --%>
  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>

    <%-- 엑셀 --%>
    <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()">
      <s:message code="cmm.excel"/>
    </button>
  </div>
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
        <wj-flex-grid-column header="<s:message code="dlvrInfo.date"/>" binding="saleDate" width="110" align="center"
                             is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.billNo"/>" binding="billNo" width="100" align="center"
                             is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.realSaleAmt"/>" binding="realSaleAmt" width="200"
                             is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.dlvrAddr"/>" binding="dlvrAddr" width="150"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.dlvrTelNo"/>" binding="dlvrTelNo" width="200"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.membrNm"/>" binding="membrNm" width="200"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.billDt"/>" binding="billDt" width="150"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrInfo.empNm"/>" binding="empNm" width="200"
                             is-read-only="true" align="right"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="memberChgBatchCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dayDlvr/dayDlvr.js?ver=2019052801.11"
        charset="utf-8"></script>
