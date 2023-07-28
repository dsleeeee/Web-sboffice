/****************************************************************
 *
 * 파일명 : tableOrderKeyMapTab.js
 * 설  명 : 테이블오더키맵관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('tableOrderKeyMapTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#tableOrderKeyMapRegistView").show();
    };

    // 테이블오더키맵등록 탭 보이기
    $scope.tableOrderKeyMapRegistShow = function () {
        $("#tableOrderKeyMapRegistTab").addClass("on");

        $("#tableOrderKeyMapRegistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("tableOrderKeyMapRegistCtrl");
        scope.flex.refresh();
    };

}]);