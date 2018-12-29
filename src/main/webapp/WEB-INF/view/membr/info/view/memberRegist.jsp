<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="memberRegistLayer" control="memberRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:570px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="memberRegistCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="regist.membr.info" />
      <span id="memberInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <div style="height:400px; overflow-y: auto;">
        <f:form id="regForm" name="regForm" >
          <h3 class="h3_tbl"><s:message code="storeManage.basicInfo" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 회원번호 --%>
              <th><s:message code="regist.membr.no" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rMembrNo" class="sb-input w100" ng-model="member.membrNo" readonly="readonly"/>
              </td>
              <%-- 등록매장 --%>
              <th><s:message code="regist.reg.store.cd" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rRegStoreCd"
                          ng-model="member.regStoreCd"
                          control="regStoreCdCombo"
                          items-source="_getComboData('rRegStoreCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 회원명 --%>
              <th><s:message code="regist.membr.nm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rMembrNm" name="membrNm" ng-model="member.membrNm"class="sb-input w100"   maxlength="15" required>
              </td>
              <%-- 회원닉네임 --%>
              <th><s:message code="regist.membr.nicknm" /></th>
              <td>
                <input type="text" id="rMembrNicknm" name="membrNicknm" ng-model="member.membrNicknm" class="sb-input w100" maxlength="15" />
              </td>
            </tr>
            <tr>
              <%-- 전화번호 --%>
              <th><s:message code="regist.tel" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rTelNo" name="telNo" ng-model="member.telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" required />
              </td>
              <%-- 성별 --%>
              <th><s:message code="regist.gender" /><em class="imp">*</em></th>
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
              <%-- 결혼여부 --%>
              <th><s:message code="regist.wedding" /><em class="imp">*</em></th>
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
              <%-- 결혼기념일 --%>
              <th><s:message code="regist.weddingDay" /></th>
              <td>
                <div class="sb-select" >
                  <wj-input-date
                          value="rWeddingDay"
                          ng-model="member.weddingday"
                          control="weddingDayCombo"
                          format="yyyy/MM/dd"
                          min="2000-01-01"
                          max="2099-12-31"
                          initialized="_initDateBox(s)">
                  </wj-input-date>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 음력/양력 구분 --%>
              <th><s:message code="regist.lunarYn" /></th>
              <td>
                <div class="sb-input">
                  <input type="radio" name="lunarYn" ng-model="member.lunarYn" value="N" checked="checked" >
                  <label><s:message code="regist.solar" /></label>
                  <input type="radio" name="lunarYn" ng-model="member.lunarYn" value="Y" >
                  <label class="mr5"><s:message code="regist.lunar" /></label>
                </div>
              </td>
              <%-- 생일 --%>
              <th><s:message code="regist.brthd" /></th>
              <td>
                <div class="sb-select" >
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
              <th><s:message code="regist.memberClass" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rMemberClass"
                          ng-model="member.membrClassCd"
                          control="memberClassCombo"
                          items-source="_getComboData('rMemberClass')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 사용여부 --%>
              <th><s:message code="regist.useYn" /><em class="imp">*</em></th>
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
              <th><s:message code="regist.email" /></th>
              <td colspan="3">
                <input type="text" id="rEmailAddr" name="emailAddr" ng-model="member.emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
            </tr>
            <tr>
              <%-- 주소 //TODO 주소검색 추가 필요 --%>
              <th><s:message code="regist.addr" /></th>
              <td colspan="3">
                <input type="text" id="rPostNo" name="postNo" ng-model="member.postNo" class="sb-input w30" maxlength="5"/>
                <a id="btnSrchAddr" href="#" class="btn_grayS ml5" ng-click="searchAddr()">
                  <s:message code="regist.srchAddr" />
                </a>
                <br>
                <input type="text" id="rAddr" name="addr" ng-model="member.addr" class="sb-input w100" maxlength="60"/>
                <input type="text" id="rAddrDtl" name="addrDtl" ng-model="member.addrDtl" class="sb-input w100" maxlength="60"/>
              </td>
            </tr>
            <tr>
              <%-- 이메일수신 --%>
              <th><s:message code="regist.email.recv" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rEmailRecvYn"
                          ng-model="member.emailRecvYn"
                          control="emailRecvYnCombo"
                          items-source="_getComboData('rEmailRecvYn')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
              <%-- SMS 수신 --%>
              <th><s:message code="regist.sms.recv" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rSmsRecvYn"
                          ng-model="member.smsRecvYn"
                          control="smsRecvYnCombo"
                          items-source="_getComboData('rSmsRecvYn')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 비고 --%>
              <th><s:message code="regist.remark" /></th>
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
        <span><a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberRegist.js?ver=20181109.01" charset="utf-8"></script>
