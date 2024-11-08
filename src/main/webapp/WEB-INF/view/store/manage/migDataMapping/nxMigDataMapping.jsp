<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="authHqList" value="${authHqList}" />

<div id="nxMigDataMappingView"class="subCon" ng-controller="nxMigDataMappingCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="migDataMapping.nxMigDataMapping.title" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('nxMigDataMappingCtrl',1)" id="nxBtnSearch2">
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
            <%-- 이관요청일자 --%>
            <th>
                <s:message code="migDataMapping.srchMigRegDate" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="nxStartDate" name="nxStartDate" class="w200px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="nxEndDate" name="nxEndDate" class="w200px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="migDataMapping.okposStoreCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="nxStoreCd" ng-model="nxStoreCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="migDataMapping.okposStoreNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="nxStoreNm" ng-model="nxStoreNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <%-- 이관여부 --%>
            <th>
                <s:message code="migDataMapping.migYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMigYn"
                            ng-model="migYn"
                            items-source="_getComboData('migYnCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMigYnCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <c:if test="${orgnFg != 'MASTER'}">
        <div>
        <p class="tl s14 mt5 lh15">- 이관 대상 데이터는 매출정보만 해당되며</p>
        <p class="tl s14 mt5 lh15">- 그 외 정보는 <span class="red">[환경설정, 기능설정, 테이블정보]</span> 추가로 등록해야합니다.</p>
        </div>
        </c:if>
        <%-- 신규등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="addNxInfo()">
            <s:message code="migDataMapping.newInfo" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.regDate"/>" binding="convSeq" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.regDate"/>" binding="convRegDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.okposStoreCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.okposStoreNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.fSaleDate"/>" binding="fSaleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.tSaleDate"/>" binding="tSaleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.maxSaleDate"/>" binding="maxSaleDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.lastBillNo"/>" binding="lastBillNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.migYn"/>" binding="procYn" data-map="migYnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.convMsg"/>" binding="convMsg" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.remark"/>" binding="convComments" width="150" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/nxMigDataMapping.js?ver=20241104.04" charset="utf-8"></script>

<%-- OKPOS-KCP 데이터 이관 신규등록 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/nxMigDataMappingInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>