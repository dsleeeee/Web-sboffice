/****************************************************************
 *
 * 파일명 : dayCorner.js
 * 설  명 : 기간별매출 > 일자별탭 > 코너별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  코너별 매출 조회 그리드 생성
 */
app.controller('dayCornerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayCornerCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayCorner", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayCorner", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayCornerCtrl");

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
        dataItem.saleDate    = messages["day.corner.saleDate"];
        dataItem.storeCnt    = messages["day.corner.storeCnt"];
        dataItem.totRealSaleAmt    = messages["day.corner.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["day.corner.totSaleQty"];


        var larr = {};
        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            larr[i] = arrCornerCol[i].substring(0,1) + arrCornerCol[i].substring(1, arrCornerCol[i].length).toLowerCase();
            dataItem['cornr' + larr[i] + 'RealSaleAmt'] = "[" + cornerColList[i].storeCd + "]" + cornerColList[i].storeNm;
            dataItem['cornr' + larr[i] + 'SaleQty'] = "[" + cornerColList[i].storeCd + "]" + cornerColList[i].storeNm;
        }

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1         = {};
        dataItem1.saleDate    = messages["day.corner.saleDate"];
        dataItem1.storeCnt    = messages["day.corner.storeCnt"];
        dataItem1.totRealSaleAmt    = messages["day.corner.totRealSaleAmt"];
        dataItem1.totSaleQty    = messages["day.corner.totSaleQty"];

        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            larr[i] = arrCornerCol[i].substring(0,1) + arrCornerCol[i].substring(1, arrCornerCol[i].length).toLowerCase();
            dataItem1['cornr' + larr[i] + 'RealSaleAmt'] = "[" + cornerColList[i].cornrCd + "]" + cornerColList[i].cornrNm;
            dataItem1['cornr' + larr[i] + 'SaleQty'] = "[" + cornerColList[i].cornrCd + "]" + cornerColList[i].cornrNm;
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
                    larr[i] = cornerColList[i].storeCornrCd.substring(0,1) + cornerColList[i].storeCornrCd.substring(1, cornerColList[i].storeCornrCd.length).toLowerCase();
                    if (col.binding === ("cornr" + larr[i] + "SaleQty")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("cornr" + larr[i] + "SaleQty")], '') !== '') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                }

                // 날짜 형식
                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
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
                    params.saleDate  = selectedRow.saleDate.replaceAll("-","");
                    params.storeCd = $scope.searchStoreCd;
                    params.gubun = "dayCorner";

                    $scope._broadcast('prodSaleDtlCtrl', params);
                }

                // 수량 클릭시 상세정보 조회
                for (var i = 0; i < cornerColList.length; i++) {
                    larr[i] = cornerColList[i].storeCornrCd.substring(0,1) + cornerColList[i].storeCornrCd.substring(1, cornerColList[i].storeCornrCd.length).toLowerCase();
                    if (col.binding === ("cornr" + larr[i] + "SaleQty")) {

                        var selectedRow = s.rows[ht.row].dataItem;
                        var params      = {};
                        params.saleDate  = selectedRow.saleDate.replaceAll("-","");
                        params.storeCd = cornerColList[i].storeCd;
                        params.cornrCd = cornerColList[i].cornrCd;
                        params.gubun = "dayCorner";

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("cornr" + larr[i] + "SaleQty")], '') !== '') {
                            $scope._broadcast('prodSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayCornerCtrl", function(event, data) {
        $scope.searchDayCorner();
        event.preventDefault();
    });

    $scope.searchDayCorner = function() {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        if($("#dayCornerStoreCd").val().split(",").length > 10) {
            $scope._popMsg(messages["day.corner.storeCntAlert"]); // 매장은 최대 10개 선택 가능합니다.
            return false;
        }

        //조회할 코너 Key값 셋팅을 위해
        var storeCornerCd = "";

        // 매장권한 로그인 시
        if(orgnFg !== null && orgnFg === 'STORE') {

            // 본인 매장것만 조회
            $("#dayCornerStoreCd").val(storeCd);

            // 매장권한의 경우, 이미 자기의 코너 값을 가져왔음.
            storeCornerCd = cornerCol;
        }

        // 본사권한 로그인 시
        if(orgnFg !== null && orgnFg === 'HQ') {

            // 매장코드 값 필수
            if ($("#dayCornerStoreCd").val() === "") {
                s_alert.pop("매장을 선택해주세요.");
                return;
            }
            var arr = $("#dayCornerStoreCd").val().split(",");

            // 해당 본사의 전체 코너에서 조회할 매장의 코너만 추려내기
            for (var i = 0; i < cornerColList.length; i++) {
                for(var j = 0; j < arr.length; j++) {
                    if (cornerColList[i].storeCd === arr[j]) {
                        storeCornerCd += "," + arrCornerCol[i];
                    }
                }
            }

            storeCornerCd = (storeCornerCd !== "" ? storeCornerCd.substring(1, storeCornerCd.length) : "");
        }

        var grid = wijmo.Control.getControl("#wjGridDayCornerList");
        var columns = grid.columns;
        var arr = storeCornerCd.split(",");

        // 기존에 조회된 컬럼 제거
        if(columns.length > 4) {
            var removeItem = [];
            for (var j = 4; j < columns.length; j++) {
                removeItem[j-4] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 코너별 실매출, 수량 컬럼 생성
        if(arr.length > 0 && storeCornerCd !== "") {
            for (var i = 0; i < arr.length; i++) {
                arr[i] = arr[i].substring(0,1) + arr[i].substring(1, arr[i].length).toLowerCase();
                columns.push(new wijmo.grid.Column({ header: messages["day.corner.realSaleAmt"], binding : 'cornr' + arr[i] + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["day.corner.saleQty"], binding : 'cornr' + arr[i] + 'SaleQty', align: "right", isReadOnly: "true", aggregate: "Sum"}));
            }
        }

        // 코너별 매출 조회
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd = $("#dayCornerStoreCd").val();
        params.storeCornerCd = storeCornerCd;

        $scope.searchStoreCd = params.storeCd;

        $scope._inquiryMain("/sale/day/day/day/getDayCornerList.sb", params, function() {}, false);

    };
    // <-- //검색 호출 -->

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
                messages["day.day"] + '(' + messages["day.corner"] + ')_'  + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);