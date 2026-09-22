/****************************************************************
 *
 * 파일명 : posRcvSaleMoms.js
 * 설  명 : 맘스터치 > 정산 > POS내역수신(매출) JavaScript
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

var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "온라인포장", "value": "3"},
    {"name": "내점포장", "value": "4"}
];

/**
 *  POS내역수신(매출) 그리드 생성
 */
app.controller('posRcvSaleMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posRcvSaleMomsCtrl', $scope, $http, false));

    // 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["posRcvSaleMoms.saleY"]},
        {id: "N", name: messages["posRcvSaleMoms.saleN"]}
    ], 'id', 'name');

    // 조회일자
    var srchDate = wcombo.genDateVal("#srchDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(dlvrInFg, 'value', 'name'); // 채널타입
        $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달구분
    };
    
    $scope.$on("posRcvSaleMomsCtrl", function (event, data) {
        // 조회
        $scope.searchPosRcvSaleMomsList();
        event.preventDefault();
    });

    // 조회
    $scope.searchPosRcvSaleMomsList = function () {

        // 조회일자 필수 체크
        if (!srchDate.value) {
            $scope._popMsg(messages['cmm.require.select']);
            return false;
        }

        // 매장선택 필수 체크
        if (!$("#posRcvSaleMomsStoreCd").val()) {
            $scope._popMsg(messages['cmm.require.selectStore']);
            return false;
        }

        // 파라미터
        var params = {};
        params.saleDate = wijmo.Globalize.format(srchDate.value, 'yyyyMMdd');
        params.storeCd = $("#posRcvSaleMomsStoreCd").val();

        $scope._inquiryMain("/sale/moms/posRcvSaleMoms/posRcvSaleMoms/getPosRcvSaleMomsList.sb", params, function () {

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
                "POS내역수신(매출)" + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
