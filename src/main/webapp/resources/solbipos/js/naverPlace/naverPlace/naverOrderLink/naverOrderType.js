/****************************************************************
 *
 * 파일명 : naverOrderType.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 > 주문유형 설정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverOrderTypeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverOrderTypeCtrl', $scope, $http, false));

    $scope.$on("naverOrderTypeCtrl", function (event, data) {


    });
    
    $scope.saveSetting = function () {
        
    }

}]);