/****************************************************************
 *
 * 파일명 : nonSaleCharge.js
 * 설  명 : 비매출충전내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.04     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  비매출충전내역 생성
 */
app.controller('nonSaleChargeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nonSaleChargeCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회 월 셋팅
    var saleMonth = new wijmo.input.InputDate('#saleMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 검색 호출
    $scope.$on("nonSaleChargeCtrl", function(event, data) {
        $scope.searchNonSaleCharge();
        event.preventDefault();
    });

    // 비매출충전내역 조회
    $scope.searchNonSaleCharge = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(saleMonth.value, 'yyyyMM') + '01';
        params.endDate = wijmo.Globalize.format(saleMonth.value, 'yyyyMM') + '31';
        params.storeCd = $("#nonSaleChargeStoreCd").val();
        params.listScale = $scope.listScaleCombo.text;

        $scope._inquiryMain("/sale/anals/nonSaleCharge/list.sb", params, function() {}, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.nonSaleChargeStoreShow = function () {
        $scope._broadcast('nonSaleChargeStoreCtrl');
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(saleMonth.value, 'yyyyMM') + '01';
        params.endDate = wijmo.Globalize.format(saleMonth.value, 'yyyyMM') + '31';
        params.storeCd = $("#nonSaleChargeStoreCd").val();

        $scope._broadcast('nonSaleChargeExcelCtrl', params);

    }

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('nonSaleChargeExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nonSaleChargeExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 조회
    $scope.$on("nonSaleChargeExcelCtrl", function(event, data) {
        $scope.searchNonSaleChargeExcel(data);
        event.preventDefault();
    });

    $scope.searchNonSaleChargeExcel = function(data){

        // 엑셀 파일명 날짜(조회월을 파일명에 표시)
        var fileDate = {};
        fileDate[0] = data.startDate.substr(0, 4);
        fileDate[1] = data.startDate.substr(4, 2);

        $scope._inquiryMain("/sale/anals/nonSaleCharge/excelList.sb", data, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, getToday() + '_' + $(menuNm).selector + '_' + fileDate[0] + '년_' + fileDate[1] + '월분.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);

        });
    };

}]);