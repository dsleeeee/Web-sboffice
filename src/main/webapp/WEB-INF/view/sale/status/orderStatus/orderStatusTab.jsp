<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="orderStatusTabCtrl" ng-init="init()">
        <ul>
            <%-- 주문현황 --%>
            <li>
                <a id="orderStatusTab" href="#" class="on" ng-click="orderStatusTabShow()"><s:message code="orderStatus.orderStatus"/></a>
            </li>
            <%-- 주문취소 --%>
            <li>
                <a id="orderCancelTab" href="#" ng-click="orderCancelTabShow()"><s:message code="orderStatus.orderCancel"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/orderStatus/orderStatusTab.js?ver=20230517.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 주문현황 --%>
<c:import url="/WEB-INF/view/sale/status/orderStatus/orderStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 주문취소 --%>
<c:import url="/WEB-INF/view/sale/status/orderStatus/orderCancel.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
