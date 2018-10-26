/****************************************************************
 *
 * 파일명 : searchAgency.js
 * 설  명 : 대리점 조회 JavaScript
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
app.controller('searchAgencyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('searchAgencyCtrl', $scope, $http, false));

  // 선택된 벤사
  $scope.agency;
  $scope.setAgency = function(obj) {
    $scope.agency = obj;
  };
  $scope.getAgency = function(){
    return $scope.agency;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "agencyNm") {
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
        if ( col.binding === "agencyNm") {
          $scope.setAgency(selectedRow);
          $scope.agencyLayer.hide();
        }
      }
    });
  };

  $scope.$on("searchAgencyCtrl", function(event, data) {
    console.log("ononon")
    $scope.searchAgency();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 대리점 조회
  $scope.searchAgency = function(){

    var params = {};
    params.agencyCd = $("#srchAgencyCd").val();
    params.agencyNm = $("#srchAgencyNm").val();

    console.log(">> searchAgency");

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/popup/getAgencyList.sb", params, function() {
    }, false);

  };
}]);
