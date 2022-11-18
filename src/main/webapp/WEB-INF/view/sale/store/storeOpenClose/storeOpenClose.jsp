<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="storeOpenCloseCtrl" ng-init="init()">
        <ul>
            <%-- 일별 탭 --%>
            <li>
                <a id="storeOpenCloseDayTab" href="#" class="on" ng-click="storeOpenCloseDayShow()"><s:message code="storeOpenClose.day"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="storeOpenCloseMonthTab" href="#" ng-click="storeOpenCloseMonthShow()"><s:message code="storeOpenClose.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeOpenClose/storeOpenClose.js?ver=20221111.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/store/storeOpenClose/storeOpenCloseDay.jsp">
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/store/storeOpenClose/storeOpenCloseMonth.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>

<%-- 본사 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/posExcclc/posExcclc/posExcclcDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
