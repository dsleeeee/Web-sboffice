<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="cupReturnStatusCtrl">
        <%-- 조회조건 --%>
        <%--<div class="searchBar flddUnfld">--%>
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('cupReturnStatusCtrl', 1)">
                    <s:message code="cmm.search" />
                </button>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <c:if test="${momsEnvstVal == '1'}">
                        <%-- 확장조회 --%>
                        <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                            <s:message code="cmm.search.addShow" />
                        </button>
                    </c:if>
                </c:if>
            </div>
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
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 바코드번호 --%>
                <th>
                    <s:message code="cupReturnStatus.barcodeId" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="BarCd" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 회수방법 --%>
                <th>
                    <s:message code="cupReturnStatus.returnType" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchReturnType"
                                ng-model="returnType"
                                items-source="_getComboData('srchReturnType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchReturnTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 전송일자 --%>
                <th><s:message code="cupReturnStatus.sendDate"/></th>
                <td colspan="4">
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchSendStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchSendEndDate" class="w110px"></span><%--전체기간--%>
                        <span class="chk ml10">
                      <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                      <label for="chkDt">
                        <s:message code="cmm.all.day" />
                      </label>
                    </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 전송성공여부 --%>
                <th>
                    <s:message code="cupReturnStatus.sendYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchSendYn"
                                ng-model="sendYn"
                                items-source="_getComboData('srchSendYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchSendYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.saleDate"/>" binding="saleDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.regSeq"/>" binding="regSeq" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.barcodeId"/>" binding="barcodeId" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.returnType"/>" binding="returnType" data-map="returnTypeDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.returnDt"/>" binding="returnDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.returnCost"/>" binding="returnCost" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.cupType"/>" binding="cupType" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.sendDate"/>" binding="sendDate" width="90" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.sendDt"/>" binding="sendDt" width="130" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.sendYn"/>" binding="sendYn" width="100" align="center" data-map="sendYnDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.regDt"/>" binding="regDt" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.regId"/>" binding="regId" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.modDt"/>" binding="modDt" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cupReturnStatus.modId"/>" binding="modId" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <ul id="cupReturnStatusCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/cupReturnStatus/cupReturnStatus.js?ver=20250717.01" charset="utf-8"></script>
