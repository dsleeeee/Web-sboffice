/****************************************************************
 *
 * 파일명 : promotionReportDtl.js
 * 설  명 : 프로모션정산 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.13    이다솜       1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
var promo = {};

/** 프로모션정산 상세 controller */
app.controller('promotionReportDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionReportDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name'); // 판매구분
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("promotionReportDtlCtrl", function (event, data) {

        promo = data;

        // 상세 조회
        $scope.searchPromotionReportDtl(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상세 조회
    $scope.searchPromotionReportDtl = function (data) {

        // 파라미터
        var params      = {};
        params.promotionCd  = data.promotionCd;
        params.startDate  = data.startYmd;
        params.endDate = data.endYmd;
        params.storeCd = data.storeCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/promotion/promotionReport/promotionReport/getPromotionReportDtlList.sb", params, function (){});
    };
    
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
            }, '프로모션정산_상세_' + promo.promotionCd  + '_' + promo.storeCd + '_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }
    
}]);