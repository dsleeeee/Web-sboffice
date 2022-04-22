<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con" id="depositDdcTab" name="depositDdcTab">
    <div class="tabType1" ng-controller="depositDdcTabCtrl" ng-init="init()">
        <ul>
            <%-- 매장별집계 탭 --%>
            <li>
                <a id="storeTotalTab" href="#" class="on" ng-click="storeTotalTabShow()"><s:message code="depositDdc.storeTotal"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/depositDdc/depositDdcTab.js?ver=20220421.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장별집계 레이어 --%>
<c:import url="/WEB-INF/view/excclc/excclc/depositDdc/storeTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>