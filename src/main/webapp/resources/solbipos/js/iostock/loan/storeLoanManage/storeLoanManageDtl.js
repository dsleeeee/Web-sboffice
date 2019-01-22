
/** 여신상세현황 그리드 controller */
app.controller('storeLoanManageDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeLoanManageDtlCtrl', $scope, $http, true));

  var date      = new Date();
  var year      = date.getFullYear();
  var month     = date.getMonth();
  var day       = date.getDate();
  month         = (month.length == 1 ? "0" : "") + month;
  var startDate = "" + year + month + day;

  $scope.srchStartDate = wcombo.genDateVal("#srchDtlStartDate", startDate);
  $scope.srchEndDate   = wcombo.genDate("#srchDtlEndDate");

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeLoanManageDtlCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeLoanManageDtlCtrl", function (event, data) {
    $scope.storeCd = data.storeCd;
    $scope.storeNm = data.storeNm;

    $scope.wjStoreLoanManageDtlLayer.show(true);
    $("#spanDtlTitle").html('[' + $scope.storeCd + '] ' + $scope.storeNm);

    $scope.searchStoreLoanManageDtl();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.searchStoreLoanManageDtl = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/loan/storeLoanManage/storeLoanManageDtl/list.sb", params);
  };

}]);
