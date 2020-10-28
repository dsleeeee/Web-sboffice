<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="membrPossesnCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('membrPossesnCtrl')">
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
                <s:message code="membrPossesn.srchDate"/>
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="mt20 oh sb-select dkbr">
        <button class="btn_skyblue mr5 mt5 fr" ng-click="excelDownload()">
            <s:message code="member.excel"/>
        </button>
    </div>
    <%-- 그리드 --%>
    <div class="w100 fl mt10 mb20">
        <div class="wj-TblWrapBr " style="height: 600px;">
            <div class="wj-gridWrap" style="height:600px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="membrPossesn.storeNm"/>" binding="storeNm"
                                             width="130"
                                             is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.totCnt"/>" binding="totCnt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.totAmt"/>" binding="totAmt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.totMemCnt"/>" binding="totMemCnt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.totMemAmt"/>" binding="totMemAmt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.perMemCnt"/>" binding="perMemCnt"
                                             width="115"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.perMemAmt"/>" binding="perMemAmt"
                                             width="115"
                                             is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.perCnt"/>" binding="perCnt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="membrPossesn.perAmt"/>" binding="perAmt"
                                             width="115"
                                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/membrPossesn/membrPossesn.js?ver=2019052801.06"
        charset="utf-8"></script>