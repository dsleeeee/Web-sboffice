<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon">
  <div ng-controller="storePayDayCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storePayDayCtrl')">
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
            <span class="txtIn"><input id="srchStorePayDayStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchStorePayDayEndDate" class="w110px"></span>
          </div>
        </td>
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
                    selected-index="1">
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
        <th class="storePayDay" style="display: none;"><s:message code="cmm.store.select"/></th>
        <td class="storePayDay" style="display: none;">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="storePayDayStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
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
        </tbody>
      </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
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
          <wj-flex-grid-column header="<s:message code="storePayDay.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.storeNm"/>" binding="storeNm" width="80" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.billCnt"/>" binding="billCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.billUprc"/>" binding="billUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.gaAmt"/>" binding="gaAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.vatAmt"/>" binding="vatAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <%-- 결제수단 컬럼 생성--%>
          <c:forEach var="payCol" items="${payColList}">
            <wj-flex-grid-column header="<s:message code="storePayDay.payCnt"/>" binding="payCnt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storePayDay.realSaleAmt"/>" binding="payAmt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storePayDay.rate"/>" binding="payRate${payCol.payCd}" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          </c:forEach>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storePayDayCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="storePayDayExcelCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
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
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storePayDay.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.branchCd"/>" binding="branchCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.storeNm"/>" binding="storeNm" width="80" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.billCnt"/>" binding="billCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.billUprc"/>" binding="billUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.gaAmt"/>" binding="gaAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.vatAmt"/>" binding="vatAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.totTipAmt"/>" binding="totTipAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.totEtcAmt"/>" binding="totEtcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.cupAmt"/>" binding="cupAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storePayDay.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <%-- 결제수단 컬럼 생성--%>
        <c:forEach var="payCol" items="${payColList}">
          <wj-flex-grid-column header="<s:message code="storePayDay.payCnt"/>" binding="payCnt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.realSaleAmt"/>" binding="payAmt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePayDay.rate"/>" binding="payRate${payCol.payCd}" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
        </c:forEach>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
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
  payParam.payNm     = "${payCol.payNm}";
  payParam.payMethod = "${payCol.payMethod}";
  payColList.push(payParam);
  </c:forEach>

  var payCol    = '${payCol}';
  var arrPayCol = payCol.split(',');

  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/pay/storePayDay/storePayDay.js?ver=20230329.03" charset="utf-8"></script>