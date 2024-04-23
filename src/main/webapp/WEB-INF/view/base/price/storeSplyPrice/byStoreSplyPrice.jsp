<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" id="byStoreSplyPriceView" style="padding: 10px 20px 40px;" ng-controller="byStoreSplyPriceCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeSplyPrice.byStoreSplyPrice"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('byStoreSplyPriceCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
            <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
                <s:message code="storeSplyPrice.select.changeAll"/>
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
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="searchStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
            <%-- 분류선택 --%>
            <th><s:message code="storeSplyPrice.prodClass"/></th>
            <td>
                <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                     placeholder="선택" readonly />
                <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="storeSplyPrice.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="storeSplyPrice.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <c:if test="${brandUseFg == '1'}">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 상품브랜드 --%>
                    <th><s:message code="cmm.moms.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchProdHqBrandCd"
                                items-source="_getComboData('srchProdHqBrandCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchProdHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
            </c:if>
        </c:if>
        </tbody>
    </table>

    <table class="searchTbl mt10" id="tblStoreChange" style="display: none;">
        <colgroup>
            <col class="w13"/>
            <col class="w87"/>
        </colgroup>
        <tbody>
        <%-- 공급가 --%>
        <tr class="brt">
            <th>
                <s:message code="storeSplyPrice.splyUprc"/>
            </th>
            <th class="oh gr">
                <%-- 적용 가격 선택 --%>
                <div class="sb-select fl w120px mr5">
                    <wj-combo-box
                            id="saleAmtOption2"
                            ng-model="saleAmtOption"
                            control="saleAmtOption2Combo"
                            items-source="_getComboData('saleAmtOption2')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%-- 변경 비율 --%>
                <div class="sb-select fl">
            <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w80px" id="inputSaleRate2" ng-model="inputSaleRate"/>
            </span>
                </div>
                <div class="sb-select fl w5px mr5 mt10">
                    <span>%</span>
                </div>
                <%-- 변경 단위 --%>
                <div class="sb-select fl w120px mr5">
                    <wj-combo-box
                            id="changeUnit"
                            ng-model="changeUnit"
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
                            ng-model="changeMode"
                            control="changeModeCombo"
                            items-source="_getComboData('changeMode')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
                <!-- // todo -->
            </th>
        </tr>
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
                id="listScaleBox2"
                ng-model="listScale"
                control="listScaleCombo2"
                items-source="_getComboData('listScaleBox2')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>

        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.downCondition"/>
        </button>
        <%-- 저장 --%>
        <button class="btn_skyblue mr5 fr" ng-click="saveStoreSplyPrice()">
            <s:message code="cmm.save"/>
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGridProd" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGrid2">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.hq"/> " binding="hqSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.store"/> " binding="storeSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.hq"/>" binding="hqSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.store"/>" binding="storeSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.update"/>" binding="splyUprc" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="120" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="byStoreSplyPriceCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<%--엑셀 리스트--%>
<div class="w100 mt10" style="display:none;" ng-controller="byStoreSplyPriceExcelCtrl">
    <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridExcelList2">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.hq"/> " binding="hqSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.store"/> " binding="storeSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.hq"/>" binding="hqSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.store"/>" binding="storeSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSplyPrice.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="120" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </wj-flex-grid>
    </div>
</div>
<%--//엑셀 리스트--%>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeSplyPrice/byStoreSplyPrice.js?ver=20240423.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>