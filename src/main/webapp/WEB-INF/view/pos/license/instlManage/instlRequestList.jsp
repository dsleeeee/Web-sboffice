<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="instlRequestListView" class="subCon" style="display: none;" ng-controller="instlRequestListCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="instl.install.request" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('instlRequestListCtrl',1)">
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
            <%-- 매장코드 --%>
            <th><s:message code="instl.storeCd" /></th>
            <td><input type="text" id="srchStoreCd" class="sb-input w100" maxlength="5" ng-model="storeCd"/></td>
            <%-- 매장명 --%>
            <th><s:message code="instl.storeNm" /></th>
            <td><input type="text" id="srchStoreNm" class="sb-input w100" maxlength="15" ng-model="storeNm"/></td>
        </tr>
        <tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if>>
            <%-- 대리점코드 --%>
            <th><s:message code="instl.agencyCd" /></th>
            <td><input type="text" id="srchAgencyCd" class="sb-input w100" maxlength="7" ng-model="agencyCd"/></td>
            <%-- 대리점명 --%>
            <th><s:message code="instl.agencyNm" /></th>
            <td><input type="text" id="srchAgencyNm" class="sb-input w100" maxlength="15" ng-model="agencyNm"/></td>
        </tr>
        <tr>
            <%-- 설치구분 --%>
            <th><s:message code="instl.instFg" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchInstFg"
                            ng-model="instFg"
                            items-source="_getComboData('srchInstFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
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
        <%--// 페이지 스케일  --%>

        <%-- 신규매장등록 --%>
        <button class="btn_skyblue ml5 fr" ng-click="requestInstall()">
            <s:message code="instl.req" />
        </button>
    </div>

    <%-- 매장 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="instl.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.storeNm"/>" binding="storeNm" width="140" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.posNo"/>" binding="posNo" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instCnt"/>" binding="seqNo" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.agencyCd"/>" binding="agencyCd" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.agencyNm"/>" binding="agencyNm"  width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instReqDt"/>" binding="instReqDt" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instReqId"/>" binding="instReqId" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instInsDt"/>" binding="instInsDt" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instInsId"/>" binding="instInsId" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instFg"/>" binding="instFg" data-map="instFgDataMap" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.instReason"/>" binding="instReason" data-map="reasonDatMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instl.remark"/>" binding="remark" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="instlRequestListCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script>
    var instFgData = [
        {"name":"전체","value":""},
        {"name":"설치의뢰","value":"0"},
        {"name":"신규설치","value":"1"},
        {"name":"재설치","value":"2"}
    ];
    var sysStatFg = ${ccu.getCommCodeExcpAll("005")};
    var reasonData = ${ccu.getCommCodeExcpAll("102")};
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var orgnNm = "${orgnNm}";
    var pAgencyCd = "${pAgencyCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/instlRequestList.js?ver=20191015" charset="utf-8"></script>

<%-- 매장정보 --%>
<c:import url="/WEB-INF/view/pos/install/installManage/installRequest.jsp">
</c:import>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>

<%-- 설치요청 상세 --%>
<c:import url="/WEB-INF/view/pos/license/instlManage/instlRequestDtl.jsp">
</c:import>