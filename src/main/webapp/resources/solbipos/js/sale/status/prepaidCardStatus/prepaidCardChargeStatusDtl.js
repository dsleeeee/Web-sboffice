/****************************************************************
 *
 * 파일명 : prepaidCardChargeStatusDtlCtrl.js
 * 설  명 : 선불카드 충전 현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.02     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 
 */
app.controller('prepaidCardChargeStatusDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prepaidCardChargeStatusDtlCtrl', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    $scope.$on("prepaidCardChargeStatusDtlCtrl", function (event, data) {

        // 조회
        $scope.searchPrepaidCardChargeStatusDtl(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchPrepaidCardChargeStatusDtl = function (data) {

        // 파라미터
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.saleYn = data.saleYn;
        params.storeCd = data.storeCd;
        if (orgnFg === "STORE") {
            params.saleDate = data.saleDate;
        }

        $scope._inquirySub("/sale/status/prepaidCardStatus/getPrepaidCardChargeStatusDtl.sb", params, function () {});
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(
                $scope.flex,
                {
                    includeColumnHeaders: true,
                    includeCellStyles: true,
                    formatItem: saveFormatItem,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },
                messages["prepaidCardStatus.prepaidCardChargeStatus"] + '_상세_' + getCurDate('-') + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);

function saveFormatItem(args) {
    let p = args.panel, row = args.row, col = args.col, xlsxCell = args.xlsxCell, cell, color;
    if (p.cellType === wijmo.grid.CellType.Cell) {
        if (p.columns[col].binding === 'cardNo') {
            if (xlsxCell.value) {
                xlsxCell.style.format = "@"
            }
        }
    }
};