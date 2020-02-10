/****************************************************************
 *
 * 파일명 : dayPeriodTime.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 시간대별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  시간대별 매출 조회 그리드 생성
 */
app.controller('dayPeriodTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodTimeCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPeriodTime", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPeriodTime", gvEndDate);

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
                    params.saleTime  = selectedRow.saleTime;
                    params.storeCds = $("#dayPeriodTimeStoreCd").val();
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

                    var storeScope = agrid.getScope('dayPeriodTimeDetailCtrl');
                    storeScope._broadcast('dayPeriodTimeDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodTimeCtrl", function(event, data) {
        $scope.searchDayPeriodTime();
        event.preventDefault();
    });

    $scope.searchDayPeriodTime = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayPeriodTimeStoreCd").val();

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodTimeList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('dayPeriodTimeDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayPeriodTimeStoreShow = function () {
        $scope._broadcast('dayPeriodTimeStoreCtrl');
    };

}]);

/**
 *  시간대별 매출 상세조회 그리드 생성
 */
app.controller('dayPeriodTimeDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodTimeDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodTimeDetailCtrl", function(event, data) {
        $scope.searchDayPeriodTimeDetail(data);
        event.preventDefault();
    });

    $scope.searchDayPeriodTimeDetail = function(data) {
        var params = {};
        if (data.saleTime === '심야[00~06]') {
            params.saleTime = '00,01,02,03,04,05,06';
        } else if (data.saleTime === '아침[07~10]') {
            params.saleTime = '07,08,09,10';
        } else if (data.saleTime === '점심[11~15]') {
            params.saleTime = '11,12,13,14,15';
        } else if (data.saleTime === '저녁[16~23]') {
            params.saleTime = '16,17,18,19,20,21,22,23';
        }
        params.storeCds = data.storeCds;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodTimeDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);