<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="monthMomsCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
          <%-- 조회 --%>
          <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('monthMomsCtrl')">
            <s:message code="cmm.search"/>
          </button>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <%-- 확장조회 --%>
            <button class="btn_blue mr5 fl" id="btnSearchAddShow" <c:if test="${hqOfficeCd == 'H0614' or hqOfficeCd == 'H0616'}">style="display: none;"</c:if> ng-click="searchAddShowChange()">
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
        <%-- 조회월 --%>
        <th><s:message code="cmm.search.month"/></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="startMonth" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="endMonth" class="w110px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 매장브랜드 --%>
          <th <c:if test="${hqOfficeCd == 'H0614' or hqOfficeCd == 'H0616'}">style="display: none"</c:if>><s:message code="cmm.moms.storeHqBrand"/></th>
          <td <c:if test="${hqOfficeCd == 'H0614' or hqOfficeCd == 'H0616'}">style="display: none"</c:if>>
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
          <th <c:if test="${hqOfficeCd == 'H0614' or hqOfficeCd == 'H0616'}">style="border-left:1px solid #ccc"</c:if>><s:message code="cmm.store.select"/></th>
          <td>
              <%-- 매장선택 모듈 사용시 include --%>
              <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="monthMomsStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="monthMomsStoreCd" value="${sessionInfo.storeCd}"/>
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

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.saleCnt"/>" binding="saleCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.billCnt"/>" binding="billCnt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.billUprc"/>" binding="billUprc" width="70" is-read-only="true" align="right" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.gaAmt"/>" binding="gaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.totTipAmt"/>" binding="totTipAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.totEtcAmt"/>" binding="totEtcAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.cupAmt"/>" binding="cupAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <%-- 결제수단 컬럼 생성--%>
          <c:forEach var="payCol" items="${payColList}">
            <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <%-- 모바일페이상세 컬럼 생성--%>
          <c:forEach var="mpayCol" items="${mpayColList}">
            <wj-flex-grid-column header="${mpayCol.mpayNm}" binding="mpay${mpayCol.mpayCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <wj-flex-grid-column header="<s:message code="month.genRealSaleAmt"/>" binding="genRealSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.genRealSaleAmtPer"/>" binding="genRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.dlvrRealSaleAmt"/>" binding="dlvrRealSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.dlvrRealSaleAmtPer"/>" binding="dlvrRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.packRealSaleAmt"/>" binding="packRealSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="month.packRealSaleAmtPer"/>" binding="packRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
          <%-- 채널 컬럼 생성--%>
          <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
            <wj-flex-grid-column header="<s:message code="monthMoms.billCnt"/>" binding="realSaleCnt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="monthMoms.prodSaleQty"/>" binding="saleQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="monthMoms.realSaleAmt"/>" binding="realSaleAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
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
  var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
  var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
  var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
  var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
  var momsStoreFg05ComboList = ${momsStoreFg05ComboList};

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

  // 모바일페이상세
  var mpayColList = [];
  <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
  <c:forEach var="mpayCol" items="${mpayColList}">
  var mpayParam       = {};
  mpayParam.payCd     = "${mpayCol.mpayCd}";
  mpayParam.payMethod = "${mpayCol.mpayMethod}";
  mpayColList.push(mpayParam);
  </c:forEach>

  var mpayCol    = '${mpayCol}';
  var arrMpayCol = mpayCol.split(',');

  // 할인
  var dcColList = [];
  <%--javascript에서 사용할 할인 json 데이터 생성--%>
  <c:forEach var="dcCol" items="${dcColList}">
  var dcParam      = {};
  dcParam.dcCd     = "${dcCol.dcCd}";
  dcParam.dcMethod = "${dcCol.dcMethod}";
  dcColList.push(dcParam);
  </c:forEach>

  var dcCol    = '${dcCol}';
  var arrDcCol = dcCol.split(',');

  // 채널
  var dlvrInFgColList = [];
  <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
  <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
  var param = {};
  param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
  param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
  dlvrInFgColList.push(param);
  </c:forEach>

  var dlvrInFgCol = '${dlvrInFgCol}';
  var arrDlvrInFgCol = dlvrInFgCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/month/monthMoms/monthMoms.js?ver=20241217.01" charset="utf-8"></script>

<%-- 팝업 레이어 시작 --%>
<%-- 매장별 매출현황 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayStoreDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 매출현황 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayProdDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품매출 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/prodInfo/prodSaleDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 할인내역 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayStoreDc.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 영수건수 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayBillInfo/dayStoreBill.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 매장별 영수건수 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayBillInfo/dayStoreBill2.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품매출 상세내역 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/dayProdSaleDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- //팝업 레이어 --%>

<%-- 팝업 추후 개발 --%>
<%-- 결제수단 팝업 레이어 시작 --%>
<%-- 신용카드 상세 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayCard.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 현금 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayCash.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; PAYCO 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPayco.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; VMEM 포인트 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVpoint.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; VMEM 전자상품권 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVcharge.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 모바일페이 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayMpay.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 모바일쿠폰 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayMcoupn.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 포인트 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPoint.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 회원선불 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPrepaid.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 회원후불 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPostpaid.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 상품권 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayGift.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 식권 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayFstmp.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 제휴카드 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayPartner.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 사원카드 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayEmpCard.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 가승인 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayTemporary.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 스마트오더 상세 레이어 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/sale/cmmSalePopup/dayPayInfo/dayVorder.jsp">--%>
<%--  <c:param name="menuCd" value="${menuCd}"/>--%>
<%--  <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>
<%--&lt;%&ndash; //결제수단 팝업 레이어 &ndash;%&gt;--%>