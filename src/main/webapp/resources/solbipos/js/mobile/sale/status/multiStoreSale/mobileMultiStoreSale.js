/****************************************************************
 *
 * 파일명 : mobileMultiStoreSale.js
 * 설  명 : (모바일) 매출현황 > 다중매장매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
/**
 *  다중매장매출현황 그리드 생성
 */
app.controller('mobileMultiStoreSaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMultiStoreSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

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
        var dataItem = {};
        dataItem.storeNm = messages["mobile.multiStoreSale.storeNm"];
        dataItem.saleCnt = messages["mobile.multiStoreSale.saleCnt"];
        dataItem.realSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.amtRate = messages["mobile.multiStoreSale.storeTotal"];
        dataItem.shopRealSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.dlvrRealSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.packRealSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.shopAmtRate = messages["mobile.multiStoreSale.amtRate"];
        dataItem.dlvrAmtRate = messages["mobile.multiStoreSale.amtRate"];
        dataItem.packAmtRate = messages["mobile.multiStoreSale.amtRate"];

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

        // 차트 초기값 셋팅
        $scope.searchMobileMultiStoreSaleChartSet();

        // 조회
        // $scope.searchMobileMultiStoreSale();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileMultiStoreSaleChartSet = function(){
        var params = {};
        params.startDate = getToday(); // 조회기간
        params.endDate = getToday(); // 조회기간
        params.srchStoreCd = $("#mobileMultiStoreSaleStoreCd").val();

        // 바 차트
        $scope._broadcast("mobileMultiStoreSaleChartCtrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMultiStoreSaleCtrl", function(event, data) {
        gridOpen("mobileMultiStoreSale");
        gridOpen("mobileMultiStoreSaleDayStore");

        $scope.searchMobileMultiStoreSale();
        event.preventDefault();
    });

    $scope.searchMobileMultiStoreSale = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileMultiStoreSaleStoreCd").val();
        params.gubun = "1";

        $scope._inquirySub("/mobile/sale/status/multiStoreSale/multiStoreSale/getMobileMultiStoreSaleList.sb", params, function() {
            // 다중매장매출현황
            $scope._broadcast("mobileMultiStoreSale2Ctrl", params);
            // 바 차트
            $scope._broadcast("mobileMultiStoreSaleChartCtrl", params);
            // 일자-매장별 매출현황
            $scope._broadcast("mobileMultiStoreSaleDayStoreCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileMultiStoreSaleStoreShow = function () {
        $scope._broadcast('mobileMultiStoreSaleStoreCtrl');
    };
}]);
app.controller('mobileMultiStoreSale2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMultiStoreSale2Ctrl', $scope, $http, false));

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
        var dataItem = {};
        dataItem.storeNm = messages["mobile.multiStoreSale.storeNm"];
        dataItem.totSaleAmt = messages["mobile.multiStoreSale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.multiStoreSale.totDcAmt"];
        dataItem.realSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.saleCnt = messages["mobile.multiStoreSale.saleCnt"];
        dataItem.dayAvrSale = messages["mobile.multiStoreSale.dayAvrSale"];
        dataItem.billCnt = messages["mobile.multiStoreSale.billCnt"];
        dataItem.billUprc = messages["mobile.multiStoreSale.billUprc"];
        dataItem.shopRealSaleAmt = messages["mobile.multiStoreSale.shop"];
        dataItem.shopAvrSale = messages["mobile.multiStoreSale.shop"];
        dataItem.shopBillCnt = messages["mobile.multiStoreSale.shop"];
        dataItem.shopBillUprc = messages["mobile.multiStoreSale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.multiStoreSale.dlvr"];
        dataItem.dlvrAvrSale = messages["mobile.multiStoreSale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.multiStoreSale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.multiStoreSale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.multiStoreSale.pack"];
        dataItem.packAvrSale = messages["mobile.multiStoreSale.pack"];
        dataItem.packBillCnt = messages["mobile.multiStoreSale.pack"];
        dataItem.packBillUprc = messages["mobile.multiStoreSale.pack"];
        dataItem.totGuestCnt = messages["mobile.multiStoreSale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.multiStoreSale.guestUprc"];
        dataItem.cardAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.cashAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.etcAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.dcAmt = messages["mobile.multiStoreSale.dc"];
        dataItem.coupnDcAmt = messages["mobile.multiStoreSale.dc"];
        dataItem.etcDcAmt = messages["mobile.multiStoreSale.dc"];

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
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMultiStoreSale2Ctrl", function(event, data) {
        $scope.searchMobileMultiStoreSale2(data);
        event.preventDefault();
    });

    $scope.searchMobileMultiStoreSale2 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "2";

        $scope._inquirySub("/mobile/sale/status/multiStoreSale/multiStoreSale/getMobileMultiStoreSaleList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);
/**
 *  다중매장매출현황 차트 생성
 */
app.controller('mobileMultiStoreSaleChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMultiStoreSaleChartCtrl', $scope, $http, false));

    // 차트
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        // s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "다중매장";
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMultiStoreSaleChartCtrl", function(event, data) {
        $scope.searchMobileMultiStoreSaleChart(data);
    });

    $scope.searchMobileMultiStoreSaleChart = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/multiStoreSale/multiStoreSale/getMobileMultiStoreSaleChartList.sb", params);
    };
    // <-- //검색 호출 -->

    $scope.rendered = function(s, e) {

        var pArea = s.hostElement.querySelector('.wj-plot-area > rect');
        var pAreaWidth = pArea.width.baseVal.value;
        var groupWidth = pAreaWidth / (s.collectionView.items.length || 1);

        var labels = document.querySelectorAll('.wj-axis-x .wj-label');
        var widthMax = new Array();

        labels.forEach(function(value, key, parent) {

            var x = +value.getAttribute('x');
            var y = +value.getAttribute('y');
            var text = value.innerHTML.split(' - ');
            value.innerHTML = '';

            widthMax[key] = new Array();

            text.forEach(function(item, index) {

                var e = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
                //e.setAttribute("x", (x + 0).toString());
                e.setAttribute('y', (y + (15 * index)).toString());
                e.innerHTML = item;
                value.appendChild(e);

                var bbox = e.getBoundingClientRect();
                var extent = e.getExtentOfChar(0);
                var boxWidth = e.getComputedTextLength();
                var gap = 0;

                gap = (groupWidth - boxWidth) / 2;
                widthMax[key][index] = gap;

                e.setAttribute('x', (x + gap).toString());
            });
        });

        labels.forEach(function(value, key, parent) {
            var children = value.childNodes;
            for (var i = 0; i < children.length; i++) {
                var e = value.childNodes[i];
                var extent = e.getExtentOfChar(0);

                e.setAttribute('x', extent.x - widthMax[key][0] + 30);
            }
        });

        s.tooltip.content = function (ht) {
            var title = ht.name;
            var nameArr = ht._xfmt.split(" - ");
            var value = ht.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            return "<b>" + title + "</b><br><br>" + nameArr[0] + "<br><br>" + value;
        }
    }
}]);


/**
 *  일자-매장별 매출현황 그리드 생성
 */
app.controller('mobileMultiStoreSaleDayStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMultiStoreSaleDayStoreCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        var dataItem = {};
        dataItem.saleDate = messages["mobile.multiStoreSale.saleDate"];
        dataItem.storeNm = messages["mobile.multiStoreSale.storeNm"];
        dataItem.totSaleAmt = messages["mobile.multiStoreSale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.multiStoreSale.totDcAmt"];
        dataItem.realSaleAmt = messages["mobile.multiStoreSale.realSaleAmt"];
        dataItem.billCnt = messages["mobile.multiStoreSale.billCnt"];
        dataItem.billUprc = messages["mobile.multiStoreSale.billUprc"];
        dataItem.shopRealSaleAmt = messages["mobile.multiStoreSale.shop"];
        dataItem.shopBillCnt = messages["mobile.multiStoreSale.shop"];
        dataItem.shopBillUprc = messages["mobile.multiStoreSale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.multiStoreSale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.multiStoreSale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.multiStoreSale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.multiStoreSale.pack"];
        dataItem.packBillCnt = messages["mobile.multiStoreSale.pack"];
        dataItem.packBillUprc = messages["mobile.multiStoreSale.pack"];
        dataItem.totGuestCnt = messages["mobile.multiStoreSale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.multiStoreSale.guestUprc"];
        dataItem.cardAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.cashAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.etcAmt = messages["mobile.multiStoreSale.pay"];
        dataItem.dcAmt = messages["mobile.multiStoreSale.dc"];
        dataItem.coupnDcAmt = messages["mobile.multiStoreSale.dc"];
        dataItem.etcDcAmt = messages["mobile.multiStoreSale.dc"];

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
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMultiStoreSaleDayStoreCtrl", function(event, data) {
        $scope.searchMobileMultiStoreSaleDayStore(data);
        event.preventDefault();
    });

    $scope.searchMobileMultiStoreSaleDayStore = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/multiStoreSale/multiStoreSale/getMobileMultiStoreSaleDayStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);