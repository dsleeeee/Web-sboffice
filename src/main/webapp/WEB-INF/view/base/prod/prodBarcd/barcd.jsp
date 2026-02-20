<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfodelete.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="barcdCtrl">
    <%--searchTbl--%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="barcd.prodBarcd" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('barcdCtrl',1)">
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
            <th><s:message code="barcd.regDate" /></th>
            <td colspan="3">
                <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeStartDate"
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
                    <span class="rg">~</span>
                    <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeEndDate"
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
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
            <th><s:message code="barcd.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="barcd.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="barcd.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="barcd.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="barcd.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="barcd.prodUseYn" /></th>
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
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
                <%-- 바코드유무 --%>
            <th><s:message code="barcd.barcdYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYn"
                            ng-model="barcdYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <%-- 브랜드 --%>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <th><s:message code="barcd.brandNm" /></th>
                <td><input type="text" class="sb-input w100" id="srchBrandNm" ng-model="hqBrandNm" onkeyup="fnNxBtnSearch();"/></td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <%--//searchTbl--%>

    <div class="mt10 oh sb-select dkbr">
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
            <%-- 전체상품엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadTotal()" ><s:message code="cmm.excel.downTotal"/></button>
            <%-- 조회조건내역엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadCondition()" ><s:message code="cmm.excel.downCondition"/></button>
            <%-- 상품엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()" ><s:message code="cmm.excel.down"/></button>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()" ><s:message code="barcd.excelUpload"/></button>
            <%-- 입력양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()" ><s:message code="barcd.sampleDownload"/></button>
    </div>
    <div class="mt5 oh sb-select dkbr">
        <c:if test="${orgnFg == 'STORE'}">
            <c:if test="${hqOfficeCd ne '00000'}">
            <span class="s12 lh20 fl mb5">프랜차이즈 매장은 매장에서 등록한 상품의 바코드만 수정 가능합니다.(본사 상품은 본사에서만 수정 가능)<br>
                [양식다운로드] 시 수정 가능한 상품의 양식만 다운로드 됩니다.</span>
            </c:if>
        </c:if>
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" ng-click="save()" ><s:message code="cmm.save"/></button>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="delete()" ><s:message code="cmm.delete"/></button>
        <%-- 자동채번 --%>
        <button class="btn_skyblue ml5 fr" ng-click="barCdAutoSet()" ><s:message code="barcd.autoBarcd"/></button>
        <%-- 바코드 자동 생성 --%>
<%--        <button class="btn_skyblue ml5 fr" ng-click="autoBarcd()" ><s:message code="barcd.autoBarcd"/></button>--%>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10" id="barcdGrid">
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
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridProdBarcd">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.result"/>" binding="result" width="80" is-read-only="true" align="center" visible = "false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdOld"/>" binding="barcdOld" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdNew"/>" binding="barCd" width="*" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.regFg"/>" binding="regFg" width="*" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="barcdCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="wj-TblWrapBr mt10" ng-controller="totalExcelCtrl" style="display: none;">
        <div class="wj-gridWrap" id="qn">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    loaded-rows="loadedRows(s,e)"
                    is-read-only="true"
                    frozen-columns="6"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="barcd.prodCd"/>" binding="prodCd" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdOld"/>" binding="barcdOld" width="*" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="barcd.barcdNew"/>" binding="barcd" width="*" format="d"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//엑셀 리스트--%>
    </div>
</div>

<script>
    var orgnFg = '${orgnFg}';
    var hqOfficeCd = '${hqOfficeCd}';

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodBarcd/barcd.js?ver=20260212.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 레이어 팝업 : 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodBarcd/excelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>