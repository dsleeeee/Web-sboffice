<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="popUpSalePriceLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:100%;height:100%;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="popUpSalePriceCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.salePrice" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
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
                                    id="popUpTukeyGrp"
                                    ng-model="popUpTukeyGrp"
                                    control="popUpTukeyGrpCombo"
                                    items-source="_getComboData('popUpTukeyGrp')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th><s:message code="salePrice.select.prodClass" /></th>
                    <td>
                        <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="선택" readonly />
                        <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="kioskKeyMap.prodCd" /></th>
                    <td>
                        <input type="text" id="srchSalePriceProdCd" ng-model="prodCd"/>
                    </td>
                    <th><s:message code="kioskKeyMap.prodNm" /></th>
                    <td>
                        <input type="text" id="srchSalePriceProdNm" ng-model="prodNm"/>
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
                <button class="btn_blue fr mr10" id="btnSearchSalePriceList" ng-click="searchSalePriceList()"><s:message code="cmm.search" /></button>
                <%-- 저장 --%>
                <button class="btn_blue fr mr10" ng-click="saveProdPrice()"><s:message code="cmm.save" /></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                          id="wjSalePrice"
                          autoGenerateColumns="false"
                          control="flex"
                          initialized="initGrid(s,e)"
                          sticky-headers="true"
                          selection-mode="Row"
                          items-source="data"
                          item-formatter="_itemFormatter"
                          id="wjGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.cd"/>" binding="tukeyGrpCd" width="40" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.nm"/>" binding="tukeyGrpNm" width="80" is-read-only="true" align="left"></wj-flex-grid-column>
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

                    <%-- 페이지 리스트 --%>
                    <div class="pageNum mt20">
                        <%-- id --%>
                        <ul id="popUpSalePriceCtrlPager" data-size="10">
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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpSalePrice.js?ver=20240611.01" charset="utf-8"></script>
