<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: none;" ng-controller="bbqExcelFileUploadCtrl">

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
                is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="bbq.member.excel.guestNo"/>" binding="remark" width="150" align="left" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="bbq.member.excel.guestNm"/>" binding="membrNm" width="150" align="left" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="bbq.member.excel.guestTelNo"/>" binding="memberTelNo" width="150" align="left" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="bbq.member.excel.guestAddr"/>" binding="memberAddr" width="150" align="left" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="bbq.member.excel.lastSaleDate"/>" binding="lastSaleDate" width="150" align="left" data-type="String"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/bbqExcelFileUpload.js?ver=20230821.01" charset="utf-8"></script>
