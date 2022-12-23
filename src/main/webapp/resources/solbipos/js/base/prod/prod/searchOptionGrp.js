/****************************************************************
 *
 * 파일명 : searchOptionGrp.js
 * 설  명 : 상품옵션그룹 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품옵션그룹 조회 그리드 생성
 */
app.controller('searchOptionGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchOptionGrpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "optionGrpCd"){ // 상품옵션그룹코드
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
                if (col.binding === "optionGrpCd") { // 상품옵션그룹코드 클릭
/*
                    var scope = agrid.getScope('prodModifyCtrl');
                    scope.prodModifyInfo.optionGrpCd = selectedRow.optionGrpCd;
                    scope.prodModifyInfo.optionGrpNm = selectedRow.optionGrpNm;*/

                    $("#_optionGrpCd").val(selectedRow.optionGrpCd);
                    $("#_optionGrpNm").val(selectedRow.optionGrpNm);

                    $scope.wjSearchOtionGrpLayer.hide();

                }
            }
        });

    };

    $scope.$on("searchOptionGrpCtrl", function(event, data) {

        // 상품옵션그룹 리스트 조회
        $scope.searchOptionGrp();
        event.preventDefault();
    });

    // 상품옵션그룹 리스트 조회
    $scope.searchOptionGrp = function () {

        var params = {};
        params.optionGrpNm = $("#searchOptionGrpNm").val();

        $scope._inquirySub("/base/prod/prod/prod/getSearchOptionGrpList.sb", params, function() {}, false);
    }

}]);