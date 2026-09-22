/****************************************************************
 *
 * 파일명 : posRcvPayMoms.js
 * 설  명 : 맘스터치 > 정산 > POS내역수신(결제) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.18     이다솜      1.0            최초생성
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  POS내역수신(결제) 그리드 생성
 */
app.controller('posRcvPayMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posRcvPayMomsCtrl', $scope, $http, false));

    // 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["posRcvSaleMoms.saleY"]},
        {id: "N", name: messages["posRcvSaleMoms.saleN"]}
    ], 'id', 'name');

    // 조회일자
    var srchDate = wcombo.genDateVal("#srchDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.payCdDataMap = new wijmo.grid.DataMap(payCd, 'value', 'name'); // 결제수단
    };

    $scope.$on("posRcvPayMomsCtrl", function (event, data) {
        // 조회
        $scope.searchPosRcvPayMomsList();
        event.preventDefault();
    });

    // 조회
    $scope.searchPosRcvPayMomsList = function () {

        // 조회일자 필수 체크
        if (!srchDate.value) {
            $scope._popMsg(messages['cmm.require.select']);
            return false;
        }
        // 매장선택 필수 체크
        if (!$("#posRcvPayMomsStoreCd").val()) {
            $scope._popMsg(messages['cmm.require.selectStore']);
            return false;
        }

        // 파라미터
        var params = {};
        params.saleDate = wijmo.Globalize.format(srchDate.value, 'yyyyMMdd');
        params.storeCd = $("#posRcvPayMomsStoreCd").val();

        $scope._inquiryMain("/sale/moms/posRcvPayMoms/posRcvPayMoms/getPosRcvPayMomsList.sb", params, function () {

        });
    };

    // 현재화면 엑셀다운로드
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
            },
                "POS내역수신(결제)" + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
