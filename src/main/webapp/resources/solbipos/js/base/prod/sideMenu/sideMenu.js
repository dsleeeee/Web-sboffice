/****************************************************************
 *
 * 파일명 : sideMenuAttr.js
 * 설  명 : 사이드메뉴>속성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사이드메뉴 그리드 생성
 */
app.controller('sideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuCtrl', $scope, $http, false));
  // 속성 탭
  $scope.isAttrTab = false;
  // 선택메뉴 탭
  $scope.isMenuTab = true;
  // 탭변경
  $scope.changeTab = function(type){
    // 속성 탭
    if ( type === 'A' ) {
      $("#sideMenuAttr").addClass("on");
      $("#sideMenuSelectMenu").removeClass("on");
      $scope.isAttrTab = false;
      $scope.isMenuTab = true;
      // 속성 조회
      $scope._broadcast("sideMenuAttrClassCtrl");
    // 선택메뉴 탭
    } else if ( type === 'M' ) {
      $("#sideMenuAttr").removeClass("on");
      $("#sideMenuSelectMenu").addClass("on");
      $scope.isAttrTab = true;
      $scope.isMenuTab = false;
      // 선택그룹 조회
      $scope._broadcast("sideMenuSelectGroupCtrl");
      // 그리드 refresh
      setTimeout(function () {
        $scope._broadcast("selectMenuRefresh");
      }, 10);
    }
  };
  // 탭 조회
  $scope.queryTab = function() {
    if ( $scope.isMenuTab ) {
      // 속성 조회
      $scope._broadcast("sideMenuAttrClassCtrl");
    } else {
      // 선택그룹 조회
      $scope._broadcast("sideMenuSelectGroupCtrl");
    }
  };

}]);

