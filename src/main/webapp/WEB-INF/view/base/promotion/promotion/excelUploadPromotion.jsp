<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: none;" ng-controller="excelUploadPromotionCtrl">

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
            <wj-flex-grid-column header="<s:message code="promotion.storeCd"/>" binding="storeCd" width="100" align="center" data-type="String"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promotion.storeNm"/>" binding="storeNm" width="300" align="left" data-type="String"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/excelUploadPromotion.js?ver=20221102.02" charset="utf-8"></script>
