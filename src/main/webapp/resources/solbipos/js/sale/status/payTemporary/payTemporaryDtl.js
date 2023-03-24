/**
 * get application
 */
var app = agrid.getApp();

/** 모바일페이 상세 내역 controller */
app.controller('payTemporaryDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('payTemporaryDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("payTemporaryDtlCtrl", function (event, data) {
    $scope.wjPayTemporaryDtlLayer.show(true);
    $scope.searchPayTemporaryDtlList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 리스트 조회
  $scope.searchPayTemporaryDtlList = function (data) {
    // 파라미터
    var params          = {};
    params.storeCd      = data.storeCd;
    if(orgnFg === "HQ"){
      params.startDate  = data.startDate;
      params.endDate    = data.endDate;
    } else if(orgnFg === "STORE"){
      params.saleDate   = data.saleDate.replaceAll('-', '');
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/payTemporary/payTemporary/getPayTemporaryDtlList.sb", params);
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      }, messages["PayTemporary.PayTemporaryDtl"] + '_' + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
