/****************************************************************
 *
 * 파일명 : dayStoreBill.js
 * 설  명 : 매출 공통팝업 > 매장별 영수건수 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.18     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('dayStoreBillCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayStoreBillCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("dayStoreBillCtrl", function(event, data) {
        $scope.wjDayStoreBillLayer.show(true);
        $scope.searchDayStoreBillList(data);
        event.preventDefault();
    });

    $scope.searchDayStoreBillList = function(data){
        var params = {};
        // 기간별매출 > 일자별 탭 > 일별종합 탭
        if(data.gubun == "day") {
            params.saleDate = data.saleDate;
        }
        // 기간별매출 > 월별 탭 > 월별종합 탭
        if(data.gubun == "month") {
            params.yearMonth = data.yearMonth;
        }
        params.storeCds  = data.storeCd;
        params.gubun  = data.gubun;

        $scope._inquiryMain("/sale/cmmSalePopup/dayBillInfo/dayBillInfo/getDayStoreBillList.sb", params, function() {}, false);
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
            }, '매장별영수건수_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);