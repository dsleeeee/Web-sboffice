<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="wjBoardReadingHistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:470px;" fade-in="false" fade-out="false">
    <div ng-controller="boardReadingHistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="boardReadingHist.info"/>
            <label id="lblBoardCd" style="display: none"></label>
            <label id="lblBoardSeqNo" style="display: none"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                    <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                        <%-- 열람자권한 --%>
                        <th>
                            <s:message code="boardReadingHist.userAuth" />
                        </th>
                        <td>
                            <div class="sb-input">
                                <%-- 전체 --%>
                                <input type="checkbox" id="totYn" name="totYnChk" ng-model="totYn" ng-change="totYnChk()">
                                <label for="totYn"><s:message code='boardReadingHist.totYn' /></label>
                                <%-- 시스템 --%>
                                <input type="checkbox" id="systemYn" name="systemYnChk" ng-model="systemYn" ng-change="systemYnChk()" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> >
                                <label for="systemYn" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> ><s:message code='boardReadingHist.systemYn' /></label>
                                <%-- 대리점 --%>
                                <input type="checkbox" id="agencyYn" name="agencyYnChk" ng-model="agencyYn" ng-change="agencyYnChk()" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> >
                                <label for="agencyYn" <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> ><s:message code='boardReadingHist.agencyYn' /></label>
                                <%-- 본사 --%>
                                <input type="checkbox" id="hqOfficeYn" name="hqOfficeYnChk" ng-model="hqOfficeYn" ng-change="hqOfficeYnChk()">
                                <label for="hqOfficeYn"><s:message code='boardReadingHist.hqOfficeYn' /></label>
                                <%-- 매장 --%>
                                <input type="checkbox" id="storeYn" name="storeYnChk" ng-model="storeYn" ng-change="storeYnChk()">
                                <label for="storeYn"><s:message code='boardReadingHist.storeYn' /></label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <%-- 열람자명 --%>
                        <th>
                            <s:message code="boardReadingHist.userNm" />
                        </th>
                        <td colspan="3">
                            <input type="text" class="sb-input w200" id="srchUserNm" ng-model="userNm" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('boardReadingHistCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:270px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.hqOfficeNm"/>" binding="hqOfficeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.authGrpNm"/>" binding="authGrpNm" data-map="authGrpNmDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.buso"/>" binding="buso" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.userNm"/>" binding="userNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="boardReadingHist.readngDt"/>" binding="readngDt" width="130" is-read-only="true" align="center" format="dateTime"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 권한그룹명 --%>
    var authGrpNmData = ${ccu.getCommCodeExcpAll("002")};

    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/board/boardReadingHist.js?ver=20200224.01" charset="utf-8"></script>