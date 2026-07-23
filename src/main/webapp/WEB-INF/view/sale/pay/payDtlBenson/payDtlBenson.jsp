<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="payDtlBensonCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('payDtlBensonCtrl', 1)">
          <s:message code="cmm.search"/>
        </button>
      </div>
    </div>

    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장브랜드 --%>
          <th><s:message code="cmm.moms.storeHqBrand"/></th>
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
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <td></td>
          <td></td>
        </c:if>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td colspan="3">
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="payDtlBensonStore"/>
            </jsp:include>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="payDtlBensonStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <%-- 확장조회 영역은 요구사항에 따라 hidden 처리 --%>
    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
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
      <tr>
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
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 분할 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDivision()"><s:message code="cmm.excel.downDivision"/></button>
      <%-- 현재화면 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownloadCurrent()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10">
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                id="wjGridList"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <wj-flex-grid-column header="<s:message code="payDtlBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.storeNm"/>" binding="storeNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.billNo"/>" binding="billNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.payFg"/>" binding="payFg" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.realSaleAmt"/>" binding="payAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.gaAmt"/>" binding="gaAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payDtlBenson.vatAmt"/>" binding="vatAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="payDtlBensonCtrlPager" data-size="10"></ul>
  </div>

  <%-- 엑셀 리스트 --%>
  <div class="w100 mt10" style="display:none;" ng-controller="payDtlBensonExcelCtrl">
    <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              id="wjGridExcelList"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="excelFlex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

        <wj-flex-grid-column header="<s:message code="payDtlBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.storeNm"/>" binding="storeNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.billNo"/>" binding="billNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.payFg"/>" binding="payFg" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.realSaleAmt"/>" binding="payAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.gaAmt"/>" binding="gaAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payDtlBenson.vatAmt"/>" binding="vatAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript">
  var menuCd = "${menuCd}";
  var menuNm = "${menuNm}";
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
  var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
  var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
  var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
  var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/pay/payDtlBenson/payDtlBenson.js?ver=20260716.01" charset="utf-8"></script>
