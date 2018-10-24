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
app.controller('storeManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeManageCtrl', $scope, $http, false));

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
        $scope.setSelectedStore(s.rows[ht.row].dataItem);
        if ( col.binding === "storeCd" ||  col.binding === "storeNm") {

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

    $.ajax({
      type: "POST",
      cache: false,
      async: true,
      dataType: "json",
      url: "/store/manage/storeManage/storeManage/getStoreList.sb",
      data: params,
      success: function(result) {
        if(result.status === "OK") {

          // console.log(result);

          $scope.list = result.data.list;
          if(isEmptyObject($scope.list)){
            $scope._popMsg(messages["cmm.empty.data"]);
            $scope._gridDataInit();
            return false;
          }
          $scope.data = new wijmo.collections.CollectionView($scope.list);
        }
        else if(result.status === "FAIL") {
          return fail(result);
        }
        else if(result.status === "SESSION_EXFIRE") {
          s_alert.popOk(result.message, function() {
            location.href = result.url;
          });
        }
        else if(result.status === "SERVER_ERROR") {
          s_alert.pop(result.message);
        }
        else {
          var msg = result.status + " : " + resultmessage;
          alert(msg);
        }
      }
    });
  };

  // 매장 추가 팝업 오픈
  $scope.addStore = function(){

  };

}]);
