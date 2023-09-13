<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" style="display: none" ng-controller="selectUploadStoreExcelUploadCtrl">

    <input type="text"
           id="selectUploadStoreExcelUploadTargetId"
           class="sb-input fl mr5"
           style="cursor:pointer; width:150px;"
           readonly/>

    <input type="file" class="form-control" id="selectUploadStoreExcelUploadFile"
           ng-model="selectUploadStoreExcelUploadFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:50px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="selectUploadStoreExcelUploadFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="selectStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="selectStore.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/common/popup/selectUploadStoreExcelUpload.js?ver=20230822.01" charset="utf-8"></script>