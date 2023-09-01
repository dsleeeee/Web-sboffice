<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="popUpStoreSalePriceLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:100%;height:100%;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="popUpStoreSalePriceCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.storeSalePrice" />
            <a href="" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 터치키 --%>
                    <th><s:message code="touchKey.grp"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="storeTukeyGrp"
                                    ng-model="storeTukeyGrp"
                                    control="storeTukeyGrpCombo"
                                    items-source="_getComboData('storeTukeyGrp')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th><s:message code="salePrice.select.prodClass" /></th>
                    <td>
                        <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w100px" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="선택" readonly />
                        <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <c:if test="${brandUseFg == '1'}">
                        <%-- 매장브랜드 --%>
                        <th><s:message code="dayProd.storeHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchStoreHqBrandCd"
                                        ng-model="storeHqBrandCd"
                                        items-source="_getComboData('srchStoreHqBrandCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchStoreHqBrandCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                    <th><s:message code="touchKey.store" /></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <c:if test="${momsEnvstVal == '0'}">
                            <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                                <jsp:param name="targetId" value="searchStore"/>
                            </jsp:include>
                        </c:if>
                        <c:if test="${momsEnvstVal == '1'}">
                            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreSMoms.jsp" flush="true">
                                <jsp:param name="targetId" value="searchStore"/>
                            </jsp:include>
                        </c:if>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
                <tr>
                    <c:if test="${brandUseFg == '1'}">
                        <%-- 상품브랜드 --%>
                        <th><s:message code="prodSaleRate.prodHqBrand"/></th>
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
                    <th><s:message code="touchKey.prod" /></th>
                    <td>
                        <%-- 상품선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/application/layer/searchProductS.jsp" flush="true">
                            <jsp:param name="targetId" value="searchProd"/>
                        </jsp:include>
                        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
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
            <%-- 버튼영역 --%>
            <div class="mt10 oh">
                <%-- 조회 --%>
                <button class="btn_blue fr mr10" id="btnSearchSalePriceList" ng-click="searchSalePriceList()"><s:message code="cmm.search" /></button>
                <%-- 저장 --%>
                <button class="btn_blue fr mr10" ng-click="saveProdPrice()"><s:message code="cmm.save" /></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 430px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                          autoGenerateColumns="false"
                          control="flex"
                          initialized="initGrid(s,e)"
                          sticky-headers="true"
                          selection-mode="Row"
                          items-source="data"
                          item-formatter="_itemFormatter"
                          id="wjGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="34"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.cd"/>" binding="tukeyGrpCd" width="40" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.nm"/>" binding="tukeyGrpNm" width="80" is-read-only="true" align="left"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="touchKey.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="touchKey.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="salePrice.poUnitQty"/>" binding="poUnitQty" visible="false" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.hqCostUprc"/>" binding="hqCostUprc" is-read-only="true" width="*" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.hqSplyUprc"/>" binding="hqSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.storeSplyUprc"/>" binding="storeSplyUprc" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="saleUprc" width="56" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginAmt"/>" binding="hqMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.hqMarginRate"/>" binding="hqMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginAmt"/>" binding="storeMarginAmt" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePrice.storeMarginRate"/>" binding="storeMarginRate" is-read-only="true" width="*" align="right" visible="false"></wj-flex-grid-column>

                        <c:if test="${subPriceFg == '1'}">
                            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeStinSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="stinSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storeDlvrSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="dlvrSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="storePackSaleUprc" width="56" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="salePrice.update"/>" binding="packSaleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>
                        </c:if>

                        <wj-flex-grid-column header="<s:message code="salePriceManage.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" is-read-only="true" width="85" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>

                    <%-- 페이지 리스트 --%>
                    <div class="pageNum mt20">
                        <%-- id --%>
                        <ul id="popUpStoreSalePriceCtrlPager" data-size="10">
                        </ul>
                    </div>
                    <%--//페이지 리스트--%>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpStoreSalePrice.js?ver=20230825.01" charset="utf-8"></script>
