<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjAlimtalkIdRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="alimtalkIdRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="alimtalkIdRegister.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <table class="tblType01">
                <colgroup>
                    <col class="w25"/>
                    <col class="w60"/>
                    <col class="w15"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 카카오계정ID --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="alimtalkIdRegister.plusFriendId"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td colspan="2">
                        <input type="text" class="sb-input w100" id="srchPlusFriendId" ng-model="plusFriendId" placeholder="@로 시작하는 플러스친구 검색용 ID 입력" />
                    </td>
                </tr>
                <tr>
                    <%-- 사업자 카테고리 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="alimtalkIdRegister.categoryCode"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td colspan="2">
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w130px fl"
                                    id="srchCategoryCodeLCombo"
                                    ng-model="categoryCodeLCombo"
                                    items-source="_getComboData('categoryCodeLCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="setCategoryCodeComboL(s)">
                            </wj-combo-box>
                        </div>
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w130px fl"
                                    id="srchCategoryCodeMCombo"
                                    ng-model="categoryCodeMCombo"
                                    items-source="_getComboData('categoryCodeMCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="setCategoryCodeComboM(s)">
                            </wj-combo-box>
                        </div>
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w130px fl"
                                    id="srchCategoryCodeSCombo"
                                    ng-model="categoryCodeSCombo"
                                    items-source="_getComboData('categoryCodeSCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 휴대폰번호 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="alimtalkIdRegister.phoneNo"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchPhoneNo" ng-model="phoneNo" placeholder="'-'를 제외한 휴대폰번호 입력" />
                    </td>
                    <td>
                        <%-- 인증요청 --%>
                        <button class="btn_skyblue fl" id="btnAlimtalkIdRegisterRequestSave" ng-click="registerRequestSave()"><s:message code='alimtalkIdRegister.registerRequestSave' /></button>
                    </td>
                </tr>
                <tr>
                    <%-- 인증번호 --%>
                    <th>
                        <s:message code="alimtalkIdRegister.token"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchToken" ng-model="token" />

                    </td>
                    <td style="display: none;">
                        <input type="text" class="sb-input w100" id="srchTokenYn" ng-model="tokenYn" />
                    </td>
                    <td>
                        <%-- 계정등록 --%>
                        <button class="btn_skyblue fl" id="btnAlimtalkIdRegisterTokenSave" ng-click="registerTokenSave()"><s:message code='alimtalkIdRegister.registerTokenSave' /></button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="w100 mt20">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 blue">* 카카오계정ID 등록하는 방법</p>
                    <p class="tl s14 mt5 lh15">1. 카카오를 접속하여 카카오톡 계정 로그인을 합니다.</p>
                    <p class="tl s14 mt5 lh15">2. 새 채널 만들기를 합니다.</p>
                    <p class="tl s14 mt5 lh15">3. 채널이 만들어지면 비지니스 채널를 신청합니다. 이때 3가지 서류가 필요합니다.</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;1) 사업자등록증 또는 고유번호</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;2) 재직증명서 또는 대표자 신분증</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;3) 업종별 인허가 서류</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;※ 업종별 인허가 서류는 통신판매업, 의료기기판매업, 건강기능식품 판매업을 하시는 분들</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;외에는 인허가 서류를 첨부할 필요는 없습니다.</p>
                    <p class="tl s14 mt5 lh15">4. 신청 후, 카카오측에서 확인을 하면 비지니스 채널로 변경됩니다.</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;- 부적절한 내용이 포함되었거나 문제가 있는 경우에 반려가 될 수 있습니다.</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;&nbsp;- 최대 2일 걸립니다.</p>
                </div>
            </div>
            <div class="oh sb-select dkbr">
                <%-- 카카오계정(채널) 생성 --%>
                <button class="btn_skyblue fr ml10" id="btnKakaoPlusFriendId" ng-click="kakaoPlusFriendId()"><s:message code='alimtalkIdRegister.kakaoPlusFriendId' /></button>
                <%-- 계정생성 가이드 --%>
                <button class="btn_skyblue fr ml10" id="btnPlusFriendIdManual" ng-click="plusFriendIdManual()"><s:message code='alimtalkIdRegister.plusFriendIdManual' /></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
    var userId = "${userId}";

    var groupSenderKey = "${groupSenderKey}";
    var groupSenderKeyNm = "${groupSenderKeyNm}";
    var appKey = "${appKey}";
    var secretKey = "${secretKey}";
    var apiUrl = "${apiUrl}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendType/alimtalkIdRegister.js?ver=20220414.01" charset="utf-8"></script>

<%-- 알림톡 계정생성 가이드 팝업 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendType/alimtalkManual.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>