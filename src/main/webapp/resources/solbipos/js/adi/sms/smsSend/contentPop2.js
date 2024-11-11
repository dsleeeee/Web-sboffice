/****************************************************************
 *
 * 파일명 : contentPop2.js
 * 설  명 : SMS 광고 및 스팸 문자 정책 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  SMS 광고 및 스팸 문자 정책 팝업 조회 그리드 생성
 */
app.controller('contentPop2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('contentPop2Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("contentPop2Ctrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->


    // 팝업 닫기
    $scope.close = function() {
        $scope.wjContentPop2Layer.hide();
        event.preventDefault();
    };

}]);