/****************************************************************
 *
 * 파일명 : userBase.js
 * 설  명 : 사용자기준 사용현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용환경 DropBoxDataMap
var useEnvData = [
    {"name":"웹","value":"1"}
];
// 대,중,소메뉴 DropBoxDataMap
var levelData = [
    {"name":"본사명","value":"1"},
    {"name":"본사명/매장명","value":"2"},
    {"name":"본사명/매장명/사용자명","value":"3"}
];

/**
 *  사용자기준 사용현황 그리드 생성
 */
app.controller('userBaseCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('userBaseCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useEnvCombo", useEnvData);
    $scope._setComboData("levelCombo", levelData);

    $("#userLayer").show();
    $("#useMenuLayer").hide();

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 사용횟수
                if (col.binding === "useCnt") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 사용횟수 클릭시 상세정보 조회
                if ( col.binding === "useCnt") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
                    params.resrceNm = $("#srchResrceNm").val();
                    params.level = $scope.level;

                    // 값이 있으면 링크 효과
                    if (nvl(selectedRow[("userId")], '') !== '') {
                        params.userId = selectedRow.userId;
                    }
                    if (nvl(selectedRow[("hqOfficeCd")], '') !== '') {
                        params.hqOfficeCd = selectedRow.hqOfficeCd;
                    }
                    if (nvl(selectedRow[("storeCd")], '') !== '') {
                        params.storeCd = selectedRow.storeCd;
                    }

                    var storeScope = agrid.getScope('userCtrl');
                    storeScope._broadcast('userCtrl', params);
                    var storeScope2 = agrid.getScope('useMenuCtrl');
                    storeScope2._broadcast('useMenuCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("userBaseCtrl", function(event, data) {
        $("#userLayer").show();
        $("#useMenuLayer").hide();

        $scope.searchUserBase();
        event.preventDefault();
    });

    $scope.searchUserBase = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.level = $scope.level;

        $scope._inquiryMain("/sys/stats/userBase/userBase/getUserBaseList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('userCtrl');
                storeScope._gridDataInit();
                var storeScope2 = agrid.getScope('useMenuCtrl');
                storeScope2._gridDataInit();
            });

            // 선택한 분류에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridUserBaseList");
            var columns = grid.columns;

            if($scope.level === "1") { //대분류
                columns[0].visible = true;
                columns[1].visible = true;
                columns[2].visible = false;
                columns[3].visible = false;
                columns[4].visible = false;
                columns[5].visible = true;
            } else if($scope.level  === "2") { //중분류
                columns[0].visible = true;
                columns[1].visible = true;
                columns[2].visible = true;
                columns[3].visible = true;
                columns[4].visible = false;
                columns[5].visible = true;
            } else if($scope.level  === "3") { //소분류
                columns[0].visible = true;
                columns[1].visible = true;
                columns[2].visible = true;
                columns[3].visible = true;
                columns[4].visible = true;
                columns[5].visible = true;
            }

        }, false);
    };
    // <-- //검색 호출 -->

}]);