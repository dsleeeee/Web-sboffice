<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="storeOpenCloseBensonMonthView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="storeOpenCloseBensonMonthTimeCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeOpenCloseBenson.month"/></a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeOpenCloseBensonMonthTimeCtrl')"><s:message code="cmm.search"/></button>
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
                <%-- 조회월 --%>
                <th><s:message code="cmm.search.month"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="monthStartDate" name="monthStartDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="monthEndDate" name="monthEndDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 매장브랜드 --%>
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCdCombo2"
                                    ng-model="storeHqBrandCd2"
                                    items-source="_getComboData('storeHqBrandCdCombo2')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="storeOpenCloseBensonMonthStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="storeOpenCloseBensonMonthStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="w50 mt10 fl" ng-controller="storeOpenCloseBensonMonthCtrl">
        <div class="wj-TblWrapBr mr10 pd10">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenCloseBenson.dayTime' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.downCurrent"/></button>
            </div>
            <div class="wj-TblWrapBr">
                <%--위즈모 테이블--%>
                <div style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="startMonth" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="endMonth" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.date"/>" binding="yyyymmdd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.openCnt"/>" binding="openCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.closeCnt"/>" binding="closeCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.noOpenCnt"/>" binding="noOpenCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.noCloseCnt"/>" binding="noCloseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="storeOpenCloseBensonDayCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div class="w50 mt10 fl" ng-controller="storeOpenCloseBensonMonthDtlCtrl">
        <div class="wj-TblWrapBr pd10">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenCloseBenson.storeDtlList' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.downCurrent"/></button>
            </div>
            <div class="wj-TblWrapBr">
                <%--위즈모 테이블--%>
                <div style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="startMonth" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="endMonth" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.date"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.brand"/>" binding="brand" width="100" align="left" is-read-only="true" data-map="brandDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.openTime"/>" binding="openTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.closeTime"/>" binding="closeTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.runTime"/>" binding="runTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenCloseBenson.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="posExcclc.closeFg"/>"        binding="closeFgNm"          width="80"  align="center" is-read-only="true" visible="false" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.regDate"/>"        binding="regDt"          	width="200" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.totSaleAmt"/>"     binding="totSaleAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.totDcAmt"/>"       binding="totDcAmt"           width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.realSaleAmt"/>"    binding="realSaleAmt"        width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashSaleAmt"/>"    binding="cashExactAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashBillSaleAmt"/>" binding="cashBillSaleAmt"   width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.posFundAmt"/>"     binding="fundAmt"            width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.inAmt"/>"          binding="accntInAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.outAmt"/>"         binding="accntOutAmt"        width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashTicketAmt"/>"  binding="cashTicketAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashLostAmt"/>"    binding="lostAmt"      	    width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="storeOpenCloseBensonMonthDtlCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storeOpenCloseBenson/storeOpenCloseBensonMonth.js?ver=20260909.01" charset="utf-8"></script>
