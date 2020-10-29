<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderReport/"/>

<div id="reportView" style="display: none;" ng-controller="vendrOrderReportCtrl">
  <div class="mt5 mb10 tr">
    <%-- 발주서 인쇄 --%>
    <button type="button" class="btn_skyblue ml5" id="btnPrint" ng-click="print()" ng-if="btnPrintIfFg">
      <s:message code="cmm.print"/></button>
    <%-- 발주서 엑셀다운 --%>
    <%--<button type="button" class="btn_skyblue ml5" id="btnExcelDown" ng-click="excelDown()" ng-if="btnExcelDownIfFg">--%>
    <%--<s:message code="vendrOrder.report.excelDown"/></button>--%>
  </div>

  <div class="vendrOrderReport reportPrint w100" id="report">
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderReport.js?ver=20190116.02" charset="utf-8"></script>
