/****************************************************************
 *
 * 파일명 : dayPeriodCorner.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 코너별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  코너별 매출 조회 그리드 생성
 */
app.controller('dayPeriodCornerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodCornerCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPeriodCorner", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPeriodCorner", gvEndDate);

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
                if (col.binding === "realSaleAmt") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 실매출 클릭시 상세정보 조회
                if ( col.binding === "realSaleAmt") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.storeCd  = selectedRow.storeCd;
                    params.cornrCd  = selectedRow.cornrCd;
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

                    var storeScope = agrid.getScope('dayPeriodCornerDetailCtrl');
                    storeScope._broadcast('dayPeriodCornerDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodCornerCtrl", function(event, data) {
        $scope.searchDayPeriodCorner();
        event.preventDefault();
    });

    $scope.searchDayPeriodCorner = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayPeriodCornerStoreCd").val();

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodCornerList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('dayPeriodCornerDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayPeriodCornerStoreShow = function () {
        $scope._broadcast('dayPeriodCornerStoreCtrl');
    };

}]);

/**
 *  코너별 매출 상세조회 그리드 생성
 */
app.controller('dayPeriodCornerDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodCornerDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodCornerDetailCtrl", function(event, data) {
        $scope.searchDayPeriodCornerDetail(data);
        event.preventDefault();
    });

    $scope.searchDayPeriodCornerDetail = function(data) {
        var params = {};
        params.storeCd = data.storeCd;
        params.cornrCd = data.cornrCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodCornerDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);