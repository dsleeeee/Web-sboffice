<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMCoupnProdMappingHistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:580px;" fade-in="false" fade-out="false">
    <div ng-controller="mCoupnProdMappingHistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mCoupnProdMappingHist.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 처리일자 --%>
                    <th><s:message code="mCoupnProdMappingHist.procDate" /></th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"><input id="srchMCoupnProdMappingHistStartDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchMCoupnProdMappingHistEndDate" class="w110px"></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="cmm.prodCd" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="cmm.prodNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 조회(가로) --%>
                    <button class="btn_skyblue ml5 fr" id="nxBtnSearch3" ng-click="_broadcast('mCoupnProdMappingHistCtrl', 'A')"><s:message code="cmm.search" />(가로)</button>
                    <%-- 조회(세로) --%>
                    <button class="btn_skyblue ml5 fr" ng-click="_broadcast('mCoupnProdMappingHistCtrl', 'B')"><s:message code="cmm.search" />(세로)</button>
                    <%-- 현재화면 엑셀다운로드 --%>
                    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            id="wjMCoupnProdMappingHistGridList"
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingHist.procDt"/>" binding="procDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingHist.userId"/>" binding="userId" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/pay/mCoupnProdMapping/mCoupnProdMappingHist.js?ver=20250827.01" charset="utf-8"></script>