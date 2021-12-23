<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="instlListView" class="subCon" style="display: none;">

    <div ng-controller="instlListCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="instl.instalList" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('instlListCtrl')" id="nxBtnSearch3">
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
                    <%-- 최초설치일자 --%>
                    <th>
                        <s:message code="instl.operation.date" />
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn w110px">
                                <wj-input-date
                                    value="startDate"
                                    ng-model="startDate"
                                    control="startDateCombo"
                                    min="2018-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                                </wj-input-date>
                            </span>
                            <span class="rg">~</span>
                            <span class="txtIn w110px">
                                <wj-input-date
                                    value="endDate"
                                    ng-model="endDate"
                                    control="endDateCombo"
                                    min="2018-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                                </wj-input-date>
                            </span>
                            <span class="chk ml10">
                              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                              <label for="chkDt">
                                <s:message code="cmm.all.day" />
                              </label>
                            </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 업체코드 --%>
                    <th>
                        <s:message code="instl.agency.agencyCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="il_srchAgencyCd" ng-model="agencyCd" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                    <%-- 업체명 --%>
                    <th>
                        <s:message code="instl.agency.agencyNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="il_srchAgencyNm" ng-model="agencyNm" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                </tr>
            </tbody>
        </table>

        <%--left--%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:420px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:300px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="instl.agency.agencyCd"/>" binding="agencyCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="instl.agency.agencyNm"/>" binding="agencyNm" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="instl.totStoreCnt"/>" binding="totStoreCnt" width="75" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="instl.closeStoreCnt"/>" binding="closeStoreCnt" width="75" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="instl.posCnt"/>" binding="posCnt" width="75" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                            <%--상세 조회시 필요--%>
                            <wj-flex-grid-column header="<s:message code="instl.restCnt"/>" binding="restCnt" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="instlListDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:420px; overflow-y: hidden;">
            <label id="lblAgencyCdInstl"></label> <label id="lblAgencyNmInstl"></label> <label id="lblPosCntInstl"></label> <label id="lblRestCntInstl"></label>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="instl.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.posNo"/>" binding="posNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.operation.agencyNm"/>" binding="agencyNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instlAgencyNm"/>" binding="instlAgencyNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instFg"/>" binding="instFg" data-map="instFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instInsId"/>" binding="instInsId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.minInstInsDt"/>" binding="minInstInsDt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.maxInstInsDt"/>" binding="maxInstInsDt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.operation.instCnt"/>" binding="instCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="instlListDetailCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/instlList.js?ver=2019052801.85" charset="utf-8"></script>

<%-- 설치현황 상세 조회 --%>
<c:import url="/WEB-INF/view/pos/license/instlManage/instlDtl.jsp">
</c:import>