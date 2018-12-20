<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
    /*.cusWrap {width: 100%; min-height: 768px; height: 100%; display: table;}*/
    .cusWrap {width: 760px; min-height: 590px; height: 100%; display: table;}
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    .cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}
</style>

<body ng-app="rootApp" ng-controller="rootCtrl">
<div class="cusWrap">
    <div class="content-middle">
        <div class="subCon" ng-controller="memberCtrl">
            <div class="searchBar flddUnfld">
                <%--<span class="cusTitle"><s:message code="postpaid.request.taxBill"/></span>--%>
                <a href="#" class="open fl"><s:message code="postpaid.request.taxBill"/></a>

                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('memberCtrl')">
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
                    <%-- 회원번호 --%>
                    <th><s:message code="postpaid.membrNo" /></th>
                    <td>
                        <input type="text" id="srchMembrNo" class="sb-input w100" ng-model="membrNo" maxlength="10"/>
                    </td>
                    <%-- 회원명 --%>
                    <th><s:message code="postpaid.membrNm" /></th>
                    <td>
                        <input type="text" id="srchMembrNm" class="sb-input w100" ng-model="membrNm" maxlength="10"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 세금계산서 발행 대상 목록 --%>
            <div class="mb40" >
                <%-- 예외출고 상품 그리드 --%>
                <div id="productGrid" class="wj-gridWrap" style="height:310px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.storeNm"/>" binding="storeNm" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.membrNo"/>" binding="membrNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.membrNm"/>" binding="membrNm" width="95" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.postpaidAmt"/>" binding="postpaidAmt" width="95" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.postpaidInAmt"/>" binding="postpaidInAmt" width="95" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.postpaidBalAmt"/>" binding="postpaidBalAmt" width="90" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.taxBillAmt"/>" binding="requestAmt" width="140" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="postpaid.request.taxBill"/>" binding="deposit" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="productCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="/resource/solbipos/js/application/pos/posPostpaid/posPostpaid.js?ver=20181203.01" charset="utf-8"></script>

<%-- 예외출고 수량 등록 팝업 --%>
<c:import url="/WEB-INF/view/application/pos/posPostpaid/taxBillRequest.jsp">
</c:import>

