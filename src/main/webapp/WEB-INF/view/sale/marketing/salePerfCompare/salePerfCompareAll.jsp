<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="salePerfCompareAllView" class="subCon" style="padding: 10px 20px 40px;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="salePerfCompare.allStore"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearchAll" ng-click="_broadcast('salePerfCompareAllCtrl')">
            <s:message code="cmm.search"/>
        </button>
    </div>
    <div ng-controller="salePerfCompareAllCtrl">
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 구분 --%>
                <th><s:message code="salePerfCompare.dlvrOrderFg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchDateFgCombo"
                                ng-model="srchDateFgCombo"
                                control="srchDateFg"
                                items-source="_getComboData('srchDateFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                selected-index-changed="selectedIndexChanged(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDateAll" ng-model="startDate" class="w110px"></span>
                        <span class="rg" id="srchRg">~</span>
                        <span class="txtIn" id="srchEnd"><input id="srchEndDateAll" ng-model="endDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 대비일자 --%>
                <th><s:message code="salePerfCompare.compDate"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="compStartDateAll" ng-model="compStartDate" class="w110px"></span>
                        <span class="rg" id="compRg">~</span>
                        <span class="txtIn" id="compEnd"><input id="compEndDateAll" ng-model="compEndDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <!-- contents start -->
        <div class="wj-gridWrap2 mt10">
            <%-- wj grid start --%>
            <div class="oh sb-select dkbr mb10 mr10" >
                <%-- 엑셀 다운로드 //TODO --%>
                <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="wj-gridWrap">
                <wj-flex-grid
                        id="salePerfCompareAllMainGrid"
                        loaded-rows="loadedRows(s,e)"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrOrderFg"/>"  binding="dlvrOrderFg"        width="80"   align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"      binding="compSaleCntFran"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"      binding="compSaleAmtFran"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"        binding="compSaleCntDm"      width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"        binding="compSaleAmtDm"      width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"       binding="compTotSaleCnt"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"       binding="compTotSaleAmt"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"      binding="srchSaleCntFran"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"      binding="srchSaleAmtFran"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"        binding="srchSaleCntDm"      width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"        binding="srchSaleAmtDm"      width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"       binding="srchTotSaleCnt"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"       binding="srchTotSaleAmt"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"      binding="grSaleCntFran"      width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"      binding="grSaleAmtFran"      width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"        binding="grSaleCntDm"        width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"        binding="grSaleAmtDm"        width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"       binding="grTotSaleCnt"       width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"       binding="grTotSaleAmt"       width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="salePerfCompareAllCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%-- //wj grid end --%>
        </div>
        <!-- //contents end -->
    </div>

    <!-- 하단 그리드 -->
    <!-- contents start -->
    <%-- wj grid start --%>
    <div class="wj-gridWrap2 mt10" ng-controller="salePerfCompareAllDtlCtrl">
        <%-- left --%>
        <div class="oh sb-select dkbr mb10 mr10" style="display: none;">
            <%-- 엑셀 다운로드 --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDtl()"><s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="w100 mt10" style="display: none;">
            <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        id="salePerfCompareAllDtlGrid"
                        loaded-rows="loadedRows2(s,e)"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dlvrInFg"/>"  binding="dlvrInFg"          width="80"  align="center"  is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"   binding="compSaleCntFran"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"   binding="compSaleAmtFran"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"     binding="compSaleCntDm"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"     binding="compSaleAmtDm"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"    binding="compTotSaleCnt"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"    binding="compTotSaleAmt"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"   binding="srchSaleCntFran"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"   binding="srchSaleAmtFran"   width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"     binding="srchSaleCntDm"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"     binding="srchSaleAmtDm"     width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"    binding="srchTotSaleCnt"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"    binding="srchTotSaleAmt"    width="80"  align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franCnt"/>"   binding="grSaleCntFran"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.franAmt"/>"   binding="grSaleAmtFran"     width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmCnt"/>"     binding="grSaleCntDm"       width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.dmAmt"/>"     binding="grSaleAmtDm"       width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totcnt"/>"    binding="grTotSaleCnt"      width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="salePerfCompare.totAmt"/>"    binding="grTotSaleAmt"      width="80"  align="right"   is-read-only="true" aggregate="Sum" format="p2"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="salePerfCompareAllDtlCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>
    <%-- //wj grid end --%>
    <!-- //contents end -->
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/marketing/salePerfCompare/salePerfCompareAll.js?ver=20250910.01" charset="utf-8"></script>