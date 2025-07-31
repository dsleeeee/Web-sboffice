/****************************************************************
 *
 * 파일명 : dcDetailMrpizzaAllStore.js
 * 설  명 : 미스터피자 > 마케팅조회 > 할인세부내역 > 할인구분 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.30    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  할인구분 탭 조회 그리드 생성
 */
app.controller('dcDetailMrpizzaDcTypeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dcDetailMrpizzaDcTypeCtrl', $scope, $http, true));

}]);