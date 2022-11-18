<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="storeOpenCloseDayView" class="subCon">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeOpenClose.day"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeOpenCloseDayCtrl')"><s:message code="cmm.search"/></button>
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
                    <span class="txtIn"><input id="dayStartDate" name="dayStartDate" class="w110px" /></span>
                </div>
            </td>
            <%-- 옵션 --%>
            <th><s:message code="storeOpenClose.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="w40 fl" ng-controller="storeOpenCloseDayCtrl">
        <div class="wj-TblWrapBr mr10 pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.dayTime' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.downCondition"/></button>
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
                            item-formatter="_itemFormatter"
                            allowMerging="Cells">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="saleDate" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="min" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="max" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.time"/>" binding="time" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openCnt"/>" binding="openCnt" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeCnt"/>" binding="closeCnt" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.open"/>" binding="open" width="50" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.close"/>" binding="close" width="50" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.noOpen"/>" binding="none" width="60" align="center"></wj-flex-grid-column>

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

    <div class="w60 fl" ng-controller="storeOpenCloseDayDtlCtrl">
        <div class="wj-TblWrapBr pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.storeDtlList' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.downCondition"/></button>
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
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openTime"/>" binding="openTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeTime"/>" binding="closeTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.runTime"/>" binding="runTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

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
                            <jsp:param name="pickerTarget" value="storeOpenCloseDayCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
   #green {background-color:transparent; border:1px solid #1ab394; color:#1ab394; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #green:hover {background-color:#1ab394; color:#FFFFFF;}
   #yellow {background-color:transparent; border:1px solid #f8ac59; color:#f8ac59; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #yellow:hover {background-color:#f8ac59; color:#FFFFFF;}
   #red {background-color:transparent; border:1px solid #ed5565; color:#ed5565; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #red:hover {background-color:#ed5565; color:#FFFFFF;}
</style>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeOpenClose/storeOpenCloseDay.js?ver=20221111.01" charset="utf-8"></script>
