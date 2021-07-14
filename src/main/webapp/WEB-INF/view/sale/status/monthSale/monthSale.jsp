<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/monthSale/monthSale"/>

<div class="con">
    <div class="tabType1" ng-controller="monthSaleCtrl" ng-init="init()">
        <ul>
            <%-- 월별종합 탭 --%>
            <li>
                <a id="monthTotalTab" href="#" class="on" ng-click="monthTotalShow()"><s:message code="month.total"/></a>
            </li>
            <%-- 할인구분별 탭 --%>
            <li>
                <a id="monthDcTab" href="#" ng-click="monthDcShow()"><s:message code="month.dc"/></a>
            </li>
            <%-- 과면세별 탭 --%>
            <li>
                <a id="monthTaxTab" href="#" ng-click="monthTaxShow()"><s:message code="month.tax"/></a>
            </li>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="monthTimeTab" href="#" ng-click="monthTimeShow()"><s:message code="month.time"/></a>
            </li>
            <%-- 상품분류별 탭 --%>
            <li>
                <a id="monthProdClassTab" href="#" ng-click="monthProdClassShow()"><s:message code="month.prodClass"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="monthCornerTab" href="#" ng-click="monthCornerShow()"><s:message code="month.corner"/></a>
            </li>
            <%-- 외식테이블 탭 --%>
            <c:if test="${orgnFg == 'STORE'}">
                <li>
                <%--<li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>--%>
                    <a id="monthTableTab" href="#" ng-click="monthTableShow()"><s:message code="month.table"/></a>
                </li>
            </c:if>
            <%-- 포스별 탭 --%>
            <li>
                <a id="monthPosTab" href="#" ng-click="monthPosShow()"><s:message code="month.pos"/></a>
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
    posParam.storeCd     = "${posCol.storeCd}";
    posParam.storeNm     = "${posCol.storeNm}";
    posParam.storePosNo     = "${posCol.storePosNo}";
    posColList.push(posParam);
    </c:forEach>

    var posCol    = '${posCol}';
    var arrPosCol = posCol.split(',');

    // 상품분류별 - 분류레벨 최대값
    var maxLevel    = '${maxLevel}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/monthSale/monthSale.js?ver=20210322.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 월별종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 과면세별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTax.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:if test="${orgnFg == 'STORE'}">
    <%-- 외식테이블 레이어 --%>
    <c:import url="/WEB-INF/view/sale/day/month/monthTable.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
</c:if>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/monthPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>

<%-- 팝업 레이어 시작 --%>
<%-- 매장별 매출현황 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayStoreDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품매출 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/prodInfo/prodSaleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 할인내역 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayStoreDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 영수건수 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayBillInfo/dayStoreBill.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품매출 상세내역 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayProdSaleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- //팝업 레이어 --%>


<%-- 결제수단 팝업 레이어 시작 --%>
<%-- 신용카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayCard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 현금 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayCash.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- PAYCO 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPayco.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 포인트 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVpoint.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 전자상품권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVcharge.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 모바일페이 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayMpay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 모바일쿠폰 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayMcoupn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포인트 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPoint.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원선불 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPrepaid.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원후불 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPostpaid.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayGift.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 식권 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayFstmp.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 제휴카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPartner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사원카드 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayEmpCard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 가승인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayTemporary.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //결제수단 팝업 레이어 --%>