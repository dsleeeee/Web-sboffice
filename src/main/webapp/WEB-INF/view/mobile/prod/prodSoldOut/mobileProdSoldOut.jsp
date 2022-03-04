<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileProdSoldOutCtrl">

    <div class="searchBar">
        <a href="#" class="fl"><s:message code="mobile.prodSoldOut"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileProdSoldOutCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>

        <button class="btn_blue fr mt5 mr10" id="btnSearchAddShow" ng-click="searchAddShowChange()">
            <s:message code="mobile.cmm.search.addShow"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 상품명 --%>
            <th><s:message code="mobile.prodSoldOut.prodNm"/></th>
            <td><input type="text"  class="sb-input w100" id="prodNm" ng-model="prodNm" /></td>
        </tr>
        <tr>
            <%-- 품절여부 --%>
            <th><s:message code="mobile.prodSoldOut.soldOutYn"/></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="soldOutYn"
                                ng-model="soldOutYn"
                                items-source="_getComboData('soldOutYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <%-- 확장조회 --%>
    <table class="searchTbl" id="tblSearchAddShow" style="display: none;" >
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 등록일자 --%>
            <th><s:message code="mobile.prodSoldOut.regDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px" /></span>
                    <br><br>
                    <%--전체기간--%>
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt"><s:message code="cmm.all.day" /></label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="mobile.prodSoldOut.prodCd"/></th>
            <td><input type="text"  class="sb-input w100" id="prodCd" ng-model="prodCd" /></td>
        </tr>
        <tr>
            <%-- 분류 --%>
            <th><s:message code="mobile.prodSoldOut.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm"
                       ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prodCorner.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;"
                        ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <tr>
            <%-- 바코드 --%>
            <th><s:message code="mobile.prodSoldOut.barCd"/></th>
            <td><input type="text"  class="sb-input w100" id="barCd" ng-model="barCd" /></td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="mobile.prodSoldOut.useYn"/></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="useYn"
                                ng-model="useYn"
                                items-source="_getComboData('useYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
        </tr>
        <c:if test="${brandUseFg ne 0}">
            <tr>
                    <%-- 브랜드명 --%>
                <th><s:message code="mobile.prodSoldOut.hqBrandNm"/></th>
                <td><input type="text"  class="sb-input w100" id="hqBrandNm" ng-model="hqBrandNm" /></td>
            </tr>
        </c:if>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                        <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectSingleStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileProdSoldOutStore"/>
                    </jsp:include>
                        <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${orgnFg eq 'STORE'}">
            <input type="hidden" id="mobileProdSoldOutStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="mt5 oh sb-select dkbr">
        <button class="btn_blue fr" id="btnSave" ng-click="save()">
            <s:message code="mobile.cmm.save"/>
        </button>
    </div>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt5 mb5">
        <colgroup>
            <col class="w20" />
            <col class="w50" />
            <col class="w30" />
        </colgroup>
        <tbody>
        <tr class="brt">
            <th><s:message code="mobile.prodSoldOut.soldOutYn" /></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="soldOutYnChg"
                                ng-model="soldOutYnChg"
                                items-source="_getComboData('soldOutYnChg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
            <td><a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="mobile.prodSoldOut.batchChange" /></a></td>
        </tr>
        </tbody>
    </table>

    <div class="w100" id="mobileProdSoldOutGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:100px; height: 100%;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.prodSoldOut.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.prodSoldOut.soldOutYn"/>" binding="soldOutYn" width="100" align="center" data-map="soldOutYnDataMap" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.prodSoldOut.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prodClassCd"/>" binding="prodClassCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodClassNm"/>" binding="prodClassNm" width="300" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.barCd"/>" binding="barCd" width="100" is-read-only="true"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prod.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column><!--// todo 원산지명 조회 필요-->
                <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80" data-map="poUnitFgComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn"/>" binding="useYn" width="80" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>

                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileProdSoldOutMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum3 mt20">
        <%-- id --%>
        <ul id="mobileProdSoldOutCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
    var brandUseFg = '${brandUseFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/prod/prodSoldOut/mobileProdSoldOut.js?ver=20220303.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/com/popup/selectProdClassCd.jsp">
</c:import>