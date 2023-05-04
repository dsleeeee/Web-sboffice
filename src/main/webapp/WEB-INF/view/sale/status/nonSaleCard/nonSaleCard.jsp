<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="nonSaleCardCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('nonSaleCardCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
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
                        <span class="txtIn"><input id="srchApprNcardStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchApprNcardEndDate" class="w110px"></span>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 매장코드 --%>
                    <th><s:message code="cmm.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="apprNcardSelectStore"/>
                            <jsp:param name="targetPosId" value="apprNcardSelectPos"/>
                            <jsp:param name="targetCornerId" value="apprNcardSelectCorner"/>
                            <jsp:param name="closeFunc" value="getPosNmList"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
            </tr>
            <tr>
                </c:if>
                <%-- 포스선택 --%>
                <th><s:message code="pos.pos" /></th>
                <td <c:if test="${sessionInfo.orgnFg == 'HQ'}">colspan="3"</c:if>>
                    <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                        <jsp:param name="targetId" value="apprNcardSelectPos"/>
                        <jsp:param name="targetStoreId" value="apprNcardSelectStore"/>
                        <jsp:param name="closeFunc" value="getPosNmList"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 승인구분 --%>
                <th><s:message code="dayMcoupn.apprProcFg" /></th>
                <td>
                    <div class="sb-select">
                      <span class="txtIn">
                            <wj-combo-box
                                    id="srchNcardSaleYnDisplay"
                                    ng-model="saleYnModel"
                                    items-source="_getComboData('srchNcardSaleYnDisplay')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
                <%-- 승인처리 --%>
                <th><s:message code="storeStatus.apprProcFg" /></th>
                <td>
                    <div class="sb-select">
                      <span class="txtIn">
                            <wj-combo-box
                                    id="srchNcardApprProcFgDisplay"
                                    ng-model="apprProcFgModel"
                                    items-source="_getComboData('srchNcardApprProcFgDisplay')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="apprNcardSelectStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <input type="hidden" id="posNcardSelectPosCd" value=""/>
                <input type="hidden" id="posNcardSelectPosName" value=""/>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
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
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="nonSaleCard.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="nonSaleCard.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.billNo"/>" binding="billNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.apprProc"/>" binding="apprProc" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.apprProcFg"/>" binding="apprProcFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.acquireNm"/>" binding="acquireNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.cardTypeFg"/>" binding="cardTypeFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.cardNo"/>" binding="cardNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.tipAmt"/>" binding="tipAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.instCntNm"/>" binding="instCntNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.instCnt"/>" binding="instCnt" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.apprDt"/>" binding="apprDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.apprNo"/>" binding="apprNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="nonSaleCard.apprAmt"/>" binding="apprAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/nonSaleCard/nonSaleCard.js?ver=20230412.09" charset="utf-8"></script>
