/****************************************************************
 *
 * 파일명 : promotionExample.js
 * 설  명 : 프로모션 예시사례 안내 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.02     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('promotionExampleCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('promotionExampleCtrl', $scope, $http, false));

    // 프로모션 예시사례 안내 팝업 오픈
    $scope.$on("promotionExampleCtrl", function(event, data) {

        // 모두 접힌 상태로 셋팅
        divClose("ex1");
        divClose("ex2");
        divClose("ex3");
        divClose("ex4");
        divClose("ex5");
        divClose("ex6");
        divClose("ex7");
        divClose("ex8");
        divClose("ex9");
        divClose("ex10");
        divClose("ex11");
        divClose("ex12");
        divClose("ex13");
        divClose("ex14");
    });

}]);