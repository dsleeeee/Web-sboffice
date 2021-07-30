<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con">
    <div class="tabType1" ng-controller="rtnStatusSaleCtrl" ng-init="init()">
        <ul>
            <%-- 반품현황(영수증상세) --%>
            <li>
                <a id="rtnStatusBillTab" href="#" class="on" ng-click="rtnStatusBillTabShow()"><s:message code="rtnStatus.rtnBill"/></a>
            </li>
            <%-- 반품현황 --%>
            <li>
                <a id="rtnStatusDayTab" href="#" ng-click="rtnStatusDayTabShow()"><s:message code="rtnStatus.rtnStatus"/></a>
            </li>
            <%-- 상품별 반품현황 --%>
            <li>
                <a id="rtnStatusProdTab" href="#" ng-click="rtnStatusProdTabShow()"><s:message code="rtnStatus.prod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');


    // 할인
    var dcColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam      = {};
    dcParam.dcCd     = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    var dcCol    = '${dcCol}';
    var arrDcCol = dcCol.split(',');

    var guestCol    = '${guestCol}';
    var arrGuestCol = guestCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatusSale.js?" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 반품현황(영수증상세) --%>
<c:import url="/WEB-INF/view/sale/status/rtnStatus/rtnStatusBill.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 반품현황 --%>
<c:import url="/WEB-INF/view/sale/status/rtnStatus/rtnStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품별 반품현황 --%>
<c:import url="/WEB-INF/view/sale/status/rtnStatus/prod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>