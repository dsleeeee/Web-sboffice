<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="payFgCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('payFgCtrl')">
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
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
      <tr>
        <%-- 옵션 --%>
        <th><s:message code="storeDayChannel.option"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="option"
                    ng-model="option"
                    items-source="_getComboData('option')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="optionCombo"
                    selected-index-changed="changeOption(s)"
                    selected-index="2">
            </wj-combo-box>
          </div>
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
          <%-- 매장선택 --%>
          <th class="payFgStore"><s:message code="cmm.store.select"/></th>
          <td class="payFgStore">
              <%-- 매장선택 모듈 사용시 include --%>
              <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="payFgStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="payFgStoreCd" value="${sessionInfo.storeCd}"/>
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
            <td></td>
            <td></td>
          </tr>
        </c:if>
        </tbody>
      </table>
    </c:if>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
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

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="payFg.branchCd"/>"    binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.storeNm"/>"     binding="storeNm" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.payFg"/>"       binding="payCd" width="100" align="center" is-read-only="true" data-map="payCdDataMap" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.payCnt"/>"  binding="payCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.totSaleAmt"/>"  binding="payAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="payFg.rate"/>"        binding="rate" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="payFgCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="payFgExcelCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        id="wjGridExcelList"
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="payFg.branchCd"/>"    binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.branchNm"/>"    binding="branchNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.storeCd"/>"     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.storeNm"/>"     binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.payFg"/>"       binding="payCd" width="100" align="center" is-read-only="true" data-map="payCdDataMap" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.payCnt"/>"  binding="payCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.totSaleAmt"/>"  binding="payAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="payFg.rate"/>"        binding="rate" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var payCd = ${ccu.getCommCode("024")};

  // List 형식("" 안붙임)
  var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

  // 결제수단
  var payColList = [];
  <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
  <c:forEach var="payCol" items="${payColList}">
  var payParam       = {};
  payParam.payCd     = "${payCol.payCd}";
  payParam.payMethod = "${payCol.payMethod}";
  payColList.push(payParam);
  </c:forEach>

  var payCol    = '${payCol}';
  var arrPayCol = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/pay/payFg/payFg.js?ver=20230329.03" charset="utf-8"></script>