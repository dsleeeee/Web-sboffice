<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="memberRegistLayer" control="memberRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:900px;height:740px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="memberRegistCtrl" ng-init="init()">
    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <s:message code="regist.membr.info"/>
        <span id="memberInfoTitle" class="ml20"></span>
        <%-- 닫을때 기본정보 form 초기화가 안됨 ㅡㅡ --%>
        <%-- <a href="#" class="wj-hide btn_close"></a> --%>
    </div>
    <div class="con">
        <div class="tabType1">
            <ul>
                <%-- 기본정보 --%>
                <li>
                    <a id="basicTab" href="#" class="on" ng-click="basicShow()"><s:message code="regist.membr.basic"/></a>
                </li>
                <%-- 카드정보 --%>
                <li>
                    <a id="cardTab" href="#" ng-click="cardShow()"><s:message code="regist.membr.card.info"/></a>
                </li>
                <%-- 배달지관리 --%>
                <li>
                    <a id="deliTab" href="#" ng-click="deliShow()"><s:message code="regist.membr.delivery"/></a>
                </li>
            </ul>
        </div>
    </div>
</wj-popup>

<script>
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberRegist.js?ver=20201116.01" charset="utf-8"></script>

<%-- 기본 레이어 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberBasic.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 카드 레이어 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberCard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 배달지관리 레이어 --%>
<c:import url="/WEB-INF/view/membr/info/view/memberDeli.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>