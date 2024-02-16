/****************************************************************
 *
 * 파일명 : mobileLastLoginHistPop.js
 * 설  명 : 최근접속이력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.16     김설아      1.0
 *
 * **************************************************************/

$(".btn_close").click(function() {
    $("#fullDimmedMobileLastLoginHistPop").hide();
    $("#layerMobileLastLoginHistPop").hide();
});

/**
 *  최근접속이력 그리드 생성
 */
app.controller('mobileLastLoginHistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileLastLoginHistCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 조회
        $scope.searchMobileLastLoginHist();
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileLastLoginHistCtrl", function(event, data) {
        $scope.searchMobileLastLoginHist();
        event.preventDefault();
    });

    $scope.searchMobileLastLoginHist = function(){
        var params = {};

        $scope._inquirySub("/user/getLastLoginHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);