/****************************************************************
 *
 * 파일명 : saleCancelStatusAll.js
 * 설  명 : 미스터피자 > 마케팅조회 > 취소현황 > 전체점포 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.31     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 취소현황(메인리스트) controller */
app.controller('saleCancelStatusAllCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleCancelStatusAllCtrl', $scope, $http, $timeout, true));

    $scope.srchStartDateAll = wcombo.genDateVal("#srchStartDateAll", getToday());
    $scope.srchEndDateAll   = wcombo.genDateVal("#srchEndDateAll", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        s.refresh();
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("saleCancelStatusAllCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "rtnAmt" || col.binding === "cancelAmt") { // 매장명
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            /* 머지된 헤더 셀 클릭시 정렬 비활성화
               * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
               * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
               */
            if(ht.cellType == 2 && ht.row < 1 && ht.col > 0) {
                s.allowSorting = false;
            } else {
                s.allowSorting = true;
            }

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params       = {};
                params.storeCd   = selectedRow.storeCd;
                params.storeNm   = selectedRow.storeNm;
                params.startDate = wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd');
                params.endDate   = wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd');
                if (col.binding === "rtnAmt" || col.binding === "cancelAmt") { // 매장명
                    if(col.binding === "rtnAmt"){
                        params.cancelFg = '1';
                    }else{
                        params.cancelFg = '2';
                    }
                    $scope._broadcast('saleCancelStatusAllDtlCtrl', params);
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.storeCd		= '';
        dataItem.storeNm      	= '';
        dataItem.saleCnt  	    = '';
        dataItem.realSaleAmt    = '';
        dataItem.rtnCnt  	    = '';
        dataItem.rtnAmt      	= '';
        dataItem.cancelCnt  	= '';
        dataItem.cancelAmt  	= '';

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


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleCancelStatusAllCtrl", function (event, data) {
        $scope.searchSaleCancelStatusList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 코너별매출일자별 리스트 조회
    $scope.searchSaleCancelStatusList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params          = {};
        params.storeCd      = $("#saleCancelStatusAllSelctStoreCd").val();
        params.startDate    = wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd');
        if(params.startDate > params.endDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/saleCancelStatus/saleCancelStatus/getSaleCancelStatusList.sb", params, function (){
            var grid = wijmo.Control.getControl("#saleCancelStatusAllMainGrid");
            var dataItem         = {};
            dataItem.storeCd		= params.startDate + '~' + params.endDate;
            dataItem.storeNm      	= params.startDate + '~' + params.endDate;
            dataItem.saleCnt  	    = params.startDate + '~' + params.endDate;
            dataItem.realSaleAmt    = params.startDate + '~' + params.endDate;
            dataItem.rtnCnt  	    = params.startDate + '~' + params.endDate;
            dataItem.rtnAmt      	= params.startDate + '~' + params.endDate;
            dataItem.cancelCnt  	= params.startDate + '~' + params.endDate;
            dataItem.cancelAmt  	= params.startDate + '~' + params.endDate;
            grid.columnHeaders.rows[0].dataItem = dataItem;

        });

        //메인그리드 조회후 상세그리드 조회.
        $scope.loadedRows = function(sender, args){
            var rows = sender.rows;

            var params		 = {};
            if(rows.length > 0){
                params.storeCd   = rows[0].dataItem.storeCd;
                params.storeNm   = rows[0].dataItem.storeNm;

                params.startDate = wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd');
                params.endDate = wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd');
                params.cancelFg = '1';


                $scope._broadcast("saleCancelStatusAllDtlCtrl", params);
            }else{
                //$scope._broadcast("saleCancelStatusAllDtlCtrl", params);
                // 바코드별 매출 그리드 조회 후 상세내역 그리드 초기화
                var orderStockInfoDtlScope = agrid.getScope('saleCancelStatusAllDtlCtrl');
                orderStockInfoDtlScope.dtlGridDefault();

            }
            // 코너별 매출현황 상세조회.

        }
    };

    // 엑셀다운로드 (상단,하단 그리드 같이)
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

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

        // 취소현황 그리드
        var workBook1 = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
            includeColumnHeaders: true,
            includeCellStyles: false,
            function() {
                // 원래의 합계 행 데이터로 복원
                groupRow.dataItem = originalDataItem;
            }
        });

        var vScope = agrid.getScope("saleCancelStatusAllDtlCtrl");

        // 합계 행(GroupRow) 가져오기
        var groupRowDtl = vScope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItemDtl = groupRowDtl.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBindingDtl = vScope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItemDtl = {};
        newDataItemDtl[firstColumnBindingDtl] = '합계';
        groupRowDtl.dataItem = newDataItemDtl;

        // 취소현황 상세 그리드
        var workBook2 = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(vScope.flex, {
            includeColumnHeaders: true,
            includeCellStyles: false,
            function() {
                // 원래의 합계 행 데이터로 복원
                groupRowDtl.dataItem = originalDataItemDtl;
            }
        });

        // 시트 정보 push
        workBook1.sheets.push(workBook2.sheets[0]);
        workBook1.saveAsync("취소현황" + '_' + wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd') + '_' + getCurDateTime() + '.xlsx');
    }

}]);

/** 취소현황 상세(일자별 상세) controller */
app.controller('saleCancelStatusAllDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleCancelStatusAllDtlCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("saleCancelStatusAllDtlCtrl");
    }

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleCancelStatusAllDtlCtrl", function (event, data) {
        if(data != undefined){
            $scope.startDate = data.startDate;
            $scope.endDate   = data.endDate;
            $scope.storeCd   = data.storeCd;
            $scope.cancelFg  = data.cancelFg;
        }
        $scope.searchSaleCancelStatusDtlList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 코너별매출일자별 리스트 조회
    $scope.searchSaleCancelStatusDtlList = function () {
        // 파라미터
        var params       = {};
//	    params.listScale = $scope.cornerDayListScale; //-페이지 스케일 갯수
        params.startDate = $scope.startDate;
        params.endDate   = $scope.endDate;
        params.storeCd   = $scope.storeCd;
        params.cancelFg  = $scope.cancelFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sale/marketing/saleCancelStatus/saleCancelStatus/getSaleCancelStatusDtlList.sb", params);
        $scope.flex.refresh();

    };

    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv          = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };

    // 엑셀다운로드
    $scope.excelDownloadDtl = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var cancelFg = '';
        if($scope.cancelFg !== null && $scope.cancelFg !== undefined && $scope.cancelFg !== '') {
            cancelFg = $scope.cancelFg === '1' ? '결제취소' : ($scope.cancelFg === '2' ? '주문취소' : '취소');
        }

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
                cancelFg + "현황상세" + '_' + $scope.startDate + '_' + $scope.endDate + '-' +getCurDateTime() + '.xlsx', function () {
                    // 원래의 합계 행 데이터로 복원
                    groupRow.dataItem = originalDataItem;

                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);