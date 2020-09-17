<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS/"/>

<div style="display: none;" ng-controller="excelUploadMPSCtrl">

    <input type="file" class="form-control" id="excelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <input type="file" class="form-control" id="textUpFile"
           ng-model="textUpFile"
           onchange="angular.element(this).scope().textFileChanged()"
           accept=".txt"/>

    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.prodBarcdCd"/>" binding="prodBarcdCd" width="100" align="center" data-type="String" visible="{{prodBarcdCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.prodCd"/>" binding="prodCd" width="100" align="center" visible="{{prodCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.barcdCd"/>" binding="barcdCd" width="100" align="center" visible="{{barcdCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.unitQty"/>" binding="unitQty" width="70" align="right" data-type="Number" format="n0" visible="{{unitQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.etcQty"/>" binding="etcQty" width="70" align="right" data-type="Number" format="n0" visible="{{etcQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.qty"/>" binding="qty" width="70" align="right" data-type="Number" format="n0" visible="{{qtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.uprc"/>" binding="uprc" width="70" align="right" visible="{{uprcVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadMPS.remark"/>" binding="remark" width="70" align="left" data-type="String" visible="{{remarkVisibleFg}}"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.js?ver=20200910.01" charset="utf-8"></script>

<%-- 공통팝업 수불/재고 엑셀업로드 에러내역 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPSErrInfo.jsp">
</c:import>