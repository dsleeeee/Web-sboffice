<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/month/monthProdClass/"/>

<div id="monthProdClassView" name="monthView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="monthProdClassCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"> <s:message code="month.prodClassSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('monthProdClassCtrl',1)" id="nxBtnSearch5">
                <s:message code="cmm.search" />
            </button>
        </div>
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
                <%-- 조회월 --%>
                <th>
                    <s:message code="cmm.search.month" />
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonthMonthProdClass" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonthMonthProdClass" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="monthProdClassStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </c:if>
                <c:if test="${orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 분류표시 --%>
                <th>
                    <s:message code="month.level" />
                </th>
                <td>
                    <div class="sb-select fl w120px">
                        <wj-combo-box
                            id="monthProdClassLevel"
                            ng-model="level"
                            items-source="_getComboData('srchMonthProdClassLevelCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index-changed="setMonthProdClass(s)">
                        </wj-combo-box>
                    </div>
                    <input type="hidden" id="hdMonthProdClassCd"/>
                    <input type="hidden" id="hdMonthProdClassNm"/>
                </td>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="month.prodCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('5');"/>
                </td>
            </tr>
            <tr>
                <%-- 상품명 --%>
                <th>
                    <s:message code="month.prodNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('5');"/>
                </td>
                <%-- 바코드 --%>
                <th>
                    <s:message code="month.barCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('5');"/>
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th>
                    <s:message code="month.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchMonthProdClassCd" ng-model="prodClassCdNm" ng-click="popUpMonthProdClass()" style="float: left;"
                           placeholder="<s:message code="month.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_monthProdClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelMonthProdClassCd" style="margin-left: 5px;" ng-click="delMonthProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjMonthProdClassList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totSaleQty"/>" binding="totSaleQty" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="monthProdClassCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthProdClass.js?ver=20241219.01" charset="utf-8"></script>