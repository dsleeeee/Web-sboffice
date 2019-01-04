/**
 * get application
 */
var app = agrid.getApp();

/** 여신현황 그리드 controller */
app.controller('loanInfoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('loanInfoCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope._setComboData("srchDateFg", [
    {"name": messages["loanInfo.all"], "value": ""},
    {"name": messages["loanInfo.searchDate"], "value": "date"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("loanInfoCtrl");

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

  // 리스트 조회
  $scope.searchLoanInfo = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/loan/loanInfo/loanInfo/list.sb", params);
  };

  // 조회일자 값 변경 이벤트 함수
  $scope.selectedIndexChanged = function (s, e) {
    if (s.selectedValue === "") {
      $scope.dateLayer = false;
    } else {
      $scope.dateLayer = true;
    }
  };

}]);
