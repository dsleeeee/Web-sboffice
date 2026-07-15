<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="acMonthTransferView" class="subCon" ng-controller="acMonthTransferCtrl" style="display: none;padding: 10px 20px 40px;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="acMonthTransfer.title"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('acMonthTransferCtrl', 1)" id="acMonthTransferBtnSearch">
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
        <%-- 조회월 --%>
        <tr>
            <th><s:message code="cmm.search.date"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchMonthStartDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchMonthEndDate" class="w110px"/></span>
                </div>
            </td>
        </tr>
        <%-- 매장선택 --%>
        <tr>
            <th><s:message code="cmm.store.select"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="acMonthTransferStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 항목1 --%>
            <th><s:message code="acStoreOption.option01"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchMonthOption01" ng-model="option01"/>
            </td>
            <%-- 항목2 --%>
            <th><s:message code="acStoreOption.option02"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMonthOption02Combo"
                            ng-model="option02"
                            items-source="_getComboData('option02Combo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 항목3 --%>
            <th><s:message code="acStoreOption.option03"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMonthOption03Combo"
                            ng-model="option03"
                            items-source="_getComboData('option03Combo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 항목4 --%>
            <th><s:message code="acStoreOption.option04"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchMonthOption04" ng-model="option04" numberOnly maxlength="3"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:420px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridAcMonthTransferList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.saleYm"/>" binding="saleYm" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.storeCd"/>" binding="storeCd" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.storeNm"/>" binding="storeNm" width="120" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.totDcAmt"/>" binding="totDcAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.billCnt"/>" binding="billCnt" width="80" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.billUprc"/>" binding="billUprc" width="80" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.gaAmt"/>" binding="gaAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.vatAmt"/>" binding="vatAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.totTipAmt"/>" binding="totTipAmt" width="90" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.totEtcAmt"/>" binding="totEtcAmt" width="90" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acMonthTransfer.cupAmt"/>" binding="cupAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingMain/acMonthTransfer.js?ver=20260714.01" charset="utf-8"></script>
