/**
 * get application
 */
var app = agrid.getApp();

/** 배송기사 그리드 controller */
app.controller('dlvrChgrListCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrChgrListCtrl', $scope, $http, true));

  // 그리드 DataMap 설정
  $scope.useYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["deliveryCharger.useYnY"]},
    {id: "N", name: messages["deliveryCharger.useYnN"]},
  ], 'id', 'name');

  $scope.hqOfficeCd = gvHqOfficeCd;

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dlvrChgrListCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "dlvrCd") {
          var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "dlvrCd") {
          var params    = {};
          params.dlvrCd = selectedRow.dlvrCd;
          params.dlvrNm = selectedRow.dlvrNm;
          params.hqOfficeCd = $scope.hqOfficeCd;
          $scope._broadcast('dlvrRegistCtrl', params);
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dlvrChgrListCtrl", function (event, data) {
    $scope.searchDlvrChgrList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchDlvrChgrList = function () {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList/list.sb", params);
  };

  // 신규 등록
  $scope.openPopNewRegist = function () {
    var params = {};
    params.dlvrCd = '';
    params.dlvrNm = '';
    params.hqOfficeCd = $scope.hqOfficeCd;
    $scope._broadcast('dlvrRegistCtrl', params);
  }
}]);
