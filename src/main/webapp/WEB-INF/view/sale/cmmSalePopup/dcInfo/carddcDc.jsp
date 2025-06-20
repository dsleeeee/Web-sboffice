<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjCarddcDcLayer" control="wjCarddcDcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="partnerMrpizzaDcLayer" class="wj-dialog wj-dialog-columns" ng-controller="carddcDcCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="carddcDc.carddcDcInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="height: 360px;">
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="carddcDc.coupnNm"/>" binding="coupnNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="carddcDc.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="carddcDc.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="carddcDc.saleUprc"/>" binding="saleUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="carddcDc.saleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="carddcDc.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

                  </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dcInfo/carddcDc.js?ver=20250616.01" charset="utf-8"></script>