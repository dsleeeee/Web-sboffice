<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayPeriod/dayPeriodCorner/"/>

<div id="dayPeriodCornerView" name="dayPeriodView" class="subCon" style="display: none;">

    <div ng-controller="dayPeriodCornerCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"> <s:message code="dayPeriod.cornerSale" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('dayPeriodCornerCtrl',1)">
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
                    <%-- 조회일자 --%>
                    <th>
                        <s:message code="dayPeriod.date" />
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"> <input id="startDateDayPeriodCorner" name="startDate" class="w110px" /></span>
                            <span class="rg">~</span>
                            <span class="txtIn"> <input id="endDateDayPeriodCorner" name="endDate" class="w110px" /></span>
                        </div>
                    </td>
                </tr>
                <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                    <%-- 매장코드 --%>
                    <th><s:message code="dayPeriod.store"/></th>
                    <td colspan="3">
                        <%-- 매장선택 모듈 싱글 선택 사용시 include
                             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수
                        --%>
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="dayPeriodCornerStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
            </tbody>
        </table>

        <%--left--%>
        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30"><s:message code="dayPeriod.corner"/></span>
                    <%-- 코너별 엑셀다운로드 --%>
                    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleCorner()"><s:message code="cmm.excel.down"/></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="dayPeriod.storeCornrNm"/>" binding="storeCornrNm" width="250" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayPeriod.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                            <wj-flex-grid-column header="<s:message code="dayPeriod.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayPeriod.cornrCd"/>" binding="cornrCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        </wj-flex-grid>

                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="dayPeriodCornerCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>

                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="dayPeriodCornerDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="dayPeriod.saleDtl"/></span>
                <%-- 매출상세 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleDtl()"><s:message code="cmm.excel.down"/></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dayPeriod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayPeriod.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayPeriod.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>

                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="dayPeriodCornerDetailCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>

                </div>
            </div>

        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayPeriod/dayPeriodCorner.js?ver=20200204.09" charset="utf-8"></script>