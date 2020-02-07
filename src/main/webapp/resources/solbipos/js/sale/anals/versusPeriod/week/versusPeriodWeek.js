/**
 * get application
 */
var app = agrid.getApp();

/** 일자별상품 상세현황 controller */
app.controller('versusPeriodWeekCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('versusPeriodWeekCtrl', $scope, $http, true));

    // 조회일자 세팅
    $scope.srchStartDate = wcombo.genDateVal("#srchWeekStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchWeekEndDate", gvEndDate);

    //1년 전 날짜 세팅
    var date = new Date();
    date.setFullYear(date.getFullYear()-1);
    var result = date.getFullYear() + '' + date.getMonth() + 1 + '' + date.getDate();

    $scope.srchCompStartDate = wcombo.genDateVal("#compWeekStartDate", result);
    $scope.srchCompEndDate   = wcombo.genDateVal("#compWeekEndDate", result);

    // 콤보박스 데이터 Set
    $scope._setComboData('versusPeriodWeeklistScaleBox', gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("versusPeriodWeekCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.saleDateCntA      = messages["versusPeriod.period"];
        dataItem.realSaleAmtA  = messages["versusPeriod.period"];
        dataItem.saleCntA    = messages["versusPeriod.period"];
        dataItem.ratA = messages["versusPeriod.period"];
        dataItem.saleDateCntB       = messages["versusPeriod.comp"];
        dataItem.realSaleAmtB      = messages["versusPeriod.comp"];
        dataItem.saleCntB   = messages["versusPeriod.comp"];
        dataItem.ratB   = messages["versusPeriod.comp"];
        dataItem.sinAmt     = messages["versusPeriod.sin"];
        dataItem.sinCnt    = messages["versusPeriod.sin"];

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

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("versusPeriodWeekCtrl", function (event, data) {
        $scope.searchVersusPeriodWeekList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 주간대비 리스트 조회
    $scope.searchVersusPeriodWeekList = function () {

        if ($("#versusPeriodWeekSelectStoreCd").val() === '') {
            $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
            return false;
        }

        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.compStartDate = wijmo.Globalize.format($scope.srchCompStartDate.value, 'yyyyMMdd');
        params.compEndDate = wijmo.Globalize.format($scope.srchCompEndDate.value, 'yyyyMMdd');
        params.storeCd   = $("#versusPeriodWeekSelectStoreCd").val();

        /*// 조회일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
          params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
          params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        // 대비일자 '전체기간' 선택에 따른 params
        if(!$scope.isCheckedComp){
          params.compStartDate = wijmo.Globalize.format($scope.compStartDate.value, 'yyyyMMdd');
          params.compEndDate = wijmo.Globalize.format($scope.compEndDate.value, 'yyyyMMdd');
        }*/

        if(params.startDate > params.endDate || params.compStartDate > params.compEndDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }

        // 조회일자와 대비일자 설정기간이 동일한지 유효성 체크
        if($scope.dateDiff(params.startDate, params.endDate) !== $scope.dateDiff(params.compStartDate, params.compEndDate)){
            $scope._popMsg(messages["versusPeriod.dateDiff"]);
            return false;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sale/anals/versusPeriod/week/list.sb", params, function() {});

        var days = "(" + $scope.dateDiff(params.startDate, params.endDate) + "일)\n";
        var srchStartToEnd = "(" + $scope.srchStartDate.text + " ~ " + $scope.srchEndDate.text + ")";
        var compStartToEnd = "(" + $scope.srchCompStartDate.text + " ~ " + $scope.srchCompEndDate.text + ")";

        var grid = wijmo.Control.getControl("#versusPeriodWeekGrid").columnHeaders.rows[0].dataItem;
        grid.saleDateCntA      = messages["versusPeriod.period"]+ (days + srchStartToEnd);
        grid.realSaleAmtA  = messages["versusPeriod.period"]+ (days + srchStartToEnd);
        grid.saleCntA    = messages["versusPeriod.period"]+ (days + srchStartToEnd);
        grid.ratA = messages["versusPeriod.period"]+ (days + srchStartToEnd);
        grid.saleDateCntB       = messages["versusPeriod.comp"] + (days + compStartToEnd);
        grid.realSaleAmtB      = messages["versusPeriod.comp"] + (days + compStartToEnd);
        grid.saleCntB   = messages["versusPeriod.comp"] + (days + compStartToEnd);
        grid.ratB   = messages["versusPeriod.comp"] + (days + compStartToEnd);

    };

    /*  // 조회일자 전체기간 체크박스 클릭이벤트
      $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
      };

      // 대비기간 전체기간 체크박스 클릭이벤트
      $scope.isChkDtComp = function() {
        $scope.compStartDate.isReadOnly = $scope.isCheckedComp;
        $scope.compEndDate.isReadOnly = $scope.isCheckedComp;
      };*/

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.versusPeriodWeekSelectStoreShow = function () {
        $scope._broadcast('versusPeriodWeekSelectStoreCtrl');
    };

    // 엑셀 다운로드
    $scope.excelDownloadDay = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, 'versusPeriodWeek.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    //두개의 날짜를 비교하여 차이를 알려준다.
    $scope.dateDiff = function(_date1, _date2) {

        var date1 = [_date1.slice(0, 4), "-", _date1.slice(4, 6), "-", _date1.slice(6, 8)].join('');
        var date2 = [_date2.slice(0, 4), "-", _date2.slice(4, 6), "-", _date2.slice(6, 8)].join('');

        var diffDate_1 = date1 instanceof Date ? date1 : new Date(date1);
        var diffDate_2 = date2 instanceof Date ? date2 : new Date(date2);

        diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
        diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

        var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
        diff = Math.ceil(diff / (1000 * 3600 * 24));

        return diff;
    }

}]);