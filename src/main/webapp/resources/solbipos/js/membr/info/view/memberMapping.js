/****************************************************************
 *
 * 파일명 : memberMapping.js
 * 설  명 : 회원 매핑코드 조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.10     김지은      1.0
 *
 * **************************************************************/

/**
 *  거래처매핑 조회 그리드
 */
app.controller('memberMappingCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberMappingCtrl', $scope, $http, false));

  // 선택된 거래처
  $scope.company;
  $scope.setCompany = function(obj) {
    $scope.company = obj;
  };
  $scope.getCompany = function(){
    return $scope.company;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "cdPartner" || col.binding === "lnPartner") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 거래처 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "cdPartner" || col.binding === "lnPartner") {
          console.log('selectedRow', selectedRow);

          $scope.setCompany(selectedRow);
          $scope._gridDataInit();
          $scope.memberMappingLayer.hide();
        }
      }
    });
  };

  $scope.$on("memberMappingCtrl", function(event, data) {
    $scope.setCompany(null);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매핑코드 조회 조회
  $scope.searchMappingCd = function(){

    var params = {};
    params.cdCompany = $("#srchCdCompany").val();
    params.cdPartner = $("#srchCdPartner").val();
    params.lnPartner = $("#srchLnPartner").val();
    params.noCompany = $("#srchNoCompany").val();
    params.nmCeo = $("#srchNmCeo").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/membr/info/view/mapping/getMappingCompany.sb", params, function() {
    }, false);

  };
}]);
