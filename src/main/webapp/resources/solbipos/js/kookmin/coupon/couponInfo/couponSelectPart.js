/****************************************************************
 *
 * 파일명 : couponSelectPart.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰정보관리 > 부서 선택 팝업JavaScript
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


/** 팝업 그리드 controller */
app.controller('couponSelectPartCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponSelectPartCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "vendrCd") { // 상품코드
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
                if (col.binding === "vendrCd") {
                    $("#selectPartCd").val(selectedRow.vendrCd);
                    $("#selectPart").val('[' + selectedRow.vendrCd +'] ' + selectedRow.vendrNm);
                    $scope.couponSelectPartLayer.hide();
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("couponSelectPartCtrl", function (event, data) {
        $scope.searchPart();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 부서정보 조회
    $scope.searchPart = function () {

        // 파라미터
        var params  = {};
        params.partCd = $scope.srchSelectPartCd;
        params.partNm = $scope.srchSelectPartNm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/coupon/couponInfo/couponInfo/getcouponSelectPartList.sb", params, function () {
        });
    };

    //


}]);
