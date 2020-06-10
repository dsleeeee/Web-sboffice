/****************************************************************
 *
 * 파일명 : incln.js
 * 설  명 : 회원 구매성향 분석 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.10     Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 구매성향 분석 그리드 생성
 */
app.controller('inclnCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('inclnCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // <-- 검색 호출 -->
  $scope.$on("inclnCtrl", function(event, data) {
    $scope.searchMembrPoint();
    event.preventDefault();
  });

  // 회원 포인트실적 그리드 조회
  $scope.searchMembrPoint = function(){
    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
    params.storeCds = $("#storeCd").val();

    $scope._inquiryMain("/membr/anals/incln/incln/getInclnList.sb", params, function() {}, false);
  };
  // <-- //검색 호출 -->

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

}]);