/****************************************************************
 *
 * 파일명 : hqManage.js
 * 설  명 : 본사관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.06     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  본사 목록 그리드
 **********************************************************************/
app.controller('hqManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqManageCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);

  // 선택 매장
  $scope.selectedHq;
  $scope.setSelectedHq = function(hq) {
    $scope.selectedHq = hq;
  };
  $scope.getSelectedHq  = function(){
    return $scope.selectedHq;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.areaFgDataMap = new wijmo.grid.DataMap(areaCd, 'value', 'name');

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
        if ( col.binding === "hqOfficeCd" ||  col.binding === "hqOfficeNm") {
          $scope.setSelectedHq(s.rows[ht.row].dataItem);
          var popup = $scope.hqInfoLayer;
          // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
          popup.shown.addHandler(function (s) {
            setTimeout(function() {
              $scope._broadcast('hqInfoCtrl');
            }, 50)
          });

          // 팝업 닫을때
          popup.show(true, function (s) {
          });
          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭 (_broadcast)
  $scope.$on("hqManageCtrl", function(event, data) {
    $scope.getHqList();
    event.preventDefault();
  });

  // 매장목록 조회
  $scope.getHqList = function(){
    var params = {};
    $scope._inquiryMain("/store/hq/hqManage/hqManage/getHqList.sb", params, function() {
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 팝업 핸들러 추가
    $scope.hqInfoLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('hqInfoCtrl');
      }, 50)
    });
  });

  // 본사 신규 등록
  $scope.regist = function(){
    $scope.setSelectedHq(null);
    $scope.hqInfoLayer.show(true, function (s) {
    });
  };
}]);
