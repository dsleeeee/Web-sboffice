/**
 * get application
 */
var app = agrid.getApp();

/** 여신정보 그리드 controller */
app.controller('loanInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('loanInfoCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);


  // 그리드 DataMap 설정
  $scope.orderFg = new wijmo.grid.DataMap([
      {id: "1", name: messages["loan.orderFg1"]},
      {id: "2", name: messages["loan.orderFg2"]},
      {id: "3", name: messages["loan.orderFg3"]}
  ], 'id', 'name');

  $scope.noOutstockAmtFg = new wijmo.grid.DataMap([
      {id: "N", name: messages["loan.noOutstockAmtFgN"]},
      {id: "Y", name: messages["loan.noOutstockAmtFgY"]}
  ], 'id', 'name');

  $scope._setComboData("srchDateFg", [
    {"name": messages["loanInfo.all"], "value": ""},
    {"name": messages["loanInfo.searchDate"], "value": "date"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("loanInfoCtrl");

  };

  // 리스트 조회
  $scope.searchLoanInfo = function () {

      if($scope.dateFg === 'date') {
          var startDt = new Date(wijmo.Globalize.format(srchStartDate.value, 'yyyy-MM-dd'));
          var endDt = new Date(wijmo.Globalize.format(srchEndDate.value, 'yyyy-MM-dd'));

          // 시작일자가 종료일자보다 빠른지 확인
          if (startDt.getTime() > endDt.getTime()) {
              $scope._popMsg(messages['cmm.dateChk.error']);
              return false;
          }
      }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.dateFg    = $scope.dateFg;

    $scope._inquiryMain("/iostock/loan/loanInfo/loanInfo/getStoreLoanManageList.sb", params, function() {
        $scope._broadcast('loanInfoManageCtrl', params);
    });

  };

  // 조회일자 값 변경 이벤트 함수
  $scope.selectedIndexChanged = function (s, e) {
    if (s.selectedValue === "") {
      $scope.dateLayer = false;
    } else {
      $scope.dateLayer = true;
    }
  };


}]);

/** 여신현황 그리드 controller */
app.controller('loanInfoManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('loanInfoManageCtrl', $scope, $http, true));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("loanInfoManageCtrl");

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

    // 조회
    $scope.$on("loanInfoManageCtrl", function (event, data) {
        $scope.searchLoanInfo(data);
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchLoanInfo = function (data) {
        // 파라미터
        var params       = {};
        params.startDate = data.startDate;
        params.endDate   = data.endDate;
        params.dateFg    = data.dateFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/iostock/loan/loanInfo/loanInfo/list.sb", params, function() {
            var grid = wijmo.Control.getControl("#wjGridLoanInfoManageCtrl");
            var rows = grid.rows;
            var currLoan = 0;

            for (var i = $scope.flex2.collectionView.items.length-1; i >= 0 ; i--) {
                var item = $scope.flex2.collectionView.items[i];

                if(i == ($scope.flex2.collectionView.items.length-1))    currLoan = item.limitLoanAmt;

                currLoan = currLoan - item.outAmt + item.inAmt;
                item.currLoanAmt = currLoan;
            }
        }, false);
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex2.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex2, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '여신현황_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);