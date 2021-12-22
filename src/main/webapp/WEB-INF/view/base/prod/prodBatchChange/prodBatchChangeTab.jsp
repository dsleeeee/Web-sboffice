<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="prodBatchChangeTabCtrl" ng-init="init()">
        <ul>
            <%-- 판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분 변경 탭 --%>
            <li>
                <a id="prodBatchChangeTab" href="#" class="on" ng-click="prodBatchChangeShow()"><s:message code="prodBatchChange.tab1"/></a>
            </li>
            <%-- 브랜드, 상품분류 변경 탭 --%>
            <li>
                <a id="prodBatchChange2Tab" href="#" ng-click="prodBatchChange2Show()"><s:message code="prodBatchChange.tab2"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%-- (상품관리)브랜드사용여부 --%>
    var brandUseFg = "${brandUseFg}";

    // 브랜드
    var brandList = ${brandList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodBatchChange/prodBatchChangeTab.js?ver=20211216.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분 변경 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodBatchChange/prodBatchChange.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 브랜드, 상품분류 변경 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodBatchChange/prodBatchChange2.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝--%>