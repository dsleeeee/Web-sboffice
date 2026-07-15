<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="acComCodeView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <%-- 조회조건 없음 : 조회 버튼만 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="acComCode.title"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('acComCodeCtrl')" id="acComCodeBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <%-- 부모(공통코드) 선택 코드 캐시용 --%>
    <input type="hidden" id="s_acNmcodeCd" name="s_acNmcodeCd" value="" />

    <%-- 좌측 : 공통코드 그리드 --%>
    <div id="acComCodeGrpGrid" class="w40 mt10 fl" ng-controller="acComCodeCtrl">
        <div class="wj-TblWrapBr mr10 pd10" style="height: 550px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="acComCode.grpGridNm" /></span>
                <button class="btn_skyblue" id="btnAddAcComCode" ng-click="addRow()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelAcComCode" ng-click="deleteRow()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveAcComCode" ng-click="save()">
                    <s:message code="cmm.save" />
                </button>
                <button class="btn_skyblue" id="btnExcelAcComCode" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="wj-gridWrap" style="height:450px">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridAcComCodeList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.nmcodeCd"/>" binding="acNmcodeCd" width="80" align="center" max-length="4"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.nmcodeNm"/>" binding="acNmcodeNm" width="150"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.etc01"/>" binding="acNmcodeEtc01" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.etc02"/>" binding="acNmcodeEtc02" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.etc03"/>" binding="acNmcodeEtc03" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acComCode.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="acComCodeCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
        </div>
    </div>

    <%-- 우측 : 상세코드 그리드 --%>
    <div id="acComCodeDtlGrid" class="w60 mt10 fr" ng-controller="acComCodeDtlCtrl">
        <div class="wj-TblWrapBr pd10" style="height: 550px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="acComCode.gridNm" /></span>
                <button class="btn_skyblue" id="btnAddAcComCodeDtl" ng-click="addRow()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelAcComCodeDtl" ng-click="deleteRow()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveAcComCodeDtl" ng-click="save()">
                    <s:message code="cmm.save" />
                </button>
                <button class="btn_skyblue" id="btnExcelAcComCodeDtl" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="wj-gridWrap" style="height:450px">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridAcComCodeDtlList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.nmcodeCd"/>" binding="acNmcodeCd" width="0" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.dtlNmcodeCd"/>" binding="acNmcodeDtlCd" width="80" align="center" max-length="4"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.dtlNmcodeNm"/>" binding="acNmcodeDtlNm" width="150"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.dtlEtc01"/>" binding="acNmcodeDtlEtc01" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.dtlEtc02"/>" binding="acNmcodeDtlEtc02" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.dtlEtc03"/>" binding="acNmcodeDtlEtc03" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="acComCode.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="90" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="acComCodeDtlCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingMain/acComCode.js?ver=20260714.01" charset="utf-8"></script>
