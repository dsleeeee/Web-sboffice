/**
 * get application
 */
var app = agrid.getApp();

/** 폐기관리 그리드 controller */
app.controller('disuseCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('disuseCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  $scope.disuseDate    = wcombo.genDate("#disuseDate");

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["disuse.procFg0"]},
    {id: "1", name: messages["disuse.procFg1"]}
  ], 'id', 'name');

  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["disuse.procFg0"], "value": "0"},
    {"name": messages["disuse.procFg1"], "value": "1"}
  ]);


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("disuseCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "disuseTitle") { // 제목
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
        if (col.binding === "disuseTitle") { // 제목 클릭
          var params        = {};
          params.disuseDate = selectedRow.disuseDate;
          params.seqNo      = selectedRow.seqNo;
          $scope._broadcast('disuseDtlCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("disuseCtrl", function (event, data) {
    $scope.searchDisuseList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 주문 리스트 조회
  $scope.searchDisuseList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/disuse/disuse/disuse/list.sb", params);
  };


  // 폐기 삭제
  $scope.deleteDisuse = function () {
    /** 선택하신 자료를 삭제하시겠습니까? */
    var msg = messages["disuse.delMsg"];
    s_alert.popConf(msg, function () {
      var params = [];

      if ($scope.flex.collectionView.itemsEdited.length <= 0) {
        $scope._popMsg(messages["cmm.not.modify"]);
        return false;
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.gChk === true && item.procFg !== '0') {
          $scope._popMsg(messages["disuse.not.delete"]); // 확정 된 자료는 삭제할 수 없습니다.
          return false;
        }

        if(item.gChk === true) {
          item.status = "U";
          params.push(item);
        }
      }

      $scope._save("/stock/disuse/disuse/disuse/delete.sb", params, function () {
        $scope.searchDisuseList();
      });
    });
  };


  // 폐기 신규등록
  $scope.newAdj = function () {
    var params        = {};
    params.disuseDate = wijmo.Globalize.format($scope.disuseDate.value, 'yyyyMMdd');
    params.seqNo      = '';
    params.callParent = 'disuse';
    $scope._broadcast('disuseRegistCtrl', params);
  };


}]);
