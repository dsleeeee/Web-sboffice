/****************************************************************
 *
 * 파일명 : cardCoSaleMrpizza.js
 * 설  명 : 미스터피자 > 마케팅조회 > 카드사별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.07     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 구분
var payFgData = [
    {"name": "전체", "value": ""},
    {"name": "카드", "value": "카드"},
    {"name": "가승인", "value": "가승인"}
];

/**
 *  카드사별매출 그리드 생성
 */
app.controller('cardCoSaleMrpizzaCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cardCoSaleMrpizzaCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope._setComboData("payFg", payFgData); // 구분 콤보박스

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("cardCoSaleMrpizzaCtrl", function (event, data) {

        // 카드사별매출 리스트 조회
        $scope.searchCardCoSaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 카드사별매출 리스트 조회
    $scope.searchCardCoSaleList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if ($("#cardCoSaleMrpizzaStoreCd").val().length > 0 && $("#cardCoSaleMrpizzaStoreCd").val().split(",").length - 1 === 0) {
            // 조회일자 최대 6달(186일) 제한
            if (diffDay > 186) {
                $scope._popMsg(messages['cmm.dateOver.6month.error']);
                return false;
            }
        } else {
            // 조회일자 최대 1달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCds = $("#cardCoSaleMrpizzaStoreCd").val();
        params.payFg = $scope.payFgCombo.selectedValue;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/mrpizza/cardCoSaleMrpizza/getCardCoSaleMrpizzaList.sb", params, function () {

            // 조회날짜 기준 엑셀 다운로드 기간
            $scope.excelStartDate = params.startDate;
            $scope.excelEndDate   = params.endDate;
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 엑셀다운로드 기간
        var startDt = $scope.excelStartDate;
        var endDt = $scope.excelEndDate;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "카드사별매출" + '_' + startDt + '_' + endDt + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);