<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
    <div ng-controller="payMonthCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('payMonthCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
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
                    <%-- 조회월 --%>
                    <th>
                        <s:message code="month.month" />
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"> <input id="startMonthMonthTotal" name="startDate" class="w110px" /></span>
                            <span class="rg">~</span>
                            <span class="txtIn"> <input id="endMonthMonthTotal" name="endDate" class="w110px" /></span>
                        </div>
                    </td>
                </tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                        <%-- 매장브랜드 --%>
                        <th><s:message code="dayProd.storeHqBrand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchStoreHqBrandCdCombo"
                                        ng-model="storeHqBrandCd"
                                        items-source="_getComboData('storeHqBrandCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchStoreHqBrandCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 매장코드 --%>
                        <th><s:message code="month.store"/></th>
                        <td colspan="3">
                            <%-- 매장선택 모듈 싱글 선택 사용시 include
                                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                              closeFunc - 팝업 닫기시 호출할 함수
                            --%>
                            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                                <jsp:param name="targetId" value="payMonthStore"/>
                            </jsp:include>
                            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="payMonthStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
            </tbody>
        </table>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                        <%-- 팀별 --%>
                    <th><s:message code="dayProd.momsTeam"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsTeamCombo"
                                    ng-model="momsTeam"
                                    items-source="_getComboData('momsTeamCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsTeamCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- AC점포별 --%>
                    <th><s:message code="dayProd.momsAcShop"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAcShopCombo"
                                    ng-model="momsAcShop"
                                    items-source="_getComboData('momsAcShopCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsAcShopCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 지역구분 --%>
                    <th><s:message code="dayProd.momsAreaFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAreaFgCombo"
                                    ng-model="momsAreaFg"
                                    items-source="_getComboData('momsAreaFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsAreaFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 상권 --%>
                    <th><s:message code="dayProd.momsCommercial"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsCommercialCombo"
                                    ng-model="momsCommercial"
                                    items-source="_getComboData('momsCommercialCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsCommercialCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 점포유형 --%>
                    <th><s:message code="dayProd.momsShopType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsShopTypeCombo"
                                    ng-model="momsShopType"
                                    items-source="_getComboData('momsShopTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsShopTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장관리타입 --%>
                    <th><s:message code="dayProd.momsStoreManageType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsStoreManageTypeCombo"
                                    ng-model="momsStoreManageType"
                                    items-source="_getComboData('momsStoreManageTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsStoreManageTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                            <%-- 지사 --%>
                        <th><s:message code="dayProd.branchCd"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchBranchCdComboo"
                                        ng-model="branchCd"
                                        items-source="_getComboData('branchCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchBranchCdComboo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </c:if>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.saleCnt"/>" binding="saleCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billCnt"/>" binding="billCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.billUprc"/>" binding="billUprc" width="100" is-read-only="true" align="right" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.gaAmt"/>" binding="gaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <%-- 결제수단 컬럼 생성--%>
                        <c:forEach var="payCol" items="${payColList}">
                            <wj-flex-grid-column header="<s:message code="payMonth.cnt"/>" binding="payCnt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="payMonth.realSaleAmt"/>" binding="payAmt${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="payMonth.rate"/>" binding="payRate${payCol.payCd}" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        </c:forEach>

                    </wj-flex-grid>

                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="payMonthCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
    var payParam       = {};
    payParam.payCd     = "${payCol.payCd}";
    payParam.payNm     = "${payCol.payNm}";
    payParam.payMethod = "${payCol.payMethod}";
    payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');

    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/pay/payMonth/payMonth.js?ver=20221013.02" charset="utf-8"></script>
