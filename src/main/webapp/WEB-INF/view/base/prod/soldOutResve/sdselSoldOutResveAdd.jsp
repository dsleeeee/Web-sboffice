<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="sdselSoldOutResveAddLayer" control="sdselSoldOutResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:90%;">

    <div ng-controller="sdselSoldOutResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="soldOutResve.sdsel"/>&nbsp;<s:message code="soldOutResve.soldOutResve"/>&nbsp;<s:message code="cmm.add"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <c:if test="${brandUseFg == '1'}">
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
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="sdselSoldOutResveAddStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                        <input type="hidden" id="sdselSoldOutResveAddStoreCd" value="${sessionInfo.storeCd}"/>
                    </c:if>
                </tr>
                <tr>
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
                        </c:if>
                    </c:if>
                    <%-- 상품 --%>
                    <th><s:message code="prodRankMoms.prod"/></th>
                    <td>
                        <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                             targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                             displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                             modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                             closeFunc - 팝업 닫기시 호출할 함수--%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdSMoms.jsp" flush="true">
                            <jsp:param name="targetId" value="sdselSoldOutResveAddProd"/>
                        </jsp:include>
                        <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
                <tr>
                    <%-- 품절여부 --%>
                    <th><s:message code="soldOutResve.soldOutYn" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchsoldOutYn"
                                    ng-model="soldOutResveYn"
                                    items-source="_getComboData('soldOutResveYnCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 사용여부 --%>
                    <th><s:message code="prod.useYn" /></th>
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
                    <th><s:message code="prod.prodClass" /></th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                               placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                </tr>
                <tr>
                    <%-- 선택그룹코드 --%>
                    <th>
                        <s:message code="sideMenuProdSoldOut.sdselGrpCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd"/>
                    </td>
                    <%-- 선택그룹명 --%>
                    <th>
                        <s:message code="sideMenuProdSoldOut.sdselGrpNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 선택분류코드 --%>
                    <th>
                        <s:message code="sideMenuProdSoldOut.sdselClassCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd"/>
                    </td>
                    <%-- 선택분류명 --%>
                    <th>
                        <s:message code="sideMenuProdSoldOut.sdselClassNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="prod.prodCd" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="prod.prodNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <table class="searchTbl" id="tblSearchAddShowAdd2" style="display: none;">
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
                        <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
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
                        </c:if>
                        <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
                            <td></td>
                            <td></td>
                        </c:if>
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
            </c:if>

            <table class="searchTbl mt10" id="tblAddChange2" style="display: none;">
                <colgroup>
                    <col class="w13" />
                    <col class="w87" />
                </colgroup>
                <tbody>
                <tr class="brt">
                    <th>
                        <s:message code="soldOutResve.resveSoldOutYn" />
                    </th>
                    <th class="oh gr">
                        <div class="sb-select" style="width:120px; float:left;">
                            <wj-combo-box
                                    id="soldOutYnAddCombo2"
                                    ng-model="soldOutYnAddChange"
                                    control="soldOutYnAddCombo2"
                                    items-source="_getComboData('soldOutYnAddCombo2')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <a href="#" class="btn_grayS ml10" ng-click="changeAdd()">일괄적용</a>
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
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="searchSdselSoldOutResveAddList()"><s:message code="cmm.search" /></button>
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeEnv()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue mr5 fr" id="btnShow" ng-click="changeAddShow()">
                    <s:message code="soldOutResve.select.changeAll" />
                </button>
             </div>

            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridHqSalePricePop"
                            frozen-columns="1">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.branchCd"/>" binding="branchCd" is-read-only="true" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.branchNm"/>" binding="branchNm" is-read-only="true" width="80" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpCd"/>" binding="sdselGrpCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpNm"/>" binding="sdselGrpNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassCd"/>" binding="sdselClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.orgSoldOutYn"/>" binding="orgSoldOutYn" is-read-only="true" width="90" align="center" data-map="soldOutYnDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="soldOutResve.soldOutYn"/>" binding="soldOutYn" width="90" align="center" data-map="soldOutYnDataMap"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>

            <div>
                <table class="tblType01">
                    <colgroup>
                        <col class="w20"/>
                        <col class="w30"/>
                        <col class="w20"/>
                        <col class="w30"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="soldOutResve.startDate"/><s:message code="soldOutResve.startTime"/></th>
                        <td colspan="3">
                            <div class="sb-select fl">
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          value="startDate"
                                          ng-model="startDate"
                                          control="startDateCombo"
                                          format="yyyy/MM/dd"
                                          min="${toDate}"
                                          max="9999-12-31"
                                          initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                            </div>
                            <div class="sb-select fl ml5" style="width:65px;">
                                <wj-combo-box
                                        id="startTime"
                                        ng-model="startTime"
                                        items-source="_getComboData('startTimeCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="startTimeCombo"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="wj-dialog-footer">
                <div class="btnSet2">
                    <button class="btn_blue" ng-click="saveProdPrice2()"><s:message code="cmm.save" /></button>
                </div>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOutResve/sdselSoldOutResveAdd.js?ver=20240221.01" charset="utf-8"></script>
