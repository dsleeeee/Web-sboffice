<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 본사 사원 상세 팝업 --%>
<wj-popup control="hqEmpDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:650px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="hqEmpDetailCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqEmp.hqEmpInfo"/><span>{{hqEmp.empInfo}}</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">

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
      <div style="height: 293px; overflow-y: auto;">
        <table class="searchTbl">
          <colgroup>
            <col class="w25" />
            <col class="w25" />
            <col class="w25" />
            <col class="w25" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 사원번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
            </th>
            <td >{{hqEmp.empNo}}</td>
            <%-- 사원명 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNm" /></div>
            </th>
            <td>{{hqEmp.empNm}}</td>
          </tr>
          <tr>
            <%-- 웹사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.webUseYn" /></div>
            </th>
            <td>{{hqEmp.webUseYnNm}}</td>
            <%-- 웹사용자ID --%>
            <th ng-if="hqEmp.webUseYn == 'Y'">
              <div class="impWrap"><s:message code="hqEmp.userId" /></div>
            </th>
            <td ng-if="hqEmp.webUseYn == 'Y'">{{hqEmp.userId}}</td>
            <th ng-if="hqEmp.webUseYn != 'Y'"></th>
            <td ng-if="hqEmp.webUseYn != 'Y'"></td>
          </tr>
          <tr>
            <%-- 휴대폰번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.mpNo" /></div>
            </th>
            <td>{{hqEmp.mpNo}}</td>
            <%-- SMS수신여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.smsRecvYn" /></div>
            </th>
            <td>{{hqEmp.smsRecvYnNm}}</td>
          </tr>
          <tr>
            <%-- 재직여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.serviceFg" /></div>
            </th>
            <td>{{hqEmp.serviceFgNm}}</td>
            <%-- 사용여부 --%>
            <th>
              <s:message code="hqEmp.useYn"/>
            </th>
            <td>
              {{hqEmp.useYn}}
            </td>
          </tr>
          <tr>
            <%-- 메인화면매출표시 --%>
            <th>
              <s:message code="hqEmp.mainSaleFg"/>
            </th>
            <td>
              {{hqEmp.mainSaleFg}}
            </td>
            <th><s:message code="hqEmp.vendr"/></th>
            <td>{{hqEmp.vendrNm}}</td>
          </tr>
          <tr id="trUserHqBrandYnDtl" style="display: none;">
            <%-- 관리브랜드 --%>
            <th>
              <s:message code="hqEmp.userHqBrand"/>
            </th>
            <td>
              {{hqEmp.hqBrandNm}}
            </td>
            <th></th>
            <td></td>
          </tr>
          <tr>
            <%-- 지사정보 --%>
            <th>
              <s:message code="hqEmp.branchNm"/>
            </th>
            <td>
              {{hqEmp.branchNm}}
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
              <col class="w25" />
              <col class="w25" />
              <col class="w25" />
              <col class="w25" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 팀별 --%>
              <th><s:message code="hqEmp.moms.momsTeam"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsTeam"
                        ng-hide="true"
                        text="_momsTeam"
                        items-source="_getComboData('momsTeamDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsTeam}}
              </td>
              <%-- AC점포별 --%>
              <th><s:message code="hqEmp.moms.momsAcShop"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsAcShop"
                        ng-hide="true"
                        text="_momsAcShop"
                        items-source="_getComboData('momsAcShopDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsAcShop}}
              </td>
            </tr>
            <tr>
              <%-- 지역구분 --%>
              <th><s:message code="hqEmp.moms.momsAreaFg"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsAreaFg"
                        ng-hide="true"
                        text="_momsAreaFg"
                        items-source="_getComboData('momsAreaFgDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsAreaFg}}
              </td>
              <%-- 상권 --%>
              <th><s:message code="hqEmp.moms.momsCommercial"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsCommercial"
                        ng-hide="true"
                        text="_momsCommercial"
                        items-source="_getComboData('momsCommercialDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsCommercial}}
              </td>
            </tr>
            <tr>
              <%-- 점포유형 --%>
              <th><s:message code="hqEmp.moms.momsShopType"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsShopType"
                        ng-hide="true"
                        text="_momsShopType"
                        items-source="_getComboData('momsShopTypeDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsShopType}}
              </td>
              <%-- 매장관리타입 --%>
              <th><s:message code="hqEmp.moms.momsStoreManageType"/></th>
              <td>
                <wj-combo-box
                        ng-model="hqEmp.momsStoreManageType"
                        ng-hide="true"
                        text="_momsStoreManageType"
                        items-source="_getComboData('momsStoreManageTypeDtlCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false">
                </wj-combo-box>
                {{_momsStoreManageType}}
              </td>
            </tr>
            </tbody>
          </table>
        </c:if>
      </div>
    </div>

    <div class="wj-dialog-footer">
      <div class="btnSet">
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue" ng-click="modify()"><s:message code="cmm.edit" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>
    </div>

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

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmpDetail.js?ver=20221101.01" charset="utf-8"></script>