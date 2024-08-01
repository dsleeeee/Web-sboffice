<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<!-- contents start -->
<div class="subCon" ng-controller="mobileDayIoStockCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileDayIoStockMainCtrlSrch')">
            <s:message code="cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w30"/>
            <col class="w20"/>
            <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDayIostockStartDate" class="w120px"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="dayIostock.prodCd"/></th>
            <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd" onkeyup="fnNxBtnSearch();"/></td>
            <%-- 상품명 --%>
            <th><s:message code="dayIostock.prodNm"/></th>
            <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm" onkeyup="fnNxBtnSearch();"/></td>
        </tr>
        <tr>
            <%-- 바코드 --%>
            <th><s:message code="dayIostock.barcdCd"/></th>
            <td><input type="text" class="sb-input w100" id="srchBarcdCd" ng-model="srchBarcdCd" onkeyup="fnNxBtnSearch();"/></td>
            <%-- 단위구분 --%>
            <th><s:message code="dayIostock.unitFg"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="srchUnitFgDisplay"
                                ng-model="unitFgModel"
                                items-source="_getComboData('srchUnitFgDisplay')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품분류 --%>
            <th><s:message code="dayIostock.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                       placeholder="<s:message code="cmm.all" />" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="dayIostock.srchOption"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="srchOptionDisplay"
                                ng-model="srchOption"
                                items-source="_getComboData('srchOptionDisplay')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                    <span class="chk ml5">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                        <label for="chkDt">
                            <s:message code="dayIostock.prodClassDisplay" />
                        </label>
                    </span>
                </div>
            </td>
        </tr>
        <tr style="display: none">
            <%-- 거래처 --%>
            <th style="display: none"><s:message code="dayIostock.vendr"/></th>
            <td style="display: none">
                <%-- 거래처선택 모듈 멀티 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                --%>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
                    <jsp:param name="targetId" value="mobileDayIoStockSelectVendr"/>
                </jsp:include>
                <input type="hidden" id="mobileDayIoStockSelectVendrCd" value=""/>
            </td>
        </tr>
        <tr>
            <%-- 기초/마감재고표시 --%>
            <th><s:message code="dayIostock.stockOption"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w120px">
                        <wj-combo-box
                                id="srchStockOptionCombo"
                                ng-model="srchStockOption"
                                items-source="_getComboData('srchStockOptionCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
    <input type="hidden" id="storeNm" value="${sessionInfo.storeNm}"/>

    <div ng-controller="mobileDayIoStockMainCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="dayIostockMainlistScaleBox"
                    ng-model="listScale"
                    items-source="_getComboData('dayIostockMainlistScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    initialized="_initComboBox(s)"
                    control="listScaleCombo"
                    is-editable="true"
                    text-changed="_checkValidation(s)">
            </wj-combo-box>
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDayIostock()"><s:message code="cmm.excel.down" />
            </button>
        </div>

        <%-- 위즈모 테이블 gird 1 --%>
        <div class="w100 mt10" id="wjWrapType2">
            <div class="wj-gridWrap" style="height: 360px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="dayIostockMainGrid"
                        loaded-rows="loadedRows(s,e)"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayIostock.prodClassNm"/>" 			binding="prodClassNm" width="200" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="dayIostock.prodCd"/>"   			binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.prodNm"/>"   			binding="prodNm" width="160" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.poUnitQty"/>"			binding="poUnitQty" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.poUnitFg"/>" 			binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.barcdCd"/>"  			binding="barcdCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.basicStockAmt"/>"      binding="baseTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInQty"/>"  		binding="storeInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInAmt"/>"  		binding="storeInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutQty"/>" 		binding="storeOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutAmt"/>" 		binding="storeOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInQty"/>" 		binding="purchsInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInAmt"/>" 		binding="purchsInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutQty"/>"		binding="purchsOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutAmt"/>"		binding="purchsOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleQty"/>"		binding="storeSaleQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleAmt"/>"		binding="storeSaleTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInQty"/>" 	binding="moveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInAmt"/>" 	binding="moveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutQty"/>"	binding="moveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutAmt"/>"	binding="moveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accDisuse"/>"      		binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accAdj"/>"         		binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.accSetIn"/>"       		binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="dayIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayIostock.endingStockAmt"/>"     binding="closeTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="startDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>

                    <div class="gridMsg" id="mobileStoreDayIoStockMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="mobileDayIoStockMainCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum3 mt20">
                <%-- id --%>
                <ul id="mobileDayIoStockMainCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <%-- 엑셀 리스트 --%>
            <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="mobileDayIoStockMainExcelCtrl">
                <div class="wj-gridWrap">
                    <wj-flex-grid
                            id="dayIostockMainExcelGrid"
                            loaded-rows="loadedRows(s,e)"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="excelFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter"
                            frozen-columns="6">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dayIostock.prodClassNm"/>" 			binding="prodClassNm" width="200" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="dayIostock.prodCd"/>"   			binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.prodNm"/>"   			binding="prodNm" width="160" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.poUnitQty"/>"			binding="poUnitQty" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.poUnitFg"/>" 			binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.barcdCd"/>"  			binding="barcdCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.basicStockAmt"/>"      binding="baseTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInQty"/>"  		binding="storeInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInAmt"/>"  		binding="storeInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutQty"/>" 		binding="storeOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutAmt"/>" 		binding="storeOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInQty"/>" 		binding="purchsInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInAmt"/>" 		binding="purchsInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutQty"/>"		binding="purchsOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutAmt"/>"		binding="purchsOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleQty"/>"		binding="storeSaleQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleAmt"/>"		binding="storeSaleTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInQty"/>" 	binding="moveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInAmt"/>" 	binding="moveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutQty"/>"	binding="moveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutAmt"/>"	binding="moveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accDisuse"/>"      		binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accAdj"/>"         		binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.accSetIn"/>"       		binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="dayIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayIostock.endingStockAmt"/>"     binding="closeTot" width="60" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//엑셀 리스트--%>

        </div>
    </div>

</div>
<!-- //contents end -->

<script type="text/javascript" src="/resource/solbipos/js/mobile/stock/status/dayIoStock/mobileStoreDayIoStock.js?ver=20240723.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/com/popup/selectProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/mobile/stock/com/popup/prodCodeDtl/mobileStoreProdCodeDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/mobile/stock/com/popup/prodQtyDtl/mobileProdQtyDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>