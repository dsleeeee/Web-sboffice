<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />


<div class="subCon" ng-controller="couponIssueStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button type="button" class="btn_blue fr" id="srchAll" ng-click="getCouponIssueStatusList('')">
                <s:message code="couponIssueStatus.srchAll"/>
            </button>
            <button type="button" class="btn_blue mr5 fr" id="srchExpire" ng-click="getCouponIssueStatusList('3')">
                <s:message code="couponIssueStatus.srchExpire"/>
            </button>
            <button type="button" class="btn_blue mr5 fr" id="srchSale" ng-click="getCouponIssueStatusList('2')">
                <s:message code="couponIssueStatus.srchSale"/>
            </button>
            <button type="button" class="btn_blue mr5 fr" id="srchIssue" ng-click="getCouponIssueStatusList('1')">
                <s:message code="couponIssueStatus.srchIssue"/>
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
            <th>
                <s:message code="couponIssueStatus.finalStatus"/>
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="finalStatusFg"
                            ng-model="finalStatusFg"
                            control="finalStatusCombo"
                            items-source="_getComboData('finalStatusFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 쿠폰코드 --%>
            <th>
                <s:message code="couponIssueStatus.coupnCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id=srchCoupnCd ng-model="coupnCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 쿠폰명 --%>
            <th>
                <s:message code="couponIssueStatus.coupnNm"/>
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
                <s:message code="couponIssueStatus.storeCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="storeCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="couponIssueStatus.storeNm"/>
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
                <s:message code="couponIssueStatus.prodCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="couponIssueStatus.prodNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 부서코드 --%>
            <th>
                <s:message code="couponIssueStatus.partCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchPartCd" ng-model="partCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 부서명 --%>
            <th>
                <s:message code="couponIssueStatus.partNm"/>
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
        <p class="fl s12 red">* 같은 상태인 경우 업데이트 제외됩니다.</p>
        <button class="btn_skyblue fr mr5" ng-click="couponProcess('3')"><s:message code="couponIssueStatus.expireProcess" /></button>
        <button class="btn_skyblue fr mr5" ng-click="couponProcess('2')"><s:message code="couponIssueStatus.saleProcess" /></button>
        <button class="btn_skyblue fr mr5" ng-click="couponProcess('1')"><s:message code="couponIssueStatus.issueProcess" /></button>
        <input type="text" class="sb-input w120px fr mr5" id="issueRemark" ng-model="issueRemark" placeholder="비고"/>
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.coupnCd"/>"         binding="mergeCoupnCd"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.coupnNm"/>"         binding="coupnNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.coupnSerNo"/>"      binding="coupnSerNo"        width="150"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.finalStatus"/>"     binding="finalStatus"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.detailFg"/>"        binding="detailFg"          width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.usePartCd"/>"       binding="coupnUsePart"      width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.usePartNm"/>"       binding="vendrNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.startUsePeriod"/>"  binding="startDate"         width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.endUsePeriod"/>"    binding="endDate"           width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.prodCd"/>"          binding="coupnProdCd"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.prodNm"/>"          binding="prodNm"            width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.saleUprc"/>"        binding="saleUprc"          width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.storeCd"/>"         binding="coupnStoreCd"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.storeNm"/>"         binding="storeNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.issueDate"/>"       binding="coupnIssueDate"    width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.saleDate"/>"        binding="coupnUseDate"      width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.lastModDt"/>"       binding="modDt"             width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="couponIssueStatus.lastModNm"/>"       binding="modNm"             width="90"  align="center" is-read-only="true"></wj-flex-grid-column>


                <wj-flex-grid-column header=""        binding="coupnCd"       width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header=""        binding="payClassCd"    width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%--위즈모 테이블--%>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/coupon/couponIssueStatus/couponIssueStatus.js?ver=20251030.01" charset="utf-8"></script>
