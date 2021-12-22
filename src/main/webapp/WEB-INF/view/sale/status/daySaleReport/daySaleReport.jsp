<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="daySaleReportCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('daySaleReportCtrl',1)">
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
            <%-- 조회월 --%>
            <th>
                <s:message code="cmm.search.month" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startMonth" name="startMonth" class="w100px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonth" name="endMonth" class="w100px" /></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <p class="tl s14 mt5 lh15 red">전체매장 자료생성 요청시 생성까지 최대 90분정도 소요됩니다.</p>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" id="btnDel" ng-click="del()">
            <s:message code="cmm.del" />
        </button>
        <%-- 자료생성 --%>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="dataCreate()">
            <s:message code="daySaleReport.dataCreate" />
        </button>
        <%-- 자료생성 날짜 --%>
        <div class="sb-select dkbr ml5 fr">
            <span class="txtIn"><input id="dataCreateMonth" name="dataCreateMonth" class="w100px" /></span>
        </div>
        <%-- 매장 --%>
        <div class="ml5 fr">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="daySaleReportStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </div>
        <div class="sb-select dkbr ml5 fr">
            <p class="tl s14 mt5 lh15">매장 :</p>
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.saleMonth"/>" binding="saleMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.procGubun"/>" binding="procGubun" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.procDt"/>" binding="procDt" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.userNm"/>" binding="userNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.procFg"/>" binding="procFg" data-map="procFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.download"/>" binding="download" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                <%--삭제시 필요--%>
                <wj-flex-grid-column header="<s:message code="daySaleReport.fromSaleDate"/>" binding="fromSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.toSaleDate"/>" binding="toSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="daySaleReport.fileName"/>" binding="fileName" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<form id="saleReport_info" name="saleReport_info" method="post" action="/sale/status/daySaleReport/daySaleReport/getDaySaleReportDownload.sb" target="saleReportFrm">
    <iframe name="saleReportFrm" style="display:none;"></iframe>

    <input type="hidden" name="fileName" value="" /> <%--파일명--%>
</form>

<script type="text/javascript">
    function saleReport_download(fileName)
    {
        document.saleReport_info.fileName.value = fileName;
        document.saleReport_info.submit();
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/daySaleReport/daySaleReport.js?ver=20211208.01" charset="utf-8"></script>