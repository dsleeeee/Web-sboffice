<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/loanInfo/loanInfo/"/>

<div class="subCon">

    <div ng-controller="loanInfoCtrl">
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="searchLoanInfo()">
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
                <th><s:message code="loanInfo.searchDate"/></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w150px fl">
                            <wj-combo-box
                                id="srchDateFg"
                                ng-model="dateFg"
                                items-source="_getComboData('srchDateFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="selectedIndexChanged(s, e)">
                            </wj-combo-box>
                        </span>
                        <div id="dateLayer" class="sb-select fl ml5" ng-show="dateLayer">
                            <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh" style="display: block;">
            <%-- 가상계좌 입금 생성 --%>
            <button class="btn_skyblue ml5 fr" ng-click="virtualAccountRegister()"><s:message code="virtualAccount.virtualAccountRegister"/></button>
            <div class="sb-select dkbr ml5 fr">
                <%-- 가상계좌 발급금액 --%>
                <s:message code="virtualAccountRegister.vaMny"/> :
                <input type="text" class="sb-input w130px tr" id="txtVaMny" ng-model="txtVaMny" />
            </div>
            <input type="hidden" id="loanInfoStoreCd" value="${sessionInfo.storeCd}"/>
            <%-- 여신사용액 --%>
            <button class="btn_skyblue ml5 fr" ng-click="loanVirtualAccount()"><s:message code="loan.loanVirtualAccount"/></button>
        </div>

        <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 70px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridLoanInfoCtrl">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" 			binding="gChk" 		width="30" 	align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.limitLoanAmt"/>" binding="limitLoanAmt" width="90" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.useLoanAmt"/>" binding="useLoanAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.currLoanAmt"/>" binding="currLoanAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.maxOrderAmtYn"/>" binding="maxOrderAmtYn" width="120" align="center" is-read-only="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.maxOrderAmt"/>" binding="maxOrderAmt" width="110" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.orderFg"/>" binding="orderFg" width="90" align="center" is-read-only="false" data-map="orderFg"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.availableOrderAmt"/>" binding="availableOrderAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.noOutstockAmtFg"/>" binding="noOutstockAmtFg" width="90" align="center" is-read-only="false" data-map="noOutstockAmtFg"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.orderCloseYn"/>" binding="orderCloseYn" width="120" align="center" is-read-only="false" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="loan.remark"/>" binding="remark" width="100" align="left" is-read-only="false" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="loanInfoCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>

<%--  <div class="mt20 oh sb-select dkbr">--%>
<%--    &lt;%&ndash; 엑셀 다운로드 &ndash;%&gt;--%>
<%--    &lt;%&ndash;<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>&ndash;%&gt;--%>
<%--  </div>--%>

    <div class="w100 mt10" ng-controller="loanInfoManageCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 mb10 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
        </div>
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex2"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridLoanInfoManageCtrl">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="loanInfo.loanDate"/>" binding="loanDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="loanInfo.outAmt"/>" binding="outAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="loanInfo.inAmt"/>" binding="inAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="loanInfo.limitLoanAmt"/>" binding="limitLoanAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="loanInfo.currLoanAmt"/>" binding="currLoanAmt" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="loanInfo.remark"/>" binding="remark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="loanInfoCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/loanInfo/loanInfo.js?ver=20270808.01" charset="utf-8"></script>

<%-- 가상계좌 입금 생성 팝업 --%>
<c:import url="/WEB-INF/view/iostock/loan/virtualAccount/virtualAccountRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>