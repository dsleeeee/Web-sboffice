/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(테이블별 매출) controller */
app.controller('tableMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('tableMonthCtrl', $scope, $http, $timeout, true));

    /*$scope.srchTableMonthStartDate = wcombo.genDateVal("#srchTableMonthStartDate", gvStartDate);
    $scope.srchTableMonthEndDate   = wcombo.genDateVal("#srchTableMonthEndDate", gvEndDate);*/
    $scope.isSearch = false;

    //조회조건 콤보박스 데이터 Set
    $scope._setComboData("tableMonthListScaleBox", gvListScaleBoxData);
    var checkInt = true;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        var storeCd = $("#tableMonthSelectStoreCd").val();
        $scope.getReTableNmList(storeCd, "", false);

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("tableMonthCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding.substring(12, 23) === "RealSaleAmt") { // 실매출
                    wijmo.addClass(e.cell, 'wijLink');
                    if(e.cell.textContent === null || e.cell.textContent === ''){
                        e.cell.textContent = '0';
                    }
                } else if (col.binding === "totRealSaleAmt"){
                    wijmo.addClass(e.cell, 'wijLink');
                } else if (col.binding.substring(12, 19) === "SaleCnt"){
                    if(e.cell.textContent === null || e.cell.textContent === ''){
                        e.cell.textContent = '0';
                    }
                } else if (col.binding.substring(12, 20) === "GuestCnt"){
                    if(e.cell.textContent === null || e.cell.textContent === ''){
                        e.cell.textContent = '0';
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 'ColumnHeaders';

        //헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        for(var i = 0; i < s.columnHeaders.rows.length; i++) {
            s.columnHeaders.setCellData(i, "saleYm", messages["tableMonth.saleYm"]);
            s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["tableDay.totRealSaleAmt"]);
            s.columnHeaders.setCellData(i, "totSaleCnt", messages["tableDay.totSaleCnt"]);
            s.columnHeaders.setCellData(i, "totGuestCnt", messages["tableDay.totGuestCnt"]);
        }

        //그리드 아이템포멧 생성
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
            } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
            } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            /* 머지된 헤더 셀 클릭시 정렬 비활성화
             * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
             * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
             */
            if(ht.cellType == 2 && ht.row < 2 && ht.col > 3) {
                s.allowSorting = false;
            } else {
                s.allowSorting = true;
            }

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params       = {};
                params.startDate = selectedRow.saleYm + '01';
                params.endDate = selectedRow.saleYm + '31';
                params.saleYm  = selectedRow.saleYm;
                params.chkPop   = "tablePop";
                params.gubun   = "month";
                var storeTable   = $scope.excelTableCd.split(",");
                var storeTableOrg   = 	$scope.tableCdOrg.split(",");
                var storeCd			=	$scope.excelStoreCd;


                if (col.binding.substring(12, 23) === "RealSaleAmt") { // 실매출 클릭
                    var arrStore= [];
                    var arrTbl= [];
                    for(var i=0; i < storeTable.length; i++) {
                        var temp = storeTable[i].split("||");
                        arrStore.push(temp[0]);
                        arrTbl.push(temp[1]);
                    }

                    //params.storeCd = arrStore[Math.floor(ht.col/3) - 1];
//		    		params.tblCd   = arrStore[Math.floor(ht.col/3) - 1] + '||' + arrTbl[Math.floor(ht.col/3) - 1];
                    params.tblCd   = arrTbl[Math.floor(ht.col/3) - 1];
                    params.storeCd	=	storeCd;

                    $scope._broadcast('saleComTableCtrl', params);
                } else if (col.binding === "totRealSaleAmt") { // 총실매출 클릭
//        			params.tblCd	 = storeTable.join(",");
                    params.tblCd	= storeTableOrg.join(",");
                    params.storeCd	=	$scope.excelStoreCd;
                    $scope._broadcast('saleComTableCtrl', params);
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("tableMonthCtrl", function (event, data) {

        $scope.searchTableMonthList(true);

        var storeCd = $("#tableMonthSelectStoreCd").val();
        var tableCd = $("#tableMonthSelectTableCd").val();

        $scope.getReTableNmList(storeCd, tableCd, true);
    });

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("tableMonthCtrlSrch", function (event, data) {

        if( $("#tableMonthSelectStoreCd").val() === ''){
            $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        $scope.searchTableMonthList(false);

        var storeCd = $("#tableMonthSelectStoreCd").val();
        var tableCd = $("#tableMonthSelectTableCd").val();

        $scope.getReTableNmList(storeCd, tableCd, true);
    });

    // 테이블별매출일자별 리스트 조회
    $scope.searchTableMonthList = function (isPageChk) {

        var startDt = new Date(wijmo.Globalize.format($scope.srchTableMonthStartDate, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchTableMonthEndDate, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3년(36개월) 제한
        if (diffMonth > 36) {
            $scope._popMsg(messages['cmm.dateOver.3year.error']);
            return false;
        }


        // 파라미터
        var params = {};
        params.storeCd = $("#tableMonthSelectStoreCd").val();
        params.tableCd = $("#tableMonthSelectTableCd").val();
        params.hqOfficeCd = $("#hqOfficeCd").val();
        params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
        params.isPageChk = isPageChk;

        //등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchTableMonthStartDate, 'yyyyMM');
            params.endDate = wijmo.Globalize.format($scope.srchTableMonthEndDate, 'yyyyMM');
        }


        $scope.excelStoreCd = params.storeCd;
        $scope.excelTableCd = params.tableCd;
        $scope.excelHqOfficeCd = params.hqOfficeCd;
        $scope.excelStartDate = params.startDate;
        $scope.excelEndDate = params.endDate;
        $scope.isSearch		= true;
        $scope.tableCdOrg   = $("#tableMonthSelectTableCdOrg").val();

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        $scope.currMonth = params.curr;
        $scope.listScale2 = params.listScale;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/table/month/getTableMonthList.sb", params, function() {

            var flex = $scope.flex;
            //row수가 0이면
            if(flex.rows.length === 0){

                var grid = wijmo.Control.getControl("#tableMonthGrid");
                //컬럼 삭제
                while(grid.columns.length > 4){
                    grid.columns.removeAt(grid.columns.length-1);
                }
            }

            $scope.getMonthListCnt(params);

        });
    };

    $scope.getMonthListCnt = function (params){

        // 페이징 처리
        $scope._postJSONQuery.withPopUp("/sale/status/table/day/getMonthListCnt.sb", params, function(response) {

            var list = response.data.data.list;
            // 페이징 처리
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (response.data.message) {
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', 10);
                    $scope._setPagingInfo('curr', 1);
                    $scope._setPagingInfo('totCnt', 1);
                    $scope._setPagingInfo('totalPage', 1);

                    $scope._broadcast('drawPager');
                    $scope._popMsg(response.data.message);
                }
                return false;
            }

            // 페이징 처리
            if (response.data.data.page && response.data.data.page.curr) {
                var pagingInfo = response.data.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }

        });
    }

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.tableMonthStartDateCombo.isReadOnly = $scope.isChecked;
        $scope.tableMonthEndDateCombo.isReadOnly = $scope.isChecked;
    };

    //매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.tableMonthSelectStoreShow = function () {
        $scope._broadcast('tableMonthSelectStoreCtrl');
    };

    //테이블선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.tableMonthSelectTableShow = function () {
        $scope._broadcast('tableMonthSelectTableCtrl');
    };

    //엑셀 다운로드
    $scope.excelDownloadDay = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchTableMonthStartDate, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchTableMonthEndDate, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3년(36개월) 제한
        if (diffMonth > 36) {
            $scope._popMsg(messages['cmm.dateOver.3year.error']);
            return false;
        }

        // 파라미터
        var params = {};

        $scope._broadcast('tableMonthExcelCtrl',params);
    };

    // 조회조건 매장 선택 팝업 닫힐 때 메서드
    $scope.closeSelectStore = function () {
        var storeCd = $("#tableMonthSelectStoreCd").val();
        $scope.getReTableNmList(storeCd, "",  false);
    };

    // 조회조건 테이블 선택 팝업 닫힐 때 메서드
    $scope.closeSelectTable = function () {
        var storeCd = $("#tableMonthSelectStoreCd").val();
        var tableCd = $("#tableMonthSelectTableCd").val();
        $scope.getReTableNmList(storeCd, tableCd,  false);
    };

    //매장의 테이블 리스트 재생성
    $scope.getReTableNmList = function (storeCd, tableCd, grindSet) {
        var url = "/sale/status/table/day/tableNmList.sb";
        var params = {};
        params.storeCd = storeCd;
        params.tableCd = tableCd;
        params.hqOfficeCd = $("#HqOfficeCd").val();

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var arrStroreTable = [];
                    var arrStoreTableNm = [];
                    var arrTblGrpCd = [];

                    for (var i = 0; i < list.length; i++) {
                        arrStroreTable.push(list[i].tableCd);
                        arrStoreTableNm.push(list[i].storeNm + "||" + list[i].tableNm);
                        arrTblGrpCd.push(list[i].tblGrpCd);
                    }

                    $("#tableMonthSelectTableCd").val(arrStroreTable.join());
                    $("#tableMonthSelectTableName").val(arrStoreTableNm.join());

                    storeTableCd = $("#tableMonthSelectTableCd").val();
                    storeTableNm = $("#tableMonthSelectTableName").val();
                    tblGrpCd = arrTblGrpCd.join();

                    if (grindSet) {
                        $scope.makeDataGrid();
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {

        });
    };

    $scope.makeDataGrid = function () {

        var grid = wijmo.Control.getControl("#tableMonthGrid");

        var colLength = grid.columns.length;

        while(grid.columns.length > 4){
            grid.columns.removeAt(grid.columns.length-1);
        }

        var arrTableCd = storeTableCd.split(',');
        var arrTableNm = storeTableNm.split(',');
        var arrTblGrpCd = tblGrpCd.split(',');

        if (arrTableCd != null) {
            for(var i = 0; i < arrTableCd.length; i++) {

                var colValue = arrTableCd[i];
                var colName = arrTableNm[i];
                var colSplit = colName.split('||');

                if(colValue != null && colValue !=''){
                    colValue = colValue.substring(0,1).toLowerCase() + colValue.substring(1, colValue.length);
                }

                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.realSaleAmt"], binding: colValue +'RealSaleAmt', width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.saleCnt"], 	 binding: colValue + 'SaleCnt', width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.guestCnt"], 	 binding: colValue + 'GuestCnt', width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));

                grid.columnHeaders.setCellData(0, colValue + 'RealSaleAmt', colSplit[0]);
                grid.columnHeaders.setCellData(0, colValue + 'SaleCnt', colSplit[0]);
                grid.columnHeaders.setCellData(0, colValue + 'GuestCnt', colSplit[0]);

                grid.columnHeaders.setCellData(1, colValue + 'RealSaleAmt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
                grid.columnHeaders.setCellData(1, colValue + 'SaleCnt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
                grid.columnHeaders.setCellData(1, colValue + 'GuestCnt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
            }
        }

        grid.refresh();

        grid.itemFormatter = function (panel, r, c, cell) {

            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;

                wijmo.setCss(cell, {
                    display : 'table',
                    tableLayout : 'fixed'
                });

                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

                wijmo.setCss(cell.children[0], {
                    display : 'table-cell',
                    verticalAlign : 'middle',
                    textAlign : 'center'
                });
            } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        var rowNm2 = ($scope.currMonth - 1) * $scope.listScale2;
                        cell.textContent = (r + 1 + rowNm2).toString();
                    }
                }
            } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }

        }

        $scope.flex.refresh();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    }
}]);

app.controller('tableMonthExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('tableMonthExcelCtrl', $scope, $http, $timeout, true));

    var checkInt = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 'ColumnHeaders';

        //헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        for(var i = 0; i < s.columnHeaders.rows.length; i++) {
            s.columnHeaders.setCellData(i, "saleYm", messages["tableMonth.saleYm"]);
            s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["tableDay.totRealSaleAmt"]);
            s.columnHeaders.setCellData(i, "totSaleCnt", messages["tableDay.totSaleCnt"]);
            s.columnHeaders.setCellData(i, "totGuestCnt", messages["tableDay.totGuestCnt"]);
        }

        //그리드 아이템포멧 생성
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
            } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
            } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("tableMonthExcelCtrl", function (event, data) {

        var storeCd = $scope.excelStoreCd;
        var tableCd = $scope.excelTableCd;

        if(data != undefined && $scope.isSearch) {
            $scope.getReTableNmList(storeCd, tableCd, true);
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        } else{
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

    });

    // 테이블별 월별  엑셀 리스트 조회
    $scope.searchTableMonthExcelList = function (isPageChk) {

        // 파라미터
        var params = {};
        params.storeCd = $scope.excelStoreCd;
        params.tableCd = $scope.excelTableCd;
        params.startDate = $scope.excelStartDate;
        params.endDate = $scope.excelEndDate;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/table/month/getTableMonthExcelList.sb", params, function() {

            var flex = $scope.excelFlex;
            //row수가 0이면
            if(flex.rows.length === 0){

                var grid = wijmo.Control.getControl("#tableMonthExcelGrid")
                //컬럼 삭제
                while(grid.columns.length > 7){
                    grid.columns.removeAt(grid.columns.length-1);
                }
            }

            if (flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["month.sale"]+'_'+messages["tableDay.table"]+'_'+messages["tableMonth.tableMonthSale"]+'_'+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);

        });
    };

    //매장의 테이블 리스트 재생성
    $scope.getReTableNmList = function (storeCd, tableCd, grindSet) {
        var url = "/sale/status/table/day/tableNmList.sb";
        var params = {};
        params.storeCd = storeCd;
        params.tableCd = tableCd;
        params.hqOfficeCd = $("#HqOfficeCd").val();

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var arrStroreTable = [];
                    var arrStoreTableNm = [];
                    var arrTblGrpCd = [];

                    for (var i = 0; i < list.length; i++) {
                        arrStroreTable.push(list[i].tableCd);
                        arrStoreTableNm.push(list[i].storeNm + "||" + list[i].tableNm);
                        arrTblGrpCd.push(list[i].tblGrpCd);
                    }

                    $("#tableMonthSelectTableCd").val(arrStroreTable.join());
                    $("#tableMonthSelectTableName").val(arrStoreTableNm.join());

                    storeTableCd = $("#tableMonthSelectTableCd").val();
                    storeTableNm = $("#tableMonthSelectTableName").val();
                    tblGrpCd = arrTblGrpCd.join();

                    if (grindSet) {
                        $scope.makeDataGrid();
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {

        });
    };

    $scope.makeDataGrid = function () {

        var grid = wijmo.Control.getControl("#tableMonthExcelGrid");

        var colLength = grid.columns.length;

        while(grid.columns.length > 4){
            grid.columns.removeAt(grid.columns.length-1);
        }

        var arrTableCd = storeTableCd.split(',');
        var arrTableNm = storeTableNm.split(',');
        var arrTblGrpCd = tblGrpCd.split(',');

        if (arrTableCd != null) {
            for(var i = 0; i < arrTableCd.length; i++) {

                var colValue = arrTableCd[i];
                var colName = arrTableNm[i];
                var colSplit = colName.split('||');
                if(colValue != null && colValue !=''){
                    colValue = colValue.substring(0,1).toLowerCase() + colValue.substring(1, colValue.length);
                }

                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.realSaleAmt"], binding: colValue +'RealSaleAmt', width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.saleCnt"], 	 binding: colValue + 'SaleCnt', width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
                grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.guestCnt"], 	 binding: colValue + 'GuestCnt', width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));

                grid.columnHeaders.setCellData(0, colValue + 'RealSaleAmt', colSplit[0]);
                grid.columnHeaders.setCellData(0, colValue + 'SaleCnt', colSplit[0]);
                grid.columnHeaders.setCellData(0, colValue + 'GuestCnt', colSplit[0]);

                grid.columnHeaders.setCellData(1, colValue + 'RealSaleAmt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
                grid.columnHeaders.setCellData(1, colValue + 'SaleCnt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
                grid.columnHeaders.setCellData(1, colValue + 'GuestCnt', arrTblGrpCd[i] + "(" + colSplit[1] + ")");
            }
        }

        grid.itemFormatter = function (panel, r, c, cell) {

            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;

                wijmo.setCss(cell, {
                    display : 'table',
                    tableLayout : 'fixed'
                });

                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

                wijmo.setCss(cell.children[0], {
                    display : 'table-cell',
                    verticalAlign : 'middle',
                    textAlign : 'center'
                });
            } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
            } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }

        }

        $scope.flex.refresh();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

        $scope.searchTableMonthExcelList(true);
    }

}]);
