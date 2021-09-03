/****************************************************************
 *
 * 파일명 : orderChannelPeriod.js
 * 설  명 : 매출관리 > 배달현황 > 주문채널별현황 - 기간별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 주문채널별현황 - 기간별 controller */
app.controller('orderChannelPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderChannelPeriodCtrl', $scope, $http, $timeout, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#periodStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#periodEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("orderChannelPeriodCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.dlvrInFgNm = messages["orderChannel.dlvrInFg"];
        dataItem.totSaleAmt = messages["orderChannel.totSaleAmt"];
        dataItem.totDcAmt = messages["orderChannel.totDcAmt"];
        dataItem.realSaleAmt = messages["orderChannel.realSaleAmt"];
        dataItem.gaAmt = messages["orderChannel.gaAmt"];
        dataItem.vatAmt = messages["orderChannel.vatAmt"];
        dataItem.totTipAmt = messages["orderChannel.totTipAmt"];
        dataItem.totEtcAmt = messages["orderChannel.totEtcAmt"];
        dataItem.billCnt = messages["orderChannel.billCnt"];
        dataItem.billUprc = messages["orderChannel.billUprc"];
        dataItem.totPayAmt = messages["orderChannel.payMethod"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["orderChannel.payMethod"];
        }
        // 할인구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrDcCol.length; i++) {
            dataItem['dc' + arrDcCol[i]] = messages["orderChannel.dcInfo"];
        }

        dataItem.totGuestCnt = messages["orderChannel.totGuestCnt"];

        // 객수 헤더머지 컬럼 생성
        for (var i = 0; i < arrGuestCol.length; i++) {
            dataItem['guest' + arrGuestCol[i]] = messages["orderChannel.guestCnt"];
        }

        dataItem.guestUprc = messages["orderChannel.guestUprc"];
        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
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

    $scope.$on("orderChannelPeriodCtrl", function (event, data) {
        
        // 기간별 조회
        $scope.searchOrderChannelPeriod();
        event.preventDefault();
    });

    // 기간별 조회
    $scope.searchOrderChannelPeriod = function(){

        if ($("#orderChannelPeriodStoreCd").val() === '') {
            $scope._popMsg(messages["orderChannel.require.selectStore"]); // 매장을 선택해주세요.
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#orderChannelPeriodStoreCd").val();
        params.payCol = payCol;
        params.dcCol = dcCol;
        params.guestCol = guestCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/dlvr/orderChannel/getOrderChannelPeriodList.sb", params, function () {

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridOrderChannelPeriodList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = 11 + 17 + 11 + 7;

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                if(columns[j].binding == "guest01" || columns[j].binding == "guest02" || columns[j].binding == "guest03" || columns[j].binding == "guest04" || columns[j].binding == "guest05" || columns[j].binding == "guest06") {
                    // 합계행 값 가져오기
                    if($scope.flex.columnFooters.getCellData(0, j, true) == 0) {
                        columns[j].visible = false;
                    } else {
                        columns[j].visible = true;
                    }
                }
            }
            // <-- //그리드 visible -->

        });
    };

    // 엑셀 다운로드
    $scope.excelDownloadPeriod = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, '주문채널별현황_기간별_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.orderChannelPeriodStoreShow = function () {
        $scope._broadcast('orderChannelPeriodStoreCtrl');
    };

}]);