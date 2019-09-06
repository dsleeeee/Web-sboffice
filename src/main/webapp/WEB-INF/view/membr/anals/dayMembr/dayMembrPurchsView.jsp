<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="dayMembrPurchsViewLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:570px;" fade-in="false" fade-out="false">
    <div ng-controller="dayMembrPurchsCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dayMembrPurchs.info" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-TblWrap mr10">
                    <h3 class="fl bk lh20 s14"><s:message code="dayMembrPurchs.purchsList"/></h3>
                    <span id="dayMembrInfoTitle" class="fl bk lh20 s14"></span>
                </div>
                <div class="wj-gridWrap" style="height:430px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleDate"/>" binding="saleDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleQty"/>" binding="saleQty" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleAmt"/>" binding="saleAmt" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.membrSavePoint"/>" binding="membrSavePoint" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayMembrPurchs.membrUsePoint"/>" binding="membrUsePoint" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/dayMembr/dayMembrPurchs.js?ver=2019052901.02" charset="utf-8"></script>