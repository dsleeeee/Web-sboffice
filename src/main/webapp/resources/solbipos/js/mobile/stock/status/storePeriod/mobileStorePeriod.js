/****************************************************************
 *
 * 파일명 : mobileCurrUnity.js
 * 설  명 : (모바일) 재고현황 > 매장기간수불 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

app.controller('mobileStorePeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStorePeriodCtrl', $scope, $http, true));

    // 조회일자 세팅
    $scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", getToday());
    $scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", getToday());
    $scope.orgnFg = gvOrgnFg;
    $scope.isSearch = false;

    // 조회조건 콤보박스 listScale 세팅
    $scope._setComboData("storePeriodListScaleBox", gvListScaleBoxData);

    //거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStorePeriodIostockSelectVendrShow = function () {
        $scope._broadcast('mobileStorePeriodIostockSelectVendrCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStorePeriodSelectStoreShow = function () {
        $scope._broadcast('mobileStorePeriodSelectStoreCtrl');
    };

    // 단위구분 세팅
    $scope._setComboData("srchUnitFg", [
        {"name": messages["storePeriod.unitStockFg"], "value": "0"},
        {"name": messages["storePeriod.orderPoUnitFg"], "value": "1"}
    ]);

    // 조회옵션 세팅
    $scope._setComboData("srchSrchOption", [
        {"name": messages["storePeriod.qtyTot"], "value": "3"},
        {"name": messages["storePeriod.qty"], "value": "1"},
        {"name": messages["storePeriod.tot"], "value": "2"}
    ]);

    // 조회조건 마감재고표시 데이터 Set
    $scope._setComboData("srchStockOptionCombo", [
        {"name": "미표시", "value": "N"},
        {"name": "표시", "value": "Y"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("storePeriodCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var colQty = col.binding.slice(-3);

                if (col.binding === "prodCd" || (colQty === "Qty" && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty") && s.cells.getCellData(e.row, e.col,false) != null) { // 상품코드 & 수량
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
                var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
                if (rng && rng.columnSpan > 1) {
                    e.preventDefault();
                }
            }

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                var params       = {};
                params.orgnFg = $scope.orgnFg;
                params.prodCd = selectedRow.prodCd; // 상품코드
                params.prodNm = selectedRow.prodNm; // 상품명
                params.startDate = selectedRow.startDate;
                params.endDate = selectedRow.endDate;
                params.storeCd = selectedRow.storeCd;
                params.storeNm = selectedRow.storeNm;
                params.poUnitQty = selectedRow.poUnitQty; // 입수

                if (col.binding === "prodCd") { // 상품코드
                    $scope._broadcast('mobileProdCodeDtlCtrl', params);
                }
                if (col.binding.slice(-3) === "Qty" && selectedRow[col.binding] != null && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty"){
                    var colCode = col.binding;
                    params.storeCd = selectedRow.storeCd;
                    params.poUnitQty = selectedRow.poUnitQty; // 입수
                    params.colCode = colCode; // 수량(컬럼 뒤에 붙는 숫자, 어떤 수량인지 구분)
                    params.ioOccrFg = s.columnHeaders.getCellData(0,ht.col,false);

                    $scope._broadcast('mobileProdQtyDtlCtrl', params);
                }
            }
        }, true);

        // 총매출열에 CSS 추가
        wijmo.addClass(s.columns[2], 'wijLink');
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem			= {};
        dataItem.prodClassNm	= messages["storePeriod.prodClassNm"];
        dataItem.prodCd			= messages["storePeriod.prodCd"];
        dataItem.prodNm			= messages["storePeriod.prodNm"];
        dataItem.storeCd		= messages["storePeriod.storeCd"];
        dataItem.storeNm		= messages["storePeriod.storeNm"];
        dataItem.poUnitQty		= messages["storePeriod.poUnitQty"];
        dataItem.poUnitFgNm		= messages["storePeriod.poUnitFg"];
        dataItem.barcdCd		= messages["storePeriod.barcdCd"];
        dataItem.baseQty		= messages["storePeriod.basicStock"];
        dataItem.baseTot	 	= messages["storePeriod.basicStock"];

        dataItem.storeInQty  	= messages["storePeriod.ioOccr03"]; // 매장입고
        dataItem.storeInTot 	= messages["storePeriod.ioOccr03"];
        dataItem.storeOutQty 	= messages["storePeriod.ioOccr12"]; // 매장반품
        dataItem.storeOutTot 	= messages["storePeriod.ioOccr12"];
        dataItem.purchsInQty 	= messages["storePeriod.ioOccr06"]; // 사입입고
        dataItem.purchsInTot 	= messages["storePeriod.ioOccr06"];
        dataItem.purchsOutQty	= messages["storePeriod.ioOccr18"]; // 사입반품
        dataItem.purchsOutTot	= messages["storePeriod.ioOccr18"];
        dataItem.storeSaleQty	= messages["storePeriod.ioOccr11"]; // 매장판매
        dataItem.storeSaleTot	= messages["storePeriod.ioOccr11"];

        dataItem.moveInQty 		= messages["storePeriod.ioOccr04"]; // 매장이입
        dataItem.moveInTot 		= messages["storePeriod.ioOccr04"];
        dataItem.moveOutQty  	= messages["storePeriod.ioOccr14"]; // 매장이출
        dataItem.moveOutTot 	= messages["storePeriod.ioOccr14"];
        dataItem.disuseQty 		= messages["storePeriod.ioOccr17"]; // 재고폐기
        dataItem.adjQty 		= messages["storePeriod.ioOccr21"]; // 재고조정
        dataItem.setInQty		= messages["storePeriod.ioOccr22"]; // 세트생성

        dataItem.closeQty		= messages["storePeriod.endingStock"];
        dataItem.closeTot	 	= messages["storePeriod.endingStock"];

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

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileStorePeriodCtrl", function (event, data) {

        $scope.searchMobileStorePeriodList(true);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    //다른 컨트롤러의 broadcast 받기(페이징 초기화)
    $scope.$on("mobileStorePeriodCtrlSrch", function (event, data) {
        $scope.comboArray =  $scope.comboArrayForSrc; // ?????????????
        $scope.searchMobileStorePeriodList(false);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 매장기간수불 리스트 조회
    $scope.searchMobileStorePeriodList = function (isPageChk) {
        // 파라미터
        var params     = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#mobileStorePeriodSelectStoreCd").val(); // 매장코드
        params.prodCd = $("#srchProdCd").val(); // 상품코드
        params.prodNm = $("#srchProdNm").val() ; // 상품명
        params.barcdCd = $("#srchBarcdCd").val(); // 바코드
        params.vendrCd = $("#storePeriodIostockSelectVendrCd").val(); // 거래처
        params.prodClassCd	= $scope.prodClassCd; // 분류
        params.unitFg = $scope.unitFgModel; // 단위구분
        params.listScale = $scope.listScaleCombo.text;
        params.isPageChk = isPageChk;

        $scope.excelStartDate	= params.startDate;
        $scope.excelEndDate 	= params.endDate;
        $scope.excelStoreCd		= params.storeCd; // 매장코드
        $scope.excelProdCd		= params.prodCd; // 상품코드
        $scope.excelProdNm		= params.prodNm; // 상품명
        $scope.excelBarcdCd		= params.barcdCd; // 바코드
        $scope.excelVendrCd		= params.vendrCd; // 거래처
        $scope.excelProdClassCd	= params.prodClassCd; // 분류
        $scope.excelUnitFg 		= params.unitFg; // 단위구분
        $scope.excelListScale 	= params.listScale;
        $scope.excelSrchOption	= $scope.srchOption;
        $scope.excelSrchStockOption	= $scope.srchStockOption;
        $scope.isSearch			= true;

        if(params.startDate > params.endDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }

        $scope.srchOptionView();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/stock/status/storePeriod/mobileStorePeriod/mobileStorePeriodList.sb", params,function (){
            gridShowMsgNoData("mobileStorePeriod", $scope.flex, "Y");
        },true);
    };

    // 조회옵션에 따른 visible 처리 (박정은, 20.03.17)
    $scope.srchOptionView = function(){
        var check = $scope.excelSrchOption;
        var check2 = $scope.excelSrchStockOption;
        var grid = wijmo.Control.getControl("#storePeriodGrid");
        var columns = grid.columns;
        var length  = grid.columns.length-2;

        if(check == '1'){ // 수량
            for(var i=7; i<length; i++){
                // 기초/마감재고
                if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                    if(check2 == "N") { // 미표시
                        columns[i].visible = false;
                    } else if(check2 == "Y") { // 표시
                        if(columns[i].binding == 'baseQty' || columns[i].binding == 'closeQty'){
                            columns[i].visible = true;
                        } else if(columns[i].binding == 'baseTot' || columns[i].binding == 'closeTot'){
                            columns[i].visible = false;
                        }
                    }
                } else {
                    if(columns[i].binding.slice(-3) == 'Tot'){
                        columns[i].visible = false;
                    }else if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                        columns[i].visible = true;
                    }
                }
            }
        }else if(check == '2'){ // 금액
            for(var i=7; i<length; i++){
                if(columns[i].binding != 'poUnitQty'){
                    // 기초/마감재고
                    if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                        if(check2 == "N") { // 미표시
                            columns[i].visible = false;
                        } else if(check2 == "Y") { // 표시
                            if(columns[i].binding == 'baseQty' || columns[i].binding == 'closeQty'){
                                columns[i].visible = false;
                            } else if(columns[i].binding == 'baseTot' || columns[i].binding == 'closeTot'){
                                columns[i].visible = true;
                            }
                        }
                    } else {
                        if(columns[i].binding.slice(-3) == 'Qty'){
                            columns[i].visible = false;
                        }else if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else{ //수량 + 금액
            for(var i=0; i<length; i++){
                if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                    // 기초/마감재고
                    if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                        if(check2 == "N") { // 미표시
                            columns[i].visible = false;
                        } else if(check2 == "Y") { // 표시
                            columns[i].visible = true;
                        }
                    } else {
                        columns[i].visible = true;
                    }
                }
            }
        }
    };

    // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.flex.columns;

        for(var i=0; i<columns.length-2; i++){
            if(columns[i].binding === 'prodClassNm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

    //엑셀 다운로드
    $scope.excelDownloadStore = function () {
        // 파라미터
        var params     = {};

        $scope._broadcast('mobileStorePeriodExcelCtrl',params);
    };

}]);


app.controller('mobileStorePeriodExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStorePeriodExcelCtrl', $scope, $http, $timeout, true));

    var checkInt = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 총매출열에 CSS 추가
        wijmo.addClass(s.columns[2], 'wijLink');
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem				= {};
        dataItem.prodClassNm		= messages["storePeriod.prodClassNm"];
        dataItem.prodCd				= messages["storePeriod.prodCd"];
        dataItem.prodNm				= messages["storePeriod.prodNm"];
        dataItem.storeCd			= messages["storePeriod.storeCd"];
        dataItem.storeNm			= messages["storePeriod.storeNm"];
        dataItem.poUnitQty			= messages["storePeriod.poUnitQty"];
        dataItem.poUnitFgNm			= messages["storePeriod.poUnitFg"];
        dataItem.barcdCd			= messages["storePeriod.barcdCd"];
        dataItem.baseQty		= messages["storePeriod.basicStock"];
        dataItem.baseTot	 	= messages["storePeriod.basicStock"];

        dataItem.storeInQty  	= messages["storePeriod.ioOccr03"]; // 매장입고
        dataItem.storeInTot 	= messages["storePeriod.ioOccr03"];
        dataItem.storeOutQty 	= messages["storePeriod.ioOccr12"]; // 매장반품
        dataItem.storeOutTot 	= messages["storePeriod.ioOccr12"];
        dataItem.purchsInQty 	= messages["storePeriod.ioOccr06"]; // 사입입고
        dataItem.purchsInTot 	= messages["storePeriod.ioOccr06"];
        dataItem.purchsOutQty	= messages["storePeriod.ioOccr18"]; // 사입반품
        dataItem.purchsOutTot	= messages["storePeriod.ioOccr18"];
        dataItem.storeSaleQty	= messages["storePeriod.ioOccr11"]; // 매장판매
        dataItem.storeSaleTot	= messages["storePeriod.ioOccr11"];

        dataItem.moveInQty 		= messages["storePeriod.ioOccr04"]; // 매장이입
        dataItem.moveInTot 		= messages["storePeriod.ioOccr04"];
        dataItem.moveOutQty  	= messages["storePeriod.ioOccr14"]; // 매장이출
        dataItem.moveOutTot 	= messages["storePeriod.ioOccr14"];
        dataItem.disuseQty 		= messages["storePeriod.ioOccr17"]; // 재고폐기
        dataItem.adjQty 		= messages["storePeriod.ioOccr21"]; // 재고조정
        dataItem.setInQty		= messages["storePeriod.ioOccr22"]; // 세트생성

        dataItem.closeQty		= messages["storePeriod.endingStock"];
        dataItem.closeTot	 	= messages["storePeriod.endingStock"];

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
    $scope.$on("mobileStorePeriodExcelCtrl", function (event, data) {
        if(data != undefined && $scope.isSearch) {
            $scope.searchMobileStorePeriodExcelList(true);
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        } else{
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

    });

    // 조회옵션에 따른 visible 처리 (박정은, 20.03.17)
    $scope.srchOptionView = function(){
        var check = $scope.excelSrchOption;
        var check2 = $scope.excelSrchStockOption;
        var grid = wijmo.Control.getControl("#storePeriodExcelGrid");
        var columns = grid.columns;
        var length  = grid.columns.length;

        if(check == '1'){ // 수량
            for(var i=0; i<length; i++){
                // 기초/마감재고
                if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                    if(check2 == "N") { // 미표시
                        columns[i].visible = false;
                    } else if(check2 == "Y") { // 표시
                        if(columns[i].binding == 'baseQty' || columns[i].binding == 'closeQty'){
                            columns[i].visible = true;
                        } else if(columns[i].binding == 'baseTot' || columns[i].binding == 'closeTot'){
                            columns[i].visible = false;
                        }
                    }
                } else {
                    if(columns[i].binding.slice(-3) == 'Tot'){
                        columns[i].visible = false;
                    }else if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                        columns[i].visible = true;
                    }
                }
            }
        }else if(check == '2'){ // 금액
            for(var i=0; i<length; i++){
                if(columns[i].binding != 'poUnitQty'){
                    // 기초/마감재고
                    if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                        if(check2 == "N") { // 미표시
                            columns[i].visible = false;
                        } else if(check2 == "Y") { // 표시
                            if(columns[i].binding == 'baseQty' || columns[i].binding == 'closeQty'){
                                columns[i].visible = false;
                            } else if(columns[i].binding == 'baseTot' || columns[i].binding == 'closeTot'){
                                columns[i].visible = true;
                            }
                        }
                    } else {
                        if(columns[i].binding.slice(-3) == 'Qty'){
                            columns[i].visible = false;
                        }else if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else{ //수량 + 금액
            for(var i=0; i<length; i++){
                if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                    if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty'){
                        // 기초/마감재고
                        if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
                            if(check2 == "N") { // 미표시
                                columns[i].visible = false;
                            } else if(check2 == "Y") { // 표시
                                columns[i].visible = true;
                            }
                        } else {
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }
    };

    // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.excelFlex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'prodClassNm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

    // 전체 엑셀 리스트 조회
    $scope.searchMobileStorePeriodExcelList = function (isPageChk) {// 파라미터

        var params     = {};
        params.startDate = $scope.excelStartDate;
        params.endDate = $scope.excelEndDate;
        params.storeCd = $scope.excelStoreCd; // 매장코드
        params.prodCd = $scope.excelProdCd; // 상품코드
        params.prodNm = $scope.excelProdNm; // 상품명
        params.barcdCd = $scope.excelBarcdCd; // 바코드
        params.vendrCd = $scope.excelVendrCd; // 거래처
        params.prodClassCd	= $scope.excelProdClassCd; // 분류
        params.unitFg = $scope.excelUnitFg; // 단위구분
        params.listScale = $scope.excelListScale;

        if(params.startDate > params.endDate){
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }

        $scope.srchOptionView();
        $scope.isChkProdClassDisplay();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/mobile/stock/status/storePeriod/mobileStorePeriod/mobileStorePeriodExcelList.sb", params, function(){
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, $(menuNm).selector + '_'+messages["storePeriod.storePeriod"]+'_'+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);