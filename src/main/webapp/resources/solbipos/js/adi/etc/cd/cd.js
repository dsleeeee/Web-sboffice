/****************************************************************
 *
 * 파일명 : cd.js
 * 설  명 : 명칭관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.13     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
// 조회조건 DropBoxDataMap
var useYnFg = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"사용안함","value":"N"}
];
// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
  {id: "", name: "전체"},
  {id: "Y", name: "사용"},
  {id: "N", name: "사용안함"}
  ], 'id', 'name');

/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('representCtrl', $scope, $http, true));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchUseYnFg", useYnFg);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("representCtrl");
    // 그리드 내 콤보박스 설정
    $scope.useYnFgDataMap = useYnFgDataMap;
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "nmcodeCd") {
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
    // 대표명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
    // 대표명칭 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem
        var col = ht.panel.columns[ht.col];
        if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
          $scope._broadcast('detailCtrl', selectedRow.nmcodeCd);
        }
      }
    });
  };
  // 대표명칭 그리드 조회
  $scope.$on("representCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.nmcodeGrpCd = "000";
    params.useYn = $scope.useYnFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/adi/etc/cd/cd/list.sb", params, function() {
      // 대표명칭 그리드 버튼 show
      $("#btnAddRepresent").show();
      $("#btnDelRepresent").show();
      $("#btnSaveRepresent").show();
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 대표명칭 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.nmcodeGrpCd = "000";
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 대표명칭 그리드 저장
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
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/adi/etc/cd/cd/save.sb", params);
  }
  // 대표명칭 그리드 행 삭제
  $scope.deleteRow = function() {
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        if(item.cnt > 0){
          $scope._popMsg("세부명칭이 등록된 대표명칭은 삭제할 수 없습니다. ");
          return false;
        }
        $scope.flex.collectionView.removeAt(i);
      }
    }
  }
}]);

/**
 * 세부명칭 그리드 생성
 */
app.controller('detailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('detailCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("detailCtrl");
    // 그리드 내 콤보박스 설정
    $scope.useYnFgDataMap = useYnFgDataMap;
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "nmcodeCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 대표명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
  };
  // 세부명칭 그리드 초기화
  $scope.$on("init", function() {
    $scope._gridDataInit();
  });
  // 세부명칭 그리드 조회
  $scope.$on("detailCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.nmcodeGrpCd = data;
    // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
    $scope._inquirySub("/adi/etc/cd/cd/list.sb", params, function() {
      // 세부명칭 그리드 버튼 show
      $("#btnAddDetail").show();
      $("#btnDelDetail").show();
      $("#btnSaveDetail").show();
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 세부명칭 그리드 행 추가
  $scope.addRow = function() {
    var gridRepresent = agrid.getScope('representCtrl');
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    var params = {};
    params.nmcodeGrpCd = selectedRow.nmcodeCd;
    params.useYn = "Y";

    $scope._addRow(params, 1);
  };
  // 세부명칭 그리드 저장
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
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/adi/etc/cd/cd/save.sb", params);
  }
  // 세부명칭 그리드 행 삭제
  $scope.deleteRow = function() {
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        $scope.flex.collectionView.removeAt(i);
      }
    }
  }

}]);
