<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="promotionSelectProdLayer" control="promotionSelectProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <s:message code="promotion.prod"/>
        <label id="lblPromoType" style="display: none"></label>
        <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <%-- body --%>
    <div class="w100 mb20 pd20" ng-controller="promotionSelectProdGridCtrl">
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
                    <%-- 적용상품의 프로모션종류 --%>
                    <th><s:message code="promotion.promotionType" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="tdSelectPromoType" readonly/>
                    </td>
                </tr>
                <tr>
                    <%-- 적용상품의 적용여부 --%>
                    <th><s:message code="promotion.prod" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="tdSelectUseYn" readonly/>
                    </td>
                </tr>
                <tr id="trSelectProd">
                    <%-- 적용상품의 구매대상 --%>
                    <th><s:message code="promotion.selectProdDs" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="selectProdDs"
                                    ng-model="selectProdDs"
                                    items-source="_getComboData('selectProdDs')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="initComboBox(s)"
                                    control="selectProdDsCombo"
                                    selected-index-changed="setSelectProdCnt(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 적용상품의 수량 --%>
                    <th id="thSelectProdCnt" style="display: none;"><label id="lblSelectProdCnt"></label></th>
                    <td id="tdSelectProdCnt" style="display: none;">
                        <input type="text" class="sb-input w100" id="selectProdCnt" ng-model="selectProdCnt" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <%-- 적용상품 등록 영역 --%>
        <div class="wj-gridWrap pdt10" id="prodGrid" style="height:200px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexSelectProdGrid"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridSelectProd">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="condiProdSeq" width="" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.fg"/>" binding="gubunDs" data-map="gubunDsFgDataMap" width="55" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.code"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.name"/>" binding="prodNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.prodQty"/>" binding="prodQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.applyDcDs"/>" binding="applyDcDs" data-map="applyDcDsDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotion.dcSetVal"/>" binding="dcSet" width="70" is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotionReport/promotionSelectProd.js?ver=20240103.01" charset="utf-8"></script>

<%-- 적용상품 상품추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionProdReg.jsp">
</c:import>

<%-- 적용상품 분류추가 --%>
<c:import url="/WEB-INF/view/base/promotion/promotion/promotionClassReg.jsp">
</c:import>