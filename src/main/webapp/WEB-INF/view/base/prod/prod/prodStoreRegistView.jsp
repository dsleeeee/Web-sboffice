<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="prodStoreRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:920px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.regStore" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <table class="tblType01" ng-controller="regStoreCtrl">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th>적용대상상품</th>
          <td colspan="3" id="prodTitle"></td>
          <input type="hidden" id="hdSideProdYn" />
          <input type="hidden" id="hdSdselGrpCd" />
          <input type="hidden" id="hdProdClassCd" />
        </tr>
        <tr><%-- 매장 --%>
          <th><s:message code="prod.storeCd"/></th>
          <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
          <th><s:message code="prod.storeNm"/></th>
          <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
        </tr>
        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
          <tr> <%-- 매장상태 --%>
            <th><s:message code="prod.sysStatFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchSysStatFg"
                        ng-model="sysStatFg"
                        items-source="_getComboData('srchSysStatFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
            <th><s:message code="prod.brandNm"/></th>
            <%-- 브랜드명 --%>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchHqBrand"
                        ng-model="hqBrandCd"
                        items-source="_getComboData('srchHqBrand')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
        </c:if>
        <c:if test="${subPriceFg == '1'}">
          <tr>
            <th><input type="checkbox" id="prodSaleUprcApply" ng-model="prodSaleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
            <td><s:message code="salePrice.saleUprcApply"/></td>
          </tr>
        </c:if>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regStoreCtrl', 1)" ><s:message code="cmm.search" /></button>
      </div>

      <%--- 적용매장 그리드 --%>
      <div class="oh mt20">
        <div class="w50 fl" ng-controller="regStoreCtrl">
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.regStore"/></span>
              <span class="fr">
                <a href="#" class="btn_grayS2" ng-click="changeSaleUprc()"><s:message code="prod.change.saleUprc" /></a>
                <a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a>
              </span>
            </div>
            <div id="regStoreGrid" class="mt10" style="height: 360px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="80" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="70" align="right" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprcB"/>" binding="saleUprcB" visible="false" ></wj-flex-grid-column>
                <c:if test="${subPriceFg == '1'}">
                  <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprc" width="70" align="right"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="70" align="right"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprc" width="70" align="right"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprcB" visible="false" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprcB" visible="false" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprcB" visible="false" ></wj-flex-grid-column>
                <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="prod.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="70" is-read-only="true" ></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>

        <%--- 미적용매장 그리드 --%>
        <div class="w50 fr" ng-controller="noRegStoreCtrl">

          <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden;" >
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.noRegStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="prod.regist"/></a></span>
            </div>

            <div id="noRegStoreGrid" class="mt10" style="height: 360px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="80" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="70" align="right"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprcB"/>" binding="saleUprcB" visible="false" ></wj-flex-grid-column>
                <c:if test="${subPriceFg == '1'}">
                  <wj-flex-grid-column header="<s:message code="prod.stinSaleUprc"/>" binding="stinSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.packSaleUprc"/>" binding="packSaleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="prod.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="70" is-read-only="true" ></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
    <%-- //body --%>

  </div>
</wj-popup>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
  <%-- 가격관리구분 --%>
  var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodStoreRegist.js?ver=20210526.05" charset="utf-8"></script>
