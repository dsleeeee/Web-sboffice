<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="wjProdRecpOriginAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:460px;" fade-in="false" fade-out="false">

    <div ng-controller="prodRecpOriginAddCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodRecpOriginAdd.info"/>
            <label id="lblProdRecpOriginAddProdCd" style="display: none;"></label>
            <label id="lblProdRecpOriginAddProdNm" style="display: none;"></label>
            <label id="lblProdRecpOriginAddHqBrandCd" style="display: none;"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 재료명 --%>
                    <th>
                        <s:message code="prodRecpOriginAdd.recipesNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchRecipesNm" ng-model="recipesNm" />
                    </td>
                    <%-- 원산지명 --%>
                    <th>
                        <s:message code="prodRecpOriginAdd.orgplceNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgplceNm" ng-model="orgplceNm" />
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('prodRecpOriginAddCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:250px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.recipesCd"/>" binding="recipesCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.recipesNm"/>" binding="recipesNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.orgplceNm"/>" binding="orgplceNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.hqBrandNm"/>" binding="hqBrandNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>

                        <%--팝업 조회시 필요--%>
                        <wj-flex-grid-column header="<s:message code="prodRecpOriginAdd.hqBrandCd"/>" binding="hqBrandCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcProdRecpOriginAddSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>

        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/prodRecpOriginAdd.js?ver=20210326.04" charset="utf-8"></script>