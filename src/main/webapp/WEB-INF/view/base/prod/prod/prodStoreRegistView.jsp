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
      <div ng-controller="regStoreCtrl">
        <table class="tblType01">
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
            <td><input type="text" id="srchRegStoreCd" ng-model="storeCd" /></td>
            <th><s:message code="prod.storeNm"/></th>
            <td><input type="text" id="srchRegStoreNm" ng-model="storeNm" /></td>
          </tr>
          <%-- 아티제용 검색조건--%>
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
          <%-- 아티제 외 검색조건--%>
          <c:if test="${hqOfficeCd ne 'A0001'}">
            <c:if test="${brandUseFg == '1'}">
             <c:if test="${sessionInfo.orgnFg == 'HQ'}">
               <tr>
                 <%-- 매장브랜드 --%>
                 <th><s:message code="cmm.moms.storeHqBrand" /></th>
                 <td>
                   <div class="sb-select">
                     <wj-combo-box
                       id="srchPsrStoreHqBrandCd"
                       ng-model="storeHqBrandCd"
                       items-source="_getComboData('srchPsrStoreHqBrandCd')"
                       display-member-path="name"
                       selected-value-path="value"
                       is-editable="false"
                       control="srchPsrStoreHqBrandCdCombo"
                       selected-index-changed="setPsrStoreHqBrandCd(s)">
                     </wj-combo-box>
                     <input type="hidden" id="hdSrchPsrStoreHqBrandCd" />
                   </div>
                 </td>
                 <td></td>
                 <td></td>
               </tr>
             </c:if>
            </c:if>
          </c:if>
          <c:if test="${subPriceFg == '1'}">
            <tr>
              <th><input type="checkbox" id="prodSaleUprcApply" ng-model="prodSaleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
              <td><s:message code="salePrice.saleUprcApply"/></td>
            </tr>
          </c:if>
          </tbody>
        </table>
        <c:if test="${momsEnvstVal == '1'}">
          <table class="searchTbl" id="tblSearchAddShowReg" style="display: none;">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
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
                          control="srchMomsTeamCombo"
                          selected-index-changed="setPsrMomsTeam(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsTeam" />
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
                          control="srchMomsAcShopCombo"
                          selected-index-changed="setPsrMomsAcShop(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsAcShop" />
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
                          control="srchMomsAreaFgCombo"
                          selected-index-changed="setPsrMomsAreaFg(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsAreaFg" />
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
                          control="srchMomsCommercialCombo"
                          selected-index-changed="setPsrMomsCommercial(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsCommercial" />
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
                          control="srchMomsShopTypeCombo"
                          selected-index-changed="setPsrMomsShopType(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsShopType" />
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
                          control="srchMomsStoreManageTypeCombo"
                          selected-index-changed="setPsrMomsStoreManageType(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrMomsStoreManageType" />
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
                          control="srchBranchCdCombo"
                          selected-index-changed="setPsrBranchCd(s)">
                  </wj-combo-box>
                  <input type="hidden" id="hdSrchPsrBranchCd" />
                </div>
              </td>
              <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
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
                            control="srchMomsStoreFg01Combo"
                            selected-index-changed="setPsrMomsStoreFg01(s)">
                    </wj-combo-box>
                    <input type="hidden" id="hdSrchPsrMomsStoreFg01" />
                  </div>
                </td>
              </c:if>
              <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
                <td></td>
                <td></td>
              </c:if>
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
                            control="srchMomsStoreFg02Combo"
                            selected-index-changed="setPsrMomsStoreFg02(s)">
                    </wj-combo-box>
                    <input type="hidden" id="hdSrchPsrMomsStoreFg02" />
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
                            control="srchMomsStoreFg03Combo"
                            selected-index-changed="setPsrMomsStoreFg03(s)">
                    </wj-combo-box>
                    <input type="hidden" id="hdSrchPsrMomsStoreFg03" />
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
                            control="srchMomsStoreFg04Combo"
                            selected-index-changed="setPsrMomsStoreFg04(s)">
                    </wj-combo-box>
                    <input type="hidden" id="hdSrchPsrMomsStoreFg04" />
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
                            control="srchMomsStoreFg05Combo"
                            selected-index-changed="setPsrMomsStoreFg05(s)">
                    </wj-combo-box>
                    <input type="hidden" id="hdSrchPsrMomsStoreFg05" />
                  </div>
                </td>
              </tr>
            </c:if>
            </tbody>
          </table>
        </c:if>
        <%-- 조회 --%>
        <div class="mt10 tr">
          <c:if test="${momsEnvstVal == '1'}">
            <%-- 확장조회 --%>
            <button class="btn_skyblue mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeReg()">
              <s:message code="cmm.search.addShow" />
            </button>
          </c:if>
          <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regStoreCtrl', 1)" ><s:message code="cmm.search" /></button>
        </div>
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
                      item-formatter="_itemFormatter"
                      ime-enabled="true">

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
        <%--- //적용매장 그리드 --%>

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
        <%--- //미적용매장 그리드 --%>

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodStoreRegist.js?ver=20240221.01" charset="utf-8"></script>
