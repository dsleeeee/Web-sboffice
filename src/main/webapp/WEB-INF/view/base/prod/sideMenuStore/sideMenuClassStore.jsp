<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="sideMenuClassStoreView" class="subCon" style="display: none;padding: 10px 20px 40px;">
    <div ng-controller="sideMenuClassStoreCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="sideMenuStoreTab.sideMenuClassStore"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('sideMenuClassStoreCtrl',1)" id="nxBtnSearch">
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
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="sideMenuClassStoreStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
                <%-- 등록구분 --%>
                <th><s:message code="sideMenuStore.regYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegYnCombo"
                                ng-model="regYn"
                                items-source="_getComboData('regYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchRegYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 선택그룹 --%>
                <%--<th><s:message code="sideMenuStore.sdselGrp"/></th>--%>
                <%--<td>--%>
                    <%--&lt;%&ndash; 선택그룹 선택 모듈 사용시 include &ndash;%&gt;--%>
                    <%--<jsp:include page="/WEB-INF/view/common/popup/selectSdselGrp.jsp" flush="true">--%>
                        <%--<jsp:param name="targetTypeFg" value="S"/>--%>
                        <%--<jsp:param name="targetId" value="sideMenuClassStoreSdselGrp"/>--%>
                    <%--</jsp:include>--%>
                    <%--&lt;%&ndash;// 선택그룹 선택 모듈 사용시 include &ndash;%&gt;--%>
                <%--</td>--%>
            </tr>
            <tr>
                <%-- 선택그룹코드 --%>
                <th><s:message code="sideMenuStore.sdselGrpCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 선택그룹코명 --%>
                <th><s:message code="sideMenuStore.sdselGrpNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 선택분류코드 --%>
                <th><s:message code="sideMenuStore.sdselClassCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 선택분류명 --%>
                <th><s:message code="sideMenuStore.sdselClassNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh">
            <p class="tl s14 mt5 lh15">- '적용매장구분'이 '제외매장', '허용매장'인 경우에만 조회됩니다.</p>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
        </div>

        <%-- 일괄적용 --%>
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 등록구분 --%>
                <th><s:message code="sideMenuStore.regYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegYnChgCombo"
                                ng-model="regYnChg"
                                items-source="_getComboData('regYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('regYnChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <td colspan="3"></td>
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
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselGrpCd"/>" binding="sdselGrpCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselGrpNm"/>" binding="sdselGrpNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselClassCd"/>" binding="sdselClassCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselClassNm"/>" binding="sdselClassNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.regYn"/>" binding="regYn" data-map="regYnDataMap" width="80" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenuStore/sideMenuClassStore.js?ver=20230615.01" charset="utf-8"></script>