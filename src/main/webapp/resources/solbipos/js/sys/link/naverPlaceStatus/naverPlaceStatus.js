/****************************************************************
 *
 * 파일명 : naverPlaceStatus.js
 * 설  명 : 네이버플레이스현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.03.11     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var viewFgComboData = [
    {"name":"사용자현황","value":"user"},
    {"name":"접속현황","value":"connect"}
];

/**
 *  네이버플레이스현황 그리드 생성
 */
app.controller('naverPlaceStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceStatusCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope._setComboData("viewFg", viewFgComboData); // 구분 콤보박스

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'reset') {
                    wijmo.addClass(e.cell, 'red');
                    e.cell.textContent = '초기화';
                }
            }
        });

        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "reset") {

                    selectedRow.resrceCd = menuCd;
                    selectedRow.pathNm = "시스템관리-연동-네이버플레이스현황-초기화 팝업";

                    // 초기화 팝업
                    $scope.wjNaverPlaceStatusResetLayer.show(true);
                    $scope._broadcast('naverPlaceStatusResetCtrl', selectedRow);

                    // 사용자 행위 기록
                    var actParams = {};
                    actParams.resrceCd = menuCd;
                    actParams.pathNm = "시스템관리-연동-네이버플레이스현황";
                    actParams.contents = "'초기화' 컬럼 클릭 시";

                    $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});
                }
            }
        });
    };

    $scope.$on("naverPlaceStatusCtrl", function (event, data) {

        // 조회
        $scope.searchNaverplaceStatus();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchNaverplaceStatus = function () {

        var url = "/sys/link/naverPlaceStatus";
        var params = {};

        if ($scope.viewFgCombo.selectedValue === "user") { // 사용자현황 조회
            url = url + "/getUserStatusList.sb";
            $("#wjGridUser").css("display", "");
            $("#wjGridConnect").css("display", "none");
        }
        if ($scope.viewFgCombo.selectedValue === "connect") { // 접속현황 조회
            url = url + "/getConnectStatusList.sb";
            params.resrceNm = '네이버플레이스 플러스 연동';
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
            $("#wjGridUser").css("display", "none");
            $("#wjGridConnect").css("display", "");
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain(url, params, function () {

        });
    };

    // 조회기간 히든처리
    $scope.setSrchDate = function (s) {
        if (s.selectedValue === "user") {
            $("#thSrchDate").css("display", "none");
            $("#tdSrchDate").css("display", "none");
        } else {
            $("#thSrchDate").css("display", "");
            $("#tdSrchDate").css("display", "");
        }
    };

}]);