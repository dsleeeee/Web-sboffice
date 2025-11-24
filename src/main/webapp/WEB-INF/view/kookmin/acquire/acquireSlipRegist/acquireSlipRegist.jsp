<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="acquireSilpRegistCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('acquireSilpRegistCtrl', 1)"><s:message code="cmm.search"/></button>
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
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w100px">
                            <wj-combo-box
                                    id="srchDateFg"
                                    ng-model="dateFg"
                                    items-source="_getComboData('srchDateFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 전표번호 --%>
                <th><s:message code="acquireSlipRegist.slipNo"/></th>
                <td>
                    <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNo" class="sb-input w100" maxlength="8" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 발주전표번호 --%>
                <th><s:message code="acquireSlipRegist.orderSlipNo"/></th>
                <td>
                    <input type="text" id="srchOrderSlipNo" name="srchOrderSlipNo" ng-model="orderSlipNo" class="sb-input w100" maxlength="8" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
            <%-- 전표구분 --%>
                <th><s:message code="acquireSlipRegist.slipFg"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w120px">
                            <wj-combo-box
                                    id="srchSlipFg"
                                    ng-model="slipFg"
                                    items-source="_getComboData('srchSlipFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
                <%-- 진행구분 --%>
                <th><s:message code="acquireSlipRegist.procFg"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w120px">
                            <wj-combo-box
                                    id="srchProcFg"
                                    ng-model="procFg"
                                    items-source="_getComboData('srchProcFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
        <tr>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="acquireSlipRegistStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
            <%-- 거래처 --%>
            <th><s:message code="acquireSlipRegist.vendr"/></th>
            <td>
                <%-- 거래처선택 모듈 싱글 선택 사용시 include
                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수
                --%>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
                    <jsp:param name="targetId" value="acquireSlipRegistSelectVendr"/>
                    <jsp:param name="vendrFg" value="1"/>
                </jsp:include>
        </tr>
        </tbody>
    </table>

    <div class="mt10 tr">
        <%-- 입고신규등록 --%>
        <button type="button" class="btn_skyblue ml5" id="btnInRegist" ng-click="inOutstockRegist(1)">
            <s:message code="acquireSlipRegist.inStockRegist"/></button>
        <%-- 반출신규등록 --%>
        <button type="button" class="btn_skyblue ml5" id="btnOutRegist" ng-click="inOutstockRegist(-1)">
            <s:message code="acquireSlipRegist.outStockRegist"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.slipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.slipFg"/>" binding="slipFg" width="65" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.procFg"/>" binding="procFg" width="65" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.inOutStockType"/>" binding="instockType" width="80" align="center" is-read-only="true" data-map="instockTypeMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="acquireSlipRegist.inOutStockDate"/>" binding="instockDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="acquireSilpRegistCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/acquireSlipRegist/acquireSlipRegist.js?ver=20251119.01" charset="utf-8"></script>

<%-- 입고/반출 상세 팝업전용 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/inOutStockRegistPop.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
