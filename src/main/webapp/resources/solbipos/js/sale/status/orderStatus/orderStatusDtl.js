/****************************************************************
 *
 * 파일명 : orderStatusDtl.js
 * 설  명 : 주문현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.01     권지현      1.0
 *
 * **************************************************************/

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

var app = agrid.getApp();

app.controller('orderStatusDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('orderStatusDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orderFgDataMap = new wijmo.grid.DataMap(orderFgData, 'value', 'name');         // 주문구분
        $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달주문구분
    };

    // 팝업 오픈 시, 조회
    $scope.$on("orderStatusDtlCtrl", function(event, data) {
        $scope.wjOrderStatusDtlLayer.show(true);
        // 조회
        $scope.searchOrderStatusDtl(data);
        event.preventDefault();
    });

    // 조회
    $scope.searchOrderStatusDtl = function (data) {
        var params = {};
        params.saleDate = data.saleDate;
        params.orderNo = data.orderNo;
        $scope._inquirySub("/sale/orderStatus/orderStatus/orderStatus/getOrderStatusDtlList.sb", params, function () {});
    };
}]);