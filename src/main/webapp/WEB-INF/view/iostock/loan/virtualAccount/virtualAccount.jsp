<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="virtualAccountCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('virtualAccountCtrl', 1)">
                <s:message code="cmm.search"/>
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
            <%-- 등록일자 --%>
            <th><s:message code="virtualAccount.reqDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 가상계좌 상태구분 --%>
            <th><s:message code="virtualAccount.depositFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchDepositFgCombo"
                            ng-model="depositFg"
                            items-source="_getComboData('depositFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchDepositFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="cmm.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 매장명 --%>
                <th><s:message code="cmm.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh" style="display: none;">
        <%-- 가상계좌 입금 생성 --%>
        <button class="btn_skyblue ml5 fr" ng-click="loanVirtualAccount()"><s:message code="virtualAccount.virtualAccountRegister"/></button>
        <div class="sb-select dkbr ml5 fr">
            <%-- 가상계좌 발급금액 --%>
            <s:message code="virtualAccountRegister.vaMny"/> :
            <input type="text" class="sb-input w130px tr" id="txtVaMny" ng-model="txtVaMny" />
        </div>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <%-- 매장 --%>
            <div class="ml5 fr">
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="virtualAccountStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </div>
            <div class="sb-select dkbr ml5 fr">
                <%-- 매장선택 --%>
                <p class="tl s14 mt5 lh15"><s:message code="cmm.store.select"/> : </p>
            </div>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="virtualAccountStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
    </div>

    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" binding="storeNm" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="virtualAccount.reqDate"/>" binding="reqDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.reqSeq"/>" binding="reqSeq" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.depositFg"/>" binding="depositFg" data-map="depositFgFgDataMap" width="115" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.vaMny"/>" binding="vaMny" width="115" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.ordrIdxx"/>" binding="ordrIdxx" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.goodName"/>" binding="goodName" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.receiptYn"/>" binding="receiptYn" data-map="receiptYnDataMap" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.vaTaxno"/>" binding="vaTaxno" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.bankname"/>" binding="bankname" width="105" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.depositor"/>" binding="depositor" width="115" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.account"/>" binding="account" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccount.orderNo"/>" binding="orderNo" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 가상계좌 API 은행코드 --%>
    var vaBankcodeComboData = ${ccu.getCommCodeSelect("233")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/virtualAccount/virtualAccount.js?ver=20240807.01" charset="utf-8"></script>

<%-- 가상계좌 입금 생성 팝업 --%>
<c:import url="/WEB-INF/view/iostock/loan/virtualAccount/virtualAccountRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>