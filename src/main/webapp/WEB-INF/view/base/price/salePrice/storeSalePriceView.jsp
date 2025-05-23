<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<%--<c:set var="prodEnvstVal" value="${prodEnvstVal}" />--%>
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" id="storeSalePriceArea" style="display:none;padding: 10px 20px 40px;">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div ng-controller="storeSalePriceCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="salePrice.storeSalePrice" /></a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('storeSalePriceCtrl', 1)">
          <s:message code="cmm.search" />
        </button>
        <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
          <s:message code="salePrice.select.changeAll" />
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
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td>
          <%-- 매장선택 모듈 사용시 include --%>
          <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
            <jsp:param name="targetTypeFg" value="S"/>
            <jsp:param name="targetId" value="searchStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 사용시 include --%>
        </td>
        <th><s:message code="salePrice.select.prodClass" /></th>
        <td>
          <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                 placeholder="선택" readonly />
          <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>
      </tr>
      <%-- 판매가 적용 옵션 (//todo 엑셀업로드 기능 추가시, 해당 기능 추가) --%>
      <tr style="display:none;">
        <th><s:message code="salePrice.apply.saleUprc" /></th>
        <td colspan="3">
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th>
          <s:message code="salePrice.prodCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
        </td>
        <%-- 상품명 --%>
        <th>
          <s:message code="salePrice.prodNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
        </td>
      </tr>
      <c:if test="${subPriceFg == '1'}">
        <tr>
          <th><input type="checkbox" id="storeSaleUprcApply" ng-model="storeSaleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
          <td><s:message code="salePrice.saleUprcApply"/></td>
        </tr>
      </c:if>
      <c:if test="${brandUseFg == '1'}">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="kioskKeyMap.prodHqBrand" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchProdHqBrandCd"
                        items-source="_getComboData('srchProdHqBrandCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchProdHqBrandCdCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
        </c:if>
      </c:if>
     </tbody>
    </table>
    <%--//searchTbl--%>

    <table class="searchTbl mt10" id="tblStoreChange" style="display: none;">
      <colgroup>
        <col class="w13" />
        <col class="w87" />
      </colgroup>
      <tbody>
      <%--판매가--%>
      <tr class="brt">
        <th>
          <s:message code="salePrice.salePrice" />
        </th>
        <th class="oh gr">
          <%-- 매장판매가/본사판매가 선택 --%>
          <div class="sb-select fl w120px mr5">
            <wj-combo-box
                    id="saleAmtOption"
                    ng-model="prodInfo.saleAmtOption"
                    control="saleAmtOptionCombo"
                    items-source="_getComboData('saleAmtOption')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <%-- 판매가 변경 비율 --%>
          <div class="sb-select fl">
            <span>
              <input type="text" class="inSty2 w80px" id="inputSaleRate" ng-model="prodInfo.inputSaleRate" ng-readonly="inputSaleRateReadOnly" />
            </span>
          </div>
          <div class="sb-select fl w5px mr5 mt10">
            <span>%</span>
          </div>
          <%-- 변경 단위 --%>
          <div class="sb-select fl w120px mr5">
            <wj-combo-box
                    id="changeUnit"
                    ng-model="prodInfo.changeUnit"
                    control="changeUnitCombo"
                    items-source="_getComboData('changeUnit')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <%-- 반올림 여부 --%>
          <div class="sb-select fl w100px mr5">
            <wj-combo-box
                    id="changeMode"
                    ng-model="prodInfo.changeMode"
                    control="changeModeCombo"
                    items-source="_getComboData('changeMode')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
          <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
          <a href="#" class="btn_grayS" style="display:none;" ng-click="changeExcelAmt()">엑셀판매가 일괄적용</a> <!-- // todo -->
        </th>
      </tr>
      <c:if test="${subPriceFg == '1'}">
          <%--내점-판매가--%>
          <tr class="brt">
            <th>
              <s:message code="salePrice.stinSaleUprc" />
            </th>
            <th class="oh gr">
              <%-- 매장판매가/본사판매가 선택 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="storeStinSaleUprcOption"
                        ng-model="prodInfo.storeStinSaleUprcOption"
                        control="storeStinSaleUprcOptionCombo"
                        items-source="_getComboData('storeStinSaleUprcOption')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 변경 비율 --%>
              <div class="sb-select fl">
                <span>
                  <input type="text" class="inSty2 w80px" id="inputStinSaleUprcRate" ng-model="prodInfo.inputStinSaleUprcRate" ng-readonly="inputStinSaleUprcRateReadOnly" />
                </span>
              </div>
              <div class="sb-select fl w5px mr5 mt10">
                <span>%</span>
              </div>
              <%-- 변경 단위 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="stinSaleUprcChangeUnit"
                        ng-model="prodInfo.stinSaleUprcChangeUnit"
                        control="stinSaleUprcChangeUnitCombo"
                        items-source="_getComboData('stinSaleUprcChangeUnit')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 반올림 여부 --%>
              <div class="sb-select fl w100px mr5">
                <wj-combo-box
                        id="stinSaleUprcChangeMode"
                        ng-model="prodInfo.stinSaleUprcChangeMode"
                        control="stinSaleUprcChangeModeCombo"
                        items-source="_getComboData('stinSaleUprcChangeMode')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <a href="#" class="btn_grayS ml10" ng-click="changeStinSaleUprc()">일괄적용</a>
            </th>
          </tr>
          <%--배달-판매가--%>
          <tr class="brt">
            <th>
              <s:message code="salePrice.dlvrSaleUprc" />
            </th>
            <th class="oh gr">
              <%-- 매장판매가/본사판매가 선택 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="storeDlvrSaleUprcOption"
                        ng-model="prodInfo.storeDlvrSaleUprcOption"
                        control="storeDlvrSaleUprcOptionCombo"
                        items-source="_getComboData('storeDlvrSaleUprcOption')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 변경 비율 --%>
              <div class="sb-select fl">
                <span>
                  <input type="text" class="inSty2 w80px" id="inputDlvrSaleUprcRate" ng-model="prodInfo.inputDlvrSaleUprcRate" ng-readonly="inputDlvrSaleUprcRateReadOnly" />
                </span>
              </div>
              <div class="sb-select fl w5px mr5 mt10">
                <span>%</span>
              </div>
              <%-- 변경 단위 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="dlvrSaleUprcChangeUnit"
                        ng-model="prodInfo.dlvrSaleUprcChangeUnit"
                        control="dlvrSaleUprcChangeUnitCombo"
                        items-source="_getComboData('dlvrSaleUprcChangeUnit')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 반올림 여부 --%>
              <div class="sb-select fl w100px mr5">
                <wj-combo-box
                        id="dlvrSaleUprcChangeMode"
                        ng-model="prodInfo.dlvrSaleUprcChangeMode"
                        control="dlvrSaleUprcChangeModeCombo"
                        items-source="_getComboData('dlvrSaleUprcChangeMode')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <a href="#" class="btn_grayS ml10" ng-click="changeDlvrSaleUprc()">일괄적용</a>
            </th>
          </tr>
          <%--포장-판매가--%>
          <tr class="brt">
            <th>
              <s:message code="salePrice.packSaleUprc" />
            </th>
            <th class="oh gr">
              <%-- 매장판매가/본사판매가 선택 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="storePackSaleUprcOption"
                        ng-model="prodInfo.storePackSaleUprcOption"
                        control="storePackSaleUprcOptionCombo"
                        items-source="_getComboData('storePackSaleUprcOption')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 변경 비율 --%>
              <div class="sb-select fl">
                <span>
                  <input type="text" class="inSty2 w80px" id="inputPackSaleUprcRate" ng-model="prodInfo.inputPackSaleUprcRate" ng-readonly="inputPackSaleUprcRateReadOnly" />
                </span>
              </div>
              <div class="sb-select fl w5px mr5 mt10">
                <span>%</span>
              </div>
              <%-- 변경 단위 --%>
              <div class="sb-select fl w120px mr5">
                <wj-combo-box
                        id="packSaleUprcChangeUnit"
                        ng-model="prodInfo.packSaleUprcChangeUnit"
                        control="packSaleUprcChangeUnitCombo"
                        items-source="_getComboData('packSaleUprcChangeUnit')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <%-- 반올림 여부 --%>
              <div class="sb-select fl w100px mr5">
                <wj-combo-box
                        id="packSaleUprcChangeMode"
                        ng-model="prodInfo.packSaleUprcChangeMode"
                        control="packSaleUprcChangeModeCombo"
                        items-source="_getComboData('packSaleUprcChangeMode')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
              <a href="#" class="btn_grayS ml10" ng-click="changePackSaleUprc()">일괄적용</a>
            </th>
          </tr>
      </c:if>
      <tr>
        <th colspan="2">
          <p class="s12 bk lh20">
            체크박스에서 선택된 항목만 일괄적용 됩니다.
          </p>
        </th>
      </tr>
      </tbody>
    </table>

    <%--추후에 수불 화면 개발 시 재 오픈 : 2019-08-07 이다솜 --%>
    <%--<table class="searchTbl mt10">
      <colgroup>
        <col class="w100" />
      </colgroup>
      <tbody>
      <tr class="brt">
        <th class="oh gr">
          본사판매가
          <span><input type="text" class="inSty2 w100px" ng-model="prodInfo.saleUprc" readonly/></span> 원
          <span class="ml20 s12 lh30">입수</span>
          <span><input type="text" class="inSty2 w100px" ng-model="prodInfo.poUnitQty" readonly/></span> 원
          <span class="ml20 s12 lh30">대표공급가</span>
          <span><input type="text" class="inSty2 w100px" ng-model="prodInfo.splyUprc" readonly/></span> 원
          <span class="ml20 s12 lh30">본사원가</span>
          <span><input type="text" class="inSty2 w100px" ng-model="prodInfo.costUprc" readonly/></span> 원
          <p class="s12 bk mt10 lh20">
            본사마진금액 = 매장공급가 – 본사원가<br />
            본사마진율 = 본사마진금액 / 본사원가 * 100<br />
            매장마진금액 = (매장판매가 * 입수) – 매장공급가<br />
            매장마진율 = (매장마진금액 - 매장공급가) / (매장판매가 * 입수) * 100<br />
            <b>가격통제가 본사인 상품만 수정 가능합니다.</b> <br />
          </p>
        </th>
      </tr>
      </tbody>
    </table>--%>

    <div class="mt10 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
              class="w100px fl"
              id="listScaleBox2"
              ng-model="listScale"
              control="listScaleCombo2"
              items-source="_getComboData('listScaleBox2')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
      </wj-combo-box>

      <%-- 조회조건 엑셀 다운로드 --%>
      <button class="btn_skyblue fr" ng-click="excelDownload()">
        <s:message code="cmm.excel.downCondition" />
      </button>
      <%-- 저장 --%>
      <button class="btn_skyblue mr5 fr" ng-click="saveProdPrice()">
        <s:message code="cmm.save" />
      </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
      <div id="theGridStore" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                ime-enabled="true"
                id="wjGridStoreSalePriceArea">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="saleUprc" width="56" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>

          <c:if test="${subPriceFg == '1'}">
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storePackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
          </c:if>

          <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <%--<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
          <%--<jsp:param name="pickerTarget" value="storeSalePriceCtrl"/>--%>
        <%--</jsp:include>--%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="storeSalePriceCtrlPager" data-size="10">
      </ul>
    </div>
    <%--//페이지 리스트--%>
  </div>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="storeSalePriceExcelCtrl">
      <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                ime-enabled="true"
                id="wjGridExcelList">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="saleUprc" width="56" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>

          <c:if test="${subPriceFg == '1'}">
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storePackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" visible="false"></wj-flex-grid-column>
          </c:if>

          <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <%--<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
        <%--<jsp:param name="pickerTarget" value="storeSalePriceCtrl"/>--%>
        <%--</jsp:include>--%>
      </div>
    </div>

</div>

<script>
  var priceEnvstVal = "${priceEnvstVal}";
  var subPriceFg = "${subPriceFg}";
  var coercionFg = "${coercionFg}";
  <%-- 가격관리구분 --%>
  var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/storeSalePrice.js?ver=20250430.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>