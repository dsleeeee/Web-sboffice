/****************************************************************
 *
 * 파일명 : couponSelectPart.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰정보관리 > 상품 선택 팝업JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.24     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/** 쿠폰관리 그리드 controller */
app.controller('couponSelectProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponSelectProdCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "prodCd") { // 상품코드
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 상품코드
                if (col.binding === "prodCd") {
                    $("#selectProdCd").val(selectedRow.prodCd);
                    $("#selectProd").val('[' + selectedRow.prodCd +'] ' + selectedRow.prodNm);
                    $scope.couponSelectProdLayer.hide();
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("couponSelectProdCtrl", function (event, data) {
        $scope.storeCd = data.storeCd;
        $scope.searchProd();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품정보 조회
    $scope.searchProd = function () {

        // 파라미터
        var params  = {};
        params.storeCd = $scope.storeCd;
        params.prodCd = $scope.srchSelectProdCd;
        params.prodNm = $scope.srchSelectProdNm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/coupon/couponInfo/couponInfo/getCouponSelectProdList.sb", params, function () {
        });
    };

    //


}]);
