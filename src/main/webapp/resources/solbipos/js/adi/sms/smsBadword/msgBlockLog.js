/****************************************************************
 *
 * 파일명 : msgBlockLog.js
 * 설  명 : 탐지/차단 결과 로그 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 차단 원인 유형
var blockTypeFg = [
    {"name":"전체","value":""},
    {"name":"금칙어","value":"keyword"},
    {"name":"URL","value":"url"}
    /*{"name":"금칙어+URL","value":"combined"}*/
];

// 메시지 상태
var msgStatusFg = [
    {"name":"전체","value":""},
    {"name":"차단","value":"blocked"},
    {"name":"보류","value":"held"},
    {"name":"주의","value":"warned"},
    {"name":"허용","value":"allowed"}
];

// 카테고리
var categoryFg = [
    {"name":"전체","value":""},
    {"name":"대출/금융","value":"loan"},
    {"name":"불법도박","value":"gambling"},
    {"name":"성인/음란","value":"adult"},
    {"name":"스미싱/피싱","value":"phishing"},
    {"name":"불법의약품/마약","value":"illegal_drug"},
    {"name":"불법사기","value":"scam"},
    {"name":"기타","value":"other"},
    {"name":"대출/금융 다중구성","value":"loan_multi"},
    {"name":"불법도박 다중구성","value":"gambling_multi"},
    {"name":"성인/음란 다중구성","value":"adult_multi"},
    {"name":"스미싱/피싱 다중구성","value":"phishing_multi"},
    {"name":"불법의약품/마약 다중구성","value":"illegal_drug_multi"},
    {"name":"불법사기 다중구성","value":"scam_multi"},
    {"name":"기타 다중구성","value":"other_multi"},
    {"name":"조합 다중구성","value":"multi_com"},
    {"name":"URL","value":"url"},
    {"name":"전화번호","value":"telNo"}
];

app.controller('msgBlockLogCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgBlockLogCtrl', $scope, $http, $timeout, true));

    // 조회일자 셋팅
    var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("blockType", blockTypeFg);   // 차단 원인 유형
    $scope._setComboData("msgStatus", msgStatusFg);   // 메시지 상태

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.blockTypeDataMap  = new wijmo.grid.DataMap(blockTypeFg.slice(1),  'value', 'name');      // 차단 원인 유형
        $scope.msgStatusDataMap  = new wijmo.grid.DataMap(msgStatusFg.slice(1),  'value', 'name');      // 메시지 상태
        $scope.categoryDataMap  = new wijmo.grid.DataMap(categoryFg.slice(1),  'value', 'name'); // 카테고리
    };

    $scope.$on("msgBlockLogCtrl", function (event, data) {
        // 조회
        $scope.searchMsgBlockLog();
        event.preventDefault();
    });

    // 조회
    $scope.searchMsgBlockLog = function () {
        var params = {};
        params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
        params.blockType = $scope.blockTypeCombo.selectedValue;
        params.triggeredKeyword = $("#triggeredKeyword").val();
        params.msgStatus = $scope.msgStatusCombo.selectedValue;
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/adi/sms/smsBadword/msgBlockLog/getMsgBlockLogList.sb", params, function () {}, false);
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
            }, 'SMS금칙어_탐지/차단 결과 로그_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
