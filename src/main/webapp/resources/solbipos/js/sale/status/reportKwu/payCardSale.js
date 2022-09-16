/****************************************************************
 *
 * 파일명 : payCardSale.js
 * 설  명 : 신용카드 매출내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 승인구분 DropBoxDataMap
var vApprFg = [
    {"name":"전체","value":""},
    {"name":"승인","value":"1"},
    {"name":"취소","value":"-1"}
];

/**
 *  신용카드 매출내역 조회 그리드 생성
 */
app.controller('payCardSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('payCardSaleCtrl', $scope, $http, true));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 승인구분 콤보박스 데이터 Set
    $scope._setComboData("apprFgCombo", vApprFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

       // add the new GroupRow to the grid's 'columnFooters' panel
       s.columnFooters.rows.push(new wijmo.grid.GroupRow());
       // add a sigma to the header to show that this is a summary row
       s.bottomLeftCells.setCellData(0, 0, '합계');

    };

    //
    $scope.$on("payCardSaleCtrl", function(event, data) {
        // 신용카드 매출내역 조회
        $scope.searchPayCardSaleList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 신용카드 매출내역 조회
    $scope.searchPayCardSaleList = function () {
        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd'); //조회기간
        params.apprFg = $scope.apprFgCombo.selectedValue;
        params.storeCd = $("#payCardSaleStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/reportKwu/payCardSale/getList.sb", params, function() {});
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.payCardSaleStoreShow = function () {
        $scope._broadcast('payCardSaleStoreCtrl');
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
              }, '신용카드매출내역_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
              });
        }, 10);
    };
}]);