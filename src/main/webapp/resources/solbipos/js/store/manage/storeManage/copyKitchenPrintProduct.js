/****************************************************************
 *
 * 파일명 : copyKitchenPrintProduct.js
 * 설  명 : 주방프린터상품연결 복사 팝업 JavaScript
 *          기준매장-기준프린터의 출력상품을 현재매장의 선택(체크)한 프린터에 복사(덮어쓰기)
 *
 * **************************************************************/

app.controller('copyKitchenPrintProductCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('copyKitchenPrintProductCtrl', $scope, $http, false));

  // 현재매장 정보 (팝업 오픈 시 broadcast 로 전달받음)
  $scope.curHqOfficeCd = "";
  $scope.curStoreCd    = "";

  // 대상프린터(현재매장) 그리드 초기화 (체크박스)
  $scope.initTargetGrid = function (s, e) {
  };

  // 기준프린터 선택 팝업 그리드 초기화 (프린터번호 링크 → 클릭 시 선택)
  $scope.initOrgPrterGrid = function (s, e) {
    // 전체 컬럼 readonly 효과 + 프린터번호 셀 링크 (기준매장 선택 모듈과 동일)
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        wijmo.addClass(e.cell, 'wj-custom-readonly');
        if (col.binding === "prterNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 프린터번호 클릭 시 선택 → hidden 세팅 후 팝업 닫기
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var row = s.rows[ht.row].dataItem;
        if (col.binding === "prterNo") {
          $("#orgPrterNo").val(row.prterNo);
          $("#orgPrterNm").val("[" + row.prterNo + "] " + nvl(row.prterNm, ""));
          $scope.orgPrterLayer.hide(true);
        }
      }
    });
  };

  // 팝업 오픈 : 현재매장 정보 받아 현재매장 프린터목록 조회
  $scope.$on("copyKitchenPrintProductCtrl", function (event, data) {
    if (data !== undefined && !isEmptyObject(data)) {
      $scope.curHqOfficeCd = data.hqOfficeCd;
      $scope.curStoreCd    = data.storeCd;
    }
    // 초기화
    $("#orgStoreCd").val("");
    $("#orgStoreNm").val(messages["cmm.select"]);
    $("#orgPrterNo").val("");
    $("#orgPrterNm").val(messages["cmm.select"]);

    // 현재매장 프린터목록 조회
    $scope.searchTargetPrter();
    event.preventDefault();
  });

  // 매장선택 모듈(기준매장) 팝업 오픈
  $scope.orgStoreShow = function () {
    $scope._pageView('orgStoreCtrl', 1);
  };

  // 기준프린터 선택 팝업 오픈 : 기준매장의 프린터목록 조회 후 표시
  $scope.orgPrterShow = function () {
    var orgStoreCd = $("#orgStoreCd").val();
    if (isEmptyObject(orgStoreCd)) {
      $scope._popMsg(messages["storeManage.origStore"] + messages["cmm.require.select"]);
      return false;
    }

    // 기준프린터 값 초기화 후 재선택
    $("#orgPrterNo").val("");
    $("#orgPrterNm").val(messages["cmm.select"]);

    var params        = {};
    params.hqOfficeCd = $scope.curHqOfficeCd; // 같은 본사
    params.storeCd    = orgStoreCd;

    $scope._postJSONQuery.withOutPopUp("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params, function (response) {
      var list = (response.data && response.data.data) ? response.data.data.list : [];
      $scope.orgPrterData = new wijmo.collections.CollectionView(list ? list : []);
      $scope.orgPrterLayer.show(true);
    });
  };

  // 기준프린터 선택 팝업 닫기
  $scope.orgPrterClose = function () {
    $scope.orgPrterLayer.hide();
  };

  // 현재매장 프린터목록 조회 (복사 대상, 체크박스)
  $scope.searchTargetPrter = function () {
    var params        = {};
    params.hqOfficeCd = $scope.curHqOfficeCd;
    params.storeCd    = $scope.curStoreCd;

    $scope._postJSONQuery.withOutPopUp("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params, function (response) {
      var list = (response.data && response.data.data) ? response.data.data.list : [];
      var cv = new wijmo.collections.CollectionView(list ? list : []);
      // 체크 초기화
      for (var i = 0; i < cv.items.length; i++) { cv.items[i].gChk = false; }
      $scope.targetData = cv;
    });
  };

  // 복사
  $scope.copy = function () {
    var orgStoreCd = $("#orgStoreCd").val();
    var orgPrterNo = $("#orgPrterNo").val();

    // 기준매장 / 기준프린터 확인
    if (isEmptyObject(orgStoreCd)) {
      $scope._popMsg(messages["storeManage.origStore"] + messages["cmm.require.select"]);
      return false;
    }
    if (isEmptyObject(orgPrterNo)) {
      $scope._popMsg(messages["storeManage.origPrter"] + messages["cmm.require.select"]);
      return false;
    }

    // 대상프린터 체크 목록
    var params = [];
    if ($scope.targetData) {
      for (var i = 0; i < $scope.targetData.items.length; i++) {
        var item = $scope.targetData.items[i];
        if (item.gChk) {
          params.push({
            orgStoreCd : orgStoreCd,        // 기준매장
            orgPrterNo : orgPrterNo,        // 기준프린터
            storeCd    : $scope.curStoreCd, // 대상(현재)매장
            prterNo    : item.prterNo       // 대상프린터
          });
        }
      }
    }

    if (params.length <= 0) {
      $scope._popMsg(messages["storeManage.targetPrter"] + messages["cmm.require.select"]);
      return false;
    }

    // 기준프린터와 대상프린터가 동일(같은 매장+같은 프린터)이면 제외 안내
    for (var j = 0; j < params.length; j++) {
      if (params[j].orgStoreCd === params[j].storeCd && params[j].orgPrterNo === params[j].prterNo) {
        $scope._popMsg(messages["storeManage.copyKitchenPrintProduct.sameChk"]);
        return false;
      }
    }

    var sParam = {};
    if (document.getElementsByName('sessionId')[0]) {
      sParam.sid = document.getElementsByName('sessionId')[0].value;
    }

    $scope._popConfirm(messages["storeManage.copyKitchenPrintProduct.confirm"], function () {

      $scope.$broadcast('loadingPopupActive');
      $http({
        method : 'POST',
        url    : '/store/manage/storeManage/storeManage/copyKitchenPrintProduct.sb',
        data   : params,
        params : sParam,
        headers: {'Content-Type': 'application/json; charset=utf-8'}
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          // 현재매장 프린터목록 재조회(건수 갱신)
          $scope.searchTargetPrter();
          // 부모(주방프린터상품 화면) 갱신
          var parentScope = agrid.getScope('kitchenPrintProductCtrl');
          if (parentScope) { parentScope.getKitchenPrintList(); }
        }
        $scope.$broadcast('loadingPopupInactive');
      }, function errorCallback(response) {
        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(nvl(response.data.message, messages["cmm.saveFail"]));
        return false;
      }).then(function () {
        $scope.$broadcast('loadingPopupInactive');
      });
    });
  };

  // 팝업 닫기
  $scope.close = function () {
    $("#orgStoreCd").val("");
    $("#orgStoreNm").val(messages["cmm.select"]);
    $("#orgPrterNo").val("");
    $("#orgPrterNm").val(messages["cmm.select"]);
    $scope.copyKitchenPrintProductLayer.hide();
  };

}]);
