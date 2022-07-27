<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: none;" ng-controller="excelUploadEmpCardInfoCtrl">

    <input type="file" class="form-control" id="excelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

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
            <wj-flex-grid-column header="<s:message code="empCardInfo.empCardNo"/>" binding="employeeCardNo" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.empNo"/>" binding="employeeNo" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.empNm"/>" binding="employeeNm" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.divNm"/>" binding="divNm" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.deptNm"/>" binding="deptNm" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.positionNm"/>" binding="positionNm" width="150" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="empCardInfo.useFg"/>" binding="useFg" width="90" align="center" data-type="String"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/excelUploadEmpCardInfo.js?ver=20220727.01" charset="utf-8"></script>
