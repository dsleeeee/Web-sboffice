<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="dlvrFgSaleDtlPopLayer" control="dlvrFgSaleDtlPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="dlvrFgSaleDtlPopCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dlvrFg.prod.saleDtl"/>
            <span id="spanDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="height: 400px;">
            <div class="fl">
                <label id="lblProdInfo"></label>
            </div>
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 320px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dlvrFg.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.storeNm"/>" binding="storeNm" width="150" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.barCd"/>" binding="barCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.saleDate"/>" 	binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.dlvrFg"/>" binding="dlvrPackFg" data-map="packFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.dlvrInFg"/>" binding="dlvrInFg" data-map="dlvrInFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.cookMemo"/>" binding="cookMemo" width="150" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.saleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.totTipAmt"/>" binding="tipAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.totEtcAmt"/>" binding="etcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrFg.totPayAmt"/>" binding="totPayAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <%-- 결제수단 컬럼 생성--%>
                        <c:forEach var="payCol" items="${payColList}">
                            <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        </c:forEach>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/dlvr/dlvrFg/dlvrFgPop/dlvrFgSaleDtlPop.js?ver=20210729.01" charset="utf-8"></script>