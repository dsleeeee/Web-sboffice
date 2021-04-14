<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="wjPosBoardDetailLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:520px;" fade-in="false" fade-out="false">
    <div ng-controller="posBoardDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="posBoardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="overflow:auto; height:570px;">
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
                        <s:message code="posBoardDetail.title"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetail.title}}
                    </td>
                </tr>
                <tr>
                    <%-- 작성자 --%>
                    <th>
                        <s:message code="posBoardDetail.userNm"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetail.userNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 게시일시 --%>
                    <th>
                        <s:message code="posBoardDetail.regDt"/>
                    </th>
                    <td>
                        {{posBoardDetail.regDt}}
                    </td>
                    <%-- 게시대상 --%>
                    <th>
                        <s:message code="posBoardDetail.targetFg"/>
                    </th>
                    <td>
                        {{posBoardDetail.targetFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 알림여부 --%>
                    <th>
                        <s:message code="posBoardDetail.alarmYn"/>
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="chk ml10">
                                <input type="checkbox" id="noticeYnPosBoardDetail" name="noticeYnChk" ng-model="posBoardDetail.noticeYn" disabled="true" >
                                <label for="noticeYnPosBoardDetail"><s:message code='posBoardDetail.noticeYn' /></label>
                            </span>
                            <span class="chk ml10" style="display: none;">
                                <input type="checkbox" id="smsYnPosBoardDetail" name="smsYnChk" ng-model="posBoardDetail.smsYn" disabled="true" >
                                <label for="smsYnPosBoardDetail"><s:message code='posBoardDetail.smsYn' /></label>
                            </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 공지기간 --%>
                    <th>
                        <s:message code="posBoardDetail.date"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetail.startDate}} ~ {{posBoardDetail.endDate}}
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th>
                        <s:message code="posBoardDetail.remark"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetail.remark}}
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
                            <col width="85%" />
                            <col width="15%" />
                        </colgroup>
                        <tbody>
                        <tr style="border: 0px;">
                            <td><input type="text" style="border: 1px solid #d0d0d0;" id="srchContent" ng-model="content" placeholder="댓글을 입력해주세요"/></td>
                            <td>
                                <%-- 댓글등록 --%>
                                <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="saveAnswer()">
                                    <s:message code="posBoardDetail.newAnswer" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="btnSet tc mt20 mb10">
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

<script type="text/javascript" src="/resource/solbipos/js/application/pos/posBoard/posBoardDetail.js?ver=20210408.04" charset="utf-8"></script>