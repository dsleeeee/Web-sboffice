<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="sendStatusView" class="subCon" style="display: none;">
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
            <button class="btn_skyblue ml5 fr" id="btnSmsSendRepresent" ng-click="smsSendPop()">
                <s:message code="sendStatus.smsSend"/>
            </button>
            <%-- 예약취소 --%>
            <button class="btn_skyblue ml5 fr" id="btnCancelRepresent" ng-click="reserveCancel()">
                <s:message code="sendStatus.reserveCancel" />
            </button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.orgnNm"/>" binding="sOgnNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.name"/>" binding="sUserNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.telNo"/>" binding="sPhoneNumber" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.orgnNm"/>" binding="rOgnNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.telNo"/>" binding="rPhoneNumber" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.sendStatus"/>" binding="sendStatus" data-map="sendStatusFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.resultNm"/>" binding="resultNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.company"/>" binding="company" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>

                    <%--저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="sendStatus.msgId"/>" binding="msgId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.reserveYn"/>" binding="reserveYn" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.gubun"/>" binding="gubun" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.smsSendSeq"/>" binding="smsSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sendStatus.sendStatus"/>" binding="sendStatus" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/sendStatus/sendStatus.js?ver=20210806.03" charset="utf-8"></script>

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