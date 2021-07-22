<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayProdSaleDtl/"/>

<wj-popup control="wjDayProdSaleDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1100px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="dayProdSaleDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="day.dayProdSaleDtl.dayProdSaleDtl"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->

                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.billNo"/>" binding="billNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.regDt"/>" binding="regDt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.saleFg"/>" binding="saleFg" data-map="saleFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.totSaleAmt"/>" binding="totSaleAmt" width="80" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.gaAmt"/>" binding="gaAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.vatAmt"/>" binding="vatAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <%-- 결제수단 컬럼 생성--%>
                        <c:forEach var="payCol" items="${payColList}">
                            <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        </c:forEach>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.orderDt"/>" binding="orderDt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.totGuestCnt"/>" binding="totGuestCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.guestUprc"/>" binding="guestUprc" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayProdSaleDtl.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 판매구분 --%>
    var saleFgData = ${ccu.getCommCodeExcpAll("047")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayProdSaleDtl.js?ver=20210722.01" charset="utf-8"></script>

<%-- 매출 상세내역 (매출) 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/saleInfo/saleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>