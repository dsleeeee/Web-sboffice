<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="prodNoEnvFg" value="${prodNoEnvFg}" />

<div class="subCon" style="display: none" ng-controller="prodExcelUploadAddCtrl">

    <input type="file" class="form-control" id="prodExcelUpFile"
        ng-model="prodExcelUpFile"
        onchange="angular.element(this).scope().excelFileChanged()"
        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:50px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.brandNm"/>" binding="hqBrandCd" width="85" align="center" <c:if test="${brandUseFg == '0'}">visible="false"</c:if> ></wj-flex-grid-column>
                <c:if test="${prodNoEnvFg == 'MANUAL'}">
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodCd"/>" binding="prodCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodNm"/>" binding="prodNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodClassCd"/>" binding="prodClassCd" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodTypeFg"/>" binding="prodTypeFg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleProdYn"/>" binding="saleProdYn" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleUprc"/>" binding="saleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${subPriceFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.stinSaleUprc"/>" binding="stinSaleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.packSaleUprc"/>" binding="packSaleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendrCd"/>" binding="vendrCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.splyUprc"/>" binding="splyUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poProdFg"/>" binding="poProdFg" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitFg"/>" binding="poUnitFg" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitQty"/>" binding="poUnitQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poMinQty"/>" binding="poMinQty" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.vatFg"/>" binding="vatFg" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.stockProdYn"/>" binding="stockProdYn" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.costUprc"/>" binding="costUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.safeStockQty"/>" binding="safeStockQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.startStockQty"/>" binding="startStockQty" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="85" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.depositCupFg"/>" binding="depositCupFg" data-map="depositCupFgDataMap" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.remark"/>" binding="remark" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>
    var prodNoEnvFg = "${prodNoEnvFg}";

    <%-- 내점/배달/포장 가격관리 사용여부 --%>
    var subPriceFg = "${subPriceFg}";
    <%-- (상품관리)브랜드사용여부 --%>
    var brandUseFg = "${brandUseFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodExcelUpload/prodExcelUploadAdd.js?ver=20210421.02" charset="utf-8"></script>