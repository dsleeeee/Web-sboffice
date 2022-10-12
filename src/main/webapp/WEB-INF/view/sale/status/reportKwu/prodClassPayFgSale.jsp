<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="prodClassPayFgSaleCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prodClassPayFgSale.prodClassPayFgSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodClassPayFgSaleCtrl')">
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
          <td >
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" ng-model="srchStartDate" class="w110px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" ng-model="srchEndDate" class="w110px"></span>
            </div>
          </td>
          <%-- 매출구분 --%>
          <th>
            <s:message code="prodClassPayFgSale.saleFg" />
          </th>
          <td>
            <div class="sb-select fl">
                <wj-combo-box
                    id="prodClassLevel"
                    ng-model="prodClassLevel"
                    items-source="_getComboData('prodClassLevelCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="prodClassLevelCombo"
                    initialized="_initComboBox(s)">
                </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장명 --%>
                <th>
                    <s:message code="prodClassPayFgSale.storeNm" />
                </th>
                <td colspan="3">
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="prodClassPayFgSaleStore"/>
                    </jsp:include>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="prodClassPayFgSaleStoreCd" value="${sessionInfo.storeCd}"/>
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
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.prodClassCd"/>" binding="levelCd" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.prodClassNm"/>" binding="levelNm" width="130" align="left"	is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.totSaleAmt"/>" binding="totSaleAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.totDcAmt"/>" binding="totDcAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.realSaleAmt"/>" binding="realSaleAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.cashAmt"/>" binding="payAmt02" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.cashBill"/>" binding="payAmt021" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.cardAmt"/>" binding="payAmt01" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.giftAmt"/>" binding="giftAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.postPaidAmt"/>" binding="postPaidAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.pointAmt"/>" binding="pointAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodClassPayFgSale.etcAmt"/>" binding="etcAmt" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript">
	var payColList = [];
	<%--javascript에서 사용할 결제수단 json 데이터 생성--%>
	<c:forEach var="payCol" items="${payColList}">
	var payParam       = {};
	payParam.payCd     = "${payCol.payCd}";
	payParam.payMethod = "${payCol.payMethod}";
	payColList.push(payParam);
	</c:forEach>

	var payCol      = '${payCol}';
	var arrPayCol   = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/reportKwu/prodClassPayFgSale.js?ver=20220916.01" charset="utf-8"></script>