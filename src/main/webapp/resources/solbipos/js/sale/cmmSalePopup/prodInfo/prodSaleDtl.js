/****************************************************************
 *
 * 파일명 : prodSaleDtl.js
 * 설  명 : 상품매출 상세내역 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 *                            1.0
 * 2019.12.11     김설아      1.1
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('prodSaleDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSaleDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("prodSaleDtlCtrl", function(event, data) {
        $scope.wjProdSaleDtlLayer.show(true);
        $scope.searchProdSaleDtlList(data);
        event.preventDefault();
    });

    $scope.searchProdSaleDtlList = function(data){
        var params = {};
        params.storeCds  = data.storeCd;
        params.gubun  = data.gubun;

        // 기간별매출 > 일자별 탭 > 과면세별,포스별,코너별 탭
        if(data.gubun == "day" ||  data.gubun == "dayCorner") {
            params.saleDate = data.saleDate;
            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlDayList.sb", params, function() {}, false);
        }
        // 기간별매출 > 일자별 탭 > 포스별 탭
        else if(data.gubun == "dayPos") {
            params.saleDate = data.saleDate;
            params.posNo  = data.posNo;
            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlDayList.sb", params, function() {}, false);
        }
        // 기간별매출 > 월별 탭 > 과면세별,포스별,코너별 탭
        else if(data.gubun == "month" ||  data.gubun == "monthCorner") {
            params.yearMonth = data.yearMonth;
            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlMonthList.sb", params, function() {}, false);
        }
        // 기간별매출 > 월별 탭 > 포스별 탭
        else if(data.gubun == "monthPos") {
            params.yearMonth = data.yearMonth;
            params.posNo  = data.posNo;
            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlMonthList.sb", params, function() {}, false);
        }
        // 기간별매출 > 일자별 탭 > 상품분류별 탭
        else if(data.gubun == "dayProdClass") {
            params.saleDate = data.saleDate;
            params.strProdClassCd  = data.strProdClassCd;
            params.level = data.level;
            params.prodCd = data.prodCd;
            params.prodNm = data.prodNm;
            params.barCd = data.barCd;
            params.prodClassCd = data.prodClassCd;

            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlDayProdClassList.sb", params, function() {}, false);
        }
        // 기간별매출 > 월별 탭 > 상품분류별 탭
        else if(data.gubun == "monthProdClass") {
            params.yearMonth = data.yearMonth;
            params.strProdClassCd  = data.strProdClassCd;
            params.level = data.level;
            params.prodCd = data.prodCd;
            params.prodNm = data.prodNm;
            params.barCd = data.barCd;
            params.prodClassCd = data.prodClassCd;

            $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/getProdSaleDtlMonthProdClassList.sb", params, function() {}, false);
        }
        // $scope._inquiryMain("/sale/cmmSalePopup/prodInfo/prodSaleDtl/list.sb", params, function() {}, false);
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
            }, messages["prodSaldDtl.prodSaleDtl"] + '_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);