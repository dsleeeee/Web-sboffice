/****************************************************************
 *
 * 파일명 : monthCorner.js
 * 설  명 : 기간별매출 > 월별탭 > 코너별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  코너별 매출 조회 그리드 생성
 */
app.controller('monthCornerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthCornerCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonthMonthCorner', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonthMonthCorner', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("monthCornerCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.yearMonth    = messages["month.yearMonth"];
        dataItem.storeCnt    = messages["month.storeCnt"];
        dataItem.totRealSaleAmt    = messages["month.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["month.totSaleQty"];

        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            dataItem['cornr' + arrCornerCol[i] + 'RealSaleAmt'] = cornerColList[i].storeNm;
            dataItem['cornr' + arrCornerCol[i] + 'SaleQty'] = cornerColList[i].storeNm;
        }

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1         = {};
        dataItem1.yearMonth    = messages["month.yearMonth"];
        dataItem1.storeCnt    = messages["month.storeCnt"];
        dataItem1.totRealSaleAmt    = messages["month.totRealSaleAmt"];
        dataItem1.totSaleQty    = messages["month.totSaleQty"];

        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            dataItem1['cornr' + arrCornerCol[i] + 'RealSaleAmt'] = cornerColList[i].cornrNm;
            dataItem1['cornr' + arrCornerCol[i] + 'SaleQty'] = cornerColList[i].cornrNm;
        }

        s.columnHeaders.rows[1].dataItem = dataItem1;

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
        // <-- //그리드 헤더3줄 -->

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 수량합계
                if (col.binding === "totSaleQty") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 수량
                for (var i = 0; i < cornerColList.length; i++) {
                    if (col.binding === ("cornr" + cornerColList[i].storeCornrCd + "SaleQty")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        // if (nvl(item[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') !== '' && nvl(item[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') != "0") {
                        if (nvl(item[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') !== '') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  수량합계 클릭시 상세정보 조회
                if ( col.binding === "totSaleQty") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.yearMonth = selectedRow.yearMonth.replace("-", "");
                    params.storeCd = $("#monthCornerStoreCd").val();
                    params.gubun = "monthCorner";

                    $scope._broadcast('prodSaleDtlCtrl', params);
                }

                // 수량 클릭시 상세정보 조회
                for (var i = 0; i < cornerColList.length; i++) {
                    if (col.binding === ("cornr" + cornerColList[i].storeCornrCd + "SaleQty")) {

                        var selectedRow = s.rows[ht.row].dataItem;
                        var params      = {};
                        params.yearMonth = selectedRow.yearMonth.replace("-", "");
                        params.storeCd = cornerColList[i].storeCd;
                        params.gubun = "monthCorner";

                        // 값이 있으면 링크
                        // if (nvl(selectedRow[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') !== '' && nvl(selectedRow[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') != "0") {
                        if (nvl(selectedRow[("cornr" + cornerColList[i].storeCornrCd + "SaleQty")], '') !== '') {
                            $scope._broadcast('prodSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("monthCornerCtrl", function(event, data) {
        $scope.searchMonthCorner();
        event.preventDefault();
    });

    $scope.searchMonthCorner = function() {

        //조회할 코너 Key값 셋팅을 위해
        var storeCornerCd = "";

        // 매장권한 로그인 시
        if(orgnFg != null && orgnFg == 'STORE') {

            // 본인 매장것만 조회
            $("#monthCornerStoreCd").val(storeCd);

            // 매장권한의 경우, 이미 자기의 코너 값을 가져왔음.
            storeCornerCd = cornerCol;
        }

        // 본사권한 로그인 시
        if(orgnFg != null && orgnFg == 'HQ') {

            // 매장코드 값 필수
            if ($("#monthCornerStoreCd").val() == "") {
                s_alert.pop("매장을 선택해주세요.");
                return;
            }

            // 해당 본사의 전체 코너에서 조회할 매장의 코너만 추려내기
            for (var i = 0; i < cornerColList.length; i++) {
                if (cornerColList[i].storeCd === $("#monthCornerStoreCd").val()) {
                    storeCornerCd += "," + arrCornerCol[i];
                }
            }

            storeCornerCd = storeCornerCd.substring(1, storeCornerCd.length)
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCd = $("#monthCornerStoreCd").val();
        params.storeCornerCd = storeCornerCd;

        $scope._inquiryMain("/sale/day/month/month/getMonthCornerList.sb", params, function() {}, false);

        // <-- 그리드 visible -->
        // 선택한 테이블에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridMonthCornerList");
        var columns = grid.columns;
        var start = 0;
        var end = 3;

        // cornerColList 에 storeCd 배열로 담기
        var cornerColArray = [];
        for (var i = 0; i < cornerColList.length; i++) {
            comboData = {};
            comboData.value = cornerColList[i].storeCd;
            cornerColArray.push(comboData);
        }

        // 컬럼 총갯수
        var columnsCnt = 4 + (cornerColArray.length * 2);

        // 전체선택시 전부 visible
        if($("#monthCornerStoreCd").val() === "")
        {
            for (var i = 4; i <= columnsCnt; i++) {
                columns[i].visible = true;
            }
        }
        // 선택한 테이블만 visible
        else
        {
            var totCornerIndex = "";
            for (var i = 0; i < cornerColArray.length; i++) {
                if (cornerColArray[i].value === $("#monthCornerStoreCd").val()) {
                    totCornerIndex = totCornerIndex + i + ','; // 해당 본사에 모든 매장별코너 리스트에서 선택한 매장의 코너들이 몇번째가 인덱스인지 체크
                }
            }

            totCornerIndex = totCornerIndex.substring(0, totCornerIndex.length-1);
            var arrCornerIndex = totCornerIndex.split(",");

            for (var i = 0; i < cornerColArray.length; i++) {
                start = 4 + (parseInt(arrCornerIndex[0]) * 2);
                end = 5 + (parseInt(arrCornerIndex[arrCornerIndex.length-1]) * 2);
            }

            for (var i = 4; i < columnsCnt; i++) {
                if (i >= start && i <= end) {
                    columns[i].visible = true;
                } else {
                    columns[i].visible = false;
                }
            }
        }
        // <-- //그리드 visible -->
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.monthCornerStoreShow = function () {
        $scope._broadcast('monthCornerStoreCtrl');
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["day.month"] + '(' + messages["month.corner"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);