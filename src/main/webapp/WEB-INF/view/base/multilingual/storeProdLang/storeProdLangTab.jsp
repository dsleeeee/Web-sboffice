<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="storeProdLangTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품명 탭 --%>
            <li>
                <a id="storeProdNmTab" href="#" class="on" ng-click="storeProdNmShow()"><s:message code="storeProdLang.prodNm"/></a>
            </li>
            <%-- 상품설명 탭 --%>
            <li>
                <a id="storeProdInfoTab" href="#" ng-click="storeProdInfoShow()"><s:message code="storeProdLang.prodInfo"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/storeProdLang/storeProdLangTab.js?ver=20250925.01" charset="utf-8"></script>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품명 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeProdLang/storeProdNm.jsp">
</c:import>

<%-- 상품설명 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeProdLang/storeProdInfo.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>
