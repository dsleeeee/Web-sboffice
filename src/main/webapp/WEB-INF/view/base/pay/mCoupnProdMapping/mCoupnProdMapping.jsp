<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="mCoupnProdMappingCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('mCoupnProdMappingCtrl', 'A')" id="nxBtnSearch">
                    <s:message code="cmm.search" />(가로)
                </button>
            </div>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('mCoupnProdMappingCtrl', 'B')">
                    <s:message code="cmm.search" />(세로)
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 등록일자 --%>
                <th><s:message code="mCoupnProdMapping.regDt" /></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w100px">
                            <wj-combo-box
                                    id="srchRegDtTypeCombo"
                                    ng-model="regDtType"
                                    items-source="_getComboData('regDtTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchRegDtTypeCombo">
                            </wj-combo-box>
                        </span>
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
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
                <th><s:message code="cmm.prodCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="cmm.prodNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th><s:message code="cmm.prodClass" /></th>
                <td>
                    <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 바코드 --%>
                <th><s:message code="mCoupnProdMapping.barCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 사용여부 --%>
                <th><s:message code="mCoupnProdMapping.useYn" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchUseYnCombo"
                                ng-model="useYn"
                                items-source="_getComboData('useYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchUseYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

            <table class="searchTbl mt10">
                <colgroup>
                    <col class="w100" />
                </colgroup>
                <tbody>
                <tr class="brt">
                    <th class="oh gr fl w70" style="height: 40px; padding-left: 10px">
                        <p class="s12 bk lh20">
                            * 상품코드, 쿠폰사, 매핑코드, 비고만 저장됩니다.<br />
                        </p>
                    </th>
                    <th class="oh gr fr w30" style="height: 40px; font-size:15px">
                        <%-- 엑셀업로드 --%>
                        <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                            <s:message code="cmm.excel.excelUpload" />
                        </button>
                        <%-- 양식다운로드 --%>
                        <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
                            <s:message code="cmm.excel.sampleDown" />
                        </button>
                    </th>
                </tr>
                </tbody>
            </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일 --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScale"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
            <%--// 페이지 스케일 --%>
            <%-- 이력조회 --%>
            <button class="btn_skyblue ml5 fr" ng-click="mCoupnProdMappingHist()">
                <s:message code="mCoupnProdMapping.mCoupnProdMappingHist" />
            </button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="mCoupnProdMappingCtrlPager" data-size="10">
                </ul>
            </div>
            <%-- //페이지 리스트 --%>
        </div>
    </div>

    <%-- 엑셀 리스트 --%>
    <div class="w100 mt10" style="display:none;" ng-controller="mCoupnProdMappingExcelUploadSampleCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="excelUploadSampleFlex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnProdMapping.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnProdMapping.mcoupnNm"/>" binding="mcoupnNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnProdMapping.mappingCd"/>" binding="mcoupnProdCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mCoupnProdMapping.remark"/>" binding="remark" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //엑셀 리스트 --%>

</div>

<script type="text/javascript">
    // 사용여부
    var useYnData = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/pay/mCoupnProdMapping/mCoupnProdMapping.js?ver=20250819.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 모바일쿠폰상품매핑 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/pay/mCoupnProdMapping/mCoupnProdMappingExcelUpload.jsp">
</c:import>

<%-- 모바일쿠폰상품매핑 이력조회 팝업 --%>
<c:import url="/WEB-INF/view/base/pay/mCoupnProdMapping/mCoupnProdMappingHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>