/****************************************************************
 *
 * 파일명 : menuBase.js
 * 설  명 : 메뉴기준 사용현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.22     김설아      1.0
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
    {"name":"대메뉴","value":"1"},
    {"name":"대메뉴/중메뉴","value":"2"},
    {"name":"대메뉴/중메뉴/소메뉴","value":"3"}
];

/**
 *  메뉴기준 사용현황 그리드 생성
 */
app.controller('menuBaseCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuBaseCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useEnvCombo", useEnvData);
    $scope._setComboData("levelCombo", levelData);

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
                    params.userId = $("#srchUserId").val();
                    params.userNm = $("#srchUserNm").val();
                    params.hqOfficeCd = $("#srchHqOfficeCd").val();
                    params.hqOfficeNm = $("#srchHqOfficeNm").val();
                    params.storeCd = $("#srchStoreCd").val();
                    params.storeNm = $("#srchStoreNm").val();
                    params.level = $scope.level;

                    if($scope.level === "1") {
                        params.resrceCd = selectedRow.level1;

                    } else if($scope.level === "2") {
                        params.resrceCd = selectedRow.level2;

                        // 값이 있으면 링크 효과
                        if (nvl(selectedRow[("level2")], '') !== '') {
                            params.level = "1";
                            params.resrceCd = selectedRow.level1;
                        }

                    } else if($scope.level === "3") {
                        params.resrceCd = selectedRow.level3;

                        // 값이 있으면 링크 효과
                        if (nvl(selectedRow[("level3")], '') === '') {
                            params.level = "2";
                            params.resrceCd = selectedRow.level2;
                        }
                        if (nvl(selectedRow[("level2")], '') === '') {
                            params.level = "1";
                            params.resrceCd = selectedRow.level1;
                        }
                    }

                    var storeScope = agrid.getScope('menuBaseDetailCtrl');
                    storeScope._broadcast('menuBaseDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("menuBaseCtrl", function(event, data) {
        $scope.searchMenuBase();
        event.preventDefault();
    });

    $scope.searchMenuBase = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.level = $scope.level;

        $scope._inquiryMain("/sys/stats/menuBase/menuBase/getMenuBaseList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('menuBaseDetailCtrl');
                storeScope._gridDataInit();
            });

            // 선택한 분류에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridMenuBaseList");
            var columns = grid.columns;

            if($scope.level === "1") { //대분류
                columns[0].visible = true;
                columns[1].visible = false;
                columns[2].visible = false;
                columns[3].visible = true;
            } else if($scope.level  === "2") { //중분류
                columns[0].visible = true;
                columns[1].visible = true;
                columns[2].visible = false;
                columns[3].visible = true;
            } else if($scope.level  === "3") { //소분류
                columns[0].visible = true;
                columns[1].visible = true;
                columns[2].visible = true;
                columns[3].visible = true;
            }

        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  메뉴기준 사용현황 상세 그리드 생성
 */
app.controller('menuBaseDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuBaseDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("menuBaseDetailCtrl", function(event, data) {
        $scope.searchMenuBaseDetail(data);
        event.preventDefault();
    });

    $scope.searchMenuBaseDetail = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.resrceNm = data.resrceNm;
        params.userId = data.userId;
        params.userNm = data.userNm;
        params.hqOfficeCd = data.hqOfficeCd;
        params.hqOfficeNm = data.hqOfficeNm;
        params.storeCd = data.storeCd;
        params.storeNm = data.storeNm;
        params.level = data.level;
        params.resrceCd = data.resrceCd;

        $scope._inquiryMain("/sys/stats/menuBase/menuBase/getMenuBaseDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);