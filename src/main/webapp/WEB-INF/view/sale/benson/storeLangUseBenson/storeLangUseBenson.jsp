<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" ng-controller="storeLangUseBensonCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeLangUseBenson.storeLangUseBenson" /></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeLangUseBensonCtrl')">
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDayStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchDayEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 옵션 --%>
            <th><s:message code="storeDayChannel.option"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="option"
                            ng-model="option"
                            items-source="_getComboData('option')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="optionCombo"
                            selected-index-changed="changeOption(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장브랜드 --%>
                <th><s:message code="cmm.moms.storeHqBrand"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchStoreHqBrandCdCombo"
                                ng-model="storeHqBrandCd"
                                items-source="_getComboData('storeHqBrandCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 매장선택 --%>
                <th class="multiLangStore" style="display: none;"><s:message code="cmm.store.select"/></th>
                <td class="multiLangStore" style="display: none;">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="multiLangStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="multiLangStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 420px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.startDate"/>" binding="startDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.endDate"/>" binding="endDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.totCnt"/>" binding="totCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="koreanCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="koreanPer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="englishCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="englishPer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="chineseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="chinesePer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="japaneseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="japanesePer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="display: none;" ng-controller="storeLangUseBensonExcelCtrl">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.startDate"/>" binding="startDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.endDate"/>" binding="endDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.totCnt"/>" binding="totCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="koreanCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="koreanPer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="englishCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="englishPer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="chineseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="chinesePer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.cnt"/>" binding="japaneseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLangUseBenson.percent"/>" binding="japanesePer" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
<%--    <div class="pageNum mt20">--%>
<%--        <ul id="storeLangUseBensonCtrlPager" data-size="10">--%>
<%--        </ul>--%>
<%--    </div>--%>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storeLangUseBenson/storeLangUseBenson.js?ver=20260909.01" charset="utf-8"></script>
