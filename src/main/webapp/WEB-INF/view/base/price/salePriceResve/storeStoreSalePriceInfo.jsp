<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeStoreSalePriceInfoLayer" control="storeStoreSalePriceInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <div ng-controller="storeStoreSalePriceInfoCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="salePriceResve.salePriceInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <label id="lblProd2"></label>&nbsp;/&nbsp;
            <label id="lblStore2"></label>
            <input type="hidden" id="hdProdCd2" />
            <input type="hidden" id="hdStoreCd2" />

            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
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
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="salePriceResve.startDate"/>" binding="startDate" width="100" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.endDate"/>" binding="endDate" width="100" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.salePrice"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>

                        <c:if test="${subPriceFg == '1'}">
                            <wj-flex-grid-column header="<s:message code="salePriceResve.stinSaleUprc"/>" binding="stinSaleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.packSaleUprc"/>" binding="packSaleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeStoreSalePriceInfo.js?ver=20220427.01" charset="utf-8"></script>
