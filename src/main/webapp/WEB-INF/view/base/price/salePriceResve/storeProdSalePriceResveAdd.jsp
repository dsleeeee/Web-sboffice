<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeProdSalePriceResveAddLayer" control="storeProdSalePriceResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">

    <div ng-controller="storeProdSalePriceResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="salePriceResve.prodSalePrice"/>&nbsp;<s:message code="cmm.add"/>
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
                    <th><s:message code="salePriceResve.select.prod" /></th>
                    <td>
                        <%-- 상품선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/application/layer/searchProductS.jsp" flush="true">
                            <jsp:param name="targetId" value="prodSel"/>
                        </jsp:include>
                        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                    <th><s:message code="salePriceResve.select.store" /></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="storeSel"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
                <c:if test="${subPriceFg == '1'}">
                    <tr>
                        <th><input type="checkbox" id="popProdSaleUprcApply" ng-model="popProdSaleUprcApply"/> <s:message code="salePriceResve.batchChange"/></th>
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
                        ng-model="listScale2"
                        control="listScaleCombo2"
                        items-source="_getComboData('listScaleBox2')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
                <button class="btn_blue fr" ng-click="_pageView('storeProdSalePriceResveAddCtrl',1)"><s:message code="cmm.search" /></button>
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
                            id="wjGridStoreProdSalePricePop">

                        <!-- define columns -->
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.storeCd"/>" binding="storeCd" width="63" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.storeNm"/>" binding="storeNm" width="80" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.hq"/>" binding="hqSaleUprc" is-read-only="true" width="56" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.store"/>" binding="saleUprcP" width="56" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="salePriceResve.update"/>" binding="saleUprc" width="56" align="right" max-length="10" ></wj-flex-grid-column>

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
                </div>
                <%--//위즈모 테이블--%>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20 mb30">
                <%-- id --%>
                <ul id="storeProdSalePriceResveAddCtrlPager" data-size="10">
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
                    <button class="btn_blue" ng-click="saveProdPrice2()"><s:message code="cmm.save" /></button>
                </div>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeProdSalePriceResveAdd.js?ver=20220418.02" charset="utf-8"></script>
