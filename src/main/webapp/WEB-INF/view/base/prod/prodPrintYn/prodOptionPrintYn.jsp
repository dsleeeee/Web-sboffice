<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="prodOptionPrintYnView" class="subCon" style="display: none;">
    <div ng-controller="prodOptionPrintYnCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="prodPrintYnTab.prodOptionPrintYn"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('prodOptionPrintYnCtrl',1)" id="nxBtnSearch">
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
                <%-- 그룹코드 --%>
                <th><s:message code="prodPrintYn.optionGrpCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionGrpCd" ng-model="optionGrpCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 그룹명 --%>
                <th><s:message code="prodPrintYn.optionGrpNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionGrpNm" ng-model="optionGrpNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 옵션코드 --%>
                <th><s:message code="prodPrintYn.optionValCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionValCd" ng-model="optionValCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 옵션명 --%>
                <th><s:message code="prodPrintYn.optionValNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOptionValNm" ng-model="optionValNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 출력여부 --%>
                <th><s:message code="prodPrintYn.printYn"/></th>
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
                <th><s:message code="prodPrintYn.printYn"/></th>
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
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optionGrpCd"/>" binding="optionGrpCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optionGrpNm"/>" binding="optionGrpNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.useYn"/>" binding="useYn" data-map="useYnDataMap" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optionValCd"/>" binding="optionValCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optionValNm"/>" binding="optionValNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optProdCd"/>" binding="optProdCd" width="105" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.optProdNm"/>" binding="optProdNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodPrintYn.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodPrintYn/prodOptionPrintYn.js?ver=20230629.01" charset="utf-8"></script>