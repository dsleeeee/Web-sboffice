<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="dcSaleCtrl" ng-init="init()">
        <%-- <ul>
            일자별 탭
            <li>
                <a id="dcDcfgTab" href="#" class="on" ng-click="dcDcfgShow()"><s:message code="dcDcfg.dcfg"/></a>
            </li>
        </ul> --%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dc/dcSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/dc/dcfg/dcfg.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 
<%-- 탭페이지 레이어 끝 --%>