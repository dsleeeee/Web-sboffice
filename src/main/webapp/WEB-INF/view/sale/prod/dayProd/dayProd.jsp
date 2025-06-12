<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">
    <div ng-controller="dayProdCtrl">
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('dayProdCtrl', 1)">
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
                    <th><s:message code="prod.prodClass" /></th>
                    <td>
                        <jsp:include page="/WEB-INF/view/application/layer/searchProdClassCd3.jsp" flush="true">
                            <jsp:param name="targetId" value="dayProdSelectClass"/>
                        </jsp:include>
                    </td>
                </tr>
                <tr>
                    <%-- 일자표시옵션 --%>
                    <th><s:message code="dayProd.dayOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchDayOptionCombo"
                                    ng-model="dayOption"
                                    items-source="_getComboData('dayOptionCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchDayOptionCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상품표시옵션 --%>
                    <th><s:message code="dayProd.prodOption"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdOptionCombo"
                                    ng-model="prodOption"
                                    items-source="_getComboData('prodOptionCombo')"
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
                    <th><s:message code="dayProd.prodCd" /></th>
                    <td>
                        <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="dayProd.prodNm" /></th>
                    <td>
                        <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
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
                                <jsp:param name="targetId" value="dayProdStore"/>
                            </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="dayProdStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                       <%-- 상품브랜드 --%>
                        <th><s:message code="cmm.moms.prodHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdHqBrandCombo"
                                        ng-model="prodHqBrandCd"
                                        items-source="_getComboData('prodHqBrandCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdHqBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                    <%-- 상품 --%>
                    <th><s:message code="dayProd.prod"/></th>
                    <td>
                        <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                                         targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                         displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                         modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                         closeFunc - 팝업 닫기시 호출할 함수--%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                            <jsp:param name="targetId" value="dayProdSelect"/>
                        </jsp:include>
                        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                        <td></td>
                        <td></td>
                    </c:if>
                </tr>
                <tr>
                    <th><s:message code="dayProd.saleAmtFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchSaleAmtFgCombo"
                                    ng-model="saleAmtFg"
                                    items-source="_getComboData('saleAmtFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchSaleAmtFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td colspan="2">
                        <button class="btn_skyblue" ng-click="remark1()"><s:message code="dayProd.saleAmtFg1"/></button>
<%--                        <button class="btn_skyblue" ng-click="remark2()"><s:message code="dayProd.saleAmtFg2"/></button>--%>
<%--                        <button class="btn_skyblue" ng-click="remark3()"><s:message code="dayProd.saleAmtFg3"/></button>--%>
                    </td>
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
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

        <div class="w100 mt10">
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
                    <wj-flex-grid-column header="<s:message code="dayProd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.selTypeFg"/>" binding="selTypeFg" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.sideProdNm"/>" binding="sideProdNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dayProdCtrlPager" data-size="10">
        </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="dayProdExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dayProd.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.yoil"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.dayFrom"/>" binding="dayFrom" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.dayTo"/>" binding="dayTo" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.lClassCd"/>" binding="lClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.mClassCd"/>" binding="mClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.sClassCd"/>" binding="sClassCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.selTypeFg"/>" binding="selTypeFg" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.sideProdNm"/>" binding="sideProdNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.saleQty1"/>" binding="saleQty1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.saleQty2"/>" binding="saleQty2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.saleQty3"/>" binding="saleQty3" width="110" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt1"/>" binding="realSaleAmt1" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt2"/>" binding="realSaleAmt2" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProd.realSaleAmt3"/>" binding="realSaleAmt3" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var userId = "${userId}";

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
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/dayProd/dayProd.js?ver=20250605.01" charset="utf-8"></script>

<%-- 20221117 상품분류 팝업 주석처리함 -> 상품 멀티 팝업에 상품분류가 들어감으로 오류 --%>
<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd2.jsp">
</c:import>

<c:import url="/WEB-INF/view/sale/prod/dayProd/saleAmtFgRemark.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:import url="/WEB-INF/view/sale/prod/dayProd/saleAmtFgRemark3.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>