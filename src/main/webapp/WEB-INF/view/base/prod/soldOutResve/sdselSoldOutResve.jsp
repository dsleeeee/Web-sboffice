<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="brandUseFg" value="${brandUseFg}" />
<c:set var="toDate" value="${toDate}" />

<div id="sdselSoldOutResveView" class="subCon" style="display: none;">
    <div ng-controller="sdselSoldOutResveCtrl">
        <%--searchTbl--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="soldOutResve.sdsel"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('sdselSoldOutResveCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                   <%-- 확장조회 --%>
                   <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange2()">
                       <s:message code="cmm.search.addShow" />
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
            <%-- 등록 일자 --%>
            <tr>
                <th><s:message code="soldOutResve.startDate" /></th>
                <td colspan="3">
                    <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w120px"></span>
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
                    <c:if test="${brandUseFg == '1'}">
                        <%-- 매장브랜드 --%>
                        <th><s:message code="dayProd.storeHqBrand"/></th>
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
                    <%-- 매장 --%>
                    <th><s:message code="cmm.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="sdselSoldOutResveStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <input type="hidden" id="sdselSoldOutResveStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <c:if test="${brandUseFg == '1'}">
                       <%-- 상품브랜드 --%>
                        <th><s:message code="prodSaleRate.prodHqBrand"/></th>
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
                        <jsp:param name="targetId" value="sdselSoldOutResveProd"/>
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
                                ng-model="sdselSoldOutResveYn"
                                items-source="_getComboData('sdselSoldOutResveYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
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
                    <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd" onkeyup="fnNxBtnSearch('2');"/>
                </td>
                <%-- 선택그룹명 --%>
                <th>
                    <s:message code="sideMenuProdSoldOut.sdselGrpNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm" onkeyup="fnNxBtnSearch('2');"/>
                </td>
            </tr>
            <tr>
                <%-- 선택분류코드 --%>
                <th>
                    <s:message code="sideMenuProdSoldOut.sdselClassCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd" onkeyup="fnNxBtnSearch('2');"/>
                </td>
                <%-- 선택분류명 --%>
                <th>
                    <s:message code="sideMenuProdSoldOut.sdselClassNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm" onkeyup="fnNxBtnSearch('2');"/>
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
        <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
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
        <%--//searchTbl--%>
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w15" />
                <col class="w85" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <th>
                    <s:message code="soldOutResve.soldOutYn" />
                </th>
                <td>
                    <div class="sb-select" style="width:100px; float:left;">
                        <wj-combo-box
                                id="sdselSoldOutResveYnCombo2"
                                ng-model="soldOutYnChange"
                                control="sdselSoldOutResveYnCombo2"
                                items-source="_getComboData('sdselSoldOutResveYnCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <a href="#" class="btn_grayS ml5" ng-click="change()">일괄적용</a>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <button class="btn_skyblue fr" ng-click="add()"><s:message code="soldOutResve.resve" /></button>
            <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.edit" /></button>
            <button class="btn_skyblue fr mr5" ng-click="del()"><s:message code="cmm.del" /></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mt10">
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div id="theGrid" style="height: 350px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        id="wjGridSdselSoldOut"
                        frozen-columns="1">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpCd"/>" binding="sdselGrpCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselGrpNm"/>" binding="sdselGrpNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.fixProdFg"/>" binding="grpFixProdFg" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselTypeFg"/>" binding="sdselTypeFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassCd"/>" binding="sdselClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.sdselQty"/>" binding="sdselQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.requireYn"/>" binding="requireYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuProdSoldOut.prodClass"/>" binding="prodClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="orgStartDate" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="orgStartTime" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.nowSoldOutYn"/>" binding="nowSoldOutYn" width="130" align="center" is-read-only="true" data-map="soldOutYnDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.startDate"/>" binding="startDate" width="150" align="center">
                        <wj-flex-grid-cell-template cell-type="CellEdit">
                            <div class="sb-select">
                              <span class="txtIn w100px">
                                <wj-input-date
                                        value="$value"
                                        min="${toDate}"
                                        max="9999-12-31">
                                </wj-input-date>
                              </span>
                            </div>
                        </wj-flex-grid-cell-template>
                    </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.startTime"/>" binding="startTime" width="100" align="center" data-map="startTimeDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="orgSoldOutYn" is-read-only="true" width="100" align="center" data-map="soldOutYnDataMap" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="" binding="resveFg" is-read-only="true" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="soldOutResve.soldOutYn"/>" binding="soldOutYn" width="100" align="center" data-map="soldOutYnDataMap"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="sdselSoldOutResveCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOutResve/sdselSoldOutResve.js?ver=20230807.01" charset="utf-8"></script>

<%-- 예약 추가 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/soldOutResve/sdselSoldOutResveAdd.jsp">
</c:import>
