<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="prepaidCardChargeStatusDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:550px;" fade-in="false" fade-out="false">
    <div ng-controller="prepaidCardChargeStatusDtlCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prepaidCardStatus.prepaidCardChargeStatus"/> 상세
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="mt20 oh sb-select dkbr">
                <%-- 엑셀 다운로드 --%>
                <button class="btn_skyblue ml5 fr" id="btnExcel" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <!-- define columns -->
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="prepaidCardStatus.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prepaidCardStatus.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.saleDate"/>" binding="saleDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="prepaidCardStatus.posNo"/>" binding="posNo" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.cardNo"/>" binding="cardNo" width="200" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.chargeCnt"/>" binding="chargeCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.totChargeAmt"/>" binding="totChargeAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.chargeCancelCnt"/>" binding="chargeCancelCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.totChargeCancelAmt"/>" binding="totChargeCancelAmt" width="120" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prepaidCardStatus.remainAmt"/>" binding="remainAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prepaidCardStatus/prepaidCardChargeStatusDtl.js?ver=20260408.01" charset="utf-8"></script>