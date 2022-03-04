<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<wj-popup id="wjOrderStatusDtlLayer" control="wjOrderStatusDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:90%;height:85%;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mobile.orderStatus.orderStatusDtl"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="orderStatusDtlCtrl">
            <div class="w100">
                <%--위즈모 테이블--%>
                <div class="popGrid mt5">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="mobile.orderStatus.posNo"/>"          binding="posNo" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderNo"/>"        binding="orderNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.orderStatus.orderFg"/>"        binding="orderDtlFg" width="50" align="center" is-read-only="true" data-map="orderFgDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.orderStatus.dlvrPackFg"/>"     binding="dlvrPackFg" width="50" align="center" is-read-only="true" data-map="dlvrOrderFgDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.orderStatus.realSaleAmt"/>"    binding="realSaleAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">

    var multiStoreFg = '${multiStoreFg}';

    // 주문구분
    var orderFgData = [
        {"name": "주문", "value": "1"},
        {"name": "취소", "value": "2"},
        {"name": "결제", "value": "3"}
    ];
    // 배달주문구분
    var dlvrOrderFgData = [
        {"name": "일반", "value": "1"},
        {"name": "배달", "value": "2"},
        {"name": "포장", "value": "3"}
    ];
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 매장선택 controller */
    app.controller('orderStatusDtlCtrl', ['$scope', '$http', function ($scope, $http) {

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('orderStatusDtlCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            $scope.orderFgDataMap = new wijmo.grid.DataMap(orderFgData, 'value', 'name');         // 주문구분
            $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달주문구분

            // 합계
            // add the new GroupRow to the grid's 'columnFooters' panel
            s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on('orderStatusDtlCtrl', function (event, data) {
            // 반품현황 상세 팝업 오픈
            $scope.wjOrderStatusDtlLayer.show(true);
            $scope.searchOrderStatusDtl(data);
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchOrderStatusDtl = function (data) {
            // 파라미터
            var params = {};
            params.srchStoreCd = data.srchStoreCd;
            params.saleDate = data.saleDate;
            params.orderNo = data.orderNo;
            $scope._inquirySub("/mobile/sale/status/orderStatus/orderStatus/getMobileOrderStatusDtlList.sb", params, function () {
            });
        };
    }]);
</script>
