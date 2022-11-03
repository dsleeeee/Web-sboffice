<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 본사 사원 신규등록 & 수정 팝업 --%>
<wj-popup control="hqEmpRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:700px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="hqEmpRegistCtrl">
  <form name="empForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqEmp.hqEmpInfo"/><span>{{hqEmpRegistInfo.empInfo}}</span>
      <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
      <%--<h3 class="h3_tbl brt"><s:message code="hqEmp.hqEmpInfo"/></h3>--%>

      <%-- 사원기초정보 탭 --%>
      <div class="tabType1">
        <ul>
          <%-- 사원기초정보 탭 --%>
          <li><a id="hqEmpInfoTab" href="#" class="on"><s:message code="hqEmp.hqEmpInfo" /></a></li>
          <%-- 메뉴권한 탭 --%>
          <li><a id="hqEmpMenuAuthTab" href="#" ng-click="changeTab()"><s:message code="hqEmp.menuSetting" /></a></li>
        </ul>
      </div>

      <%-- 상세 --%>
      <div id="dtlArea" style="height: 410px; overflow-y: auto;">

        <table class="searchTbl">
          <colgroup>
            <col class="w20" />
            <col class="w30" />
            <col class="w20" />
            <col class="w30" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 사원번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
            </th>
            <td>
              <input type="text" name="empNo" class="sb-input w100" ng-model="hqEmpRegistInfo.empNo" placeholder="사원번호는 자동생성 됩니다." disabled />
            </td>
            <%-- 사원명 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNm" /></div>
            </th>
            <td>
              <input type="text" id="_empNm" name="empNm" class="sb-input w100"
                     ng-model="hqEmpRegistInfo.empNm"
                     required
                     popover-enable="empForm.empNm.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="hqEmp.empNm" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 웹사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.webUseYn" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_webUseYn" name="webUseYn"
                              control="hqEmpWebUseYnCombo"
                              ng-model="hqEmpRegistInfo.webUseYn"
                              items-source="_getComboData('hqEmpWebUseYnComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.webUseYn.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.webUseYn" />은(는) 필수 입력항목 입니다.">
                </wj-combo-box>
              </div>
            </td>
            <th></th>
            <td></td>
          </tr>
          <tr ng-if="hqEmpRegistInfo.webUseYn == 'Y'">
            <%-- 웹사용자ID --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.userId" /></div>
            </th>
            <td colspan="3">
              <input type="text" id="_userId" name="userId" class="w100 sb-input" style="width:30%"
                     maxlength="20"
                     ng-model="hqEmpRegistInfo.userId"
                     required
                     ng-readonly="newEmpYn == false"
                     popover-enable="empForm.userId.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="hqEmp.userId" />은(는) 필수 입력항목 입니다."/>
              <input type="hidden" ng-model="hqEmpRegistInfo.originalWebUserId"/>
              <%-- 중복체크 --%>
              <span class="txtIn" ng-show="hqEmpRegistInfo.originalWebUserId == '' || hqEmpRegistInfo.originalWebUserId == undefined">
                <a href="#" class="btn_grayS" ng-click="checkDuplicate()"><s:message code="hqEmp.chk.duplicate" /></a>
              </span>
            </td>
          </tr>
          <tr ng-if="hqEmpRegistInfo.webUseYn == 'Y'">
            <%-- 웹 사용 비밀번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.pwd" /></div>
            </th>
            <td colspan="3">
              <input type="password" id="_userPwd" name="userPwd" class="sb-input w30" style="width: 30%"
                     ng-model="hqEmpRegistInfo.userPwd"
                     ng-show="newEmpYn == true"
                     ng-required="newEmpYn == true"
                     maxlength="12"
                     popover-enable="empForm.userPwd.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     placeholder="<s:message code="hqEmp.pwd" />"
                     uib-popover="<s:message code="hqEmp.pwd" />은(는) 필수 입력항목 입니다."/>
              <input type="password" id="_userPwdCfm" name="userPwdCfm" class="sb-input ml10 w30" style="width: 30%"
                     ng-model="hqEmpRegistInfo.userPwdCfm"
                     ng-show="newEmpYn == true"
                     ng-required="newEmpYn == true"
                     maxlength="12"
                     popover-enable="empForm.userPwd.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     placeholder="<s:message code="hqEmp.pwd.confirm" />"
                     uib-popover="<s:message code="hqEmp.pwd.confirm" />은(는) 필수 입력항목 입니다."/>
              <a href="#" class="btn_grayS" ng-show="newEmpYn == false" ng-click="changePassword()"><s:message code="hqEmp.change.pwd" /></a>
            </td>
          </tr>
          <tr>
            <%-- 휴대폰번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.mpNo" /></div>
            </th>
            <td>
              <input type="text" id="_mpNo" name="mpNo" class="sb-input w100"
                     ng-model="hqEmpRegistInfo.mpNo"
                     required
                     popover-enable="empForm.mpNo.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     maxlength="15"
                     uib-popover="<s:message code="hqEmp.mpNo" />은(는) 필수 입력항목 입니다."/>
            </td>
            <%-- SMS수신여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.smsRecvYn" /></div>
            </th>
            <td>
              <div class="sb-select">
              <wj-combo-box id="_smsRecvYn" name="smsRecvYn"
                            ng-model="hqEmpRegistInfo.smsRecvYn"
                            items-source="_getComboData('hqEmpSmsRecvYnComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            required
                            popover-enable="empForm.smsRecvYn.$invalid"
                            popover-placement="bottom-left"
                            popover-trigger="'mouseenter'"
                            uib-popover="<s:message code="hqEmp.smsRecvYn" />은(는) 필수 입력항목 입니다."
                            control="hqEmpSmsRecvYnCombo">
              </wj-combo-box>
            </div>
            </td>
          </tr>
          <tr>
            <%-- 재직여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.serviceFg" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_serviceFg" name="serviceFg"
                              ng-model="hqEmpRegistInfo.serviceFg"
                              items-source="_getComboData('hqEmpServiceFgComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.serviceFg.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.serviceFg" />은(는) 필수 입력항목 입니다."
                              control="hqEmpServiceFgCombo">
                </wj-combo-box>
              </div>
            </td>
            <%-- 사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.useYn" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_useYnFg" name="useYnFg"
                              ng-model="hqEmpRegistInfo.useYn"
                              items-source="_getComboData('hqEmpUseYnFgComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.useYnFg.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.useYn" />은(는) 필수 입력항목 입니다."
                              control="hqEmpUseYnFgCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
            <%-- 메인화면매출표시 --%>
            <th>
              <s:message code="hqEmp.mainSaleFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_mainSaleFg" name="mainSaleFg"
                              ng-model="hqEmpRegistInfo.mainSaleFg"
                              items-source="_getComboData('hqEmpMainSaleFgComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.mainSaleFg.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.mainSaleFg" />은(는) 필수 입력항목 입니다."
                              control="hqEmpMainSaleFgCombo">
                </wj-combo-box>
              </div>
            </td>
            <%-- 거래처 --%>
            <th><s:message code="hqEmp.vendr"/></th>
            <td>
              <div class="sb-select w100">
                <wj-combo-box
                  id="vendrCd"
                  ng-model="hqEmpRegistInfo.vendrCd"
                  control="hqEmpVendrCdCombo"
                  items-source="_getComboData('hqEmpVendrCdComboData')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr id="trUserHqBrandYn" style="display: none;">
              <%-- 관리브랜드 --%>
              <th>
                  <s:message code="hqEmp.userHqBrand"/>
              </th>
              <td>
                  <input type="text" class="sb-input w70" id="srchHqBrandNm" ng-model="hqEmpRegistInfo.hqBrandNm" ng-click="popUpHqBrandCd()" style="float: left;"
                         placeholder="<s:message code="hqEmp.userHqBrand" /> 선택" readonly/>
                  <input type="hidden" id="_hqBrandCd" name="hqBrandCd" ng-model="hqEmpRegistInfo.hqBrandCd" disabled />
              </td>
              <th></th>
              <td></td>
          </tr>
          <tr>
            <%-- 지사명 --%>
            <th>
              <s:message code="hqEmp.branchNm"/>
            </th>
            <td>
              <div class="sb-select w100">
                <wj-combo-box
                        id="branchCd"
                        ng-model="hqEmpRegistInfo.branchCd"
                        control="hqEmpBranchCdCombo"
                        items-source="_getComboData('hqEmpBranchCdComboData')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
            <th></th>
            <td></td>
          </tr>
          </tbody>
        </table>
        <%-- [1250 맘스터치] --%>
        <c:if test="${momsEnvstVal == '1'}">
          <%-- 추가정보 --%>
          <h3 class="h3_tbl"><s:message code="hqEmp.moms.member" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15"/>
              <col class="w35"/>
              <col class="w15"/>
              <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
              <%-- 팀별 --%>
              <th><s:message code="hqEmp.moms.momsTeam"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsTeamCombo"
                          ng-model="hqEmpRegistInfo.momsTeam"
                          items-source="_getComboData('momsTeamCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsTeamCombo">
                  </wj-combo-box>
                </div>
              </td>
              <%-- AC점포별 --%>
              <th><s:message code="hqEmp.moms.momsAcShop"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsAcShopCombo"
                          ng-model="hqEmpRegistInfo.momsAcShop"
                          items-source="_getComboData('momsAcShopCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsAcShopCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 지역구분 --%>
              <th><s:message code="hqEmp.moms.momsAreaFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsAreaFgCombo"
                          ng-model="hqEmpRegistInfo.momsAreaFg"
                          items-source="_getComboData('momsAreaFgCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsAreaFgCombo">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 상권 --%>
              <th><s:message code="hqEmp.moms.momsCommercial"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsCommercialCombo"
                          ng-model="hqEmpRegistInfo.momsCommercial"
                          items-source="_getComboData('momsCommercialCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsCommercialCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 점포유형 --%>
              <th><s:message code="hqEmp.moms.momsShopType"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsShopTypeCombo"
                          ng-model="hqEmpRegistInfo.momsShopType"
                          items-source="_getComboData('momsShopTypeCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsShopTypeCombo">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 매장관리타입 --%>
              <th><s:message code="hqEmp.moms.momsStoreManageType"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsStoreManageTypeCombo"
                          ng-model="hqEmpRegistInfo.momsStoreManageType"
                          items-source="_getComboData('momsStoreManageTypeCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsStoreManageTypeCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </c:if>
      </div>
    </div>

    <div class="wj-dialog-footer">
      <%-- 등록 regist() --%>
      <button class="btn btn_blue" ng-click="empForm.$valid && regist()" ng-show="newEmpYn == true"><s:message code="cmm.new.add"/></button>
      <%-- 저장 --%>
      <button class="btn btn_blue" ng-click="empForm.$valid && save()" ng-show="newEmpYn == false"><s:message code="cmm.save"/></button>
      <%-- 닫기 --%>
      <button class="btn btn_gray" ng-click="close()"><s:message code="cmm.close"/></button>
    </div>
  </form>
  </div>
</wj-popup>

<script>
  // [1250 맘스터치] 환경설정값
  var momsEnvstVal = "${momsEnvstVal}";
  // List 형식("" 안붙임)
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmpSave.js?ver=20221101.01" charset="utf-8"></script>

<%-- 관리브랜드 조회 팝업 --%>
<c:import url="/WEB-INF/view/base/store/emp/searchUserHqBrand.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>