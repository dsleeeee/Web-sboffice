<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="sendStatusView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="sendStatusCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="sendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('sendStatusCtrl',1)" id="nxBtnSearch1">
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
                    <s:message code="sendStatus.regDt"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <%-- 보내는이 전화번호(발신번호) --%>
                <th>
                    <s:message code="sendStatus.sPhoneNumber"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSsPhoneNumber" ng-model="ssPhoneNumber" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 보내는이 소속코드 --%>
                <th>
                    <s:message code="sendStatus.sOrgnCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSsOrgnCd" ng-model="ssOrgnCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 보내는이 소속명 --%>
                <th>
                    <s:message code="sendStatus.sOrgnNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSsOrgnNm" ng-model="ssOrgnNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 받는이 소속코드 --%>
                <th>
                    <s:message code="sendStatus.rOrgnCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchRrOrgnCd" ng-model="rrOrgnCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 받는이 소속명 --%>
                <th>
                    <s:message code="sendStatus.rOrgnNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchRrOrgnNm" ng-model="rrOrgnNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 예약여부 --%>
                <th>
                    <s:message code="sendStatus.reserveYn" />
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
                    <s:message code="sendStatus.sendStatus" />
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
            <button class="btn_skyblue ml5 fr" id="btnCancelRepresent" ng-click="reserveCancel()">
                <s:message code="sendStatus.reserveCancel" />
            </button>
            <c:if test="${orgnFg == 'MASTER'}">
                <%-- SMS전송 --%>
                <button class="btn_skyblue ml5 fr" id="btnSmsSendRepresent" ng-click="smsSendPop()">
                    <s:message code="sendStatus.smsSend"/>
                </button>
            </c:if>
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
                        id="wjGridSendStatus">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
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
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="sendStatusCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

    <%-- 엑셀다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="sendStatusExcelCtrl">
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
    </div>

</div>

<script type="text/javascript">
    // SMS전송 - 메세지그룹
    var msgGrpColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="msgGrpCol" items="${msgGrpColList}">
        var msgGrpParam = {};
        msgGrpParam.msgGrpCd = "${msgGrpCol.msgGrpCd}";
        msgGrpParam.msgGrpNm = "${msgGrpCol.msgGrpNm}";
        msgGrpColList.push(msgGrpParam);
    </c:forEach>
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/sendStatus/sendStatus.js?ver=20241202.01" charset="utf-8"></script>

<%-- 메세지 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/sendStatus/messageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS전송 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsSend.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>