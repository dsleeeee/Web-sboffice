/****************************************************************
 *
 * 파일명 : urlBlockLog.js
 * 설  명 : 탐지/차단결과 로그 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.30     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// URL 유형
var urlBlockLogUrlTypeFg = [
    {"name":"전체","value":""},
    {"name":"차단","value":"B"},
    {"name":"전송","value":"W"},
    {"name":"그 외 차단","value":"G"}
];

// 메시지 상태
var urlBlockLogMsgStatusFg = [
    {"name":"전체","value":""},
    {"name":"차단","value":"block"},
    {"name":"허용","value":"allow"}
];

app.controller('urlBlockLogCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('urlBlockLogCtrl', $scope, $http, $timeout, true));

    // 조회일자 셋팅
    var urlBlockLogSrchStartDate = wcombo.genDateVal("#urlBlockLogSrchStartDate", gvStartDate);
    var urlBlockLogSrchEndDate   = wcombo.genDateVal("#urlBlockLogSrchEndDate", gvEndDate);

    // 콤보박스 데이터 Set
    $scope._setComboData("urlBlockLogUrlType", urlBlockLogUrlTypeFg);       // URL 유형

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.urlTypeDataMap  = new wijmo.grid.DataMap(urlBlockLogUrlTypeFg,  'value', 'name');    // URL 유형
        $scope.msgStatusDataMap  = new wijmo.grid.DataMap(urlBlockLogMsgStatusFg,  'value', 'name'); // 메시지 상태
    };

    $scope.$on("urlBlockLogCtrl", function (event, data) {
        // 조회
        $scope.searchUrlBlockLog();
        event.preventDefault();
    });

    // 조회
    $scope.searchUrlBlockLog = function () {
        var params = {};
        params.startDate = wijmo.Globalize.format(urlBlockLogSrchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(urlBlockLogSrchEndDate.value, 'yyyyMMdd');
        params.urlType = $scope.urlBlockLogUrlTypeCombo.selectedValue;
        params.chkUrl = $("#urlBlockLogChkUrl").val();

        $scope._inquiryMain("/adi/sms/smsXrayManage/urlBlockLog/getUrlBlockLogList.sb", params, function () {}, false);
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
            }, '악성문자차단관리_탐지차단결과로그_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
