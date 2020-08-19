/****************************************************************
 *
 * 파일명 : dayPeriodGift.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 상품권별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품권별 매출 조회 그리드 생성
 */
app.controller('dayPeriodGiftCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodGiftCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPeriodGift", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPeriodGift", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPeriodGiftCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.payClassNm    = messages["dayPeriod.payClassNm"];
        dataItem.giftQtyUse    = messages["dayPeriod.use"];
        dataItem.giftUprcUse    = messages["dayPeriod.use"];
        dataItem.giftQtyRef    = messages["dayPeriod.ref"];
        dataItem.giftUprcRef    = messages["dayPeriod.ref"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "payClassNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 권종 클릭시 상세정보 조회
                if ( col.binding === "payClassNm") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.storeCd = selectedRow.storeCd;
                    params.payClassCd = selectedRow.payClassCd;
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

                    var storeScope = agrid.getScope('dayPeriodGiftDetailCtrl');
                    storeScope._broadcast('dayPeriodGiftDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodGiftCtrl", function(event, data) {
        $scope.searchDayPeriodGift();
        event.preventDefault();
    });

    $scope.searchDayPeriodGift = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayPeriodGiftStoreCd").val();

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodGiftList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('dayPeriodGiftDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayPeriodGiftStoreShow = function () {
        $scope._broadcast('dayPeriodGiftStoreCtrl');
    };

}]);

/**
 *  상품권별 매출 상세조회 그리드 생성
 */
app.controller('dayPeriodGiftDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodGiftDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPeriodGiftDetailCtrl");

        // 그리드 DataMap 설정
        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name'); //판매구분
        $scope.giftProcFgDataMap = new wijmo.grid.DataMap(giftProcFgData, 'value', 'name'); //상품권처리구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodGiftDetailCtrl", function(event, data) {
        $scope.searchDayPeriodGiftDetail(data);
        event.preventDefault();
    });

    $scope.searchDayPeriodGiftDetail = function(data) {
        var params = {};
        params.storeCd = data.storeCd;
        params.payClassCd = data.payClassCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodGiftDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);