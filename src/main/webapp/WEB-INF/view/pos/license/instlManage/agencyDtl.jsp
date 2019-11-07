<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="agencyDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:570px;" fade-in="false" fade-out="false">
    <div ng-controller="agencyDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblAgencyDtl1"></label>
            <label id="lblAgencyCdAgencyDtl"></label>
            <label id="lblAgencyDtl2"></label>
            <label id="lblAgencyNmAgencyDtl"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('agencyDtlCtrl',1)">
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
                        <%-- 조회일자 --%>
                        <th>
                            <s:message code="instl.date" />
                        </th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn"> <input id="startDateDtl" name="startDate" class="w200px" /></span>
                                <span class="rg">~</span>
                                <span class="txtIn"> <input id="endDateDtl" name="endDate" class="w200px" /></span>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 그리드 --%>
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
                        <wj-flex-grid-column header="<s:message code="instl.dt"/>" binding="dt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.agency.buyCnt"/>" binding="buyCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.buyAmt"/>" binding="buyAmt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.buyStore"/>" binding="buyStore" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.agency.instCnt"/>" binding="instCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.storeCd"/>" binding="storeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.posNo"/>" binding="posNo" width="115" is-read-only="true" align="center" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.restCnt"/>" binding="restCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.memo"/>" binding="memo" data-map="instFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/agencyDtl.js?ver=2019052801.33" charset="utf-8"></script>