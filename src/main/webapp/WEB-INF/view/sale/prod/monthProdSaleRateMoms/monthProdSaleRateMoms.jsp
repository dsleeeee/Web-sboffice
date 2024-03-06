<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.session.jspInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="monthProdSaleRateMomsCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="monthProdSaleRateMoms.monthProdSaleRateMoms"/></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('monthProdSaleRateMomsCtrl')">
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
                    <%-- 조회월 --%>
                    <th><s:message code="cmm.search.month"/></th>
                    <td>
                        <div class="sb-select">
                            <span class="txtIn"><input id="startMonth" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="endMonth" class="w110px"></span>
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
                    <%-- 일자표시옵션 --%>
                    <th><s:message code="prodSaleRate.dayOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchDayOption"
                                    ng-model="dayOption"
                                    items-source="_getComboData('srchDayOption')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchDayOptionCombo">
                            </wj-combo-box>
                        </div>
                    </td>
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
                                    control="srchProdOptionCombo"
                                    selected-index="3">
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
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
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
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="monthProdSaleRateMomsStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="monthProdSaleRateMomsStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                       <%-- 상품브랜드 --%>
                        <th><s:message code="cmm.moms.prodHqBrand"/></th>
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
                            <jsp:param name="targetId" value="monthProdSaleRateMomsSelect"/>
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
              <th><s:message code="cmm.moms.momsAcShop"/></th>
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
              <th><s:message code="cmm.moms.momsAreaFg"/></th>
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
              <th><s:message code="cmm.moms.momsCommercial"/></th>
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
              <th><s:message code="cmm.moms.momsShopType"/></th>
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
              <th><s:message code="cmm.moms.momsStoreManageType"/></th>
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
                <%-- 그룹 --%>
                <th><s:message code="cmm.moms.branch"/></th>
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
                                control="srchMomsStoreFg01Combo">
                            </wj-combo-box>
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
            <%-- 분할 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload('2')"><s:message code="cmm.excel.downDivision"/></button>
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()"><s:message code="cmm.excel.downCurrent"/></button>
            <%-- 조회조건 엑셀다운로드 --%>
            <%--<button class="btn_skyblue ml5 fr" ng-click="excelDownload('1')"><s:message code="cmm.excel.downCondition"/></button>--%>
        </div>

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
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleYm"/>" binding="saleYm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.lClassNm"/>" binding="lClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.mClassNm"/>" binding="mClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.sClassNm"/>" binding="sClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>

                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

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
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <ul id="monthProdSaleRateMomsCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="wj-gridWrap" style="display: none;"  ng-controller="monthProdSaleRateMomsExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleYm"/>" binding="saleYm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.branchNm"/>" binding="branchNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.lClassNm"/>" binding="lClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.mClassNm"/>" binding="mClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.sClassNm"/>" binding="sClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.selTypeFg"/>" binding="selTypeFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodRankMoms.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

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
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
    var menuCd = "${menuCd}";
    var menuNm = "${menuNm}";
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    // 콤보박스 데이터
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
    var dlvrInFgColNm = '${dlvrInFgColNm}';
    var arrDlvrInFgCol = dlvrInFgCol.split(',');
    var arrDlvrInFgColNm = dlvrInFgColNm.split(',');

    // header값 셋팅을 위한 변수
    var vDlvrOrderFg = ["stin", "dlvr", "pack"];
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/monthProdSaleRateMoms/monthProdSaleRateMoms.js?ver=20240221.01" charset="utf-8"></script>