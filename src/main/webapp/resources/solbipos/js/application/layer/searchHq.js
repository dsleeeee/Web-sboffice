/****************************************************************
 *
 * 파일명 : searchHq.js
 * 설  명 : 본사 조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.01     김지은      1.0
 *
 * **************************************************************/

/**
 *  벤사 조회 그리드
 */
app.controller('searchHqCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('searchHqCtrl', $scope, $http, false));

  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택된 벤사
  $scope.hq;
  $scope.setHq = function(obj) {
    $scope.hq = obj;
  };
  $scope.getHq = function(){
    return $scope.hq;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "hqOfficeCd" || col.binding === "hqOfficeNm") {
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
        if ( col.binding === "hqOfficeCd" || col.binding === "hqOfficeNm") {
          $scope.setHq(selectedRow);
          $scope.hqLayer.hide();
        }
      }
    });
  };

  $scope.$on("searchHqCtrl", function(event, data) {
    $scope.searchHq();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 본사 조회
  $scope.searchHq = function(){

    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/popup/getHqList.sb", params, function() {
    }, false);

  };
}]);
