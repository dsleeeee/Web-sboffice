<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="stockCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"> <s:message code="stock.stock"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('stockCtrl',1)" id="nxBtnSearch">
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
            <%-- 상품코드 --%>
            <th>
                <s:message code="stock.prodCd"/>
            </th>
            <td>
                <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="stock.prodNm"/>
            </th>
            <td>
                <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th>
                <s:message code="stock.srchClass" />
            </th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="stock.srchClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th>
                <s:message code="stock.barcdNm"/>
            </th>
            <td>
                <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 상품분류 항목표시 --%>
            <th>
                <s:message code="stock.prodClassDisplay" />
            </th>
            <td>
                <div class="sb-select">
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                        <label for="chkDt">
                            <s:message code="stock.prodClassDisplay" />
                        </label>
                    </span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장명 --%>
                <th>
                    <s:message code="stock.storeNm" />
                </th>
                <td>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="stockSelectStore"/>
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
                <s:message code="stock.unitFg"/>
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
                    frozen-columns="7">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="stock.lv1Nm"/>" binding="lv1Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.lv2Nm"/>" binding="lv2Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.lv3Nm"/>" binding="lv3Nm" width="120" align="center"	 is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.prodNm"/>" binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="stock.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="stock.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="stock.barcdNm"/>" binding="barcdNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.accPrdtionInQty"/>" binding="accPrdtionInQty" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.accPrdtionOutQty"/>" binding="accPrdtionOutQty" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.accStoreSaleQty"/>" binding="accStoreSaleQty" width="70" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.accMallSaleQty"/>" binding="accMallSaleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.accMallRtnQty"/>" binding="accMallRtnQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.firstSaleDate"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="stock.lastSaleDate"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="stockCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/product/stock/stock.js?ver=20220907.03" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>