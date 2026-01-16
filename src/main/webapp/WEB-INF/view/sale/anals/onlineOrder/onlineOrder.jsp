<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="onlineOrderCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('onlineOrderCtrl',1)">
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
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="saleDate" name="saleDate" class="w110px" /></span>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                            <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="onlineOrderStore"/>
                        </jsp:include>
                            <%--// 매장선택 모듈 사용시 include --%>
                    </td>
            </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="onlineOrderStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <tr>
                <%-- 영수증번호 --%>
                <th><s:message code="onlineOrder.srchBillNo" /></th>
                <td>
                    <input type="text" id="srchBillNo" ng-model="billNo" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
                </td>
                <%-- 오더주문번호 --%>
                <th><s:message code="onlineOrder.vorderNo" /></th>
                <td>
                    <input type="text" id="srchVorderNo" ng-model="vorderNo" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue ml5 fr" id="btnExcel" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="onlineOrder.storeCd"/>"       binding="storeCd"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.storeNm"/>"       binding="storeNm"       width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.saleDate"/>"      binding="saleDate"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.posNo"/>"         binding="posNo"         width="70"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.billNo"/>"        binding="billNo"        width="70"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.billDt"/>"        binding="billDt"        width="130" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.saleFg"/>"        binding="saleFg"        width="60"  is-read-only="true" align="center" data-map="saleFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.totSaleAmt"/>"    binding="totSaleAmt"    width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.totDcAmt"/>"      binding="totDcAmt"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.realSaleAmt"/>"   binding="realSaleAmt"   width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.slVorder"/>"      binding="slVorder"      width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="onlineOrder.rvVorder"/>"      binding="rvVorder"      width="150" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="onlineOrderCtrl"/>
            </jsp:include>
        </div>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/onlineOrder/onlineOrder.js?ver=20260116.01" charset="utf-8"></script>