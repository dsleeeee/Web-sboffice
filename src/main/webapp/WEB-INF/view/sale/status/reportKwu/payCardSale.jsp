<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="payCardSaleCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="payCardSale.payCardSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('payCardSaleCtrl')">
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
          <th><s:message code="cmm.search.date" /></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" ng-model="srchStartDate" class="w110px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" ng-model="srchEndDate" class="w110px"></span>
            </div>
          </td>
          <%-- 승인구분 --%>
          <th>
            <s:message code="payCardSale.apprFg" />
          </th>
          <td>
            <div class="sb-select fl w150px">
                <wj-combo-box
                    id="apprFg"
                    ng-model="apprFg"
                    items-source="_getComboData('apprFgCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="apprFgCombo"
                    initialized="_initComboBox(s)">
                </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="payCardSaleStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="payCardSaleStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter"
                id="wjGrid">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="payCardSale.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.apprFg"/>" binding="apprGubun" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.cardNo"/>" binding="cardNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.apprAmt"/>" binding="apprAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.acquireNm"/>" binding="acquireNm" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.apprNo"/>" binding="apprNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.membrJoinNo"/>" binding="membrJoinNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.instCnt"/>" binding="instCntNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="payCardSale.transactionType"/>" binding="transactionType" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/reportKwu/payCardSale.js?ver=20220916.01" charset="utf-8"></script>