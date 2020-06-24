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
app.controller('dlvrCtrl', ['$scope', '$http', function ($scope, $http) {
  // 회월 듣급

  $scope.classList = [
    {value: '1', name: '전체'},
    {value: '2', name: '기본등급'},
    {value: '3', name: '중간등급'},
    {value: '4', name: 'VIP'},
    {value: '5', name: '특별등급'},
    {value: '6', name: '미친등급'}
  ]
  $scope.memberClass = $scope.classList[0]

  $scope.areaList = [
    {value: '1', name: '전체'},
    {value: '2', name: '구로동'},
    {value: '3', name: '신림동'},
    {value: '4', name: '청담동'}
  ]
  $scope.dlArea = $scope.areaList[0]

  $scope.useYnList = [
    {value: '1', name: '전체'},
    {value: '2', name: 'Y'},
    {value: '3', name: 'N'},
  ]
  $scope.useYn = $scope.useYnList[0]

  $scope.phoneUseYnList = [
    {value: '1', name: '전체'},
    {value: '2', name: 'Y'},
    {value: '3', name: 'N'},
  ]
  $scope.phoneUseYn = $scope.phoneUseYnList[0]

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  };

  $scope.adjustAll = function () {
    // $http({
    //   method: 'POST', //방식
    //   url: "/membr/info/point/point/adjustAll.sb", /* 통신할 URL */
    //   params: param, /* 파라메터로 보낼 데이터 */
    //   headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    // }).then(function successCallback(response) {
    // }, function errorCallback(response) {
    //   // 로딩바 hide
    //   $scope.$broadcast('loadingPopupInactive');
    //   // called asynchronously if an error occurs
    //   // or server returns response with an error status.
    //   if (response.data.message) {
    //     $scope._popMsg(response.data.message);
    //   } else {
    //     $scope._popMsg(messages['cmm.error']);
    //   }
    //   return false;
    // }).then(function () {
    //   // 'complete' code here
    //   if (typeof callback === 'function') {
    //     setTimeout(function () {
    //       callback();
    //     }, 10);
    //   }
    // });
  }

// $scope.$on("dlvrCtrl", function (event, data) {
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