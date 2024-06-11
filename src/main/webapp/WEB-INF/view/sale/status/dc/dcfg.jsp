<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="baseUrl" value="/sale/satus/dc/" />

<div class="subCon" ng-controller="dcDcfgCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnDcDcfgSearch" ng-click="_broadcast('dcDcfgMainCtrlSrch')">
            <s:message code="cmm.search" />
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDcDcfgStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchDcDcfgEndDate" class="w110px"></span>
                    <span class="chk ml10" style="display: none;">
                        <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                    </span>
                </div>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="periodIostock.srchOption" /></th>
            <td>
		          	<span class="chk ml10">
						<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
		              	<label for="chkDt">
	                		<s:message code="periodIostock.prodClassDisplay" />
	              		</label>
	            	</span>
            </td>
        </tr>
        <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                     closeFunc - 팝업 닫기시 호출할 함수
                                     targetTypeFg - S:싱글, M:멀티
                       --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dcDcfgSelectStore" />
                        <jsp:param name="closeFunc" value="resetDcfg"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="dcDcfgSelectStoreCd" value="${sessionInfo.storeCd}" />
            </c:if>
        </tr>
        <tr>
            <%-- 할인구분선택 --%>
            <th><s:message code="cmm.dcfg.select" /></th>
            <td colspan="3">
                <%-- 할인구분 선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectDcfg.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="dcDcfgSelectDcfg" />
                    <jsp:param name="targetStoreId" value="dcDcfgSelectStore" />
                </jsp:include>
                <%--// 할인구분 선택 모듈 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>

    <div id="gridRepresent" ng-controller="dcDcfgMainCtrl" class="w50 fl" style="width: 49%;">
        <%-- 할인구분별 --%>
        <div class="w100 mt10">
            <div class="oh sb-select mb10">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                        class="w100px fl"
                        id="dcDcfgListScaleBox"
                        ng-model="dcDcfgListScale"
                        items-source="_getComboData('dcDcfgListScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        initialized="initComboBox(s)"
                        control="listScaleCombo"
                        is-editable="true"
                        text-changed="_checkValidation(s)">
                </wj-combo-box>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <input type="text" id="dcDcfgSelectStoreStoreNum" ng-model="storeNum" class="inputTxt">
                </c:if>
                <%-- 할인구분별 매출 엑셀다운로드 --%>
                <button class="btn_skyblue fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%--위즈모 테이블--%>
            <div class="w100 mt10" id="wjWrapType1">
                <div class="wj-gridWrap col2-t2">
                    <wj-flex-grid loaded-rows="loadedRows(s,e)" autoGenerateColumns="false" selection-mode="Row" items-source="data" control="flex" initialized="initGrid(s,e)" is-read-only="false" item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dcDcfg.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.dcCd"/>" binding="dcCd" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.dcNm"/>" binding="dcNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.totSaleQty"/>" binding="totSaleQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="dcDcfgMainCtrl" />
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//위즈모 테이블--%>
            <div class="pageNum3 mt20">
                <%-- id --%>
                <ul id="dcDcfgMainCtrlPager" data-size="10"></ul>
            </div>
        </div>
    </div>

    <div id="gridDetail" class="w50 fr" style="width: 49%;">
        <%-- 상품상세 --%>
        <div class="w100 mt10" ng-controller="dcDcfgDtlCtrl">
            <div class="oh sb-select mb10">
                <%-- 할인구분별 매출 상세 엑셀다운로드 --%>
                <button class="btn_skyblue fr"
                        ng-click="excelDownloadDcDcfgDtl()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%--위즈모 테이블--%>
            <div class="w100 mt10 dtl"  id="wjWrapType1">
                <div class="wj-gridWrap col2-t2">
                    <wj-flex-grid id="dcfgDtlGrid" autoGenerateColumns="false" selection-mode="Row" items-source="data" control="flex" initialized="initGrid(s,e)" is-read-only="false" item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dcDcfg.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfg.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.prodNm"/>" 			binding="prodNm" 	    width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.prodNm"/>" 			binding="dcdtlDcNm" 	width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.saleQty"/>" 		binding="saleQty" 		width="80"  align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.saleAmt"/>" 		binding="saleAmt" 		width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.dcAmt"/>" 			binding="dcAmt" 		width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.totDcAmt"/>" 		binding="totDcAmt" 		width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dcDcfgDtl.realSaleAmt"/>" 	binding="realSaleAmt" 	width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="dcDcfgDtlCtrl" />
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>

    <%-- 엑셀 리스트 --%>
    <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="dcDcfgExcelCtrl">
        <div class="wj-gridWrap">
            <wj-flex-grid
                    id="dcDcfgExcelGrid"
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
                <wj-flex-grid-column header="<s:message code="dcDcfg.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.dcCd"/>" binding="dcCd" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.dcNm"/>" binding="dcNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.totSaleQty"/>" binding="totSaleQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcDcfg.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dc/dcfg.js?ver=20240605.01" charset="utf-8"></script>

<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
    <c:param name="menuCd" value="${menuCd}" />
    <c:param name="menuNm" value="${menuNm}" />
</c:import>