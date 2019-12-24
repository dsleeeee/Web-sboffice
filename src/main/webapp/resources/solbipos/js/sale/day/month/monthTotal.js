/****************************************************************
 *
 * 파일명 : monthTotal.js
 * 설  명 : 기간별매출 > 월별탭 > 월별종합 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.09     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  월별종합 조회 그리드 생성
 */
app.controller('monthTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthTotalCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonthMonthTotal', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonthMonthTotal', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        dataItem.yearMonth    = messages["month.yearMonth"];
        dataItem.storeCnt    = messages["month.storeCnt"];
        dataItem.saleCnt    = messages["month.saleCnt"];
        dataItem.totSaleAmt    = messages["month.sale"];
        dataItem.totDcAmt    = messages["month.sale"];
        dataItem.realSaleAmt    = messages["month.sale"];
        dataItem.billCnt   = messages["month.sale"];
        dataItem.billUprc   = messages["month.sale"];
        dataItem.gaAmt   = messages["month.sale"];
        dataItem.vatAmt   = messages["month.sale"];
        dataItem.totTipAmt   = messages["month.totTipAmt"];
        dataItem.totEtcAmt   = messages["month.totEtcAmt"];
        dataItem.totPayAmt   = messages["month.payMethod"];
        dataItem.genRealSaleAmt   = messages["month.dlvrPack"];
        dataItem.genRealSaleAmtPer   = messages["month.dlvrPack"];
        dataItem.dlvrRealSaleAmt   = messages["month.dlvrPack"];
        dataItem.dlvrRealSaleAmtPer   = messages["month.dlvrPack"];
        dataItem.packRealSaleAmt   = messages["month.dlvrPack"];
        dataItem.packRealSaleAmtPer   = messages["month.dlvrPack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["month.payMethod"];
        }

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
                if (col.binding === "yearMonth" || col.binding === "totDcAmt" || col.binding === "billCnt") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 결제수단
                for (var i = 0; i < payColList.length; i++) {
                    if (col.binding === ("pay" + payColList[i].payCd)) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("pay" + payColList[i].payCd)], '') !== '' && nvl(item[("pay" + payColList[i].payCd)], '') != "0") {
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

                var selectedRow = s.rows[ht.row].dataItem;
                var params      = {};
                params.yearMonth = selectedRow.yearMonth.replace("-", "");
                params.storeCd = $("#monthTotalStoreCd").val();
                params.gubun = "month";

                // 년월 클릭시 상세정보 조회
                if ( col.binding === "yearMonth") {
                    $scope._broadcast('dayStoreDtlCtrl', params);
                }

                // 총할인 클릭시 상세정보 조회
                if ( col.binding === "totDcAmt") {
                    $scope._broadcast('dayStoreDcCtrl', params);
                }

                // 영수건수 클릭시 상세정보 조회
                if ( col.binding === "billCnt") {
                    $scope._broadcast('dayStoreBillCtrl', params);
                }

                // 결제수단
                for (var i = 0; i < payColList.length; i++) {
                    if (col.binding === ("pay" + payColList[i].payCd)) {
                        var callCtrl = '';

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '' && nvl(selectedRow[("pay" + payColList[i].payCd)], '') != "0") {
                            callCtrl = 'day'+payColList[i].payMethod.substr(0,1).toUpperCase() + payColList[i].payMethod.substr(1).toLowerCase() + 'Ctrl';
                            // 포인트 이름이 안맞음(dayMembr->dayPoint)
                            if(callCtrl == 'dayMembrCtrl') {
                                callCtrl = 'dayPointCtrl';
                            }
                            // 사원카드 이름이 안맞음(dayEmp_Card->dayEmpCard)
                            if(callCtrl == 'dayEmp_cardCtrl') {
                                callCtrl = 'dayEmpCardCtrl';
                            }
                            $scope._broadcast(callCtrl, params);
                            // alert(callCtrl);
                            // sale/cmmSalePopup/dayPayInfo/
                            // dayCard.jsp
                            // dayCash.jsp
                            // dayPayco.jsp
                            // dayVpoint.jsp
                            // dayVcharge.jsp
                            // dayMpay.jsp
                            // dayMcoupn.jsp
                            // dayPoint.jsp
                            // dayPrepaid.jsp
                            // dayPostpaid.jsp
                            // dayGift.jsp
                            // dayFstmp.jsp
                            // dayPartner.jsp
                            // OK캐쉬백 -> 안만들어짐(dayOkcsb)
                            // dayEmpCard.jsp
                            // dayTemporary.jsp
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("monthTotalCtrl", function(event, data) {
        $scope.searchMonthTotal();
        event.preventDefault();
    });

    $scope.searchMonthTotal = function() {
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCds = $("#monthTotalStoreCd").val();
        params.payCol    = payCol;

        $scope._inquiryMain("/sale/day/month/month/getMonthTotalList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.monthTotalStoreShow = function () {
        $scope._broadcast('monthTotalStoreCtrl');
    };

}]);