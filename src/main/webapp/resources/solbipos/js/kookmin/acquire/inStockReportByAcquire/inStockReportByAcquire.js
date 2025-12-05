/****************************************************************
 *
 * 파일명 : inStockReportByAcquire.js
 * 설  명 : 국민대 > 매입처관리 > 매입처별 입고 내역서 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.12.02     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 입고내역서 그리드 controller */
app.controller('inStockReportByAcquireCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('inStockReportByAcquireCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("inStockReportByAcquireCtrl");

        s.mergeManager = new wijmo.grid.MergeManager(s);

        s.mergeManager.getMergedRange = function(panel, r, c) {

            // 1) 헤더 행 병합 (가로 방향)
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {

                var col = panel.columns[c];

                // 현재 셀 값
                var val = panel.getCellData(r, c, true);
                if (!val) return null;

                var rng = new wijmo.grid.CellRange(r, c);

                // 왼쪽으로 확장
                for (var i = c - 1; i >= 0; i--) {
                    if (panel.getCellData(r, i, true) === val) {
                        rng.col = i;
                    } else break;
                }

                // 오른쪽으로 확장
                for (var i = c + 1; i < panel.columns.length; i++) {
                    if (panel.getCellData(r, i, true) === val) {
                        rng.col2 = i;
                    } else break;
                }

                return rng.isSingleCell ? null : rng;
            }

            // 2) 데이터셀 병합 (prodClassNm 세로 병합)
            if (panel.cellType === wijmo.grid.CellType.Cell) {

                var col = panel.columns[c];
                if (col.binding !== 'prodClassNm') return null;

                var val = panel.getCellData(r, c, true);
                if (val == null) return null;

                var rng = new wijmo.grid.CellRange(r, c);

                // 위로 확장
                for (var i = r - 1; i >= 0; i--) {
                    if (panel.getCellData(i, c, true) === val) {
                        rng.row = i;
                    } else break;
                }

                // 아래로 확장
                for (var i = r + 1; i < panel.rows.length; i++) {
                    if (panel.getCellData(i, c, true) === val) {
                        rng.row2 = i;
                    } else break;
                }

                return rng.isSingleCell ? null : rng;
            }

            return null; // 나머지 셀은 병합 안함
        };


        // 행 전체 색상 변경
        // s.itemFormatter = function(panel, r, c, cell) {
        //     if (panel.cellType !== wijmo.grid.CellType.Cell) return;
        //
        //     // vendrNm 컬럼 값 가져오기
        //     var vendrVal = panel.getCellData(r, 'vendrNm', true);
        //
        //     if (vendrVal === '소계') {
        //         // 이 로우 전체 컬럼 색 변경
        //         cell.style.backgroundColor = '#f0f0f0';
        //         cell.style.fontWeight = 'bold';
        //     }
        // };

    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("inStockReportByAcquireCtrl", function (event, data) {
        $scope.getInStockReportByAcquireList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 입고내역서 조회
    $scope.getInStockReportByAcquireList = function () {

        // 매장(을)를 선택해주세요.
        if($("#inStockReportByAcquireStoreCd").val() === "") {
            $scope._popMsg(messages["cmm.require.selectStore"]);
            return false;
        }
        // 파라미터
        var params       = {};
        params.startDate    = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd      = $("#inStockReportByAcquireStoreCd").val();

        $scope.startDate    = params.startDate;
        $scope.endDate      = params.endDate;
        $scope.storeCd      = params.storeCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/acquire/inStockReportByAcquire/inStockReportByAcquire/getInStockReportByAcquireList.sb", params);
    };

    // 리포트
    $scope.print = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = {};
        params.startDate = $scope.startDate;
        params.endDate = $scope.endDate;
        params.storeCd = $scope.storeCd;
        $scope._broadcast('inStockReportByAcquireReportCtrl', params);

    };
}]);
