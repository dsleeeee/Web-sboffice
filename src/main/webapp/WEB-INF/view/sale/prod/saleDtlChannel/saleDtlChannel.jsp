<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="saleDtlChannelView" class="subCon" style="display: none;">
    <div ng-controller="saleDtlChannelCtrl">
        <div class="searchBar">
          <a href="#" class="open fl"><s:message code="saleDtlChannel.saleDtlChannel"/></a>
          <%-- 조회 --%>
          <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('saleDtlChannelCtrl')">
            <s:message code="cmm.search"/>
          </button>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <%-- 확장조회 --%>
            <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
              <s:message code="cmm.search.addShow" />
            </button>
          </c:if>
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
                            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                        </div>
                    </td>
                    <%-- 분류조회 --%>
                    <th><s:message code="prodSaleRate.prodClass" /></th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                                placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <%-- 상품표시옵션 --%>
                    <th><s:message code="prodSaleRate.prodOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdOption"
                                    ng-model="prodOption"
                                    items-source="_getComboData('srchProdOption')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdOptionCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="prodSaleRate.prodCd" /></th>
                    <td>
                        <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="prodSaleRate.prodNm" /></th>
                    <td>
                        <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                </tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 매장브랜드 --%>
                    <th><s:message code="prodSaleRate.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                              id="srchStoreHqBrandCd"
                              items-source="_getComboData('srchStoreHqBrandCd')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장코드 --%>
                    <th><s:message code="cmm.store"/></th>
                    <td>
                      <%-- 매장선택 모듈 싱글 선택 사용시 include
                           param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                        displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                        modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                        closeFunc - 팝업 닫기시 호출할 함수
                      --%>
                      <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                          <jsp:param name="targetId" value="saleDtlChannelStore"/>
                      </jsp:include>
                      <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="saleDtlChannelStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                       <%-- 상품브랜드 --%>
                        <th><s:message code="prodSaleRate.prodHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                    id="srchProdHqBrand"
                                    items-source="_getComboData('srchProdHqBrand')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                    <%-- 상품 --%>
                    <th><s:message code="prodRankMoms.prod"/></th>
                    <td>
                        <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                             targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                             displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                             modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                             closeFunc - 팝업 닫기시 호출할 함수--%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                            <jsp:param name="targetId" value="saleDtlChannelSelect"/>
                        </jsp:include>
                        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                        <td></td>
                        <td></td>
                    </c:if>
                </tr>
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
              <th><s:message code="prodSaleRate.momsTeam"/></th>
              <td>
                <div class="sb-select">
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
              <th><s:message code="prodSaleRate.momsAcShop"/></th>
              <td>
                <div class="sb-select">
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
              <th><s:message code="prodSaleRate.momsAreaFg"/></th>
              <td>
                <div class="sb-select">
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
              <th><s:message code="prodSaleRate.momsCommercial"/></th>
              <td>
                <div class="sb-select">
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
              <th><s:message code="prodSaleRate.momsShopType"/></th>
              <td>
                <div class="sb-select">
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
              <th><s:message code="prodSaleRate.momsStoreManageType"/></th>
              <td>
                <div class="sb-select">
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
            <tr>
              <%-- 지사 --%>
              <th><s:message code="prodSaleRate.branchCd"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                    id="srchBranchCd"
                    items-source="_getComboData('srchBranchCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="srchBranchCdCombo">
                  </wj-combo-box>
                </div>
              </td>
              <td></td>
              <td></td>
            </tr>
            </tbody>
          </table>
        </c:if>

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
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>

                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt1"/>" binding="totSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt2"/>" binding="totSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt3"/>" binding="totSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt1"/>" binding="dcAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt2"/>" binding="dcAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt3"/>" binding="dcAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt1"/>" binding="gaAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt2"/>" binding="gaAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt3"/>" binding="gaAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt1"/>" binding="vatAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt2"/>" binding="vatAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt3"/>" binding="vatAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                  <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  </c:forEach>

                  <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  </c:forEach>

                  <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  </c:forEach>

              </wj-flex-grid>

            </div>
            <%--//위즈모 테이블--%>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <ul id="saleDtlChannelCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

        <%--엑셀 리스트--%>
<%--        <div class="wj-gridWrap" style="display: none;"  ng-controller="saleDtlChannelExcelCtrl">--%>
<%--          <wj-flex-grid--%>
<%--            id="wjGridExcelList"--%>
<%--            autoGenerateColumns="false"--%>
<%--            selection-mode="Row"--%>
<%--            items-source="data"--%>
<%--            control="excelFlex"--%>
<%--            initialized="initGrid(s,e)"--%>
<%--            is-read-only="true"--%>
<%--            item-formatter="_itemFormatter">--%>

<%--            <!-- define columns -->--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>--%>

<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt1"/>" binding="totSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt2"/>" binding="totSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.totSaleAmt3"/>" binding="totSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt1"/>" binding="dcAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt2"/>" binding="dcAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.dcAmt3"/>" binding="dcAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt1"/>" binding="gaAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt2"/>" binding="gaAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.gaAmt3"/>" binding="gaAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt1"/>" binding="vatAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt2"/>" binding="vatAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="saleDtlChannel.vatAmt3"/>" binding="vatAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>

<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packSaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packSaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packSaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packRealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packRealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--              <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packRealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>

<%--              <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="stinDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--            </c:forEach>--%>

<%--            <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="dlvrDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--            </c:forEach>--%>

<%--            <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.saleQty3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}SaleQty3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt1"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt2"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--                <wj-flex-grid-column header="<s:message code="prodSaleRate.realSaleAmt3"/>" binding="packDifg${dlvrInFgCol.dlvrInFg}RealSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>--%>
<%--            </c:forEach>--%>

<%--          </wj-flex-grid>--%>

<%--        </div>--%>
        <%--//엑셀 리스트--%>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/saleDtlChannel/saleDtlChannel.js?ver=20230104.02" charset="utf-8"></script>