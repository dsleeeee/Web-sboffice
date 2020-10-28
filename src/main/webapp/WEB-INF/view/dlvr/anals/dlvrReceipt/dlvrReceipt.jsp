<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="session" value="${sessionScope.sessionInfo}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon">
    <div ng-controller="dlvrReceiptCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnDel" ng-click="dlvrReceiptSearch()">
                    <s:message code="cmm.search"/>
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
                <%-- 회원번호 --%>
                <th><s:message code="dlvrReceipt.sale.date"/></th>
                <td colspan="3">
                <span class="txtIn">
                  <div class="sb-select">
                    <wj-input-date
                            value="periodStartDate"
                            ng-model="periodStartDate"
                            control="periodStartDateCombo"
                            format="yyyy/MM/dd"
                            min="2000-01-01"
                            max="2099-12-31"
                            initialized="_initDateBox(s)">
                    </wj-input-date>
                  </div>
                </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                    <div class="sb-select">
                        <wj-input-date
                                value="periodEndDate"
                                ng-model="periodEndDate"
                                control="periodEndDateCombo"
                                format="yyyy/MM/dd"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                    </wj-input-date>
                </div>
                </span>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 left --%>
        <div class="w50 fl mt40 mb20" style="width: 40%">
            <div class="mb10 oh sb-select dkbr ">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                        class="w100px fl"
                        id="listScaleBox"
                        ng-model="listScale"
                        items-source="_getComboData('listScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="initComboBox(s)">
                </wj-combo-box>
            </div>
            <div class="wj-gridWrap pd20" style="height:480px; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"
                                             align="center"
                                             is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.area"/>" binding="dlvrAddr"
                                             width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.bill.cnt"/>" binding="billCount"
                                             width="230" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.real.sale"/>"
                                             binding="realSaleAmt" data-type="Number" format="n0" aggregate="Sum"
                                             width="230" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column binding="hqOfficeCd" width="120" dalign="center"
                                             is-read-only="false" align="right"
                                             visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column binding="hqBrandCd" width="120"
                                             is-read-only="true" align="right"
                                             visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum">
                <%-- id --%>
                <ul id="dlvrReceiptCtrlPager" data-size="1">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>

    </div>

    <div ng-controller="dlvrReceiptDetailCtrl">
        <%-- 그리드 right --%>
        <div class="w50 mt40 fl mb20" style="width: 60%">
            <div class="mb10 ml10 oh sb-select dkbr ">
                <%-- 페이지 스케일  --%>
                <wj-combo-box
                        class="w100px fl"
                        id="listScaleBox"
                        ng-model="listScale"
                        items-source="_getComboData('listScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="initComboBox(s)">
                </wj-combo-box>
            </div>
            <div class="wj-gridWrap ml10 pd20" style="height:480px; overflow-y: hidden;">
                <div class="row">
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
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"
                                             align="center"
                                             is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.sale.date"/>" binding="saleDate"
                                             width="220"
                                             is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.pos.no"/>" binding="posNo"
                                             width="230"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.bill.no"/>" binding="billNo"
                                             width="115"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.real.sale"/>"
                                             binding="realSaleAmt" data-type="Number" format="n0"
                                             width="120" aggregate="Sum"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.member.nm"/>" binding="membrNm"
                                             width="120"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrReceipt.dlvr.nm"/>" binding="empNm"
                                             width="120"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt10">
                <%-- id --%>
                <ul id="dlvrReceiptDetailCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
</div>
<script>
    <%--var useYn = ${ccu.getCommCodeExcpAll("067")};--%>
    <%--var dlvrFirstList = ${dlvrFirstList};--%>
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dlvrReceipt/dlvrReceipt.js?ver=2020062901.11"
        charset="utf-8"></script>

<%-- 영수증상세 --%>
<c:import url="/WEB-INF/view/dlvr/anals/dlvrInfo/billPopup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
