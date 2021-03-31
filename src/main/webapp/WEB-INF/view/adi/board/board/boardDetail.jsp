<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjBoardDetailLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:750px;" fade-in="false" fade-out="false">

    <div ng-controller="boardDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="boardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="overflow:auto; height:700px;">
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
                            <s:message code="boardDetail.title"/>
                        </th>
                        <td colspan="3">
                            {{boardDetail.title}}
                        </td>
                    </tr>
                    <tr>
                        <%-- 작성자 --%>
                        <th>
                            <s:message code="boardDetail.userNm"/>
                        </th>
                        <td colspan="3">
                            {{boardDetail.userNm}}
                        </td>
                    </tr>
                    <tr>
                        <%-- 게시일시 --%>
                        <th>
                            <s:message code="boardDetail.regDt"/>
                        </th>
                        <td>
                            {{boardDetail.regDt}}
                        </td>
                        <%-- 게시대상 --%>
                        <th>
                            <s:message code="boardDetail.targetFg"/>
                        </th>
                        <td>
                            {{boardDetail.targetFg}}
                        </td>
                    </tr>
                    <tr>
                        <%-- 알림여부 --%>
                        <th>
                            <s:message code="boardDetail.alarmYn"/>
                        </th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="chk ml10">
                                    <input type="checkbox" id="noticeYnBoardDetail" name="noticeYnChk" ng-model="boardDetail.noticeYn" disabled="true" >
                                    <label for="noticeYnBoardDetail"><s:message code='boardDetail.noticeYn' /></label>
                                </span>
                                <span class="chk ml10">
                                    <input type="checkbox" id="smsYnBoardDetail" name="smsYnChk" ng-model="boardDetail.smsYn" disabled="true" >
                                    <label for="smsYnBoardDetail"><s:message code='boardDetail.smsYn' /></label>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 공지기간 --%>
                        <th>
                            <s:message code="boardDetail.date"/>
                        </th>
                        <td colspan="3">
                            {{boardDetail.startDate}} ~ {{boardDetail.endDate}}
                        </td>
                    </tr>
                    <tr>
                        <%-- 비고 --%>
                        <th>
                            <s:message code="boardDetail.remark"/>
                        </th>
                        <td colspan="3">
                            {{boardDetail.remark}}
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 게시글내용 --%>
            <div id="summernoteDetail" style="overflow:auto; height:250px; padding:10px;"></div>

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
                            <col width="85%" />
                            <col width="15%" />
                        </colgroup>
                        <tbody>
                            <tr style="border: 0px;">
                                <td><input type="text" style="border: 1px solid #d0d0d0;" id="srchContent" ng-model="content" placeholder="댓글을 입력해주세요"/></td>
                                <td>
                                    <%-- 댓글등록 --%>
                                    <button class="btn_skyblue m15 fr" id="btnAddRepresent" ng-click="saveAnswer()">
                                        <s:message code="boardDetail.newAnswer" />
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="btnSet tc mt20 mb10">
                <%-- 열람자목록 --%>
                <span><a href="#" class="btn_blue pd20" ng-click="readingHist()"><s:message code="boardDetail.readingHist" /></a></span>
                <%-- 삭제 --%>
                <span id="delButton"><a href="#" class="btn_blue pd20" ng-click="del()"><s:message code="boardDetail.del" /></a></span>
                <%-- 수정 --%>
                <span id="modifyButton"><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="boardDetail.modify" /></a></span>
                <%-- 닫기 --%>
                <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardDetail.js?ver=20210331.01" charset="utf-8"></script>

<%-- 열람자목록 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardReadingHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>