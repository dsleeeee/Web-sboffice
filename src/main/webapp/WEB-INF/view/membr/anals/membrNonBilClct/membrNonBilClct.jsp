<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">

    <div ng-controller="membrNonBilClctCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('membrNonBilClctCtrl')">
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
                <%-- 조회기간 --%>
                <th>
                    <s:message code="membrNonBilClct.srchDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 회원명 --%>
                <th>
                    <s:message code="membrNonBilClct.membrNm"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm"/>
                </td>
                <%-- 회원번호 --%>
                <th>
                    <s:message code="membrNonBilClct.membrNo"/>
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="membrNo"/>
                </td>
            </tr>
            </tbody>
        </table>

        <%--left--%>
        <div class="wj-TblWrap mb20 w60 fl">
            <div class="mt20 oh sb-select dkbr">
                <button class="btn_skyblue mr5 mt5 fr" id="leftExcel" ng-click="excelDownload()">
                    <s:message code="dayDlvr.leftExcelDownload"/>
                </button>
            </div>
            <div class="wj-TblWrapBr mt10 mr10 pd20" style="height:470px;">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.membrNo"/>" binding="membrNo"
                                             width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.membrNm"/>" binding="membrNm"
                                             width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidAmt"/>"
                                             binding="postpaidAmt" width="115" is-read-only="true" align="center"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidInAmt"/>"
                                             binding="postpaidInAmt" width="115" is-read-only="true" align="center"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidBalAmt"/>"
                                             binding="postpaidBalAmt" width="115" is-read-only="true" align="center"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidAmtDt"/>"
                                             binding="postpaidAmtDt" width="115" is-read-only="true" align="center"
                                             aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidInAmtDt"/>"
                                             binding="postpaidInAmtDt" width="115" is-read-only="true"
                                             align="center" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mb20 w40 fr" ng-controller="membrNonBilClctDetailCtrl">
        <div class="mt20 oh sb-select dkbr">
            <button class="btn_skyblue mr5 mt5 fr" id="rigthExcel" ng-click="excelDownload()">
                <s:message code="dayDlvr.rightExcelDownload"/>
            </button>
        </div>
        <div class="wj-TblWrapBr mt10 ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="membrNonBilClct.regDt"/>" binding="regDt"
                                         width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidAmt"/>"
                                         binding="postpaidAmt" width="115" is-read-only="true" align="center"
                                         aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="membrNonBilClct.postpaidInAmt"/>"
                                         binding="postpaidInAmt" width="115" is-read-only="true" align="center"
                                         aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript"
        src="/resource/solbipos/js/membr/anals/membrNonBilClct/membrNonBilClct.js?ver=2019052801.10"
        charset="utf-8"></script>