<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con" id="storeTypeTabView" name="storeTypeTabView">
    <div class="tabType1" ng-controller="storeTypeTabCtrl" ng-init="init()">
        <ul>
            <%-- 매장타입 탭 --%>
            <li>
                <a id="storeTypeTab" href="#" class="on" ng-click="storeTypeShow()"><s:message code="storeType.storeType"/></a>
            </li>
            <%-- 메뉴그룹 탭 --%>
            <li>
                <a id="menuGroupTab" href="#" ng-click="menuGroupShow()"><s:message code="storeType.menuGroup"/></a>
            </li>
            <%-- 매장타입변경이력 탭 --%>
            <li>
                <a id="storeTypeChgHistTab" href="#" ng-click="storeTypeChgHistShow()"><s:message code="storeType.storeTypeChgHist"/></a>
            </li>
            <%-- 메뉴그룹변경이력 탭 --%>
            <li>
                <a id="menuGroupChgHistTab" href="#" ng-click="menuGroupChgHistShow()"><s:message code="storeType.menuGroupChgHist"/></a>
            </li>
            <%-- 매장적용이력 탭 --%>
            <li>
                <a id="storeApplyChgHistTab" href="#" ng-click="storeApplyChgHistShow()"><s:message code="storeType.storeApplyChgHist"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 브랜드
    var brandList = ${brandList};
    // 매장타입
    var storeTypeList = ${storeTypeList};
    // 메뉴그룹
    var storeGroupList = ${storeGroupList};
    // 매장타입자동적용(1106)
    var storeTypeAutoEnvstVal = "${storeTypeAutoEnvstVal}";
    // 매장타입판매가설정(1107)
    var storeTypeApplyEnvstVal = "${storeTypeApplyEnvstVal}";
    // 내점/배달/포장가격관리(0044)
    var subPriceFg = "${subPriceFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeTypeTab.js?ver=20221124.01" charset="utf-8"></script>
<%-- 탭페이지 레이어 시작 --%>
<%-- 매장타입 탭 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storeType/storeType.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 메뉴그룹 탭 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storeType/menuGroup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장타입변경이력 탭 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storeType/storeTypeChgHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 메뉴그룹변경이력 탭 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storeType/menuGroupChgHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장적용이력 탭 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storeType/storeApplyChgHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝--%>