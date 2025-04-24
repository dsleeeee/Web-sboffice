/****************************************************************
 *
 * 파일명 : searchProdClassCd3.js
 * 설  명 : 상품정보관리 분류선택 팝업3 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.04.18     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */

app.controller('prodClassPopUp3Ctrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassPopUp3Ctrl', $scope, $http, false));

    // 상품분류 선택 정보
    $scope.selectedClass = "";
    $scope.setSelectedClass = function (data) {
        $scope.selectedClass = data;
    };
    $scope.getSelectedClass = function () {
        return $scope.selectedClass;
    };

    $scope.$on('prodClassPopUp3Ctrl', function (event, data) {
    });

}]);