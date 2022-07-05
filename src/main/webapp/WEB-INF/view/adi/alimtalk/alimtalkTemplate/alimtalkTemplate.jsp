<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="alimtalkTemplateView" class="subCon" style="display: none;">
    <div ng-controller="alimtalkTemplateCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="alimtalkTemplate.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('alimtalkTemplateCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 전송유형 --%>
                <th>
                    <s:message code="alimtalkTemplate.sendTypeNm"/>
                </th>
                <td>
                    <div class="sb-select dkbr ml5 fl">
                        <wj-combo-box
                                class="w110px fl"
                                id="srchSendTypeCdCombo"
                                ng-model="sendTypeCdCombo"
                                items-source="_getComboData('sendTypeCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="sendTypeCdComboChange(s)">
                        </wj-combo-box>
                    </div>
                    <div class="sb-select dkbr ml5 fl">
                        <wj-combo-box
                                class="w110px fl"
                                id="srchSendTypeDtlCdCombo"
                                ng-model="sendTypeDtlCdCombo"
                                items-source="_getComboData('sendTypeDtlCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 템플릿 승인구분 --%>
                <th>
                    <s:message code="alimtalkTemplate.apprFg"/>
                </th>
                <td>
                    <div class="sb-select dkbr ml5 fl">
                        <wj-combo-box
                                class="w100px fl"
                                id="srchApprFgCombo"
                                ng-model="apprFgCombo"
                                items-source="_getComboData('apprFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 템플릿코드 --%>
                <th>
                    <s:message code="alimtalkTemplate.templateCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchTemplateCd" ng-model="templateCd" />
                </td>
                <%-- 템플릿명 --%>
                <th>
                    <s:message code="alimtalkTemplate.templateNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchTemplateNm" ng-model="templateNm" />
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 알림톡 템플릿등록 --%>
            <button class="btn_skyblue ml5 fr" id="btnAlimtalkTemplateRegister" ng-click="alimtalkTemplateRegister()">
                <s:message code="alimtalkTemplate.alimtalkTemplateRegister" />
            </button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.sendTypeNm"/>" binding="sendTypeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.sendTypeDtlNm"/>" binding="sendTypeDtlNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.commonFg"/>" binding="commonFg" data-map="commonFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateCd"/>" binding="templateCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateNm"/>" binding="templateNm" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.regDt"/>" binding="regDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.sendTypeCd"/>" binding="sendTypeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkTemplate.sendTypeDtlCd"/>" binding="sendTypeDtlCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkTemplate/alimtalkTemplate.js?ver=20220704.01" charset="utf-8"></script>

<%-- 알림톡 템플릿등록 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알림톡 템플릿상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkTemplate/alimtalkTemplateDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>