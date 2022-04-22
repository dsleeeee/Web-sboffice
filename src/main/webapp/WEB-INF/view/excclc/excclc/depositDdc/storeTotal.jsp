<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="storeTotalView" name="storeTotalView" class="subCon" style="display: none;">
    <div ng-controller="storeTotalCtrl">

        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="depositDdc.storeTotal" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('storeTotalCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
            <%-- 입금/기타공제 등록 --%>
            <button class="btn_blue fr mt5 mr10"  id="btnDepositDdcReg" ng-click="depositDdcReg()">
                <s:message code="depositDdc.depositDdcReg" />
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
                <th><s:message code="depositDdc.searchDate" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="srchStartDate" name="srchStartDate" class="w150px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="srchEndDate" name="srchEndDate" class="w150px" /></span>
                        </span>
                    </div>
                </td>
                <%-- 매장선택 --%>
                <th><s:message code="depositDdc.storeSelect" /></th>
                <td>
                    <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="selectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:600px;">
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh100"><s:message code="depositDdc.storeTotal" /></span>
                    <button class="btn_skyblue" id="btnExcel" ng-click="excelDownload()">
                        <s:message code="cmm.excel.down" />
                    </button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="depositDdc.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="depositDdc.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="depositDdc.deposit"/>" binding="depositMoneyAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="depositDdc.otherDdc"/>" binding="otherMoneyAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="depositDdc.tot"/>" binding="totMoneyAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="storeTotalDtlCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:600px;">
            <div class="updownSet oh mb10 pd5">
                <span class="fl bk lh100"><s:message code="depositDdc.dtlHistory" /></span>
                <button class="btn_skyblue" id="btnExcel2" ng-click="excelDownload2()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridStoreTotalDtl">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="depositDdc.date"/>" binding="moneyDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.moneyFg"/>" binding="moneyFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.moneyAmt"/>" binding="moneyAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.remark"/>" binding="remark" width="150" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.userNm"/>" binding="userNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="depositDdc.date"/>" binding="moneyDate" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="depositDdc.seqNo"/>" binding="seqNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/depositDdc/storeTotal.js?ver=20220421.03" charset="utf-8"></script>

<%-- 입금/기타공제 등록 팝업 --%>
<c:import url="/WEB-INF/view/excclc/excclc/depositDdc/popup/depositDdcReg.jsp">
</c:import>

<%-- 계정 등록 팝업 --%>
<c:import url="/WEB-INF/view/excclc/excclc/depositDdc/popup/moneyFgReg.jsp">
</c:import>