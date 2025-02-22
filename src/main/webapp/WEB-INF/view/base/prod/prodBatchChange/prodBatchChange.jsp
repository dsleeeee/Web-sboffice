<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="prodBatchChangeVeiw" name="prodBatchChangeVeiw" class="subCon" style="padding: 10px 20px 40px;">

    <div ng-controller="prodBatchChangeCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="prodBatchChange.prodBatchChange" /> (<s:message code="prodBatchChange.tab1" />)</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('prodBatchChangeCtrl',1)" id="nxBtnSearch1">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="prodBatchChange.prodCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 상품명 --%>
                <th>
                    <s:message code="prodBatchChange.prodNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th>
                    <s:message code="prodBatchChange.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="prodBatchChange.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 바코드 --%>
                <th>
                    <s:message code="prodBatchChange.barCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 판매상품여부 --%>
                <th>
                    <s:message code="prodBatchChange.saleProdYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchSaleProdYn"
                                ng-model="saleProdYn"
                                items-source="_getComboData('saleProdYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 포인트적립여부 --%>
                <th>
                    <s:message code="prodBatchChange.pointSaveYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchPointSaveYn"
                                ng-model="pointSaveYn"
                                items-source="_getComboData('pointSaveYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매핑상품코드 --%>
                <th>
                    <s:message code="prodBatchChange.mapProdCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMapProdCd" ng-model="mapProdCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 본사일때만 --%>
                <c:if test="${orgnFg eq 'HQ'}">
                    <%-- 가격관리구분 --%>
                    <th>
                        <s:message code="prodBatchChange.prcCtrlFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchPrcCtrlFg"
                                    ng-model="prcCtrlFg"
                                    items-source="_getComboData('prcCtrlFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <%-- 프랜 매장일때만 --%>
                <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                    <%-- 상품등록구분 --%>
                    <th>
                        <s:message code="prodBatchChange.regFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchRegFg"
                                    ng-model="regFg"
                                    items-source="_getComboData('regFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <%-- 단독매장일때만 --%>
                <c:if test="${orgnFg eq 'STORE' and hqOfficeCd eq '00000'}">
                    <th></th>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 과세여부 --%>
                <th><s:message code="prodBatchChange.vatFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchVatFg"
                                ng-model="vatFg"
                                items-source="_getComboData('vatFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 부가세포함여부 --%>
                <th><s:message code="prodBatchChange.vatIncldYn" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchVatIncldYn"
                                ng-model="vatIncldYn"
                                items-source="_getComboData('vatIncldYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일 --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScale"
                    control="listScaleCombo"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
            <%-- 저장 --%>
            <button class="btn_blue ml5 fr" id="btnProdSave" ng-click="save()"><s:message code="cmm.save" /></button>
            <%-- 프랜 매장일때만 --%>
            <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                <%-- 상품등록구분이 '본사'인 상품은 수정할 수 없습니다. --%>
                <div class="s14 bk lh25 ml5 mr15 fr">
                    <s:message code="prodBatchChange.regFgHqBlank" />
                </div>
            </c:if>
        </div>

        <%-- 일괄적용 --%>
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 판매상품여부 --%>
                <th>
                    <s:message code="prodBatchChange.saleProdYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchSaleProdYnChg"
                                ng-model="saleProdYnChg"
                                items-source="_getComboData('saleProdYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('saleProdYnChg')"><s:message code="prodBatchChange.batchChange" /></a>
                </td>
                <%-- 포인트적립여부 --%>
                <th>
                    <s:message code="prodBatchChange.pointSaveYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchPointSaveYnChg"
                                ng-model="pointSaveYnChg"
                                items-source="_getComboData('pointSaveYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('pointSaveYnChg')"><s:message code="prodBatchChange.batchChange" /></a>
                </td>
            </tr>
            <tr>
                <%-- 매핑상품코드 --%>
                <th>
                    <s:message code="prodBatchChange.mapProdCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMapProdCdChg" ng-model="mapProdCdChg" />
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('mapProdCdChg')"><s:message code="prodBatchChange.batchChange" /></a>
                </td>
                <%-- 본사일때만 --%>
                <c:if test="${orgnFg eq 'HQ'}">
                    <%-- 가격관리구분 --%>
                    <th>
                        <s:message code="prodBatchChange.prcCtrlFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchPrcCtrlFgChg"
                                    ng-model="prcCtrlFgChg"
                                    items-source="_getComboData('prcCtrlFgChgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 일괄적용 --%>
                    <td>
                        <a href="#" class="btn_grayS ml10" ng-click="batchChange('prcCtrlFgChg')"><s:message code="prodBatchChange.batchChange" /></a>
                    </td>
                </c:if>
                <%-- 매장일때만 --%>
                <c:if test="${orgnFg eq 'STORE'}">
                    <th></th>
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 과세여부 --%>
                <th>
                    <s:message code="prodBatchChange.vatFg" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchVatFgChg"
                                ng-model="vatFgChg"
                                items-source="_getComboData('vatFgChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('vatFgChg')"><s:message code="prodBatchChange.batchChange" /></a>
                </td>
                <%-- 부가세포함여부 --%>
                <th>
                    <s:message code="prodBatchChange.vatIncldYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchVatIncldYnChg"
                                ng-model="vatIncldYnChg"
                                items-source="_getComboData('vatIncldYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index="1">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('vatIncldYnChg')"><s:message code="prodBatchChange.batchChange" /></a>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridProdBatchChange">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <%-- 프랜 매장일때만 --%>
                    <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                        <wj-flex-grid-column header="<s:message code="prodBatchChange.regFg"/>" binding="regFg" data-map="regFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.prodClassCd"/>" binding="pathNm" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.pointSaveYn"/>" binding="pointSaveYn" data-map="pointSaveYnDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.mapProdCd"/>" binding="mapProdCd" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.saleProdYn"/>" binding="oldSaleProdYn" data-map="saleProdYnDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.pointSaveYn"/>" binding="oldPointSaveYn" data-map="pointSaveYnDataMap" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.mapProdCd"/>" binding="oldMapProdCd" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <%-- 본사일때만 --%>
                    <c:if test="${orgnFg eq 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="prodBatchChange.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodBatchChange.prcCtrlFg"/>" binding="oldPrcCtrlFg" data-map="prcCtrlFgDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.vatFg"/>" binding="oldVatFg" data-map="vatFgDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.vatIncldYn"/>" binding="vatIncldYn" data-map="vatIncldYnDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.vatIncldYn"/>" binding="oldVatIncldYn" data-map="vatIncldYnDataMap" width="100" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prodBatchChangeCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="prodBatchChangeExcelCtrl">
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <%-- 프랜 매장일때만 --%>
                <c:if test="${orgnFg eq 'STORE' and hqOfficeCd ne '00000'}">
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.regFg"/>" binding="regFg" data-map="regFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.prodClassCd"/>" binding="pathNm" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.pointSaveYn"/>" binding="pointSaveYn" data-map="pointSaveYnDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.mapProdCd"/>" binding="mapProdCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <%-- 본사일때만 --%>
                <c:if test="${orgnFg eq 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prodBatchChange.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.vatIncldYn"/>" binding="vatIncldYn" data-map="vatIncldYnDataMap" width="100" align="center"></wj-flex-grid-column>

                <%--저장시 필요--%>
                <wj-flex-grid-column header="<s:message code="prodBatchChange.saleProdYn"/>" binding="oldSaleProdYn" width="90" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

    <%-- 판매상품여부 --%>
    var saleProdYnData = ${ccu.getCommCodeExcpAll("067")};
    <%-- 포인트적립여부 --%>
    var pointSaveYnData = ${ccu.getCommCodeExcpAll("067")};
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodBatchChange/prodBatchChange.js?ver=20250122.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>