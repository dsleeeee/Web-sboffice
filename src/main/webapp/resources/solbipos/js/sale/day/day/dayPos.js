/****************************************************************
 *
 * 파일명 : dayPos.js
 * 설  명 : 기간별매출 > 일자별탭 > 포스별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.18     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  포스별 매출 조회 그리드 생성
 */
app.controller('dayPosCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPosCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPos", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPos", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPosCtrl");

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
        dataItem.saleDate    = messages["day.dayPos.saleDate"];
        dataItem.yoil    = messages["day.dayPos.yoil"];
        dataItem.totSaleAmt    = messages["day.dayPos.totSaleAmt"];
        dataItem.totDcAmt    = messages["day.dayPos.totDcAmt"];
        dataItem.totRealSaleAmt    = messages["day.dayPos.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["day.dayPos.totSaleQty"];

        var larr = {};
        // 포스구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrPosCol.length; i++) {
            larr[i] = arrPosCol[i].substring(0,1) + arrPosCol[i].substring(1, arrPosCol[i].length).toLowerCase();
            dataItem['pos' + larr[i] + 'SaleAmt'] = posColList[i].storeNm;
            dataItem['pos' + larr[i] + 'DcAmt'] = posColList[i].storeNm;
            dataItem['pos' + larr[i] + 'RealSaleAmt'] = posColList[i].storeNm;
            dataItem['pos' + larr[i] + 'SaleQty'] = posColList[i].storeNm;
        }

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1         = {};
        dataItem1.saleDate    = messages["day.dayPos.saleDate"];
        dataItem1.yoil    = messages["day.dayPos.yoil"];
        dataItem1.totSaleAmt    = messages["day.dayPos.totSaleAmt"];
        dataItem1.totDcAmt    = messages["day.dayPos.totDcAmt"];
        dataItem1.totRealSaleAmt    = messages["day.dayPos.totRealSaleAmt"];
        dataItem1.totSaleQty    = messages["day.dayPos.totSaleQty"];

        // 포스구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrPosCol.length; i++) {
            larr[i] = arrPosCol[i].substring(0,1) + arrPosCol[i].substring(1, arrPosCol[i].length).toLowerCase();
            dataItem1['pos' + larr[i] + 'SaleAmt'] = posColList[i].posNm;
            dataItem1['pos' + larr[i] + 'DcAmt'] = posColList[i].posNm;
            dataItem1['pos' + larr[i] + 'RealSaleAmt'] = posColList[i].posNm;
            dataItem1['pos' + larr[i] + 'SaleQty'] = posColList[i].posNm;
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
                for (var i = 0; i < posColList.length; i++) {
                    if (col.binding === ("pos" + posColList[i].storePosNo + "SaleQty")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("pos" + posColList[i].storePosNo + "SaleQty")], '') !== '') {
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
                    params.saleDate = selectedRow.saleDate.replaceAll("-","");
                    params.storeCd = $scope.srchStoreCd;
                    params.gubun = "day";

                    $scope._broadcast('prodSaleDtlCtrl', params);
                }

                // 수량 클릭시 상세정보 조회
                for (var i = 0; i < posColList.length; i++) {
                    if (col.binding === ("pos" + posColList[i].storePosNo + "SaleQty")) {

                        var selectedRow = s.rows[ht.row].dataItem;
                        var params      = {};
                        params.saleDate = selectedRow.saleDate.replaceAll("-","");
                        params.storeCd = posColList[i].storeCd;
                        params.posNo = posColList[i].posNo;
                        params.gubun = "dayPos";

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("pos" + posColList[i].storePosNo + "SaleQty")], '') !== '') {
                            $scope._broadcast('prodSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPosCtrl", function(event, data) {
        $scope.searchDayPos();
        event.preventDefault();
    });

    $scope.searchDayPos = function() {

        if($("#dayPosStoreCd").val().split(",").length > 10) {
            $scope._popMsg(messages["day.corner.storeCntAlert"]); // 매장은 최대 10개 선택 가능합니다.
            return false;
        }

        //조회할 POS Key값 셋팅을 위해
        var storePosCd = "";

        // 매장권한 로그인 시
        if(orgnFg !== null && orgnFg === 'STORE') {

            // 본인 매장것만 조회
            $("#dayPosStoreCd").val(storeCd);

            // 매장권한의 경우, 이미 자기의 포스 값을 가져왔음.
            storePosCd = posCol;
        }

        // 본사권한 로그인 시
        if(orgnFg !== null && orgnFg === 'HQ') {

            // 매장코드 값 필수
            if ($("#dayPosStoreCd").val() === "") {
                s_alert.pop("매장을 선택해주세요.");
                return;
            }

            // 해당 본사의 전체 포스에서 조회할 매장의 포스만 추려내기
            for (var i = 0; i < posColList.length; i++) {
                if($("#dayPosStoreCd").val().indexOf(posColList[i].storeCd) > -1){
                    storePosCd += "," + arrPosCol[i];
                }
            }

            storePosCd = (storePosCd !== "" ? storePosCd.substring(1, storePosCd.length) : "");
        }

        var grid = wijmo.Control.getControl("#wjGridDayPosList");
        var columns = grid.columns;
        var arr = storePosCd.split(",");

        // 기존에 조회된 컬럼 제거
        if(columns.length > 6) {
            var removeItem = [];
            for (var j = 6; j < columns.length; j++) {
                removeItem[j-6] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 포스별 총매출, 총할인, 실매출, 수량 컬럼 생성
        if(arr.length > 0 && storePosCd !== "") {
            for (var i = 0; i < arr.length; i++) {
                arr[i] = arr[i].substring(0,1) + arr[i].substring(1, arr[i].length).toLowerCase();
                columns.push(new wijmo.grid.Column({ header: messages["day.dayPos.saleAmt"], binding : 'pos' + arr[i] + 'SaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["day.dayPos.dcAmt"], binding : 'pos' + arr[i] + 'DcAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["day.dayPos.realSaleAmt"], binding : 'pos' + arr[i] + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["day.dayPos.saleQty"], binding : 'pos' + arr[i] + 'SaleQty', align: "right", isReadOnly: "true", aggregate: "Sum"}));
            }
        }

        // 포스별 매출 조회
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd = $("#dayPosStoreCd").val();
        params.posCol = posCol;

        $scope.srchStoreCd = params.storeCd;

        $scope._inquiryMain("/sale/day/day/day/getDayPosList.sb", params, function() {}, false);

    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayPosStoreShow = function () {
        $scope._broadcast('dayPosStoreCtrl');
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
                messages["day.day"] + '(' + messages["day.pos"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);