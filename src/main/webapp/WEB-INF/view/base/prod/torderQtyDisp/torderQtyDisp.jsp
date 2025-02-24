<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="brandUseFg" value="${brandUseFg}" />

<div class="subCon" ng-controller="torderQtyDispCtrl">

    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('torderQtyDispCtrl', 1)">
                <s:message code="cmm.search" />
            </button>
            <div style="display: none;">
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
            </div>
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
            <%-- 등록일자 --%>
            <th><s:message code="torderQtyDisp.regDate" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                    <%--전체기간--%>
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품 --%>
            <th><s:message code="cmm.prod"/></th>
            <td>
                <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                     targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                     closeFunc - 팝업 닫기시 호출할 함수--%>
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdSMoms.jsp" flush="true">
                    <jsp:param name="targetBrandFg" value="A"/>
                    <jsp:param name="targetId" value="torderQtyDispProd"/>
                </jsp:include>
                <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="torderQtyDispStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="torderQtyDispStoreCd" value="${sessionInfo.storeCd}"/>
                <td></td>
                <td></td>
            </c:if>
        </tr>
        <tr style="display: none;">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <c:if test="${brandUseFg == '1'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="cmm.moms.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrandCd"
                                    items-source="_getComboData('srchProdHqBrandCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장브랜드 --%>
                    <th><s:message code="cmm.moms.storeHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchStoreHqBrandCd"
                                    ng-model="storeHqBrandCd"
                                    items-source="_getComboData('srchStoreHqBrandCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchStoreHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
            </c:if>
        </tr>
        <tr>
            <%-- 수량변경표시여부 --%>
            <th><s:message code="torderQtyDisp.ordQtyDispFg" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchOrdQtyDispFg"
                            ng-model="ordQtyDispFg"
                            items-source="_getComboData('ordQtyDispFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="cmm.useYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYn"
                            ng-model="useYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index="1">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="cmm.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 키오스크사용여부 --%>
            <th style="display: none;"><s:message code="torderQtyDisp.kioskUseYn" /></th>
            <td style="display: none;">
                <div class="sb-select">
                    <wj-combo-box
                            id="srchKioskUseYn"
                            ng-model="kioskUseYn"
                            control="kioskUseYnAllCombo"
                            items-source="_getComboData('kioskUseYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="cmm.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="cmm.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        </tbody>
    </table>
    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 팀별 --%>
            <th><s:message code="cmm.moms.momsTeam"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsTeamCombo"
                            ng-model="momsTeam"
                            items-source="_getComboData('momsTeamCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsTeamCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- AC점포별 --%>
            <th><s:message code="cmm.moms.momsAcShop"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsAcShopCombo"
                            ng-model="momsAcShop"
                            items-source="_getComboData('momsAcShopCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsAcShopCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 지역구분 --%>
            <th><s:message code="cmm.moms.momsAreaFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsAreaFgCombo"
                            ng-model="momsAreaFg"
                            items-source="_getComboData('momsAreaFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsAreaFgCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 상권 --%>
            <th><s:message code="cmm.moms.momsCommercial"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsCommercialCombo"
                            ng-model="momsCommercial"
                            items-source="_getComboData('momsCommercialCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsCommercialCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 점포유형 --%>
            <th><s:message code="cmm.moms.momsShopType"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsShopTypeCombo"
                            ng-model="momsShopType"
                            items-source="_getComboData('momsShopTypeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsShopTypeCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장관리타입 --%>
            <th><s:message code="cmm.moms.momsStoreManageType"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsStoreManageTypeCombo"
                            ng-model="momsStoreManageType"
                            items-source="_getComboData('momsStoreManageTypeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsStoreManageTypeCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 그룹 --%>
            <th><s:message code="cmm.moms.branch"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchBranchCdCombo"
                            ng-model="branchCd"
                            items-source="_getComboData('branchCdCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchBranchCdCombo">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장그룹 --%>
            <th><s:message code="cmm.moms.momsStoreFg01"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMomsStoreFg01Combo"
                            ng-model="momsStoreFg01"
                            items-source="_getComboData('momsStoreFg01Combo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMomsStoreFg01Combo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
            <tr>
                <%-- 매장그룹2 --%>
                <th><s:message code="cmm.moms.momsStoreFg02"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreFg02Combo"
                                ng-model="momsStoreFg02"
                                items-source="_getComboData('momsStoreFg02Combo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreFg02Combo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 매장그룹3 --%>
                <th><s:message code="cmm.moms.momsStoreFg03"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreFg03Combo"
                                ng-model="momsStoreFg03"
                                items-source="_getComboData('momsStoreFg03Combo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreFg03Combo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장그룹4 --%>
                <th><s:message code="cmm.moms.momsStoreFg04"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreFg04Combo"
                                ng-model="momsStoreFg04"
                                items-source="_getComboData('momsStoreFg04Combo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreFg04Combo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 매장그룹5 --%>
                <th><s:message code="cmm.moms.momsStoreFg05"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreFg05Combo"
                                ng-model="momsStoreFg05"
                                items-source="_getComboData('momsStoreFg05Combo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreFg05Combo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w15" />
            <col class="w15" />
            <col class="w15" />
            <col class="*" />
        </colgroup>
        <tbody>
        <tr class="brt">
            <%-- 수량변경표시여부 --%>
            <th>
                <s:message code="torderQtyDisp.ordQtyDispFg" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchOrdQtyDispFgChg"
                            ng-model="ordQtyDispFgChg"
                            items-source="_getComboData('ordQtyDispFgComboChg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <td>
                <%-- 일괄적용 --%>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="cmm.batchApply" /></a>
            </td>
            <td>
                <%-- 저장 --%>
                <button class="btn_blue ml5 fr" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 현재화면 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" width="170" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.ordQtyDispFg"/>" binding="ordQtyDispFg" data-map="ordQtyDispFgDataMap" width="110" align="center"></wj-flex-grid-column>
                <c:if test="${brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="120" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.prodClassCd"/>" binding="prodClassCd" width="90" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.prodClassNm"/>" binding="prodClassNm" width="250" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.barCd"/>" binding="barCd" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="torderQtyDisp.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.costUprc"/>" binding="costUprc" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.storeSalePrice"/>" binding="saleUprc" width="90" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.poUnitFg"/>" binding="poUnitFg" width="80" data-map="poUnitFgComboDataMap" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.useYn"/>" binding="useYn" width="65" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.soldOutYn"/>" binding="soldOutYn" width="70" data-map="soldOutYnDataMap" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.channelSoldOutYn"/>" binding="channelSoldOutYn" width="90" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.brandCd"/>" binding="hqBrandCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.brandNm"/>" binding="hqBrandN" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.sideProdYn"/>" binding="sideProdYn" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.sdattrClassCd"/>" binding="sdattrClassCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.sdselGrpCd"/>" binding="sdselGrpCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.regFg"/>" binding="regFg" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.useYn4068"/>" binding="useYn4068" width="100" data-map="useYnComboDataMap" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="torderQtyDisp.useYn4069"/>" binding="useYn4069" width="100" data-map="useYnComboDataMap" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //그리드 --%>

</div>

<script>
    var orgnFg = "${orgnFg}";

    /* 상품상세 필수 START */
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
    var storeProdUseFg = "${storeProdUseFg}";
    // 브랜드
    var brandList = ${brandList};
    // 매장별 브랜드 콤보박스 조회(사용자 상관없이 전체 브랜드 표시)
    var userHqStoreBrandCdComboList = ${userHqStoreBrandCdComboList};
    // 사용자 매장브랜드(조회용)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    // 코너 콤보박스
    var cornerList = ${cornerList};
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
    /* 상품상세 필수 END */
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/torderQtyDisp/torderQtyDisp.js?ver=20250224.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>