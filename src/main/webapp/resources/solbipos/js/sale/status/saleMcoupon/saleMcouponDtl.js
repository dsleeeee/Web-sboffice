/****************************************************************
 *
 * 파일명 : saleMcouponDtl.js
 * 설  명 : 모바일쿠폰 현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.04.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분
var saleYnDataMapData = [
    {"name":"매출","value":"Y"},
    {"name":"반품","value":"N"}
];

/**
 *  모바일쿠폰 현황 상세 팝업 조회 그리드 생성
 */
app.controller('saleMcouponDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleMcouponDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleYnDataMap = new wijmo.grid.DataMap(saleYnDataMapData, 'value', 'name'); // 매출구분

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("saleMcouponDtlCtrl", function(event, data) {
        $scope.searchSaleMcouponDtl(data);
        event.preventDefault();
    });

    $scope.searchSaleMcouponDtl = function(data){
        var params = {};
        params.storeCd = data.storeCd;
        if(orgnFg === "HQ"){
            params.startDate = data.startDate;
            params.endDate = data.endDate;
        } else if(orgnFg === "STORE"){
            params.saleDate = data.saleDate.replaceAll('-', '');
        }

        $scope._inquiryMain("/sale/status/saleMcoupon/saleMcoupon/getSaleMcouponDtlList.sb", params, function() {}, false);
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
            },  '모바일쿠폰 현황_상세_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjSaleMcouponDtlLayer.hide();
    };

}]);