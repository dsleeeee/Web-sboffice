<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="memberRegistLayer" control="memberRegistLayer" show-trigger="Click" hide-trigger="Click" style="width:750px;height:570px;" fade-in="false" fade-out="false">
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
                <input type="text" name="rMembrNm"  id="rMembrNm" class="sb-input w100" ng-model="member.membrNm" name="membrNm" >
                <div
                        class="error-message"
                <%--        ng-show="regForm.membrNm.$invalid && !regForm.membrNm.$pristine"--%>
                >
                  We need a valid e-mail address.
                </div>


              </td>
              <%-- 회원명(영문) --%>
              <th><s:message code="regist.membr.nm.eng" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rMembrNmEng" class="sb-input w100" ng-model="member.membrNmEng" readonly="readonly"/>
              </td>
            </tr>
            <tr>
              <%-- 회원카드번호(전화번호) --%>
              <th><s:message code="regist.membr.card.no" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rMembrCardNo" class="sb-input w100" ng-model="member.membrCardNo" maxlength="15"/>
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
                          selected-index-changed="setSysStatFgVal(s,e)">
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
              <%-- 생일 --%>
              <th><s:message code="regist.brthd" /></th>
              <td>
                <div class="sb-select" >
                  <wj-input-date
                    value="rBirthday"
                    ng-model="member.birthday"
                    control="birthdayCombo"
                    format="yyyy/MM/dd"
                    min="2000-01-01"
                    max="2099-12-31"
                    initialized="_initDateBox(s)">
                  </wj-input-date>
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
                          initialized="_initComboBox(s)"
                          selected-index-changed="setAreaCdVal(s,e)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 전화번호 --%>
              <th><s:message code="regist.tel" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="rTelNo" ng-model="member.telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
            </tr>
            <tr>
              <%-- E-mail --%>
              <th><s:message code="regist.email" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="rEmailAddr" ng-model="member.emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
            </tr>
            <tr>
              <%-- 주소 //TODO 주소검색 추가 필요 --%>
              <th><s:message code="regist.addr" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="rPostNo" ng-model="member.postNo" class="sb-input w30" maxlength="5"/>
                <a id="btnSrchAddr" href="#" class="btn_grayS ml5" ng-click="searchAddr()">
                  <s:message code="regist.srchAddr" />
                </a>
                <br>
                <input type="text" id="rAddr" ng-model="member.addr" class="sb-input w100" maxlength="60"/>
                <input type="text" id="rAddrDtl" ng-model="member.addrDtl" class="sb-input w100" maxlength="60"/>
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
                          initialized="_initComboBox(s)"
                    <%--selected-index-changed="setClsFgVal(s,e)"--%>>
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 비고 --%>
              <th><s:message code="regist.remark" /></th>
              <td colspan="3">
                <input id="rRemark" ng-model="member.remark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            <tr ng-if=""> <%-- //todo 조건추가 --%>
              <%-- 후불회원 적용매장 (등록매장이 본사일경우에만 (후불회원 적용본사일 경우만 사용)) --%>
              <th><s:message code="regist.credit.store" /></th>
              <td colspan="3">
                <a href="#" id="store" ng-click="regist" class="btn_grayS ml5"><s:message code="cmm.store.select" /></a>
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
