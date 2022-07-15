/**
 * get application
 */
var app = agrid.getApp();

/** 조정관리 그리드 controller */
app.controller('adjCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('adjCtrl', $scope, $http, true));

  $scope._setComboData("adjReason", [{name: "전체", value: ""}].concat(reasonData));

  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  $scope.adjDate       = wcombo.genDate("#adjDate");

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["adj.procFg0"]},
    {id: "1", name: messages["adj.procFg1"]}
  ], 'id', 'name');

  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["adj.procFg0"], "value": "0"},
    {"name": messages["adj.procFg1"], "value": "1"}
  ]);


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.adjReasonDataMap = new wijmo.grid.DataMap(reasonData, 'value', 'name'); // 사유

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("adjCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "adjTitle") { // 제목
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "adjTitle") { // 제목 클릭
          var params     = {};
          params.adjDate = selectedRow.adjDate;
          params.seqNo   = selectedRow.seqNo;
          params.adjStorageCd     = selectedRow.adjStorageCd;
          $scope._broadcast('adjDtlCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("adjCtrl", function (event, data) {
    $scope.searchAdjList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 주문 리스트 조회
  $scope.searchAdjList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/adj/adj/adj/list.sb", params);
  };


  // 조정 삭제
  $scope.deleteAdj = function () {
    /** 선택하신 자료를 삭제하시겠습니까? */
    var msg = messages["adj.delMsg"];
    s_alert.popConf(msg, function () {
      var params = [];

      if ($scope.flex.collectionView.itemsEdited.length <= 0) {
        $scope._popMsg(messages["cmm.not.modify"]);
        return false;
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.gChk === true && item.procFg !== '0') {
          $scope._popMsg(messages["adj.not.delete"]); // 확정 된 자료는 삭제할 수 없습니다.
          return false;
        }

        if(item.gChk === true) {
          item.status = "U";
          params.push(item);
        }
      }

      $scope._save("/stock/adj/adj/adj/delete.sb", params, function () {
        $scope.searchAdjList();
      });
    });
  };


  // 조정 신규등록
  $scope.newAdj = function () {
    var params        = {};
    params.adjDate    = wijmo.Globalize.format($scope.adjDate.value, 'yyyyMMdd');
    params.seqNo      = '';
    params.callParent = 'adj';
    $scope._broadcast('adjRegistCtrl', params);
  };


}]);
