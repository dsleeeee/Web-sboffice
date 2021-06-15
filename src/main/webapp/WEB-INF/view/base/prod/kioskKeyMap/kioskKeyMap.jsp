<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con" id="kioskKeyMapView" name="kioskKeyMapView">
    <div class="tabType1" ng-controller="kioskKeyMapManageCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크키맵등록 탭 --%>
            <li>
                <a id="kioskKeyMapRegistTab" href="#" class="on" ng-click="kioskKeyMapRegistShow()"><s:message code="kioskKeyMap.kioskKeyMapRegist"/></a>
            </li>
            <%-- 키오스크키맵복사 탭 --%>
            <%--<li>
                <a id="kioskKeyMapCopyTab" href="#" ng-click="kioskKeyMapCopyShow()"><s:message code="kioskKeyMap.kioskKeyMapCopy"/></a>
            </li>--%>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMap.js?ver=20200903.03" charset="utf-8"></script>
<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크키맵등록 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 키오스크키맵복사 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapCopy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝--%>