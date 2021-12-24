<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="storeProdSaleReportView" class="subCon" style="display: none;">
    <div ng-controller="storeProdSaleReportCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeProdSaleReportTab.storeProdSaleReport"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('storeProdSaleReportCtrl',1)">
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

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15 red">전체매장 자료생성 요청시 생성까지 최대 90분정도 소요됩니다.</p>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" id="btnDel" ng-click="del()">
                <s:message code="cmm.del" />
            </button>
            <%-- 자료생성 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="dataCreate()">
                <s:message code="storeProdSaleReport.dataCreate" />
            </button>
            <%-- 자료생성 날짜 --%>
            <div class="sb-select dkbr ml5 fr">
                <span class="txtIn"><input id="dataCreateMonth" name="dataCreateMonth" class="w100px" /></span>
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
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.saleMonth"/>" binding="saleMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.procGubun"/>" binding="procGubun" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.procDt"/>" binding="procDt" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.userNm"/>" binding="userNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.procFg"/>" binding="procFg" data-map="procFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.procMsg"/>" binding="procMsg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.download"/>" binding="download" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--삭제시 필요--%>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.fromSaleDate"/>" binding="fromSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.toSaleDate"/>" binding="toSaleDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdSaleReport.fileName"/>" binding="fileName" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<form id="saleReport_info" name="saleReport_info" method="post" action="/sale/status/storeProdSaleReport/storeProdSaleReport/getStoreProdSaleReportDownload.sb" target="saleReportFrm">
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

<script type="text/javascript" src="/resource/solbipos/js/sale/status/storeProdSaleReport/storeProdSaleReport.js?ver=20211223.01" charset="utf-8"></script>