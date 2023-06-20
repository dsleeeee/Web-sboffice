<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="sideMenuSalePriceCtrl">
  <%--searchTbl--%>
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('sideMenuSalePriceCtrl')">
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
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장선택 --%>
          <tr>
            <th><s:message code="sideMenuSalePrice.store" /></th>
            <td colspan="3">
              <%-- 매장선택 모듈 사용시 include --%>
              <c:if test="${momsEnvstVal == '0'}">
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                  <jsp:param name="targetId" value="sideMenuSalePriceStore"/>
                </jsp:include>
              </c:if>
              <c:if test="${momsEnvstVal == '1'}">
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreSMoms.jsp" flush="true">
                  <jsp:param name="targetId" value="sideMenuSalePriceStore"/>
                </jsp:include>
              </c:if>
              <%--// 매장선택 모듈 사용시 include --%>
            </td>
          </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="sideMenuSalePriceStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <%-- 선택그룹코드 --%>
        <th>
          <s:message code="sideMenuSalePrice.sdselGrpCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 선택그룹명 --%>
        <th>
          <s:message code="sideMenuSalePrice.sdselGrpNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 선택분류코드 --%>
        <th>
          <s:message code="sideMenuSalePrice.sdselClassCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 선택분류명 --%>
        <th>
          <s:message code="sideMenuSalePrice.sdselClassNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
          <c:if test="${brandUseFg == '1'}">
              <%-- 상품브랜드 --%>
              <th><s:message code="salePriceResve.prodHqBrand"/></th>
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
          </c:if>
          <th><s:message code="sideMenuSalePrice.prodClass" /></th>
          <td>
              <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                     placeholder="선택" readonly />
              <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
              <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
          </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th>
          <s:message code="sideMenuSalePrice.prodCd" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 상품명 --%>
        <th>
          <s:message code="sideMenuSalePrice.prodNm" />
        </th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      </tbody>
    </table>
      <table class="searchTbl mt10" id="tblChange">
          <colgroup>
              <col class="w13" />
              <col class="w87" />
          </colgroup>
          <tbody>
          <%--판매가--%>
          <tr class="brt">
              <th>
                  <s:message code="salePriceManage.salePrice" />
              </th>
              <th class="oh gr">
                  <div class="sb-select fl">
                        <span>
                            <input type="text" maxlength="10" numberOnly class="inSty2 w80px" id="inputSaleAmt"ng-model="inputSaleAmt" />
                        </span>
                  </div>
                  <div class="sb-select fl w5px mr5 mt10">
                      <span>원</span>
                  </div>
                  <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
              </th>
          </tr>
          <tr>
              <th colspan="2">
                  <p class="s12 bk lh20">
                      체크박스에서 선택된 항목만 일괄적용 됩니다.
                  </p>
              </th>
          </tr>
          </tbody>
      </table>


    <div class="mt10 oh sb-select dkbr">
      <button class="btn_skyblue fr" ng-click="saveAddProdUprc()">
        <s:message code="cmm.save" />
      </button>
    </div>

  <%--//searchTbl--%>
    <div class="wj-TblWrapBr mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 330px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                sticky-headers="true"
                control="flex"
                initialized="initGrid(s,e)"
                item-formatter="_itemFormatter"
                frozen-columns="1">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.store"/>" binding="storeCd" width="100" align="center" is-read-only="true" visible="false" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselGrpCd"/>" binding="sdselGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.fixProdFg"/>" binding="grpFixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselTypeFg"/>" binding="sdselTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.sdselQty"/>" binding="sdselQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.requireYn"/>" binding="requireYn" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.prodClass"/>" binding="prodClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.addProdUprc"/>" binding="addProdUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.saleUprc"/>" binding="saleUprc" width="100" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.addProdQty"/>" binding="addProdQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="sideMenuSalePrice.fixProdFg"/>" binding="fixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="sideMenuSalePriceCtrlPager" data-size="10">
      </ul>
    </div>
    <%-- //페이지 리스트 --%>
</div>
<script type="text/javascript" src=/resource/solbipos/js/base/price/sideMenuSalePrice/sideMenuSalePrice.js?ver=20230502.03" charset="utf-8"></script>

<script>
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    var momsEnvstVal = "${momsEnvstVal}";
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
        });
    });
</script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>