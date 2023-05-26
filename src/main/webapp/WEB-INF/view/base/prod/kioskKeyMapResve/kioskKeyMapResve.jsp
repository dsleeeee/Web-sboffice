<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="tomorrowDate" value="${tomorrowDate}" />

<div class="subCon" id="kioskKeyMapResve" ng-controller="kioskKeyMapResveCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="kioskKeyMapResve.kioskKeyMapResve" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('kioskKeyMapResveCtrl', 1)">
                <s:message code="cmm.search" />
            </button>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
                <%-- 일괄변경 --%>
                <button class="btn_blue mr5 fl" id="btnShow" ng-click="changeShow()">
                    <s:message code="kioskKeyMapResve.select.changeAll" />
                </button>
            </c:if>
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeStartDate" ng-model="startDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeEndDate" ng-model="endDate" class="w120px"></span>
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
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="kioskKeyMapResve.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                </td>
                <%-- 매장명 --%>
                <th><s:message code="kioskKeyMapResve.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <tr>
                <%-- POS번호 --%>
                <th><s:message code="kioskKeyMapResve.posNo" /></th>
                    <td>
                        <div class="sb-select w100 fl">
                            <wj-combo-box
                                    id="posNoCombo"
                                    ng-model="posNo"
                                    control="posNoCombo"
                                    items-source="_getComboData('posNoCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="setTuClsType(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                <th></th>
                <td></td>
            </tr>
        </c:if>
        <tr>
            <%-- 예약키맵그룹 --%>
            <th><s:message code="kioskKeyMapResve.resveTuClsType" /></th>
            <td>
                <div class="sb-select w100 fl">
                    <wj-combo-box
                            id="tuClsTypeCombo"
                            ng-model="tuClsType"
                            control="tuClsTypeCombo"
                            items-source="_getComboData('tuClsTypeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 키맵구분 --%>
            <th><s:message code="kioskKeyMapResve.envstCd" /></th>
            <td>
                <div class="sb-select w100 fl">
                    <wj-combo-box
                            id="envstCdCombo"
                            ng-model="envstCd"
                            control="envstCdCombo"
                            items-source="_getComboData('envstCdCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장브랜드 --%>
                <th><s:message code="dayProd.storeHqBrand"/></th>
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
            </tr>
        </c:if>
        </tbody>
    </table>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                <th><s:message code="dayProd.momsTeam"/></th>
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
                <th><s:message code="dayProd.momsAcShop"/></th>
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
                <th><s:message code="dayProd.momsAreaFg"/></th>
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
                <th><s:message code="dayProd.momsCommercial"/></th>
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
                <th><s:message code="dayProd.momsShopType"/></th>
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
                <th><s:message code="dayProd.momsStoreManageType"/></th>
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
                <th><s:message code="dayProd.branchCd"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchBranchCdComboo"
                                ng-model="branchCd"
                                items-source="_getComboData('branchCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchBranchCdComboo">
                        </wj-combo-box>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </c:if>

    <table class="searchTbl mt10" id="tblHqChange" style="display: none;">
        <colgroup>
            <col class="w13" />
            <col class="w87" />
        </colgroup>
        <tbody>
        <%--판매가--%>
        <tr class="brt">
            <th>
                <s:message code="kioskKeyMapResve.resveTuClsType" />
            </th>
            <th class="oh gr">
                <div class="sb-select" style="width:120px; float:left;">
                    <wj-combo-box
                            id="tuClsTypeCombo2"
                            ng-model="tuClsTypeChange"
                            control="tuClsTypeCombo2"
                            items-source="_getComboData('tuClsTypeCombo2')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <a href="#" class="btn_grayS ml10" ng-click="change()">일괄적용</a>
            </th>
        </tr>
        <tr>
            <th colspan="2">
                <p class="s12 bk lh20">
                    체크박스에서 선택된 항목만 일괄적용 됩니다.
                </p>
            </th>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr" ng-click="add('4069')"><s:message code="kioskKeyMapResve.storeEnv4069" /></button>
        <button class="btn_skyblue fr mr5" ng-click="add('4068')"><s:message code="kioskKeyMapResve.storeEnv4068" /></button>
        <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.edit" /></button>
        <button class="btn_skyblue fr mr5" ng-click="del()"><s:message code="cmm.del" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                ime-enabled="true"
                id="wjGridKioskKeyMapResve">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.branchCd"/>" binding="branchCd" is-read-only="true" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.branchNm"/>" binding="branchNm" is-read-only="true" width="80" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.posNo"/>" binding="posNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.envstCd"/>" binding="envstCd" width="70" is-read-only="true" align="center" data-map="envstDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.brand"/>" binding="brand" is-read-only="true" width="80" align="center" data-map="brandDataMap" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.momsTeam"/>" binding="momsTeam" is-read-only="true" width="80" align="center" data-map="momsTeamDataMap" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.momsAcShop"/>" binding="momsAcShop" width="80" align="center" data-map="momsAcShopDataMap" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="orgStartDate" width="100" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.nowTuClsType"/>" binding="nowTuClsType" width="90" align="center" is-read-only="true" data-map="tuClsTypeDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.orgModDt"/>" binding="modDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.startDate"/>" binding="startDate" width="120" align="center">
                    <wj-flex-grid-cell-template cell-type="CellEdit">
                        <div class="sb-select">
                          <span class="txtIn w110px">
                            <wj-input-date
                                    value="$value"
                                    min="${tomorrowDate}"
                                    max="9999-12-31">
                            </wj-input-date>
                          </span>
                        </div>
                    </wj-flex-grid-cell-template>
                </wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="orgTouchKeyGrp" is-read-only="true" width="100" align="center" data-map="tuClsTypeDataMap" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.tuClsType"/>" binding="tuClsType" width="90" align="center" data-map="tuClsTypeDataMap"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="kioskKeyMapResveCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
    </div>
</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    // 키오스크 키맵그룹 조회
    var tuClsTypeData = ${kioskTuClsTypeList};
    var tuClsTypeDataAll = ${kioskTuClsTypeListAll};

    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMapResve/kioskKeyMapResve.js?ver=20230526.02" charset="utf-8"></script>

<%-- 예약 추가 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMapResve/kioskKeyMapResveAdd.jsp">
</c:import>