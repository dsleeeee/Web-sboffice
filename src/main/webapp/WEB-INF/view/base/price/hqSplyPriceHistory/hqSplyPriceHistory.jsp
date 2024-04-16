<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="hqSplyPriceHistoryCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="hqSplyPriceHistory.hqSplyPriceHistory"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqSplyPriceHistoryCtrl', 1)">
                <s:message code="cmm.search"/>
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
        <%-- 변경 일자 --%>
        <tr>
            <th><s:message code="hqSplyPriceHistory.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
            <th><s:message code="hqSplyPriceHistory.prodClass"/></th>
            <td>
                <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;" placeholder="선택" readonly/>
                <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th>
                <s:message code="hqSplyPriceHistory.prodCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th>
                <s:message code="hqSplyPriceHistory.prodNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGrid" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.saleB"/>" binding="bSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.sale"/>" binding="aSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.procFg"/>" binding="procFg" is-read-only="true" width="100" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.procDt"/>" binding="procDt" is-read-only="true" width="150" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="hqSplyPriceHistoryCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<%--엑셀 리스트--%>
<div id="wjWrapType2" class="w100 mt10" style="display:none;" ng-controller="hqSplyPriceHistoryExcelCtrl">
    <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.prodCd"/>" binding="prodCd" width="120" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.saleB"/>" binding="bSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.sale"/>" binding="aSplyUprc" is-read-only="true" width="80" align="right"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.procFg"/>" binding="procFg" is-read-only="true" width="100" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.procDt"/>" binding="procDt" is-read-only="true" width="150" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSplyPriceHistory.modId"/>" binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>
<%--//엑셀 리스트--%>

<script>
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSplyPriceHistory/hqSplyPriceHistory.js?ver=20240416.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
