/****************************************************************
 *
 * 파일명 : dayProdSaleStoreDtl.js
 * 설  명 : 매출관리 > 매출현황2 > 일별상품매출현황(매장별) > 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.01.09    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일별상품매출현황(매장별) 상세 팝업
 */
app.controller('dayProdSaleStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdSaleStoreDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 상품 구분
        $scope.selTypeFgMap = new wijmo.grid.DataMap(selTypeFgData, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdSaleStoreDtlCtrl", function (event, data) {
        // 조회
        $scope.searchDtlList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    // 조회
    $scope.searchDtlList = function (data) {

        // 파라미터
        var params = data;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/dayProdSaleStore/getDayProdSaleStoreDtl.sb", params);
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
            }, messages["dayProdSaleStore.dayProdSaleStore"]+ '_' +  messages["dayProdSaleStore.saleDtl"] + '_'+ getToday()+'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
