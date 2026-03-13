/****************************************************************
 *
 * 파일명 : systemCd.js
 * 설  명 : 시스템명칭관리 JavaScript
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

// 조회조건 DropBoxDataMap
var useTargetFg = [
  {"name":"전체","value":""},
  {"name":"공통","value":"C"},
  {"name":"본사전용","value":"H"},
  {"name":"매장전용","value":"S"},
  {"name":"시스템전용","value":"A"}
];
var useSystemFg = [
  {"name":"전체","value":""},
  {"name":"공통","value":"C"},
  {"name":"포스전용","value":"P"},
  {"name":"웹전용","value":"W"}
];
// 사용대상 DropBoxDataMap
var useTargetFgDataMap = new wijmo.grid.DataMap([
  {id: "C", name: "공통"},
  {id: "H", name: "본사전용"},
  {id: "S", name: "매장전용"},
  {id: "A", name: "시스템전용"}
  ], 'id', 'name');
// 사용시스템 DropBoxDataMap
var useSystemFgDataMap = new wijmo.grid.DataMap([
  {id: "C", name: "공통"},
  {id: "P", name: "포스전용"},
  {id: "W", name: "웹전용"}
  ], 'id', 'name');

/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('representCtrl', $scope, $http, true));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchUseTargetFg", useTargetFg);
  $scope._setComboData("srchUseSystemFg", useSystemFg);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("representCtrl");
    // 그리드 내 콤보박스 설정
    $scope.useTargetFgDataMap = useTargetFgDataMap;
    $scope.useSystemFgDataMap = useSystemFgDataMap;
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
    // 대표명칭 그리드 붙여넣기 방지
    s.pastingCell.addHandler(function (sender, elements) {
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
        var selectedRow = s.rows[ht.row].dataItem;
        var col = ht.panel.columns[ht.col];
        if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
          $scope._broadcast('detailCtrl', selectedRow.nmcodeCd);
        }
      }
    });
  };
  // 대표명칭 그리드 조회
  $scope.$on('representCtrl', function(event, data) {
    var params = {};
    params.nmcodeGrpCd = "000";
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/cd/systemCd/systemCd/list.sb", params, function() {
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
    params.useTargetFg = "C";
    params.useSystemFg = "C";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 대표명칭 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = [];

    var arr = [];

    for (var a = 0; a < $scope.flex.collectionView.items.length; a++) {
      var item = $scope.flex.collectionView.items[a];

      if(nvl(item.nmcodeCd, '').getByteLengthForOracle() > 4){
        var msg = messages["cd.grpGridNm"] + " " + messages["cd.nmcodeCd"] + messages["cmm.overLength"] + " 4 "  +
            ", 현재 : " + item.nmcodeCd.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
        $scope._popMsg(msg); // 현재 대표명칭 코드의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 :
        return false;
      }

      if (arr.indexOf(item.nmcodeCd) !== -1) {
        $scope._popMsg(messages["cd.detail.require.nmcodeCdChk"]); // 코드가 중복되었습니다.
        return false;
      } else {
        arr.push(item.nmcodeCd);
      }
    }

    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      var item = $scope.flex.collectionView.itemsAdded[i];
      if(item.nmcodeCd === undefined || item.nmcodeCd.length === 0){
        $scope._popMsg(messages["cd.represent.require.nmcodeCd"]); // 대표명칭의 코드를 입력해주세요
        return false;
      }

      if(item.nmcodeNm === undefined || item.nmcodeNm.length === 0){
        $scope._popMsg(messages["cd.represent.require.nmcodeNm"]); // 대표명칭의 코드명을 입력해주세요
        return false;
      }

      item.status = "I";
      params.push(item);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/cd/systemCd/systemCd/save.sb", params, function(){
      $scope._broadcast('representCtrl');
    });
  };

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
    $scope.useTargetFgDataMap = useTargetFgDataMap;
    $scope.useSystemFgDataMap = useSystemFgDataMap;
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

    // 세부명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });

    // 세부명칭 그리드 붙여넣기 방지
    s.pastingCell.addHandler(function (sender, elements) {
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
    var params = {};
    params.nmcodeGrpCd = data;
    // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
    $scope._inquirySub("/sys/cd/systemCd/systemCd/list.sb", params, function() {
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
    params.useTargetFg = "C";
    params.useSystemFg = "C";

    $scope._addRow(params);
  };
  // 세부명칭 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = [];

    var arr = [];

    for (var a = 0; a < $scope.flex.collectionView.items.length; a++) {
      var item = $scope.flex.collectionView.items[a];

      if(nvl(item.nmcodeCd, '').getByteLengthForOracle() > 4){
        var msg = messages["cd.gridNm"] + " " + messages["cd.nmcodeCd"] + messages["cmm.overLength"] + " 4 "  +
            ", 현재 : " + item.nmcodeCd.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
        $scope._popMsg(msg); // 현재 세부명칭 코드의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 :
        return false;
      }

      if (arr.indexOf(item.nmcodeCd) !== -1) {
        $scope._popMsg(messages["cd.detail.require.nmcodeCdChk"]); // 코드가 중복되었습니다.
        return false;
      } else {
        arr.push(item.nmcodeCd);
      }
    }

    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      var item = $scope.flex.collectionView.itemsAdded[i];
      if(item.nmcodeCd === undefined || item.nmcodeCd.length === 0){
        $scope._popMsg(messages["cd.detail.require.nmcodeCd"]); // 세부명칭의 코드를 입력해주세요
        return false;
      }

      if(item.nmcodeNm === undefined || item.nmcodeNm.length === 0){
        $scope._popMsg(messages["cd.detail.require.nmcodeNm"]); // 세부명칭의 코드명을 입력해주세요
        return false;
      }

      item.status = "I";
      params.push(item);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/cd/systemCd/systemCd/save.sb", params, function() {
      var scope = agrid.getScope('representCtrl');
      $scope._broadcast('detailCtrl', scope.flex.selectedRows[0]._data.nmcodeCd);
    });
  };

}]);
