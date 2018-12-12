<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="memberInfoDetailLayer" show-trigger="Click" hide-trigger="Click" style="width:750px;height:570px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="memberInfoDetailCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="regist.membr.info" />
      <span id="memberInfoDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <div style="height:400px; overflow-y: auto;">
        <f:form id="viewForm" name="viewForm" >
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
              <th><s:message code="regist.membr.no" /></th>
              <td>{{member.membrNo}}</td>
                <%-- 등록매장 --%>
              <th><s:message code="regist.reg.store.cd" /></th>
              <td>
                <wj-combo-box
                        ng-model="member.regStoreCd"
                        ng-hide="true"
                        text="_regStore"
                        items-source="_getComboData('rRegStoreCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_regStore}}
              </td>
            </tr>
            <tr>
                <%-- 회원명 --%>
              <th><s:message code="regist.membr.nm" /></th>
              <td>{{member.membrNm}}</td>
                <%-- 회원닉네임 --%>
              <th><s:message code="regist.membr.nicknm" /></th>
              <td>{{member.membrNicknm}}</td>
            </tr>
            <tr>
                <%-- 전화번호 --%>
              <th><s:message code="regist.tel" /></th>
              <td>{{member.telNo}}</td>
                <%-- 성별 --%>
              <th><s:message code="regist.gender" /></th>
              <td>
                <wj-combo-box
                        ng-model="member.gendrFg"
                        ng-hide="true"
                        text="_gendr"
                        items-source="_getComboData('rGendrFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_gendr}}
              </td>
            </tr>
            <tr>
                <%-- 결혼여부 --%>
              <th><s:message code="regist.wedding" /></th>
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
                <%-- 결혼기념일 --%>
              <th><s:message code="regist.weddingDay" /></th>
              <td>
                <wj-input-date
                        ng-model="member.weddingday"
                        ng-hide="true"
                        text="_weddingday"
                        format="yyyy/MM/dd"
                        min="2000-01-01"
                        max="2099-12-31">
                </wj-input-date>
                {{_weddingday}}
              </td>
            </tr>
            <tr>
                <%-- 음력/양력 구분 --%>
              <th><s:message code="regist.lunarYn" /></th>
              <td ng-if="member.lunarYn == 'Y'">양력</td>
              <td ng-if="member.lunarYn == 'N'">음력</td>
              <%-- 생일 --%>
              <th><s:message code="regist.brthd" /></th>
              <td>
                <wj-input-date
                        ng-model="member.birthday"
                        ng-hide="true"
                        text="_birthday"
                        format="yyyy/MM/dd"
                        min="2000-01-01"
                        max="2099-12-31">
                </wj-input-date>
                {{_birthday}}
              </td>
            </tr>
            <tr>
                <%-- 회원등급 --%>
              <th><s:message code="regist.memberClass" /></th>
              <td>
                <wj-combo-box
                        ng-model="member.membrClassCd"
                        ng-hide="true"
                        text="_membrClass"items-source="_getComboData('rMemberClass')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_membrClass}}
              </td>
                <%-- 사용여부 --%>
              <th><s:message code="regist.useYn" /></th>
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
              <th><s:message code="regist.email" /></th>
              <td colspan="3">{{member.emailAddr}}</td>
            </tr>
            <tr>
                <%-- 주소 --%>
              <th><s:message code="regist.addr" /></th>
              <td colspan="3">({{member.postNo}}) {{member.addr}} {{member.addrDtl}}</td>
            </tr>
            <tr>
                <%-- 이메일수신 --%>
              <th><s:message code="regist.email.recv" /></th>
              <td>
                <wj-combo-box
                        ng-model="member.emailRecvYn"
                        ng-hide="true"
                        text="_emailRecvYn"
                        items-source="_getComboData('rEmailRecvYn')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_emailRecvYn}}
              </td>
                <%-- SMS 수신 --%>
              <th><s:message code="regist.sms.recv" /></th>
              <td>
                  <wj-combo-box
                          ng-model="member.smsRecvYn"
                          ng-hide="true"
                          text="_smsRecvYn"
                          items-source="_getComboData('rSmsRecvYn')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false">
                  </wj-combo-box>
                {{_smsRecvYn}}
              </td>
            </tr>
            <tr>
                <%-- 비고 --%>
              <th><s:message code="regist.remark" /></th>
              <td colspan="3">{{member.remark}}</td>
            </tr>
            </tbody>
          </table>

        </f:form>
      </div>
      <div class="btnSet">
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="regist.modify" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>

    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfoDetail.js?ver=20181109.01" charset="utf-8"></script>
