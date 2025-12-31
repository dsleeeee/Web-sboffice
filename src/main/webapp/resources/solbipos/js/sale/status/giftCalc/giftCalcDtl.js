/****************************************************************
 *
 * 파일명 : giftCalcDtl.js
 * 설  명 : 지류상품권 정산 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  지류상품권 정산 상세 팝업 조회 그리드 생성
 */
app.controller('giftCalcDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('giftCalcDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgComboData, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("giftCalcDtlCtrl", function(event, data) {
        $scope.searchGiftCalcDtl(data);
        event.preventDefault();
    });

    $scope.searchGiftCalcDtl = function(data){
        var params = data;

        $.postJSON("/sale/status/giftCalc/giftCalc/getGiftCalcDtlList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;
        });
    };
    // <-- //검색 호출 -->

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },  '지류상품권 정산_상세_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjGiftCalcDtlLayer.hide();
    };

}]);