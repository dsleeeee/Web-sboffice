<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="hqSalePriceResveAddLayer" control="hqSalePriceResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">

    <div ng-controller="hqSalePriceResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="salePriceResve.hqSalePriceResve"/>&nbsp;<s:message code="cmm.add"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="salePriceResve.select.prodClass" /></th>
                    <td colspan="3">
                        <input type="text" ng-model="prodClassNm" class="sb-input" ng-click="popUpProdClass2()" style="float: left; width: 250px;"
                               placeholder="선택" readonly />
                        <input type="hidden" ng-model="prodClassCd" disabled/>
                        <button type="button" class="btn_skyblue fl mr5" style="margin-left: 5px;" ng-click="delProdClass2()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="salePriceResve.prodCd" /></th>
                    <td>
                        <input type="text" id="srchPopProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <th><s:message code="salePriceResve.prodNm" /></th>
                    <td>
                        <input type="text" id="srchPopProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="salePriceResve.useYn" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="useYn"
                                    ng-model="useYn"
                                    items-source="_getComboData('useYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="useYnCombo"
                                    selected-index="2">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
                <c:if test="${brandUseFg == '1'}">
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <%-- 상품브랜드 --%>
                        <th><s:message code="salePriceResve.prodHqBrand"/></th>
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
                    </c:if>
                </c:if>
                <c:if test="${subPriceFg == '1'}">
                    <tr>
                        <th><input type="checkbox" id="popSaleUprcApply" ng-model="popSaleUprcApply"/> <s:message code="salePriceResve.batchChange"/></th>
                        <td colspan="3"><s:message code="salePriceResve.saleUprcApply"/></td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                        class="w100px fl"
                        id="listScaleBox2"
                        ng-model="listScale"
                        control="listScaleCombo2"
                        items-source="_getComboData('listScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('hqSalePriceResveAddCtrl',1)"><s:message code="cmm.search" /></button>
            </div>

            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridHqSalePricePop">

                        <!-- define columns -->
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="saleUprc" width="60" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hqMarginAmt"/>" binding="hqMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hqMarginRate"/>" binding="hqMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>

                        <c:if test="${subPriceFg == '1'}">
                            <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="stinSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="dlvrSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="packSaleUprc" width="60" align="right" max-length="10" ></wj-flex-grid-column>
                        </c:if>

                        <wj-flex-grid-column header="<s:message code="salePriceResve.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.useYn"/>" binding="useYn" data-map="useYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20 mb30">
                <%-- id --%>
                <ul id="hqSalePriceResveAddCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <div>
                <table class="tblType01">
                    <colgroup>
                        <col class="w20"/>
                        <col class="w30"/>
                        <col class="w20"/>
                        <col class="w30"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="salePriceResve.resveDate"/></th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          value="startDate"
                                          ng-model="startDate"
                                          control="startDateCombo"
                                          format="yyyy/MM/dd"
                                          min="${tomorrowDate}"
                                          max="9999-12-31"
                                          initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                                <span class="rg">~</span>
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          value="endDate"
                                          ng-model="endDate"
                                          control="endDateCombo"
                                          format="yyyy/MM/dd"
                                          min="${tomorrowDate}"
                                          max="9999-12-31"
                                          initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="salePriceResve.storeSaveFg"/></th>
                        <td colspan="3">
                            <div class="sb-select w200px fl mr10">
                                <wj-combo-box
                                        id="addStoreSaveFg"
                                        ng-model="addStoreSaveFg"
                                        items-source="_getComboData('addStoreSaveFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="addStoreSaveFgCombo"
                                        selected-index-changed="selectedIndexChanged(s)">
                                </wj-combo-box>
                            </div>
                            <div id="addStoreSaveStore" class="fl oh bk" style="width: 200px; height:25px; display: none">
                                <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 --%>
                                <c:if test="${momsEnvstVal == '0'}">
                                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                                        <jsp:param name="targetId" value="addChoiceSaveStore"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${momsEnvstVal == '1'}">
                                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                                        <jsp:param name="targetId" value="addChoiceSaveStore"/>
                                    </jsp:include>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="wj-dialog-footer">
                <div class="mt10 btnSet2">
                    <button class="btn_blue" ng-click="saveProdPrice2()"><s:message code="cmm.save" /></button>
                </div>
            </div>
        </div>
    </div>

</wj-popup>

<script>
    // 사용여부
    var useYnData = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/hqSalePriceResveAdd.js?ver=20250430.01" charset="utf-8"></script>
