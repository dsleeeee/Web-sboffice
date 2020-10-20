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

    <input type="file" class="form-control" id="excelUpFile"
        ng-model="excelUpFile"
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
                <c:if test="${prodNoEnvFg == 'MANUAL'}">
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodCd"/>" binding="prodCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodNm"/>" binding="prodNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodClassCd"/>" binding="prodClassCd" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodTypeFg"/>" binding="prodTypeFg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleProdYn"/>" binding="saleProdYn" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleUprc"/>" binding="saleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendrCd"/>" binding="vendrCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.splyUprc"/>" binding="splyUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poProdFg"/>" binding="poProdFg" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitFg"/>" binding="poUnitFg" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.poMinQty"/>" binding="poMinQty" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.vatFg"/>" binding="vatFg" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.stockProdYn"/>" binding="stockProdYn" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.costUprc"/>" binding="costUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.safeStockQty"/>" binding="safeStockQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.startStockQty"/>" binding="startStockQty" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodExcelUpload.remark"/>" binding="remark" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>
    var prodNoEnvFg = "${prodNoEnvFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodExcelUpload/prodExcelUploadAdd.js?ver=20201019.06" charset="utf-8"></script>