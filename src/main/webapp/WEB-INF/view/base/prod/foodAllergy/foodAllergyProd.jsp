<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjFoodAllergyProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;height:670px;" fade-in="false" fade-out="false">

    <div ng-controller="foodAllergyProdCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="foodAllergyProd.info"/>
            <label id="lblFoodAllergyProdRecipesCd" style="display: none;"></label>
            <label id="lblFoodAllergyProRecipesNm" style="display: none;"></label>
            <label id="lblFoodAllergyProdAllergieNm" style="display: none;"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('foodAllergyProdCtrl', 1)">
                        <s:message code="cmm.search" />
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
                    <%-- 상품코드 --%>
                    <th>
                        <s:message code="foodAllergyProd.prodCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                    </td>
                    <%-- 상품명 --%>
                    <th>
                        <s:message code="foodAllergyProd.prodNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 분류조회 --%>
                    <th>
                        <s:message code="foodAllergyProd.srchClass" />
                    </th>
                    <td colspan="3">
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="<s:message code="recpProd.srchClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns.="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergyProd.pathNm"/>" binding="pathNm" width="350" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergyProd.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergyProd.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergyProd.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>

        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/foodAllergy/foodAllergyProd.js?ver=20201006.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>