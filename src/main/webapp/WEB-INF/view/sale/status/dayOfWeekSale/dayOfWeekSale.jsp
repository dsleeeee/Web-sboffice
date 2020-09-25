<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeekSale/dayOfWeekSale"/>

<div class="con">
    <div class="tabType1" ng-controller="dayOfWeekSaleCtrl" ng-init="init()">
        <ul>
            <%-- 주간종합 탭 --%>
            <li>
                <a id="dayOfWeekTotalTab" href="#" class="on" ng-click="dayOfWeekTotalShow()"><s:message code="dayofweek.total"/></a>
            </li>
            <%-- 할인구분별 탭 --%>
            <li>
                <a id="dayOfWeekDcTab" href="#" ng-click="dayOfWeekDcShow()"><s:message code="dayofweek.dc"/></a>
            </li>
            <%-- 과면세별 탭 --%>
            <li>
                <a id="dayOfWeekTaxTab" href="#" ng-click="dayOfWeekTaxShow()"><s:message code="dayofweek.tax"/></a>
            </li>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="dayOfWeekTimeTab" href="#" ng-click="dayOfWeekTimeShow()"><s:message code="dayofweek.time"/></a>
            </li>
            <%-- 상품분류별 탭 --%>
            <li>
                <a id="dayOfWeekProdClassTab" href="#" ng-click="dayOfWeekProdClassShow()"><s:message code="dayofweek.prodClass"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="dayOfWeekCornerTab" href="#" ng-click="dayOfWeekCornerShow()"><s:message code="dayofweek.corner"/></a>
            </li>
            <%-- 외식테이블 탭 --%>
            <li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> >
                <a id="dayOfWeekTableTab" href="#" ng-click="dayOfWeekTableShow()"><s:message code="dayofweek.table"/></a>
            </li>
            <%-- 포스별 탭 --%>
            <li>
                <a id="dayOfWeekPosTab" href="#" ng-click="dayOfWeekPosShow()"><s:message code="dayofweek.pos"/></a>
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


    // 코너
    var cornerColList = [];
    <%--javascript에서 사용할 코너 json 데이터 생성--%>
    <c:forEach var="cornerCol" items="${cornerColList}">
    var cornerParam      = {};
    cornerParam.storeCornrCd     = "${cornerCol.storeCornrCd}";
    cornerParam.storeNm     = "${cornerCol.storeNm}";
    cornerParam.storeCd     = "${cornerCol.storeCd}";
    cornerParam.cornrNm     = "${cornerCol.cornrNm}";
    cornerColList.push(cornerParam);
    </c:forEach>

    var cornerCol    = '${cornerCol}';
    var arrCornerCol = cornerCol.split(',');


    // 테이블
    var tableColList = [];
    <%--javascript에서 사용할 포스 json 데이터 생성--%>
    <c:forEach var="tableCol" items="${tableColList}">
    var tableParam      = {};
    tableParam.tblCd     = "${tableCol.tblCd}";
    tableParam.tblNm     = "${tableCol.tblNm}";
    tableColList.push(tableParam);
    </c:forEach>

    var tableCol    = '${tableCol}';
    var arrTableCol = tableCol.split(',');


    // 포스
    var posColList = [];
    <%--javascript에서 사용할 포스 json 데이터 생성--%>
    <c:forEach var="posCol" items="${posColList}">
    var posParam      = {};
    posParam.posNo     = "${posCol.posNo}";
    posParam.posNm     = "${posCol.posNm}";
    posColList.push(posParam);
    </c:forEach>

    var posCol    = '${posCol}';
    var arrPosCol = posCol.split(',');

    // 상품분류별 - 분류레벨 최대값
    var maxLevel    = '${maxLevel}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dayOfWeekSale/dayOfWeekSale.js?ver=20200924.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 주간종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 과면세별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTax.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 외식테이블 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTable.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>

<%-- 팝업 레이어 시작 --%>
<%-- 매장별 매출현황 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/day/day/dayStoreDtl.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 상품매출 상세 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/prodInfo/prodSaleDtl.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 매장별 할인내역 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/day/day/dayStoreDc.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 매장별 영수건수 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayBillInfo/dayStoreBill.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 상품매출 상세내역 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/day/day/dayProdSaleDtl.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 매출 상세내역 (매출) 팝업 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/saleInfo/saleDtl.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- //팝업 레이어 --%>