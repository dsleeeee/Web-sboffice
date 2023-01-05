<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="stockPeriodCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('stockPeriodCtrl',1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
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
                    <span class="txtIn"><input id="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" class="w110px"></span>
                </div>
            </td>
            <%-- 일자표시옵션 --%>
            <th><s:message code="stockPeriod.dayOption"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchDayOptionCombo"
                            ng-model="dayOption"
                            items-source="_getComboData('dayOptionCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchDayOptionCombo"
                            selected-index="1">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th>
                <s:message code="stockPeriod.prodCd"/>
            </th>
            <td>
                <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="stockPeriod.prodNm"/>
            </th>
            <td>
                <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th>
                <s:message code="stockPeriod.srchClass" />
            </th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="stockPeriod.srchClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th>
                <s:message code="stockPeriod.barcdNm"/>
            </th>
            <td>
                <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 상품분류 항목표시 --%>
            <th>
                <s:message code="stockPeriod.prodClassDisplay" />
            </th>
            <td>
                <div class="sb-select">
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                        <label for="chkDt">
                            <s:message code="stockPeriod.prodClassDisplay" />
                        </label>
                    </span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장명 --%>
                <th>
                    <s:message code="stockPeriod.storeNm" />
                </th>
                <td>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="stockPeriodSelectStore"/>
                    </jsp:include>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <th></th>
                <td></td>
            </c:if>
        </tr>
        <tr style="display: none;">
            <%-- 단위구분 --%>
            <th>
                <s:message code="stockPeriod.unitFg"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                                id="srchUnitFg"
                                ng-model="unitFg"
                                items-source="_getComboData('srchUnitFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    id="wjGridList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="stockPeriod.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.lv1Nm"/>" binding="lv1Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.lv2Nm"/>" binding="lv2Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.lv3Nm"/>" binding="lv3Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.prodNm"/>" binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.barcdNm"/>" binding="barcdNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.accPrdtionInQty"/>" binding="accPrdtionInQty" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.accPrdtionOutQty"/>" binding="accPrdtionOutQty" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.accStoreSaleQty"/>" binding="accStoreSaleQty" width="70" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.accMallSaleQty"/>" binding="accMallSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stockPeriod.accMallRtnQty"/>" binding="accMallRtnQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="stockPeriodCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/product/stockPeriod/stockPeriod.js?ver=20221223.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>