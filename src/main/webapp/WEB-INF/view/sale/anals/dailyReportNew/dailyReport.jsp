<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="dailyReportCtrl" ng-init="init()">
        <ul>
            <li>
                <a id="reportTab"	href="#" ng-click="reportShow()"	class="on"	><s:message code="dailyReport.report"/></a>			<%-- [영업일보]     Tab --%>
            </li>
            <li>
                <a id="configTab" 	href="#" ng-click="configShow()"				><s:message code="dailyReport.reportConfig"/></a>	<%-- [영업일보 구성] Tab --%>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/dailyReportNew/dailyReport.js" charset="utf-8"></script>


<c:import url="/WEB-INF/view/sale/anals/dailyReportNew/report/report.jsp">	<%-- [영업일보] layer --%>
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:import url="/WEB-INF/view/sale/anals/dailyReportNew/config/config.jsp">	<%-- [영업일보 구성] layer --%>
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

