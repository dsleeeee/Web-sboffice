/****************************************************************
 *
 * 파일명 : prod.js
 * 설  명 : 상품정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 상품정보관리 그리드 생성
 */
app.controller('prodCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodCtrl', $scope, $http, true));
  // 전체기간 체크박스
  $scope.isChecked = true;
  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodCtrl");
    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "prodCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if( col.binding === "prodCd") {
          $scope.searchProdDetail(selectedRow.prodCd);
        }
      }
    });

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;

  };
  // 상품정보관리 그리드 조회
  $scope.$on("prodCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prod/prod/list.sb", params, function() {

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
  };

  // 상세정보 팝업
  $scope.searchProdDetail = function(prodCd) {
    var popup = $scope.prodDetailLayer;
    popup.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (popup.dialogResult === "wj-hide-apply") {

      }
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
      setTimeout(function() {
        var params = {};
        params.prodCd = selectedRow.prodCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });
  });

}]);
