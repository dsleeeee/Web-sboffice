/** 발주번호 그리드 controller */
app.controller('vendrInstockOrderSlipCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockOrderSlipCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchSlipStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchSlipEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
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
        if (col.binding === "slipNo") { // 전표번호 클릭
          $scope.$apply(function () {
            var vendrInstockDtlScope                  = agrid.getScope('vendrInstockDtlCtrl');
            vendrInstockDtlScope.slipInfo.orderSlipNo = selectedRow.slipNo;
            vendrInstockDtlScope.slipInfo.vendrCd     = selectedRow.vendrCd;
            vendrInstockDtlScope.slipInfo.vendrNm     = selectedRow.vendrNm;
            $scope.popupClose();
          });
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockOrderSlipCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.wjVendrInstockOrderSlipLayer.show(true);

    $scope.searchVendrInstockOrderSlipList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 발주번호 리스트 조회
  $scope.searchVendrInstockOrderSlipList = function () {
    // 파라미터
    var params     = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.vendrCd = $("#vendrInstockOrderSlipSelectVendrCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/vendr/vendrInstock/vendrInstockOrderSlip/list.sb", params);
  };


  $scope.popupClose = function () {
    $scope.wjVendrInstockOrderSlipLayer.hide(true);
  };


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.vendrInstockOrderSlipSelectVendrShow = function () {
    $scope._broadcast('vendrInstockOrderSlipSelectVendrCtrl');
  };


}]);
