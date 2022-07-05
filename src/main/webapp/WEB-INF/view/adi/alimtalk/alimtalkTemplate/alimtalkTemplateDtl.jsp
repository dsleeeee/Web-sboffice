<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjAlimtalkTemplateDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:620px;height:630px;" fade-in="false" fade-out="false">
    <div ng-controller="alimtalkTemplateDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="alimtalkTemplateDtl.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="subCon2" style="height:570px; overflow-y: auto; overflow-x: hidden;">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w85"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 전송유형 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.sendTypeNm"/>
                    </th>
                    <td>
                        [ {{alimtalkTemplateDtl.sendTypeNm}} ] - [ {{alimtalkTemplateDtl.sendTypeDtlNm}} ]
                    </td>
                </tr>
                <tr>
                    <%-- 계정/그룹 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateGrpFg"/>
                    </th>
                    <td>
                        <wj-combo-box
                                ng-model="alimtalkTemplateDtl.templateGrpFg"
                                ng-hide="true"
                                text="_templateGrpFg"
                                items-source="_getComboData('dtlTemplateGrpFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false">
                        </wj-combo-box>
                        [ {{_templateGrpFg}} ] - [ {{alimtalkTemplateDtl.groupKeyNm}} ]
                    </td>
                </tr>
                <tr>
                    <%-- 템플릿코드 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateCd"/>
                    </th>
                    <td>
                        {{alimtalkTemplateDtl.templateCd}}
                    </td>
                </tr>
                <tr>
                    <%-- 템플릿명 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateNm"/>
                    </th>
                    <td>
                        {{alimtalkTemplateDtl.templateNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 메세지유형 --%>
                    <th>
                        <s:message code="alimtalkTemplateRegister.templateMsgType"/>
                    </th>
                    <td>
                        <wj-combo-box
                            ng-model="alimtalkTemplateDtl.templateMsgType"
                            ng-hide="true"
                            text="_templateMsgType"
                            items-source="_getComboData('dtlTemplateMsgTypeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_templateMsgType}}
                    </td>
                </tr>
                <tr>
                    <%-- 템플릿 강조 유형 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateEmpsizeType"/>
                    </th>
                    <td>
                        <wj-combo-box
                                ng-model="alimtalkTemplateDtl.templateEmpsizeType"
                                ng-hide="true"
                                text="_templateEmpsizeType"
                                items-source="_getComboData('dtlTemplateEmpsizeTypeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false">
                        </wj-combo-box>
                        {{_templateEmpsizeType}}
                    </td>
                </tr>
                <tr id="trDtlTemplateTitle" style="display: none;">
                    <%-- 템플릿 강조 제목 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateTitle"/>
                    </th>
                    <td>
                        {{alimtalkTemplateDtl.templateTitle}}
                    </td>
                </tr>
                <tr id="trDtlTemplateSubtitle" style="display: none;">
                    <%-- 템플릿 강조 부제목 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateSubtitle"/>
                    </th>
                    <td>
                        {{alimtalkTemplateDtl.templateSubtitle}}
                    </td>
                </tr>
                <tr id="trDtlTemplateImgNm" style="display: none;">
                    <%-- 이미지 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.imgNm"/>
                    </th>
                    <td>
                        <img id="dtlImgAlimtalkImage" style="width:205px; height:102px;"/>
                    </td>
                </tr>
                <tr>
                    <%-- 템플릿내용 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateContent"/>
                    </th>
                    <td>
                        <textarea id="dtlTemplateContent" name="templateContent" ng-model="templateContent" style="width:100%; height:125px; overflow-x:hidden; background-color: #EAF7FF;" readonly>
                        </textarea>
                    </td>
                </tr>
                <tr id="trDtlTemplateExtra" style="display: none;">
                    <%-- 부가정보 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateExtra"/>
                    </th>
                    <td>
                        <textarea id="dtlTemplateExtra" name="templateExtra" ng-model="templateExtra" style="width:100%; height:50px; overflow-x:hidden; background-color: #EAF7FF;" readonly>
                        </textarea>
                    </td>
                </tr>
                <tr id="trDtlTemplateAd" style="display: none;">
                    <%-- 채널 추가 안내 메세지 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateAd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="dtlTemplateAd" ng-model="templateAd" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 보안 템플릿 여부 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.securityFg"/>
                    </th>
                    <td>
                        <wj-combo-box
                                ng-model="alimtalkTemplateDtl.securityFg"
                                ng-hide="true"
                                text="_securityFg"
                                items-source="_getComboData('dtlSecurityFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false">
                        </wj-combo-box>
                        {{_securityFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 카테고리 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.templateClsCd"/>
                    </th>
                    <td>
                        [ {{alimtalkTemplateDtl.templateClsNmL}} ] - [ {{alimtalkTemplateDtl.templateClsNmM}} ]
                    </td>
                </tr>
                <tr>
                    <%-- 버튼 --%>
                    <th>
                        <s:message code="alimtalkTemplateDtl.buttons"/>
                    </th>
                    <td>
                        <%-- 그리드 --%>
                        <div class="w100">
                            <div class="wj-gridWrap" style="height:165px; overflow-y: hidden; overflow-x: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsType"/>" binding="buttonsType" data-map="buttonsTypeDataMap" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsName"/>" binding="buttonsName" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsLinkMo"/>" binding="buttonsLinkMo" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsLinkPc"/>" binding="buttonsLinkPc" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsLinkLos"/>" binding="buttonsLinkLos" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="alimtalkTemplateDtl.buttonsLinkAndroid"/>" binding="buttonsLinkAndroid" width="2.*" is-read-only="true" align="center"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkTemplate/alimtalkTemplateDtl.js?ver=20220704.01" charset="utf-8"></script>