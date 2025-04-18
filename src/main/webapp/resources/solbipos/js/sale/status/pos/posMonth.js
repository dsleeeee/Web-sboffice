/**
 * get application
 */
var app = agrid.getApp();

/** 월별(포스별 매출) controller */
app.controller('posMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posMonthCtrl', $scope, $http, $timeout, true));

    //조회조건 콤보박스 데이터 Set
    $scope._setComboData("posMonthListScaleBox", gvListScaleBoxData);

    var checkInt = true;
    $scope.excelFg = false;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        var storeCd = $("#posMonthSelectStoreCd").val();

//		$scope.getRePosNmList(storeCd);

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("posMonthCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding.substring(col.binding.length, col.binding.length-7) === "SaleQty") { // 수량합계
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
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
            s.columnHeaders.setCellData(i, "yearMonth", messages["pos.yearMonth"]);
            s.columnHeaders.setCellData(i, "saleStoreCnt", messages["pos.saleStore"]);
            s.columnHeaders.setCellData(i, "totSaleAmt", messages["pos.totSaleAmt"]);
            s.columnHeaders.setCellData(i, "totDcAmt", messages["pos.totDcAmt"]);
            s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["pos.totRealSaleAmt"]);
            s.columnHeaders.setCellData(i, "totSaleCnt", messages["pos.totSaleCnt"]);
            s.columnHeaders.setCellData(i, "totSaleQty", messages["pos.totSaleQty"]);
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
    $scope.$on("posMonthCtrl", function (event, data) {

        if( $("#posMonthSelectStoreCd").val() === ''){
            $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        $scope.searchPosMonthList(true);

        var storeCd = $("#posMonthSelectStoreCd").val();
        var posCd = $("#posMonthSelectPosCd").val();

        $scope.getRePosNmList(storeCd, posCd);
    });

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("posMonthCtrlSrch", function (event, data) {

        if( $("#posMonthSelectStoreCd").val() === ''){
            $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        $scope.searchPosMonthList(false);

        var storeCd = $("#posMonthSelectStoreCd").val();
        var posCd = $("#posMonthSelectPosCd").val();

        $scope.getRePosNmList(storeCd, posCd);
    });

    // 포스별매출월별 리스트 조회
    $scope.searchPosMonthList = function (isPageChk) {

        var startDt = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM'));
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
        params.storeCd = $("#posMonthSelectStoreCd").val();
        params.posNo = $("#posMonthSelectPosCd").val();
        params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
        params.arrPosCd = $scope.comboArray; //-포스정보
        params.isPageChk = isPageChk;

        $scope.searchStoreCd   = params.storeCd;
        $scope.searchPosNo     = params.posNo;
        $scope.searchChecked = $scope.isChecked;

        //등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
//			params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM') + "01";
//	    	params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMM') + "31";
            params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
            params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMM');

            $scope.searchStartDate = params.startDate;
            $scope.searchEndDate   = params.endDate;
        }

        if(params.startDate > params.endDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/pos/month/getPosMonthList.sb", params, function() {

            var flex = $scope.flex;
            //row수가 0이면
            if(flex.rows.length === 0){

                var grid = wijmo.Control.getControl("#posMonthGrid");
                //컬럼 삭제
                while(grid.columns.length > 7){
                    grid.columns.removeAt(grid.columns.length-1);
                }
            }

        });

        $scope.excelFg = true;
    };

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.posMonthStartDateCombo.isReadOnly = $scope.isChecked;
        $scope.posMonthEndDateCombo.isReadOnly = $scope.isChecked;
    };

    //매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.posMonthSelectStoreShow = function () {
        $scope._broadcast('posMonthSelectStoreCtrl');
    };

    //포스선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.posMonthSelectPosShow = function () {
        $scope._broadcast('posMonthSelectPosCtrl');
    };

    //엑셀 다운로드
    $scope.excelDownloadDay = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM'));
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

        $scope._broadcast('posMonthExcelCtrl',params);
    };

    //매장의 포스(pos) 리스트 조회
    $scope.getPosNmList = function () {
        var url             = '/sale/status/pos/pos/posNmList.sb';
        var comboParams     = {};

        comboParams.storeCd = $("#posMonthSelectStoreCd").val();
    };

    //매장의 포스 리스트 재생성
    $scope.getRePosNmList = function (storeCd, posCd) {
        var url = "/sale/status/pos/pos/posNmList.sb";
        var params = {};
        params.storeCd = storeCd;
        params.PosNo = posCd;
        params.hqOfficeCd = $("#posMonthSelectHqOfficeCd").val();

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
                    var arrStorePos = [];
                    var arrStorePosNm = [];

                    for (var i = 0; i < list.length; i++) {
                        arrStorePos.push(list[i].posCd);
                        arrStorePosNm.push(list[i].storeNm + "||" + list[i].posNm);
                    }

                    $("#posMonthSelectPosCd").val(arrStorePos.join());
                    $("#posMonthSelectPosName").val(arrStorePosNm.join());

                    storePosCd = $("#posMonthSelectPosCd").val();
                    storePosNm = $("#posMonthSelectPosName").val();

//	    			if (!checkInt) {
                    $scope.makeDataGrid();
//	    			} else {
//	    				checkInt = false;
//	    			}
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {

        });
    };

    $scope.makeDataGrid = function () {

        var grid = wijmo.Control.getControl("#posMonthGrid");

        var colLength = grid.columns.length;

        if (grid.columns.length > 7) {
            for(var i = 7; i < colLength; i++) {
                grid.columns.removeAt(grid.columns.length-1);
            }
        }

        var arrPosCd = storePosCd.split(',');
        var arrPosNm = storePosNm.split(',');

        if (arrPosCd != null) {

            for(var i = 1; i < arrPosCd.length + 1; i++) {

                var colValue = arrPosCd[i-1];
                var colName = arrPosNm[i-1];
                var colSplit = colName.split('||');
                var colSplit2 = colValue.split('||');

                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'DcAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'RealSaleAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleCnt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleQty", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));

                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleQty", colSplit[0]+"("+colSplit2[0]+")");

                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleQty", colSplit[1]);

                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleAmt", messages["pos.SaleAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'DcAmt", messages["pos.DcAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'RealSaleAmt", messages["pos.realSaleAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleCnt", messages["pos.saleCnt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleQty", messages["pos.saleQty"]);

            }
        }

        // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
        grid.addEventListener(grid.hostElement, 'mousedown', function (e) {

            var ht = grid.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {

                var col         = ht.panel.columns[ht.col];
                var selectedRow = grid.rows[ht.row].dataItem;
                var storeNm		= grid.columnHeaders.getCellData(0,ht.col,true);
                var storeCd 	= storeNm.match( /[^()]+(?=\))/g);
                var posNo		= "";
                if(grid.columnHeaders.getCellData(1,ht.col,true).indexOf('-') > -1){
                    posNo =  grid.columnHeaders.getCellData(1,ht.col,true).split('-')[0];
                }else{
                    posNo = grid.columnHeaders.getCellData(1,ht.col,true);
                }

                var params       = {};
                params.chkPop	= "posMonthPop";
                params.saleMonth   = selectedRow.yearMonth;

                if (col.binding.substring(col.binding.length, col.binding.length-8) === "'SaleQty") {
                    params.storeCd   = storeCd;
                    params.posNo	 = storeCd + "||" + posNo;
                    $scope._broadcast('saleComProdCtrl', params); // 수량
                }else if (col.binding === "totSaleQty") { // 수량합계
                    params.storeCd   = $scope.searchStoreCd;
                    params.posNo	 = $scope.searchPosNo;
                    // if($("#posMonthSelectPosNm").val() !== "전체"){
                    //     var splPosNo = {};
                    //     splPosNo = $("#posMonthSelectPosCd").val().split(',');
                    //     for(var i = 0; i < splPosNo.length; i++){
                    //
                    //     }
                    //     alert(splPosNo);
                    //     alert(splPosNo[0]);
                    //     alert(splPosNo.length);
                    //     alert(splPosNo[0].substr(-2,2));
                    //     alert(splPosNo[1].substr(-2,2));
                    // }
                    $scope._broadcast('saleComProdCtrl', params);
                }
            }
        });

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
    }

    $scope.loadedRows = function (s, e) {

        var rowLength = s.rows.length;
        var arrPosCd = storePosCd.split(',');
        var arrPosNm = storePosNm.split(',');

        if (arrPosCd != null) {

            for(var i = 1; i < arrPosCd.length + 1; i++) {

                var colValue = arrPosCd[i-1];
                var colName = arrPosNm[i-1];
                var colSplit = colName.split('||');

                for(var j = 0; j < rowLength; j++) {

                    var saleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", false);
                    var dcAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", false);
                    var realSaleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", false);
                    var saleCnt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", false);
                    var saleQty = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleQty", false);

                    if (saleAmt == null || saleAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", "0");
                    }

                    if (dcAmt == null || dcAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", "0");
                    }

                    if (realSaleAmt == null || realSaleAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", "0");
                    }

                    if (saleCnt == null || saleCnt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", "0");
                    }

                    if (saleQty == null || saleQty == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleQty", "0");
                    }
                }
            }
        }
    }
}]);



/** 월별(포스별 매출) 엑셀 controller */
app.controller('posMonthExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posMonthExcelCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding.substring(col.binding.length, col.binding.length-7) === "SaleQty") { // 수량합계
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
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
            s.columnHeaders.setCellData(i, "yearMonth", messages["pos.yearMonth"]);
            s.columnHeaders.setCellData(i, "saleStoreCnt", messages["pos.saleStore"]);
            s.columnHeaders.setCellData(i, "totSaleAmt", messages["pos.totSaleAmt"]);
            s.columnHeaders.setCellData(i, "totDcAmt", messages["pos.totDcAmt"]);
            s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["pos.totRealSaleAmt"]);
            s.columnHeaders.setCellData(i, "totSaleCnt", messages["pos.totSaleCnt"]);
            s.columnHeaders.setCellData(i, "totSaleQty", messages["pos.totSaleQty"]);
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
    $scope.$on("posMonthExcelCtrl", function (event, data) {
        if(data != undefined && $scope.excelFg) {

            var storeCd = $scope.searchStoreCd;
            var posCd = $scope.searchPosNo;

            $scope.getRePosNmList(storeCd, posCd);

        }else{
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }
    });

    // 포스별매출월별 리스트 조회
    $scope.searchPosMonthExcelList = function (isPageChk) {

        // 파라미터
        var params = {};
        params.storeCd   = $scope.searchStoreCd;
        params.posNo     = storePosCd;
        params.arrPosCd = $scope.comboArray; //-포스정보
        params.isPageChk = isPageChk;

        //등록일자 '전체기간' 선택에 따른 params
        if(!$scope.searchChecked){
            params.startDate = $scope.searchStartDate;
            params.endDate   = $scope.searchEndDate;
        }
//		if(params.startDate > params.endDate){
//			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
//			return false;
//		}

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/pos/month/getPosMonthExcelList.sb", params, function() {

            var flex = $scope.excelFlex;
            //row수가 0이면
            if(flex.rows.length === 0){

                var grid = wijmo.Control.getControl("#posMonthExcelGrid");
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
                }, messages["month.sale"]+'_'+messages["empsale.pos"]+'_'+messages["pos.month"]+'_'+getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };



    //매장의 포스 리스트 재생성
    $scope.getRePosNmList = function (storeCd, posCd) {
        var url = "/sale/status/pos/pos/posNmList.sb";
        var params = {};
        params.storeCd = storeCd;
        params.PosNo = posCd;
        params.hqOfficeCd = $("#posMonthSelectHqOfficeCd").val();

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
                    var arrStorePos = [];
                    var arrStorePosNm = [];

                    for (var i = 0; i < list.length; i++) {
                        arrStorePos.push(list[i].posCd);
                        arrStorePosNm.push(list[i].storeNm + "||" + list[i].posNm);
                    }

                    $scope.arrStorePosExcel   = arrStorePos.join();
                    $scope.arrStorePosNmExcel = arrStorePosNm.join();

                    storePosCd = $scope.arrStorePosExcel;
                    storePosNm = $scope.arrStorePosNmExcel;

//	    			if (!checkInt) {
                    $scope.makeDataGrid();
//	    			} else {
//	    				checkInt = false;
//	    			}
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {

        });
    };

    $scope.makeDataGrid = function () {

        var grid = wijmo.Control.getControl("#posMonthExcelGrid");

        var colLength = grid.columns.length;

        if (grid.columns.length > 7) {
            for(var i = 7; i < colLength; i++) {
                grid.columns.removeAt(grid.columns.length-1);
            }
        }

        var arrPosCd = storePosCd.split(',');
        var arrPosNm = storePosNm.split(',');

        if (arrPosCd != null) {

            for(var i = 1; i < arrPosCd.length + 1; i++) {

                var colValue = arrPosCd[i-1];
                var colName = arrPosNm[i-1];
                var colSplit = colName.split('||');
                var colSplit2 = colValue.split('||');

                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'DcAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'RealSaleAmt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleCnt", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));
                grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleQty", width: 80, align: "right", isReadOnly: "true", aggregate: "Sum"}));

                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[0]+"("+colSplit2[0]+")");
                grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleQty", colSplit[0]+"("+colSplit2[0]+")");

                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[1]);
                grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleQty", colSplit[1]);

                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleAmt", messages["pos.SaleAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'DcAmt", messages["pos.DcAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'RealSaleAmt", messages["pos.realSaleAmt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleCnt", messages["pos.saleCnt"]);
                grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleQty", messages["pos.saleQty"]);

            }
        }

        // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
        grid.addEventListener(grid.hostElement, 'mousedown', function (e) {
            var ht = grid.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {

                var col         = ht.panel.columns[ht.col];
                var selectedRow = grid.rows[ht.row].dataItem;
                var storeNm		= grid.columnHeaders.getCellData(0,ht.col,true);
                var storeCd 	= storeNm.match( /[^()]+(?=\))/g);
                var posNo		= grid.columnHeaders.getCellData(1,ht.col,true);

                var params       = {};
                params.chkPop	= "posMonthPop";
                params.saleMonth   = selectedRow.yearMonth;

                if (col.binding.substring(col.binding.length, col.binding.length-8) === "'SaleQty") {
                    params.storeCd   = storeCd;
                    params.posNo	 = posNo;
                    $scope._broadcast('saleComProdCtrl', params); // 수량
                }else if (col.binding === "totSaleQty") { // 수량합계
                    params.storeCd   = $("#posMonthSelectStoreCd").val();
                    $scope._broadcast('saleComProdCtrl', params);
                }
            }
        });

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

        $scope.searchPosMonthExcelList(true);
    }

    $scope.loadedRows = function (s, e) {

        var rowLength = s.rows.length;
        var arrPosCd = storePosCd.split(',');
        var arrPosNm = storePosNm.split(',');

        if (arrPosCd != null) {

            for(var i = 1; i < arrPosCd.length + 1; i++) {

                var colValue = arrPosCd[i-1];
                var colName = arrPosNm[i-1];
                var colSplit = colName.split('||');

                for(var j = 0; j < rowLength; j++) {

                    var saleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", false);
                    var dcAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", false);
                    var realSaleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", false);
                    var saleCnt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", false);
                    var saleQty = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleQty", false);

                    if (saleAmt == null || saleAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", "0");
                    }

                    if (dcAmt == null || dcAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", "0");
                    }

                    if (realSaleAmt == null || realSaleAmt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", "0");
                    }

                    if (saleCnt == null || saleCnt == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", "0");
                    }
                    if (saleQty == null || saleQty == "") {
                        s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleQty", "0");
                    }
                }
            }
        }
    }
}]);
