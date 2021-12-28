<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="prodBatchChange2Veiw" name="prodBatchChange2Veiw" class="subCon" ng-controller="prodBatchChange2Ctrl" style="display: none;">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prodBatchChange.prodBatchChange"/> (<s:message code="prodBatchChange.tab2"/>)</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('prodBatchChange2Ctrl',1)" id="nxBtnSearch2">
                <s:message code="cmm.search"/>
            </button>
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
            <%-- 상품코드 --%>
            <th>
                <s:message code="prodBatchChange.prodCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="prodBatchChange.prodNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th>
                <s:message code="prodBatchChange.srchClass"/>
            </th>
            <td>
                <input type="text" class="sb-input w70" ng-model="prodClassCdNm" ng-click="popProdClass('srch')" style="float: left;" placeholder="<s:message code="prodBatchChange.srchClass" /> 선택" readonly/>
                <input type="hidden" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" style="margin-left: 5px;" ng-click="deleteProdClass('srch')"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th>
                <s:message code="prodBatchChange.barCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" ng-model="barCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <c:if test="${orgnFg == 'HQ' and brandUseFg == '1'}"><%-- 브랜드를 사용하는 본사/매장인 경우만 변경 가능 --%>
                <tr>
                    <%-- 브랜드 --%>
                    <th>
                        <s:message code="prodBatchChange.brand"/>
                    </th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                id="srchHqBrand"
                                ng-model="hqBrandCd"
                                items-source="_getComboData('srchHqBrand')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchHqBrandCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일 --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox2"
                ng-model="listScale"
                control="listScale2Combo"
                items-source="_getComboData('listScaleBox2')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>
        <%-- 저장 --%>
        <button class="btn_blue ml5 fr" ng-click="prodBatchSave()"><s:message code="cmm.save" /></button>
        <%-- 프랜차이즈 매장일때만 --%>
        <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
            <%-- 상품등록구분이 '본사'인 상품은 수정할 수 없습니다. --%>
            <div class="s14 bk lh25 ml5 mr15 fr">
                <s:message code="prodBatchChange.regFgHqBlank" />
            </div>
        </c:if>
    </div>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr class="brt">
            <c:if test="${orgnFg == 'HQ' and brandUseFg == '1'}"><%-- 브랜드를 사용하는 본사/매장인 경우만 변경 가능 --%>
                <%-- 브랜드 --%>
                <th>
                    <s:message code="prodBatchChange.brand"/>
                </th>
                <td>
                    <div class="sb-select w50" style="float: left;">
                        <wj-combo-box
                            ng-model="hqBrandCdChg"
                            items-source="_getComboData('srchHqBrandChg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchHqBrandChgCombo">
                        </wj-combo-box>
                    </div>
                    <a href="#" class="btn_grayS ml10" ng-click="prodBatchChange('srchHqBrandChg')"><s:message code="prodBatchChange.batchChange"/></a>
                </td>
            </c:if>
            <%-- 상품분류 --%>
            <th>
                <s:message code="prodBatchChange.prodClassCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w50" ng-model="prodClassCdNmChg" ng-click="popProdClass('chg')" style="float: left;" placeholder="<s:message code="prodBatchChange.srchClass" /> 선택" readonly/>
                <input type="hidden" ng-model="prodClassCdChg" disabled/>
                <button type="button" class="btn_skyblue fl mr5" style="margin-left: 5px;" ng-click="deleteProdClass('chg')"><s:message code="cmm.selectCancel"/></button>
                <a href="#" class="btn_grayS ml10" ng-click="prodBatchChange('srchProdClassChg')"><s:message code="prodBatchChange.batchChange"/></a>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
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

                <%-- 프랜차이즈 매장일때만 --%>
                <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.regFg"/>" binding="regFg" data-map="regFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>

                <%-- 브랜드를 사용하는 본사/매장인 경우만 변경 가능 --%>
                <c:if test="${orgnFg == 'HQ' and brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="150" align="center"></wj-flex-grid-column>
                </c:if>

                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodClassCd"/>" binding="pathNm" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodClassCode"/>" binding="prodClassCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.barCd"/>" binding="barCd" width="150" is-read-only="true" align="center"></wj-flex-grid-column>



            </wj-flex-grid>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prodBatchChange2CtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodBatchChange/prodBatchChange2.js?ver=20211216.04" charset="utf-8"></script>