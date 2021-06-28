<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="contents" id ="divSmsSendPageAuth" style="display: none;">
    <div class="elseCon">
        <p class="lgTxt">죄송합니다.<br />발신번호가 등록되지 않았습니다.</p>
        <p class="smTxt mt20">
            발신번호를 사전 등록하셔야 합니다.<br />
            해당 페이지에 접근할 수 없습니다.<br />
            ${sessionScope.sessionInfo.currentMenu.resrceCd}
        </p>
    </div>
</div>

<div class="subCon" id ="divSmsSendPage" ng-controller="smsSendCtrl">

    <%-- 좌측 --%>
    <div class="wj-TblWrapBr w60 fl">
        <%-- (광고), (무료수신거부) display 여부 : pageGubun값이 SMS일때만 보여줌 --%>
        <label id="lblPageGubun" style="display: none;"></label>
        <%-- 제목 --%>
        <div class="w55 fl pd10" style="height:360px;">
            <table>
                <colgroup>
                    <col class="w20" />
                    <col class="w80" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 제목 --%>
                    <th>
                        <s:message code="smsSend.title" />
                    </th>
                    <td>
                        <input type="text" class="sb-input-msg w100" id="srchTitle" ng-model="title" />
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr id="trStoreNmInfo" style="display: none">
                    <td></td>
                    <td>
                        <label id="lblStoreNmInfo"></label>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <%-- 메세지내용 --%>
                    <th>
                        <s:message code="smsSend.messageContext" />
                    </th>
                    <td>
                        <textarea id="messageContent" name="messageContent" ng-model="messageContent" style="width:100%; height:160px; overflow-x:hidden; background-color: #EAF7FF" ng-keyup="showByte()"></textarea>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr id="trMemoInfo" style="display: none;">
                    <td></td>
                    <td>
                        <label id="lblMemoInfo"></label>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td></td>
                    <td>
                        <label id="lblTxtByte"></label>
                        <s:message code="smsSend.byte" />
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <%-- 전송자 --%>
                    <th>
                        <s:message code="smsSend.sendName" />
                    </th>
                    <td>
                        <%-- 전송자번호 --%>
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w130px fl"
                                    id="srchTelNoCombo"
                                    ng-model="telNoCombo"
                                    items-source="_getComboData('telNoCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 발신번호추가 --%>
                        <button class="btn_skyblue ml5 fl" id="btnTelNoAdd" ng-click="telNoAdd()">
                            <s:message code="smsSend.telNoAdd" />
                        </button>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td></td>
                    <td>
                        <%-- 전송 --%>
                        <button class="btn_skyblue ml5 fl" id="btnSmsSend" ng-click="smsSendReserve('N')">
                            <s:message code="smsSend.send" />
                        </button>
                        <%-- 예약 --%>
                        <button class="btn_skyblue ml5 fl" id="btnSmsReserve" ng-click="smsSendReserve('Y')">
                            <s:message code="smsSend.reserve" />
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- 자동변환 --%>
        <div class="w45 fl pd10 bl" style="height:360px;">
            <div class="w100" style="overflow-x: auto; overflow-y: hidden;">
                <table>
                    <tr>
                        <td>
                            <%-- 자동변환 --%>
                            <span><img src="/resource/solbipos/css/img/sms/auto_str.jpg"></span>
                            <%-- 이름 --%>
                            <span><a href="#" ng-click="addMsg('#이름#')"><img src="/resource/solbipos/css/img/sms/btn_add_name.jpg"></a></span>
                            <%-- 추가사항 --%>
                            <span><a href="#" ng-click="addMsg('#추가사항#')"><img src="/resource/solbipos/css/img/sms/btn_add_str.jpg"></a></span>
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td>
                            <%-- 이모티콘 --%>
                            <c:import url="/WEB-INF/view/adi/sms/smsSend/emoticon.jsp">
                            </c:import>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <%-- 문구 --%>
        <div class="w100 fl pd10 bt" style="height:220px;">
            <div id ="divMsgGrpPage">
                <%--subTab--%>
                <div class="tabType1">
                    <ul>
                        <%-- 메세지그룹 탭 --%>
                        <c:forEach var="msgGrpCol" items="${msgGrpColList}">
                            <li>
                                <a id="msgGrpTab${msgGrpCol.msgGrpCd}" href="#" ng-click="msgGrpShow('${msgGrpCol.msgGrpCd}')">${msgGrpCol.msgGrpNm}</a>
                            </li>
                        </c:forEach>
                    </ul>
                    <div style="height:170px; overflow-x: hidden; overflow-y: auto;">
                        <div id="divMsgComment"></div>
                    </div>
                </div>
            </div>
            <div id ="divMsgGrpPageAuth" style="display: none;">
                <div class="updownSet oh mb10 mt60">
                    <span class="fl bk lh30">등록된 메세지그룹이 없습니다.</span>
                </div>
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30">'부가서비스 > SMS관리 > 메세지관리' 등록바랍니다.</span>
                </div>
            </div>
        </div>
    </div>
    <%-- //좌측 --%>

    <%-- 우측 --%>
    <div class="wj-TblWrapBr w40 fl">
        <%-- 수신자목록 --%>
        <div class="w100 fl pd10" style="height:580px;">
            <table>
                <tr>
                    <td>
                        <%-- 받는사람 --%>
                        <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                        <span><s:message code="smsSend.receiveName" /></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%-- 잔여수량 --%>
                        <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                        <span><s:message code="smsSend.smsQty" /></span>
                        <span><label id="lblSmsQty"></label></span>
                    </td>
                </tr>
            </table>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="updownSet oh mb10">
                    <button class="btn_skyblue" id="btnAddresseeAdd" ng-click="addAddressee()"><s:message code='smsSend.addresseeAdd' /></button>
                    <button class="btn_skyblue" id="btnAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnDel" ng-click="del()"><s:message code='cmm.del' /></button>
                </div>
                <div class="wj-gridWrap" style="height:470px; overflow-y: hidden; overflow-x: hidden;">
                    <div class="row">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.telNm"/>" binding="telNm" width="80" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.telNo"/>" binding="telNo" width="100" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.memo"/>" binding="memo" width="80" align="center"></wj-flex-grid-column>

                            <%--저장시 필요--%>
                            <wj-flex-grid-column header="<s:message code="smsSend.membrNo"/>" binding="membrNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.rOgnFg"/>" binding="rOgnFg" width="110" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.rOgnCd"/>" binding="rOgnCd" width="110" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="smsSend.rUserId"/>" binding="rUserId" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
            <%-- //그리드 --%>
        </div>
    </div>
    <%-- //우측 --%>

    <!-- 팝업 -->
    <%-- 광고문자 발송에 대한 의무사항 안내 --%>
    <div ng-controller="smsPopupCtrl">
        <div id="divDimmed" class="fullDimmed" style="display: none;"></div>
        <div id="divPopup" class="layer" style="display: none;">
            <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
                <!--layerContent-->
                <div class="title" style="width:770px;">
                    <a href="#" class="btn_close" ng-click="close()"></a>
                    <div class="con">
                        <img src="/resource/solbipos/css/img/sms/sms_advertisement.jpg" style="width:100%" alt="광고문자 발송에 대한 의무사항 안내" />
                    </div>
                    <div class="btnSet">
                        <span><a href="#" class="btn_blue" id="btnCloseToday" ng-click="closeToday()">오늘하루 열지않기</a></span>
                        <span><a href="#" class="btn_blue" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 팝업 -->

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsSend.js?ver=20210625.01" charset="utf-8"></script>

<%-- 발신번호 사전등록 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsTelNoRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수신자추가 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/addresseeAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS예약 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsReserve.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>