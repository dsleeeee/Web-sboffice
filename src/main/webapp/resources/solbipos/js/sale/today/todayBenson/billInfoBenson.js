/****************************************************************
 *
 * 파일명 : billInfoBenson.js
 * 설  명 : (벤슨) 영수증 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/**
 *  영수증 조회 팝업 생성
 */
app.controller('billInfoBensonCtrl', ['$scope', '$http', '$timeout', '$sce', function ($scope, $http, $timeout, $sce) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('billInfoBensonCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    $scope.$on("billInfoBensonCtrl", function(event, data) {

        $scope.wjBillInfoBensonLayer.show(true);

        // 영수증 출력 데이터 조회
        $scope.getBillPrintData(data);
        event.preventDefault();
    });

    // 영수증 출력 데이터 조회
    $scope.getBillPrintData = function (data) {

        var params = {};
        params.storeCd  = data.storeCd;
        params.saleDate = data.saleDate;
        params.posNo    = data.posNo;
        params.billNo   = data.billNo;

        $scope._postJSONQuery.withOutPopUp("/sale/today/todayBenson/todayBenson/getBillPrintData.sb", params, function (response) {

            var result = response.data.data;

            // 영수증 출력데이터를 신뢰된 HTML로 변환하여 바인딩
            $scope.printData = $sce.trustAsHtml(nvl(result && result.printData, ''));
        });
    };

    // 팝업 닫기
    $scope.close = function () {
        $scope.printData = '';
        $scope.wjBillInfoBensonLayer.hide();
    };

}]);