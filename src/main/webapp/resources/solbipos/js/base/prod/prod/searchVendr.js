/****************************************************************
 *
 * 파일명 : searchVendr.js
 * 설  명 : 상품 팝업 - 매입처/매장 추가 팝업 - 거래처 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.14    김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  거래처 조회 그리드 생성
 */
app.controller('searchVendrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchVendrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "vendrCd"){ // 거래처코드
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
                if (col.binding === "vendrCd") { // 거래처코드 클릭
                    var scope = agrid.getScope('prodVendrRegistCtrl');
                    scope.addRow(selectedRow);
                    $scope.close();
                }
            }
        });

    };

    $scope.$on("searchVendrCtrl", function(event, data) {

        $scope.wjSearchVendrLayer.show(true);
        // 거래처 리스트 조회
        $scope.getSearchVendr();
        event.preventDefault();
    });

    // 거래처 리스트 조회
    $scope.getSearchVendr = function () {

        var params = {};
        params.vendrCd = $("#searchVendrCd").val();
        params.vendrNm = $("#searchVendrNm").val();

        $scope._inquiryMain("/base/prod/prod/prod/getSearchVendr.sb", params, function() {});
    }

    // 닫기
    $scope.close = function (){
        $("#searchVendrCd").val("");
        $("#searchVendrNm").val("");
        $scope.wjSearchVendrLayer.hide();
    }

}]);