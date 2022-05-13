<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="kitchenprintCtrl" ng-init="init()">
        <ul>
            <%-- 상품-매장주방프린터 연결 탭 --%>
            <li>
                <a id="prodKitchenprintLinkTab" href="#" class="on" ng-click="prodKitchenprintLinkShow()"><s:message code="prodKitchenprintLink.prodKitchenprintLink"/></a>
            </li>
            <%-- 주방프린터그룹관리 탭 --%>
            <li>
                <a id="printerGroupTab" href="#" ng-click="printerGroupShow()"><s:message code="printerGroup.printerGroup"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 브랜드
    var brandList = ${brandList};
    // 매장타입
    var storeGroupList = ${storeGroupList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodKitchenprintLink/kitchenprintTab.js?ver=20220510.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품-매장주방프린터 연결 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodKitchenprintLink/prodKitchenprintLink.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 주방프린터그룹관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodKitchenprintLink/printerGroup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- //탭페이지 레이어 --%>
