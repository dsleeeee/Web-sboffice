<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="comparePeriodMomsCtrl">

    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="periodMomsMoms.periodMomsMoms"/></a>
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
            <th><s:message code="periodMoms.storeHqBrand"/></th>
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
            <%-- 매장코드 --%>
            <th><s:message code="cmm.store"/></th>
            <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
              <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                  <jsp:param name="targetId" value="comparePeriodMomsStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="comparePeriodMomsStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
          <th><s:message code="periodMoms.momsTeam"/></th>
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
          <th><s:message code="periodMoms.momsAcShop"/></th>
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
          <th><s:message code="periodMoms.momsAreaFg"/></th>
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
          <th><s:message code="periodMoms.momsCommercial"/></th>
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
          <th><s:message code="periodMoms.momsShopType"/></th>
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
          <th><s:message code="periodMoms.momsStoreManageType"/></th>
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
          <%-- 지사 --%>
          <th><s:message code="periodMoms.branchCd"/></th>
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
          <td></td>
          <td></td>
        </tr>
        </tbody>
      </table>
    </c:if>

  <div class="mt10 oh sb-select dkbr">
    <%-- 조회조건 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
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
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.rate"/>" binding="rate" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleCnt"/>" binding="realSaleCnt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.billUprc"/>" binding="billUprc1" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleQty"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleAmt"/>" binding="totSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totDcAmt"/>" binding="totDcAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleAmt"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.rate"/>" binding="rate1" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="periodMoms.realSaleCnt"/>"   binding="rateRealSaleCnt" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.billUprc"/>"   binding="rateBillUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="periodMoms.totSaleQty"/>"  binding="rateSaleQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
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

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/period/comparePeriodMoms/comparePeriodMoms.js?ver=20230125.01" charset="utf-8"></script>