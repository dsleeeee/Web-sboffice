<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="wjPosBoardDetailFullSizeLayer" id="wjPosBoardDetailFullSizeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:95%;height:95%;" fade-in="false" fade-out="false">
    <div ng-controller="posBoardDetailFullSizeCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="posBoardDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="closeFs()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" id="divFullSizeMain" style="overflow:auto;">

            <%-- 게시글내용 --%>
            <div id="summernoteDetailFs" style="overflow-x:auto; overflow-y: hidden; min-height:150px;"></div>

            <%--게시글 정보--%>
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
                        <s:message code="posBoardDetail.title"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetailFs.title}}
                    </td>
                </tr>
                <tr>
                    <%-- 작성자 --%>
                    <th>
                        <s:message code="posBoardDetail.userNm"/>
                    </th>
                    <td>
                        {{posBoardDetailFs.userNm}}
                    </td>
                    <%-- 게시일시 --%>
                    <th>
                        <s:message code="posBoardDetail.regDt"/>
                    </th>
                    <td>
                        {{posBoardDetailFs.regDt}}
                    </td>
                </tr>
                <tr>
                    <%-- 화면 사이즈 --%>
                    <th>
                        <s:message code="posBoardDetail.viewSize"/>
                    </th>
                    <td>
                        <div class="sb-select">
                            <span class="chk ml10">
                                <input type="checkbox" id="fullSizeYnPosBoardDetailFs" name="fullSizeYnChkFs" ng-model="posBoardDetailFs.fullSizeYn" disabled="true" >
                                <label for="fullSizeYnPosBoardDetailFs"><s:message code='posBoardDetail.fullSizeYn' /></label>
                            </span>
                        </div>
                    </td>
                    <%-- 게시대상 --%>
                    <th>
                        <s:message code="posBoardDetail.targetFg"/>
                    </th>
                    <td>
                        {{posBoardDetailFs.targetFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 알림여부 --%>
                    <th>
                        <s:message code="posBoardDetail.alarmYn"/>
                    </th>
                    <td>
                        <div class="sb-select">
                            <span class="chk ml10">
                                <input type="checkbox" id="noticeYnPosBoardDetailFs" name="noticeYnChkFs" ng-model="posBoardDetailFs.noticeYn" disabled="true" >
                                <label for="noticeYnPosBoardDetailFs"><s:message code='posBoardDetail.noticeYn' /></label>
                            </span>
                            <span class="chk ml10"  ng-if="posBoardDetail.noticeYn">
                                <input type="checkbox" id="emergencyYnPosBoardDetailFs" name="emergencyYnChkFs" ng-model="posBoardDetailFs.emergencyYn" disabled="true" >
                                <label for="emergencyYnPosBoardDetailFs"><s:message code='posBoardDetail.emergencyYn' /></label>
                            </span>
                            <span class="chk ml10" style="display: none;">
                                <input type="checkbox" id="smsYnPosBoardDetailFs" name="smsYnChkFs" ng-model="posBoardDetailFs.smsYn" disabled="true" >
                                <label for="smsYnPosBoardDetailFs"><s:message code='posBoardDetail.smsYn' /></label>
                            </span>
                        </div>
                    </td>
                    <%-- 공지기간 --%>
                    <th>
                        <s:message code="posBoardDetail.date"/>
                    </th>
                    <td >
                        {{posBoardDetailFs.startDate}} ~ {{posBoardDetailFs.endDate}}
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th>
                        <s:message code="posBoardDetail.remark"/>
                    </th>
                    <td colspan="3">
                        {{posBoardDetailFs.remark}}
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
                            <col width="85%" />
                            <col width="15%" />
                        </colgroup>
                        <tbody>
                        <tr style="border: 0px;">
                            <td><input type="text" style="border: 1px solid #d0d0d0;" id="srchContentFs" ng-model="contentFs" placeholder="댓글을 입력해주세요"/></td>
                            <td>
                                <%-- 댓글등록 --%>
                                <button class="btn_skyblue ml5 fr" id="btnAddRepresentFs" ng-click="saveAnswerFs()">
                                    <s:message code="posBoardDetail.newAnswer" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="btnSet tc">
                <%-- 닫기 --%>
                <span><a href="#" class="btn_blue" ng-click="closeFs()"><s:message code="cmm.close" /></a></span>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/posBoard/posBoardDetailFullSize.js?ver=20210408.08" charset="utf-8"></script>