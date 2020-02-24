<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjBoardInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;height:570px;" fade-in="false" fade-out="false">
    <div ng-controller="boardInfoCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="boardInfoTitle" class="ml20"></span>
            <label id="lblStatus" style="display: none"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 제목 --%>
                        <th>
                            <s:message code="boardInfo.title"/>
                        </th>
                        <td colspan="3">
                            <input type="text" class="sb-input w100" id="srchTitle" ng-model="title" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 작성자 --%>
                        <th>
                            <s:message code="boardInfo.userNm"/>
                        </th>
                        <td colspan="3">
                            <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 승인구분 --%>
                        <th>
                            <s:message code="boardInfo.apprFg"/>
                        </th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn w150px">
                                    <wj-combo-box
                                        id="srchApprFg"
                                        ng-model="apprFg"
                                        items-source="_getComboData('apprFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                    </wj-combo-box>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 공개대상 --%>
                        <th>
                            <s:message code="boardInfo.targetFg"/>
                        </th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                    id="srchTargetFg"
                                    ng-model="targetFg"
                                    items-source="_getComboData('targetFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td colspan="2">
                            <div ng-if="targetFg == '2'">
                                <%-- 매장선택 모듈 싱글 선택 사용시 include
                                    param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                                 displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                                 modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                                 closeFunc - 팝업 닫기시 호출할 함수
                                --%>
                                <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                                    <jsp:param name="targetId" value="boardInfoStore"/>
                                </jsp:include>
                                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 알림여부 --%>
                        <th>
                            <s:message code="boardInfo.alarmYn"/>
                        </th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="chk ml10">
                                    <input type="checkbox" id="noticeYn" name="noticeYnChk" ng-model="noticeYn" >
                                    <label for="noticeYn"><s:message code='boardInfo.noticeYn' /></label>
                               </span>
                                <span class="chk ml10">
                                    <input type="checkbox" id="smsYn" name="smsYnChk" ng-model="smsYn" >
                                    <label for="smsYn"><s:message code='boardInfo.smsYn' /></label>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 공지기간 --%>
                        <th>
                            <s:message code="boardInfo.date"/>
                        </th>
                        <td colspan="3">
                            <div class="sb-select" >
                                <span class="txtIn w120px">
                                    <wj-input-date
                                        value="startDate"
                                        ng-model="startDate"
                                        control="startDateCombo"
                                        format="yyyy/MM/dd"
                                        min="2000-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                                <span class="rg">~</span>
                                <span class="txtIn w120px">
                                    <wj-input-date
                                        value="endDate"
                                        ng-model="endDate"
                                        control="endDateCombo"
                                        format="yyyy/MM/dd"
                                        min="2000-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 첨부파일 --%>
                        <th>
                            <s:message code="boardInfo.file"/>
                        </th>
                        <td colspan="3">
                            <div>
                                <input type="text" style="border: 1px solid #d0d0d0; width: 85%;" id="srchFileNm" ng-model="fileNm" readonly="true"/>
                                <%-- 파일찾기 --%>
                                <button class="btn_skyblue ml5 fr" id="btnAddFile" ng-click="findFile()">
                                    <s:message code="boardDetail.findFile" />
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>

        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 공개대상 --%>
    var targetFgData = ${ccu.getCommCodeExcpAll("106")};
    <%-- 승인구분 --%>
    var apprFgData = ${ccu.getCommCodeExcpAll("107")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardInfo.js?ver=202002204.01" charset="utf-8"></script>