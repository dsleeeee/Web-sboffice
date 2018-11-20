/****************************************************************
 *
 * 파일명 : sideMenuProdView.js
 * 설  명 : 사이드메뉴 상품선택 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.20    노현수      1.0
 *
 * **************************************************************/


/**
 *  사이드메뉴 상품선택 그리드 생성
 */
app.controller('sideMenuProdCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuProdCtrl', $scope, $http, false));
  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

  };

  // 사이드메뉴 상품선택 그리드 조회
  $scope.$on("sideMenuProdCtrl", function(event, data) {
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuProd/getProdList.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);

