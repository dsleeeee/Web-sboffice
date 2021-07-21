/****************************************************************
 *
 * 파일명 : posHwInfo.js
 * 설  명 : 가상로그인 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.13     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 가상로그인 그리드 생성
 */
app.controller('posHwInfoCtrl',  ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posHwInfoCtrl', $scope, $http, true));
  // 가상로그인 개수
  $scope.popupCnt = 0;

  // 접속 사용자의 권한
  $scope.userOrgnFg = gvOrgnFg;

  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchClsFg", clsFg);
  $scope._setComboData("srchStatFg", sysStatFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("posHwInfoCtrl");
  };

  // 가상로그인 그리드 조회
  $scope.$on("posHwInfoCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.orgnFg = orgnFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/store/manage/posHwInfo/posHwInfo/list.sb", params, function() {});
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);
