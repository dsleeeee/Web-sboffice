<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="dailyTableKwuCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('dailyTableKwuCtrl',1)">
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
                        <%-- 매장코드 --%>
                    <th>
                        <s:message code="cmm.store"/>
                    </th>
                    <td>
                            <%-- 매장선택 모듈 싱글 선택 사용 시 include
                                    param 정의 : targetId	- angular 콘트롤러 및 input 생성시 사용할 타켓id
                                                displayNm 	- 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                                modiFg 		- 수정여부(변수 없을 경우 기본값으로 수정가능)
                                                closeFunc 	- 팝업 닫기시 호출할 함수
                            --%>
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
                            <jsp:param name="targetId" value="dailyTableKwuSelectStore"/>
                        </jsp:include>
                    </td>
                </tr>
            </c:if>
            <c:if test="${orgnFg == 'STORE'}">
                <input type="hidden" id="dailyTableKwuSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <span class="fr">
                <button class="btn_skyblue" ng-click="print('1')">첫째 장 <s:message code="cmm.print"	/></button>
                <button class="btn_skyblue" ng-click="print('2')">둘째 장 <s:message code="cmm.print"	/></button>
             <button class="btn_skyblue" ng-click="excelDownload()" ><s:message code="cmm.excel.down"	/></button>
            </span>
        </div>
    </div>

    <%-- 매출종합 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwuCtrl_sl">
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
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.saleAmt"/>" binding="saleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.dcAmt"/>" binding="dcAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.netSaleAmt"/>" binding="netSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.membrSaleAmt"/>" binding="membrSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthSaleAmt"/>" binding="monthSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthDcAmt"/>" binding="monthDcAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthNetSaleAmt"/>" binding="monthNetSaleAmt"	width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthMembrSaleAmt"/>" binding="monthMembrSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.guestCnt"/>" binding="guestCnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.guestUprc"/>" binding="guestUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthGuestCnt"/>" binding="monthGuestCnt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthGuestUprc"/>" binding="monthGuestUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 분류 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwuCtrl_prodClass">
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
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.prodClassCd"/>" binding="prodClassCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.prodClassNm"/>" binding="prodClassNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.cashAmt"/>" binding="cashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.cardAmt"/>" binding="cardAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.etcSaleAmt"/>" binding="etcSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthSaleQty"/>" binding="monthSaleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthCashAmt"/>" binding="monthCashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthCardAmt"/>" binding="monthCardAmt" width="150" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthEtcSaleAmt"/>" inding="monthEtcSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthRealSaleAmt"/>" binding="monthRealSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 결제수단 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwuCtrl_pay">
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
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.cashAmt"/>" binding="cashAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.cardAmt"/>" binding="cardAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.postpaidAmt"/>" binding="postpaidAmt"	width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.gitfAmt"/>" binding="gitfAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.bMonthUnpaidAmt"/>" binding="bMonthUnpaidAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.monthUnpaidAmt"/>" binding="monthUnpaidAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.commissionAmt"/>" binding="commissionAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.interestAmt"/>" binding="interestAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 반품출납 --%>
    <div class="w100 mt10 mb20" ng-controller="dailyTableKwuCtrl_rtn">
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
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.rtnSaleCnt"/>" binding="rtnSaleCnt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.rtnSaleAmt"/>" binding="rtnSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.totAmt"/>" binding="totAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.outAmt"/>" binding="outAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dailyTableKwu.inAmt"/>"	binding="inAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dailyTableKwu/dailyTableKwu.js?ver=20221027.03" charset="utf-8"></script>

<%-- 일일일계표2 인쇄 레이어 --%>
<c:import url="/WEB-INF/view/excclc/excclc/dailyTableKwu/dailyTableReportKwu.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>