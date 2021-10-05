<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjBoardDetailFullSizeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1024px; height:768px;" fade-in="false" fade-out="false">
    <div ng-controller="boardDetailFullSizeCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="boardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="closeFs()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="overflow:auto; height:718px;">

            <%-- 게시글내용 --%>
            <div id="summernoteDetailFs" style="overflow:auto; height:400px;"></div>

            <%-- 게시글정보 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 제목 --%>
                    <th>
                        <s:message code="boardDetail.title"/>
                    </th>
                    <td colspan="3">
                        {{boardDetailFs.title}}
                    </td>
                </tr>
                <tr>
                    <%-- 작성자 --%>
                    <th>
                        <s:message code="boardDetail.userNm"/>
                    </th>
                    <td>
                        {{boardDetailFs.userNm}}
                    </td>
                    <%-- 게시일시 --%>
                    <th>
                        <s:message code="boardDetail.regDt"/>
                    </th>
                    <td>
                        {{boardDetailFs.regDt}}
                    </td>
                </tr>
                <tr>
                    <%-- 화면 사이즈 --%>
                    <th>
                        <s:message code="boardDetail.viewSize"/>
                    </th>
                    <td>
                        <div class="sb-select">
                                <span class="chk ml10">
                                    <input type="checkbox" id="fullSizeYnBoardDetailFs" name="fullSizeYnChkFs" ng-model="boardDetailFs.fullSizeYn" disabled="true">
                                    <label for="fullSizeYnBoardDetailFs"><s:message code='boardDetail.fullSizeYn'/></label>
                                </span>
                        </div>
                    </td>
                    <%-- 게시대상 --%>
                    <th>
                        <s:message code="boardDetail.targetFg"/>
                    </th>
                    <td>
                        {{boardDetailFs.targetFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 알림여부 --%>
                    <th>
                        <s:message code="boardDetail.alarmYn"/>
                    </th>
                    <td>
                        <div class="sb-select">
                            <span class="chk ml10">
                                <input type="checkbox" id="noticeYnBoardDetailFs" name="noticeYnChkFs" ng-model="boardDetailFs.noticeYn" disabled="true">
                                <label for="noticeYnBoardDetailFs"><s:message code='boardDetail.noticeYn'/></label>
                            </span>
                            <span class="chk ml10" ng-if="boardDetail.noticeYn">
                                <input type="checkbox" id="emergencyYnBoardDetailFs" name="emergencyYnChkFs" ng-model="boardDetailFs.emergencyYn" disabled="true">
                                <label for="emergencyYnBoardDetailFs"><s:message code='boardDetail.emergencyYn'/></label>
                            </span>
                            <span class="chk ml10" style="display: none;">
                                <input type="checkbox" id="smsYnBoardDetailFs" name="smsYnChkFs" ng-model="boardDetailFs.smsYn" disabled="true">
                                <label for="smsYnBoardDetailFs"><s:message code='boardDetail.smsYn'/></label>
                            </span>
                        </div>
                    </td>
                    <%-- 공지기간 --%>
                    <th>
                        <s:message code="boardDetail.date"/>
                    </th>
                    <td>
                        {{boardDetailFs.startDate}} ~ {{boardDetailFs.endDate}}
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th>
                        <s:message code="boardDetail.remark"/>
                    </th>
                    <td colspan="3">
                        {{boardDetailFs.remark}}
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 첨부파일 --%>
            <div id="fileContentFs"></div>

            <%-- 댓글 --%>
            <div class="w100 mt10 mb20" id="divAnswerFs">
                <%-- 댓글 리스트 --%>
                <div id="divCommentFs"></div>
                <%-- 댓글 입력 --%>
                <div style="padding-top:10px;">
                    <table class="tblType01" style="border: 0px;">
                        <colgroup>
                            <col width="85%"/>
                            <col width="15%"/>
                        </colgroup>
                        <tbody>
                        <tr style="border: 0px;">
                            <td><input type="text" style="border: 1px solid #d0d0d0;" id="srchContentFs" ng-model="contentFs" placeholder="댓글을 입력해주세요"/></td>
                            <td>
                                <%-- 댓글등록 --%>
                                <button class="btn_skyblue m15 fr" id="btnAddRepresentFs" ng-click="saveAnswerFs()">
                                    <s:message code="boardDetail.newAnswer"/>
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <%-- 버튼 --%>
            <div class="btnSet tc">
                <%-- 열람자목록 --%>
                <span><a href="#" class="btn_blue" ng-click="readingHistFs()"><s:message code="boardDetail.readingHist"/></a></span>
                <%-- 삭제 --%>
                <span id="delButtonFs"><a href="#" class="btn_blue" ng-click="delFs()"><s:message code="boardDetail.del"/></a></span>
                <%-- 수정 --%>
                <span id="modifyButtonFs"><a href="#" class="btn_blue" ng-click="modifyFs()"><s:message code="boardDetail.modify"/></a></span>
                <%-- 닫기 --%>
                <span><a href="#" class="btn_blue" ng-click="closeFs()"><s:message code="cmm.close"/></a></span>
            </div>

        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardDetailFullSize.js?ver=20210930.04" charset="utf-8"></script>
