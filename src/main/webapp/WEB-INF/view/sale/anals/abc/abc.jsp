<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="gvOrgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="prodEnvstVal" value="${prodEnvstVal}" />

<div class="subCon3" ng-controller="abcCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="nxBtnSearch" ng-click="_broadcast('abcCtrlSrch')">
            <s:message code="cmm.search" />
        </button>
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
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchAbcStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchAbcEndDate" class="w110px"></span>
                    <span class="chk ml10" style="display: none;">
                        <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt"><s:message code="cmm.all.day" /></label>
                    </span>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <input type="hidden" id="abcSelectStoreCd" value=""/>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 --%>
                    <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="abcSelectStore"/>
                        <jsp:param name="targetTableId" value="abcSelectTable"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="abcSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
        </tr>
        <tr>
            <%-- 누적판매비율 --%>
            <th><s:message code="abc.totSaleRate" /></th>
            <td colspan="3">
                <span class="txtIn" style="width:20%;">A등급<input id="abcGradeA" class="sb-input" value="70" style="width:40%;" onkeyup="fnNxBtnSearch();">%</span>
                <span class="txtIn" style="width:20%;">B등급<input id="abcGradeB" class="sb-input" value="90" style="width:40%;" onkeyup="fnNxBtnSearch();">%</span>
                <span class="txtIn" style="width:20%;">C등급<input id="abcGradeC" class="sb-input" value="100" style="width:45%;" onkeyup="fnNxBtnSearch();">%</span>
                <span class="txtIn" style="width:20%;">그외 Z등급</span>
            </td>
        </tr>
        <tr>
            <%-- 정렬순서 --%>
            <th><s:message code="abc.order" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                        <wj-combo-box id="srchAbcDisplay" ng-model="sortFg"
                                      items-source="_getComboData('srchAbcDisplay')"
                                      display-member-path="name" selected-value-path="value"
                                      is-editable="false" initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="periodIostock.srchOption" /></th>
            <td>
                <span class="chk ml10">
                    <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                    <label for="chkDt">
                        <s:message code="periodIostock.prodClassDisplay" />
                    </label>
                </span>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadAbc()"><s:message code="cmm.excel.down" />
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="abcGrid"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="abc.grade"/>"              binding="grade"       width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.lv1Nm"/>"              binding="lv1Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.lv2Nm"/>"              binding="lv2Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.lv3Nm"/>"              binding="lv3Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.prodCd"/>"             binding="prodCd"        width="*" align="center" is-read-only="true" word-wrap="true" multi-line="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.prodNm"/>"             binding="prodNm"   width="165" align="center" is-read-only="true" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.realSaleAmt"/>"        binding="realSaleAmt"   width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.totSaleQty"/>"         binding="totSaleQty"   width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.rat"/>"                binding="rat"        width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="abc.acc"/>"                binding="accRat"        width="*" align="center" is-read-only="true" word-wrap="true" multi-line="true"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="abcCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script>
    //var prodEnvstVal = "${prodEnvstVal}";

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

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/abc/abc.js?ver=20250221.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>