/****************************************************************
 *
 * 파일명 : cardCreditStatus.js
 * 설  명 : 신용카드입금현황 (광운대 아이스링크) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.16     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 일자구분
var dateFgComboData = [
    {"name":"승인일자","value":"1"},
    {"name":"영업일자","value":"2"},
    {"name":"입금일자","value":"3"}
];
// 입금은행
var creditBankData = [
    {"name":"국민은행","value":"국민은행"},
    {"name":"하나은행","value":"하나은행"}
];
// 입금은행
var creditBankComboData = [
    {"name":"전체","value":""},
    {"name":"국민은행","value":"국민은행"},
    {"name":"하나은행","value":"하나은행"}
];
// 입금구분
var creditFgComboData = [
    {"name":"전체","value":""},
    {"name":"미입금","value":"0"},
    {"name":"입금","value":"1"}
];
// 취소내역포함여부
var rtnSaleFgComboData = [
    {"name":"미포함","value":"0"},
    {"name":"포함","value":"1"}
];

/**
 *  신용카드입금현황 그리드 생성
 */
app.controller('cardCreditStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cardCreditStatusCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("dateFgCombo", dateFgComboData); // 일자구분
    $scope._setComboData("creditBankCombo", creditBankComboData); // 입금은행
    $scope._setComboData("creditFgCombo", creditFgComboData); // 입금구분
    $scope._setComboData("rtnSaleFgCombo", rtnSaleFgComboData); // 취소내역포함여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.creditBankDataMap = new wijmo.grid.DataMap(creditBankData, 'value', 'name'); // 입금은행

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("cardCreditStatusCtrl", function(event, data) {
        $scope.searchCardCreditStatus();
        event.preventDefault();
    });

    $scope.searchCardCreditStatus = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.dateFg = $scope.dateFgCombo;
        params.creditBank = $scope.creditBankCombo;
        params.creditFg = $scope.creditFgCombo;
        params.rtnSaleFg = $scope.rtnSaleFgCombo;

        $scope._inquiryMain("/sale/card/cardCreditStatus/cardCreditStatus/getCardCreditStatusList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                '신용카드입금현황_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);