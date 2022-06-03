<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale//status/daySaleReport"/>

<div class="con">
    <div class="tabType1" ng-controller="daySaleReportTabCtrl" ng-init="init()">
        <ul>
            <%-- 일별매출내역 조회 탭 --%>
            <li>
                <a id="daySaleReportListTab" href="#" class="on" ng-click="daySaleReportListShow()"><s:message code="daySaleReportTab.daySaleReportList"/></a>
            </li>
            <c:if test="${orgnFg == 'HQ'}">
                <%-- 일별매출내역 다운로드 탭 --%>
                <li>
                    <a id="daySaleReportTab" href="#" ng-click="daySaleReportShow()"><s:message code="daySaleReportTab.daySaleReport"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/daySaleReport/daySaleReportTab.js?ver=20220530.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별매출내역 조회(제너시스올떡 분식대장) 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/daySaleReport/daySaleReportList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일별매출내역 다운로드(제너시스올떡 분식대장) 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/daySaleReport/daySaleReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>