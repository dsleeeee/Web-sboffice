<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">
    <div ng-controller="storeSoldOutHistoryBensonCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeSoldOutHistoryBenson.storeSoldOutHistoryBenson" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeSoldOutHistoryBensonCtrl', 1)">
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
            <tr>
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 분류조회 --%>
                <th><s:message code="prod.prodClass" /></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <input type="hidden" id="_selectCancelFg" name="selectCancelFg" ng-model="selectCancelFg" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                        <%-- 매장브랜드 --%>
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCdCombo"
                                    ng-model="storeHqBrandCd"
                                    items-source="_getComboData('storeHqBrandCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                            <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="storeSoldOutHistoryBensonStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="storeSoldOutHistoryBensonCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="cmm.moms.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrandCombo"
                                    ng-model="prodHqBrandCd"
                                    items-source="_getComboData('prodHqBrandCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <%-- 상품 --%>
                <th><s:message code="dayProd.prod"/></th>
                <td>
                    <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                                     targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                     closeFunc - 팝업 닫기시 호출할 함수--%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="storeSoldOutSelect"/>
                    </jsp:include>
                    <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 분할 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload('2')"><s:message code="cmm.excel.downDivision"/></button>
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload2()"><s:message code="cmm.excel.downCurrent"/></button>
            <%-- 조회조건 엑셀다운로드 --%>
            <%--<button class="btn_skyblue ml5 fr" ng-click="excelDownload('1')"><s:message code="cmm.excel.downCondition"/></button>--%>
        </div>

        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.storeCd"/>"   binding="storeCd"   width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.storeNm"/>"   binding="storeNm"   width="120" align="left"    is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.prodCd"/>"    binding="prodCd"    width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.prodNm"/>"    binding="prodNm"    width="150" align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.soldOutDt"/>" binding="soldOutDt" width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.soldDt"/>"    binding="soldDt"    width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="storeSoldOutHistoryBensonCtrlPager" data-size="10">
        </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="storeSoldOutHistoryBensonExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.storeCd"/>"   binding="storeCd"   width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.storeNm"/>"   binding="storeNm"   width="120" align="left"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.prodCd"/>"    binding="prodCd"    width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.prodNm"/>"    binding="prodNm"    width="150" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.soldOutDt"/>" binding="soldOutDt" width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSoldOutHistoryBenson.soldDt"/>"    binding="soldDt"    width="130" align="center"  is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
    var menuCd = "${menuCd}";
    var menuNm = "${menuNm}";
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
    var hqOfficeCd = "${hqOfficeCd}"

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson.js?ver=20260910.02" charset="utf-8"></script>
<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCdCheck.jsp">
</c:import>
