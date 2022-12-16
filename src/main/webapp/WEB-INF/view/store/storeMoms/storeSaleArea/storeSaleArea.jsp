<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="storeSaleAreaCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeSaleAreaCtrl',1)">
            <s:message code="cmm.search"/>
            </button>
            <%-- 확장조회 --%>
            <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="cmm.search.addShow" />
            </button>
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
      <tr>
          <%-- 매장코드 --%>
          <th><s:message code="storeInfo.storeCd"/></th>
          <td>
              <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
          </td>
          <%-- 매장명 --%>
          <th><s:message code="storeInfo.storeNm"/></th>
          <td>
              <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
          </td>
      </tr>
      <tr>
          <%-- 매장브랜드 --%>
          <th><s:message code="dayProd.storeHqBrand"/></th>
          <td>
              <div class="sb-select">
                  <wj-combo-box
                          id="srchStoreHqBrandCdCombo"
                          ng-model="storeHqBrandCd"
                          items-source="_getComboData('storeHqBrandCdCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchStoreHqBrandCdCombo">
                  </wj-combo-box>
              </div>
          </td>
          </td>
      </tr>
    </tbody>
    </table>
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
                <%-- 지사 --%>
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
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

  <div class="mt20 oh sb-select dkbr">
    <div class="tr">
      <%-- 신규등록 --%>
      <button class="btn_skyblue" ng-click="addStoreSaleArea()"><s:message code="cmm.new.add" /></button>
    </div>
  </div>

 <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          control="flex"
          autoGenerateColumns="false"
          selection-mode="Row"
          initialized="initGrid(s,e)"
          items-source="data"
          item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storeSaleArea.branchCd"/>" binding="branchCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.branchNm"/>" binding="branchNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.storeNm"/>" binding="storeNm" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.addr"/>" binding="addr" width="400" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeSaleArea.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeSaleAreaCtrlPager" data-size="10">
    </ul>
  </div>

</div>

<script type="text/javascript">
    var areaCd = ${ccu.getCommCode("061")};
    var sysStatFg = ${ccu.getCommCode("005")};
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

    // 지사
    var branchCombo = ${branchCombo};

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

</script>


<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeSaleArea/storeSaleArea.js?ver=20221125.01" charset="utf-8"></script>

<%-- NAVER Maps API --%>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=67szg2cbo7"></script>

<%-- 점포영업지역관리 상세 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeSaleArea/storeSaleAreaDtl.jsp">
</c:import>

<%-- 점포영업지역관리 등록 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeSaleArea/storeSaleAreaReg.jsp">
</c:import>