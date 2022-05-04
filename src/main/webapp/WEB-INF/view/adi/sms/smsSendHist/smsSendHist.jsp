<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="smsSendHistView" class="subCon" style="display: none;">
    <div ng-controller="smsSendHistCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="smsSendHist.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('smsSendHistCtrl',1)" id="nxBtnSearch1">
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
                    <s:message code="smsSendHist.regDt"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <c:if test="${orgnFg eq 'HQ'}">
                <tr>
                    <%-- 제목 --%>
                    <th>
                        <s:message code="smsSendHist.subject" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchSubject" ng-model="subject" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <%-- 메세지 --%>
                    <th>
                        <s:message code="smsSendHist.msgContent" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMsgContent" ng-model="msgContent" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                </tr>
                <tr>
                    <%-- 조회구분 --%>
                    <th>
                        <s:message code="smsSendHist.srchGubun" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchGubunCombo"
                                    ng-model="gubunCombo"
                                    items-source="_getComboData('gubunCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장 --%>
                    <th>
                        <s:message code="smsSendHist.store"/>
                    </th>
                    <td>
                        <%-- 매장선택 모듈 싱글 선택 사용시 include
                             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수
                        --%>
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="smsSendHistStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
            </c:if>
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
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
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
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="smsSendHist.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.orgnCd"/>" binding="smsSendOrgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.orgnNm"/>" binding="smsSendOrgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.smsSendCount"/>" binding="smsSendCount" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.subject"/>" binding="subject" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.reserveYn"/>" binding="reserveYn" data-map="reserveYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.sendQty"/>" binding="sendQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.waitQty"/>" binding="waitQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.successQty"/>" binding="successQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.failQty"/>" binding="failQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="smsSendHist.smsSendSeq"/>" binding="smsSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="smsSendHistCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

    <%-- 엑셀다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="smsSendHistExcelCtrl">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="smsSendHist.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.orgnCd"/>" binding="smsSendOrgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.orgnNm"/>" binding="smsSendOrgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.smsSendCount"/>" binding="smsSendCount" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.msgType"/>" binding="msgType" data-map="msgTypeDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.subject"/>" binding="subject" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.msgContent"/>" binding="msgContent" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.sendDate"/>" binding="sendDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.readDate"/>" binding="readDate" width="125" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.reserveYn"/>" binding="reserveYn" data-map="reserveYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.sendQty"/>" binding="sendQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.waitQty"/>" binding="waitQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.successQty"/>" binding="successQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsSendHist.failQty"/>" binding="failQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                <%--저장시 필요--%>
                <wj-flex-grid-column header="<s:message code="smsSendHist.smsSendSeq"/>" binding="smsSendSeq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSendHist/smsSendHist.js?ver=20220504.01" charset="utf-8"></script>

<%-- 메세지 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/sendStatus/messageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수신자정보 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSendHist/addresseeDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>