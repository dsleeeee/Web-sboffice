<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="salePriceManageCtrl">

    <%-- 조회조건 --%>
    <%--<div class="searchBar flddUnfld">--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('salePriceManageCtrl',1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
            <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
                <s:message code="salePrice.select.changeAll" />
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
        <tr>
            <%-- 상품코드 --%>
            <th>
                <s:message code="salePriceManage.prodCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="salePriceManage.prodNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <c:if test="${subPriceFg == '1'}">
            <tr>
                <th><input type="checkbox" id="saleUprcApply" ng-model="saleUprcApply"/> <s:message code="salePrice.batchChange"/></th>
                <td><s:message code="salePrice.saleUprcApply"/></td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <table class="searchTbl mt10" id="tblChange" style="display: none;">
        <colgroup>
            <col class="w13" />
            <col class="w87" />
        </colgroup>
        <tbody>
        <%--판매가--%>
        <tr class="brt">
            <th>
                <s:message code="salePriceManage.salePrice" />
            </th>
            <th class="oh gr">
                <%-- 매장판매가/본사판매가 선택 --%>
                <div class="sb-select fl w120px mr5">
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
                <div class="sb-select fl w120px mr5">
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
            </th>
        </tr>
        <c:if test="${subPriceFg == '1'}">
            <%--내점-판매가--%>
            <tr class="brt">
                    <th>
                        <s:message code="salePriceManage.stinSaleUprc" />
                    </th>
                    <th class="oh gr">
                        <%-- 매장판매가/본사판매가 선택 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="storeStinSaleUprcOption"
                                    ng-model="prodInfo.storeStinSaleUprcOption"
                                    control="storeStinSaleUprcOptionCombo"
                                    items-source="_getComboData('storeStinSaleUprcOption')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 변경 비율 --%>
                        <div class="sb-select fl">
                  <span>
                    <input type="text" class="inSty2 w80px" id="inputStinSaleUprcRate" ng-model="prodInfo.inputStinSaleUprcRate" ng-readonly="inputStinSaleUprcRateReadOnly" />
                  </span>
                        </div>
                        <div class="sb-select fl w5px mr5 mt10">
                            <span>%</span>
                        </div>
                        <%-- 변경 단위 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="stinSaleUprcChangeUnit"
                                    ng-model="prodInfo.stinSaleUprcChangeUnit"
                                    control="stinSaleUprcChangeUnitCombo"
                                    items-source="_getComboData('stinSaleUprcChangeUnit')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 반올림 여부 --%>
                        <div class="sb-select fl w100px mr5">
                            <wj-combo-box
                                    id="stinSaleUprcChangeMode"
                                    ng-model="prodInfo.stinSaleUprcChangeMode"
                                    control="stinSaleUprcChangeModeCombo"
                                    items-source="_getComboData('stinSaleUprcChangeMode')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <a href="#" class="btn_grayS ml10" ng-click="changeStinSaleUprc()">일괄적용</a>
                    </th>
                </tr>
            <%--배달-판매가--%>
            <tr class="brt">
                    <th>
                        <s:message code="salePriceManage.dlvrSaleUprc" />
                    </th>
                    <th class="oh gr">
                        <%-- 매장판매가/본사판매가 선택 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="storeDlvrSaleUprcOption"
                                    ng-model="prodInfo.storeDlvrSaleUprcOption"
                                    control="storeDlvrSaleUprcOptionCombo"
                                    items-source="_getComboData('storeDlvrSaleUprcOption')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 변경 비율 --%>
                        <div class="sb-select fl">
                  <span>
                    <input type="text" class="inSty2 w80px" id="inputDlvrSaleUprcRate" ng-model="prodInfo.inputDlvrSaleUprcRate" ng-readonly="inputDlvrSaleUprcRateReadOnly" />
                  </span>
                        </div>
                        <div class="sb-select fl w5px mr5 mt10">
                            <span>%</span>
                        </div>
                        <%-- 변경 단위 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="dlvrSaleUprcChangeUnit"
                                    ng-model="prodInfo.dlvrSaleUprcChangeUnit"
                                    control="dlvrSaleUprcChangeUnitCombo"
                                    items-source="_getComboData('dlvrSaleUprcChangeUnit')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 반올림 여부 --%>
                        <div class="sb-select fl w100px mr5">
                            <wj-combo-box
                                    id="dlvrSaleUprcChangeMode"
                                    ng-model="prodInfo.dlvrSaleUprcChangeMode"
                                    control="dlvrSaleUprcChangeModeCombo"
                                    items-source="_getComboData('dlvrSaleUprcChangeMode')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <a href="#" class="btn_grayS ml10" ng-click="changeDlvrSaleUprc()">일괄적용</a>
                    </th>
                </tr>
            <%--포장-판매가--%>
            <tr class="brt">
                    <th>
                        <s:message code="salePriceManage.packSaleUprc" />
                    </th>
                    <th class="oh gr">
                        <%-- 매장판매가/본사판매가 선택 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="storePackSaleUprcOption"
                                    ng-model="prodInfo.storePackSaleUprcOption"
                                    control="storePackSaleUprcOptionCombo"
                                    items-source="_getComboData('storePackSaleUprcOption')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 변경 비율 --%>
                        <div class="sb-select fl">
                  <span>
                    <input type="text" class="inSty2 w80px" id="inputPackSaleUprcRate" ng-model="prodInfo.inputPackSaleUprcRate" ng-readonly="inputPackSaleUprcRateReadOnly" />
                  </span>
                        </div>
                        <div class="sb-select fl w5px mr5 mt10">
                            <span>%</span>
                        </div>
                        <%-- 변경 단위 --%>
                        <div class="sb-select fl w120px mr5">
                            <wj-combo-box
                                    id="packSaleUprcChangeUnit"
                                    ng-model="prodInfo.packSaleUprcChangeUnit"
                                    control="packSaleUprcChangeUnitCombo"
                                    items-source="_getComboData('packSaleUprcChangeUnit')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 반올림 여부 --%>
                        <div class="sb-select fl w100px mr5">
                            <wj-combo-box
                                    id="packSaleUprcChangeMode"
                                    ng-model="prodInfo.packSaleUprcChangeMode"
                                    control="packSaleUprcChangeModeCombo"
                                    items-source="_getComboData('packSaleUprcChangeMode')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <a href="#" class="btn_grayS ml10" ng-click="changePackSaleUprc()">일괄적용</a>
                    </th>
                </tr>
        </c:if>
        <tr>
            <th colspan="2">
                <p class="s12 bk lh20">
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
                    item-formatter="_itemFormatter"
                    id="wjGridSalePriceManage">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
                <c:if test="${hqOfficeCd != '00000'}">
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="70" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="70" align="right"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="salePriceManage.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                <c:if test="${hqOfficeCd != '00000'}">
                    <wj-flex-grid-column header="<s:message code="salePriceManage.hq"/>" binding="hqSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="salePriceManage.store"/>" binding="storeSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceManage.update"/>" binding="saleUprc" width="60" align="right"></wj-flex-grid-column>

                <c:if test="${subPriceFg == '1'}">
                    <c:if test="${hqOfficeCd != '00000'}">
                        <wj-flex-grid-column header="<s:message code="salePriceManage.hq"/>" binding="hqStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.store"/>" binding="storeStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.update"/>" binding="stinSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                    <c:if test="${hqOfficeCd != '00000'}">
                        <wj-flex-grid-column header="<s:message code="salePriceManage.hq"/>" binding="hqDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.store"/>" binding="storeDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.update"/>" binding="dlvrSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                    <c:if test="${hqOfficeCd != '00000'}">
                        <wj-flex-grid-column header="<s:message code="salePriceManage.hq"/>" binding="hqPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.store"/>" binding="storePackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceManage.update"/>" binding="packSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                </c:if>

                <c:if test="${hqOfficeCd != '00000'}">
                    <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>
                </c:if>

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
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
    var subPriceFg = "${subPriceFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceManage/salePriceManage.js?ver=20220124.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>