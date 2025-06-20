<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="remainAmtHistLayer" control="remainAmtHistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:500px;">

    <div ng-controller="remainAmtHistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="remainAmtChk.remainAmtHist"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
            </div>

            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="remainAmtChk.useFg"/>" binding="useFg" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="remainAmtChk.useAmt"/>" binding="useAmt" width="50" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <!-- 충전내역 -->
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.orgn"/>" binding="orgn" width="300" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeDate"/>" binding="chargeDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeTime"/>" binding="chargeTime" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.pgresource"/>" binding="pgresource" data-map="pgresourceDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.controlno"/>" binding="controlno" width="110" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.baseChargeAmt"/>" binding="baseChargeAmt" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeAmt"/>" binding="chargeAmt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.vatAmt"/>" binding="vatAmt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeTot"/>" binding="chargeTot" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.successYn"/>" binding="successYn" data-map="successYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.resultmessage"/>" binding="resultmessage" width="100" align="left" is-read-only="true"></wj-flex-grid-column>

                        <%-- 사용내역 --%>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeIdNm"/>" binding="chargeIdNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.orgnCd"/>" binding="orgnCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.orgnNm"/>" binding="orgnNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsChargeHist.approvalnum"/>" binding="approvalnum" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sOrgnCd"/>" binding="sOgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sOrgnNm"/>" binding="sOgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sName"/>" binding="sUserNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sTelNo"/>" binding="sPhoneNumber" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.rOrgnCd"/>" binding="rOgnCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.rOrgnNm"/>" binding="rOgnNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.rTelNo"/>" binding="rPhoneNumber" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.reserveYn"/>" binding="reserveYn" data-map="reserveYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.sendStatus"/>" binding="sendStatus" data-map="sendStatusFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.resultNm"/>" binding="resultNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.company"/>" binding="company" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="sendStatus.msgId"/>" binding="msgId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.gubun"/>" binding="gubun" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sendStatus.smsSendSeq"/>" binding="smsSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/remainAmtChk/remainAmtHist.js?ver=20250612.01" charset="utf-8"></script>
