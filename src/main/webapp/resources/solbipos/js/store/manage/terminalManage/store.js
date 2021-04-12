/****************************************************************
 *
 * 파일명 : store.js
 * 설  명 : 매장 찾기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.07     김지은      1.0
 *
 * **************************************************************/


/**
 *  매장겁색 레이어
 */
app.controller('storeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeCtrl', $scope, $http, true));

  // 선택 매장
  $scope.selectStore;
  $scope.setSelectedStore = function(s){
    $scope.selectStore = s;
  };
  $scope.getSelectedStore = function(){
    return $scope.selectStore;
  };

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "storeCd" || col.binding === "storeNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd" || col.binding === "storeNm") {
          $scope.setSelectedStore(selectedRow);
          $scope.storeLayer.hide();
        }
      }
    });
  };

  // 조회버튼 클릭
  $scope.$on("storeCtrl", function(event, data) {
    $scope.searchStore();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장 조회
  $scope.searchStore = function(){
    var params = {};
    if (orgnFg != 'HQ') {
      params.hqOfficeCd = $("#srchHqOfficeCd").val();
      params.hqOfficeNm = $("#srchHqOfficeNm").val();
    } else if (orgnFg == 'HQ'){
      params.hqOfficeCd = hqOfficeCd;
    }
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();
    params.clsFg = $scope.clsFg;
    params.sysStatFg = $scope.sysStatFg;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "terminalManage/getStoreList.sb", params, function() {}, false);
  };

}]);

