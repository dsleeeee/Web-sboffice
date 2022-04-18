<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="alimtalkSendTypeCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('alimtalkSendTypeCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
                <%-- 계정등록 --%>
                <button class="btn_blue mr5 fl" id="btnAlimtalkIdRegister" ng-click="alimtalkIdRegister()">
                    <s:message code="alimtalkSendType.alimtalkIdRegister" />
                </button>
            </div>
        </div>
        <div class="w100 fl mt10" style="height:25px;">
            <table>
                <tr>
                    <td class="s14">
                        <%-- 잔여금액 --%>
                        <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                        <span><s:message code="alimtalkSendType.smsAmt" /></span>
                        <span class="mr5"><label id="lblAlimtalkSendTypeSmsAmt"></label></span>
                        <%-- 잔여금액이 없는 경우 알림톡이 전송되지 않습니다. --%>
                        <span><img src="/resource/solbipos/css/img/sms/s_icon.jpg"></span>
                        <span><s:message code="alimtalkSendType.smsAmtMemo" /></span>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="w100 fl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:570px; overflow-y: hidden;">
            <%-- left --%>
            <div class="fl pdr10 br" style="width:260px;">
                <%-- left 상단 --%>
                <div ng-controller="alimtalkSendTypeCtrl">
                    <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <%-- 전송유형 --%>
                        <s:message code="alimtalkSendType.sendType"/>
                    </span>
                    </div>
                    <div class="w100 mt10 mb20">
                        <div class="wj-gridWrap" style="height:210px; overflow-y: hidden; overflow-x: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeCd"/>" binding="sendTypeCd" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeNm"/>" binding="sendTypeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
                <%-- //left 상단 --%>
                <%-- left 하단 --%>
                <div class="bt" ng-controller="alimtalkSendTypeDetailCtrl">
                    <div class="updownSet oh mb10 mt20">
                    <span class="fl bk lh30">
                        <%-- 전송상세 --%>
                        <s:message code="alimtalkSendType.sendTypeDtl"/>
                        <label id="lblSendTypeCd"></label>
                        <label id="lblSendTypeNm"></label>
                    </span>
                        <button class="btn_skyblue" id="btnAlimtalkSendTypeDetailSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    </div>
                    <div class="w100 mt10 mb20">
                        <div class="wj-gridWrap" style="height:210px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeDtlCd"/>" binding="sendTypeDtlCd" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeDtlNm"/>" binding="sendTypeDtlNm" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="70" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
                <%-- //left 하단 --%>
            </div>
            <%-- //left --%>
            <%-- center --%>
            <div class="fl pdl10 pdr10 br" style="width:215px;">
                <div ng-controller="alimtalkSendTypeTemplateCtrl">
                    <div class="updownSet oh mb10">
                        <span class="fl bk lh30">
                            <%-- 템플릿 --%>
                             <label id="lblSendTypeDtlCd"></label>
                             <label id="lblSendTypeDtlNm"></label>
                        </span>
                        <%-- 저장 --%>
                        <button class="btn_skyblue" id="btnAlimtalkSendTypeTemplateSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    </div>
                    <table class="tblType01" id="tabSendTypeTemplate" style="display: none">
                        <colgroup>
                            <col class="w40" />
                            <col class="w60" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 전송주기 --%>
                            <th>
                                <s:message code="alimtalkSendType.sendPeriod" />
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" ng-model="sendPeriod" />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <%-- 템플릿 양식 --%>
                    <div class="w100 fl" style="height:510px;">
                        <div id ="divTemplateGrpPage" style="display: none;">
                            <div style="display: none;">
                                <%-- 템플릿 그룹구분 --%>
                                <label id="lblTemplateGrpFg"></label>
                                <%-- 템플릿코드 --%>
                                <label id="lblTemplateCd"></label>
                            </div>
                            <div style="height:460px; overflow-x: hidden; overflow-y: auto;">
                                <div id="divTemplateComment"></div>
                            </div>
                        </div>
                    </div>
                    <%-- //템플릿 양식 --%>
                </div>
            </div>
            <%-- //center --%>
            <%-- right --%>
            <div class="fr pdl10" style="width:calc(100% - 475px);">
                <div ng-controller="templateListCtrl">
                    <%-- 템플릿 양식 --%>
                    <div class="w100 fl" style="height:570px;">
                        <div style="height:550px; overflow-x: hidden; overflow-y: auto;">
                            <div id="divTemplateCommentList"></div>
                        </div>
                    </div>
                    <%-- //템플릿 양식 --%>
                </div>
            </div>
            <%-- //right --%>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};

    // 템플릿 치환값 리스트
    var templateChangeKeyColList = [];
    <%--javascript에서 사용할 템플릿 치환값 json 데이터 생성--%>
    <c:forEach var="templateChangeKeyCol" items="${templateChangeKeyColList}">
        var templateChangeKeyParam = {};
        templateChangeKeyParam.nmcodeNm = "${templateChangeKeyCol.nmcodeNm}";
        templateChangeKeyParam.nmcodeItem2 = "${templateChangeKeyCol.nmcodeItem2}";
        templateChangeKeyColList.push(templateChangeKeyParam);
    </c:forEach>
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendType/alimtalkSendType.js?ver=20220418.02" charset="utf-8"></script>

<%-- 알림톡 계정등록 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendType/alimtalkIdRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>