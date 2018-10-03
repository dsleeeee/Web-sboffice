/****************************************************************
 *
 * 파일명 : kind.js
 * 설  명 : 출력물종류 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 출력물종류 그리드 생성
 */
app.controller('printCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('printCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prtClassCd") {
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
    // 출력물종류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "prtClassCd") {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
    // 출력물종류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "prtClassCd" && selectedRow.status !== "I") {
          $scope._broadcast('mapngCtrl', selectedRow.prtClassCd);
        }
      }
    });
  };
  // 출력물종류 그리드 조회
  $scope.$on("printCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/bill/kind/bill/list.sb", params, function() {
      $("#btnAddPrint").show();
      $("#btnDelPrint").show();
      $("#btnSavePrint").show();

      // var selectedRow = $scope.flex.selectedRows[0]._data;
      // params.prtClassCd = selectedRow.prtClassCd;
      // $("#prtClassCd").val(selectedRow.prtClassCd);
      // 출력물매핑 그리드 조회
      // $scope._broadcast('mapngCtrl', selectedRow.prtClassCd);
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 출력물종류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.generalYn = false;
    params.foodYn = false;
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 저장
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
    $scope._save("/sys/bill/kind/bill/save.sb", params);
  }
}]);

/**
 * 출력물매핑 그리드 생성
 */
app.controller('mapngCtrl', ['$scope', '$http', 'prtClassCd', function ($scope, $http, prtClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('mapngCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prtClassCd") {
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
    // 출력물종류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "prtClassCd") {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
    // 출력물종류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "prtClassCd" && selectedRow.status !== "I") {
          // searchMapng(selectedRow.prtClassCd);
        }
      }
    });
  };
  // prtClassCd Data Setter
  $scope.setKindPrtClassCd = function (data) {
    prtClassCd.set(data);
  };
  // prtClassCd Data Getter
  $scope.getKindPrtClassCd = function () {
    return prtClassCd.get();
  };
  // 출력물매핑 그리드 조회
  $scope.$on("mapngCtrl", function(event, data) {
    // scope 영역에 변수 Set
    $scope.setKindPrtClassCd(data);
    // 파라미터
    var params = {};
    params.prtClassCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/sys/bill/kind/mapng/list.sb", params, function(){
      $("#btnUpMapng").show();
      $("#btnDownMapng").show();
      $("#btnAddMapng").show();
      $("#btnDelMapng").show();
      $("#btnSaveMapng").show();
    }, false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 출력물매핑 그리드 행 추가
  $scope.addRow = function() {

    var popup = $scope.itemSelLayer;
    popup.shown.addHandler(function (s) {
      // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
      setTimeout(function() {
        var params = {};
        var grid = agrid.getScope("printCtrl").flex;
        var selectedRow = grid.selectedRows[0]._data;
        params.prtClassCd = selectedRow.prtClassCd;

        $scope._broadcast('printCodeCtrl', params);
      }, 50)
    });
    popup.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (popup.dialogResult === "wj-hide-apply") {
        var gridPrintCode = agrid.getScope('printCodeCtrl');
        for (var i = 0; i < gridPrintCode.flex.collectionView.itemCount; i++) {
          var item = gridPrintCode.flex.collectionView.items[i];
          // 선택된 행만 체크
          if (item.gChk) {
            var dupCheck = false;
            // 중복체크
            for (var j = 0; j < $scope.flex.collectionView.itemCount; j++) {
              var savedItem = $scope.flex.collectionView.items[j];
              if (savedItem.prtCd === item.prtCd) {
                dupCheck = true;
                break;
              }
            }
            // 중복체크 통과시 추가
            if (!dupCheck) {
              $scope.flex.collectionView.trackChanges = true;
              var newRow = $scope.flex.collectionView.addNew();
              newRow.status = "I";
              newRow.prtClassCd = $scope.getKindPrtClassCd();
              newRow.prtCd = item.prtCd;
              newRow.gChk = false;

              $scope.flex.collectionView.commitNew();
            }
          }
        }
        $scope.flex.select($scope.flex.rows.length - 1, 1);
      }
    });
  };
  // 출력물매핑 그리드 저장
  $scope.save = function() {

    // dispSeq 재설정
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      $scope.flex.collectionView.editItem($scope.flex.collectionView.items[i]);
      $scope.flex.collectionView.items[i].prtClassCd = $scope.getKindPrtClassCd();
      $scope.flex.collectionView.items[i].dispSeq = (i + 1);
      $scope.flex.collectionView.commitEdit();
    }

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
    $scope._save("/sys/bill/kind/mapng/save.sb", params);
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
}]);
/** 출력물매핑 그리드 의 변수 값 영역 */
app.factory('prtClassCd', function () {
  var prtClassCd = {};
  prtClassCd.set = function (data) {
    prtClassCd.value = data;
  };
  prtClassCd.get = function () {
    return prtClassCd.value;
  };
  return prtClassCd;
});
