<%--
  Created by IntelliJ IDEA.
  User: doasys_daniel
  Date: 20-7-9
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="dayDlvrCtrl">
    <input type="hidden" id="resurceFg" class="sb-input w50" ng-model="resurceFg" value="${orgnFg}"/>
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('dayDlvrCtrl')">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w5"/>
            <col class="w30"/>
            <col class="w5"/>
            <col class="w25"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회기간 --%>
            <th>
                <s:message code="dlvrInfo.srchDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
                </div>
            </td>
            <td></td>
        </tr>
        </tbody>
    </table>


    <%-- 그리드 left --%>
    <div class="w50 fl mt40 mb20" style="width: 38%">
        <div class="oh sb-select dkbr">
            <%-- 엑셀G 버튼 --%>
            <button class="btn_skyblue ml5 fr" id="save" ng-click="leftExcelDownload()">
                <s:message code="dayDlvr.leftExcelDownload"/>
            </button>
        </div>
        <div class="wj-TblWrapBr mt10 ml10 pd20" style="height: 600px;">
            <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="dayDlvr.saleDate"/>" binding="saleDate"
                                             width="110"
                                             align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.cntBillNo"/>" binding="dlvrBillCnt"
                                             width="80"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="dlvrAmt"
                                             width="150" is-read-only="true" align="right"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.cntBillNo"/>" binding="nonDlvrBillCnt"
                                             width="80"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="nonDlvrAmt"
                                             width="150" is-read-only="true" align="right"
                                             aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="hqOfficeCd"
                                             width="150" is-read-only="true" align="right" aggregate="Sum"
                                             visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="hqBrandCd"
                                             width="150" is-read-only="true" align="right" aggregate="Sum"
                                             visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="posNo"
                                             width="150" is-read-only="true" align="right" aggregate="Sum"
                                             visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="billNo"
                                             width="150" is-read-only="true" align="right" aggregate="Sum"
                                             visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--    &lt;%&ndash; 페이지 리스트 &ndash;%&gt;--%>
        <%--    <div class="pageNum">--%>
        <%--      &lt;%&ndash; id &ndash;%&gt;--%>
        <%--      <ul id="dayDlvrCtrlPager" data-size="10">--%>
        <%--      </ul>--%>
        <%--    </div>--%>
        <%--    &lt;%&ndash;//페이지 리스트&ndash;%&gt;--%>
    </div>
</div>

<div ng-controller="dayDlvrDtlCtrl">
    <%--right--%>
    <%-- 그리드 right --%>
    <div class="w50 fl mb20" style="width: 60%">
        <div class="oh sb-select dkbr">
            <button class="btn_skyblue ml5 fr" id="save" ng-click="rightExcelDownload()">
                <s:message code="dayDlvr.rightExcelDownload"/>
            </button>
        </div>
        <div class="wj-TblWrapBr mt10 ml10 pd20" style="height: 600px;">
            <div class="wj-gridWrap" style="height:480px; overflow-y: hidden;">
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
                        <%--            <wj-flex-grid-column header="<s:message code="dayDlvr.prodClassNm"/>" binding="prodClassNm" width="150"--%>
                        <%--                                 is-read-only="true" align="center" visible="false"></wj-flex-grid-column>--%>
                        <%--            <wj-flex-grid-column header="<s:message code="dayDlvr.prodClassNm"/>" binding="lv1Cd" width="100"--%>
                        <%--                                 is-read-only="true" align="center" aggregate="Sum" visible="false"></wj-flex-grid-column>--%>
                        <%--            <wj-flex-grid-column header="<s:message code="dayDlvr.prodClassNm"/>" binding="lv1Nm" width="150"--%>
                        <%--                                 is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.prodClassNm"/>" binding="prodClassNm"
                                             width="150"
                                             is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <%--            <wj-flex-grid-column header="<s:message code="dayDlvr.prodLV2"/>" binding="lv2Cd" width="100"--%>
                        <%--                                 is-read-only="true" align="center" aggregate="Sum"  visible="false"></wj-flex-grid-column>--%>
                        <%--            <wj-flex-grid-column header="<s:message code="dayDlvr.prodLV2"/>" binding="lv2Nm" width="150"--%>
                        <%--                                 is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.prodDd"/>" binding="prodCd" width="100"
                                             is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.prodNm"/>" binding="prodNm" width="150"
                                             is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumSaleQty"/>" binding="dlvrSaleQty"
                                             width="150"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="dlvrAmt"
                                             width="150" is-read-only="true" align="right"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumSaleQty"/>" binding="nonDlvrSaleQty"
                                             width="100"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayDlvr.sumRealSaleAmt"/>" binding="nonDlvrAmt"
                                             width="150" is-read-only="true" align="right"
                                             aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--    &lt;%&ndash; 페이지 리스트 &ndash;%&gt;--%>
        <%--    <div class="pageNum mt10">--%>
        <%--      &lt;%&ndash; id &ndash;%&gt;--%>
        <%--      <ul id="dayDlvrDtlCtrlPager" data-size="10">--%>
        <%--      </ul>--%>
        <%--    </div>--%>
        <%--  &lt;%&ndash;//페이지 리스트&ndash;%&gt;--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dayDlvr/dayDlvr.js?ver=2019052801.11"
        charset="utf-8"></script>
