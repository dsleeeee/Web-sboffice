<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="prodNoEnvFg" value="${prodNoEnvFg}" />

<div class="subCon" style="display: none" ng-controller="dlvrExcelUploadAddCtrl">

    <input type="file" class="form-control" id="dlvrExcelUpFile"
           ng-model="dlvrExcelUpFile"
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

<%--                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidCallDt"/>"     binding="cidCallDt"     width="100" align="center"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidLineNo"/>"     binding="cidLineNo"     width="100" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidTelNo"/>"      binding="cidTelNo"      width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddr"/>"      binding="dlvrAddr"      width="200" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddrDtl"/>"   binding="dlvrAddrDtl"   width="85" align="left"></wj-flex-grid-column>
<%--                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.orderNo"/>"       binding="orderNo"       width="75" align="center"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrFg"/>"        binding="dlvrFg"        width="80" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrMemo"/>"      binding="dlvrMemo"      width="100" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrExcelUpload/dlvrExcelUploadAdd.js?ver=20220614.01" charset="utf-8"></script>