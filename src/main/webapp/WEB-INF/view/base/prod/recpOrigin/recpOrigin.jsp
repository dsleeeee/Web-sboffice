<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="recpOriginView" class="subCon" style="display: none;">

    <div ng-controller="recpOriginCtrl">
        <%--searchTbl--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="recpOriginTab.recpOrigin"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue mr3" id="nxBtnSearch2" ng-click="_pageView('recpOriginCtrl',1)">
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
                <tr>
                    <%-- 재료명 --%>
                    <th><s:message code="recpOrigin.recipesNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="recipesNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                    <%-- 원산지명 --%>
                    <th><s:message code="recpOrigin.orgplceNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="orgplceNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <%--//searchTbl--%>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <s:message code="recpOrigin.recpOrigin"/>
                <div class="updownSet oh mb10">
                    <button class="btn_skyblue" id="btnRecpOriginAdd" ng-click="addRow()"><s:message code='cmm.add' /></button>
                    <button class="btn_skyblue" id="btnRecpOriginSave" ng-click="save()"><s:message code='cmm.save' /></button>
                    <button class="btn_skyblue" id="btnRecpOriginDel" ng-click="del()"><s:message code='cmm.del' /></button>
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
                                <wj-flex-grid-column header="<s:message code="recpOrigin.recipesCd"/>" binding="recipesCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="recpOrigin.recipesNm"/>" binding="recipesNm" width="100" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="recpOrigin.orgplceNm"/>" binding="orgplceNm" width="100" align="center"></wj-flex-grid-column>
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
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="recpOriginDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <s:message code="recpOrigin.recpProd"/>
            <label id="lblRecipesCd"></label>
            <label id="lblRecipesNm"></label>
            <label id="lblOrgplceNm"></label>
            <div class="updownSet oh mb10">
                <button class="btn_skyblue" id="btnRecpOriginProdAdd" ng-click="add()"><s:message code='cmm.add' /></button>
                <button class="btn_skyblue" id="btnRecpOriginProdDel" ng-click="del()"><s:message code='cmm.del' /></button>
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
                        <wj-flex-grid-column header="<s:message code="recpOrigin.pathNm"/>" binding="pathNm" width="250" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/recpOrigin.js?ver=20210327.04" charset="utf-8"></script>

<%-- 재료-상품 등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/recpOrigin/recpProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>