<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="nonSaleChargeCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('nonSaleChargeCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w85"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회월 --%>
            <th><s:message code="cmm.search.month"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="saleMonth" name="saleMonth" class="w120px" /></span>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장 --%>
                <th><s:message code="nonSaleCharge.storeNm"/></th>
                <td>
                    <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="nonSaleChargeStore"/>
                    </jsp:include>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="nonSaleChargeStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScaleBox"
                control="listScaleCombo"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>

        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnExcel" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
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
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.billNo"/>" binding="billNo" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.nonsaleTypeNo"/>" binding="nonsaleTypeNo" width="250" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.membrCardNo"/>" binding="cardNo" width="150" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.payCd"/>" binding="payCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.payNm"/>" binding="payNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.paySeq"/>" binding="paySeq" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.vanTermnlNo"/>" binding="vanTermnlNo" width="120" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprNo"/>" binding="apprNo" width="90" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprDt"/>" binding="apprDt" width="120" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprAmt"/>" binding="payAmt" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="nonSaleChargeCtrl"/>
            </jsp:include>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="nonSaleChargeCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%-- 엑셀 다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="nonSaleChargeExcelCtrl">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="excelFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.billNo"/>" binding="billNo" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.nonsaleTypeNo"/>" binding="nonsaleTypeNo" width="250" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.membrCardNo"/>" binding="cardNo" width="150" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.payCd"/>" binding="payCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.payNm"/>" binding="payNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.paySeq"/>" binding="paySeq" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.vanTermnlNo"/>" binding="vanTermnlNo" width="120" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprNo"/>" binding="apprNo" width="90" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprDt"/>" binding="apprDt" width="120" is-read-only="true" align="left" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCharge.apprAmt"/>" binding="payAmt" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="nonSaleChargeCtrl"/>
                </jsp:include>
            </div>
        </div>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/nonSaleCharge/nonSaleCharge.js?ver=20210206.01" charset="utf-8"></script>