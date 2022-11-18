<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="storeOpenCloseMonthView" class="subCon" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeOpenClose.month"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeOpenCloseMonthCtrl')"><s:message code="cmm.search"/></button>
    </div>

    <table class="searchTbl mb10">
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
                    <span class="txtIn"><input id="monthStartDate" name="monthStartDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="monthEndDate" name="monthEndDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="w50 fl" ng-controller="storeOpenCloseMonthCtrl">
        <div class="wj-TblWrapBr mr10 pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.dayTime' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.downCondition"/></button>
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
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.date"/>" binding="yyyymmdd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openCnt"/>" binding="openCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeCnt"/>" binding="closeCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.noOpenCnt"/>" binding="noOpenCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.noCloseCnt"/>" binding="noCloseCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="storeOpenCloseDayCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div class="w50 fl" ng-controller="storeOpenCloseMonthDtlCtrl">
        <div class="wj-TblWrapBr pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.storeDtlList' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.downCondition"/></button>
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
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.date"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openTime"/>" binding="openTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeTime"/>" binding="closeTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.runTime"/>" binding="runTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

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
                            <jsp:param name="pickerTarget" value="storeOpenCloseMonthDtlCtrl"/>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeOpenClose/storeOpenCloseMonth.js?ver=20221111.01" charset="utf-8"></script>