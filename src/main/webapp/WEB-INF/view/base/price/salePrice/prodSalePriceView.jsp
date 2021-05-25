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
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="salePrice.prodSalePrice" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodSalePriceCtrl', 1)">
        <s:message code="cmm.search" />
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
   </tbody>
  </table>
  <%--//searchTbl--%>


  <table class="searchTbl mt10">
    <colgroup>
      <col class="w100" />
    </colgroup>
    <tbody>
    <tr class="brt">
      <th class="oh gr">
        <div class="sb-select fl w130 mr5">
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
          <input type="text" maxlength="10" numberOnly class="inSty2 w130" id="inputSaleAmt"ng-model="prodInfo.inputSaleAmt" ng-readonly="inputSaleAmtReadOnly" />
        </span> 원
        <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
        <a href="#" class="btn_grayS" style="display:none;" ng-click="changeExcelAmt()">엑셀판매가 일괄적용</a> <!-- // todo -->
        <p class="s12 bk mt10 lh20">
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hqSaleUprc"/>" binding="hqSaleUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.costUprc"/>" binding="costUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.saleUprcP"/>" binding="saleUprcP" width="*" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.saleUprc"/>" binding="saleUprc" width="*" align="right" max-length="10" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.saleUprcAmt"/>" binding="saleUprcAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" width="*" visible="false" ></wj-flex-grid-column>
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
  // console.log('priceEnvstVal >> ', priceEnvstVal);
  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
    });
  });
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/prodSalePrice.js?ver=20210426.01" charset="utf-8"></script>
