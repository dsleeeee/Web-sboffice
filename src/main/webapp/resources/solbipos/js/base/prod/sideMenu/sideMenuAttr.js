/****************************************************************
 *
 * 파일명 : sideMenuAttr.js
 * 설  명 : 사이드메뉴>속성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴 속성분류 그리드 생성
 */
app.controller('sideMenuAttrClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuAttrClassCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdattrClassCd') {
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
    // 속성분류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdattrClassCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 속성분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdattrClassCd' && selectedRow.status !== 'I') {
          $scope._broadcast('sideMenuAttrAttrCtrl', selectedRow.sdattrClassCd);
        }
      }
    });
  };
  // 속성분류 그리드 조회
  $scope.$on('sideMenuAttrClassCtrl', function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/attrClass/list.sb', params, function() {
      $('#btnAddClass').show();
      $('#btnDelClass').show();
      $('#btnSaveClass').show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 속성분류 그리드 행 추가
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
    $scope._save('/base/prod/sideMenu/attrClass/save.sb', params, function() {
      // 저장 후 재조회
      $scope._broadcast('sideMenuAttrClassCtrl');
    });
  }

}]);

/**
 * 사이드메뉴 속성 그리드 생성
 */
app.controller('sideMenuAttrAttrCtrl', ['$scope', '$http', 'sdattrClassCd', function ($scope, $http, sdattrClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuAttrAttrCtrl', $scope, $http, false));
  // sdattrClassCd Data Setter
  $scope.setSdattrClassCd = function (value) {
    sdattrClassCd.set(value);
  };
  // sdattrClassCd Data Getter
  $scope.getSdattrClassCd = function () {
    return sdattrClassCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdattrCd') {
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
    // 속성 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdattrCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
  };
  // 속성 그리드 조회
  $scope.$on('sideMenuAttrAttrCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdattrClassCd(data);
    // 파라미터
    var params = {};
    params.sdattrClassCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/attrCd/list.sb', params, function() {
      $('#btnUpAttr').show();
      $('#btnDownAttr').show();
      $('#btnAddAttr').show();
      $('#btnDelAttr').show();
      $('#btnSaveAttr').show();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 속성 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.sdattrClassCd = $scope.getSdattrClassCd();
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
      $scope.flex.collectionView.items[s].sdattrClassCd = $scope.getSdattrClassCd();
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
    $scope._save('/base/prod/sideMenu/attrCd/save.sb', params, function() {
      // 저장 후 재조회
      $scope._broadcast('sideMenuAttrAttrCtrl', $scope.getSdattrClassCd());
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
}]).factory('sdattrClassCd', function () {
  // 사이드메뉴 속성 그리드 의 변수 값 영역
  var sdattrClassCd = {};
  sdattrClassCd.set = function (value) {
    sdattrClassCd.value = value;
  };
  sdattrClassCd.get = function () {
    return sdattrClassCd.value;
  };
  return sdattrClassCd;
});
