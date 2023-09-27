<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" id ="storeSalePriceResveExcelUploadView" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="storeSalePriceResveExcelUploadSampleCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeSalePriceResveExcelUpload.excelUpload" /></a>
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
            <p class="tl s14 mt5 lh15">- 매장 선택 후 진행해주세요. (매장은 최대 10개까지 선택가능합니다.)</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '판매가'가 업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '검증성공'인 판매가만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15 red">※ 업로드시 '매장코드, 상품코드, 변경판매가' 기준으로 처리됩니다.</p>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
            <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
            <%-- 매장 --%>
            <div class="ml5 fr">
                <%-- [1250 맘스터치] --%>
                <c:if test="${momsEnvstVal == '1'}">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include param 정의 :
                                  targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수--%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="storeSalePriceResveExcelUploadStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </c:if>
                <c:if test="${momsEnvstVal == '0'}">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include param 정의 :
                                  targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수--%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="storeSalePriceResveExcelUploadStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </c:if>
            </div>
            <div class="sb-select dkbr ml5 fr">
                <p class="tl s14 mt5 lh15"><s:message code="cmm.store"/></p>
            </div>
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
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeNm"/>" binding="storeNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.hqSaleUprc"/>" binding="hqSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeSaleUprc"/>" binding="storeSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.saleUprc"/>" binding="saleUprc" width="100" align="right"></wj-flex-grid-column>

                <c:if test="${subPriceFg == '1'}">
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeStinSaleUprc"/>" binding="storeStinSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.stinSaleUprc"/>" binding="stinSaleUprc" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeDlvrSaleUprc"/>" binding="storeDlvrSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="100" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storePackSaleUprc"/>" binding="storePackSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.packSaleUprc"/>" binding="packSaleUprc" width="100" align="right"></wj-flex-grid-column>
                </c:if>

                <%-- [1250 맘스터치] --%>
                <c:if test="${momsEnvstVal == '1'}">
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeChgNot"/>" binding="storeChgNot" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                </c:if>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 그리드 --%>
    <div ng-controller="storeSalePriceResveExcelUploadCtrl">
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
                    <span class="tl"><s:message code="storeSalePriceResveExcelUpload.resveDate"/></span>
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
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeNm"/>" binding="storeNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.hqSaleUprc"/>" binding="hqSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeSaleUprc"/>" binding="storeSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.saleUprc"/>" binding="saleUprc" width="100" align="right"></wj-flex-grid-column>

                    <c:if test="${subPriceFg == '1'}">
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeStinSaleUprc"/>" binding="storeStinSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.stinSaleUprc"/>" binding="stinSaleUprc" width="100" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storeDlvrSaleUprc"/>" binding="storeDlvrSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.dlvrSaleUprc"/>" binding="dlvrSaleUprc" width="100" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.storePackSaleUprc"/>" binding="storePackSaleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.packSaleUprc"/>" binding="packSaleUprc" width="100" align="right"></wj-flex-grid-column>
                    </c:if>

                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%-- 삭제시 필요 --%>
                    <wj-flex-grid-column header="<s:message code="storeSalePriceResveExcelUpload.seq"/>" binding="seq" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script>
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeSalePriceResveExcelUpload.js?ver=20230830.01" charset="utf-8"></script>

<%-- 가격예약(매장판매가) 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeSalePriceResveExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>