<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="cardCreditCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('cardCreditCtrl',1)">
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
            <tr>
                <%-- 승인일자 --%>
                <th>
                    <s:message code="cardCredit.apprDate" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <div class="w100">
                <div class="w50 fl">
                    <p class="tl s14 mt5 lh15">※ 입력 항목</p>
                    <p class="tl s14 mt5 lh15">1. 입금일자 : '2090-09-15', '20900915' 형식으로 입력 가능</p>
                    <p class="tl s14 mt5 lh15">2. 입금금액 : 숫자만 입력 가능</p>
                    <p class="tl s14 mt5 lh15">3. 수수료 &nbsp : 숫자만 입력 가능</p>
                    <p class="tl s14 mt5 lh15">4. 입금은행 : '국민은행', '하나은행' 만 입력 가능</p>
                    <p class="tl s14 mt5 lh15 red">※ 양식에 맞지 않는 데이터는 저장되지 않습니다.</p>
                </div>
                <div class="w50 fr">
                    <p class="tl s14 mt5 lh15">※ 엑셀업로드</p>
                    <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
                    <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
                    <p class="tl s14 mt5 lh15">3. 조회 후 '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
                    <p class="tl s14 mt5 lh15">- 조회한 승인일자 내에 자료만 업로드 가능합니다.</p>
                </div>
            </div>
        </div>
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
                <s:message code='cmm.save' />
            </button>
            <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelSampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
            <div class="sb-select ml5 fr">
                <p class="tl s14 mt5 lh15 blue"><label id="lblSrchDate"></label></p>
                <div style="display: none">
                    <label id="lblSrchStartDate"></label>
                    <label id="lblSrchEndDate"></label>
                </div>
            </div>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <c:if test="${orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="cardCredit.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cardCredit.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="cardCredit.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.bill"/>" binding="bill" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprDate"/>" binding="apprDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprNo"/>" binding="apprNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprAmt"/>" binding="apprAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditDate"/>" binding="creditDate" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditAmt"/>" binding="creditAmt" width="70" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditFee"/>" binding="creditFee" width="70" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditBank"/>" binding="creditBank" data-map="creditBankDataMap" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprGubun"/>" binding="apprGubun" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.cardNo"/>" binding="cardNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.acquireNm"/>" binding="acquireNm" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.membrJoinNo"/>" binding="membrJoinNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.instCntNm"/>" binding="instCntNm" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    <%-- 저장시 필요 --%>
                    <wj-flex-grid-column header="<s:message code="cardCredit.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.hqBrandCd"/>" binding="hqBrandCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.posNo"/>" binding="posNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.billNo"/>" binding="billNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.lineNo"/>" binding="lineNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.lineSeqNo"/>" binding="lineSeqNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 엑셀 샘플 양식 --%>
    <div ng-controller="cardCreditExcelSampleCtrl">
        <%-- 그리드 --%>
        <div class="w100 mt10 mb20" style="display: none;">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprDate"/>" binding="apprDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.apprNo"/>" binding="apprNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditDate"/>" binding="creditDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditAmt"/>" binding="creditAmt" width="70" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditFee"/>" binding="creditFee" width="70" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cardCredit.creditBank"/>" binding="creditBank" data-map="creditBankDataMap" width="70" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/card/cardCredit/cardCredit.js?ver=20220917.01" charset="utf-8"></script>

<%-- 신용카드입금관리 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/sale/card/cardCredit/cardCreditExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>