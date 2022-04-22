/****************************************************************
 *
 * 파일명 : closeStore.js
 * 설  명 : 폐점예정매장 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.22     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 그리드 생성
 */
app.controller('closeStoreCtrl',  ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('closeStoreCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startMonth = new wijmo.input.InputDate('#startMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });

  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchStatFg", sysStatFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("closeStoreCtrl");
  };

  // 가상로그인 그리드 조회
  $scope.$on("closeStoreCtrl", function(event, data) {
    $scope.closeStoreList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.closeStoreList = function (){
    // 파라미터
    var params = {};
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
    params.orgnFg = orgnFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/store/manage/closeStore/closeStore/list.sb", params, function() {});
  };

  $scope.closeStore = function (){

    // 파라미터 설정
    var params = new Array();
    var param = {};
    param.remark = $scope.remark;
    params.push(param);

    $scope._save("/store/manage/closeStore/closeStore/saveCloseStore.sb", params, function (){
      $scope.closeStoreList();
    });
  };

}]);
