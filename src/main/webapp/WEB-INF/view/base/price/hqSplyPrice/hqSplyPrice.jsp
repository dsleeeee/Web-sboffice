<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" id="hqSplyPriceView" style="padding: 10px 20px 40px;" ng-controller="hqSplyPriceCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="hqSplyPrice.hqSplyPrice"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqSplyPriceCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
            <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
                <s:message code="hqSplyPrice.select.changeAll"/>
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
            <%--분류 선택--%>
            <th><s:message code="hqSplyPrice.select.prodClass"/></th>
            <td>
                <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                                 placeholder="선택" readonly />
                <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <td></td>
            <tr>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="hqSplyPrice.prodCd"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 상품명 --%>
                <th>
                    <s:message code="hqSplyPrice.prodNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
        </tbody>
    </table>

    <%-- 일괄변경 --%>
    <table class="searchTbl mt10" id="tblHqChange" style="display: none;">
        <colgroup>
            <col class="w13"/>
            <col class="w87"/>
        </colgroup>
        <tbody>
        <%-- 공급가 --%>
        <tr class="brt">
            <th>
                <s:message code="hqSplyPrice.splyUprc"/>
            </th>
            <th class="oh gr">
                <%-- 매장판매가/본사판매가 선택 --%>
                <div class="sb-select fl w120px mr5">
                    <wj-combo-box
                            id="saleAmtOption"
                            ng-model="saleAmtOption"
                            control="saleAmtOptionCombo"
                            items-source="_getComboData('saleAmtOption')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%-- 공급가 변경 비율 --%>
                <div class="sb-select fl">
                <span>
                  <input type="text" maxlength="10" numberOnly class="inSty2 w80px" id="inputSaleRate" ng-model="inputSaleRate"/>
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
                <a href="#" class="btn_grayS ml10" ng-click="changeAmt()"><s:message code="hqSplyPrice.batch"/></a>
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

    <div class="mt10 oh">
        <%-- 페이지 스케일  --%>
        <div class="fl sb-select">
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
        </div>

        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.downCondition"/>
        </button>
        <%-- 저장 --%>
        <button class="btn_skyblue mr5 fr" ng-click="saveProdPrice()">
            <s:message code="cmm.save"/>
        </button>

        <div id="storeSaveStore" class="fr oh bk" style="width: 280px; height:25px;display: none;">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="choiceSaveStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
        </div>
        <div class="sb-select w200px fr mr5">
            <wj-combo-box
                    id="storeSaveFg"
                    ng-model="storeSaveFg"
                    items-source="_getComboData('storeSaveFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="storeSaveFgCombo"
                    selected-index-changed="selectedIndexChanged(s)">
            </wj-combo-box>
        </div>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGridStore" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGrid">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prodCd"/>" binding="prodCd" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.hq"/> " binding="hqSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.hq"/>" binding="hqSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.update"/>" binding="splyUprc" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="120" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="hqSplyPriceCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<%--엑셀 리스트--%>
<div class="w100 mt10" style="display:none;" ng-controller="hqSplyPriceExcelCtrl">
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
                    id="wjGridExcelList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prodCd"/>" binding="prodCd" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.hq"/> " binding="hqSaleUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.hq"/>" binding="hqSplyUprc" is-read-only="true" width="100" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPrice.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="120" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </wj-flex-grid>
    </div>
</div>
<%--//엑셀 리스트--%>

<script type="text/javascript">
    $(function () {
        $("input:text[numberOnly]").on("keyup", function () {
            $(this).val($(this).val().replace(/[^-|^0-9]/g, ""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSplyPrice/hqSplyPrice.js?ver=20240423.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>