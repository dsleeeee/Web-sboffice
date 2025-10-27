/****************************************************************
 *
 * 파일명 : saleRegistChargeKmuMember.js
 * 설  명 : 회원선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원선택 팝업 조회 그리드 생성
 */
app.controller('saleRegistChargeKmuMemberCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleRegistChargeKmuMemberCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 회원코드
                if (col.binding === "membrNo") {
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
                if (col.binding === "membrNo") {
                    $scope._popMsg("선택한 회원 : [" + selectedRow.membrNo + "] " + selectedRow.membrNm);

                    $("#membrNo").val(selectedRow.membrNo);
                    $("#membrNm").val(selectedRow.membrNm);

                    // 팝업 닫기
                    $scope.close();
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("saleRegistChargeKmuMemberCtrl", function(event, data) {
        $scope.searchSaleRegistChargeKmuMember();
        event.preventDefault();
    });

    $scope.searchSaleRegistChargeKmuMember = function(){
        var params = {};
        params.srchMembrNo = $scope.srchMembrNo;
        params.srchMembrNm = $scope.srchMembrNm;

        $scope._inquiryMain("/excclc/excclc/saleRegistChargeKmu/saleRegistChargeKmu/getSaleRegistChargeKmuMemberList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.srchMembrNo = "";
        $scope.srchMembrNm = "";

        $scope.wjSaleRegistChargeKmuMemberLayer.hide();
    };

}]);