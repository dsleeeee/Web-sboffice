<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="mCoupnHistoryCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="mCoupnHistory.mCoupnHistory"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_broadcast('mCoupnHistoryCtrl')">
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
            <%-- 조회기간 --%>
            <th id="thSrchDate"><s:message code="mCoupnHistory.srchDate"/></th>
            <td id="tdSrchDate">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px"/></span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                        <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="mCoupnHistoryStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="mCoupnHistoryStoreCd" value="${sessionInfo.storeCd}"/>
                <th></th>
                <td></td>
            </c:if>
        </tr>
        <tr>
            <%-- 모바일쿠폰바코드번호 --%>
            <th><s:message code="mCoupnHistory.mCoupnBarcdNo"/></th>
            <td>
                <input type="text" id="mCoupnBarcdNo" name="mCoupnBarcdNo" ng-model="mCoupnBarcdNo" class="sb-input w100" onkeyup="fnNxBtnSearch('');"/>
            </td>
            <%-- 승인번호 --%>
            <th><s:message code="mCoupnHistory.apprNo"/></th>
            <td>
                <input type="text" id="apprNo" name="apprNo" ng-model="apprNo" class="sb-input w100" onkeyup="fnNxBtnSearch('');"/>
            </td>
        </tr>
        <tr>
            <%-- 모바일쿠폰사 --%>
            <th><s:message code="mCoupnHistory.mCoupnCd"/></th>
            <td>
                <div class="sb-select w50">
                    <wj-combo-box
                            id="mCoupnCd"
                            ng-model="mCoupnCd"
                            items-source="_getComboData('mCoupnCd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="mCoupnCdCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 영수타입 --%>
            <th><s:message code="mCoupnHistory.billType"/></th>
            <td>
                <div class="sb-select w50">
                    <wj-combo-box
                            id="billType"
                            ng-model="billType"
                            items-source="_getComboData('billType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="billTypeCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>	<%-- 엑셀 다운로드 --%>
    </div>

    <div class="w100 mt10">
        <%--모바일쿠폰사용이력 테이블--%>
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:500px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.billType"/>" binding="billType" data-map="billTypeDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.billInfo"/>" binding="rtnReasonNm" width="190" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.lineNo"/>" binding="lineNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.lineSeqNo"/>" binding="lineSeqNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.saleYn"/>" binding="saleYn" data-map="saleYnDataMap" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.saleAmt"/>" binding="saleAmt" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.taxAmt"/>" binding="taxAmt" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.vatAmt"/>" binding="vatAmt" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.tipAmt"/>" binding="tipAmt" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.noTaxAmt"/>" binding="noTaxAmt" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnCd"/>" binding="mcoupnCd" data-map="mcoupnCdDataMap" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnTerminlNo"/>" binding="mcoupnTermnlNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnTypeFg"/>" binding="mcoupnTypeFg" data-map="mcoupnTypeFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnBarcdNo"/>" binding="mcoupnBarcdNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnUprc"/>" binding="mcoupnUprc" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.mCoupnRemainAmt"/>" binding="mcoupnRemainAmt" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprUniqueNo"/>" binding="apprUniqueNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprDt"/>" binding="apprDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprNo"/>" binding="apprNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprMsg"/>" binding="apprMsg" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cornrCd"/>" binding="cornrCd" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprLogNo"/>" binding="apprLogNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cashBillApprProcFg"/>" binding="cashBillApprProcFg" data-map="cashBillApprProcFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cashBillCardNo"/>" binding="cashBillCardNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cashBillApprDt"/>" binding="cashBillApprDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cashBillApprNo"/>" binding="cashBillApprNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cashBillApprLogNo"/>" binding="cashBillApprLogNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.orgBillNo"/>" binding="orgBillNo" width="160" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.regDt"/>" binding="regDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.regId"/>" binding="regId" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.modDt"/>" binding="modDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.modId"/>" binding="modId" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.apprProcFg"/>" binding="apprProcFg" data-map="apprProcFgDataMap" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnHistory.cupAmt"/>" binding="cupAmt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>



            </wj-flex-grid>
        </div>
        <%--모바일쿠폰사용이력 테이블--%>
    </div>
</div>

<script type="text/javascript">

    //
    var mCoupnCd = ${ccu.getCommCode("166")};

    var payColList = [];
    var dcColList  = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
    var dcParam      = {};
    dcParam.dcCd     = "${dcCol.dcCd}";
    dcParam.dcMethod = "${dcCol.dcMethod}";
    dcColList.push(dcParam);
    </c:forEach>

    var payCol      = '${payCol}';
    var dcCol       = '${dcCol}';
    var guestCol    = '${guestCol}';
    var arrPayCol   = payCol.split(',');
    var arrDcCol    = dcCol.split(',');
    var arrGuestCol = guestCol.split(',');

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/mCoupnHistory/mCoupnHistory.js?ver=20260327.01" charset="utf-8"></script>

<%-- 영수증 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/billInfo/billInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="payColList" value="${payColList}"/>
    <c:param name="guestColList" value="${guestColList}"/>
</c:import>