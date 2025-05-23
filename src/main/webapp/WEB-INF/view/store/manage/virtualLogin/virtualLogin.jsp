<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

<div class="subCon" ng-controller="gridCtrl">

  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('gridCtrl',1)">
        <s:message code="cmm.search" />
      </button>
      <%-- 확장조회 --%>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <c:if test="${momsEnvstVal == '1'}">
          <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
          </button>
        </c:if>
      </c:if>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
    </colgroup>
    <tbody>
      <tr <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>
        <%-- 본사코드 --%>
        <th><s:message code="virtualLogin.hqOfficeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();" />
        </td>
        <%-- 본사명 --%>
        <th><s:message code="virtualLogin.hqOfficeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="virtualLogin.storeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
        </td>
        <%-- 매장명 --%>
        <th><s:message code="virtualLogin.storeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
        </td>
      </tr>
      <tr>
        <%-- 용도 --%>
        <th><s:message code="virtualLogin.clsFgNm" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchClsFg"
              ng-model="clsFg"
              control="clsFgCombo"
              items-source="_getComboData('srchClsFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 매장상태 --%>
        <th><s:message code="virtualLogin.sysStatFgNm" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchStatFg"
              ng-model="sysStatFg"
              control="statFgCombo"
              items-source="_getComboData('srchStatFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <c:if test="${brandUseFg == '1'}">
          <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="cmm.moms.storeHqBrand" /></th>
            <td>
              <div class="sb-select">
                    <wj-combo-box
                      id="srchStoreHqBrandCd"
                      items-source="_getComboData('srchStoreHqBrandCd')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="srchStoreHqBrandCdCombo">
                    </wj-combo-box>
                </div>
            </td>
            <td></td>
            <td></td>
          </tr>
        </c:if>
      </c:if>
    </tbody>
  </table>
  <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
            <th><s:message code="cmm.moms.momsTeam"/></th>
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
            <th><s:message code="cmm.moms.momsAcShop"/></th>
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
            <th><s:message code="cmm.moms.momsAreaFg"/></th>
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
            <th><s:message code="cmm.moms.momsCommercial"/></th>
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
            <th><s:message code="cmm.moms.momsShopType"/></th>
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
            <th><s:message code="cmm.moms.momsStoreManageType"/></th>
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
            <th><s:message code="cmm.moms.branch"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchBranchCdCombo"
                        ng-model="branchCd"
                        items-source="_getComboData('branchCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchBranchCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <%-- 매장그룹 --%>
            <th><s:message code="cmm.moms.momsStoreFg01"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchMomsStoreFg01Combo"
                        ng-model="momsStoreFg01"
                        items-source="_getComboData('momsStoreFg01Combo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchMomsStoreFg01Combo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
            <tr>
              <%-- 매장그룹2 --%>
              <th><s:message code="cmm.moms.momsStoreFg02"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsStoreFg02Combo"
                          ng-model="momsStoreFg02"
                          items-source="_getComboData('momsStoreFg02Combo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsStoreFg02Combo">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 매장그룹3 --%>
              <th><s:message code="cmm.moms.momsStoreFg03"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsStoreFg03Combo"
                          ng-model="momsStoreFg03"
                          items-source="_getComboData('momsStoreFg03Combo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsStoreFg03Combo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 매장그룹4 --%>
              <th><s:message code="cmm.moms.momsStoreFg04"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsStoreFg04Combo"
                          ng-model="momsStoreFg04"
                          items-source="_getComboData('momsStoreFg04Combo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsStoreFg04Combo">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 매장그룹5 --%>
              <th><s:message code="cmm.moms.momsStoreFg05"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchMomsStoreFg05Combo"
                          ng-model="momsStoreFg05"
                          items-source="_getComboData('momsStoreFg05Combo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchMomsStoreFg05Combo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
          </c:if>
          </tbody>
        </table>
    </c:if>
  </c:if>

  <div id="grid" class="w100">

    <div class="mt10 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
        class="w100px fl"
        id="listScaleBox"
        ng-model="listScale"
        control="listScaleCombo"
        items-source="_getComboData('listScaleBox')"
        display-member-path="name"
        selected-value-path="value"
        is-editable="false"
        initialized="_initComboBox(s)">
      </wj-combo-box>
      <%-- 엑셀 다운로드 //TODO --%>
      <%--<button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>--%>
    </div>

    <div class="wj-gridWrap mt10" style="height:450px; overflow-y: hidden;">
      <div class="row">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter"
          is-read-only="true">

          <!-- define columns -->

          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" ng-if="userOrgnFg == 'M'"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm" ng-if="userOrgnFg == 'M'"></wj-flex-grid-column>--%>
          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">visible="false"</c:if>></wj-flex-grid-column>--%>
          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm" <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">visible="false"</c:if>></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqUserId"/>" binding="hqUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeCd"/>" binding="storeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeNm"/>" binding="storeNm" width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.msUserId"/>" binding="msUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.clsFgNm"/>" binding="clsFgNm" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysStatFgNm"/>" binding="sysStatFgNm" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.ownerNm"/>" binding="ownerNm" width="90" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.telNo"/>" binding="telNo" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.mpNo"/>" binding="mpNo" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.agencyNm"/>" binding="agencyNm" width="100" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.cmUserId"/>" binding="cmUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysOpenDate"/>" binding="sysOpenDate" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysClosureDate"/>" binding="sysClosureDate" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.orgnFg"/>" binding="orgnFg" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeModYn"/>" binding="storeModYn" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="gridCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="gridCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var clsFg = ${ccu.getCommCode("001")};
  var sysStatFg = ${ccu.getCommCode("005")};
  var pAgencyCd = "${pAgencyCd}";
  var orgnFg = "${orgnFg}";

  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";

  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};

  // [1250 맘스터치] 환경설정값
  var momsEnvstVal = "${momsEnvstVal}";

  // 확장조회 관련
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
  var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
  var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
  var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
  var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
  var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/virtualLogin/virtualLogin.js?ver=20250211.01" charset="utf-8"></script>