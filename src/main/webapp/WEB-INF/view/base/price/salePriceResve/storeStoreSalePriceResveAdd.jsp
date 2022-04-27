<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeStoreSalePriceResveAddLayer" control="storeStoreSalePriceResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">

    <div ng-controller="storeStoreSalePriceResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="salePriceResve.storeSalePrice"/>&nbsp;<s:message code="cmm.add"/>
            <a href="#" class="wj-hide btn_close" ng-click="close2()"></a>
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
                <%-- 매장선택 --%>
                <tr>
                    <th><s:message code="salePriceResve.select.store" /></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                            <jsp:param name="targetId" value="popSearchStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                    <th><s:message code="salePriceResve.select.prodClass" /></th>
                    <td>
                        <input type="text" id="popSearchProdClassNm" ng-model="prodClassNm" class="sb-input" ng-click="popUpProdClass2()" style="float: left; width: 200px;"
                               placeholder="선택" readonly />
                        <input type="hidden" id="popSearchProdClassCd" ng-model="prodClassCd" disabled/>
                        <button type="button" class="btn_skyblue fl mr5" id="btnPopCancelProdClassCd" style="margin-left: 5px;" ng-click="popDelProdClass2()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th>
                        <s:message code="salePriceResve.prodCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchPopProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th>
                        <s:message code="salePriceResve.prodNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchPopProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
                <c:if test="${subPriceFg == '1'}">
                    <tr>
                        <th><input type="checkbox" id="popStoreSaleUprcApply" ng-model="popStoreSaleUprcApply"/> <s:message code="salePriceResve.batchChange"/></th>
                        <td colspan="3"><s:message code="salePriceResve.saleUprcApply"/></td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                    <%-- 페이지 스케일  --%>
                    <wj-combo-box
                            class="w100px fl"
                            id="listScaleBox4"
                            ng-model="listScale"
                            control="listScaleCombo4"
                            items-source="_getComboData('listScaleBox4')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>

                    <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('storeStoreSalePriceResveAddCtrl',1)"><s:message code="cmm.search" /></button>
                </div>

            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr mt10">
                    <div style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true"
                                id="wjGridStoreStoreSalePricePop">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                            <%--<wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>--%>
                            <%--<wj-flex-grid-column header="<s:message code="salePrice.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>--%>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="storeSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="saleUprc" width="56" align="right"></wj-flex-grid-column>
                            <%--<wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>--%>

                            <c:if test="${subPriceFg == '1'}">
                                <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="storeStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="storeDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="storePackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                            </c:if>

                            <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            <%--//위즈모 테이블--%>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20 mb30">
            <%-- id --%>
                <ul id="storeStoreSalePriceResveAddCtrlPager" data-size="10">
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
                        </tbody>
                    </table>
                </div>
                <div class="wj-dialog-footer">
                    <div class="btnSet2">
                        <button class="btn_blue" ng-click="saveProdPrice3()"><s:message code="cmm.save" /></button>
                    </div>
                </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeStoreSalePriceResveAdd.js?ver=20220427.01" charset="utf-8"></script>