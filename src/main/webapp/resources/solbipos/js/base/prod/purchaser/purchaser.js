/****************************************************************
 *
 * 파일명 : purchaser.js
 * 설  명 : 국민대 > 매입처관리 > 매입처조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매입처조회 그리드 생성
 */
app.controller('purchaserCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('purchaserCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("purchaserCtrl", function(event, data) {
        $scope.searchPurchaser();
        event.preventDefault();
    });

    $scope.searchPurchaser = function(){
        // 파라미터
        var params = {};
        params.vendrCd = $scope.vendrCd;
        params.vendrNm = $scope.vendrNm;
        params.teamCd = $scope.teamCd;
        params.teamNm = $scope.teamNm;
        params.branchCd = $scope.branchCd;
        params.branchNm = $scope.branchNm;

        $scope._inquiryMain("/base/prod/purchaser/purchaser/getPurchaserList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
                "매입처조회_" + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);