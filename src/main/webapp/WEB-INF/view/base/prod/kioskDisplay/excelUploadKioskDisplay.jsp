<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />=

<div class="subCon" style="display: none" ng-controller="excelUploadKioskDisplayCtrl">

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

                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskDisplay.kioskDisplayYn"/>" binding="kioskDisplayYn" width="*" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskDisplay/excelUploadKioskDisplay.js?ver=20230814.01" charset="utf-8"></script>