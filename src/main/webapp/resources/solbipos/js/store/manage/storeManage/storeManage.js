/****************************************************************
 *
 * 파일명 : storeManage.js
 * 설  명 : 매장관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장 목록 그리드
 **********************************************************************/
app.controller('storeManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeManageCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function(store) {
    $scope.selectedStore = store;
  };
  $scope.getSelectedStore = function(){
    return $scope.selectedStore;
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
        // var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "storeCd" ||  col.binding === "storeNm") {
          $scope.setSelectedStore(s.rows[ht.row].dataItem);
          var popup = $scope.storeInfoLayer;
          // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
          popup.shown.addHandler(function (s) {
            setTimeout(function() {
              $scope._broadcast('storeInfoCtrl');
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
  $scope.$on("storeManageCtrl", function(event, data) {
    $scope.getStoreList();
    event.preventDefault();
  });

  // 매장목록 조회
  $scope.getStoreList = function(){
    var params = {};
    $scope._inquiryMain("/store/manage/storeManage/storeManage/getStoreList.sb", params, function() {
    });
  };

  // 매장 추가 팝업 오픈
  $scope.addStore = function(){

    $scope.setSelectedStore(null);

    var popup = $scope.storeInfoLayer;
    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeInfoCtrl');
      }, 50)
    });

    // 팝업 닫을때
    popup.show(true, function (s) {
    });

    event.preventDefault();
  };

  // <-- 엑셀다운로드 호출 -->
  $scope.excelDownload = function(){
    var params       = {};

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope._broadcast('storeManageExcelCtrl', params);
  };
  // <-- //엑셀다운로드 호출 -->

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeManageExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeManageExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.areaFgDataMap = new wijmo.grid.DataMap(areaCd, 'value', 'name');
  };

    // <-- 검색 호출 -->
    $scope.$on("storeManageExcelCtrl", function(event, data) {
      $scope.getStoreExcelList();
      event.preventDefault();
    });

    $scope.getStoreExcelList = function(){
      var params = {};

      $scope._inquiryMain("/store/manage/storeManage/storeManage/getStoreExcelList.sb", params, function() {

        if ($scope.excelFlex.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function()	{
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
              {
                includeColumnHeaders: 	true,
                includeCellStyles	: 	false,
                includeColumns      :	function (column) {
                  return column.visible;
                }
              },
              '매장정보관리_'+getToday()+'.xlsx',
              function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                }, 10);
              }
          );
        }, 10);

      });
    };
    // <-- //검색 호출 -->

}]);