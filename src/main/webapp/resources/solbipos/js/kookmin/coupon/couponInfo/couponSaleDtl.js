/****************************************************************
 *
 * 파일명 : couponSaleDtl.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰정보관리 회수쿠폰조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('couponSaleDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponSaleDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("couponSaleDtlCtrl", function(event, data) {
        $scope.wjCouponSaleDtlLayer.show(true);
        $scope.getCouponSaleDtlList(data);
        event.preventDefault();
    });

    // 회수쿠폰 조회
    $scope.getCouponSaleDtlList = function(data){
        var params = data;

        $scope._inquiryMain("/kookmin/coupon/couponIssueStatus/couponIssueStatus/getCouponIssueStatusList.sb", params, function() {}, false);
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
            }, '회수쿠폰조회' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);