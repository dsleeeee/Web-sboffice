<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" id ="hqSalePriceResveExcelUploadView" style="display: none">

    <div ng-controller="hqSalePriceResveExcelUploadSampleCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="hqSalePriceResveExcelUpload.excelUpload" /></a>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <c:if test="${subPriceFg == '1'}">
                <tr>
                    <th><input type="checkbox" ng-model="saleUprcApply"/> <s:message code="salePriceResve.batchChange"/></th>
                    <td><s:message code="salePriceResve.saleUprcApply"/></td>
                </tr>
            </c:if>
            </tbody>
        </table>

        <div class="mt10 oh">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '판매가'가 업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '검증성공'인 판매가만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15 red">※ 업로드시 '상품코드, 변경판매가' 기준으로 처리됩니다.</p>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
            <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
        </div>

        <div class="wj-gridWrap" style="display: none">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqSaleUprc"/>" binding="hqSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.saleUprc"/>" binding="saleUprc" width="100" align="right"></wj-flex-grid-column>

                <c:if test="${subPriceFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqStinSaleUprc"/>" binding="hqStinSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.stinSaleUprc"/>" binding="stinSaleUprc" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqDlvrSaleUprc"/>" binding="hqDlvrSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqPackSaleUprc"/>" binding="hqPackSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.packSaleUprc"/>" binding="packSaleUprc" width="100" align="right"></wj-flex-grid-column>
                </c:if>

            </wj-flex-grid>
        </div>
    </div>

    <%-- 그리드 --%>
    <div ng-controller="hqSalePriceResveExcelUploadCtrl">

        <div class="mt10 oh sb-select dkbr">
            <div id="excelStoreSaveStore" class="fr oh bk mr10" style="width: 200px; height:25px; display: none;">
                <c:if test="${momsEnvstVal == '0'}">
                    <jsp:include page="/WEB-INF/view/application/layer/searchPriceStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="excelChoiceSaveStore"/>
                    </jsp:include>
                </c:if>
                <c:if test="${momsEnvstVal == '1'}">
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="excelChoiceSaveStore"/>
                    </jsp:include>
                </c:if>
            </div>
            <div class="sb-select w200px fr mr10">
                <wj-combo-box
                        id="excelStoreSaveFg"
                        ng-model="excelStoreSaveFg"
                        items-source="_getComboData('excelStoreSaveFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="excelStoreSaveFgCombo"
                        selected-index-changed="selectedIndexChanged(s)">
                </wj-combo-box>
            </div>
        </div>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" ng-click="delete()">
                <s:message code="cmm.del" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
                <s:message code="cmm.save" />
            </button>

            <%-- 예약일시 --%>
            <span class="fr mr10">
                <div class="sb-select">
                    <span class="tl"><s:message code="hqSalePriceResveExcelUpload.resveDate"/></span>
                    <span class="txtIn w110px">
                        <wj-input-date
                                value="excelUploadStartDate"
                                ng-model="startDate"
                                control="excelUploadStartDateCombo"
                                format="yyyy/MM/dd"
                                min="${tomorrowDate}"
                                max="9999-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w110px">
                        <wj-input-date
                                value="excelUploadEndDate"
                                ng-model="endDate"
                                control="excelUploadEndDateCombo"
                                format="yyyy/MM/dd"
                                min="${tomorrowDate}"
                                max="9999-12-31"
                                initialized="_initDateBox(s)">
                        </wj-input-date>
                    </span>
                </div>
            </span>
        </div>
        <%-- 저장 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:310px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        frozen-columns="2">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqSaleUprc"/>" binding="hqSaleUprc" width="100" is-read-only="true" align="right" max-length="10"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.saleUprc"/>" binding="saleUprc" width="100" align="right" max-length="10"></wj-flex-grid-column>

                    <c:if test="${subPriceFg == '1'}">
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqStinSaleUprc"/>" binding="hqStinSaleUprc" width="100" is-read-only="true" align="right" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.stinSaleUprc"/>" binding="stinSaleUprc" width="100" align="right" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqDlvrSaleUprc"/>" binding="hqDlvrSaleUprc" width="100" is-read-only="true" align="right" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="100" align="right" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.hqPackSaleUprc"/>" binding="hqPackSaleUprc" width="100" is-read-only="true" align="right" max-length="10"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.packSaleUprc"/>" binding="packSaleUprc" width="100" align="right" max-length="10"></wj-flex-grid-column>
                    </c:if>

                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%-- 삭제시 필요 --%>
                    <wj-flex-grid-column header="<s:message code="hqSalePriceResveExcelUpload.seq"/>" binding="seq" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script>
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/hqSalePriceResveExcelUpload.js?ver=20230830.01" charset="utf-8"></script>

<%-- 가격예약(본사판매가) 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/hqSalePriceResveExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>