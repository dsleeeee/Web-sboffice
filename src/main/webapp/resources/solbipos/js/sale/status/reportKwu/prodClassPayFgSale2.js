/****************************************************************
 *
 * 파일명 : prodClassPayFgSale2.js
 * 설  명 : 분류상품별 결제수단 매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.30     김중선      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  분류별결제수단별 매출내역 조회 그리드 생성
 */
app.controller('prodClassPayFgSale2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassPayFgSale2Ctrl', $scope, $http, true));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        /*s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 분류없는매출 '-' 처리
                if(col.binding === "levelCd" || col.binding === "levelNm"){
                    if(item.levelNm === "NO_DATA_FOUND"){
                        e.cell.innerHTML = "-";
                    }
                }
            }
        });*/
    };

    //
    $scope.$on("prodClassPayFgSale2Ctrl", function(event, data) {
        // 분류별결제수단별 매출내역 조회
        $scope.searchProdClassPayFgSale2List();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 분류별결제수단별 매출내역 조회
    $scope.searchProdClassPayFgSale2List = function(){
        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd'); //조회기간
        params.payCol = payCol;
        params.storeCd = $("#prodClassPayFgSale2StoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/reportKwu/prodClassPayFgSale2/getList.sb", params, function() {

            // 분류없는매출 '-' 처리
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.levelNm === "NO_DATA_FOUND"){
                    item.levelCd = "-";
                    item.levelNm = "-";
                }
            }
        });
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodClassPayFgSale2StoreShow = function () {
        $scope._broadcast('prodClassPayFgSale2StoreCtrl');
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
              }, '분류상품별결제수단매출_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
              });
        }, 10);
    };

}]);