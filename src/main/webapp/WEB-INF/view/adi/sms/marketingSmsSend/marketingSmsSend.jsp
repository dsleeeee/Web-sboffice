<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<%--<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />--%>

<div id="marketingSmsSendView" style="display: none;">

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

    <div class="subCon" id ="divSmsSendPage" ng-controller="marketingSmsSendCtrl">
        <%-- 조회조건 --%>
        <%--<div class="searchBar flddUnfld">--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="marketingSmsSend.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue mr3 fr" id="btnSearch" ng-click="_broadcast('marketingSmsSendCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 검색기간 --%>
                <th>
                    <div class="sb-select">
                        <wj-combo-box
                                id="periodType"
                                ng-model="periodType"
                                control="periodTypeCombo"
                                items-source="_getComboData('periodType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn">
                          <div class="sb-select w110px">
                            <wj-input-date
                                    value="periodStartDate"
                                    ng-model="periodStartDate"
                                    control="periodStartDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                          </div>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn">
                          <div class="sb-select w110px">
                            <wj-input-date
                                    value="periodEndDate"
                                    ng-model="periodEndDate"
                                    control="periodEndDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                          </div>
                        </span>
                    </div>
                </td>
                <%-- SMS기간(광고성 SMS전송) --%>
                <th>
                    <s:message code="marketingSmsSend.marketingSmsSend" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMarketingSmsGubunCombo"
                                ng-model="marketingSmsGubun"
                                items-source="_getComboData('marketingSmsGubunCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="marketingSmsGubunCombo"
                                selected-index="5">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 회원등급 --%>
                <th>
                    <s:message code="marketingSmsSend.memberClass"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="rMemberClass"
                                ng-model="membrClassCd"
                                control="memberClassCombo"
                                items-source="_getComboData('rMemberClass')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 주소 --%>
                <th>
                    <s:message code="marketingSmsSend.addr"/>
                </th>
                <td colspan="3">
                    <input type="text" id="addr" class="sb-input w100" ng-model="addr"/>
                </td>
                <%-- 회원번호 --%>
                <th>
                    <s:message code="marketingSmsSend.membrNo"/>
                </th>
                <td>
                    <input type="text" id="memberNo" class="sb-input w100" ng-model="memberNo" maxlength="10" ng-disabled="newMemberYn === true"/>
                </td>
                <%-- 회원명 --%>
                <th>
                    <s:message code="marketingSmsSend.membrNm"/>
                </th>
                <td>
                    <input type="text" id="memberNm" class="sb-input w100" ng-model="memberNm" maxlength="15" ng-disabled="newMemberYn === true"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
            <colgroup>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
                <col class="w12"/>
                <col class="w13"/>
            </colgroup>
            <tbody>
            <%-- 매장 선택 --%>
            <c:if test="${orgnFg == 'HQ'}">
                <tr>
                    <%-- 등록매장 --%>
                    <th>
                        <s:message code="marketingSmsSend.regStoreCd"/>
                    </th>
                    <td colspan="3">
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="regStore"/>
                        </jsp:include>
                    </td>
                    <%-- 사용매장 --%>
                    <th>
                        <s:message code="marketingSmsSend.useStoreCd"/>
                    </th>
                    <td colspan="3">
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="regUseStore"/>
                        </jsp:include>
                    </td>
                </tr>
            </c:if>
            <%-- 우리매장 --%>
            <c:if test="${orgnFg == 'STORE'}">
                <c:if test="${hqOfficeCd ne '00000'}">
                    <tr>
                        <%-- 우리매장 등록회원 --%>
                        <th>
                            <s:message code="marketingSmsSend.useStoreMembr"/>
                        </th>
                        <td colspan="3">
                            <input type="checkbox" id="storeMembr" ng-model="storeMembr"/>
                        </td>
                        <%-- 우리매장 방문회원 --%>
                        <th>
                            <s:message code="marketingSmsSend.useVisitStoreMembr"/>
                        </th>
                        <td colspan="3">
                            <input type="checkbox" id="visitStoreMembr" ng-model="visitStoreMembr"/>
                        </td>
                    </tr>
                </c:if>
            </c:if>
            <tr>
                <%-- 회원카드번호 --%>
                <th>
                    <s:message code="marketingSmsSend.membrCardNo"/>
                </th>
                <td colspan="3">
                    <input type="text" id="membrCardNo" class="sb-input w60 fl" ng-model="membrCardNo" maxlength="15"/>
                    <div class="sb-select w30 ml5 fl">
                        <wj-combo-box
                                id="membrCardFg"
                                ng-model="cstCardUseFg"
                                control="membrCardFgCombo"
                                items-source="_getComboData('membrCardFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <th>E-Mail</th>
                <td>
                    <input type="text" id="emailAddr" class="sb-input w100" ng-model="emailAddr" maxlength="15"/>
                </td>
                <%-- 회원단축번호 --%>
                <th>
                    <s:message code="marketingSmsSend.membrStortNo"/>
                </th>
                <td>
                    <input type="text" id="shortNo" class="sb-input w100" ng-model="shortNo" maxlength="15"/>
                </td>
            </tr>
            <tr>
                <%-- 적립매출횟수/금액 --%>
                <th>
                    <div class="sb-select">
                        <wj-combo-box
                                id="memberSaleList"
                                ng-model="memberSaleFg"
                                control="memberSaleListCombo"
                                items-source="memberSaleList"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </th>
                <td colspan="3">
                    <input type="text" id="startSaveSale" class="sb-input w100px" ng-model="startSaveSale" maxlength="15"/>
                    <span class="rg">~</span>
                    <input type="text" id="endSaveSale" class="sb-input w100px" ng-model="endSaveSale" maxlength="15"/>
                </td>
                <%-- 이메일 수신 --%>
                <th>
                    <s:message code="marketingSmsSend.emailRecv"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="emailRecvYn"
                                ng-model="emailRecvYn"
                                control="emailRecvYnCombo"
                                items-source="_getComboData('emailRecvYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- SMS 수신 --%>
                <th>
                    <s:message code="marketingSmsSend.smsRecv"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="smsRecvYn"
                                ng-model="smsRecvYn"
                                control="smsRecvYnCombo"
                                items-source="_getComboData('smsRecvYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 가용포인트 --%>
                <th>
                    <div class="sb-select">
                        <wj-combo-box
                                id="memberSaleLmemberPointListist"
                                ng-model="memberPointFg"
                                control="memberPointListCombo"
                                items-source="memberPointList"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </th>
                <td colspan="3">
                    <input type="text" id="startAvablPoint" class="sb-input w100px" ng-model="startAvablPoint" maxlength="15"/>
                    <span class="rg">~</span>
                    <input type="text" id="endAvablPoint" class="sb-input w100px" ng-model="endAvablPoint" maxlength="15"/>
                </td>
                <%-- 성별 --%>
                <th>
                    <s:message code="marketingSmsSend.gender"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="gendrFg"
                                ng-model="gendrFg"
                                control="genderCombo"
                                items-source="_getComboData('gendrFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 사용여부 --%>
                <th>
                    <s:message code="marketingSmsSend.useYn"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="useYnAll"
                                ng-model="useYn"
                                control="useYnAllCombo"
                                items-source="_getComboData('useYnAll')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 생일, 결혼기념일 날짜 --%>
                <th>
                    <div class="sb-select">
                        <wj-combo-box
                                id="anvType"
                                ng-model="anvType"
                                control="anvTypeCombo"
                                items-source="_getComboData('anvType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn">
                            <div class="sb-select w110px">
                                <wj-input-date
                                        value="anvStartDate"
                                        ng-model="anvStartDate"
                                        control="anvStartDateCombo"
                                        format="yyyy/MM/dd"
                                        min="2000-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                </wj-input-date>
                            </div>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn">
                        <div class="sb-select w110px">
                            <wj-input-date
                                    value="anvEndDate"
                                    ng-model="anvEndDate"
                                    control="anvEndDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </span>
                    </div>
                </td>
                <%-- 결혼여부 --%>
                <th>
                    <s:message code="marketingSmsSend.wedding"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="weddingYn"
                                ng-model="weddingYn"
                                control="weddingYnCombo"
                                items-source="_getComboData('weddingYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 회원명(영문) --%>
                <th>
                    <s:message code="marketingSmsSend.membrEngNm"/>
                </th>
                <td>
                    <input type="text" id="memberEngNm" class="sb-input w100" ng-model="membrEngNm" maxlength="15" ng-disabled="newMemberYn === true"/>
                </td>
            </tr>
            <tr>
                <%-- 연락처 --%>
                <th>
                    <s:message code="marketingSmsSend.tel"/>
                </th>
                <td colspan="3">
                    <input type="text" id="telNo" class="sb-input w100" ng-model="telNo" maxlength="15"/>
                </td>
                <%-- 신규회원 --%>
                <th>
                    <s:message code="marketingSmsSend.membrNew"/>
                </th>
                <td>
                    <input type="checkbox" id="newMemberYn" class="mt5" ng-model="newMemberYn"/>
                </td>
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>

        <%-- 상단 --%>
        <div class="wj-TblWrapBr w100 fl">
            <%-- 제목 --%>
            <div class="w30 fl pd10" style="height:340px; width:250px;">
                <table>
                    <colgroup>
                        <col class="w100" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 제목 --%>
                        <td>
                            <input type="text" class="sb-input-msg w100" id="marketingSmsSendTitle" ng-model="title" />
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td class="s14">
                            <label id="lblMarketingSmsSendStoreNmInfo"></label>
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <%-- 메세지내용 --%>
                        <td>
                            <textarea id="marketingSmsSendMessageContent" name="messageContent" ng-model="messageContent" style="width:100%; height:140px; overflow-x:hidden; background-color: #EAF7FF" ng-keyup="showByte()"></textarea>
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td class="s14">
                            <label id="lblMarketingSmsSendMemoInfo"></label>
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td class="s14">
                            <label id="lblMarketingSmsSendTxtByte"></label>
                            <s:message code="marketingSmsSend.byte" />
                            <label id="lblMarketingSmsSendMsgType"></label>
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td>
                            <%-- 전송자 --%>
                            <div class="sb-select dkbr fl s14">
                                <s:message code="marketingSmsSend.sendName" />
                            </div>
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
                        </td>
                    </tr>
                    <tr style="height: 10px"></tr>
                    <tr>
                        <td>
                            <%-- 전송 --%>
                            <button class="btn_skyblue fl" id="btnSmsSend" ng-click="smsSendReserve('0')">
                                <s:message code="marketingSmsSend.send" />
                            </button>
                            <%-- 예약 --%>
                            <button class="btn_skyblue ml5 fl" id="btnSmsReserve" ng-click="smsSendReserve('1')">
                                <s:message code="marketingSmsSend.reserve" />
                            </button>
                            <%-- 발신번호추가 --%>
                            <button class="btn_skyblue ml5 fl" id="btnTelNoAdd" ng-click="telNoAdd()">
                                <s:message code="marketingSmsSend.telNoAdd" />
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 수신자목록 --%>
            <div class="w70 fl pd10 bl" style="height:340px; width:calc(100% - 250px);">
                <div class="w100">
                    <div class="w70 fl">
                        <table>
                            <tr>
                                <td class="s14">
                                    <%-- 잔여금액 --%>
                                    <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                                    <span><s:message code="marketingSmsSend.smsAmt" /></span>
                                    <span class="mr5"><label id="lblMarketingSmsSendSmsAmt"></label></span>
                                    <%-- 받는사람 --%>
                                    <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                                    <span><s:message code="marketingSmsSend.receiveName" /></span>

                                    <%-- SMS건당금액 --%>
                                    <span style="display: none;"><label id="lblMarketingSmsSendSmsOneAmt"/></span>
                                    <%-- LMS건당금액 --%>
                                    <span style="display: none;"><label id="lblMarketingSmsSendLmsOneAmt"/></span>
                                    <%-- MMS건당금액 --%>
                                    <span style="display: none;"><label id="lblMarketingSmsSendMmsOneAmt"/></span>

                                    <%-- 조회건수 --%>
                                    <span style="display: none;"><label id="lblMarketingSmsSendListCnt"/></span>
                                    <%-- 전송이력시퀀스 --%>
                                    <span style="display: none;"><label id="lblMarketingSmsSendSmsSendSeq"/></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="w30 fl">
                        <div class="updownSet oh mb5">
                            <%--<button class="btn_skyblue" id="btnAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>--%>
                            <%--<button class="btn_skyblue" id="btnDel" ng-click="del()"><s:message code='cmm.del' /></button>--%>
                        </div>
                    </div>
                </div>
                <%-- 그리드 --%>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:280px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row" id="marketingSmsSendGrid" style="display: block;">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flexMarketingSmsSend"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="marketingSmsSend.telNo"/>" binding="telNo" width="100" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="marketingSmsSend.addr"/>" binding="addr" width="80" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="marketingSmsSend.lastSaleDate"/>" binding="lastSaleDate" width="80" align="center" format="date"></wj-flex-grid-column>

                                <%--저장시 필요--%>
                                <wj-flex-grid-column header="<s:message code="marketingSmsSend.rOgnFg"/>" binding="rOgnFg" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                        <!-- 조회 결과가 없을 때, msg 띄우기 -->
                        <div class="gridMsg" id="marketingSmsSendGridMsg" style="line-height: 100px; display: none;"><label id="lblMarketingSmsSendGridMsg"/></div>
                    </div>
                </div>
                <%-- //그리드 --%>
            </div>
        </div>
        <%-- //상단 --%>

        <%-- 하단 --%>
        <div class="wj-TblWrapBr w100 fl">
            <%-- 자동변환 --%>
            <div class="w30 fl pd10" style="height:400px; width:250px;">
                <div class="w100" style="overflow-x: auto; overflow-y: hidden;">
                    <table>
                        <%-- 첨부파일 --%>
                        <f:form id="marketingSmsSendSmsForm" name="marketingSmsSendSmsForm" method="post" enctype="multipart/form-data">
                            <tr>
                                <td>
                                    <input class="form-control" type="file" id="marketingSmsSendFileSms1" name="marketingSmsSendFileSms1" accept=".jpg" onchange="angular.element(this).scope().changeSmsImage(this)"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input class="form-control" type="file" id="marketingSmsSendFileSms2" name="marketingSmsSendFileSms2" accept=".jpg" onchange="angular.element(this).scope().changeSmsImage(this)"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input class="form-control" type="file" id="marketingSmsSendFileSms3" name="marketingSmsSendFileSms3" accept=".jpg" onchange="angular.element(this).scope().changeSmsImage(this)"/>
                                </td>
                            </tr>
                        </f:form>
                        <%--<tr>--%>
                            <%--<td>--%>
                                <%--&lt;%&ndash; 자동변환 &ndash;%&gt;--%>
                                <%--<span><img src="/resource/solbipos/css/img/sms/auto_str.jpg"></span>--%>
                                <%--&lt;%&ndash; 이름 &ndash;%&gt;--%>
                                <%--<span><a href="#" ng-click="addMsg('#이름#')"><img src="/resource/solbipos/css/img/sms/btn_add_name.jpg"></a></span>--%>
                                <%--&lt;%&ndash; 추가사항 &ndash;%&gt;--%>
                                <%--<span><a href="#" ng-click="addMsg('#추가사항#')"><img src="/resource/solbipos/css/img/sms/btn_add_str.jpg"></a></span>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <tr style="height: 10px"></tr>
                        <tr>
                            <td>
                                <%-- 이모티콘 --%>
                                <c:import url="/WEB-INF/view/adi/sms/marketingSmsSend/emoticon3.jsp">
                                </c:import>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <%-- 문구 --%>
            <div class="w70 fl pd10 bl" style="height:400px; width:calc(100% - 250px);">
                <div id ="divMsgGrpPage">
                    <%--subTab--%>
                    <div class="tabType1">
                        <ul>
                            <%-- 메세지그룹 탭 --%>
                            <c:forEach var="msgGrpAddCol" items="${msgGrpAddColList}">
                                <li>
                                    <a id="msgGrpAddTab${msgGrpAddCol.msgGrpCd}" href="#" ng-click="msgGrpShow('${msgGrpAddCol.msgGrpCd}')">${msgGrpAddCol.msgGrpNm}</a>
                                </li>
                            </c:forEach>
                        </ul>
                        <div style="height:340px; overflow-x: hidden; overflow-y: auto;">
                            <div id="divMarketingSmsSendMsgComment"></div>
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
        <%-- //하단 --%>

        <!-- 팝업 -->
        <%-- 광고문자 발송에 대한 의무사항 안내 --%>
        <div ng-controller="marketingSmsSendPopupCtrl">
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
        <!-- //팝업 -->
    </div>

</div>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";

    // SMS전송 - 메세지그룹
    var msgGrpAddColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="msgGrpAddCol" items="${msgGrpAddColList}">
        var msgGrpAddParam = {};
        msgGrpAddParam.msgGrpCd = "${msgGrpAddCol.msgGrpCd}";
        msgGrpAddParam.msgGrpNm = "${msgGrpAddCol.msgGrpNm}";
        msgGrpAddColList.push(msgGrpAddParam);
    </c:forEach>

    // 회원등급
    var memberClassList = ${memberClassList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/marketingSmsSend/marketingSmsSend.js?ver=20211023.01" charset="utf-8"></script>

<%-- 발신번호 사전등록 팝업 --%>
<%--<c:import url="/WEB-INF/view/adi/sms/smsSend/smsTelNoRegister.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- SMS예약 팝업 --%>
<%--<c:import url="/WEB-INF/view/adi/sms/smsSend/smsReserve.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>