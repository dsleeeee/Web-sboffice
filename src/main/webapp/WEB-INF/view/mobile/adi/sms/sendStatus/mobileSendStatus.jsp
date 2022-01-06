<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileSendStatusCtrl">

    <div class="searchBar">
        <%-- 전송결과 --%>
        <a href="#" class="fl"><s:message code="mobile.sendStatus"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('mobileSendStatusCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 등록일시 --%>
            <th>
                <s:message code="mobile.sendStatus.regDt"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 예약여부 --%>
            <th>
                <s:message code="mobile.sendStatus.reserveYn" />
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
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
        <%-- SMS전송 --%>
        <%--<button class="btn_skyblue ml5 fr" id="btnSmsSendRepresent" ng-click="smsSendPop()">--%>
            <%--<s:message code="mobile.sendStatus.smsSend"/>--%>
        <%--</button>--%>
        <%-- 예약취소 --%>
        <button class="btn_skyblue ml5 fr" id="btnCancelRepresent" ng-click="reserveCancel()">
            <s:message code="mobile.sendStatus.reserveCancel" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flexMobileSendStatus"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

            <%--<!-- define columns -->--%>
            <wj-flex-grid-column header="<s:message code="mobile.cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.orgnNm"/>" binding="sOgnNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.name"/>" binding="sUserNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.telNo"/>" binding="sPhoneNumber" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.orgnNm"/>" binding="rOgnNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.telNo"/>" binding="rPhoneNumber" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.sendStatus"/>" binding="sendStatus" data-map="sendStatusFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.resultNm"/>" binding="resultNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.company"/>" binding="company" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

            <%--저장시 필요--%>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.msgId"/>" binding="msgId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.reserveYn"/>" binding="reserveYn" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.gubun"/>" binding="gubun" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.smsSendSeq"/>" binding="smsSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mobile.sendStatus.sendStatus"/>" binding="sendStatus" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <!-- 조회 결과가 없을 때, msg 띄우기 -->
            <div class="gridMsg" id="mobileSendStatusMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum3 mt20">
        <%-- id --%>
        <ul id="mobileSendStatusCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<%--<script type="text/javascript">--%>
    <%--// SMS전송 - 메세지그룹--%>
    <%--var msgGrpColList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 할인 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="msgGrpCol" items="${msgGrpColList}">--%>
        <%--var msgGrpParam = {};--%>
        <%--msgGrpParam.msgGrpCd = "${msgGrpCol.msgGrpCd}";--%>
        <%--msgGrpParam.msgGrpNm = "${msgGrpCol.msgGrpNm}";--%>
        <%--msgGrpColList.push(msgGrpParam);--%>
    <%--</c:forEach>--%>
<%--</script>--%>

<script type="text/javascript" src="/resource/solbipos/js/mobile/adi/sms/sendStatus/mobileSendStatus.js?ver=20220104.01" charset="utf-8"></script>

<%-- 메세지 팝업 --%>
<c:import url="/WEB-INF/view/mobile/adi/sms/sendStatus/mobileMessageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS전송 팝업 --%>
<%--<c:import url="/WEB-INF/view/adi/sms/smsSend/smsSend.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>