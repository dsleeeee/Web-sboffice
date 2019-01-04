/** 상품 입고현황 그리드 controller */
app.controller('prodInstockInfoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodInstockInfoCtrl', $scope, $http, true));

  // 그리드 전표구분
  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["prodInstockInfo.slipFgIn"]},
    {id: "-1", name: messages["prodInstockInfo.slipFgRtn"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodInstockInfoCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.prodCd    = data.prodCd;
    $scope.prodNm    = data.prodNm;
    $scope.slipNo    = nvl(data.slipNo, '');
    $scope.inSlipNo  = nvl(data.inSlipNo, '');
    $scope.startDate = nvl(data.startDate, '');
    $scope.endDate   = nvl(data.endDate, '');

    $scope.wjProdInstockInfoLayer.show(true);
    $scope.prodCdNm = '[' + $scope.prodCd + '] ' + $scope.prodNm;
    // $("#subTitle").html(messages["prodInstockInfo.prodCd"] + ' : ' + $scope.prodCd + messages["prodInstockInfo.prodNm"] + ' : ' + $scope.prodNm);

    $scope.searchProdInstockInfoList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 상품 입고 리스트 조회
  $scope.searchProdInstockInfoList = function () {
    // 파라미터
    var params       = {};
    params.prodCd    = $scope.prodCd;
    params.slipNo    = $scope.slipNo;
    params.inSlipNo  = $scope.inSlipNo;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/vendr/orderStockInfo/prodInstockInfo/list.sb", params);
  };

}]);
