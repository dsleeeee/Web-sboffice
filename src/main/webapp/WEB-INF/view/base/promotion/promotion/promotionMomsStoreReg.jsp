<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="promotionMomsStoreRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="promotionMomsStoreRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="promotion.storeAdd" />
            <a href="" class="wj-hide btn_close" ng-click="closeMomsStoreReg()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
              <tr>
                  <th><s:message code="promotion.storeCd" /></th>
                  <td>
                      <input type="text" id="srchMomsStoreCd" onkeyup="fnNxBtnSearch('3');"/>
                  </td>
                  <th><s:message code="promotion.storeNm" /></th>
                  <td>
                      <input type="text" id="srchMomsStoreNm" onkeyup="fnNxBtnSearch('3');"/>
                  </td>
              </tr>
              <tr>
                <%-- 매장브랜드 --%>
                <th><s:message code="promotion.storeHqBrand" /></th>
                <td>
                    <div class="sb-select w100">
                        <wj-combo-box
                            id="srchMomsStoreHqBrand"
                            items-source="_getComboData('srchMomsStoreHqBrand')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchMomsStoreHqBrandCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 지사 --%>
                <th><s:message code="promotion.branch"/></th>
                <td>
                    <div class="sb-select w100">
                        <wj-combo-box
                            id="srchMomsBranch"
                            items-source="_getComboData('srchMomsBranch')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchMomsBranchCombo">
                        </wj-combo-box>
                    </div>
                </td>
              </tr>
              <tr>
                  <%-- 팀별 --%>
                  <th><s:message code="promotion.team"/></th>
                  <td>
                      <div class="sb-select w100">
                          <wj-combo-box
                              id="srchMomsTeam"
                              items-source="_getComboData('srchMomsTeam')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchMomsTeamCombo">
                          </wj-combo-box>
                      </div>
                  </td>
                  <%-- AC점포별 --%>
                  <th><s:message code="promotion.acShop"/></th>
                  <td>
                      <div class="sb-select w100">
                          <wj-combo-box
                              id="srchMomsAcShop"
                              items-source="_getComboData('srchMomsAcShop')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchMomsAcShopCombo">
                          </wj-combo-box>
                      </div>
                  </td>
              </tr>
              <tr>
                  <%-- 지역구분 --%>
                  <th><s:message code="promotion.areaFg"/></th>
                  <td>
                      <div class="sb-select w100">
                           <wj-combo-box
                               id="srchMomsAreaFg"
                               items-source="_getComboData('srchMomsAreaFg')"
                               display-member-path="name"
                               selected-value-path="value"
                               is-editable="false"
                               control="srchMomsAreaFgCombo">
                           </wj-combo-box>
                      </div>
                  </td>
                  <%-- 상권 --%>
                  <th><s:message code="promotion.commercial"/></th>
                  <td>
                      <div class="sb-select w100">
                         <wj-combo-box
                             id="srchMomsCommercial"
                             items-source="_getComboData('srchMomsCommercial')"
                             display-member-path="name"
                             selected-value-path="value"
                             is-editable="false"
                             control="srchMomsCommercialCombo">
                         </wj-combo-box>
                      </div>
                  </td>
              </tr>
              <tr>
                  <%-- 점포유형 --%>
                  <th><s:message code="promotion.shopType"/></th>
                  <td>
                      <div class="sb-select w100">
                           <wj-combo-box
                               id="srchMomsShopType"
                               items-source="_getComboData('srchMomsShopType')"
                               display-member-path="name"
                               selected-value-path="value"
                               is-editable="false"
                               control="srchMomsShopTypeCombo">
                           </wj-combo-box>
                      </div>
                  </td>
                  <%-- 매장관리타입 --%>
                  <th><s:message code="promotion.storeManageType"/></th>
                  <td>
                      <div class="sb-select w100">
                           <wj-combo-box
                               id="srchMomsStoreManageType"
                               items-source="_getComboData('srchMomsStoreManageType')"
                               display-member-path="name"
                               selected-value-path="value"
                               is-editable="false"
                               control="srchMomsStoreManageTypeCombo">
                           </wj-combo-box>
                      </div>
                  </td>
              </tr>
              </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <button class="btn_skyblue" id="btnInsertStoreAll" ng-click="btnMomsInsertStoreAll()"><s:message code="promotion.setAllStore" /></button>
                <button class="btn_skyblue" id="nxBtnSearch3" ng-click="btnMomsSearchStore()"><s:message code="cmm.search" /></button>
                <button class="btn_skyblue ml5 fr" id="btnInsertStore" ng-click="btnMomsInsertStore()"><s:message code="cmm.add"/></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="promotion.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="promotion.storeNm"/>" binding="storeNm" width="300" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="promotion.sysStatFg"/>" binding="sysStatFg" width="100"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/promotionMomsStoreReg.js?ver=20221202.01" charset="utf-8"></script>