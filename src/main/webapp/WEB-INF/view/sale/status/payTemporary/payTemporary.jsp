<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="payTemporaryCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('payTemporaryCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <%-- 확장조회 --%>
        <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
          <s:message code="cmm.search.addShow" />
        </button>
      </c:if>
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
          <span class="txtIn"><input id="srchDayStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchDayEndDate" class="w110px"></span>
        </div>
      </td>
    </tr>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
            <jsp:param name="targetId" value="payTemporaryStore"/>
          </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </tr>
    </c:if>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
      <input type="hidden" id="payTemporaryStoreCd" value="${sessionInfo.storeCd}"/>
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
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
      </c:if>
      </tbody>
    </table>
  </c:if>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        id="wjGridList"
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <wj-flex-grid-column header="<s:message code="payTemporary.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.brand"/>" binding="brand" width="100" align="left" is-read-only="true" data-map="brandDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.momsTeam"/>" binding="momsTeam" width="100" align="left" is-read-only="true" data-map="momsTeamDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.momsAcShop"/>" binding="momsAcShop" width="100" align="left" is-read-only="true" data-map="momsAcShopDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.startDate"/>" binding="startDate" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.endDate"/>" binding="endDate" width="100" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <wj-flex-grid-column header="<s:message code="payTemporary.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="payTemporary.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payTemporary.saleAmt"/>" binding="saleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="display: none;" ng-controller="payTemporaryExcelCtrl">
        <wj-flex-grid
                id="wjGridExcelList"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="payTemporary.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.branchNm"/>" binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.brand"/>" binding="brand" width="100" align="left" is-read-only="true" data-map="brandDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.momsTeam"/>" binding="momsTeam" width="100" align="left" is-read-only="true" data-map="momsTeamDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payTemporary.momsAcShop"/>" binding="momsAcShop" width="100" align="left" is-read-only="true" data-map="momsAcShopDataMap"></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="payTemporary.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="payTemporary.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payTemporary.saleAmt"/>" binding="saleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="payTemporaryCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/payTemporary/payTemporary.js?ver=20230320.01" charset="utf-8"></script>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";

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

<%-- 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/payTemporary/payTemporaryDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
