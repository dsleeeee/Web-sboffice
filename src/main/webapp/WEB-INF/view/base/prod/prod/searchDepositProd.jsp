<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchDepositProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:600px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns" ng-controller="searchDepositProdCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.depositProdCd"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w30" />
                    <col class="w70" />
                </colgroup>
                <tbody>
                <c:if test="${brandUseFg == '1'}">
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <tr>
                                <%-- 상품브랜드 --%>
                            <th><s:message code="prod.prodHqBrand"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchProdHqBrand"
                                            items-source="_getComboData('srchProdHqBrand')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="srchProdHqBrandCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:if>
                <tr>
<%--                    // 상품코드--%>
                    <th><s:message code="prod.prodCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchProdCd" ng-model="searchProdCd" maxlength="13"/>
                    </td>
                </tr>
                <tr>
<%--                    // 상품명--%>
                    <th><s:message code="prod.prodNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="searchProdNm" ng-model="searchProdNm" maxlength="10"/>
                    </td>
                </tr>
                <tr>
<%--                    // 상품유형--%>
                    <th><s:message code="prod.prodTypeFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box ng-model="searchProdTypeFg"
                                          items-source="_getComboData('searchProdTypeFg')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false"
                                          initialized="_initComboBox(s)"
                                          ng-disabled="true">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr ml5" id="btnSearch" ng-click="searchDepositProd()" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="280" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/searchDepositProd.js?ver=20230125.01" charset="utf-8"></script>
