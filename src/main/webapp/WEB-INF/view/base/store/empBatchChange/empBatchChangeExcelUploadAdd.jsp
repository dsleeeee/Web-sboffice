<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" style="display: none" ng-controller="storeExcelUploadAddCtrl">

    <input type="file" class="form-control" id="empExcelUpFile"
        ng-model="empExcelUpFile"
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
                <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.branchCd"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empBatchChange/empBatchChangeExcelUploadAdd.js?ver=20230307.01" charset="utf-8"></script>