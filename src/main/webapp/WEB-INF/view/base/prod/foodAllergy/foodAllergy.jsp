<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon">

    <div ng-controller="foodAllergyCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('foodAllergyCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w40" />
                <col class="w10" />
                <col class="w40" />
            </colgroup>
            <tbody>
            <c:if test="${brandUseFg == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                  <tr>
                    <%-- 상품브랜드 --%>
                    <th><s:message code="foodAllergy.prodHqBrand" /></th>
                    <td>
                      <div class="sb-select">
                          <div class="sb-select">
                              <wj-combo-box
                                  id="srchProdHqBrandCd"
                                  ng-model="prodHqBrandCd"
                                  items-source="_getComboData('srchProdHqBrandCd')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchProdHqBrandCdCombo">
                              </wj-combo-box>
                          </div>
                      </div>
                    </td>
                    <th></th>
                    <td></td>
                  </tr>
                </c:if>
              </c:if>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <s:message code="foodAllergy.foodAllergy"/>
                <div class="updownSet oh mb10">
                    <button class="btn_skyblue" id="btnFoodAllergyAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnFoodAllergySave" ng-click="save()"><s:message code='cmm.save' /></button>
                    <button class="btn_skyblue" id="btnFoodAllergyDel" ng-click="del()"><s:message code='cmm.del' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="foodAllergy.recipesCd"/>" binding="recipesCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="foodAllergy.recipesNm"/>" binding="recipesNm" width="100" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="foodAllergy.allergieNm"/>" binding="allergieNm" width="100" align="center"></wj-flex-grid-column>
                                <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                                    <wj-flex-grid-column header="<s:message code="recpOrigin.hqBrandNm"/>" binding="hqBrandCd" data-map="hqBrandFgMap" width="70" align="center" ></wj-flex-grid-column>
                                </c:if>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="foodAllergyDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <s:message code="foodAllergy.allergyProd"/>
            <label id="lblRecipesCd"></label>
            <label id="lblRecipesNm"></label>
            <label id="lblAllergieNm"></label>
            <div class="updownSet oh mb10">
                <button class="btn_skyblue" id="btnFoodAllergyProdAdd" ng-click="add()"><s:message code='cmm.add' /></button>
                <button class="btn_skyblue" id="btnFoodAllergyProdDel" ng-click="del()"><s:message code='cmm.del' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergy.pathNm"/>" binding="pathNm" width="250" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergy.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergy.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="foodAllergy.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 매장브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    // 사용자 매장브랜드(리스트 DataMap용)
    var userHqBrandCdComboList2 = ${userHqBrandCdComboList};
    userHqBrandCdComboList2[0].name = "선택";
    userHqBrandCdComboList2[0].value = 0;
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/foodAllergy/foodAllergy.js?ver=20230110.02" charset="utf-8"></script>

<%-- 알레르기-상품 등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/foodAllergy/foodAllergyProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>