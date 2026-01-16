/****************************************************************
 *
 * 파일명 : onlineOrder.js
 * 설  명 : 매출관리 > 매출분석 > 온라인주문확인 JavaScript
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

// 매출 구분
var saleFgComboData = [
    {"name":"매출","value":"1"},
    {"name":"반품","value":"-1"}
];

/**
 *  온라인주문확인 그리드 조회
 */
app.controller('onlineOrderCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('onlineOrderCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.saleDate  = wcombo.genDateVal("#saleDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgComboData, 'value', 'name'); // 상태
    };

    // 검색 호출
    $scope.$on("onlineOrderCtrl", function(event, data) {
        $scope.getSearchOnlineOrder();
        event.preventDefault();
    });

    // 온라인주문확인 조회
    $scope.getSearchOnlineOrder = function(){

        if( isEmptyObject( $("#onlineOrderStoreCd").val()) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        var params = {};
        params.saleDate = wijmo.Globalize.format($scope.saleDate.value, 'yyyyMMdd');
        params.storeCd = $("#onlineOrderStoreCd").val();

        $scope._inquiryMain("/sale/anals/onlineOrder/onlineOrder/getSearchOnlineOrderList.sb", params, function() {}, false);
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex,
                {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                messages["onlineOrder.onlineOrder"] + '_' + getCurDateTime() + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);