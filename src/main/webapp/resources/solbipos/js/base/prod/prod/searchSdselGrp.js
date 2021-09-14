/****************************************************************
 *
 * 파일명 : searchSdselGrp.js
 * 설  명 : 선택메뉴 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선택메뉴 조회 그리드 생성
 */
app.controller('searchSdselGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchSdselGrpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "sdselGrpCd"){ // 선택메뉴코드
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
                if (col.binding === "sdselGrpCd") { // 선택메뉴코드 클릭

                    var scope = agrid.getScope('prodModifyCtrl');
                    scope.prodModifyInfo.sdselGrpNm = selectedRow.sdselGrpNm;
                    scope.prodModifyInfo.sdselGrpCd = selectedRow.sdselGrpCd;

                    $scope.wjSearchSdselGrpLayer.hide();

                }
            }
        });
        
    };

    $scope.$on("searchSdselGrpCtrl", function(event, data) {
        
        // 선택메뉴 리스트 조회
        $scope.searchSdselGrp();
        event.preventDefault();
    });

    // 선택메뉴 리스트 조회
    $scope.searchSdselGrp = function () {

        var params = {};
        params.sdselGrpNm = $("#searchSdselGrpNm").val();

        $scope._inquirySub("/base/prod/prod/prod/getSearchSdselGrpList.sb", params, function() {}, false);
    }

}]);