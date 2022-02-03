<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="msgManageView" class="subCon" style="display: none;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="msgManage.info"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('msgManageCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <%-- left --%>
    <div class="wj-TblWrap mt20 mb20 w50 fl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:500px;">
            <div ng-controller="msgManageCtrl">
                <div class="updownSet oh mb10">
                    <%-- 메세지그룹 --%>
                    <span class="fl bk lh30">
                        <s:message code="msgManage.msgGroup"/>
                    </span>
                    <%-- 본사일때만 --%>
                    <c:if test="${orgnFg == 'HQ'}">
                         <%--매장적용--%>
                        <button class="btn_skyblue" id="btnStoreApply" ng-click="storeApply()"><s:message code="msgManage.storeApply" /></button>
                    </c:if>
                    <button class="btn_skyblue" id="btnAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    <button class="btn_skyblue" id="btnDel" ng-click="del()"><s:message code='cmm.del' /></button>
                </div>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="msgManage.msgGrpCd"/>" binding="msgGrpCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="msgManage.msgGrpNm"/>" binding="msgGrpNm" width="100" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="msgManage.msgGrpCnt"/>" binding="msgGrpCnt" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
            <%-- 문구 --%>
            <div ng-controller="msgManageDtlCtrl">
                <div class="w100 fl bt bb" style="height:260px; overflow-x: hidden; overflow-y: auto;">
                    <div id="divMsgManageMsgComment"></div>
                </div>
            </div>
        </div>
    </div>
    <%--left--%>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="msgManageDtlCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:500px; overflow-y: hidden;">
            <%-- 메세지관리 --%>
            <s:message code="msgManage.info"/>
            <%-- 그룹명 --%>
            <label id="lblMsgGrpNmInfo"></label>
            <label id="lblMsgGrpNm"></label>
            <%-- 그룹코드 --%>
            <label id="lblMsgGrpCd" style="display: none;"></label>
            <%-- 메세지 수정시 SEQ_NO --%>
            <label id="lblSeqNo" style="display: none;"></label>
            <div class="w100">
                <%-- 메세지 --%>
                <div class="w40 fl pdt10 pdr10" style="height:320px;">
                    <table>
                        <colgroup>
                            <col class="w100" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>
                                <input type="text" class="sb-input-msg w100" id="msgManageTitle" ng-model="title" />
                            </td>
                        </tr>
                        <tr style="height: 10px"></tr>
                        <tr>
                            <td>
                                <textarea id="msgManageMessageContent" name="messageContent" ng-model="messageContent" style="width:100%; height:160px; overflow-x:hidden; background-color: #EAF7FF" ng-keyup="showByte()"></textarea>
                            </td>
                        </tr>
                        <tr style="height: 10px"></tr>
                        <tr>
                            <td>
                                <label id="lblMsgManageTxtByte"></label>
                                <s:message code="msgManage.byte" />
                                <label id="lblMsgManageMsgType"></label>
                            </td>
                        </tr>
                        <tr style="height: 10px"></tr>
                        <tr>
                            <td>
                                <%-- 신규 --%>
                                <button class="btn_skyblue fl" id="btnMsgNew" ng-click="msgNew()">
                                    <s:message code="msgManage.new" />
                                </button>
                                <%-- 저장 --%>
                                <button class="btn_skyblue ml5 fl" id="btnMsgSave" ng-click="msgSave()">
                                    <s:message code="cmm.save" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <%-- 자동변환 --%>
                <div class="w60 fl pdt10 pdl10 bl" style="height:320px;">
                    <div class="w100" style="overflow-x: auto; overflow-y: hidden;">
                        <table>
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
                            <%--<tr style="height: 10px"></tr>--%>
                            <tr>
                                <td>
                                    <%-- 이모티콘 --%>
                                    <c:import url="/WEB-INF/view/adi/sms/msgManage/emoticon2.jsp">
                                    </c:import>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/msgManage/msgManage.js?ver=20211006.03" charset="utf-8"></script>

<%-- 메세지관리 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/msgManage/msgManageStoreRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>