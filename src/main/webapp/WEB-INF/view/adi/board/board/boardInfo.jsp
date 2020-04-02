<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userNm" value="${sessionScope.sessionInfo.userNm}" />

<wj-popup control="wjBoardInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:980px;" fade-in="false" fade-out="false">
    <div ng-controller="boardInfoCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="boardInfoTitle" class="ml20"></span>
            <label id="lblStatus" style="display: none"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 940px;">
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
                        <%-- 비고 --%>
                        <th>
                            <s:message code="boardInfo.remark"/>
                        </th>
                        <td colspan="3">
                            <input type="text" class="sb-input w100" id="srchRemark" ng-model="remark" maxlength="100" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 글쓰기 에디터 --%>
                        <td colspan="4">
                            <div id="summernote"></div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 첨부파일 --%>
                        <th>
                            <s:message code="boardInfo.file"/>
                        </th>
                        <td colspan="3">
                            <f:form id="boradForm" name="boradForm" method="post" enctype="multipart/form-data" >
                                <%--다중업로드--%>
                                <input multiple="multiple" type="file" id="file" name="file"/>
                            </f:form>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 첨부파일 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="boardInfo.orginlFileNm"/>" binding="orginlFileNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardInfo.del"/>" binding="del" width="50" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%-- 삭제시 필요 --%>
                        <wj-flex-grid-column header="<s:message code="boardInfo.idx"/>" binding="idx" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 공개대상 --%>
    //var targetFgData = ${ccu.getCommCodeExcpAll("106")};
    var targetFgData = [
        {"name":"전체","value":"1"},
        {"name":"특정매장","value":"2"}
    ];
    <%-- 승인구분 --%>
    //var apprFgData = ${ccu.getCommCodeExcpAll("107")};
    var apprFgData = [
        {"name":"기안","value":"1"},
        {"name":"승인","value":"2"},
        {"name":"반려","value":"3"}
    ];


    var userNm = "${userNm}";
</script>

<%-- 글쓰기 summernote 에디터 --%>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<%--<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>--%>
<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardInfo.js?ver=20200318.13" charset="utf-8"></script>