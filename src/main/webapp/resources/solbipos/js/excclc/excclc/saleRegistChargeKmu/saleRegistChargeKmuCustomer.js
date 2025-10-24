/****************************************************************
 *
 * 파일명 : saleRegistChargeKmuCustomer.js
 * 설  명 : 매출처선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.22     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출처선택 팝업 조회 그리드 생성
 */
app.controller('saleRegistChargeKmuCustomerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleRegistChargeKmuCustomerCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 매출처코드
                if (col.binding === "customerCd") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 회원코드
                if (col.binding === "customerCd") {
                    // 매출처 선택 값 넣기
                    var scope = agrid.getScope("newRegistCtrl");
                    selectedRow.rowNum = $("#lblRowNum").text();
                    scope.customerChange(selectedRow);

                    // 팝업 닫기
                    $scope.close();
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("saleRegistChargeKmuCustomerCtrl", function(event, data) {
        $("#lblRowNum").text(data.rowNum);
        $scope.searchSaleRegistChargeKmuCustomer();
        event.preventDefault();
    });

    $scope.searchSaleRegistChargeKmuCustomer = function(){
        var params = {};
        params.srchCustomerCd = $scope.srchCustomerCd;
        params.srchCustomerNm = $scope.srchCustomerNm;

        $scope._inquiryMain("/excclc/excclc/saleRegistChargeKmu/saleRegistChargeKmu/getSaleRegistChargeKmuCustomerList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.srchCustomerCd = "";
        $scope.srchCustomerNm = "";

        $scope.wjSaleRegistChargeKmuCustomerLayer.hide();
    };

}]);