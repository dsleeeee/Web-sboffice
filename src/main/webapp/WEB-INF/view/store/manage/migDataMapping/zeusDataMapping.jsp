<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="authHqList" value="${authHqList}" />

<div id="zeusDataMappingView"class="subCon" ng-controller="zeusDataMappingCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="migDataMapping.zeusDataMapping.title" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('zeusDataMappingCtrl',1)" id="nxBtnSearch3">
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
            <%-- 연동일자 --%>
            <th>
                <s:message code="migDataMapping.mappingDay" />
            </th>
            <td colspan="4">
                <div class="sb-select">
                    <span class="txtIn"> <input id="zeusStartDate" name="zeusStartDate" ng-model="zeusStartDate" class="w150px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="zeusEndDate" name="zeusEndDate" ng-model="zeusEndDate" class="w150px" /></span>
                    <%--기간선택--%>
                    <div class="txtIn tabType2">
                        <ul>
                            <li>
                                <a id="oneWeek" class="on" ng-click="setDt('0');">
                                    <s:message code="migDataMapping.oneWeek" />
                                </a>
                            </li>
                            <li>
                                <a id="oneMonth" ng-click="setDt('1');">
                                    <s:message code="migDataMapping.oneMonth" />
                                </a>
                            </li>
                            <li>
                                <a id="threeMonth" ng-click="setDt('2');">
                                    <s:message code="migDataMapping.threeMonth" />
                                </a>
                            </li>
                            <li>
                                <a id="sixMonth" ng-click="setDt('3');">
                                    <s:message code="migDataMapping.sixMonth" />
                                </a>
                            </li>
                            <li>
                                <a id="all" ng-click="setDt('4');">
                                    <s:message code="cmm.all" />
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="migDataMapping.solbiStoreCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="zeusStoreCd" ng-model="zeusStoreCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="migDataMapping.solbiStoreNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="zeusStoreNm" ng-model="zeusStoreNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 이관여부 --%>
            <th>
                <s:message code="migDataMapping.mappingFg" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchMapFg"
                            ng-model="mapFg"
                            items-source="_getComboData('mapFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchMapFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <%-- 신규등록 --%>
        <button class="btn_blue ml5 fr" id="btnAddMapping" ng-click="addZeusMapping()">
            <s:message code="migDataMapping.zeusMappingStore" />
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
                <wj-flex-grid-column header="<s:message code="migDataMapping.hqOfficeCd"/>"         binding="cocd"          width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreCd"/>"       binding="buut"          width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreNm"/>"       binding="buname1"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.hqOfficeCd"/>"         binding="hqOfficeCd"    width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreCd"/>"       binding="storeCd"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.solbiStoreNm"/>"       binding="storeNm"       width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.mappingFg"/>"          binding="mappingFg"     width="80"  is-read-only="true" align="center" data-map="mapFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.mappingTime"/>"        binding="mappingDt"     width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.lastMappingTime"/>"    binding="regDt"         width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.delMapping"/>"         binding="delDt"         width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="migDataMapping.remark"/>"             binding="remark"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script>

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/zeusDataMapping.js?ver=20250204.01" charset="utf-8"></script>

<%-- OKPOS-KCP 데이터 이관 신규등록 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/zeusDataMappingStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>