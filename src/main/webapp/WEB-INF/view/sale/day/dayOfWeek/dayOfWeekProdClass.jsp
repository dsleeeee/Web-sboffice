<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeek/dayOfWeekProdClass/"/>

<div id="dayOfWeekProdClassView" name="dayOfWeekView" class="subCon" style="display: none;" ng-controller="dayOfWeekProdClassCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="dayofweek.prodClassSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('dayOfWeekProdClassCtrl',1)" id="nxBtnSearch5">
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
                <%-- 조회일자 --%>
                <th>
                    <s:message code="cmm.search.date" />
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDateDayOfWeekProdClass" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDateDayOfWeekProdClass" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="dayOfWeekProdClassStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </c:if>
                <c:if test="${orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            <%--<tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >--%>
            <%--</tr>--%>
            <tr>
                <%-- 분류표시 --%>
                <th>
                    <s:message code="dayofweek.level" />
                </th>
                <td>
                    <div class="sb-select fl w120px">
                        <wj-combo-box
                            id="dayOfWeekProdClassLevel"
                            ng-model="level"
                            items-source="_getComboData('srchDayOfWeekProdClassLevelCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index-changed="setDayOfWeekProdClass(s)">
                        </wj-combo-box>
                    </div>
                    <input type="hidden" id="hdDayOfWeekProdClassCd"/>
                    <input type="hidden" id="hdDayOfWeekProdClassNm"/>
                </td>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="dayofweek.prodCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchDayOfWeekProdClassProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('5');"/>
                </td>
            </tr>
            <tr>
                <%-- 상품명 --%>
                <th>
                    <s:message code="dayofweek.prodNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchDayOfWeekProdClassProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('5');"/>
                </td>
                <%-- 바코드 --%>
                <th>
                    <s:message code="dayofweek.barCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchDayOfWeekProdClassBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('5');"/>
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th>
                    <s:message code="dayofweek.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchDayOfWeekProdClassCd" ng-model="prodClassCdNm" ng-click="popUpDayOfWeekProdClass()" style="float: left;"
                           placeholder="<s:message code="dayofweek.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_dayOfWeekProdClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelDayOfWeekProdClassCd" style="margin-left: 5px;" ng-click="delDayOfWeekProdClass()"><s:message code="cmm.selectCancel"/></button>
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
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                    id="wjDayOfWeekProdClassList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayofweek.yoil"/>" binding="yoil" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.totSaleQty"/>" binding="totSaleQty" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="dayOfWeekProdClassCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayOfWeek/dayOfWeekProdClass.js?ver=20220114.02" charset="utf-8"></script>