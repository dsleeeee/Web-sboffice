<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjBoardDetailLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:710px;height:900px;" fade-in="false" fade-out="false">

    <div ng-controller="boardDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="boardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 840px;">
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
                        <%-- 글쓰기 에디터 --%>
                        <td colspan="4">
                            <div id="summernoteDetail"></div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 첨부파일 --%>
                        <td colspan="4">
                            <tbody id="fileContent">
                            </tbody>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 댓글 그리드 --%>
            <div class="w100 mt10 mb20" id="divAnswer">
                <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        id="wjGridBoardDetailAnswerList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="boardDetail.userNm"/>" binding="userNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDetail.content"/>" binding="content" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDetail.answer.regDt"/>" binding="regDt" width="130" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDetail.edit"/>" binding="edit" width="50" is-read-only="true" align="center" visible="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDetail.save"/>" binding="save" width="50" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardDetail.del"/>" binding="del" width="50" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--팝업 조회시 필요--%>
                        <wj-flex-grid-column header="<s:message code="boardDetail.idx"/>" binding="idx" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <div>
                    <input type="text" style="border: 1px solid #d0d0d0; width: 88%; background-color: #e8e8e8;" id="srchContent" ng-model="content" placeholder="댓글을 입력해주세요"/>
                    <%-- 댓글등록 --%>
                    <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="saveAnswer()">
                        <s:message code="boardDetail.newAnswer" />
                    </button>
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
    <%-- 공개대상 --%>
    var targetFgData = ${ccu.getCommCodeExcpAll("106")};
    <%-- 승인구분 --%>
    var apprFgData = ${ccu.getCommCodeExcpAll("107")};

    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardDetail.js?ver=20200309.01" charset="utf-8"></script>

<%-- 게시판 신규등록,수정 팝업 --%>
<%--<c:import url="/WEB-INF/view/adi/board/board/boardInfo.jsp">--%>
<%--<c:param name="menuCd" value="${menuCd}"/>--%>
<%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 열람자목록 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardReadingHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>