<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="agencyPurchsLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="agencyPurchsCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblAgencyCdPurchs"></label>
            <label id="lblAgencyNmPurchs"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <label id="lblStartDatePurchs"></label>
            <label id="lblEndDatePurchs"></label>
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
                        <wj-flex-grid-column header="<s:message code="instl.storeCd"/>" binding="storeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.maxSaleDate"/>" binding="maxSaleDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.posCnt"/>" binding="posCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/agencyPurchs.js?ver=2019052801.36" charset="utf-8"></script>