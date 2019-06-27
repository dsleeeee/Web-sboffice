<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/prodInfo/prodSaleDtl/"/>

<wj-popup id="wjProdSaleDtlLayer" control="wjProdSaleDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="prodSaleDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="prodSaleDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodSaldDtl.prodSaleDtl"/>
            <span id="spanDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 400px;">

            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.mainCategory"/>" binding="prodClassNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.prodNm"/>" binding="prodNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.saleQty"/>" binding="saleQty" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.totSaleAmt"/>" binding="saleAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.totDcAmt"/>" binding="dcAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.realSaleAmt"/>" binding="realSaleAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.taxTot"/>" binding="taxSaleAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.taxGaAmt"/>" binding="gaAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.vatAmt"/>" binding="vatAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.noTaxSaleAmt"/>" binding="noTaxSaleAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodSaleDtl.netSaleAmt"/>" binding="netSaleAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/prodInfo/prodSaleDtl.js?ver=20190619.02" charset="utf-8"></script>
