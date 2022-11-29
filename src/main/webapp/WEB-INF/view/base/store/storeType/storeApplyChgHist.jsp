<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="storeApplyChgHistView" name="storeApplyChgHistView" class="subCon" style="display: none;">

    <div ng-controller="storeApplyChgHistCtrl">

        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeType.storeApplyChgHist" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch5" ng-click="_pageView('storeApplyChgHistCtrl', 1)">
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
                <%-- 변경 일자 --%>
                <th><s:message code="storeType.chgDate" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchSachStartDate" name="startDate" class="w110px"/></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchSachEndDate" name="endDate" class="w110px"/></span>
                    </div>
                </td>
                <%-- 매장코드 --%>
                <th><s:message code="storeType.store"/></th>
                <td>
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="storeApplyChgHistStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 매장타입 --%>
                <th><s:message code="storeType.storeType" /></th>
                <td>
                    <div class="sb-select w100">
                        <wj-combo-box
                                id="srchSachStoreType"
                                items-source="_getComboData('srchSachStoreType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchSachStoreTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 메뉴그룹 --%>
                <th><s:message code="storeType.menuGroup" /></th>
                <td>
                    <div class="sb-select w100">
                        <wj-combo-box
                                id="srchSachStoreGroup"
                                items-source="_getComboData('srchSachStoreGroup')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchSachStoreGroupCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일 --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScale"
                    control="listScaleCombo5"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
            <%-- 취소 --%>
            <button class="btn_skyblue ml5 fr" id="btnCancel" ng-click="cancel()"><s:message code="cmm.cancel" /></button>
        </div>

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
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeTypeCd"/>" binding="storeTypeCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeTypeNm"/>" binding="storeTypeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeGroupCd"/>" binding="storeGroupCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeGroupNm"/>" binding="storeGroupNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.procDt"/>" binding="chgProcDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.modId"/>" binding="modId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.applyProcFg"/>" binding="applyProcFg" data-map="applyProcFgDataMap" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.applyInfo"/>" binding="applyInfo" width="190" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%-- 저장시 필요 --%>
                    <wj-flex-grid-column header="<s:message code="storeType.procDt"/>" binding="procDt" width="125" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeType.applyProcFgYn"/>" binding="applyProcFgYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="storeApplyChgHistCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storeType/storeApplyChgHist.js?ver=20221124.02" charset="utf-8"></script>

<%-- 매장적용이력 상세 팝업 --%>
<c:import url="/WEB-INF/view/base/store/storeType/storeApplyChgHistDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>