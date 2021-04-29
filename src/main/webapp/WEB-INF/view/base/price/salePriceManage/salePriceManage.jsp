<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" ng-controller="salePriceManageCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('salePriceManageCtrl',1)">
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
        <tr>
            <%-- 분류조회 --%>
            <th>
                <s:message code="salePriceManage.srchClass" />
            </th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="salePriceManage.srchClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w100" />
        </colgroup>
        <tbody>
        <tr class="brt">
            <th class="oh gr">
                <%-- 매장판매가/본사판매가 선택 --%>
                <div class="sb-select fl w130 mr5">
                    <wj-combo-box
                            id="saleAmtOption"
                            ng-model="prodInfo.saleAmtOption"
                            control="saleAmtOptionCombo"
                            items-source="_getComboData('saleAmtOption')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%-- 판매가 변경 비율 --%>
                <div class="sb-select fl">
                    <span>
                        <input type="text" class="inSty2 w80px" id="inputSaleRate"ng-model="prodInfo.inputSaleRate" ng-readonly="inputSaleRateReadOnly" />
                    </span>
                </div>
                <div class="sb-select fl w5px mr5 mt10">
                    <span>%</span>
                </div>
                <%-- 변경 단위 --%>
                <div class="sb-select fl w130 mr5">
                    <wj-combo-box
                            id="changeUnit"
                            ng-model="prodInfo.changeUnit"
                            control="changeUnitCombo"
                            items-source="_getComboData('changeUnit')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%-- 반올림 여부 --%>
                <div class="sb-select fl w100px mr5">
                    <wj-combo-box
                            id="changeMode"
                            ng-model="prodInfo.changeMode"
                            control="changeModCombo"
                            items-source="_getComboData('changeMode')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
                <p class="s12 bk mt10 lh20">
                    체크박스에서 선택된 항목만 일괄적용 됩니다.
                </p>
            </th>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                control="listScaleCombo"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>
        <%-- 저장 --%>
        <button class="btn_skyblue fr" ng-click="saveProdPrice()"><s:message code="cmm.save" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGridStore" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="salePriceManage.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
                <c:if test="${hqOfficeCd != '00000'}">
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="salePriceManage.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
                <c:if test="${hqOfficeCd != '00000'}">
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hqSaleUprc"/>" binding="hqSaleUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="salePriceManage.storeSaleUprc"/>" binding="storeSaleUprc" is-read-only="true" width="*" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.saleUprc"/>" binding="saleUprc" width="*" align="right"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" visible="false" ></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="salePriceManageCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var priceEnvstVal = "${priceEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceManage/salePriceManage.js?ver=20210429.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>