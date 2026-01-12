/****************************************************************
 *
 * 파일명 : nonTaxSale.js
 * 설  명 : 미스터피자 > 매출분석 > 비과세매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.02     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 일자표시옵션
var dayOptionComboData = [
    {"name":"영수별","value":"1"},
    {"name":"기간합","value":"2"}
];

// 조회 쿨다운 설정
var searchCooldown = 60000; // 1분
var nextSearchTime = null;  // 다음 조회 가능 시간

/**
 *  비과세매출 그리드 생성
 */
app.controller('nonTaxSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nonTaxSaleCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        $scope.srchDayOptionCombo.selectedValue = '2';
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("nonTaxSaleCtrl", function (event, data) {

        // 일별상품매출현황 리스트 조회
        $scope.getNonTaxSaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 비과세매출 리스트 조회
    $scope.getNonTaxSaleList = function () {
        // 조회 쿨다운 체크
        var currentTime = new Date().getTime();
        if (nextSearchTime !== null && currentTime < nextSearchTime) {
            var availableTimeStr = wijmo.Globalize.format(new Date(nextSearchTime), 'yyyy-MM-dd HH:mm:ss');
            $scope._popMsg("시스템 안정성 확보를 위해 조회 대기중입니다. (사용가능 예상시간: " + availableTimeStr + ")(여러사용자 사용 시 초기화 될 수 있습니다.)");
            return false;
        }

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 31일 제한
        if (diffDay >= 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCds = $("#nonTaxSaleStoreCd").val();
        params.dayOption = $scope.dayOption;
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/nonTaxSale/nonTaxSale/getNonTaxSaleList.sb", params, function (){
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 일자표시옵션
                if(params.dayOption === "1"){  // 영수별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo"){
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleDate" || columns[j].binding == "posNo" ||columns[j].binding == "billNo"
                    || columns[j].binding == "onlineFg" || columns[j].binding == "posFg") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->

            // 다음 조회 가능 시간 저장
            nextSearchTime = new Date().getTime() + searchCooldown;
        });
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "비과세매출_" + wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);