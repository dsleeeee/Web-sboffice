/****************************************************************
 *
 * 파일명 : popUpTouchKeyApplyStore.js
 * 설  명 : 출력물샘플 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.28     노현수      1.0
 *
 * **************************************************************/
// 팝업 그리드 생성
app.controller('popUpApplyStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('popUpApplyStoreCtrl', $scope, $http, false));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFgCombo", sysStatFgComboData);
  $scope._setComboData("srchClsFgCombo", clsFgComboData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

  };
  // 팝업 그리드 조회
  $scope.$on("popUpApplyStoreCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/touchKey/touchKey/storeList.sb", params, function() {

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
}]);

