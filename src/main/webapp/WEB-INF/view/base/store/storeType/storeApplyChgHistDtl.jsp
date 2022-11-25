<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjStoreApplyChgHistDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:880px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="storeApplyChgHistDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeType.storeApplyChgHist"/>
            <span id="storeApplyChgHistDtlTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
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
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="storeType.storeGroupCd"/>" binding="storeGroupCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.storeGroupNm"/>" binding="storeGroupNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.prodNm"/>" binding="prodNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.saleUprc"/>" binding="saleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.stinSaleUprc"/>" binding="stinSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.packSaleUprc"/>" binding="packSaleUprc" width="52" <c:if test="${storeTypeApplyEnvstVal == '0' or subPriceFg == '0'}">visible="false"</c:if> align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.procDt"/>" binding="chgProcDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.modId"/>" binding="modId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeType.applyProcFg"/>" binding="applyProcFg" data-map="applyProcFgDataMap" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeApplyChgHistDtl.js?ver=20221124.01" charset="utf-8"></script>