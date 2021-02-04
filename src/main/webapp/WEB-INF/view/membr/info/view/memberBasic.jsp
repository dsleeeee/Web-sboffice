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
    <div style="height:400px; overflow-y: auto;">
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
                    <th><s:message code="regist.membr.nm"/><em class="imp">*</em></th>
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
                            <s:message code="vendrInstock.procFg0"/><input type="checkbox" name="birthChk" id="birthChk" ng-click="showBirthday()">
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
        </f:form>
    </div>
    <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save"/></a></span>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
    </div>
</div>

<script>
    var memberClassList = ${memberClassList};
    var orgnNm = "${orgnNm}";
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberBasic.js?ver=20201112.01" charset="utf-8"></script>