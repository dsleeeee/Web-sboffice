/****************************************************************
 *
 * 파일명 : dayEmpCard.js
 * 설  명 : 매출관리 > 매출현황 > 일자별 > 사원카드별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.17     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  사원카드별 조회 그리드 생성
 */
app.controller('dayEmpCardCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayEmpCardCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayEmpCard", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayEmpCard", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayEmpCardCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayEmpCardCtrl", function(event, data) {
        $scope.searchDayEmpCard();
        event.preventDefault();
    });

    $scope.searchDayEmpCard = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayEmpCardStoreCd").val();
        params.payCol = payCol;

        $scope._inquiryMain("/sale/day/day/day/getDayEmpCardList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayEmpCardStoreShow = function () {
        $scope._broadcast('dayEmpCardStoreCtrl');
    };


// 엑셀 다운로드
    $scope.excelDownloadDayEmpCard = function () {

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
            },
                messages["day.day"] + '(' + messages["day.empCard"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);