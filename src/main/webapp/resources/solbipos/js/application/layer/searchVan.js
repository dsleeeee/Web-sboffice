/****************************************************************
 *
 * 파일명 : searchVan.js
 * 설  명 : 벤사 조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
// var app = agrid.getApp();

/**
 *  벤사 조회 그리드
 */
app.controller('searchVanCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('searchVanCtrl', $scope, $http, false));

  // 선택된 벤사
  $scope.van;
  $scope.setVan = function(obj) {
    $scope.van = obj;
  };
  $scope.getVan = function(){
    return $scope.van;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vanNm") {
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
        if ( col.binding === "vanNm") {
          $scope.setVan(selectedRow);
          $scope.vanLayer.hide();
        }
      }
    });
  };

  $scope.$on("searchVanCtrl", function(event, data) {
    $scope.searchVan();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 벤사 조회
  $scope.searchVan = function(){

    var params = {};
    params.vanFg = "01"; // 벤사
    params.vanCd = $("#srchVanCd").val();
    params.vanNm = $("#srchVanNm").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/popup/getVanList.sb", params, function() {
    }, false);

  };
}]);
