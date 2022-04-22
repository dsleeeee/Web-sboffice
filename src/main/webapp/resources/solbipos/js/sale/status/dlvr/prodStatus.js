/****************************************************************
 *
 * 파일명 : prodStatus.js
 * 설  명 : 배달상품현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.11     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var optionFg = [
    {"name":"전체","value":""},
    {"name":"주문앱미등록상품","value":"1"},
    {"name":"주문앱미등록상품외","value":"2"}
];
/**
 * 배달상품현황 그리드 생성
 */
app.controller('prodStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodStatusCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope._setComboData("cookMemoUseYn", cookMemoUseYn);
    $scope._setComboData("optionFg", optionFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 조회
    $scope.$on("prodStatusCtrl", function(event, data) {
        $scope.searchProdStatusList();
        event.preventDefault();
    });

    // 배달상품현황 그리드 조회
    $scope.searchProdStatusList = function(){

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.cookMemoUseYn = $scope.cookMemoUseYn;
        params.optionFg = $scope.optionFg;

        $scope._inquirySub('/sale/status/dlvr/prodStatus/getProdStatusList.sb', params, function() {

        });
    };

}]);