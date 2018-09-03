/****************************************************************
 *
 * 파일명 : popUpKind.js
 * 설  명 : 출력물종류 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
// 팝업 그리드 생성
app.controller('printCodeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('printCodeCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("printCodeCtrl");
    //auto size rows
    s.autoSizeRows(0, s.rows.length);
  };
  // 팝업 그리드 조회
  $scope.$on("printCodeCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/bill/item/item/list.sb", params, function() {
      //auto size rows
      $scope.flex.autoSizeRows(0, $scope.flex.rows.length);
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
}]);

