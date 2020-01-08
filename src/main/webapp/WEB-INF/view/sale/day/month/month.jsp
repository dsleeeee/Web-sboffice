<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/month/month"/>

<div class="con" id="monthView" name="monthView">
    <div class="tabType1" ng-controller="monthCtrl" ng-init="init()">
        <ul>
            <%-- 월별종합 탭 --%>
            <li>
                <a id="monthTotalTab" href="#" class="on" ng-click="monthTotalShow()"><s:message code="month.total"/></a>
            </li>
            <%-- 할인구분별 탭 --%>
            <li>
                <a id="monthDcTab" href="#" ng-click="monthDcShow()"><s:message code="month.dc"/></a>
            </li>
            <%-- 과면세별 탭 --%>
            <li>
                <a id="monthTaxTab" href="#" ng-click="monthTaxShow()"><s:message code="month.tax"/></a>
            </li>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="monthTimeTab" href="#" ng-click="monthTimeShow()"><s:message code="month.time"/></a>
            </li>
            <%-- 상품분류별 탭 --%>
            <li>
                <a id="monthProdClassTab" href="#" ng-click="monthProdClassShow()"><s:message code="month.prodClass"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="monthCornerTab" href="#" ng-click="monthCornerShow()"><s:message code="month.corner"/></a>
            </li>
            <%-- 외식테이블 탭 --%>
            <li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>
                <a id="monthTableTab" href="#" ng-click="monthTableShow()"><s:message code="month.table"/></a>
            </li>
            <%-- 포스별 탭 --%>
            <li>
                <a id="monthPosTab" href="#" ng-click="monthPosShow()"><s:message code="month.pos"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/month.js?ver=20191209" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 월별종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 과면세별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTax.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 외식테이블 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTable.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>