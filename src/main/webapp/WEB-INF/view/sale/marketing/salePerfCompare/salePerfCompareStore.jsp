<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="salePerfCompareStoreView" ng-controller="salePerfCompareStoreCtrl" class="subCon" style="padding: 10px 20px 40px;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="saleCancelStatus.selectStore"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearchStore" ng-click="_broadcast('salePerfCompareStoreCtrl')">
            <s:message code="cmm.search"/>
        </button>
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
            <%-- 구분 --%>
            <th><s:message code="salePerfCompare.dlvrOrderFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchDateFgStoreCombo"
                            ng-model="srchDateFgStoreCombo"
                            control="srchDateFgStore"
                            items-source="_getComboData('srchDateFgStoreCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            selected-index-changed="selectedIndexChanged(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <%--        <c:if test="${sessionInfo.orgnFg == 'HQ'}">--%>
        <tr>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="salePerfCompareStoreSelctStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <%--        </c:if>--%>
        <%--        <c:if test="${sessionInfo.orgnFg == 'STORE'}">--%>
        <%--            <input type="hidden" id="salePerfCompareStoreSelctStoreCd" value="${sessionInfo.storeCd}"/>--%>
        <%--        </c:if>--%>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDateStore" ng-model="startDate" class="w110px"></span>
                    <span class="rg" id="srchRgStore">~</span>
                    <span class="txtIn" id="srchEndStore"><input id="srchEndDateStore" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
            <%-- 대비일자 --%>
            <th><s:message code="salePerfCompare.compDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="compStartDateStore" ng-model="compStartDate" class="w110px"></span>
                    <span class="rg" id="compRgStore">~</span>
                    <span class="txtIn" id="compEndStore"><input id="compEndDateStore" ng-model="compEndDate" class="w110px"></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <!-- contents start -->
    <div class="wj-gridWrap2 mt10">
        <%-- wj grid start --%>
        <div class="oh sb-select dkbr mb10 mr10" >
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="salePerfCompareStoreMainGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="saleCancelStatus.storeCd"/>"  binding="storeCd"           width="80"  align="center"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.storeNm"/>"   binding="storeNm"           width="100" align="center"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commCnt"/>"   binding="compSaleCntComm"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commAmt"/>"   binding="compSaleAmtComm"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrCnt"/>"   binding="compSaleCntDlvr"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrAmt"/>"   binding="compSaleAmtDlvr"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packCnt"/>"   binding="compSaleCntPack"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packAmt"/>"   binding="compSaleAmtPack"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commCnt"/>"   binding="srchSaleCntComm"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commAmt"/>"   binding="srchSaleAmtComm"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrCnt"/>"   binding="srchSaleCntDlvr"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrAmt"/>"   binding="srchSaleAmtDlvr"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packCnt"/>"   binding="srchSaleCntPack"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packAmt"/>"   binding="srchSaleAmtPack"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commCnt"/>"   binding="grSaleCntComm"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.commAmt"/>"   binding="grSaleAmtComm"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrCnt"/>"   binding="grSaleCntDlvr"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrAmt"/>"   binding="grSaleAmtDlvr"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packCnt"/>"   binding="grSaleCntPack"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePerfCompare.packAmt"/>"   binding="grSaleAmtPack"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="salePerfCompareStoreCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- //wj grid end --%>
    </div>
    <!-- //contents end -->
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/marketing/salePerfCompare/salePerfCompareStore.js?ver=20250818.01" charset="utf-8"></script>