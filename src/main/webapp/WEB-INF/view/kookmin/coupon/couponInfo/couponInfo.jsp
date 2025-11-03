<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />


<div class="subCon" ng-controller="couponInfoCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('couponInfoCtrl',1)">
                <s:message code="cmm.search" />
            </button>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </c:if>
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
            <%-- 조회일자 --%>
                <th>
                    <div class="sb-select">
                        <wj-combo-box
                                id="periodType"
                                ng-model="periodType"
                                control="periodTypeCombo"
                                items-source="_getComboData('periodType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setPeriodType(s)">
                        </wj-combo-box>
                    </div>
                </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 쿠폰코드 --%>
            <th>
                <s:message code="couponInfo.coupnCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id=srchCoupnCd ng-model="coupnCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 쿠폰명 --%>
            <th>
                <s:message code="couponInfo.coupnNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchCoupnNm" ng-model="coupnNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
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
                <%-- 매장코드 --%>
                <th>
                    <s:message code="couponInfo.storeCd"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="storeCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="couponInfo.storeNm"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="storeNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="couponInfo.prodCd"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
                <%-- 상품명 --%>
                <th>
                    <s:message code="couponInfo.prodNm"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="couponInfo.partCd"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="srchPartCd" ng-model="partCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="couponInfo.partNm"/>
                </th>
                <td>
                    <div class="sb-select">
                        <input type="text" id="srchPartNm" ng-model="partNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="couponInfo.print" /></button>
        <button class="btn_skyblue fr mr5" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition" /></button>
        <button class="btn_skyblue fr mr5" ng-click="sampleDownload()"><s:message code="cmm.excel.sampleDown" /></button>
        <button class="btn_skyblue fr mr5" ng-click="couponInfoDtl()"><s:message code="couponInfo.couponInfoDtl" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <%--                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="couponInfo.coupnCd"/>"        binding="mergeCoupnCd"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.coupnNm"/>"        binding="coupnNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.usePartCd"/>"      binding="coupnUsePart"      width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.usePartNm"/>"      binding="vendrNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.startUsePeriod"/>" binding="startDate"         width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.endUsePeriod"/>"   binding="endDate"           width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.prodCd"/>"         binding="coupnProdCd"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.prodNm"/>"         binding="prodNm"            width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.saleUprc"/>"       binding="saleUprc"          width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.issueQty"/>"       binding="coupnCount"        width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.issuePrice"/>"     binding="coupnIssueUprc"    width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.issueDate"/>"      binding="coupnIssueDate"    width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.saleCnt"/>"        binding="saleCnt"           width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.saleAmt"/>"        binding="saleAmt"           width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.lastSaleDate"/>"   binding="coupnLastSaleDate" width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.notSaleCnt"/>"     binding="notSaleCnt"        width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponInfo.notSaleAmt"/>"     binding="notSaleAmt"        width="80"  align="center" is-read-only="true"></wj-flex-grid-column>

                <wj-flex-grid-column header=""        binding="coupnCd"       width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header=""        binding="payClassCd"    width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="display: none">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex2"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <%--                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="회원번호"      binding="membrNo"      width="80"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="수신자명"      binding="membrNm"      width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="수신자연락처"   binding="membrTelNo"   width="90"  align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/coupon/couponInfo/couponInfo.js?ver=20251022.01" charset="utf-8"></script>

<%-- 쿠폰정보관리 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/coupon/couponInfo/couponRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰정보관리 팝업 상품선택 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/coupon/couponInfo/couponSelectProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰정보관리 팝업 부서선택 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/coupon/couponInfo/couponSelectPart.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰정보관리 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/coupon/couponInfo/couponInfoReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 쿠폰정보관리 회수쿠폰 조회 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/coupon/couponInfo/couponSaleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>