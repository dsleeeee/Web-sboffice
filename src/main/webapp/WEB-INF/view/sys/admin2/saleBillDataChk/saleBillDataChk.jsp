<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="saleBillDataChkCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 검증 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button id="nxBtnSearch" class="btn_blue fr" ng-click="saleBillDataChk()">
                <s:message code="saleBillDataChk.chk" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="saleBillDataChk.storeCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" maxlength="12" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 영업일자 --%>
            <th><s:message code="saleBillDataChk.saleDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchSaleDate" class="w120px"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 포스번호 --%>
            <th><s:message code="saleBillDataChk.posNo"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchPosNo" ng-model="posNo" maxlength="2" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 영수번호 --%>
            <th><s:message code="saleBillDataChk.billNo"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBillNo" ng-model="billNo" maxlength="4" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <p class="tl s14 mt5 lh15">※ <s:message code="saleBillDataChk.info"/></p>
    </div>

    <%-- 검증결과 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.chkDt"/>" binding="chkDt" width="130" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.storeCd"/>" binding="storeCd" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.saleDate"/>" binding="saleDate" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.posNo"/>" binding="posNo" width="60" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.billNo"/>" binding="billNo" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.result"/>" binding="resultNm" width="60" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.resultCode"/>" binding="resultCode" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleBillDataChk.resultMsg"/>" binding="resultMsg" width="*" align="left"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin2/saleBillDataChk/saleBillDataChk.js?ver=20261103.02" charset="utf-8"></script>
