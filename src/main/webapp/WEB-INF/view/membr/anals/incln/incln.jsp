<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="inclnCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('inclnCtrl')">
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
                <s:message code="dlvrInfo.srchDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                      <div class="sb-select w115px">
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
                      <div class="sb-select w115px">
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
                </div>
            </td>
            <td>
                <div class="sb-input">
                    <%--금액--%>
                    <input type="radio" name="searchOption" ng-model="searchOption" value="AMT" ng-checked="true" checked="checked">
                    <label class="mr5"><s:message code="incln.sum.amt"/></label>
                    <%--수량--%>
                    <input type="radio" name="searchOption" ng-model="searchOption" value="QTY">
                    <label><s:message code="incln.sum.qty"/></label>
                </div>
            </td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>
    <%-- 그리드 --%>
    <div class="w100 fl mt10 mb20">
        <div class="wj-TblWrapBr " style="height: 470px;">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        id="inclnGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="incln.level1Nm"/>" binding="level1Nm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.level2Nm"/>" binding="level2Nm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.level3Nm"/>" binding="level3Nm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumSale"/>" binding="sumSale" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.gendrF"/>" binding="sumGendrF" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.gendrG"/>" binding="sumGendrM" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.gendrNon"/>" binding="sumGendrNon" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge10"/>" binding="sumAge10" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge20"/>" binding="sumAge20" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge30"/>" binding="sumAge30" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge40"/>" binding="sumAge40" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge50"/>" binding="sumAge50" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="incln.sumAge60"/>" binding="sumAge60" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/incln/incln.js?ver=20201207.04" charset="utf-8"></script>