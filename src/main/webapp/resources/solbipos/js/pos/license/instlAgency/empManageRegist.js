/****************************************************************
 *
 * 파일명 : systemEmpSave.js
 * 설  명 : 사원정보 등록 및 수정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.26     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

/**
 * 매장사원 등록 및 수정
 */
app.controller('empManageRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empManageRegistCtrl', $scope, $http, false));

    // 해당 scope 호출
    $scope.$on("empManageRegistCtrl", function(event, data) {

    });

}]);