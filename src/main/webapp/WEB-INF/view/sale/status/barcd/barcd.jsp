<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="barcdView" class="subCon3" ng-controller="barcdCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="nxBtnSearch" ng-click="_broadcast('barcdMainCtrlSrch')">
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchBarcdStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchBarcdEndDate" class="w110px"></span>
                    <span class="chk ml10" style="display: none;">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                    </span>
                </div>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="barcd.srchOption" /></th>
            <td>
                <span class="chk ml10">
                    <input type="checkbox" id="chkChkProd" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                    <label for="chkChkProd">
                        <s:message code="periodIostock.prodClassDisplay" />
                    </label>
                </span>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="barcdSelectStoreCd" value="${sessionInfo.storeCd}"/>
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="barcdSelectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="barcdSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 바코드 --%>
            <th><s:message code="barcd.barcdCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="searchBarCd" ng-model="searchBarCd" ng-keydown="searchBarCdKeyEvt($event)" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="barcd.prodNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="searchProdNm" ng-model="searchProdNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <!-- contents start -->
    <%-- wj grid start --%>
    <%-- left --%>
    <div id="gridRepresent" ng-controller="barcdMainCtrl" class="w50 fl" style="width: 49%;">
        <%-- 할인구분별 --%>
        <div class="w100 mt10">
            <div class="oh sb-select mb10">
	            <%-- 페이지 스케일 --%>
	            <wj-combo-box
	                    class="w100px fl"
	                    id="barcdListScaleBox"
	                    ng-model="barcdListScale"
	                    items-source="_getComboData('barcdListScaleBox')"
	                    display-member-path="name"
	                    selected-value-path="value"
	                    initialized="initComboBox(s)"
	                    control="listScaleCombo"
	                    is-editable="true"
	                    text-changed="_checkValidation(s)">
	            </wj-combo-box>
	            <%-- 엑셀 다운로드 //TODO --%>
	            <button class="btn_skyblue fr" ng-click="excelDownloadBarcd()"><s:message code="cmm.excel.down" /></button>
            </div>
            <div class="wj-TblWrapBr1" id="wjWrapType1">
                <div class="wj-gridWrap col2-t2" style="height:380px;">
                    <wj-flex-grid
                            id="barcdGrid"
                            loaded-rows="loadedRows(s,e)"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="barcd.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassLNm"/>" binding="lv1Nm" width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassMNm"/>" binding="lv2Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassSNm"/>" binding="lv3Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="150" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleQty"/>" binding="totSaleQty" width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="barcdMainCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum3 mt20">
                <ul id="barcdMainCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
            <%-- 엑셀 리스트 --%>
            <div class="wj-TblWrapBr1" id="wjWrapType1" style="display:none;" ng-controller="barcdExcelCtrl">
                <div class="wj-gridWrap col2-t2">
                    <wj-flex-grid
                            id="barcdExcelGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="excelFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="barcd.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassLNm"/>" binding="lv1Nm" width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassMNm"/>" binding="lv2Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodClassSNm"/>" binding="lv3Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="150" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleQty"/>" binding="totSaleQty" width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="barcdMainCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--// 엑셀 리스트 --%>
        </div>
    </div>

    <%-- right --%>
    <div id="gridDetail" class="w50 fr" style="width: 49%;">
        <%-- 상품상세 --%>
        <div class="w100 mt10" ng-controller="barcdDtlCtrl">
            <div class="oh sb-select mb10">
                <%-- 페이지 스케일 --%>
                <wj-combo-box
                       class="w100px fl"
                       id="barcdDtlListScaleBox"
                       ng-model="barcdDtlListScale"
                       items-source="_getComboData('barcdDtlListScaleBox')"
                       display-member-path="name"
                       selected-value-path="value"
                       initialized="initComboBox(s)"
                       control="listScaleCombo"
                       is-editable="true"
                       text-changed="_checkValidation(s)">
                </wj-combo-box>
                <%-- 엑셀 다운로드 //TODO --%>
                <button class="btn_skyblue fr" ng-click="excelDownloadBarcdDtl()"><s:message code="cmm.excel.down" /></button>
            </div>
            <div class="wj-TblWrapBr1" id="wjWrapType1">
                <div class="wj-gridWrap col2-t2" style="height:380px;">
                    <wj-flex-grid
                            id="barcdDtlGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="barcd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleQty"/>" binding="totSaleQty" width="50" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="barcdDtlCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum3 mt20">
                <ul id="barcdDtlCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
              
            <div class="wj-TblWrapBr1" id="wjWrapType1" style="display:none;" ng-controller="barcdDtlExcelCtrl">
                <div class="wj-gridWrap col2-t2">
                    <wj-flex-grid
                            id="barcdDtlExcelGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="excelDtlFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="barcd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleQty"/>" binding="totSaleQty" width="50" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.saleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="barcd.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- //wj grid end --%>
    <!-- //contents end -->
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/barcd/barcd.js?ver=20250415.01" charset="utf-8"></script>