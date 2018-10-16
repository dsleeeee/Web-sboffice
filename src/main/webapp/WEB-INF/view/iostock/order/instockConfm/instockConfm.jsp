<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/instockConfm/instockConfm/"/>

<div class="subCon" ng-controller="instockConfmCtrl">
    <div class="searchBar">
        <a href="#" class="open">${menuNm}</a>
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
            <%-- 출고일자 --%>
            <th><s:message code="instockConfm.outDate"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w150"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w150"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 진행 --%>
            <th><s:message code="instockConfm.procFg"/></th>
            <td></td>
            <%-- 종류 --%>
            <th><s:message code="instockConfm.slipKind"/></th>
            <td></td>
        </tr>
        <tr>
            <%-- 기사 --%>
            <th><s:message code="instockConfm.dlvrNm"/></th>
            <td colspan="3"></td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="searchInstockConfmList()"><s:message code="cmm.search" /></button>
    </div>

    <div class="w100 mt10" >
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="instockConfm.storeCd"/>"      binding="storeCd"       width="0"   align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.slipNo"/>"       binding="slipNo"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.slipFg"/>"       binding="slipFg"        width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.slipKind"/>"     binding="slipKind"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.procFg"/>"       binding="procFg"        width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.dlvrNm"/>"       binding="dlvrNm"        width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.reqDate"/>"      binding="reqDate"       width="90"  align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.outDate"/>"      binding="outDate"       width="90"  align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.inDate"/>"       binding="inDate"        width="90"  align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.orderTot"/>"     binding="orderTot"      width="80"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.mgrTot"/>"       binding="mgrTot"        width="80"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.outTot"/>"       binding="outTot"        width="80"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.inTot"/>"        binding="inTot"         width="80"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instockConfm.remark"/>"       binding="remark"        width="150" align="left"   is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="instockConfmCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 입고확정 그리드 controller */
    app.controller('instockConfmCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('instockConfmCtrl', $scope, $http, true));
        $scope.slipFg = 1;

        var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
        var srchEndDate   = wcombo.genDateVal("#srchEndDate"  , "${sessionScope.sessionInfo.startDt}");

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {

            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("instockConfmCtrl");

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "slipNo") { // 전표번호
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }

                    if(col.format === "date") {
                        e.cell.innerHTML = getFormatDate(e.cell.innerText);
                    }
                    else if(col.format === "dateTime") {
                        e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                    }
                }
            });

            // 그리드 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "slipNo") { // 전표번호 클릭
                        var params = {};
                        params.slipNo    = selectedRow.slipNo;
                        $scope._broadcast('instockConfmDtlCtrl', params);
                    }
                }
            });

            // add the new GroupRow to the grid's 'columnFooters' panel
            s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 입고확정 리스트 조회
        $scope.searchInstockConfmList = function() {
            // 파라미터
            var params = {};
            params.slipFg    = $scope.slipFg;
            // params.procFg    = "20";
            params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
            params.endDate   = wijmo.Globalize.format(srchEndDate.value  , 'yyyyMMdd');

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/instockConfm/instockConfm/list.sb", params);
        };

    }]);
</script>

<%-- 출고자료생성 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/instockConfm/instockConfmDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
