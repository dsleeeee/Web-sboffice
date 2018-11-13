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
  angular.extend(this, new RootController('prodCtrl', $scope, $http, false));
  // 상품 상세 정보
  $scope.prodInfo = {};
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };
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
    $scope._inquiryMain("/base/prod/prod/prod/list.sb", params);
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
    var detailPopUp = $scope.prodDetailLayer;
    detailPopUp.show(true, function (s) {
      // 수정 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        // 상품정보 수정 팝업
        var modifyPopUp = $scope.prodModifyLayer;
        modifyPopUp.show(true, function (s) {
          // 상품정보 수정 팝업 - 저장
          if (s.dialogResult === "wj-hide-apply") {
            // 팝업 속성에서 상품정보 get
            var params = s.data;
            // 저장수행
            $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
              $scope._popMsg(messages["cmm.saveSucc"]);
            });
          }
        });
      }
    });
  };

  // 신규상품 등록
  $scope.addProd = function() {
    $scope.setProdInfo({});
    var modifyPopUp = $scope.prodModifyLayer;
    modifyPopUp.show(true, function (s) {
      // 상품정보 등록 팝업 - 저장
      if (s.dialogResult === "wj-hide-apply") {
        // 팝업 속성에서 상품정보 get
        var params = s.data;
        // 저장수행
        $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
          $scope._popMsg(messages["cmm.saveSucc"]);
        });
      }
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품상세정보 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = {};
        params.prodCd = selectedRow.prodCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });
    // 상품상세정보 수정 팝업 핸들러 추가
    $scope.prodModifyLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope.$apply(function() {
          $scope._broadcast('prodModifyCtrl', $scope.getProdInfo());
          // 팝업에 속성 추가 : 상품정보
          s.data = $scope.getProdInfo();
        });
      }, 10);
    });
  });

}]);
