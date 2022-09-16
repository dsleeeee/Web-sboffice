/****************************************************************
 *
 * 파일명 : prodClassPayFgSale.js
 * 설  명 : 분류별결제수단별 매출내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분 DropBoxDataMap
var vLevel = [
    {"name":"대분류 기준","value":"1"},
    {"name":"중분류 기준","value":"2"},
    {"name":"소분류 기준","value":"3"}
];

/**
 *  분류별결제수단별 매출내역 조회 그리드 생성
 */
app.controller('prodClassPayFgSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassPayFgSaleCtrl', $scope, $http, true));
    
    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 매출구분 콤보박스 데이터 Set
    $scope._setComboData("prodClassLevelCombo", vLevel);

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
    $scope.$on("prodClassPayFgSaleCtrl", function(event, data) {
        // 분류별결제수단별 매출내역 조회
        $scope.searchProdClassPayFgSaleList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 분류별결제수단별 매출내역 조회
    $scope.searchProdClassPayFgSaleList = function(){
        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd'); //조회기간
        params.level = $scope.prodClassLevelCombo.selectedValue;
        params.payCol = payCol;
        params.storeCd = $("#prodClassPayFgSaleStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/reportKwu/prodClassPayFgSale/getList.sb", params, function() {
            
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
    $scope.prodClassPayFgSaleStoreShow = function () {
        $scope._broadcast('prodClassPayFgSaleStoreCtrl');
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
              }, '분류별결제수단별매출내역_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
              });
        }, 10);
    };

}]);