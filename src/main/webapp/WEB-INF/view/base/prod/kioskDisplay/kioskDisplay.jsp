<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="brandUseFg" value="${brandUseFg}" />

<div class="subCon" ng-controller="kioskDisplayCtrl" >
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('kioskDisplayCtrl',1)">
                <s:message code="cmm.search" />
            </button>
            <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
               <%-- 확장조회 --%>
               <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
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
        <tr>
            <%-- 등록일자 --%>
            <th><s:message code="prod.regDate" /></th>
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
                        <jsp:param name="targetId" value="kioskDisplayStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="kioskDisplayStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
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
                    <jsp:param name="targetBrandFg" value="A"/>
                    <jsp:param name="targetId" value="kioskDisplayProd"/>
                </jsp:include>
                <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 비노출여부 --%>
            <th><s:message code="kioskDisplay.kioskDisplayYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchKioskDisplayYn"
                            ng-model="kioskDisplayYn"
                            items-source="_getComboData('kioskDisplayYnCombo')"
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
                <%-- 키오스크사용여부 --%>
            <th><s:message code="kioskDisplay.kioskUseYn" /></th>
            <td>
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
    </c:if>
    <%--//searchTbl--%>

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
            <%-- 판매상품여부 --%>
            <th>
                <s:message code="kioskDisplay.kioskDisplayYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchKioskDisplayYnChg"
                            ng-model="kioskDisplayYnChg"
                            items-source="_getComboData('kioskDisplayYnComboChg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="prodBatchChange.batchChange" /></a>
            </td>
            <%-- 저장 --%>
            <td>
                <button class="btn_blue ml5 fr" id="btnKioskDisplayYnSave" ng-click="save()"><s:message code="cmm.save" /></button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        <%-- 엑셀업로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnExcelUpload" ng-click="excelUpload()">
            <s:message code="cmm.excel.excelUpload" />
        </button>
        <%-- 양식다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnExcelDownload" ng-click="sampleDownload()">
            <s:message code="cmm.excel.sampleDown" />
        </button>
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
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="170" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskDisplay.kioskDisplayYn"/>" binding="kioskDisplayYn" width="80" data-map="kioskDisplayYnDataMap" align="center"></wj-flex-grid-column>
                <c:if test="${brandUseFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.prodClassCd"/>" binding="prodClassCd" width="90" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodClassNm"/>" binding="prodClassNm" width="250" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.barCd"/>" binding="barCd" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prod.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskDisplay.storeSalePrice"/>" binding="saleUprc" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column><!--// todo 원산지명 조회 필요-->
                <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80" data-map="poUnitFgComboDataMap" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn"/>" binding="useYn" width="70" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="soldOut.soldOutYn"/>" binding="soldOutYn" width="70" data-map="soldOutYnDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="soldOut.channelSoldOutYn"/>" binding="channelSoldOutYn" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.brandCd"/>" binding="hqBrandCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandN" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sideProdYn"/>" binding="sideProdYn" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdattrClassCd"/>" binding="sdattrClassCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdselGrpCd"/>" binding="sdselGrpCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.regFg"/>" binding="regFg" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn4068"/>" binding="useYn4068" width="100" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.useYn4069"/>" binding="useYn4069" width="100" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>


    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10" style="display: none;" ng-controller="kioskDisplayExcelCtrl">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="theGrid" style="height: 350px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskDisplay.kioskDisplayYn"/>" binding="kioskDisplayYn" width="80" data-map="kioskDisplayYnDataMap" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>
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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskDisplay/kioskDisplay.js?ver=20250221.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>

<%-- 엑셀업로드 --%>
<c:import url="/WEB-INF/view/base/prod/kioskDisplay/excelUploadKioskDisplay.jsp">
</c:import>