<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="storeProdOptionPrintYnView" class="subCon" style="display: none;">
    <div ng-controller="storeProdOptionPrintYnCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="storeProdPrintYnTab.storeProdOptionPrintYn"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('storeProdOptionPrintYnCtrl',1)" id="nxBtnSearch">
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
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="storeProdOptionPrintYnStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 그룹코드 --%>
                <th><s:message code="storeProdPrintYn.optionGrpCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionGrpCd" ng-model="optionGrpCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 그룹명 --%>
                <th><s:message code="storeProdPrintYn.optionGrpNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionGrpNm" ng-model="optionGrpNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 옵션코드 --%>
                <th><s:message code="storeProdPrintYn.optionValCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionValCd" ng-model="optionValCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 옵션명 --%>
                <th><s:message code="storeProdPrintYn.optionValNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionValNm" ng-model="optionValNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 출력여부 --%>
                <th><s:message code="storeProdPrintYn.printYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchPrintYnCombo"
                                ng-model="printYn"
                                items-source="_getComboData('printYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchPrintYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
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
                <%-- 출력여부 --%>
                <th><s:message code="storeProdPrintYn.printYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchPrintYnChgCombo"
                                ng-model="printYnChg"
                                items-source="_getComboData('printYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('printYnChg')"><s:message code="cmm.batchChange" /></a>
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
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optionGrpCd"/>" binding="optionGrpCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optionGrpNm"/>" binding="optionGrpNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.useYn"/>" binding="useYn" data-map="useYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optionValCd"/>" binding="optionValCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optionValNm"/>" binding="optionValNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optProdCd"/>" binding="optProdCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.optProdNm"/>" binding="optProdNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.hqPrintYn"/>" binding="hqPrintYn" data-map="hqPrintYnDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeProdPrintYn.remark"/>" binding="remark" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="storeProdOptionPrintYnCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

    </div>
</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/storeProdPrintYn/storeProdOptionPrintYn.js?ver=20240304.01" charset="utf-8"></script>