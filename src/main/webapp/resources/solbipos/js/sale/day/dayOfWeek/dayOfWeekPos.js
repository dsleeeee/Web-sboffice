/****************************************************************
 *
 * 파일명 : dayOfWeekPos.js
 * 설  명 : 기간별매출 > 요일별탭 > 포스별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  포스별 매출 조회 그리드 생성
 */
app.controller('dayOfWeekPosCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayOfWeekPosCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayOfWeekPos", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayOfWeekPos", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayOfWeekPosCtrl");

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
        dataItem.yoil    = messages["dayofweek.yoil"];
        dataItem.storeCnt    = messages["dayofweek.storeCnt"];
        dataItem.totSaleAmt    = messages["dayofweek.pos.totSaleAmt"];
        dataItem.totDcAmt    = messages["dayofweek.pos.totDcAmt"];
        dataItem.totRealSaleAmt    = messages["dayofweek.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["dayofweek.totSaleQty"];

        // 포스구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrPosCol.length; i++) {
            dataItem['pos' + arrPosCol[i] + 'SaleAmt'] = posColList[i].storeNm;
            dataItem['pos' + arrPosCol[i] + 'DcAmt'] = posColList[i].storeNm;
            dataItem['pos' + arrPosCol[i] + 'RealSaleAmt'] = posColList[i].storeNm;
            dataItem['pos' + arrPosCol[i] + 'SaleQty'] = posColList[i].storeNm;
        }

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1         = {};
        dataItem1.yoil    = messages["dayofweek.yoil"];
        dataItem1.storeCnt    = messages["dayofweek.storeCnt"];
        dataItem1.totSaleAmt    = messages["dayofweek.pos.totSaleAmt"];
        dataItem1.totDcAmt    = messages["dayofweek.pos.totDcAmt"];
        dataItem1.totRealSaleAmt    = messages["dayofweek.totRealSaleAmt"];
        dataItem1.totSaleQty    = messages["dayofweek.totSaleQty"];

        // 포스구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrPosCol.length; i++) {
            dataItem1['pos' + arrPosCol[i] + 'SaleAmt'] = posColList[i].posNm;
            dataItem1['pos' + arrPosCol[i] + 'DcAmt'] = posColList[i].posNm;
            dataItem1['pos' + arrPosCol[i] + 'RealSaleAmt'] = posColList[i].posNm;
            dataItem1['pos' + arrPosCol[i] + 'SaleQty'] = posColList[i].posNm;
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
    };

    // <-- 검색 호출 -->
    $scope.$on("dayOfWeekPosCtrl", function(event, data) {
        $scope.searchDayOfWeekPos();
        event.preventDefault();
    });

    $scope.searchDayOfWeekPos = function() {

        //조회할 POS Key값 셋팅을 위해
        var storePosCd = "";

        // 매장권한 로그인 시
        if(orgnFg !== null && orgnFg === 'STORE') {

            // 본인 매장것만 조회
            $("#dayofweekPosStoreCd").val(storeCd);

            // 매장권한의 경우, 이미 자기의 포스 값을 가져왔음.
            storePosCd = posCol;
        }

        // 본사권한 로그인 시
        if(orgnFg !== null && orgnFg === 'HQ') {

            // 매장코드 값 필수
            if ($("#dayofweekPosStoreCd").val() === "") {
                s_alert.pop("매장을 선택해주세요.");
                return;
            }

            // 해당 본사의 전체 포스에서 조회할 매장의 포스만 추려내기
            for (var i = 0; i < posColList.length; i++) {
                if($("#dayofweekPosStoreCd").val().indexOf(posColList[i].storeCd) > -1){
                    storePosCd += "," + arrPosCol[i];
                }
            }

            storePosCd = (storePosCd !== "" ? storePosCd.substring(1, storePosCd.length) : "");
        }

        var grid = wijmo.Control.getControl("#wjGridDayOfWeekPosList");
        var columns = grid.columns;
        var arr = storePosCd.split(",");

        // 기존에 조회된 컬럼 제거
        if(columns.length > 5) {
            var removeItem = [];
            for (var j = 5; j < columns.length; j++) {
                removeItem[j-5] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 포스별 총매출, 총할인, 실매출, 수량 컬럼 생성
        if(arr.length > 0 && storePosCd !== "") {
            for (var i = 0; i < arr.length; i++) {
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.saleAmt"], binding : 'pos' + arr[i] + 'SaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.dcAmt"], binding : 'pos' + arr[i] + 'DcAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.realSaleAmt"], binding : 'pos' + arr[i] + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.saleQty"], binding : 'pos' + arr[i] + 'SaleQty', align: "right", isReadOnly: "true", aggregate: "Sum"}));
            }
        }

        // 포스별 매출 조회
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayofweekPosStoreCd").val();
        params.posCol    = posCol;

        $scope._inquiryMain("/sale/day/dayOfWeek/dayOfWeek/getDayOfWeekPosList.sb", params, function() {}, false);

    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayofweekPosStoreShow = function () {
        $scope._broadcast('dayofweekPosStoreCtrl');
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
                messages["dayofweek.dayofweek"] + '(' + messages["dayofweek.posSale"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);