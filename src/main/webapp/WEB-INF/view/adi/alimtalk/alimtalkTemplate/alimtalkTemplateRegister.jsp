<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<wj-popup control="wjAlimtalkTemplateRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:880px;height:745px;" fade-in="false" fade-out="false">
    <div ng-controller="alimtalkTemplateRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="alimtalkTemplateRegister.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="subCon2" style="height:690px; overflow-y: auto; overflow-x: hidden;">
            <div class="w100 fl b4">
                <div class="w70 fl br" style="width:600px;">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w15"/>
                            <col class="w85"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 전송유형 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.sendTypeNm"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterSendTypeCdCombo"
                                            ng-model="registerSendTypeCdCombo"
                                            items-source="_getComboData('registerSendTypeCdCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="registerSendTypeCdComboChange(s)"
                                            control="srchRegisterSendTypeCdCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterSendTypeDtlCdCombo"
                                            ng-model="registerSendTypeDtlCdCombo"
                                            items-source="_getComboData('registerSendTypeDtlCdCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchRegisterSendTypeDtlCdCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 계정/그룹 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateGrpFg"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterTemplateGrpFgCombo"
                                            ng-model="registerTemplateGrpFgCombo"
                                            items-source="_getComboData('registerTemplateGrpFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="registerTemplateGrpFgComboChange(s)"
                                            control="srchRegisterTemplateGrpFgCombo">
                                    </wj-combo-box>
                                </div>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterGroupKeyCombo"
                                            ng-model="registerGroupKeyCombo"
                                            items-source="_getComboData('registerGroupKeyCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchRegisterGroupKeyCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 템플릿코드 --%>
                            <th>
                                <s:message code="alimtalkTemplateRegister.templateCd"/>
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" id="registerTemplateCd" ng-model="templateCd" placeholder="자동채번" readonly />
                            </td>
                        </tr>
                        <tr>
                            <%-- 템플릿명 --%>
                            <th>
                                <s:message code="alimtalkTemplateRegister.templateNm"/>
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" id=registerTemplateNm" ng-model="templateNm" placeholder="자동채번" readonly />
                            </td>
                        </tr>
                        <tr>
                            <%-- 메세지유형 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateMsgType"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterTemplateMsgTypeCombo"
                                            ng-model="registerTemplateMsgTypeCombo"
                                            items-source="_getComboData('registerTemplateMsgTypeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="registerTemplateMsgTypeComboChange(s)"
                                            control="srchRegisterTemplateMsgTypeCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 템플릿 강조 유형 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateEmpsizeType"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <div class="sb-select dkbr ml5 fl">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterTemplateEmpsizeTypeCombo"
                                            ng-model="registerTemplateEmpsizeTypeCombo"
                                            items-source="_getComboData('registerTemplateEmpsizeTypeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="registerTemplateEmpsizeTypeComboChange(s)"
                                            control="srchRegisterTemplateEmpsizeTypeCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr id="trTemplateTitle" style="display: none;">
                            <%-- 템플릿 강조 제목 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateTitle"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" id="registerTemplateTitle" ng-model="templateTitle" placeholder="최대 2줄 23자(24부터 말줄임 처리, 권장하지 않음)" ng-keyup="keyInTemplateTitle()"/>
                            </td>
                        </tr>
                        <tr id="trTemplateSubtitle" style="display: none;">
                            <%-- 템플릿 강조 부제목 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateSubtitle"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" id="registerTemplateSubtitle" ng-model="templateSubtitle" placeholder="치환 변수 불가능, 최대 2줄 18자(19자부터 말줄임 처리, 권장하지 않음)" ng-keyup="keyInTemplateSubtitle()"/>
                            </td>
                        </tr>
                        <tr id="trTemplateImgNm" style="display: none;">
                            <%-- 이미지 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.imgNm"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <%--첨부파일--%>
                                <form id="regForm" name="regForm">
                                    <input type="file" id="file" name="file" accept="image/x-png" onchange="angular.element(this).scope().changeImage(this)"/>
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <%-- 템플릿내용 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateContent"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <textarea id="registerTemplateContent" name="templateContent" ng-model="templateContent" style="width:100%; height:125px; overflow-x:hidden; background-color: #EAF7FF;" ng-keyup="keyInTemplateContent()"
                                          placeholder="한/영 구분 없이 변수 및 URL포함 1,000자&#13;&#10;변수는 \#{변수} 형태로 띄어쓰기 없이 입력&#13;&#10;&#13;&#10;예) \#{이름} 님의 택배가 오늘 \#{00:00} 배달 예정입니다.&#13;&#10;&#13;&#10;대체 발송 시, 템플릿내용으로 대체 발송됩니다.&#13;&#10;대체 발송 메세지는 EUC-KR 기준으로 발송되며, 지원하지 않는 이모티콘은 대체발송에 실패합니다.&#13;&#10;템플릿 내용/부가 정보/채널 추가 안내 메세지 합 최대 1,000자">
                                </textarea>
                                <%-- #{변수} 조회 --%>
                                <a id="btnRegisterTemplateParams" href="#" class="btn_grayS ml5" ng-click="templateParams()">
                                    <s:message code="alimtalkTemplateRegister.templateParams" />
                                </a>
                                <label class="gr">
                                    * 전송유형에 따라 조회되는 \#{변수}가 다릅니다. \#{변수}명 클릭시 템플릿내용에 적용됩니다.
                                </label>
                            </td>
                        </tr>
                        <tr id="trTemplateParams" style="display: none;">
                            <%-- #{변수} 조회 --%>
                            <th>
                                <s:message code="alimtalkTemplateRegister.templateParams" />
                            </th>
                            <td>
                                <%-- 그리드 --%>
                                <div class="w100" ng-controller="alimtalkTemplateParamsCtrl">
                                    <div class="wj-gridWrap" style="height:110px; overflow-y: hidden; overflow-x: hidden;">
                                        <wj-flex-grid
                                                autoGenerateColumns="false"
                                                control="flex"
                                                initialized="initGrid(s,e)"
                                                sticky-headers="true"
                                                selection-mode="Row"
                                                items-source="data"
                                                item-formatter="_itemFormatter">

                                            <!-- define columns -->
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateParamsCd"/>" binding="templateParamsCd" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateParamsNm"/>" binding="templateParamsNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateParamsEx"/>" binding="templateParamsEx" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                        </wj-flex-grid>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr id="trTemplateExtra" style="display: none;">
                            <%-- 부가정보 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateExtra"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <textarea id="registerTemplateExtra" name="templateExtra" ng-model="templateExtra" style="width:100%; height:50px; overflow-x:hidden; background-color: #EAF7FF;" ng-keyup="keyInTemplateExtra()"
                                          placeholder="치환 변수 사용 불가, URL 포함 가능, 최대 500자">
                                </textarea>
                            </td>
                        </tr>
                        <tr id="trTemplateAd" style="display: none;">
                            <%-- 채널 추가 안내 메세지 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateAd"/>
                                    <em class="imp">*</em>
                                </div>
                            </th>
                            <td>
                                <input type="text" class="sb-input w100" id="registerTemplateAd" ng-model="templateAd" placeholder="채널 추가하고 이채널의 광고와 마케팅 메세지 받기" readonly />
                                <label class="red">
                                    * 메세지 유형이 "채널추가형" 혹은 "복합형"인 경우 "채널 추가" 버튼이 필수이며, 메세지 하단에 채널 추가 안내 메세지가 함께 노출됩니다.<br/>
                                    (발송 시점 이미 채널을 추가한 수신자에게는 채널 추가 버튼과 채널 추가 안내 메세지가 미노출됩니다.)
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <%-- 보안 템플릿 여부 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.securityFg"/>
                                    <em class="imp">*</em>
                                </div>
                                <div class="tooltip">?
                                    <span class="tooltiptext tooltip-right">
                                        * OTP등 보안 메세지일 경우 설정<br/>
                                        발신 당시의 메인 디바이스를 제외한 모든 디바이스에 메세지 텍스트를 표시하지 않음.
                                    </span>
                                </div>
                            </th>
                            <td>
                                <div class="oh sb-select dkbr">
                                    <wj-combo-box
                                            class="w110px fl"
                                            id="srchRegisterSecurityFgCombo"
                                            ng-model="registerSecurityFgCombo"
                                            items-source="_getComboData('registerSecurityFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchRegisterSecurityFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 카테고리 --%>
                            <th>
                                <div class="impWrap">
                                    <s:message code="alimtalkTemplateRegister.templateClsCd"/>
                                    <em class="imp">*</em>
                                </div>
                                <div class="tooltip">?
                                    <span class="tooltiptext tooltip-right">
                                        * 카테고리 코드 기타 선택시<br/>
                                        템플릿 검수 우선 순위 최하로 검수됩니다.
                                    </span>
                                </div>
                            </th>
                            <td>
                                <div class="oh sb-select dkbr">
                                    <div class="sb-select dkbr ml5 fl">
                                        <wj-combo-box
                                                class="w110px fl"
                                                id="srchRegisterTemplateClsCdLCombo"
                                                ng-model="registerTemplateClsCdLCombo"
                                                items-source="_getComboData('registerTemplateClsCdLCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                selected-index-changed="registerTemplateClsCdLComboChange(s)"
                                                control="srchRegisterTemplateClsCdLCombo">
                                        </wj-combo-box>
                                    </div>
                                    <div class="sb-select dkbr ml5 fl">
                                        <wj-combo-box
                                                class="w110px fl"
                                                id="srchRegisterTemplateClsCdMCombo"
                                                ng-model="registerTemplateClsCdMCombo"
                                                items-source="_getComboData('registerTemplateClsCdMCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchRegisterTemplateClsCdMCombo">
                                        </wj-combo-box>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 버튼 --%>
                            <th>
                                <s:message code="alimtalkTemplateRegister.buttons"/>
                                <div class="tooltip">?
                                    <span class="tooltiptext tooltip-right">
                                        * 버튼 이름에는 치환자를 사용할 수 없고, 버튼 링크에는 치환자를 사용할수 있습니다.<br/>
                                        웹 링크 타입의 버튼 링크 URL에는 [http://, https://]를 반드시 포함해야 하며, 500자까지 입력할 수 있습니다.<br/>
                                        예) http://www.toast.com, hrrps://\#{변수}<br/>
                                        <br/>
                                        - 배송 조회 : 메세지 내용에서 택배사와 송장번호를 파싱해 카카오 검색 배송 조회 페이지로 이동하는 [배송 조회하기] 버튼 링크를 자동으로 생성합니다.<br/>
                                        - 웹 링크 : 버튼을 클릭하면 인앱 브라우저에서 웹 페이지가 실행됩니다.<br/>
                                        - 앱 링크 : 버튼을 클릭하면 앱 커스텀 스킴이 실행됩니다.<br/>
                                        - 봇 키워드 : 버튼을 클릭하면 '버튼 이름'이 텍스트로 들어간 챗버블이 발송되며 봇 또는 상담원에게 사용자의 액션을 전달합니다.<br/>
                                        - 메세지 전달 버튼을 클릭하면 '버튼 이름 + 메세지 본문'이 텍스트로 들어간 챗버블이 발송되며, 봇 또는 상담원에게 사용자가 수신한 알림톡과 사용자의 액션을 전달합니다.<br/>
                                    </span>
                                </div>
                            </th>
                            <td>
                                <%-- 그리드 --%>
                                <div class="w100">
                                    <div class="updownSet oh">
                                        <button class="btn_skyblue" id="btnCouponAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                                    </div>
                                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                                        <wj-flex-grid
                                                autoGenerateColumns="false"
                                                control="flex"
                                                initialized="initGrid(s,e)"
                                                sticky-headers="true"
                                                selection-mode="Row"
                                                items-source="data"
                                                item-formatter="_itemFormatter">

                                            <!-- define columns -->
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsType"/>" binding="buttonsType" data-map="buttonsTypeDataMap" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsName"/>" binding="buttonsName" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsLinkMo"/>" binding="buttonsLinkMo" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsLinkPc"/>" binding="buttonsLinkPc" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsLinkLos"/>" binding="buttonsLinkLos" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.buttonsLinkAndroid"/>" binding="buttonsLinkAndroid" width="2.*" is-read-only="false" align="center"></wj-flex-grid-column>
                                            <wj-flex-grid-column header="<s:message code="cmm.del"/>" binding="del" width="1.*" is-read-only="true" align="center"></wj-flex-grid-column>

                                            <%--저장시 필요--%>
                                            <wj-flex-grid-column header="<s:message code="alimtalkTemplate.templateAdButtons"/>" binding="templateAdButtons" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                                        </wj-flex-grid>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <%-- 저장 버튼 --%>
                    <div class="tc mt10 mb10">
                        <button id="funcSave" class="btn_blue">
                            <s:message code="cmm.save" />
                        </button>
                    </div>
                </div>
                <%-- 템플릿 양식 그리기 --%>
                <div class="w30 fr pdl10" style="width:calc(100% - 600px); background-color:lightblue;">
                    <div style="height:460px; overflow-x: hidden; overflow-y: auto;">
                        <div style="float:left; text-align:center; width:205px; height:230px; padding-top:10px; padding-right:10px;">
                            <table>
                                <colgroup>
                                    <col class="w100" />
                                </colgroup>
                                <tbody>
                                    <table style="background-color:lightyellow;">
                                        <tr><td><input type="text" class="sb-input-alk-msgTop w100" value='알림톡 도착' disabled/></td></tr>
                                        <%-- 템플릿 강조 부제목 --%>
                                        <tr id="trTemplateContentForm4" style="display: none"><td><textarea id="templateContentForm4" style="width:205px; height:30px; overflow-x:hidden; background-color: lightyellow; color: #888;font-size:11px;" readonly></textarea></td></tr>
                                        <%-- 템플릿 강조 제목 --%>
                                        <tr id="trTemplateContentForm5" style="display: none" class="bb"><td><textarea id="templateContentForm5" style="width:205px; height:30px; overflow-x:hidden; background-color: lightyellow;" readonly></textarea></td></tr>
                                        <%-- 이미지 --%>
                                        <tr id="trTemplateContentForm11" style="display: none"><td><img id="imgAlimtalkImage" style="width:205px; height:102px;"/></td></tr>
                                        <%-- 템플릿내용 --%>
                                        <tr><td><textarea id="templateContentForm1" style="width:100%; height:140px; overflow-x:hidden; background-color: lightyellow;" readonly></textarea></td></tr>
                                        <%-- 부가정보 --%>
                                        <tr id="trTemplateContentForm2" style="display: none"><td><textarea id="templateContentForm2" style="width:205px; height:50px; overflow-x:hidden; background-color: lightyellow;" readonly></textarea></td></tr>
                                        <%-- 채널 추가 안내 메세지 --%>
                                        <tr id="trTemplateContentForm3" style="display: none"><td><textarea id="templateContentForm3" style="width:205px; height:30px; overflow-x:hidden; background-color: lightyellow; color: #888;" readonly>채널 추가하고 이채널의 광고와 마케팅 메세지 받기</textarea></td></tr>
                                        <%-- 버튼 --%>
                                        <tr id="trTemplateContentForm6" style="display: none"><td><input id="templateContentForm6" style="background-color:white; cursor:pointer;" type="text" class="sb-input-alk-top w100" value="" disabled/></td></tr>
                                        <tr id="trTemplateContentForm7" style="display: none"><td><input id="templateContentForm7" style="background-color:white; cursor:pointer;" type="text" class="sb-input-alk-top w100" value="" disabled/></td></tr>
                                        <tr id="trTemplateContentForm8" style="display: none"><td><input id="templateContentForm8" style="background-color:white; cursor:pointer;" type="text" class="sb-input-alk-top w100" value="" disabled/></td></tr>
                                        <tr id="trTemplateContentForm9" style="display: none"><td><input id="templateContentForm9" style="background-color:white; cursor:pointer;" type="text" class="sb-input-alk-top w100" value="" disabled/></td></tr>
                                        <tr id="trTemplateContentForm10" style="display: none"><td><input id="templateContentForm10" style="background-color:white; cursor:pointer;" type="text" class="sb-input-alk-top w100" value="" disabled/></td></tr>
                                    </table>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="w100 fl mt10">
                <div class="oh sb-select dkbr">
                    <%-- 알림톡 이미지 가이드 --%>
                    <a href="https://kakaobusiness.gitbook.io/main/ad/bizmessage/notice-friend/content-guide/image#4-3.-sketch" target="_blank">
                        <button class="btn_skyblue fr ml10"><s:message code='alimtalkTemplateRegister.templateImageManual' /></button>
                    </a>
                </div>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
    var appKey = "${appKey}";
    var secretKey = "${secretKey}";
    var apiUrl = "${apiUrl}";

    // 알림톡 템플릿등록 팝업 - 전체 \#{변수} 조회
    var alimtalkTemplateParamsColList = [];
    <%--javascript에서 사용할 전체 \#{변수} json 데이터 생성--%>
    <c:forEach var="alimtalkTemplateParamsCol" items="${alimtalkTemplateParamsColList}">
        var alimtalkTemplateParamsParam = {};
        alimtalkTemplateParamsParam.templateParamsCd = "${alimtalkTemplateParamsCol.templateParamsCd}";
        alimtalkTemplateParamsParam.templateParamsNm = "${alimtalkTemplateParamsCol.templateParamsNm}";
        alimtalkTemplateParamsParam.templateParamsEx = "${alimtalkTemplateParamsCol.templateParamsEx}";
        alimtalkTemplateParamsColList.push(alimtalkTemplateParamsParam);
    </c:forEach>
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister.js?ver=20220704.01" charset="utf-8"></script>