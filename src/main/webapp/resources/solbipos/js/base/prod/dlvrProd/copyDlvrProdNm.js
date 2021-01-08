/****************************************************************
 *
 * 파일명 : copyDlvrProdNm.js
 * 설  명 : 상품명칭복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.01.06     이다솜      1.0
 *
 * **************************************************************/

app.controller('copyDlvrProdNmCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyDlvrProdNmCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    $scope.$on("copyDlvrProdNmCtrl", function(event, data) {
        //$scope.searchEnvList();
        event.preventDefault();
    });

}]);