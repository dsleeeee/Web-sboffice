<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="prodStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prodStatus.prodStatus" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodStatusCtrl', 1)">
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
            <%-- 영업일자 --%>
            <th><s:message code="prodStatus.saleDate" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
            <%-- 요리메모 사용여부 --%>
            <th><s:message code="prodStatus.cookMemoUseYn" /></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="cookMemoUseYn"
                                ng-model="cookMemoUseYn"
                                items-source="_getComboData('cookMemoUseYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="prodStatus.optionFg" /></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="optionFg"
                                ng-model="optionFg"
                                items-source="_getComboData('optionFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index="1">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <%-- grid --%>
    <div id="grid" class="w100">
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:470px; overflow-y: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="prodStatus.saleDate"/>" binding="saleDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.billDtlNo"/>" binding="billDtlNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.saleUprc"/>" binding="saleUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.saleQty"/>" binding="saleQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.nmcodeNm"/>" binding="nmcodeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodStatus.cookMemo"/>" binding="cookMemo" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>

    </div>
</div>

<script type="text/javascript">
    var cookMemoUseYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dlvr/prodStatus.js?ver=20220411.05" charset="utf-8"></script>
