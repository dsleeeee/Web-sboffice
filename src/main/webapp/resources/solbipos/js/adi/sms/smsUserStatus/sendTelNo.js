/****************************************************************
 *
 * 파일명 : sendTelNo.js
 * 설  명 : 발신번호 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.07     이다솜      1.0
 *
 * **************************************************************/
var app = agrid.getApp();

// SMS사용등록 구분
var smsUseRegFgData = [
    {"name": "전체", "value": ""},
    {"name": "SMS사용등록", "value": "Y"},
    {"name": "SMS사용등록 외", "value": "N"}
];

// 사용여부
var useYnFgData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

app.controller('sendTelNoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('sendTelNoCtrl', $scope, $http, $timeout, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("smsUseRegFg", smsUseRegFgData); // SMS사용등록 구분

    $scope.initGrid = function (s, e) {
        // 사용여부
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name');
    };

    $scope.$on("sendTelNoCtrl", function (event, data) {
        // 조회
        $scope.searchSendTelNo();
        event.preventDefault();
    });

    // 조회
    $scope.searchSendTelNo = function () {
        var params = {};
        params.orgnCd = $scope.orgnCd;
        params.orgnNm = $scope.orgnNm;
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;
        params.smsUseRegFg = $scope.smsUseRegFgCombo.selectedValue;

        $scope._inquiryMain("/adi/sms/smsUserStatus/getSendTelNoList.sb", params, function () {}, false);
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
            }, 'SMS사용현황_발신번호_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };
}]);
