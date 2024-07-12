<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="dailyTableKwu2Ctrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('dailyTableKwu2Ctrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 조회일자 --%>
                <th>
                    <s:message code="cmm.search.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <c:if test="${orgnFg == 'HQ'}">
                <tr>
                        <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                            <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="dailyTableKwu2SelectStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${orgnFg == 'STORE'}">
                <input type="hidden" id="dailyTableKwu2SelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>
        <div class="mt10 oh sb-select dkbr">
            <span class="fr">
                <button class="btn_skyblue" ng-click="print('1')">첫째 장 <s:message code="cmm.print"	/></button>
                <button class="btn_skyblue" ng-click="print('2')" style="display: none;">둘째 장 <s:message code="cmm.print"	/></button>
             <button class="btn_skyblue" ng-click="excelDownload()" ><s:message code="cmm.excel.down"	/></button>
            </span>
        </div>
    </div>

    <%-- 매출종합 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwu2Ctrl_sl">
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.saleAmt"/>" binding="saleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.dcAmt"/>" binding="dcAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.netSaleAmt"/>" binding="netSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.membrSaleAmt"/>" binding="membrSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthSaleAmt"/>" binding="monthSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthDcAmt"/>" binding="monthDcAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthNetSaleAmt"/>" binding="monthNetSaleAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthMembrSaleAmt"/>" binding="monthMembrSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.guestCnt"/>" binding="guestCnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.guestUprc"/>" binding="guestUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthGuestCnt"/>" binding="monthGuestCnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthGuestUprc"/>" binding="monthGuestUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 분류 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwu2Ctrl_prodClass">
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.prodClassCd"/>" binding="prodClassCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.prodClassNm"/>" binding="prodClassNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.cashAmt"/>" binding="cashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.cardAmt"/>" binding="cardAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.etcSaleAmt"/>" binding="etcSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthSaleQty"/>" binding="monthSaleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthCashAmt"/>" binding="monthCashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthCardAmt"/>" binding="monthCardAmt" width="150" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthEtcSaleAmt"/>" inding="monthEtcSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthRealSaleAmt"/>" binding="monthRealSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 결제수단 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwu2Ctrl_pay">
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.cashAmt"/>" binding="cashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.cardAmt"/>" binding="cardAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.postpaidAmt"/>" binding="postpaidAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.gitfAmt"/>" binding="gitfAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.bMonthUnpaidAmt"/>" binding="bMonthUnpaidAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.monthUnpaidAmt"/>" binding="monthUnpaidAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.commissionAmt"/>" binding="commissionAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.interestAmt"/>" binding="interestAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 반품출납 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwu2Ctrl_rtn">
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.rtnSaleCnt"/>" binding="rtnSaleCnt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.rtnSaleAmt"/>" binding="rtnSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.totAmt"/>" binding="totAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.outAmt"/>" binding="outAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu2.inAmt"/>"	binding="inAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dailyTableKwu2/dailyTableKwu2.js?ver=20240711.01" charset="utf-8"></script>

<%-- 일일일계표3 인쇄 레이어 --%>
<c:import url="/WEB-INF/view/excclc/excclc/dailyTableKwu2/dailyTableReportKwu2.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>