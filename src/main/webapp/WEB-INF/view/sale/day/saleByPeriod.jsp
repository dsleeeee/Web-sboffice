<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="saleByPeriodCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 탭 --%>
            <li>
                <a id="dayTab" href="#" class="on" ng-click="dayShow()"><s:message code="day.day"/></a>
            </li>
            <%-- 요일별 탭 --%>
            <li>
                <a id="dayOfWeekTab" href="#" ng-click="dayOfWeekShow()"><s:message code="day.dayOfWeek"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="monthTab" href="#" ng-click="monthShow()"><s:message code="day.month"/></a>
            </li>
            <%-- 설정기간별 탭 --%>
            <li>
                <a id="dayPeriodTab" href="#" ng-click="dayPeriodShow()"><s:message code="day.dayPeriod"/></a>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/day/saleByPeriod.js?ver=20200130.04" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/day.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/month.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriod.jsp">
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
