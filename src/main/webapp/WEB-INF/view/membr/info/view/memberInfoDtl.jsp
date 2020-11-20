<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<wj-popup control="wjMemberInfoDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;height:440px;" fade-in="false" fade-out="false">

    <div ng-controller="memberInfoDtlCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.Dtl.info"/>
            <label id="lblMemberInfoDtl"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 440px;">
            <table class="tblType01">
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
                        {{member.membrNo}}
                    </td>
                    <%-- 회원닉네임 --%>
                    <th><s:message code="regist.membr.nicknm"/></th>
                    <td>
                        {{member.membrNicknm}}
                    </td>
                </tr>
                <tr>
                    <%-- 회원명 --%>
                    <th><s:message code="regist.membr.nm"/><em class="imp">*</em></th>
                    <td>
                        {{member.membrNm}}
                    </td>
                    <%-- 회원명영문 --%>
                    <th><s:message code="regist.membr.nm.eng"/></th>
                    <td>
                        {{member.membrEngNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 회원카드번호 --%>
                    <th><s:message code="regist.membr.card.no"/></th>
                    <td>
                        {{member.membrCardNo}}
                    </td>
                    <%-- 회원카드구분 --%>
                    <th><s:message code="regist.membr.card.yn"/></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.cstCardUseFg"
                            ng-hide="true"
                            text="_cstCardUseFg"
                            items-source="_getComboData('rMembrcardYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_cstCardUseFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 회사단축번호 --%>
                    <th><s:message code="regist.membr.stortNo"/></th>
                    <td>
                        {{member.shortNo}}
                    </td>
                    <%-- 등록매장 --%>
                    <th><s:message code="regist.reg.store.cd"/><em class="imp">*</em></th>
                    <td>
                        {{member.storeNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 연락처 --%>
                    <th><s:message code="regist.tel"/><em class="imp">*</em></th>
                    <td>
                        {{member.telNo}}
                    </td>
                    <%-- 결혼여부 --%>
                    <th><s:message code="regist.wedding"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.weddingYn"
                            ng-hide="true"
                            text="_weddingYn"
                            items-source="_getComboData('rWeddingYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_weddingYn}}
                    </td>
                </tr>
                <tr id="trWeddingDay">
                    <%-- 결혼기념일 --%>
                    <th><s:message code="regist.weddingDay"/></th>
                    <td>
                        {{member.weddingday}}
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 음력/양력 구분 --%>
                    <th><s:message code="regist.lunarYn"/></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.lunarYn"
                            ng-hide="true"
                            text="_lunarYn"
                            items-source="_getComboData('rLunarYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_lunarYn}}
                    </td>
                    <%-- 생일 --%>
                    <th id="thBirthday2" style="display: none;"><s:message code="regist.brthd"/></th>
                    <td id="thBirthday3" style="display: none;">
                        {{member.birthday}}
                    </td>
                </tr>
                <tr>
                    <%-- 회원등급 --%>
                    <th><s:message code="regist.memberClass"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.membrClassCd"
                            ng-hide="true"
                            text="_membrClassCd"
                            items-source="_getComboData('rMemberClassSelect')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_membrClassCd}}
                    </td>
                    <%-- 사용여부 --%>
                    <th><s:message code="regist.useYn"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.useYn"
                            ng-hide="true"
                            text="_useYn"
                            items-source="_getComboData('rUseYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_useYn}}
                    </td>
                </tr>
                <tr>
                    <%-- 거래처 매핑코드 --%>
                    <th><s:message code="regist.membr.mappingCd" /></th>
                    <td>
                        {{member.lnPartner}}
                    </td>
                    <th></th>
                    <td></td>
                </tr>
                <tr>
                    <%-- E-mail --%>
                    <th><s:message code="regist.email"/></th>
                    <td>
                        {{member.emailAddr}}
                    </td>
                    <%-- 성별 --%>
                    <th><s:message code="regist.gender"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.gendrFg"
                            ng-hide="true"
                            text="_gendrFg"
                            items-source="_getComboData('rGendrFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_gendrFg}}
                    </td>
                </tr>
                <tr>
                    <%-- 주소 --%>
                    <th><s:message code="regist.addr"/></th>
                    <td colspan="3">
                        {{member.postNo}}
                        <br>
                        {{member.addr}}
                        {{member.addrDtl}}
                    </td>
                </tr>
                <tr>
                    <%-- 이메일수신 --%>
                    <th><s:message code="regist.email.recv"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.emailRecvYn"
                            ng-hide="true"
                            text="_emailRecvYn"
                            items-source="_getComboData('rRecvYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_emailRecvYn}}
                    </td>
                    <%-- SMS 수신 --%>
                    <th><s:message code="regist.sms.recv"/><em class="imp">*</em></th>
                    <td>
                        <wj-combo-box
                            ng-model="member.smsRecvYn"
                            ng-hide="true"
                            text="_smsRecvYn"
                            items-source="_getComboData('rRecvYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                        </wj-combo-box>
                        {{_smsRecvYn}}
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="regist.remark"/></th>
                    <td colspan="3">
                        {{member.remark}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script>
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfoDtl.js?ver=20201120.01" charset="utf-8"></script>