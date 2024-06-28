

/** 여신상세현황 그리드 controller */
app.controller('storeLoanInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeLoanInfoCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeLoanInfoCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });
  };

  // 리스트 조회
  $scope.searchStoreLoanInfo = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#storeLoanInfoSelectStoreCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/loan/storeLoanInfo/storeLoanInfo/list.sb", params, function() {
        var grid = wijmo.Control.getControl("#wjGridStoreLoanInfoCtrl");
        var rows = grid.rows;
        var currLoan    = 0;
        var store_cd    = '';

        for (var i = $scope.flex.collectionView.items.length-1; i >= 0 ; i--) {
            var item = $scope.flex.collectionView.items[i];

            if(store_cd != item.storeCd)
            {
                currLoan = item.limitLoanAmt;
                store_cd = item.storeCd;
            }

            currLoan = currLoan - item.outAmt + item.inAmt;
            item.currLoanAmt = currLoan;
        }
    }, false);

  };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '매장여신상세현황_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
