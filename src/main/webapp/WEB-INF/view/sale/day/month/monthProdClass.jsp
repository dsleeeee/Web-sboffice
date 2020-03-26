<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/month/monthProdClass/"/>

<div id="monthProdClassView" name="monthView" class="subCon" style="display: none;" ng-controller="monthProdClassCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="month.prodClassSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('monthProdClassCtrl',1)">
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
                <%-- 조회월 --%>
                <th>
                    <s:message code="month.month" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonthMonthProdClass" name="startDate" class="w120px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonthMonthProdClass" name="endDate" class="w120px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                <%-- 매장코드 --%>
                <th><s:message code="month.store"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="monthProdClassStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 분류표시 --%>
                <th>
                    <s:message code="month.level" />
                </th>
                <td>
                    <div class="sb-select fl w200px">
                        <wj-combo-box
                            id="monthProdClassLevel"
                            ng-model="level"
                            items-source="_getComboData('srchMonthProdClassLevelCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index-changed="setMonthProdClass(s)">
                        </wj-combo-box>
                    </div>
                    <input type="hidden" id="hdMonthProdClassCd"/>
                    <input type="hidden" id="hdMonthProdClassNm"/>
                </td>
                <%-- 상품코드 --%>
                <th>
                    <s:message code="month.prodCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassProdCd" ng-model="prodCd" />
                </td>
            </tr>
            <tr>
                <%-- 상품명 --%>
                <th>
                    <s:message code="month.prodNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassProdNm" ng-model="prodNm" />
                </td>
                <%-- 바코드 --%>
                <th>
                    <s:message code="month.barCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMonthProdClassBarCd" ng-model="barCd" />
                </td>
            </tr>
            <tr>
                <%-- 분류조회 --%>
                <th>
                    <s:message code="month.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchMonthProdClassCd" ng-model="prodClassCdNm" ng-click="popUpMonthProdClass()" style="float: left;"
                           placeholder="<s:message code="month.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_monthProdClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelMonthProdClassCd" style="margin-left: 5px;" ng-click="delMonthProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

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
                    is-read-only="true"
                    id="wjMonthProdClassList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totSaleQty"/>" binding="totSaleQty" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    <%-- 컬럼 생성--%>
                    <c:forEach var="i" begin="1" end="100" step="1">
                        <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="pay${i}SaleAmt" width="100" align="right" is-read-only="true" visible="false" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="month.saleQty"/>" binding="pay${i}SaleQty" width="100" align="right" is-read-only="true" visible="false" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthProdClass.js?ver=20200324.06" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<%--<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">--%>
<%--</c:import>--%>

<%-- 상품매출 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/prodInfo/prodSaleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>