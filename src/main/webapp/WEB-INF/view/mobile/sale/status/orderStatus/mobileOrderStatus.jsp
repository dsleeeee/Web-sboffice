<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileOrderStatusCtrl">

    <div class="searchBar">
        <%-- 주문현황 --%>
        <a href="#" class="fl"><s:message code="mobile.orderStatus"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileOrderStatusCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="mobile.cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <c:if test="${multiStoreFg ne 0}">
            <tr>
                <%-- (다중)매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                    <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileOrderStatusStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileOrderStatusStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 주문현황 --%>
    <div class="gridBar mt10" id="mobileOrderStatus" onclick="girdFldUnfld('mobileOrderStatus')">
        <a href="#" class="open"><s:message code="mobile.orderStatus"/></a>
    </div>
    <div class="w100" id="mobileOrderStatusGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileOrderStatus"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.saleDate"/>"       binding="saleDate" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.posNo"/>"          binding="posNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderNo"/>"        binding="orderNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderFg"/>"        binding="orderFg" width="70" align="center" is-read-only="true" data-map="orderFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.dlvrOrderFg"/>"    binding="dlvrOrderFg" width="70" align="center" is-read-only="true" data-map="dlvrOrderFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderStartDt"/>"   binding="orderStartDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderEndDt"/>"     binding="orderEndDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.totSaleAmt"/>"     binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.totDcAmt"/>"       binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.totTipAmt"/>"      binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.totEtcAmt"/>"      binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.realSaleAmt"/>"    binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.taxSaleAmt"/>"     binding="taxSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.vatAmt"/>"         binding="vatAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.noTaxSaleAmt"/>"   binding="noTaxSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.pureSaleAmt"/>"    binding="pureSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.expectPayAmt"/>"   binding="expectPayAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.recvPayAmt"/>"     binding="recvPayAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.rtnPayAmt"/>"      binding="rtnPayAmt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.dutchPayCnt"/>"    binding="dutchPayCnt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.totGuestCnt"/>"    binding="totGuestCnt" width="70" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.kitchenMemo"/>"    binding="kitchenMemo" width="70" align="left" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.tblCd"/>"          binding="tblCd" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.moveOrderNo"/>"    binding="moveOrderNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.empNo"/>"          binding="empNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.pagerNo"/>"        binding="pagerNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.dlvrYn"/>"         binding="dlvrYn" width="70" align="center" is-read-only="true" data-map="dlvrYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.membrYn"/>"       binding="membrYn" width="70" align="center" is-read-only="true" data-map="memberYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.resveYn"/>"        binding="resveYn" width="70" align="center" is-read-only="true" data-map="resveYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.refundYn"/>"       binding="refundYn" width="70" align="center" is-read-only="true"data-map="refundYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.sendYn"/>"         binding="sendYn" width="70" align="center" is-read-only="true" data-map="sendYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderStatus.sendDt"/>"         binding="sendDt" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>

                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileOrderStatusMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //주문현황 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/orderStatus/mobileOrderStatus.js?ver=20211005.01" charset="utf-8"></script>

<%-- 주문현황 상세 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/status/orderStatus/mobileOrderStatusDtl.jsp">
</c:import>