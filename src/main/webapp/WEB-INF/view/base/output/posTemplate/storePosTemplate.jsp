<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="storePosTemplateLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:600px;" >
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storePosTemplateCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="posTemplate.prtFormToStore" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

      <input type="hidden" id="hdPrtClassCd" />
      <input type="hidden" id="hdApplyTempltRegFg" />
      <input type="hidden" id="hdTempltCd" />

    <%-- body --%>
    <div class="wj-dialog-body">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr><%-- 적용할 템플릿명 --%>
          <th><s:message code="posTemplate.applyTempltNm"/></th>
          <td><label id="lblApplyTempltNm"></label></td>
          <c:if test="${momsEnvstVal == '1'}">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <%-- 매장브랜드 --%>
              <th><s:message code="kioskKeyMap.storeHqBrand" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchStoreHqBrandCd"
                          ng-model="storeHqBrandCd"
                          items-source="_getComboData('srchStoreHqBrandCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchStoreHqBrandCdCombo">
                  </wj-combo-box>
                </div>
              </td>
            </c:if>
          </c:if>
        </tr>
        <tr><%-- 매장 --%>
          <th><s:message code="prod.storeCd"/></th>
          <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
          <th><s:message code="prod.storeNm"/></th>
          <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
        </tr>
        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
          <tr> <%-- 매장상태 --%>
            <th><s:message code="prod.sysStatFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchSysStatFg"
                        ng-model="sysStatFg"
                        items-source="_getComboData('srchSysStatFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
        </c:if>
        </tbody>
      </table>
      <c:if test="${momsEnvstVal == '1'}">
        <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
              <%-- 팀별 --%>
            <th><s:message code="dayProd.momsTeam"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsTeamCombo"
                        ng-model="momsTeam"
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
            <th><s:message code="dayProd.momsAcShop"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsAcShopCombo"
                        ng-model="momsAcShop"
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
            <th><s:message code="dayProd.momsAreaFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsAreaFgCombo"
                        ng-model="momsAreaFg"
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
            <th><s:message code="dayProd.momsCommercial"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsCommercialCombo"
                        ng-model="momsCommercial"
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
            <th><s:message code="dayProd.momsShopType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsShopTypeCombo"
                        ng-model="momsShopType"
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
            <th><s:message code="dayProd.momsStoreManageType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsStoreManageTypeCombo"
                        ng-model="momsStoreManageType"
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
          <tr>
              <%-- 그룹 --%>
            <th><s:message code="dayProd.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchBranchCdComboo"
                        ng-model="branchCd"
                        items-source="_getComboData('branchCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchBranchCdComboo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </c:if>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <c:if test="${momsEnvstVal == '1'}">
          <%-- 확장조회 --%>
          <button class="btn_skyblue" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
          </button>
        </c:if>
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('storePosTemplateCtrl',1)" ><s:message code="cmm.search" /></button>
        <button class="btn_skyblue" id="btnApply" ng-click="storeApply()" ><s:message code="cmm.apply" /></button>
      </div>

      <%--- 적용매장 그리드 --%>
      <div class="oh mt20">
        <div class="fl" >
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.regStore"/></span>
            </div>
            <div id="regStoreGrid" class="mt10" style="height: 370px; overflow-y: hidden; overflow-x: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="300" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

              </wj-flex-grid>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
  var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/output/posTemplate/storePosTemplate.js?ver=20230315.02" charset="utf-8"></script>
