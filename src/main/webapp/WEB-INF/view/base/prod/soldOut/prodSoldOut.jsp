<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="brandUseFg" value="${brandUseFg}" />

<div class="subCon" ng-controller="prodSoldOutCtrl" id="prodSoldOutView">
    <%--searchTbl--%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="soldOut.prod" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('prodSoldOutCtrl',1)">
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
        <%-- 등록 일자 --%>
        <tr>
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
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="prod.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="prod.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
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
            <%-- 브랜드 --%>
            <c:if test="${brandUseFg == '1'}">
                <th><s:message code="prod.brandNm" /></th>
                <td><input type="text" class="sb-input w100" id="srchBrandNm" ng-model="hqBrandNm" onkeyup="fnNxBtnSearch('2');"/></td>
            </c:if>
            <c:if test="${brandUseFg == '0'}">
                <th></th>
                <td></td>
            </c:if>
        </tr>
        <tr>
            <th><s:message code="soldOut.soldOutYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchSoldOutYn"
                            ng-model="soldOutYn"
                            items-source="_getComboData('soldOutYnCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <c:if test="${orgnFg == 'HQ'}">
                <th><s:message code="soldOut.store" /></th>
                <td>
                <c:if test="${momsEnvstVal == '0'}">
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                            <jsp:param name="targetId" value="prodSoldOutStore"/>
                        </jsp:include>
                </c:if>
                <c:if test="${momsEnvstVal == '1'}">
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreSMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="prodSoldOutStore"/>
                    </jsp:include>
                </c:if>
                </td>
            </c:if>
        </tr>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
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
        <%-- 저장 --%>
        <button class="btn_blue ml5 fr" id="btnSoldOutYnSave" ng-click="save()"><s:message code="cmm.save" /></button>
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
                <s:message code="soldOut.soldOutYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchSoldOutYnChg"
                            ng-model="soldOutYnChg"
                            items-source="_getComboData('soldOutYnComboChg')"
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
        </tr>
        </tbody>
    </table>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="theGrid" style="height: 370px;">
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
                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="soldOut.soldOutYn"/>" binding="soldOutYn" width="80" data-map="soldOutYnDataMap" align="center"></wj-flex-grid-column>
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

                <wj-flex-grid-column header="<s:message code="prod.brandCd"/>" binding="hqBrandCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.brandNm"/>" binding="hqBrandN" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sideProdYn"/>" binding="sideProdYn" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdattrClassCd"/>" binding="sdattrClassCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sdselGrpCd"/>" binding="sdselGrpCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.regFg"/>" binding="regFg" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="prodSoldOutCtrl"/>
            </jsp:include>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prodSoldOutCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var orgnFg = "${orgnFg}";
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 브랜드
    var brandList = ${brandList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOut/prodSoldOut.js?ver=20220225.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>
