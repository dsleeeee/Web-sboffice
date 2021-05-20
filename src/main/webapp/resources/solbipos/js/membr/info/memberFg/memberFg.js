/****************************************************************
 *
 * 파일명 : memberFg.js
 * 설  명 : 회원정보 > 회원정보 > 선불/후불회원 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.12     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 선불/후불 회원 관리 화면
 */
app.controller('memberFgCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberFgCtrl', $scope, $http, false));

  // 선불 탭
  $scope.isPrepaidTab = false;
  // 후불 탭
  $scope.isPostpaidTab = true;

  // 탭변경
  $scope.changeTab = function(type){
    if ( type === 'Pr' ) {  // 선불
      $("#memberPrepaid").addClass("on");
      $("#memberPostpaid").removeClass("on");
      $("#memberPrepaidArea").show();
      $("#memberPostpaidArea").hide();
      $scope.isPrepaidTab = false;
      $scope.isPostpaidTab = true;
      var scope = agrid.getScope('memberPrepaidCtrl');
      scope._gridDataInit();
    } else if ( type === 'Po' ) {  // 후불
      $("#memberPrepaid").removeClass("on");
      $("#memberPostpaid").addClass("on");
      $("#memberPrepaidArea").hide();
      $("#memberPostpaidArea").show();
      $scope.isPrepaidTab = true;
      $scope.isPostpaidTab = false;
      var scope = agrid.getScope('memberPostpaidCtrl');
      scope._gridDataInit();
    }
  };
}]);

