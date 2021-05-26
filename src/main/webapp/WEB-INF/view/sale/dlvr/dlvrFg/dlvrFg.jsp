<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con" id="dlvrFgView" name="dlvrFgView">
    <div class="tabType1" ng-controller="dlvrFgCtrl" ng-init="init()">
        <ul>
            <%-- 상품별 탭 --%>
            <li>
                <a id="dlvrFgTimeTab" href="#" class="on" ng-click="dlvrFgProdShow()"><s:message code="dlvrFg.prod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>
    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');

    <c:if test="${hqOfficeCd eq 'A0001'}">
        var comboFgData = ${comboFgData};
    </c:if>
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/dlvr/dlvrFg/dlvrFg.js?ver=20210521.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품별 레이어 --%>
<c:import url="/WEB-INF/view/sale/dlvr/dlvrFg/dlvrFgProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- //탭페이지 레이어 --%>