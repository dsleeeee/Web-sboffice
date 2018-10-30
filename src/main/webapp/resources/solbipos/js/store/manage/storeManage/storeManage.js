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

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd" || col.binding === "storeNm") {
          var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
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
          $scope.list = result.data.list;
          if(isEmptyObject($scope.list)){
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
          }

          $scope.view = new wijmo.collections.CollectionView($scope.list);
          $scope.view.groupDescriptions.push(new wijmo.collections.PropertyGroupDescription('hqOfficeNm'));
          $scope.data = $scope.view;



          for(var i=0; i<$scope.data.itemCount; i++) {
            $scope.storeNm = $scope.data.items[i].storeNm;
            if(isNull($scope.storeNm)){
              // $scope.data.items[i].hqOfficeCd = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].hqOfficeNm = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].storeCd = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].storeNm = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].clsFg = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].sysStatFg = messages["storeManage.require.regist.store2"];
              // $scope.data.items[i].sysOpenDate = messages["storeManage.require.regist.store2"];

              $scope.view.items[i].hqOfficeCd = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].hqOfficeNm = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].storeCd = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].storeNm = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].clsFg = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].sysStatFg = messages["storeManage.require.regist.store2"];
              $scope.view.items[i].sysOpenDate = messages["storeManage.require.regist.store2"];

              // $scope.view.items[i].merge(0,6);
              // $scope.data.items[i].sysOpenDate = messages["storeManage.require.regist.store2"];
            }
          }

          console.log(">> scope");
          console.log($scope)

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
          var msg = result.status + " : " + result.message;
          alert(msg);
        }
      }
    });

  };

  // 매장 추가 버튼 클릭
  $scope.addRow = function(){


  };

}]);
