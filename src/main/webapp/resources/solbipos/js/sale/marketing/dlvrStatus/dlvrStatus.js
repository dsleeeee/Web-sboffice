/****************************************************************
 *
 * 파일명 : dlvrStatus.js
 * 설  명 : 미스터피자 > 마케팅조회 > 배달비 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.25     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  배달비 그리드 생성
 */
app.controller('dlvrStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrStatusCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dlvrStatusCtrl", function (event, data) {
        $scope.searchDlvrStatusList();
        event.preventDefault();
    });

    $scope.searchDlvrStatusList = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate    = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd      = $("#dlvrStatusSelectStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/dlvrStatus/dlvrStatus/getDlvrStatusList.sb", params, function (){});
    };
    // <-- //검색 호출 -->

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

        // 합계 행(GroupRow) 가져오기
        var groupRow = $scope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItem = groupRow.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBinding = $scope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItem = {};
        newDataItem[firstColumnBinding] = '합계';
        groupRow.dataItem = newDataItem;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "배달비_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    // 원래의 합계 행 데이터로 복원
                    groupRow.dataItem = originalDataItem;

                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
