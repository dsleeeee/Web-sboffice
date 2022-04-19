<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" id="prodSalePriceResveArea" ng-controller="storeProdSalePriceResveCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="salePriceResve.prodSalePrice" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeProdSalePriceResveCtrl', 1)">
                <s:message code="cmm.search" />
            </button>
            <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
                <s:message code="salePriceResve.select.changeAll" />
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
        <%-- 상품선택 --%>
        <tr>
            <th><s:message code="salePriceResve.select.prod" /></th>
            <td>
                <%-- 상품선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/application/layer/searchProductS.jsp" flush="true">
                    <jsp:param name="targetId" value="prod"/>
                </jsp:include>
                <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
            </td>
            <th><s:message code="salePriceResve.select.store" /></th>
            <td>
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="store"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        <c:if test="${subPriceFg == '1'}">
            <tr>
                <th><input type="checkbox" id="prodSaleUprcApply" ng-model="prodSaleUprcApply"/> <s:message code="salePriceResve.batchChange"/></th>
                <td><s:message code="salePriceResve.saleUprcApply"/></td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <table class="searchTbl mt10" id="tblProdChange" style="display: none;">
        <colgroup>
            <col class="w13" />
            <col class="w87" />
        </colgroup>
        <tbody>
        <%--판매가--%>
        <tr class="brt">
            <th>
                <s:message code="salePriceResve.salePrice" />
            </th>
            <th class="oh gr">
                <div class="sb-select fl w120px mr5">
                    <wj-combo-box
                            id="saleAmtOption"
                            ng-model="prodInfo.saleAmtOption"
                            control="saleAmtOptionCombo"
                            items-source="_getComboData('saleAmtOption')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index-changed="setSelectedSaleAmtOption(s)">
                    </wj-combo-box>
                </div>
                <span>
          <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputSaleAmt"ng-model="prodInfo.inputSaleAmt" ng-readonly="inputSaleAmtReadOnly" />
        </span> 원
                <a href="#" class="btn_grayS ml10" ng-click="changeAmt()">일괄적용</a>
                <a href="#" class="btn_grayS" style="display:none;" ng-click="changeExcelAmt()">엑셀판매가 일괄적용</a> <!-- // todo -->
            </th>
        </tr>
        <c:if test="${subPriceFg == '1'}">
            <%--내점-판매가--%>
            <tr class="brt">
                <th>
                    <s:message code="salePriceResve.stinSaleUprc" />
                </th>
                <th class="oh gr">
                    <div class="sb-select fl w120px mr5">
                        <wj-combo-box
                                id="stinSaleUprcOption"
                                ng-model="prodInfo.stinSaleUprcOption"
                                control="stinSaleUprcOptionCombo"
                                items-source="_getComboData('stinSaleUprcOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setSelectedStinSaleUprcOption(s)">
                        </wj-combo-box>
                    </div>
                    <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputStinSaleUprc" ng-model="prodInfo.inputStinSaleUprc" ng-readonly="inputStinSaleUprcReadOnly" />
            </span> 원
                    <a href="#" class="btn_grayS ml10" ng-click="changeStinSaleUprc()">일괄적용</a>
                </th>
            </tr>
            <%--배달-판매가--%>
            <tr class="brt">
                <th>
                    <s:message code="salePriceResve.dlvrSaleUprc" />
                </th>
                <th class="oh gr">
                    <div class="sb-select fl w120px mr5">
                        <wj-combo-box
                                id="dlvrSaleUprcOption"
                                ng-model="prodInfo.dlvrSaleUprcOption"
                                control="dlvrSaleUprcOptionCombo"
                                items-source="_getComboData('dlvrSaleUprcOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setSelectedDlvrSaleUprcOption(s)">
                        </wj-combo-box>
                    </div>
                    <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputDlvrSaleUprc" ng-model="prodInfo.inputDlvrSaleUprc" ng-readonly="inputDlvrSaleUprcReadOnly" />
            </span> 원
                    <a href="#" class="btn_grayS ml10" ng-click="changeDlvrSaleUprc()">일괄적용</a>
                </th>
            </tr>
            <%--포장-판매가--%>
            <tr class="brt">
                <th>
                    <s:message code="salePriceResve.packSaleUprc" />
                </th>
                <th class="oh gr">
                    <div class="sb-select fl w120px mr5">
                        <wj-combo-box
                                id="packSaleUprcOption"
                                ng-model="prodInfo.packSaleUprcOption"
                                control="packSaleUprcOptionCombo"
                                items-source="_getComboData('packSaleUprcOption')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setSelectedPackSaleUprcOption(s)">
                        </wj-combo-box>
                    </div>
                    <span>
              <input type="text" maxlength="10" numberOnly class="inSty2 w120px" id="inputPackSaleUprc" ng-model="prodInfo.inputPackSaleUprc" ng-readonly="inputPackSaleUprcReadOnly" />
            </span> 원
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
                id="listScaleBox1"
                ng-model="listScale"
                control="listScaleCombo1"
                items-source="_getComboData('listScaleBox1')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
        </wj-combo-box>

        <button class="btn_skyblue fr ml5" ng-click="addProdPrice()"><s:message code="cmm.add" /></button>
        <button class="btn_skyblue fr" ng-click="saveProdPrice()"><s:message code="cmm.edit" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div style="height: 370px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridProdSalePriceResveArea">

                <%--본사마진금액, 본사마진율, 매장마진금액, 매장마진율 visible false 처리 / 추후에 수불 화면 개발 시 재 오픈 : 2019-08-07 이다솜 --%>
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="orgStartDate" width="100" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.startDate"/>" binding="startDate" width="120" align="center">
                    <wj-flex-grid-cell-template cell-type="CellEdit">
                        <div class="sb-select">
                          <span class="txtIn w110px">
                            <wj-input-date
                                    value="$value"
                                    min="${tomorrowDate}"
                                    max="9999-12-31">
                            </wj-input-date>
                          </span>
                        </div>
                    </wj-flex-grid-cell-template>
                </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.endDate"/>" binding="endDate" width="120" align="center">
                    <wj-flex-grid-cell-template cell-type="CellEdit">
                        <div class="sb-select">
                          <span class="txtIn w110px">
                              <wj-input-date
                                    value="$value"
                                    min="${tomorrowDate}"
                                    max="9999-12-31">
                              </wj-input-date>
                          </span>
                        </div>
                    </wj-flex-grid-cell-template>
                </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
<%--                <wj-flex-grid-column header="<s:message code="salePriceResve.costUprc"/>" binding="costUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="saleUprcP" width="56" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="saleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
<%--                <wj-flex-grid-column header="<s:message code="salePriceResve.storeSplyUprc"/>" binding="storeSplyUprc" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.hqMarginAmt"/>" binding="hqMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.hqMarginRate"/>" binding="hqMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.saleUprcAmt"/>" binding="saleUprcAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.storeMarginAmt"/>" binding="storeMarginAmt" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.storeMarginRate"/>" binding="storeMarginRate" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="salePriceResve.poUnitQty"/>" binding="poUnitQty" width="*" visible="false" ></wj-flex-grid-column>--%>

                <c:if test="${subPriceFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="stinSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="dlvrSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="packSaleUprcP" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                </c:if>

                <wj-flex-grid-column header="<s:message code="salePriceResve.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="storeProdSalePriceResveCtrl"/>
            </jsp:include>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="storeProdSalePriceResveCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var subPriceFg = "${subPriceFg}";
    var coercionFg = "${coercionFg}";

    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};

    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeProdSalePriceResve.js?ver=20220418.01" charset="utf-8"></script>

<%-- 가격예약(매장판매가) [상품별 판매가관리] 상품가격정보 팝업 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeProdSalePriceInfo.jsp">
</c:import>

<%-- 가격예약(매장판매가) [상품별 판매가관리] 추가 팝업 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeProdSalePriceResveAdd.jsp">
</c:import>