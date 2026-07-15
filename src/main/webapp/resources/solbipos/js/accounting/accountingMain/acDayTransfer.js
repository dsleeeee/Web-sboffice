/****************************************************************
 *
 * 파일명 : acDayTransfer.js
 * 설  명 : 벤슨 > 회계관리 > 회계관리 > 일별전송 탭 JavaScript
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
 * 일별전송 그리드 생성
 */
app.controller('acDayTransferCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acDayTransferCtrl', $scope, $http, false));

    // 조회일자 (시작/종료 서로 범위 제한)
    $scope.srchStartDate = wcombo.genDateStart("#srchDayStartDate", "acDayTransferCtrl", "srchEndDate");
    $scope.srchEndDate   = wcombo.genDateEnd("#srchDayEndDate", "acDayTransferCtrl", "srchStartDate");

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
    $scope.$on("acDayTransferCtrl", function (event, data) {
        $scope.searchAcDayTransfer();
        event.preventDefault();
    });

    $scope.searchAcDayTransfer = function () {
        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt   = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages["cmm.dateChk.error"]);
            return false;
        }
        // 조회일자 최대 3개월(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages["cmm.dateOver.3month.error"]);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd   = $("#acDayTransferStoreCd").val(); // 매장선택(멀티)
        params.option01  = $scope.option01;
        params.option02  = $scope.option02;
        params.option03  = $scope.option03;
        params.option04  = $scope.option04;

        $scope._inquiryMain("/accounting/accountingMain/acDayTransfer/getAcDayTransferList.sb", params, function () {});
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
            }, messages["acDayTransfer.title"] + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
