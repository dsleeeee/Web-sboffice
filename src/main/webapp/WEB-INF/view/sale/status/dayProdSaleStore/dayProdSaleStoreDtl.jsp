<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<wj-popup id="dayProdSaleStoreDtlLayer" control="dayProdSaleStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="dayProdSaleStoreDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dayProdSaleStore.saleDtl"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>


        <div class="mt10 oh sb-select mr20">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down"/>
            </button>
        </div>

        <div class="w100 mb10 pd20">
            <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeNm"/>" binding="storeNm" width="150" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleQty"/>" binding="saleQty" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleAmt"/>" binding="saleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totDcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.realSaleAmt"/>" binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <c:if test="${orgnFg eq 'HQ' and hqOfficeCd eq 'A0001'}">
                        <wj-flex-grid-column header="<s:message code="dayProdSaleStore.erpSendProdCd"/>" binding="erpSendProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProdSaleStore.erpSendYn"/>" binding="erpSendYn" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </c:if>
                </wj-flex-grid>
            </div>
            <%--//위즈모 테이블--%>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dayProdSaleStore/dayProdSaleStoreDtl.js?ver=20250120.01" charset="utf-8"></script>