<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjMobileBoardDetailLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:100%;height:600px;" fade-in="false" fade-out="false">
    <div ng-controller="mobileBoardDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mobile.boardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="overflow:auto; height:560px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w25"/>
                    <col class="w25"/>
                    <col class="w25"/>
                    <col class="w25"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 제목 --%>
                    <th>
                        <s:message code="mobile.boardDetail.title"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.title}}
                    </td>
                </tr>
                <tr>
                    <%-- 작성자 --%>
                    <th>
                        <s:message code="mobile.boardDetail.userNm"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.userNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 게시일시 --%>
                    <th>
                        <s:message code="mobile.boardDetail.regDt"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.regDt}}
                    </td>
                </tr>
                <tr>
                    <%-- 게시대상 --%>
                    <th>
                        <s:message code="mobile.boardDetail.targetFg"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.targetFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 알림여부 --%>
                    <th>
                        <s:message code="mobile.boardDetail.alarmYn"/>
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="chk ml10">
                                <input type="checkbox" id="noticeYnBoardDetail" name="noticeYnChk" ng-model="boardDetail.noticeYn" disabled="true" >
                                <label for="noticeYnBoardDetail"><s:message code='mobile.boardDetail.noticeYn' /></label>
                            </span>
                            <span class="chk ml10" ng-if="boardDetail.noticeYn">
                                <input type="checkbox" id="emergencyYnBoardDetail" name="emergencyYnChk" ng-model="boardDetail.emergencyYn" disabled="true" >
                                <label for="emergencyYnBoardDetail"><s:message code='mobile.boardDetail.emergencyYn' /></label>
                            </span>
                            <span class="chk ml10" style="display: none;">
                                <input type="checkbox" id="smsYnBoardDetail" name="smsYnChk" ng-model="boardDetail.smsYn" disabled="true" >
                                <label for="smsYnBoardDetail"><s:message code='mobile.boardDetail.smsYn' /></label>
                            </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 공지기간 --%>
                    <th>
                        <s:message code="mobile.boardDetail.date"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.startDate}} ~ {{boardDetail.endDate}}
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th>
                        <s:message code="mobile.boardDetail.remark"/>
                    </th>
                    <td colspan="3">
                        {{boardDetail.remark}}
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 게시글내용 --%>
            <div id="summernoteDetail" style="overflow:auto; height:150px; padding:10px;"></div>

            <%-- 첨부파일 --%>
            <div id="fileContent"></div>

            <%-- 댓글 --%>
            <div class="w100 mt10 mb20" id="divAnswer">
                <%-- 댓글 리스트 --%>
                <div id="divComment"></div>
                <%-- 댓글 입력 --%>
                <div style="padding-top:20px;">
                    <table class="tblType01" style="border: 0px;">
                        <colgroup>
                            <col width="70%" />
                            <col width="30%" />
                        </colgroup>
                        <tbody>
                        <tr style="border: 0px;">
                            <td><input type="text" style="border: 1px solid #d0d0d0;" id="srchContent" ng-model="content" placeholder="댓글을 입력해주세요"/></td>
                            <td>
                                <%-- 댓글등록 --%>
                                <button class="btn_skyblue fr" id="btnAddRepresent" ng-click="saveAnswer()">
                                    <s:message code="mobile.boardDetail.newAnswer" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="btnSet tc mt5 mb5">
                <%-- 닫기 --%>
                <span><a href="#" class="btn_blue" ng-click="close()"><s:message code="mobile.cmm.close" /></a></span>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/adi/board/board/mobileBoardDetail.js?ver=20210531.02" charset="utf-8"></script>
