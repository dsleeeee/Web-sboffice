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
  angular.extend(this, new RootController('printCodeCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

  };
  // 그리드 Row 사이즈 정렬
  $scope.autoSizeVisibleRows = function(flex) {
    for ( var r = 0; r < flex.itemsSource.itemCount; r++ ) {
      if(flex.rows[r]._data.content && flex.rows[r]._data.content.split("\n").length > 1 ) {
        flex.autoSizeRow(r, false);
      }
    }
  };
  // 팝업 그리드 조회
  $scope.$on("printCodeCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.prtClassCd = data.prtClassCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/bill/kind/mapng/unUsedList.sb", params, function() {
      //auto size rows
      $scope.autoSizeVisibleRows($scope.flex);
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
}]);

