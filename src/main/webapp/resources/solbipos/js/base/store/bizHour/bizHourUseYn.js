/****************************************************************
 * 파일명 : bizHourUseYn.js
 * 설  명 : 매장영업시간관리 사용여부 - 본사 셔틀 팝업 (좌:사용 / 우:미사용)
 *          그리드 2개(각 컨트롤러), 이동 = 체크행 즉시 저장 후 양쪽 재조회
 * **************************************************************/

/** 사용여부 팝업 - 오케스트레이션 (오픈 / 조회) */
app.controller('bizHourUseYnPopCtrl', ['$scope', function ($scope) {

  // 본사 버튼 broadcast 로 팝업 오픈
  $scope.$on('bizHourUseYnPopCtrl', function (event, data) {
    $("#useYnSearchStoreCd").val("");
    $("#useYnSearchStoreNm").val("");
    $scope.wjBizHourUseYnLayer.show(true);
    $scope.searchUseYn();
    event.preventDefault();
  });

  // 조회 : 양쪽 그리드 재조회
  $scope.searchUseYn = function () {
    $scope._broadcast('bizHourUseYnUseCtrl');
    $scope._broadcast('bizHourUseYnUnuseCtrl');
  };

}]);

/** 사용 그리드 */
app.controller('bizHourUseYnUseCtrl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('bizHourUseYnUseCtrl', $scope, $http, false));

  $scope.initGrid = function (s, e) {
    $scope._gridDataInit();
  };

  $scope.$on('bizHourUseYnUseCtrl', function (event, data) {
    $scope.searchList();
    event.preventDefault();
  });

  // 사용(1) 매장 조회
  $scope.searchList = function () {
    var params = {};
    params.envstVal      = '1';
    params.searchStoreCd = $("#useYnSearchStoreCd").val();
    params.searchStoreNm = $("#useYnSearchStoreNm").val();
    params.listScale     = 5000;
    params.curr          = 1;
    $scope._inquirySub(baseUrl + "useYn/list.sb", params);
  };

  // 체크한 매장을 미사용(0)으로 이동 (즉시 저장 후 양쪽 재조회)
  $scope.moveToUnuse = function () {
    var params = [];
    var items  = $scope.flex.collectionView.items;
    for (var i = 0; i < items.length; i++) {
      if (items[i].gChk) {
        params.push({storeCd: items[i].storeCd, envstVal: '0'});
      }
    }
    if (params.length === 0) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }
    $.postJSONArray(baseUrl + "useYn/saveList.sb", params,
      function (result) {
        $scope._broadcast('bizHourUseYnUseCtrl');
        $scope._broadcast('bizHourUseYnUnuseCtrl');
      },
      function (result) {
        s_alert.pop(result.message);
      });
  };

}]);

/** 미사용 그리드 */
app.controller('bizHourUseYnUnuseCtrl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('bizHourUseYnUnuseCtrl', $scope, $http, false));

  $scope.initGrid = function (s, e) {
    $scope._gridDataInit();
  };

  $scope.$on('bizHourUseYnUnuseCtrl', function (event, data) {
    $scope.searchList();
    event.preventDefault();
  });

  // 미사용(0) 매장 조회
  $scope.searchList = function () {
    var params = {};
    params.envstVal      = '0';
    params.searchStoreCd = $("#useYnSearchStoreCd").val();
    params.searchStoreNm = $("#useYnSearchStoreNm").val();
    params.listScale     = 5000;
    params.curr          = 1;
    $scope._inquirySub(baseUrl + "useYn/list.sb", params);
  };

  // 체크한 매장을 사용(1)으로 이동 (즉시 저장 후 양쪽 재조회)
  $scope.moveToUse = function () {
    var params = [];
    var items  = $scope.flex.collectionView.items;
    for (var i = 0; i < items.length; i++) {
      if (items[i].gChk) {
        params.push({storeCd: items[i].storeCd, envstVal: '1'});
      }
    }
    if (params.length === 0) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }
    $.postJSONArray(baseUrl + "useYn/saveList.sb", params,
      function (result) {
        $scope._broadcast('bizHourUseYnUseCtrl');
        $scope._broadcast('bizHourUseYnUnuseCtrl');
      },
      function (result) {
        s_alert.pop(result.message);
      });
  };

}]);
