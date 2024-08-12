/****************************************************************
 *
 * 파일명 : basicSideMenuSelectMenuSingle.js
 * 설  명 : (기준)사이드메뉴 > 선택메뉴(싱글) Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.07     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * (기준)사이드메뉴 > 선택메뉴(싱글) Tab 생성
 */
app.controller('basicSideMenuSelectGroupSingleCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('basicSideMenuSelectGroupSingleCtrl', $scope, $http, false));

}]);

app.controller('basicSideMenuSelectClassSingleCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('basicSideMenuSelectClassSingleCtrl', $scope, $http, false));

}]);

app.controller('basicSideMenuSelectProdSingleCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('basicSideMenuSelectProdSingleCtrl', $scope, $http, false));

}]);