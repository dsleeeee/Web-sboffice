<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="prodEnvstVal" value="${prodEnvstVal}" />
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" id="prodSalePriceArea" ng-controller="prodSalePriceCtrl">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="salePrice.prodSalePrice" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodSalePriceCtrl', 1)">
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
    <%-- 상품선택 --%>
    <tr>
      <th><s:message code="salePrice.select.prod" /></th>
      <td>
        <%-- 상품선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchProductS.jsp" flush="true">
          <jsp:param name="targetId" value="prod"/>
        </jsp:include>
        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <th><s:message code="salePrice.select.store" /></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="store"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
    </tr>
    <c:if test="${subPriceFg == '1'}">
      <tr>
        <th><input type="checkbox" id="prodSaleUprcApply" ng-model="prodSaleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
        <td><s:message code="salePrice.saleUprcApply"/></td>
      </tr>
    </c:if>
   </tbody>
  </table>
  <%--//searchTbl--%>


  <table class="searchTbl mt10" id="tblProdChange" style="display: none;">
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
        <div class="sb-select fl w120px mr5">
          <wj-combo-box
                  id="saleAmtOption"
                  ng-model="prodInfo.saleAmtOption"
                  control="saleAmtOptionCombo"
                  items-source="_getComboData('saleAmtOption')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)"
                  selected-index-changed="setSelectedSaleAmtOption(s)">
          </wj-combo-box>
        </div>
        <span>
          <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputSaleAmt"ng-model="prodInfo.inputSaleAmt" ng-readonly="inputSaleAmtReadOnly" />
        </span> 원
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
            <div class="sb-select fl w120px mr5">
              <wj-combo-box
                      id="stinSaleUprcOption"
                      ng-model="prodInfo.stinSaleUprcOption"
                      control="stinSaleUprcOptionCombo"
                      items-source="_getComboData('stinSaleUprcOption')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setSelectedStinSaleUprcOption(s)">
              </wj-combo-box>
            </div>
            <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputStinSaleUprc" ng-model="prodInfo.inputStinSaleUprc" ng-readonly="inputStinSaleUprcReadOnly" />
            </span> 원
            <a href="#" class="btn_grayS ml10" ng-click="changeStinSaleUprc()">일괄적용</a>
          </th>
        </tr>
        <%--배달-판매가--%>
        <tr class="brt">
          <th>
            <s:message code="salePrice.dlvrSaleUprc" />
          </th>
          <th class="oh gr">
            <div class="sb-select fl w120px mr5">
              <wj-combo-box
                      id="dlvrSaleUprcOption"
                      ng-model="prodInfo.dlvrSaleUprcOption"
                      control="dlvrSaleUprcOptionCombo"
                      items-source="_getComboData('dlvrSaleUprcOption')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setSelectedDlvrSaleUprcOption(s)">
              </wj-combo-box>
            </div>
            <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputDlvrSaleUprc" ng-model="prodInfo.inputDlvrSaleUprc" ng-readonly="inputDlvrSaleUprcReadOnly" />
            </span> 원
            <a href="#" class="btn_grayS ml10" ng-click="changeDlvrSaleUprc()">일괄적용</a>
          </th>
        </tr>
        <%--포장-판매가--%>
        <tr class="brt">
          <th>
            <s:message code="salePrice.packSaleUprc" />
          </th>
          <th class="oh gr">
            <div class="sb-select fl w120px mr5">
              <wj-combo-box
                      id="packSaleUprcOption"
                      ng-model="prodInfo.packSaleUprcOption"
                      control="packSaleUprcOptionCombo"
                      items-source="_getComboData('packSaleUprcOption')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setSelectedPackSaleUprcOption(s)">
              </wj-combo-box>
            </div>
            <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputPackSaleUprc" ng-model="prodInfo.inputPackSaleUprc" ng-readonly="inputPackSaleUprcReadOnly" />
            </span> 원
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
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
    <button class="btn_skyblue fr" ng-click="saveProdPrice()">
      <s:message code="cmm.save" />
    </button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10">
    <div id="theGridProd" style="height: 370px;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

        <%--본사마진금액, 본사마진율, 매장마진금액, 매장마진율 visible false 처리 / 추후에 수불 화면 개발 시 재 오픈 : 2019-08-07 이다솜 --%>
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeCd"/>" binding="storeCd" width="63" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeNm"/>" binding="storeNm" width="80" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.costUprc"/>" binding="costUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="saleUprcP" width="56" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="saleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.saleUprcAmt"/>" binding="saleUprcAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" width="*" visible="false" ></wj-flex-grid-column>

        <c:if test="${subPriceFg == '1'}">
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="stinSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="dlvrSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="packSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
        </c:if>

        <wj-flex-grid-column header="<s:message code="salePrice.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="prodSalePriceCtrl"/>
      </jsp:include>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prodSalePriceCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>
  var priceEnvstVal = "${priceEnvstVal}";
  var subPriceFg = "${subPriceFg}";
  var coercionFg = "${coercionFg}";
  // console.log('priceEnvstVal >> ', priceEnvstVal);
  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
    });
  });
</script>

<script>
  <%-- 가격관리구분 --%>
  var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/prodSalePrice.js?ver=20210526.12" charset="utf-8"></script>
