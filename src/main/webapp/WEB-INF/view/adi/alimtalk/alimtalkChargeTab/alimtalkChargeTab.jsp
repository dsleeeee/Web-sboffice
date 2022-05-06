<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/alimtalk/alimtalkChargeTab"/>

<div class="con">
    <div class="tabType1" ng-controller="alimtalkChargeTabCtrl" ng-init="init()">
        <ul>
            <%-- 알림톡충전/KCP PG 탭 --%>
            <li>
                <a id="smsChargeTab" href="#" class="on" ng-click="alimtalkChargeShow()"><s:message code="alimtalkChargeTab.alimtalkCharge"/></a>
            </li>
            <%-- 알림톡충전내역 탭 --%>
            <li>
                <a id="smsChargeHistTab" href="#" ng-click="alimtalkChargeHistShow()"><s:message code="alimtalkChargeTab.alimtalkChargeHist"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var tabVal = "${tabVal}"; // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
    var urlVal = "/adi/alimtalk/alimtalkChargeTab/alimtalkChargeTab/list.sb"; // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
    var pageGubunVal = "alimtalk"; // 페이지구분
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkChargeTab/alimtalkChargeTab.js?ver=20211021.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- SMS충전/KCP PG 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsCharge/smsCharge.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS충전내역 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/smsChargeHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>