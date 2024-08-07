<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="comparePeriodMomsCtrl">

    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('comparePeriodMomsCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <%-- 확장조회 --%>
        <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
          <s:message code="cmm.search.addShow" />
        </button>
      </c:if>
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
        <%-- 조회기간 --%>
        <th><s:message code="periodMoms.srchDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <th><s:message code="periodMoms.compDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="compStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="compEndDate" class="w110px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="cmm.moms.storeHqBrand"/></th>
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
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                  <jsp:param name="targetTypeFg" value="M"/>
                  <jsp:param name="targetId" value="comparePeriodMomsStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="comparePeriodMomsStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
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
        <th><s:message code="cmm.moms.momsTeam"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchMomsTeam"
              items-source="_getComboData('srchMomsTeam')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              control="srchMomsTeamCombo">
            </wj-combo-box>
          </div>
        </td>
        <%-- AC점포별 --%>
        <th><s:message code="cmm.moms.momsAcShop"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchMomsAcShop"
              items-source="_getComboData('srchMomsAcShop')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
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
              id="srchMomsAreaFg"
              items-source="_getComboData('srchMomsAreaFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              control="srchMomsAreaFgCombo">
            </wj-combo-box>
          </div>
        </td>
        <%-- 상권 --%>
        <th><s:message code="cmm.moms.momsCommercial"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchMomsCommercial"
              items-source="_getComboData('srchMomsCommercial')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
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
              id="srchMomsShopType"
              items-source="_getComboData('srchMomsShopType')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              control="srchMomsShopTypeCombo">
            </wj-combo-box>
          </div>
        </td>
        <%-- 매장관리타입 --%>
        <th><s:message code="cmm.moms.momsStoreManageType"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchMomsStoreManageType"
              items-source="_getComboData('srchMomsStoreManageType')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
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
              id="srchBranchCd"
              items-source="_getComboData('srchBranchCd')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
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

    <div class="mt10 oh sb-select dkbr">
      <%-- 조회조건 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="periodMoms.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleCnt"/>" binding="realSaleCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.billUprc"/>" binding="billUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.prodSaleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.rate"/>" binding="rate" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleCnt"/>" binding="realSaleCnt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.billUprc"/>" binding="billUprc1" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.prodSaleQty"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleAmt"/>" binding="totSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totDcAmt"/>" binding="totDcAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleAmt"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.rate"/>" binding="rate1" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleCnt"/>"   binding="rateRealSaleCnt" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.billUprc"/>"   binding="rateBillUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.prodSaleQty"/>"  binding="rateSaleQty" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleAmt"/>"  binding="rateTotSaleAmt" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleAmt"/>"  binding="rateRealSaleAmt" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="comparePeriodMomsCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
  var storeCd = "${storeCd}";

  // 콤보박스 데이터
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
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

<script type="text/javascript" src="/resource/solbipos/js/sale/period/comparePeriodMoms/comparePeriodMoms.js?ver=20240530.01" charset="utf-8"></script>