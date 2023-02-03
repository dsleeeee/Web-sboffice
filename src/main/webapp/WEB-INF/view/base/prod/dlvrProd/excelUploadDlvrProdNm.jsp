<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: none;" ng-controller="excelUploadDlvrProdNmCtrl">

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
            <wj-flex-grid-column header="<s:message code="dlvrProd.prodCd"/>" binding="prodCd" width="120" align="center" data-type="String" ></wj-flex-grid-column>

            <%-- 배달앱 구분코드 컬럼 생성--%>
            <c:forEach var="dlvrCol" items="${dlvrColList}">
                <wj-flex-grid-column header="${dlvrCol.dlvrNm}[${dlvrCol.dlvrCd}]" binding="dlvrProdNm${dlvrCol.dlvrCd}" width="120" data-type="String"></wj-flex-grid-column>
            </c:forEach>

        </wj-flex-grid>
        <!-- 엑셀 다운로드 시 사용 -->
        <input type="hidden" id="hdDlvrCol" value="${dlvrCol}"/>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/dlvrProd/excelUploadDlvrProdNm.js?ver=20210114.04" charset="utf-8"></script>
