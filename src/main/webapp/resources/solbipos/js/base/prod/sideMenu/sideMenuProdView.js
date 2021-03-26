/****************************************************************
 *
 * 파일명 : sideMenuProdView.js
 * 설  명 : 사이드메뉴 상품선택 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.20    노현수      1.0
 *
 * **************************************************************/


/**
 *  사이드메뉴 상품선택 그리드 생성
 */
app.controller('sideMenuProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuProdCtrl', $scope, $http, false));
  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  // 상품 선택여부
  $scope.itemChecked = false;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        // 체크박스 클릭시
        if (col.binding === 'gChk' && s.rows[ht.row].dataItem.gChk ) {
          $scope.itemChecked = true;
        } else {
          $scope.itemChecked = false;
        }
      }
    });
  };

  // 사이드메뉴 상품선택 그리드 조회
  $scope.$on("sideMenuProdCtrl", function(event, data) {
    $("#lblsdselClassCd").text(data);
    var params = {};
    params.sdselClassCd = $("#lblsdselClassCd").text();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuProd/getProdList.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품선택버튼 클릭
  $scope.selProdConfirm = function() {
    var idx = 0;
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if ($scope.flex.collectionView.items[i].gChk) {
        idx++;
      }
    }
    if ( idx < 1 ) {
      $scope._popMsg('상품을 선택해주세요.');
      return false;
    }
  }

}]);

