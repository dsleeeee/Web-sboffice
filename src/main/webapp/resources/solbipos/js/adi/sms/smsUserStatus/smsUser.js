/****************************************************************
 *
 * 파일명 : smsUser.js
 * 설  명 : SMS 사용자 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.07     이다솜      1.0
 *
 * **************************************************************/
var app = agrid.getApp();

app.controller('smsUserCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('smsUserCtrl', $scope, $http, $timeout, true));

    $scope.initGrid = function (s, e) {
    };

    $scope.$on("smsUserCtrl", function (event, data) {
        // 조회
        $scope.searchSmsUser();
        event.preventDefault();
    });
    
    // 조회
    $scope.searchSmsUser = function () {
        var params = {};
        params.orgnCd = $scope.orgnCd;
        params.orgnNm = $scope.orgnNm;
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;

        $scope._inquiryMain("/adi/sms/smsUserStatus/getSmsUserList.sb", params, function () {}, false);
    };
    
    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, 'SMS사용현황_SMS사용자_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };
}]);
