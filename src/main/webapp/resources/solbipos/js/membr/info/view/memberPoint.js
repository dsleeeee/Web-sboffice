/****************************************************************
 *
 * 파일명 : memberPoint.js
 * 설  명 : 회원포인트조정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.15    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('memberPointCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPointCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  };

  $scope.adjustAll = function () {
    let param = $scope.changeAll
    param.totAjdPoint = param.totAjdPoint * 1
    $http({
      method: 'POST', //방식
      url: "/membr/info/point/point/adjustAll.sb", /* 통신할 URL */
      params: param, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
    }, function errorCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // 'complete' code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  }

// $scope.$on("memberPointCtrl", function (event, data) {
//   $scope.searchMemberPointList();
//   event.preventDefault();
// });

// // 후불회원 그리드 조회
// $scope.searchMemberPointList = function () {
//   var params = {};
//   $scope._inquiryMain("membr/info/point/point/getMemberPointList.sb", params, function () {
//   }, false);
// };
}])
;