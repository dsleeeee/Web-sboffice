/****************************************************************
 *
 * 파일명 : kioskKeyMapStore.js
 * 설  명 : 기초관리 > 상품관리2 > 키오스크키맵관리(매장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.01.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *
 */
app.controller('kioskKeyMapStoreCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapStoreCtrl', $scope, $http, false));

    $scope.init = function () {
        // $("#kioskKeyMapRegistView").show();
        // $("#kioskKeyMapCopyView").hide();
        // $("kioskKeyMapCopyTab").css("display", 'none');
    };

}]);

