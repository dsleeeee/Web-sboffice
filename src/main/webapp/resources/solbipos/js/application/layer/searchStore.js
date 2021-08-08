/****************************************************************
 *
 * 파일명 : searchStore.js
 * 설  명 : 매장 조회 JavaScript(시스템에서 사용할 매장조회-세션에 본사정보X)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.03     권지현      1.0
 *
 * **************************************************************/

/**
 *  벤사 조회 그리드
 */
app.controller('searchStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('searchStoreCtrl', $scope, $http, false));

  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택된 매장
  $scope.store;
  $scope.setStore = function(obj) {
    $scope.store = obj;
  };
  $scope.getStore = function(){
    return $scope.store;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd" || col.binding === "storeNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 매장 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd" || col.binding === "storeNm") {
          $scope.setStore(selectedRow);
          $scope.storeLayer.hide();
        }
      }
    });
  };

  $scope.$on("searchStoreCtrl", function(event, data) {
    //$scope._setPagingInfo('curr', '1')
    $scope.searchStore();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장 조회
  $scope.searchStore = function(){

    var params = {};
    params.listScale = $scope.listScale;
    var scope = agrid.getScope('storeInfoCtrl');
    params.hqOfficeCd = scope.getEnvHqOfficeCdVal();
    params.storeCd = $scope.storeCd;
    params.storeNm = $scope.storeNm;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/popup/getStoreUnSesstionList.sb", params, function() {
    }, false);

  };
}]);
