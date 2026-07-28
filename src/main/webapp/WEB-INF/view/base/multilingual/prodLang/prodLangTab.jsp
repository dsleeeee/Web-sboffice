<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con">
    <div class="tabType1" ng-controller="prodLangTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품명 탭 --%>
            <li>
                <a id="prodNmTab" href="#" class="on" ng-click="prodNmShow()"><s:message code="prodLang.prodNm"/></a>
            </li>
            <%-- 상품설명 탭 --%>
            <li>
                <a id="prodInfoTab" href="#" ng-click="prodInfoShow()"><s:message code="prodLang.prodInfo"/></a>
            </li>
            <%-- 플랫폼 상품명 탭 --%>
            <li <c:if test="${hqOfficeCd != 'DS079' and hqOfficeCd != 'H0665'}">style="display: none;"</c:if> >
                <a id="prodPlatformTab" href="#" ng-click="prodPlatformShow()"><s:message code="prodLang.platform"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";

    var useYn = ${ccu.getCommCode("067")};

    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/prodLang/prodLangTab.js?ver=20260728.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품명 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/prodLang/prodNm.jsp">
</c:import>

<%-- 상품설명 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/prodLang/prodInfo.jsp">
</c:import>

<%-- 플랫폼 상품명 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/prodLang/prodPlatform.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>