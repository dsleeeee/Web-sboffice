<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/mobile/stock/status/currUnity/"/>

<div class="subCon"`>
    <div ng-controller="mobileCurrUnityCtrl">
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch" ng-click="_broadcast('mobileCurrUnitySrchCtrl')">
                <s:message code="cmm.search" />
            </button>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w20"/>
                <col class="w25"/>
                <col class="w15"/>
                <col class="w40"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 상품코드 --%>
                <th><s:message code="hqCurr.prodCd"/></th>
                <td>
                    <input type="text" id="srchProdCd" name="srchProdCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="hqCurr.prodNm"/></th>
                <td>
                    <input type="text" id="srchProdNm" name="srchProdNm" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 바코드 --%>
                <th><s:message code="hqCurr.barcdNm"/></th>
                <td>
                    <input type="text" id="srchBarcdCd" name="srchBarcdCd" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
                </td>

                <%-- 분류 --%>
                <th><s:message code="hqCurr.prodClass"/></th>
                <td>
                    <input type="text" class="sb-input w50" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                           placeholder="<s:message code="cmm.all" />" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                    <span class="chk ml10">
            <input type="checkbox" class="mt10" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                <label for="chkDt">
                <s:message code="currUnity.isChk" />
            </label>
        </span>
                </td>
            </tr>
<%--            <tr>--%>
<%--                &lt;%&ndash; 거래처 &ndash;%&gt;--%>
<%--                <th style="display: none;"><s:message code="hqCurr.vendrNm"/></th>--%>
<%--                <td style="display: none;">--%>
<%--                    &lt;%&ndash; 거래처선택 모듈 싱글 선택 사용시 include--%>
<%--                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id--%>
<%--                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)--%>
<%--                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)--%>
<%--                                      closeFunc - 팝업 닫기시 호출할 함수--%>
<%--                    &ndash;%&gt;--%>
<%--                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">--%>
<%--                        <jsp:param name="targetId" value="currUnitySelectVendr"/>--%>
<%--                        <jsp:param name="displayNm" value="전체"/>--%>
<%--                    </jsp:include>--%>
<%--                    &lt;%&ndash;// 거래처선택 모듈 싱글 선택 사용시 include &ndash;%&gt;--%>
<%--                    <input type="hidden" id="currUnitySelectVendrCd"/>--%>
<%--                </td>--%>
<%--            </tr>--%>
            </tbody>
        </table>

        <div class="wj-dialog-body sc2">
            <div class="mt10 oh sb-select dkbr">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                        class="w100px fl"
                        id="currUnityListScaleBox"
                        ng-model="listScale"
                        items-source="_getComboData('currUnityHqDtlListScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        initialized="_initComboBox(s)"
                        control="listScaleCombo"
                        is-editable="true"
                        text-changed="_checkValidation(s)">
                </wj-combo-box>

                <%-- 엑셀 다운로드 //TODO --%>
                <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
                </button>
            </div>

            <%--위즈모 테이블--%>
            <div class="w100 mt10" id="wjWrapType1">
                <div class="wj-gridWrap" style="height: 320px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            id="currUnityMainGrid"
                            loaded-rows="loadedRows(s,e)"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="currUnity.prodClassNm"/>"    binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.prodCd"/>"         binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.prodNm"/>"         binding="prodNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.hqBarcdCd"/>"      binding="hBarcdCd" width="80" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.storeBarcdCd"/>"   binding="mBarcdCd" width="80" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.safeStockQty"/>"   binding="hSafeStockQty" width="65" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.hqQty"/>"          binding="hCurrQty" width="65" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.storeQty"/>"       binding="mCurrQty" width="65" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.totStockQty"/>"    binding="totCurrQty" width="65" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>

                        <div class="gridMsg" id="mobileCurrUnity1Msg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
<%--                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                        <jsp:param name="pickerTarget" value="mobileCurrUnityCtrl"/>--%>
<%--                    </jsp:include>--%>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//위즈모 테이블--%>
            <%-- 페이지 리스트 --%>
            <div class="pageNum3 mt10">
                <%-- id --%>
                <ul id="mobileCurrUnityCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <%--엑셀 리스트--%>
            <div class="w100 mt10" id="wjWrapType1" style="display:none;" ng-controller="mobileCurrUnityMainExcelCtrl">
                <div class="wj-gridWrap" style="height: 450px;">
                    <wj-flex-grid
                            id="currUnityMainExcelGrid"
                            loaded-rows="loadedRows(s,e)"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="mainExcelFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="currUnity.prodClassNm"/>"    binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.prodCd"/>"         binding="prodCd" width="150" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.prodNm"/>"         binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.hqBarcdCd"/>"      binding="hBarcdCd" width="150" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.storeBarcdCd"/>"   binding="mBarcdCd" width="150" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.safeStockQty"/>"   binding="hSafeStockQty" width="80" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.hqQty"/>"          binding="hCurrQty" width="80" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.storeQty"/>"       binding="mCurrQty" width="80" align="center" is-read-only="true"aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="currUnity.totStockQty"/>"    binding="totCurrQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
<%--                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                        <jsp:param name="pickerTarget" value="currUnityCtrl"/>--%>
<%--                    </jsp:include>--%>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//엑셀 리스트--%>
        </div>

    </div>

    <div ng-controller="mobileCurrUnityHqDtlCtrl" id="currUnityHqDtl" class="mt10">

<%--        <div class="mt20 oh sb-select dkbr">--%>
<%--            &lt;%&ndash; 페이지 스케일  &ndash;%&gt;--%>
<%--            <wj-combo-box--%>
<%--                    class="w100px fl"--%>
<%--                    id="currUnityHqDtlListScaleBox"--%>
<%--                    ng-model="listScale"--%>
<%--                    items-source="_getComboData('currUnityHqDtlListScaleBox')"--%>
<%--                    display-member-path="name"--%>
<%--                    selected-value-path="value"--%>
<%--                    initialized="_initComboBox(s)"--%>
<%--                    control="listScaleCombo"--%>
<%--                    is-editable="true"--%>
<%--                    text-changed="_checkValidation(s)">--%>
<%--            </wj-combo-box>--%>

<%--            &lt;%&ndash; 엑셀 다운로드 //TODO &ndash;%&gt;--%>
<%--            <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />--%>
<%--            </button>--%>
<%--        </div>--%>

        <div class="gridBar" id="mobileCurrUnityHqDtl" onclick="girdFldUnfld('mobileCurrUnityHqDtl')">
            <a href="#" class="open"><s:message code="currUnity.hqDtl"/></a>
            <%-- 결제수단 엑셀다운로드 --%>
            <button ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="w100">
            <div class="wj-gridWrap" style="height: 250px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="currUnityHqDtlGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"

                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="currUnity.iostockDate"/>"       binding="ioProcDate"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.iostockFg"/>"         binding="ioOccrFgNm"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.qty"/>"               binding="ioOccrQty"         width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.totQty"/>"            binding="totIoOccrQty"      width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <div class="gridMsg" id="mobileCurrUnity2Msg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
<%--                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                    <jsp:param name="pickerTarget" value="currUnityHqDtlCtrl"/>--%>
<%--                </jsp:include>--%>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
        <%--//위즈모 테이블--%>
        <%-- 페이지 리스트 --%>
<%--        <div class="pageNum3 mt20">--%>
<%--            &lt;%&ndash; id &ndash;%&gt;--%>
<%--            <ul id="mobileCurrUnityHqDtlCtrlPager" data-size="10">--%>
<%--            </ul>--%>
<%--        </div>--%>
        <%--//페이지 리스트--%>

        <%--엑셀 리스트--%>
        <div class="w100 mt10" style="display:none;" ng-controller="mobileCurrUnityHqDtlExcelCtrl">
            <div class="wj-gridWrap" style="height: 450px;">
                <wj-flex-grid
                        id="currUnityHqDtlExcelGrid"
                        autoGenerateColumns="false"
                        control="HqDtlExcelFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"

                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="currUnity.iostockDate"/>"       binding="ioProcDate"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.iostockFg"/>"         binding="ioOccrFgNm"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.qty"/>"               binding="ioOccrQty"         width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.totQty"/>"            binding="totIoOccrQty"     width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
<%--                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                    <jsp:param name="pickerTarget" value="currUnityHqDtlCtrl"/>--%>
<%--                </jsp:include>--%>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
        <%--//엑셀리스트--%>

    </div>

    <div ng-controller="mobileCurrUnityStoreDtlCtrl" id="currUnityStoreDtl" class="mt10" style="display: none">

<%--        <div class="mt20 oh sb-select dkbr">--%>
<%--            &lt;%&ndash; 페이지 스케일  &ndash;%&gt;--%>
<%--            <wj-combo-box--%>
<%--                    class="w100px fl"--%>
<%--                    id="currUnityStoreDtllistScaleBox"--%>
<%--                    ng-model="listScale"--%>
<%--                    control="listScaleCombo"--%>
<%--                    items-source="_getComboData('currUnityStoreDtlListScaleBox')"--%>
<%--                    display-member-path="name"--%>
<%--                    selected-value-path="value"--%>
<%--                    is-editable="false"--%>
<%--                    initialized="_initComboBox(s)">--%>
<%--            </wj-combo-box>--%>

<%--            &lt;%&ndash; 엑셀 다운로드 //TODO &ndash;%&gt;--%>
<%--            <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />--%>
<%--            </button>--%>
<%--        </div>--%>
        <div class="gridBar" id="mobileCurrUnityStoreDtl" onclick="girdFldUnfld('mobileCurrUnityStoreDtl')">
            <a href="#" class="open"><s:message code="currUnity.storeDtl"/></a>
            <%-- 결제수단 엑셀다운로드 --%>
            <button ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="w100">
            <div class="wj-gridWrap" style="height: 250px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="currUnityStoreDtlGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="currUnity.storeCd"/>"           binding="storeCd"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.storeNm"/>"           binding="storeNm"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.safeStockQty"/>"      binding="safeStockQty"  width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.outPrice"/>"          binding="splyUprc"      width="*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.qty"/>"               binding="currQty"       width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <div class="gridMsg" id="mobileCurrUnity3Msg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
<%--                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                    <jsp:param name="pickerTarget" value="currUnityStoreDtlCtrl"/>--%>
<%--                </jsp:include>--%>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
        <%--//위즈모 테이블--%>
        <%-- 페이지 리스트 --%>
<%--        <div class="pageNum3 mt20">--%>
<%--            &lt;%&ndash; id &ndash;%&gt;--%>
<%--            <ul id="mobileCurrUnityStoreDtlCtrlPager" data-size="10">--%>
<%--            </ul>--%>
<%--        </div>--%>
        <%--//페이지 리스트--%>

        <%--엑셀 리스트--%>
        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 450px; display:none;" ng-controller="mobileCurrUnityStoreDtlExcelCtrl">
                <wj-flex-grid
                        id="currUnityStoreDtlExcelGrid"
                        autoGenerateColumns="false"
                        control="storeDtlExcelFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="currUnity.storeCd"/>"           binding="storeCd"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.storeNm"/>"           binding="storeNm"       width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.safeStockQty"/>"      binding="safeStockQty"  width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.outPrice"/>"          binding="splyUprc"      width="*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="currUnity.qty"/>"               binding="currQty"       width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
<%--                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                    <jsp:param name="pickerTarget" value="currUnityStoreDtlCtrl"/>--%>
<%--                </jsp:include>--%>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
        <%--//엑셀 리스트--%>

    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/mobile/stock/status/currUnity/mobileCurrUnity.js?ver=20240805.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/mobile/sale/com/popup/selectProdClassCd.jsp">
</c:import>
