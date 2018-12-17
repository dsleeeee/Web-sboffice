/****************************************************************
 *
 * 파일명 : vanCard.js
 * 설  명 : Van/Card사 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * VAN사 그리드 생성
 */
app.controller('vanCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vanCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vanCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // VAN사 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "vanCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
    // VAN사 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem
        var col = ht.panel.columns[ht.col];
        if( col.binding === "vanCd" && selectedRow.status !== "I") {
          $scope._broadcast('vanCardCtrl', selectedRow.vanCd);
        }
      }
    });
  };
  // VAN사 그리드 조회
  $scope.$on("vanCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/etc/vanCard/vanCard/van/list.sb", params, function() {
      // 버튼 Show
      $("#btnAddVan").show();
      $("#btnDelVan").show();
      $("#btnSaveVan").show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // VAN사 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.vanFg = "01";
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // VAN사 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/etc/vanCard/vanCard/van/save.sb", params, function() {
      // 저장기능 수행 후 재조회
      $scope._broadcast("vanCtrl");
    });
  }
}]);


/**
 * VAN/CARD사 매핑 그리드 생성
 */
app.controller('vanCardCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vanCardCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vanCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // VAN/CARD사 매핑 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "vanCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
    // VAN/CARD사 매핑 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem
        var col = ht.panel.columns[ht.col];
        if( col.binding === "cardcoCd" && selectedRow.status !== "I") {
          $scope._broadcast(' ', selectedRow.vanCd);
        }
      }
    });
  };
  // VAN/CARD사 매핑 그리드 조회
  $scope.$on("vanCardCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.vanCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/etc/vanCard/vanCard/mapng/list.sb", params, function() {
      // 버튼 Show
      $("#btnAddMapng").show();
      $("#btnDelMapng").show();
      $("#btnSaveMapng").show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // VAN/CARD사 매핑 그리드 행 추가
  $scope.addRow = function() {
    var scope = agrid.getScope("vanCtrl");
    var selectedRow = scope.flex.selectedRows[0]._data;

    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.vanCd = selectedRow.vanCd;
    params.vanNm = selectedRow.vanNm;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // VAN/CARD사 매핑 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/etc/vanCard/vanCard/mapng/save.sb", params, function() {
      // 저장기능 수행 후 재조회
      $scope._broadcast("vanCardCtrl");
    });
  }
}]);


/**
 * CARD사 그리드 생성
 */
app.controller('cardCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cardCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "cardcoCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // CARD사 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "cardcoCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
    // CARD사 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem
        var col = ht.panel.columns[ht.col];
        if( col.binding === "cardcoCd" && selectedRow.status !== "I") {
          $scope._broadcast('vanCardCtrl', selectedRow.vanCd);
        }
      }
    });
  };
  // CARD사 그리드 조회
  $scope.$on("cardCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/etc/vanCard/vanCard/card/list.sb", params, function() {
      // 버튼 Show
      $("#btnAddCard").show();
      $("#btnDelCard").show();
      $("#btnSaveCard").show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // CARD사 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // CARD사 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/etc/vanCard/vanCard/card/save.sb", params, function() {
      // 저장기능 수행 후 재조회
      $scope._broadcast("cardCtrl");
    });
  }
}]);


