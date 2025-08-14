/****************************************************************
 *
 * 파일명 : dcDetailMrpizzaAllStore.js
 * 설  명 : 미스터피자 > 마케팅조회 > 할인세부내역 > 선택점포 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.30    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선택점포 탭 조회 그리드 생성
 */
app.controller('dcDetailMrpizzaSelectStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dcDetailMrpizzaSelectStoreCtrl', $scope, $http, true));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#startDateDcDetailMrpizzaSelectStore", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#endDateDcDetailMrpizzaSelectStore", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 첫째줄 헤더에서 사용할 날짜
        //var salePeriod =  wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') + "~" + wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;

        // 첫째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem = {};
        dataItem.coupnCd        = "";
        dataItem.coupnNm        = "";
        dataItem.totDCnt        = "";
        dataItem.totSaleAmt     = "";
        dataItem.dcAmt          = "";
        dataItem.realSaleAmt    = "";

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.coupnCd           = messages["dcDetailMrpizza.fg"];
        dataItem1.coupnNm           = messages["dcDetailMrpizza.fg"];
        dataItem1.totDCnt           = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
        dataItem1.totSaleAmt        = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
        dataItem1.dcAmt             = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
        dataItem1.realSaleAmt       = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();

        s.columnHeaders.rows[1].dataItem = dataItem1;

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

                if ((panel.grid.columnHeaders.rows.length - 1) === r) {
                    // 헤더의 전체선택 클릭 로직
                    var flex = panel.grid;
                    var column = flex.columns[c];
                    // check that this is a boolean column
                    if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                        // prevent sorting on click
                        column.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, c) === true) {
                                cnt++;
                            }
                        }
                        // create and initialize checkbox
                        if (column.format === 'checkBoxText') {
                            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                                + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                        } else {
                            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                        }
                        var cb = cell.firstChild;
                        cb.checked = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                if (!flex.rows[i].isReadOnly) {
                                    flex.setCellData(i, c, cb.checked);
                                }
                            }
                            flex.endUpdate();
                        });
                    }
                }
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
        // <-- //그리드 헤더3줄 -->

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dcDetailMrpizzaSelectStoreCtrl", function (event, data) {

        // 선택점포 탭 리스트 조회
        $scope.searchDcDetailMrpizzaSelectStoreList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 선택점포 탭 리스트 조회
    $scope.searchDcDetailMrpizzaSelectStoreList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 6달(186일) 제한
        if (diffDay > 186) {
            $scope._popMsg(messages['cmm.dateOver.6month.error']);
            return false;
        }

        // 매장(을)를 선택해주세요.
        if($("#dcDetailMrpizzaSelStoreCd").val() === "") {
            $scope._popMsg(messages["dcDetailMrpizza.store"] + messages["cmm.require.select"]);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCds = $("#dcDetailMrpizzaSelStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/mrpizza/dcDetailMrpizza/getDcDetailMrpizzaSelectStoreList.sb", params, function () {

            // 그리도 header 조회 날짜 셋팅
            var grid = wijmo.Control.getControl("#wjGridList2");

            // 첫째줄 헤더에서 사용할 날짜
            var salePeriod =  wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') + "~" + wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

            // 첫째줄 헤더 생성
            var dataItem = {};
            dataItem.coupnCd       = salePeriod;
            dataItem.coupnNm       = salePeriod;
            dataItem.totDcCnt      = salePeriod;
            dataItem.totSaleAmt    = salePeriod;
            dataItem.dcAmt         = salePeriod;
            dataItem.realSaleAmt   = salePeriod;

            grid.columnHeaders.rows[0].dataItem = dataItem;
            
            // 둘째줄 헤더 생성
            var dataItem1 = {};
            dataItem1.coupnCd           = messages["dcDetailMrpizza.fg"];
            dataItem1.coupnNm           = messages["dcDetailMrpizza.fg"];
            dataItem1.totDcCnt          = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
            dataItem1.totSaleAmt        = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
            dataItem1.dcAmt             = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
            dataItem1.realSaleAmt       = $("#dcDetailMrpizzaSelStoreNm").val() === "선택" ? "" : $("#dcDetailMrpizzaSelStoreNm").val();
            
            grid.columnHeaders.rows[1].dataItem = dataItem1;

            // 조회날짜 기준 엑셀 다운로드 기간
            $scope.excelStartDate = params.startDate;
            $scope.excelEndDate   = params.endDate;
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 엑셀다운로드 기간
        var startDt = $scope.excelStartDate;
        var endDt = $scope.excelEndDate;

        // 합계 행(GroupRow) 가져오기
        var groupRow = $scope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItem = groupRow.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBinding = $scope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItem = {};
        newDataItem[firstColumnBinding] = '합계';
        groupRow.dataItem = newDataItem;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "할인세부내역_선택점포" + '_' + startDt + '_' + endDt + '_' + getCurDateTime() + '.xlsx', function () {
                    // 원래의 합계 행 데이터로 복원
                    groupRow.dataItem = originalDataItem;
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);