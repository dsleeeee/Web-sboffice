<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" id ="divDlvrExcelUpload">
    <div ng-controller="dlvrExcelUploadCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:none;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('dlvrExcelUploadCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">- '양식 샘플 미리보기'를 꼭 참고해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '배달지내역'이 업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '검증성공'인 배달지만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15">- 배달지는 5000개 이하로 업로드해주세요.</p>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="dlvrExcelUpload.excelUpload" />
            </button>
           <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="dlvrExcelUpload.sampleDownload" />
            </button>
        </div>

        <%-- 그리드 --%>
        <%-- 양식 샘플 미리보기--%>
        <s:message code="dlvrExcelUpload.sample" />
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:70px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                    <!-- define columns -->
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidCallDt"/>"     binding="cidCallDt"     width="120" align="center" format="d"></wj-flex-grid-column>--%>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidLineNo"/>"     binding="cidLineNo"     width="100" align="center"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidTelNo"/>"      binding="cidTelNo"      width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddr"/>"      binding="dlvrAddr"      width="200" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddrDtl"/>"   binding="dlvrAddrDtl"   width="85" align="left"></wj-flex-grid-column>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.orderNo"/>"       binding="orderNo"       width="75" align="center"></wj-flex-grid-column>--%>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrFg"/>"        binding="dlvrFg"        data-map="dlvrFgDataMap" width="80" align="center"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrMemo"/>"      binding="dlvrMemo"      width="100" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 그리드 --%>
    <div ng-controller="dlvrExcelUploadDlvrCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="prodExcelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" ng-click="delete()">
                <s:message code="cmm.del" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnDlvrExcelUploadSave" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
        </div>
        <%-- 배달지내역--%>
        <s:message code="dlvrExcelUpload.dlvrList" />
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
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.result"/>"        binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.seq"/>"           binding="seq"           width="100" align="center" visible="false"></wj-flex-grid-column>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidCallDt"/>"     binding="cidCallDt"     width="120" align="center" format="d"></wj-flex-grid-column>--%>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidLineNo"/>"     binding="cidLineNo"     width="100" align="center"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.cidTelNo"/>"      binding="cidTelNo"      width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddr"/>"      binding="dlvrAddr"      width="200" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrAddrDtl"/>"   binding="dlvrAddrDtl"   width="85" align="left"></wj-flex-grid-column>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.orderNo"/>"       binding="orderNo"       width="75" align="center"></wj-flex-grid-column>--%>
<%--                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrFg"/>"        binding="dlvrFg"        data-map="dlvrFgDataMap" width="80" align="center"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="<s:message code="dlvrExcelUpload.dlvrMemo"/>"      binding="dlvrMemo"      width="100" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script>
    // String 형식
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrExcelUpload/dlvrExcelUpload.js?ver=20220614.01" charset="utf-8"></script>

<%-- 배달지엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/dlvr/info/dlvrExcelUpload/dlvrExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
