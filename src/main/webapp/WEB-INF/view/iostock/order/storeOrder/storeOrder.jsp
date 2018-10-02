<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrder/"/>

<div class="subCon" ng-controller="storeOrderCtrl">
    <div class="searchBar flddUnfld">
        <a href="javascript:;" class="open">${menuNm}</a>
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
                    <span class="txtIn w150">
                    <wj-combo-box
                            id="srchDateFg"
                            ng-model="dateFg"
                            items-source="_getComboData('srchDateFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                    </span>
                    <span class="txtIn"><input id="srchStartDate" class="w150"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w150"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 출고요청일자 --%>
            <th><s:message code="storeOrder.reqDate"/></th>
            <td>
                <div class="sb-select fl mr10">
                    <span class="txtIn"><input id="reqDate" class="w150" ng-model="storeOrder.reqDate"></span>
                </div>
                <a href="javascript:;" class="btn_grayS" ng-click="newReqOrder()"><s:message code="storeOrder.reqRegist" /></a>
                <%--<button type="button" class="btn_blue" id="btnReqRegist" ng-click="newReqOrder()"><s:message code="storeOrder.reqRegist" /></button>--%>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        envst173 : ${envst173} &nbsp;&nbsp;envst594 : ${envst594}
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeOrderCtrl')"><s:message code="cmm.search" /></button>
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
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeOrder.reqDate"/>"  binding="reqDate"  width="100" align="center" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.slipFg"/>"   binding="slipFg"   width="70" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.procFg"/>"   binding="procFg"   width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.dtlCnt"/>"   binding="dtlCnt"   width="70" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.orderAmt"/>" binding="orderAmt" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.orderVat"/>" binding="orderVat" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.orderTot"/>" binding="orderTot" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeOrder.remark"/>"   binding="remark"   width="*"  align="left"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="storeOrderCtrl"/>
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

    /** 주문등록 그리드 controller */
    app.controller('storeOrderCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeOrderCtrl', $scope, $http, true));

        var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
        var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDt}");
        var reqDate       = wcombo.genDate("#reqDate");
        $scope._setComboData("srchDateFg", [
            {"name":"<s:message code='storeOrder.reqDate'/>","value":"req"},
            {"name":"<s:message code='storeOrder.regDate'/>","value":"reg"},
            {"name":"<s:message code='storeOrder.modDate'/>","value":"mod"}
        ]);

        // 출고가능일자 세팅
        reqDate.value = new Date(getFormatDate("${reqDate}", "-"));
        // 출고요청일자 선택가능여부에 따라 출고요청일자 선택여부 처리
        if("${envst594}" === "Y") {
            reqDate.isReadOnly = true;
        }

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("storeOrderCtrl");

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "reqDate") { // 마감월 클릭
                        let item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            });

            // 그리드 출고요청일자 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "reqDate") {
                        var params = {};
                        params.reqDate  = selectedRow.reqDate;
                        params.slipFg   = selectedRow.slipFg;
                        params.procFg   = selectedRow.procFg;
                        params.hdRemark = selectedRow.remark;
                        $scope._broadcast('storeOrderDtlCtrl', params);
                    }
                }
            });

            // add the new GroupRow to the grid's 'columnFooters' panel
            s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeOrderCtrl", function(event, data) {
            $scope.searchStoreOrderList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 주문 리스트 조회
        $scope.searchStoreOrderList = function() {
            // 파라미터
            var params = {};
            params.dateFg = $scope.dateFg;
            params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
            params.endDate   = wijmo.Globalize.format(srchEndDate.value  , 'yyyyMMdd');

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/storeOrder/storeOrder/list.sb", params);
        };

        // 신규 요청등록
        $scope.newReqOrder = function () {
            var params = {};
            params.callParent = "storeOrder";
            params.reqDate    = wijmo.Globalize.format(reqDate.value, 'yyyyMMdd');
            params.slipFg     = 1;
            params.hdRemark   = "";
            $scope._broadcast("storeOrderRegistCtrl", params);
        }
    }]);
</script>

<%-- 주문등록 상품 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/storeOrder/storeOrderDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 주문등록 상품 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/storeOrder/storeOrderRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
