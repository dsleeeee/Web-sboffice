<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="wjOrderStatusDtlLayer" control="wjOrderStatusDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:750px;" fade-in="false" fade-out="false">

    <div ng-controller="orderStatusDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="orderStatusDtl.orderStatusDtl" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                         <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.orderSeq"/>"          binding="orderSeq" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.orderDtlNo"/>"        binding="orderDtlNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.orderDtlFg"/>"        binding="orderDtlFg" data-map="orderFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.dlvrPackFg"/>"        binding="dlvrPackFg" data-map="dlvrOrderFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.prodCd"/>"            binding="prodCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.prodNm"/>"            binding="prodNm" width="70" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.saleUprc"/>"          binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.saleQty"/>"           binding="saleQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.saleAmt"/>"           binding="saleAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.dcAmt"/>"             binding="dcAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.tipAmt"/>"            binding="tipAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.etcAmt"/>"            binding="etcAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.realSaleAmt"/>"       binding="realSaleAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.vatAmt"/>"            binding="vatAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.membrSavePoint"/>"    binding="membrSavePoint" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="orderStatusDtl.membrUsePoint"/>"     binding="membrUsePoint" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                   </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/orderStatus/orderStatusDtl.js?ver=20211001.01" charset="utf-8"></script>

