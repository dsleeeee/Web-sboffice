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

    <%-- 엑셀G 버튼 --%>
    <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()">
      <s:message code="dayDlvr.leftExcelDownload"/>
    </button>
    <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()">
      <s:message code="dayDlvr.rightExcelDownload"/>
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
        <wj-flex-grid-column header="<s:message code="dayDlvr.saleDate"/>" binding="nonDlvrSaleDate" width="110"
                             align="center"
                             is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayDlvr.cntBillNo"/>" binding="cntDlvrBillNo" width="150"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="sumDlvrRealSaleAmt"
                             width="200"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayDlvr.cntBillNo"/>" binding="cntBillNo" width="150"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="sumRealSaleAmt" width="200"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="dayDlvrCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dayDlvr/dayDlvr.js?ver=2019052801.11"
        charset="utf-8"></script>
