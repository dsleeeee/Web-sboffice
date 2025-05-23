<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="posMonthView" class="subCon" style="display: none;" ng-controller="posMonthCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="pos.month"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnPosMonthSearch" ng-click="_broadcast('posMonthCtrlSrch')">
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
            <%-- 조회월 --%>
            <th><s:message code="cmm.search.month"/></th>
            <td>
                <div class="sb-select">
       					<span class="txtIn w110px">
       						<wj-input-date
                                    id="srchPosMonthStartDate"
                                    value="posMonthStartDate"
                                    ng-model="startDate"
                                    control="posMonthStartDateCombo"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)"
                                    selection-mode="Month"
                                    format="y">
       						</wj-input-date>
       					</span>
                    <span class="rg">~</span>
                    <span class="txtIn w110px">
       						<wj-input-date
                                    id="srchPosMonthEndDate"
                                    value="posMonthProdEndDate"
                                    ng-model="endDate"
                                    control="posMonthEndDateCombo"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)"
                                    selection-mode="Month"
                                    format="y">
       						</wj-input-date>
       					</span>
                    <span class="chk ml10" style="display: none;">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="posMonthSelectStoreCd" value=""/>
        <tr>
                <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 --%>
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
                    <jsp:param name="targetId" value="posMonthSelectStore"/>
                    <jsp:param name="subTargetId" value="posMonthSelectPos"/>
                    <jsp:param name="closeFunc" value="getPosNmList"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="posMonthSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <input type="hidden" id="posMonthSelectPosCd" value=""/>
        <input type="hidden" id="posMonthSelectPosName" value=""/>
        <input type="hidden" id="posMonthSelectHqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
        <tr>
            <%-- 포스선택 --%>
            <th><s:message code="pos.pos" /></th>
            <td>
                <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                    <jsp:param name="targetId" value="posMonthSelectPos"/>
                    <jsp:param name="targetStoreId" value="posMonthSelectStore"/>
                    <jsp:param name="closeFunc" value="getPosNmList"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </tr>
        </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="posMonthListScaleBox"
                ng-model="posMonthListScale"
                items-source="_getComboData('posMonthListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                initialized="initComboBox(s)"
                control="listScaleCombo"
                is-editable="true"
                text-changed="_checkValidation(s)">
        </wj-combo-box>

        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
        <div class="wj-gridWrap">
            <wj-flex-grid
                    id="posMonthGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    loaded-rows="loadedRows(s,e)"
                    is-read-only="true"
                    frozen-columns="7"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="pos.yearMonth"/>"			binding="yearMonth" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.saleStore"/>"			binding="saleStoreCnt" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleCnt"/>"		binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="posMonthCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <ul id="posMonthCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>


    <%--엑셀 리스트--%>
    <div class="w100 mt10" id="wjWrapType3" style="display: none;" ng-controller="posMonthExcelCtrl">
        <div class="wj-gridWrap">
            <wj-flex-grid
                    id="posMonthExcelGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    loaded-rows="loadedRows(s,e)"
                    is-read-only="true"
                    frozen-columns="7"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="pos.yearMonth"/>"         binding="yearMonth" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.saleStore"/>"         binding="saleStoreCnt" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"        binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"          binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"    binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleCnt"/>"        binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"        binding="totSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//엑셀 리스트--%>
    </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/posMonth.js?ver=20250415.01" charset="utf-8"></script>


