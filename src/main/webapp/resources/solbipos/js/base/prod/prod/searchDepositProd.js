/****************************************************************
 *
 * 파일명 : searchDepositProd.js
 * 설  명 : 보증금상품코드 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.25     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  단품/세트선택설정 조회 그리드 생성
 */
app.controller('searchDepositProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchDepositProdCtrl', $scope, $http, true));

    // 콤보박스 데이터
    // 상품유형 콤보박스
    $scope._getComboDataQuery('008', 'searchProdTypeFg');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd"){ // 단품/세트선택설정코드
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {

            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") { // 단품/세트선택설정코드 클릭

                    $("#_depositProdCd").val(selectedRow.prodCd);
                    $("#_depositProdNm").val(selectedRow.prodNm);

                    $scope.wjSearchDepositProdLayer.hide();

                }
            }
        });

    };

    $scope.$on("searchDepositProdCtrl", function(event, data) {
        $scope.searchProdTypeFg = '4';
        // 단품/세트선택설정 리스트 조회
        $scope.searchDepositProd();
        event.preventDefault();
    });

    // 단품/세트선택설정 리스트 조회
    $scope.searchDepositProd = function () {

        var params = {};
        params.searchProdCd = $("#searchProdCd").val();
        params.searchProdNm = $("#searchProdNm").val();
        params.prodTypeFg   = $scope.searchProdTypeFg;

        if(brandUseFg === "1" && orgnFg === "HQ"){

            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquirySub("/base/prod/prod/prod/getSearchDepositProdList.sb", params, function() {}, false);
    }

}]);