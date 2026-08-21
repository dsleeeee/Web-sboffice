/****************************************************************
 *
 * 파일명 : naverOrderInfo.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 > 정보 수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverOrderInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverOrderInfoCtrl', $scope, $http, false));

    $scope.$on("naverOrderInfoCtrl", function (event, data) {

        alert(data);

    });
    
    // 저장
    $scope.btnSaveInfo = function () {
        
    };
    
    // 닫기
    $scope.closeInfo = function () {

        // 초기화
        $("#storeNm").val("");
        $("#businessId").val("");
        $("#serviceNm").val("");
        $("#phoneNo").val("");
        $("#addr").val( "");
        $("#addrDtl").val("");

        $("input[name='tableOrder'][value='Y']").prop("checked", false);
        $("input[name='tableOrder'][value='N']").prop("checked", false);
        $("input[name='pickupOrder'][value='Y']").prop("checked", false);
        $("input[name='pickupOrder'][value='N']").prop("checked", false);
    }

}]);