<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="alimtalkSendStatusView" class="subCon" style="display: none;">

    <div ng-controller="alimtalkSendStatusCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="alimtalkSendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('alimtalkSendStatusCtrl',1)" id="nxBtnSearch1">
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
            <tr>
                <%-- 등록일시 --%>
                <th>
                    <s:message code="alimtalkSendStatus.regDt"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <%-- 보내는이 소속코드 --%>
                <th>
                    <s:message code="alimtalkSendStatus.sOrgnCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSsOrgnCd" ng-model="ssOrgnCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 보내는이 소속명 --%>
                <th>
                    <s:message code="alimtalkSendStatus.sOrgnNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSsOrgnNm" ng-model="ssOrgnNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 받는이 소속코드 --%>
                <th>
                    <s:message code="alimtalkSendStatus.rOrgnCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchRrOrgnCd" ng-model="rrOrgnCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 받는이 소속명 --%>
                <th>
                    <s:message code="alimtalkSendStatus.rOrgnNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchRrOrgnNm" ng-model="rrOrgnNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 예약여부 --%>
                <th>
                    <s:message code="alimtalkSendStatus.reserveYn" />
                </th>
                <td>
                    <div class="sb-select w40">
                        <wj-combo-box
                                id="srchReserveYnCombo"
                                ng-model="reserveYn"
                                items-source="_getComboData('reserveYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="reserveYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 결과 --%>
                <th>
                    <s:message code="alimtalkSendStatus.sendStatus" />
                </th>
                <td>
                    <div class="sb-select w40">
                        <wj-combo-box
                                id="srchSendStatusCombo"
                                ng-model="sendStatus"
                                items-source="_getComboData('sendStatusCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="sendStatusCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
            <%-- 예약취소 --%>
            <button class="btn_skyblue ml5 fr" id="btnCancelRepresent" ng-click="reserveCancel()" style="display: none;">
                <s:message code="alimtalkSendStatus.reserveCancel" />
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
                        id="wjGridAlimtalSendStatus">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sOrgnCd"/>" binding="sOgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sOrgnNm"/>" binding="sOgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sUserNm"/>" binding="sUserNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rOrgnCd"/>" binding="rOgnCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rOrgnNm"/>" binding="rOgnNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rTelNo"/>" binding="rPhoneNumber" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.reserveYn"/>" binding="reserveYn" data-map="reserveYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendStatus"/>" binding="sendStatus" data-map="sendStatusFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.resultNm"/>" binding="resultNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendOrgnCd"/>" binding="sendOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendOrgnNm"/>" binding="sendOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.payOrgnCd"/>" binding="payOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.payOrgnNm"/>" binding="payOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.senderKeyOrgnCd"/>" binding="senderKeyOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.senderKeyOrgnNm"/>" binding="senderKeyOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmSendYn"/>" binding="rmSendYn" data-map="rmSendYnDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmType"/>" binding="rmType" data-map="rmTypeDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmContent"/>" binding="rmContent" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmSendNo"/>" binding="rmSendNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgId"/>" binding="msgId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.gubun"/>" binding="gubun" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.alkSendSeq"/>" binding="alkSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.requestId"/>" binding="requestId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="alimtalkSendStatusCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

    <%-- 엑셀다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="alimtalkSendStatusExcelCtrl">
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sOrgnCd"/>" binding="sOgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sOrgnNm"/>" binding="sOgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sUserNm"/>" binding="sUserNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rOrgnCd"/>" binding="rOgnCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rOrgnNm"/>" binding="rOgnNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rTelNo"/>" binding="rPhoneNumber" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.reserveYn"/>" binding="reserveYn" data-map="reserveYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendStatus"/>" binding="sendStatus" data-map="sendStatusFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.resultNm"/>" binding="resultNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendOrgnCd"/>" binding="sendOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.sendOrgnNm"/>" binding="sendOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.payOrgnCd"/>" binding="payOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.payOrgnNm"/>" binding="payOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.senderKeyOrgnCd"/>" binding="senderKeyOrgnCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.senderKeyOrgnNm"/>" binding="senderKeyOrgnNm" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmSendYn"/>" binding="rmSendYn" data-map="rmSendYnDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmType"/>" binding="rmType" data-map="rmTypeDataMap" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmContent"/>" binding="rmContent" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.rmSendNo"/>" binding="rmSendNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                <%--저장시 필요--%>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.msgId"/>" binding="msgId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.gubun"/>" binding="gubun" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.alkSendSeq"/>" binding="alkSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkSendStatus.requestId"/>" binding="requestId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var userId = "${userId}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    var groupSenderKey = "${groupSenderKey}";
    var groupSenderKeyNm = "${groupSenderKeyNm}";
    var appKey = "${appKey}";
    var secretKey = "${secretKey}";
    var apiUrl = "${apiUrl}";

    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus.js?ver=20241126.01" charset="utf-8"></script>

<%-- 알림톡 메세지 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendStatus/alimtalkMessageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>