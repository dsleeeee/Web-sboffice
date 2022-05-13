<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/daySale/daySale"/>

<div class="con">
    <div class="tabType1" ng-controller="daySaleCtrl" ng-init="init()">
        <ul>
            <%-- 일별종합 탭 --%>
            <li>
                <a id="dayTotalTab" href="#" class="on" ng-click="dayTotalShow()"><s:message code="day.dayTotal"/></a>
            </li>
<%--            &lt;%&ndash; 할인구분별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayDcTab" href="#" ng-click="dcShow()"><s:message code="day.dc"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 과면세별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayTaxTab" href="#" ng-click="taxShow()"><s:message code="day.tax"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 시간대별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayTimeTab" href="#" ng-click="timeShow()"><s:message code="day.time"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 상품분류별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayProdClassTab" href="#" ng-click="prodClassShow()"><s:message code="day.prodClass"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 코너별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayCornerTab" href="#" ng-click="cornerShow()"><s:message code="day.corner"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 외식테이블 탭 &ndash;%&gt;--%>
<%--            <c:if test="${orgnFg == 'STORE'}">--%>
<%--                <li>--%>
<%--                &lt;%&ndash;<li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>&ndash;%&gt;--%>
<%--                    <a id="dayTableTab" href="#" ng-click="tableShow()"><s:message code="day.table"/></a>--%>
<%--                </li>--%>
<%--            </c:if>--%>
<%--            &lt;%&ndash; 포스별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayPosTab" href="#" ng-click="posShow()"><s:message code="day.pos"/></a>--%>
<%--            </li>--%>
<%--            &lt;%&ndash; 사원카드별 탭 &ndash;%&gt;--%>
<%--            <li>--%>
<%--                <a id="dayEmpCardTab" href="#" ng-click="empCardShow()"><s:message code="day.empCard"/></a>--%>
<%--            </li>--%>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/status/daySale/daySale.js?ver=20210322.04" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayCashTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 과면세별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayTax.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:if test="${orgnFg == 'STORE'}">
    <%-- 외식테이블 레이어 --%>
    <c:import url="/WEB-INF/view/sale/day/day/dayTable.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
</c:if>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사원카드별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayEmpCard.jsp">
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

<%-- 매장별 매출현황 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayProdDtl.jsp">
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
<%-- 매장별 영수건수 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayBillInfo/dayStoreBill2.jsp">
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

<%-- 스마트오더 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVorder.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //결제수단 팝업 레이어 --%>


<%-- 할인 팝업 레이어 시작 --%>
<%-- 일반할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/generalDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/coupnDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/membrDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 제휴할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/partnerDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 서비스 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/serviceDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 프로모션할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/promtnDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VMEM 쿠폰할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/vcoupnDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 스마트 오더 할인 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dcInfo/vorderDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //할인 팝업 레이어 시작 --%>

<%-- 영수증 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/billInfo/billInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="payColList" value="${payColList}"/>
    <c:param name="guestColList" value="${guestColList}"/>
</c:import>
