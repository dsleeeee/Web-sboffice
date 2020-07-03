/****************************************************************
 *
 * 파일명 : dlvr.js
 * 설  명 : 배달지조회및 변경 JavaScript
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

  // // grid 초기화 : 생성되기전 초기화되면서 생성된다
  // $scope.initGrid = function (s, e) {
  //   // 그리드 DataMap 설정
  //   $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  // };

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

// grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  $scope.$on("dlvrCtrl", function (event, data) {
    $scope.searchDlvrList();
    event.preventDefault();
  });

// 후불회원 그리드 조회
  $scope.searchDlvrList = function () {
    var params = {};
    $scope._inquiryMain("/membr/info/dlvr/dlvr/getDlvrList.sb", params, function () {
    }, false);
  };
}]);