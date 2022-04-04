<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="alimtalkSendTypeView" class="subCon" style="display: none;">

    <div ng-controller="alimtalkSendTypeCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="alimtalkSendType.info"/></a>
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
    </div>

    <div class="w100">
        <div class="fl" style="width:calc(100% - 335px);">
            <%-- left --%>
            <div class="wj-TblWrap mt20 mb20 w40 fl" ng-controller="alimtalkSendTypeCtrl">
                <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                    <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <%-- 전송유형 --%>
                        <s:message code="alimtalkSendType.sendType"/>
                    </span>
                    </div>
                    <div class="w100 mt10 mb20">
                        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                                    <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeCd"/>" binding="sendTypeCd" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeNm"/>" binding="sendTypeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--//left--%>
            <%--center--%>
            <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="alimtalkSendTypeDetailCtrl">
                <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
                    <div class="updownSet oh mb10">
                        <span class="fl bk lh30">
                            <%-- 전송유형 상세 --%>
                            <s:message code="alimtalkSendType.sendTypeDtl"/>
                            <label id="lblSendTypeCd"></label>
                            <label id="lblSendTypeNm"></label>
                        </span>
                        <button class="btn_skyblue" id="btnAlimtalkSendTypeDetailSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    </div>
                    <div class="w100 mt10 mb20">
                        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeDtlCd"/>" binding="sendTypeDtlCd" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.sendTypeDtlNm"/>" binding="sendTypeDtlNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="alimtalkSendType.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="70" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
            <%--//center--%>
        </div>
        <div class="fr" style="width:335px;">
            <%--right--%>
            <div class="wj-TblWrap mt20 mb20 w100 fr" ng-controller="alimtalkSendTypeTemplateCtrl">
                <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
                    <div class="updownSet oh mb10">
                        <span class="fl bk lh30">
                            <%-- 템플릿 --%>
                            <s:message code="alimtalkSendType.sendTypeTemplate"/>
                             <label id="lblSendTypeDtlCd"></label>
                             <label id="lblSendTypeDtlNm"></label>
                        </span>
                        <%-- 저장 --%>
                        <button class="btn_skyblue" id="btnAlimtalkSendTypeTemplateSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    </div>
                    <table class="tblType01" id="tabSendTypeTemplate" style="display: none">
                        <colgroup>
                            <col class="w25" />
                            <col class="w40" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 전송주기 --%>
                            <th>
                                <s:message code="alimtalkSendType.sendPeriod" />
                            </th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchSendPeriodFgCombo"
                                            ng-model="sendPeriodFg"
                                            items-source="_getComboData('sendPeriodFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchSendPeriodFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <td>
                                <input type="text" class="sb-input w100" ng-model="sendPeriod" />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <%-- 템플릿 양식 --%>
                    <div class="w100 fl pd10 bt" style="height:310px;">
                        <div id ="divTemplateGrpPage" style="display: none;">
                            <div style="display: none;">
                                <%-- 템플릿 그룹구분 --%>
                                <label id="lblTemplateGrpFg"></label>
                                <%-- 템플릿코드 --%>
                                <label id="lblTemplateCd"></label>
                            </div>
                            <div class="oh sb-select dkbr mb10">
                                <p class="tl s14 mt5 lh15 blue">[템플릿 선택] 후, [저장]을 클릭하셔야 저장됩니다.</p>
                            </div>
                            <%-- 템플릿 선택 --%>
                            <div class="updownSet oh">
                                <button class="btn_skyblue fl" id="btnAlimtalkSendTypeTemplatePopup" ng-click="templatePopup()"><s:message code='alimtalkSendType.templatePopup' /></button>
                            </div>
                            <div style="height:260px; overflow-x: hidden; overflow-y: auto;">
                                <div id="divTemplateComment"></div>
                            </div>
                        </div>
                    </div>
                    <%-- //템플릿 양식 --%>
                </div>
            </div>
            <%--//right--%>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendType/alimtalkSendType.js?ver=20220402.024" charset="utf-8"></script>

<%-- 템플릿 선택 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendType/templatePopup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알림톡 계정등록 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendType/alimtalkIdRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>