/****************************************************************
 *
 * 파일명 : acMonthTransfer.js
 * 설  명 : 벤슨 > 회계관리 > 회계관리 > 월별전송 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.14     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 항목2 (방식구분)
var option02ComboData = [
    {"name":"전체","value":""},
    {"name":"방식1","value":"1"},
    {"name":"방식2","value":"2"},
    {"name":"방식3","value":"3"}
];

// 항목3 (체크여부) : 그리드에 체크박스로 렌더링되지 않는 단순 검색조건이라 서버 VO는 String("Y"/"N")으로 처리
var option03ComboData = [
    {"name":"전체","value":""},
    {"name":"체크","value":"Y"},
    {"name":"미체크","value":"N"}
];

/**
 * 월별전송 그리드 생성
 */
app.controller('acMonthTransferCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acMonthTransferCtrl', $scope, $http, false));

    // 조회월 (월 단위 선택)
    var startMonth = new wijmo.input.InputDate('#srchMonthStartDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#srchMonthEndDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("option02Combo", option02ComboData); // 항목2
    $scope._setComboData("option03Combo", option03ComboData); // 항목3

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계 표시
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                wijmo.addClass(e.cell, 'wj-custom-readonly');
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("acMonthTransferCtrl", function (event, data) {
        $scope.searchAcMonthTransfer();
        event.preventDefault();
    });

    $scope.searchAcMonthTransfer = function () {
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt   = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작월이 종료월보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages["cmm.dateChk.error"]);
            return false;
        }
        // 조회월 최대 6개월 제한
        if (diffMonth > 6) {
            $scope._popMsg(messages["cmm.dateOver.6month.error"]);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth   = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCd    = $("#acMonthTransferStoreCd").val(); // 매장선택(멀티)
        params.option01   = $scope.option01;
        params.option02   = $scope.option02;
        params.option03   = $scope.option03;
        params.option04   = $scope.option04;

        $scope._inquiryMain("/accounting/accountingMain/acMonthTransfer/getAcMonthTransferList.sb", params, function () {});
    };
    // <-- //검색 호출 -->

    // 엑셀다운로드 (클라이언트 사이드 export)
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
            }, messages["acMonthTransfer.title"] + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
