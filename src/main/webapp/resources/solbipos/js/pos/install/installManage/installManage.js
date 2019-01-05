/****************************************************************
 *
 * 파일명 : installManage.js
 * 설  명 : 설치관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.02     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  설치업체 화면
 **********************************************************************/
app.controller('installManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('installManageCtrl', $scope, $http, true));

  // 조회 버튼 클릭 (_broadcast)
  $scope.$on("installManageCtrl", function(event, data) {
    $scope.changeTab("REG"); // 설치요청 탭 선택
    event.preventDefault();
  });

  // 설치요청 탭
  $scope.isReqTab = true;
  // 업체현황 탭
  $scope.isCorpStatusTab = false;
  // 운영현황 탭
  $scope.isOperStatusTab = false;

  // 탭변경
  $scope.changeTab = function(type){
    // 설치요청 탭
    if ( type === 'REQ' ) {
      $("#reqTab").addClass("on");
      $("#corpStatusTab").removeClass("on");
      $("#operStatusTab").removeClass("on");
      $scope.isReqTab = true;
      $scope.isCorpStatusTab = false;
      $scope.isOperStatusTab = false;
      // 설치요청 조회
      // $scope._broadcast("sideMenuAttrClassCtrl");
    }
    // 업체현황 탭
    else if ( type === 'CS' ) {

      $scope._popMsg("서비스 준비중입니다.");
      return false;

      $("#reqTab").removeClass("on");
      $("#corpStatusTab").addClass("on");
      $("#operStatusTab").removeClass("on");
      $scope.isReqTab = true;
      $scope.isCorpStatusTab = false;
      $scope.isOperStatusTab = false;
      // 업체현황 조회
      // $scope._broadcast("sideMenuSelectGroupCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }
    // 운영현황 탭
    else if ( type === 'OS' ) {

      $scope._popMsg("서비스 준비중입니다.");
      return false;

      $("#reqTab").removeClass("on");
      $("#corpStatusTab").removeClass("on");
      $("#operStatusTab").addClass("on");

      $scope.isReqTab = true;
      $scope.isCorpStatusTab = false;
      $scope.isOperStatusTab = false;
      // // 운영현황 조회
      // $scope._broadcast("sideMenuSelectGroupCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }
  };

}]);
