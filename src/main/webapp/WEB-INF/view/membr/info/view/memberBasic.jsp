<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="wj-dialog-body" id="basicView" name="basicView" class="subCon" ng-controller="memberBasicCtrl">
    <%-- body --%>
    <div style="height:520px; overflow-y: auto;">
        <f:form id="regForm" name="regForm" ng-submit="submit()">
            <%--                    <h3 class="h3_tbl"><s:message code="storeManage.basicInfo"/></h3>--%>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #ccc;">
                    <%-- 회원번호 --%>
                    <th><s:message code="regist.membr.no"/><em class="imp">*</em></th>
                    <td>
                        <input type="text" id="rMembrNo" class="sb-input w100" ng-model="member.membrNo" readonly="readonly"/>
                    </td>
                    <%-- 회원닉네임 --%>
                    <th><s:message code="regist.membr.nicknm"/></th>
                    <td>
                        <input type="text" id="rMembrNicknm" name="membrNicknm" ng-model="member.membrNicknm" class="sb-input w100" maxlength="30"/>
                    </td>
                </tr>
                <tr>
                    <%-- 회원명 --%>
                    <th><s:message code="regist.membr.nm"/></th>
                    <td>
                        <input type="text" id="rMembrNm" name="membrNm" ng-model="member.membrNm" class="sb-input w100" maxlength="30" required>
                    </td>
                    <%-- 회원명영문 --%>
                    <th><s:message code="regist.membr.nm.eng"/></th>
                    <td>
                        <input type="text" id="rMemberEngNm" name="membrEngNm" ng-model="member.membrEngNm" class="sb-input w100" maxlength="30" required>
                    </td>
                </tr>
                <tr>
                    <%-- 회원카드번호 --%>
                    <th><s:message code="regist.membr.card.no"/></th>
                    <td>
                        <input type="text" id="basicMembrCardNo" name="membrCardNo" ng-model="member.membrCardNo" class="sb-input w100" maxlength="30" required  ng-disabled="member.membrNo !== '자동채번'">
                    </td>
                    <%-- 카드사용구분 --%>
                    <th><s:message code="regist.membr.card.yn"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rMembrcardYn"
                                    ng-model="member.cstCardUseFg"
                                    control="rMembrcardYn"
                                    items-source="_getComboData('rMembrcardYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                            <%--                <input type="text" id="rMembrcardYn" name="cardYn" ng-model="member.cardYn" class="sb-input w100" maxlength="30" />--%>
                    </td>
                </tr>
                <tr>
                    <%-- 회사단축번호 --%>
                    <th><s:message code="regist.membr.stortNo"/></th>
                    <td>
                        <input type="text" id="basicStortNo" class="sb-input w100" ng-model="member.shortNo" maxlength="15"/>
                    </td>
                    <%-- 등록매장 --%>
                    <th><s:message code="regist.reg.store.cd"/><em class="imp">*</em></th>
                    <td>
                        <c:if test="${hqOfficeCd eq '00000'}">
                            <input type="text" id="basicRegStoreNm" readonly class="sb-input w100" ng-model="member.storeNm" maxlength="15"/>
                            <input type="hidden" id="basicRegStoreCd"  class="sb-input w100" ng-model="member.regStoreCd" maxlength="15"/>
                        </c:if>
                        <c:if test="${hqOfficeCd ne '00000'}">
                            <div class="sb-select">
                                <wj-combo-box
                                        id="basicRegStoreCd"
                                        ng-model="member.regStoreCd"
                                        control="basicRegStoreCdCombo"
                                        items-source="_getComboData('basicRegStoreCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </c:if>
<%--                        <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">--%>
<%--                            <jsp:param name="targetId" value="regStoreSelect"/>--%>
<%--                        </jsp:include>--%>
                    </td>
                </tr>
                <tr>
                    <%-- 연락처 --%>
                    <th><s:message code="regist.tel"/><em class="imp">*</em></th>
                    <td>
                        <input type="text" id="telNo" name="telNo" ng-model="member.telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" required/>
                    </td>
                    <%-- 핸드폰번호 --%>
                    <th style="display:none;"><s:message code="regist.phone.no"/><em class="imp">*</em></th>
                    <td style="display:none;">
                        <input type="hidden" id="phoneNo" class="sb-input w100" ng-model="member.phoneNo" maxlength="15"/>
                    </td>
                    <%-- 결혼여부 --%>
                    <th><s:message code="regist.wedding"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rWeddingYn"
                                    ng-model="member.weddingYn"
                                    control="weddingYnCombo"
                                    items-source="_getComboData('rWeddingYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="changeWeddingCombo(s,e)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr id="trWeddingDay">
                    <%-- 결혼기념일 --%>
                    <th><s:message code="regist.weddingDay"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-input-date
                                    value="rWeddingDay"
                                    ng-model="member.weddingday"
                                    control="weddingDayCombo"
                                    format="yyyy/MM/dd"
                                    min="1930-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 음력/양력 구분 --%>
                    <th><s:message code="regist.lunarYn"/></th>
                    <td>
                        <div class="sb-input">
                            <div style="float: left; padding-right: 10px;">
                            <s:message code="cmm.input"/><input type="checkbox" name="birthChk" id="birthChk" ng-click="showBirthday()">
                            </div>
                            <div id="divBirthday1" style="display: none;">
                            <input type="radio" name="lunarYn" ng-model="member.lunarYn" value="N">
                            <label><s:message code="regist.solar"/></label>
                            <input type="radio" name="lunarYn" ng-model="member.lunarYn" value="Y">
                            <label class="mr5"><s:message code="regist.lunar"/></label>
                            </div>
                        </div>
                    </td>
                    <%-- 생일 --%>
                    <th id="thBirthday2" style="display: none;"><s:message code="regist.brthd"/></th>
                    <td id="thBirthday3" style="display: none;">
                        <div class="sb-select">
                            <wj-input-date
                                    value="rBirthday"
                                    ng-model="member.birthday"
                                    control="birthdayCombo"
                                    format="yyyy/MM/dd"
                                    min="1930-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 회원등급 --%>
                    <th><s:message code="regist.memberClass"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rMemberClassSelect"
                                    ng-model="member.membrClassCd"
                                    control="rMemberClassSelectCombo"
                                    items-source="_getComboData('rMemberClassSelect')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 사용여부 --%>
                    <th><s:message code="regist.useYn"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rUseYn"
                                    ng-model="member.useYn"
                                    control="useYnCombo"
                                    items-source="_getComboData('rUseYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <%-- 본사(A0001, A0007)만 보이게 --%>
                <c:if test="${ (orgnFg eq 'HQ' and hqOfficeCd eq 'A0001') or (orgnFg eq 'HQ' and hqOfficeCd eq 'A0007') }">
                    <tr>
                        <%-- 거래처 매핑코드 --%>
                        <th><s:message code="regist.membr.mappingCd" /></th>
                        <td>
                            <input type="text" id="rCdNmPartner" class="sb-input w100" ng-model="member.lnPartner" readonly="readonly" ng-click="searchMemberMappingCd()"/>
                            <input type="hidden" id="rCdCompany" ng-model="member.cdCompany" />
                            <input type="hidden" id="rCdPartner" ng-model="member.cdPartner" />
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                </c:if>
                <tr>
                    <%-- E-mail --%>
                    <th><s:message code="regist.email"/></th>
                    <td>
                        <input type="text" id="rEmailAddr" name="emailAddr" ng-model="member.emailAddr" class="sb-input w100" maxlength="50"/>
                    </td>
                    <%-- 성별 --%>
                    <th><s:message code="regist.gender"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rGender"
                                    ng-model="member.gendrFg"
                                    control="genderCombo"
                                    items-source="_getComboData('rGendrFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 주소 //TODO 주소검색 추가 필요 --%>
                    <th><s:message code="regist.addr"/></th>
                    <td colspan="3">
                        <input type="text" id="rPostNo" name="postNo" ng-model="member.postNo" class="sb-input w80px" maxlength="5" placeholder="우편번호" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" readonly/>
                        <a id="btnSrchAddr" href="#" class="btn_grayS ml5" onclick="searchAddr()">
                            <s:message code="regist.srchAddr"/>
                        </a>
                        <input type="text" id="rLatitude" name="latitude" ng-model="member.latitude" class="sb-input w130px" placeholder="위도" style="margin:4px 0px;" readonly/>
                        <input type="text" id="rLongitude" name="longitude" ng-model="member.longitude" class="sb-input w130px" placeholder="경도" style="margin:4px 0px;" readonly/>
                        <a id="btnOpenMap" href="#" class="btn_grayS" ng-click="openMap()">
                          <s:message code="regist.membr.openMap" />
                        </a>
                        <br>
                        <input type="text" id="rAddr" name="addr" ng-model="member.addr" class="sb-input w100" placeholder="주소1" maxlength="60" style="margin:4px 0px;" readonly/>
                        <input type="text" id="rAddrDtl" name="addrDtl" ng-model="member.addrDtl" class="sb-input w100" placeholder="주소2" maxlength="60"/>
                    </td>
                </tr>
                <tr>
                    <%-- 이메일수신 --%>
                    <th><s:message code="regist.email.recv"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rEmailRecvYn"
                                    ng-model="member.emailRecvYn"
                                    control="emailRecvYnCombo"
                                    items-source="_getComboData('recvYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- SMS 수신 --%>
                    <th><s:message code="regist.sms.recv"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="rSmsRecvYn"
                                    ng-model="member.smsRecvYn"
                                    control="smsRecvYnCombo"
                                    items-source="_getComboData('recvYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <%--<tr ng-if="member.membrNm === ''">--%>
                <tr id="trMovePoint">
                    <%-- 이전포인트 --%>
                    <th><s:message code="regist.membr.move.point"/></th>
                    <td colspan="3">
                        <input type="text" id="movePoint" name="movePoint" ng-model="member.movePoint" class="sb-input w30"/><s:message code="regist.membr.move.point.txt"/>
                    </td>
                </tr>
                <%--<tr>--%>
                        <%-- 회원유치사원 --%>
<%--                    <th><s:message code="regist.membr.attract"/></th>--%>
<%--                    <td colspan="3">--%>
<%--                        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">--%>
<%--                            <jsp:param name="targetId" value="regStore"/>--%>
<%--                        </jsp:include>--%>
<%--                    </td>--%>
                <%--</tr>--%>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="regist.remark"/></th>
                    <td colspan="3">
                        <input type="text" id="rRemark" name="remark" ng-model="member.remark" class="sb-input w100" maxlength="100"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- [1246 광운대아이스링크] --%>
            <c:if test="${kwuEnvstVal == '1'}">
                <%-- 회원 추가정보 --%>
                <h3 class="h3_tbl"><s:message code="regist.kuw.member" /></h3>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 회원구분 --%>
                        <th><s:message code="regist.kuw.membrFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMembrFgCombo"
                                        ng-model="member.membrFg"
                                        items-source="_getComboData('membrFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMembrFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 구분 --%>
                        <th><s:message code="regist.kuw.privateFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPrivateFgCombo"
                                        ng-model="member.privateFg"
                                        items-source="_getComboData('privateFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchPrivateFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 외상가능 --%>
                        <th><s:message code="regist.kuw.postpaidFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPostpaidFgCombo"
                                        ng-model="member.postpaidFg"
                                        items-source="_getComboData('postpaidFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchPostpaidFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 인원수 --%>
                        <th><s:message code="regist.kuw.peopleCnt"/></th>
                        <td>
                            <input type="text" id="peopleCnt" name="srchPeopleCnt" ng-model="member.peopleCnt" class="sb-input w100" />
                        </td>
                    </tr>
                    </tbody>
                </table>
                <%-- 단체 --%>
                <h3 class="h3_tbl"><s:message code="regist.kuw.group" /></h3>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 단체구분 --%>
                        <th><s:message code="regist.kuw.groupFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchGroupFgCombo"
                                        ng-model="member.groupFg"
                                        items-source="_getComboData('groupFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchGroupFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 구분 --%>
                        <th><s:message code="regist.kuw.groupTypeFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchGroupTypeFgCombo"
                                        ng-model="member.groupTypeFg"
                                        items-source="_getComboData('groupTypeFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchGroupTypeFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 납부상태 --%>
                        <th><s:message code="regist.kuw.paymentFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPaymentFgCombo"
                                        ng-model="member.paymentFg"
                                        items-source="_getComboData('paymentFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchPaymentFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 강사,강습구분현황 --%>
                <h3 class="h3_tbl"><s:message code="regist.kuw.teacher" /></h3>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 강사명 --%>
                        <th><s:message code="regist.kuw.teacherCd"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchTeacherCdCombo"
                                        ng-model="member.teacherCd"
                                        items-source="_getComboData('teacherCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchTeacherCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 강습구분 --%>
                        <th><s:message code="regist.kuw.classFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchClassFgCombo"
                                        ng-model="member.classFg"
                                        items-source="_getComboData('classFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchClassFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 스케이트종류 --%>
                        <th><s:message code="regist.kuw.skateFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchSkateFgCombo"
                                        ng-model="member.skateFg"
                                        items-source="_getComboData('skateFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchSkateFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 이용정보 --%>
                <h3 class="h3_tbl"><s:message code="regist.kuw.use" /></h3>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 가입일자 --%>
                        <th><s:message code="regist.kuw.registerDate"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-input-date
                                        value="rRegisterDate"
                                        ng-model="member.registerDate"
                                        control="registerDateCombo"
                                        format="yyyy/MM/dd"
                                        min="1930-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                </wj-input-date>
                            </div>
                        </td>
                        <%-- 사용기간 구분 --%>
                        <th><s:message code="regist.kuw.useDateFg"/></th>
                        <td>
                            <div class="sb-select">
                                <div class="sb-select w20 fl">
                                    <div style="float: left; padding-right: 10px;">
                                        <s:message code="cmm.input"/><input type="checkbox" name="useDateChk" id="useDateChk" ng-click="showUseDate()">
                                    </div>
                                </div>
                                <div class="sb-select w80 fr" id="divUseDate" style="display: none;">
                                    <wj-combo-box
                                            id="srchUseDateFgCombo"
                                            ng-model="member.useDateFg"
                                            items-source="_getComboData('useDateFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            selected-index-changed="srchUseDateFgComboChange(s)"
                                            control="srchUseDateFgCombo">
                                    </wj-combo-box>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr id="trUseDate" style="display: none;">
                        <%-- 시작일자 --%>
                        <th><s:message code="regist.kuw.useStartDate"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-input-date
                                        value="rUseStartDate"
                                        ng-model="member.useStartDate"
                                        control="useStartDateCombo"
                                        format="yyyy/MM/dd"
                                        min="1930-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                </wj-input-date>
                            </div>
                        </td>
                        <%-- 만료일자 --%>
                        <th><s:message code="regist.kuw.useEndDate"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-input-date
                                        value="rUseEndDate"
                                        ng-model="member.useEndDate"
                                        control="useEndDateCombo"
                                        format="yyyy/MM/dd"
                                        min="1930-01-01"
                                        max="2099-12-31"
                                        initialized="_initDateBox(s)">
                                </wj-input-date>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 사용요일 --%>
                        <th><s:message code="regist.kuw.useWeek"/></th>
                        <td>
                            <input type="text" id="useWeek" name="srchUseWeek" ng-model="member.useWeek" class="sb-input w100" />
                        </td>
                        <%-- 상품명 --%>
                        <th><s:message code="regist.kuw.useProdNm"/></th>
                        <td>
                            <input type="text" id="useProdNm" name="srchUseProdNm" ng-model="member.useProdNm" class="sb-input w100" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 매출금액 --%>
                        <th><s:message code="regist.kuw.useAmt"/></th>
                        <td>
                            <input type="text" id="useAmt" name="srchUseAmt" ng-model="member.useAmt" class="sb-input w100" />
                        </td>
                        <%-- 영업비 --%>
                        <th><s:message code="regist.kuw.businessAmt"/></th>
                        <td>
                            <input type="text" id="businessAmt" name="srchBusinessAmt" ng-model="member.businessAmt" class="sb-input w100" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 강사비 --%>
                        <th><s:message code="regist.kuw.teacherAmt"/></th>
                        <td>
                            <input type="text" id="teacherAmt" name="srchTeacherAmt" ng-model="member.teacherAmt" class="sb-input w100" />
                        </td>
                        <%-- 강사인원 --%>
                        <th><s:message code="regist.kuw.teacherCnt"/></th>
                        <td>
                            <input type="text" id="teacherCnt" name="srchTeacherCnt" ng-model="member.teacherCnt" class="sb-input w100" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 잔액 --%>
                        <th><s:message code="regist.kuw.useRemainAmt"/></th>
                        <td>
                            <input type="text" id="useRemainAmt" name="srchUseRemainAmt" ng-model="member.useRemainAmt" class="sb-input w100" />
                        </td>
                        <%-- 수송비 --%>
                        <th><s:message code="regist.kuw.transportationAmt"/></th>
                        <td>
                            <input type="text" id="transportationAmt" name="srchTransportationAmt" ng-model="member.transportationAmt" class="sb-input w100" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 수송수량 --%>
                        <th><s:message code="regist.kuw.transportationCnt"/></th>
                        <td>
                            <input type="text" id="transportationCnt" name="srchTransportationCnt" ng-model="member.transportationCnt" class="sb-input w100" />
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
        </f:form>
    </div>
    <div class="btnSet">
        <%-- 저장 --%>
        <span <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
            <a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save"/></a>
        </span>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
    </div>
</div>

<script>
    var memberClassList = ${memberClassList};
    var orgnNm = "${orgnNm}";
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";

    // [1246 광운대아이스링크] 환경설정값
    var kwuEnvstVal = "${kwuEnvstVal}";
    // List 형식("" 안붙임)
    var membrFgComboList = ${membrFgComboList};
    var groupFgComboList = ${groupFgComboList};
    var teacherCdComboList = ${teacherCdComboList};
    var classFgComboList = ${classFgComboList};
    var skateFgComboList = ${skateFgComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberBasic.js?ver=20221014.01" charset="utf-8"></script>