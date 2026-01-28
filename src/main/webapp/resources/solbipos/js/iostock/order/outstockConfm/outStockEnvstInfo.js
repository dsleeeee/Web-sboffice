/****************************************************************
 *
 * 파일명 : outStockEnvstInfo.js
 * 설  명 : 수불관리 > 수주관리 > 출고확정 > 수발주설정정보 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.28     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 수발주설정환경 그리드 controller */
app.controller('outStockEnvstInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout){
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('outStockEnvstInfoCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("outStockEnvstInfoCtrl", function (event, data) {

        $scope.wjOutStockEnvstInfoLayer.show(true);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

}]);