<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">

    <div ng-controller="menuRankDisplayCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('menuRankDisplayCtrl')">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <s:message code="menuRank.menuRankDisplayYn"/>
                <div class="updownSet oh mb10" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>
                    <button class="btn_skyblue" id="btnRankUseSave" ng-click="rankUseSave()"><s:message code='cmm.save' /></button>
                </div>
                <div id="divRank"></div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
   <%-- <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="todayMenuSetCtrl">
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
                        <wj-flex-grid-column header="<s:message code="recpOrigin.level1Nm"/>" binding="level1Nm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.level2Nm"/>" binding="level2Nm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.level3Nm"/>" binding="level3Nm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>--%>
    <%--right--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/menuRank/menuRankDisplay.js?ver=20200806.31" charset="utf-8"></script>

<%-- 메뉴 순위 사용/미사용 매장 등록 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/menuRank/menuRankStoreRegist.jsp">
</c:import>
