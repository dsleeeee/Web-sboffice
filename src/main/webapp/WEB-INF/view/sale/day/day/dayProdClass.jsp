<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayProdclass/"/>

<div id="dayProdClassView" name="dayView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="dayProdClassCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="day.prodClass"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="nxBtnSearch5" ng-click="_broadcast('dayProdClassCtrl')">
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
                    <span class="txtIn"><input id="srchProdClassStartDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchProdClassEndDate" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dayProdClassSelectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="dayProdClassSelectStoreCd" value="${sessionInfo.storeCd}"/>
                <td></td>
                <td></td>
            </c:if>
        </tr>
        <tr>
            <th><s:message code="day.prodClass.level"/></th>
            <td>
                <div class="sb-select fl w120px">
                    <wj-combo-box
                            id="level"
                            ng-model="level"
                            items-source="_getComboData('srchLevelCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index-changed="setProdClass(s)">
                    </wj-combo-box>
                </div>
                <input type="hidden" id="hdProdClassCd"/>
                <input type="hidden" id="hdProdClassNm"/>
            </td>
            <th><s:message code="day.prodClass.prodCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdClassProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('5');"/>
            </td>
        </tr>
        <tr>
            <th><s:message code="day.prodClass.prodNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdClassProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('5');"/>
            </td>
            <th><s:message code="day.prodClass.barCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdClassBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('5');"/>
            </td>
        </tr>
        <tr>
            <th><s:message code="day.prodClass.srchClass"/></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="day.prodClass.srchClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    id="wjDayProdClassList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="day.prodClass.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.prodClass.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.prodClass.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.prodClass.totSaleQty"/>" binding="totSaleQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>

            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="dayProdClassCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>

        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayProdClass.js?ver=20220114.02" charset="utf-8"></script>