/****************************************************************
 *
 * 파일명 : sideMenuSelectMenu.js
 * 설  명 : 사이드메뉴>선택메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴 선택그룹 그리드 생성
 */
app.controller('sideMenuSelectGroupCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectGroupCtrl', $scope, $http, false));
  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselGrpCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 선택그룹 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselGrpCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 선택그룹 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdselGrpCd' && selectedRow.status !== 'I') {
          $scope._broadcast('sideMenuSelectClassCtrl', selectedRow.sdselGrpCd);
        }
      }
    });
  };
  // 선택그룹 그리드 조회
  $scope.$on('sideMenuSelectGroupCtrl', function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuGrp/list.sb', params, function() {
      $('#btnAddSelGroup').show();
      $('#btnDelSelGroup').show();
      $('#btnSaveSelGroup').show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 선택그룹 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = 'I';
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = [];
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = 'U';
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = 'I';
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = 'D';
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
      // 저장 후 그리드 재조회
      $scope._broadcast("sideMenuSelectGroupCtrl");
    });
  }

}]);

/**
 * 사이드메뉴 선택분류 그리드 생성
 */
app.controller('sideMenuSelectClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectClassCtrl', $scope, $http, false));
  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });
  // sdselGrpCd Data Setter
  $scope.setSdselGrpCd = function (value) {
    sdselGrpCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselGrpCd = function () {
    return sdselGrpCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselClassCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 선택분류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselClassCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 선택분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdselClassCd' && selectedRow.status !== 'I') {
          $scope._broadcast('sideMenuSelectProdCtrl', selectedRow.sdselClassCd);
        }
      }
    });
  };
  // 선택분류 그리드 조회
  $scope.$on('sideMenuSelectClassCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselGrpCd(data);
    // 파라미터
    var params = {};
    params.sdselGrpCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuClass/list.sb', params, function() {
      $('#btnUpSelClass').show();
      $('#btnDownSelClass').show();
      $('#btnAddSelClass').show();
      $('#btnDelSelClass').show();
      $('#btnSaveSelClass').show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 선택분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselQty = '0';
    params.status = 'I';
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 저장
  $scope.save = function() {

    // dispSeq 재설정
    for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
      $scope.flex.collectionView.items[s].dispSeq = (s + 1);
      $scope.flex.collectionView.commitEdit();
    }

    // 파라미터 설정
    var params = [];
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = 'U';
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = 'I';
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = 'D';
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {
      // 그리드 저장 후 재조회
      $scope._broadcast('sideMenuSelectClassCtrl', $scope.getSdselGrpCd());
    });
  };
  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
}]).factory('sdselGrpCd', function () {
  // 사이드메뉴 선택분류 그리드 의 변수 값 영역
  var sdselGrpCd = {};
  sdselGrpCd.set = function (value) {
    sdselGrpCd.value = value;
  };
  sdselGrpCd.get = function () {
    return sdselGrpCd.value;
  };
  return sdselGrpCd;
});

/**
 * 사이드메뉴 선택상품 그리드 생성
 */
app.controller('sideMenuSelectProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectProdCtrl', $scope, $http, false));
  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });
  // sdselGrpCd Data Setter
  $scope.setSdselClassCd = function (value) {
    sdselClassCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselClassCd = function () {
    return sdselClassCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'prodCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 선택상품 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'prodCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
  };
  // 선택상품 그리드 조회
  $scope.$on('sideMenuSelectProdCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselClassCd(data);
    // 파라미터
    var params = {};
    params.sdselClassCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuProd/list.sb', params, function() {
      $('#btnUpSelProd').show();
      $('#btnDownSelProd').show();
      $('#btnAddSelProd').show();
      $('#btnDelSelProd').show();
      $('#btnSaveSelProd').show();
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 선택상품 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.sdselClassCd = $scope.getSdselClassCd();
    params.status = 'I';
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 저장
  $scope.save = function() {

    // dispSeq 재설정
    for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
      $scope.flex.collectionView.items[s].dispSeq = (s + 1);
      $scope.flex.collectionView.commitEdit();
    }

    // 파라미터 설정
    var params = [];
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = 'U';
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = 'I';
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = 'D';
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {
      // 그리드 저장 후 재조회
      $scope._broadcast('sideMenuSelectProdCtrl', $scope.getSdselClassCd());
    });
  };
  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
}]).factory('sdselClassCd', function () {
  // 사이드메뉴 선택상품 그리드 의 변수 값 영역
  var sdselClassCd = {};
  sdselClassCd.set = function (value) {
    sdselClassCd.value = value;
  };
  sdselClassCd.get = function () {
    return sdselClassCd.value;
  };
  return sdselClassCd;
});
