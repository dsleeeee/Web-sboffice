<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="storeFnkeyCtrl" id="storeFnkeyView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="fnkeyCmNmcd.storeFnkey"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('storeFnkeyCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <c:if test="${orgnFg eq 'HQ'}"><%--본사 전용 탭 화면이지만 관리자 전용 탭 화면과 함께 있으므로, 본사권한일 때만 사용함으로써 쿼리 에러 방지 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="multilingualStoreFnkey"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <th><s:message code="fnkeyCmNmcd.fnkeyFg"/></th>
            <td>
                <div class="sb-select w100">
                    <wj-combo-box
                        id="fnkeyFg2"
                        ng-model="fnkeyFg"
                        items-source="_getComboData('fnkeyFg2')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchFnkeyFg2Combo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="fnkeyCmNmcd.fnkeyNo"/></th>
            <td><input type="text" id="srchFnkeyNo2" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/></td>
            <th><s:message code="fnkeyCmNmcd.fnkeyNm"/></th>
            <td><input type="text" id="srchFnkeyNm2" class="sb-input w100" onkeyup="fnNxBtnSearch('2');"/></td>
        </tr>
        </tbody>
    </table>

    <div class="updownSet oh mt10">
        <%-- 저장 --%>
        <button class="btn_skyblue" ng-click="saveRow()" >
            <s:message code="cmm.save" />
        </button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
        <%-- 양식다운로드 --%>
        <button class="btn_skyblue" ng-click="sampleDownload()">
            <s:message code="cmm.excel.sampleDown" />
        </button>
        <%-- 엑셀업로드 --%>
        <button class="btn_skyblue" ng-click="excelUpload()">
            <s:message code="cmm.excel.excelUpload" />
        </button>
    </div>

    <%-- 위즈모 테이블 --%>
    <div class="wj-TblWrap mt5">
        <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                control="flex"
                autoGenerateColumns="false"
                selection-mode="Row"
                initialized="initGrid(s,e)"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeNm"/>" binding="storeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>

<%-- 양식다운로드 관련 --%>
<div style="display: none;" ng-controller="storeFnkeyExcelDownCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeNm"/>" binding="storeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<%-- 엑셀 업로드 관련 --%>
<div style="display: none;" ng-controller="storeFnkeyExcelUploadCtrl">
    <input type="file" class="form-control" id="storeFnkeyExcelUpFile"
            ng-model="excelUpFile"
            onchange="angular.element(this).scope().excelFileChanged()"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.storeNm"/>" binding="storeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<style>
    /* 중문, 일문 엑셀업로드 font */
    .chinese-excel-form {
        font-family: "Microsoft YaHei";
    }

    .japanese-excel-form {
        font-family: "Meiryo";
    }
</style>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/fnkeyCmNmcd/storeFnkey.js?ver=20231222.01" charset="utf-8"></script>