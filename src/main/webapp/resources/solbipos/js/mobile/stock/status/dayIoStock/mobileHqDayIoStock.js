/****************************************************************
 *
 * 파일명 : mobileHqDayIoStock.js
 * 설  명 : (모바일) 재고현황 > 일수불현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.19     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 주문대비 입고현황 그리드 controller */
app.controller('mobileHqDayIoStockCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileHqDayIoStockCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchDayIostockStartDate", getToday());

    // 접속 사용자의 권한
    $scope.orgnFg = gvOrgnFg;
    $scope.isChecked = false;

    // 조회조건 단위구분 데이터 Set
    $scope._setComboData("srchUnitFgDisplay", [
        {"name": messages["dayIostock.unitStockFg"], "value": "0"}, // 재고단위
        {"name": messages["dayIostock.unitOrderFg"], "value": "1"} // 주문단위
    ]);

    // 조회조건 조회옵션 데이터 Set
    $scope._setComboData("srchOptionDisplay", [
        {"name": messages["dayIostock.Qty"] + "+" + messages["dayIostock.Amt"], "value": "3"}, // 수량+금액
        {"name": messages["dayIostock.Qty"], "value": "1"}, // 수량
        {"name": messages["dayIostock.Amt"], "value": "2"} // 금액
    ]);

    // 조회조건 마감재고표시 데이터 Set
    $scope._setComboData("srchStockOptionCombo", [
        {"name": "미표시", "value": "N"},
        {"name": "표시", "value": "Y"}
    ]);

    //거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileHqDayIoStockSelectVendrShow = function () {
        $scope._broadcast('mobileHqDayIoStockSelectVendrCtrl');
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


    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    // comboId : combo 생성할 ID
    // gridMapId : grid 에서 사용할 Map ID
    // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    // params : 데이터 조회할 url에 보낼 파라미터
    // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    // callback : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
        var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
        if (url) {
            comboUrl = url;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : comboUrl, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
                        comboArray = [];
                        if (option === "A") {
                            comboData.name  = messages["cmm.all"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        } else if (option === "S") {
                            comboData.name  = messages["cmm.select"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        }

                        for (var i = 0; i < list.length; i++) {
                            comboData       = {};
                            comboData.name  = list[i].nmcodeNm;
                            comboData.value = list[i].nmcodeCd;
                            comboArray.push(comboData);
                        }
                        $scope._setComboData(comboId, comboArray);
                    }

                    if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
                        comboArray = [];
                        for (var i = 0; i < list.length; i++) {
                            comboData      = {};
                            comboData.id   = list[i].nmcodeCd;
                            comboData.name = list[i].nmcodeNm;
                            comboArray.push(comboData);
                        }
                        $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };

    //상품분류 항목표시 함수
    $scope.isChkDt = function() {
        var grid = wijmo.Control.getControl("#dayIostockMainGrid");
        var columns = grid.columns;
        var length  = grid.columns.length-1;
        var isChecked = $scope.isChecked;
        if(isChecked){
            for(var i=0; i<length; i++){
                if(columns[i].binding === 'prodClassNm'){
                    columns[i].visible = true;
                }
            }
        }else{
            for(var i=0; i<length; i++){
                if(columns[i].binding === 'prodClassNm'){
                    columns[i].visible = false;
                }
            }
        }
    };
}]);


/** 주문대비 입고현황 그리드 controller */
app.controller('mobileHqDayIoStockMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileHqDayIoStockMainCtrl', $scope, $http, true));

    $scope.excelFg = false;

    //조회조건 콤보박스 listScale 세팅
    $scope._setComboData("dayIostockMainlistScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mobileHqDayIoStockMainCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var colLength = col.binding.length;
                if (col.binding === "prodCd" || col.binding.slice(-3) === 'Qty' && s.cells.getCellData(e.row, e.col,false) != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty" ) { // 수량
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
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
                var colLength 	= col.binding.length;
                var selectedRow = s.rows[ht.row].dataItem;
                var params       = {};
                params.prodCd = selectedRow.prodCd; // 상품코드
                params.prodNm = selectedRow.prodNm; // 상품명
                params.storeCd		= $("#storeCd").val();
                params.storeNm = $("#storeNm").val();
                params.startDate = selectedRow.startDate; // 시작날짜
                params.endDate = selectedRow.startDate; // 종료날짜

                params.poUnitQty = selectedRow.poUnitQty;
                if (col.binding === "prodCd") { // 상품코드
                    $scope._broadcast('mobileProdCodeDtlCtrl', params);
                }
                if (col.binding.slice(-3) === 'Qty' && selectedRow[col.binding] != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty" ) { // 수량
                    var colCode = col.binding;
                    params.colCode = colCode; // 수량(컬럼 뒤에 붙는 숫자, 어떤 수량인지 구분)
                    params.ioOccrFg = s.columnHeaders.getCellData(0,ht.col,false);

                    $scope._broadcast('mobileProdQtyDtlCtrl', params);
                }
            }
        }, true);

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        s.columnHeaders.rows[0].dataItem = {
            prodClassNm	: messages["dayIostock.prodClassNm"],

            prodCd 			: messages["dayIostock.prodCd"],
            prodNm 			: messages["dayIostock.prodNm"],
            poUnitQty			: messages["dayIostock.poUnitQty"],
            poUnitFgNm		: messages["dayIostock.poUnitFg"],
            barcdCd			: messages["dayIostock.barcdCd"],
            baseQty			:messages["dayIostock.basicStock"],
            baseTot			:messages["dayIostock.basicStock"],

            vendrInQty		:messages["dayIostock.accVendrIn"],
            vendrInTot		:messages["dayIostock.accVendrIn"],
            vendrOutQty		:messages["dayIostock.accVendrOut"],
            vendrOutTot		:messages["dayIostock.accVendrOut"],
            hqOutQty			:messages["dayIostock.hqOut"],
            hqOutTot			:messages["dayIostock.hqOut"],
            hqInQty			:messages["dayIostock.hqIn"],
            hqInTot			:messages["dayIostock.hqIn"],

            storeMoveInQty	:messages["dayIostock.accStoreMoveIn"],
            storeMoveInTot	:messages["dayIostock.accStoreMoveIn"],
            storeMoveOutQty	:messages["dayIostock.accStoreMoveOut"],
            storeMoveOutTot	:messages["dayIostock.accStoreMoveOut"],
            disuseQty			:messages["dayIostock.accDisuse"],
            adjQty 			:messages["dayIostock.accAdj"],
            setInQty			:messages["dayIostock.accSetIn"],

            saleVendrOrderQty	:messages["dayIostock.accSaleVendrOut"],
            saleVendrOrderTot	:messages["dayIostock.accSaleVendrOut"],
            saleVendrRtnQty	:messages["dayIostock.accSaleVendrIn"],
            saleVendrRtnTot	:messages["dayIostock.accSaleVendrIn"],

            closeQty			:messages["dayIostock.endingStock"],
            closeTot			:messages["dayIostock.endingStock"],
        };

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
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileHqDayIoStockMainCtrl", function (event, data) {
        $scope.searchMobileDayIoStockList(true);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    //다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileHqDayIoStockMainCtrlSrch", function (event, data) {
        $scope.searchMobileDayIoStockList(false);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 주문대비 입고현황 리스트 조회
    $scope.searchMobileDayIoStockList = function (isPageChk) {
        $scope.searchedStartDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');

        // 파라미터
        var params         = {};
        params.startDate   = $scope.searchedStartDate;
        params.endDate     = $scope.searchedStartDate;
        params.prodCd	   = $scope.srchProdCd;
        params.prodNm	   = $scope.srchProdNm;
        params.barcdCd	   = $scope.srchBarcdCd;
        params.vendrCd 	   = $("#mobileHqDayIoStockSelectVendrCd").val();

        params.prodClassCd = $scope.prodClassCd;
        params.unitFg 	   = $scope.unitFgModel;
        params.isPageChk   = isPageChk;
        params.listScale   = $scope.listScaleCombo.text;


        $scope.excelStartDate   = params.startDate;
        $scope.excelEndDate     = params.endDate;
        $scope.excelProdCd	    = params.prodCd;
        $scope.excelProdNm	    = params.prodNm;
        $scope.excelBarcdCd	    = params.barcdCd;
        $scope.excelVendrCd 	= params.vendrCd;
        $scope.excelProdClassCd = params.prodClassCd;
        $scope.excelUnitFg 	    = params.unitFg;
        $scope.excelSrchOption	= $scope.srchOption;
        $scope.excelSrchStockOption	= $scope.srchStockOption;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/stock/status/dayIoStock/mobileDayIoStock/viewList.sb", params, function () {
            gridShowMsgNoData("mobileHqDayIoStock", $scope.flex, "Y");
            $scope.displayChg($scope.srchOption, $scope.srchStockOption);
        },true);
        $scope.excelFg = true;
    };


    //조회옵션 함수
    $scope.displayChg = function (srchOption, srchStockOption) {
        var check = srchOption;
        var check2 = srchStockOption;
        var grid = wijmo.Control.getControl("#dayIostockMainGrid");
        var columns = grid.columns;
        var length  = grid.columns.length-1;

        if(check == '1'){ // 수량
            for(var i=6; i<length; i++){
                if(columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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
                        }else if(columns[i].binding.slice(-3) == 'Qty'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else if(check == '2'){ // 금액
            for(var i=6; i<length; i++){
                if(columns[i].binding != 'poUnitQty' && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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
                        }else if(columns[i].binding.slice(-3) == 'Tot'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else{ //수량 + 금액
            for(var i=0; i<length; i++){
                if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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

    //엑셀 다운로드
    $scope.excelDownloadDayIostock = function () {
        // 파라미터
        var params     = {};
        $scope._broadcast('mobileHqDayIoStockMainExcelCtrl', params);

    };
}]);



/** 주문대비 입고현황 그리드 controller */
app.controller('mobileHqDayIoStockMainExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileHqDayIoStockMainExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        s.columnHeaders.rows[0].dataItem = {
            prodClassNm	: messages["dayIostock.prodClassNm"],
            lv2Nm			: messages["dayIostock.prodClassMNm"],
            lv3Nm			: messages["dayIostock.prodClassSNm"],

            prodCd 		: messages["dayIostock.prodCd"],
            prodNm 		: messages["dayIostock.prodNm"],
            poUnitQty		: messages["dayIostock.poUnitQty"],
            poUnitFgNm	: messages["dayIostock.poUnitFg"],
            barcdCd		: messages["dayIostock.barcdCd"],
            baseQty			:messages["dayIostock.basicStock"],
            baseTot			:messages["dayIostock.basicStock"],

            vendrInQty		:messages["dayIostock.accVendrIn"],
            vendrInTot		:messages["dayIostock.accVendrIn"],
            vendrOutQty		:messages["dayIostock.accVendrOut"],
            vendrOutTot		:messages["dayIostock.accVendrOut"],
            hqOutQty			:messages["dayIostock.hqOut"],
            hqOutTot			:messages["dayIostock.hqOut"],
            hqInQty			:messages["dayIostock.hqIn"],
            hqInTot			:messages["dayIostock.hqIn"],

            storeMoveInQty	:messages["dayIostock.accStoreMoveIn"],
            storeMoveInTot	:messages["dayIostock.accStoreMoveIn"],
            storeMoveOutQty	:messages["dayIostock.accStoreMoveOut"],
            storeMoveOutTot	:messages["dayIostock.accStoreMoveOut"],
            disuseQty			:messages["dayIostock.accDisuse"],
            adjQty 			:messages["dayIostock.accAdj"],
            setInQty			:messages["dayIostock.accSetIn"],

            saleVendrOrderQty	:messages["dayIostock.accSaleVendrOut"],
            saleVendrOrderTot	:messages["dayIostock.accSaleVendrOut"],
            saleVendrRtnQty	:messages["dayIostock.accSaleVendrIn"],
            saleVendrRtnTot	:messages["dayIostock.accSaleVendrIn"],

            closeQty			:messages["dayIostock.endingStock"],
            closeTot			:messages["dayIostock.endingStock"],
        };

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
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileHqDayIoStockMainExcelCtrl", function (event, data) {

        if(data != undefined && $scope.excelFg) {
            if($scope.excelStartDate > $scope.excelEndDate){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }
            $scope.searchMobileDayIoStockExcelList(false);
        }else{
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 주문대비 입고현황 리스트 조회
    $scope.searchMobileDayIoStockExcelList = function (isPageChk) {

        // 파라미터
        var params         = {};
        params.startDate   = $scope.excelStartDate;
        params.endDate     = $scope.excelEndDate;
        params.prodCd	   = $scope.excelProdCd;
        params.prodNm	   = $scope.excelProdNm;
        params.barcdCd	   = $scope.excelBarcdCd;
        params.vendrCd 	   = $scope.excelVendrCd;
        params.prodClassCd = $scope.excelProdClassCd;
        params.unitFg 	   = $scope.excelUnitFg;

        params.isPageChk   = isPageChk; //-페이지 초기화 여부

        $scope.displayChg();
        $scope.isChkDt();


        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/mobile/stock/status/dayIoStock/mobileDayIoStock/viewExcelList.sb", params, function () {
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
                }, $(menuNm).selector + '_'+messages["cmmStockStatus.stockStatus"]+'_'+messages["dayIostock.dayIoStockList"] +'_'+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

    $scope.isChkDt = function() {
        var grid = wijmo.Control.getControl("#dayIostockMainExcelGrid");
        var columns = grid.columns;
        var length  = grid.columns.length;
        var isChecked = $scope.isChecked;
        if(isChecked){
            for(var i=0; i<length; i++){
                if(columns[i].binding === 'prodClassNm'){
                    columns[i].visible = true;
                }
            }
        }else{
            for(var i=0; i<length; i++){
                if(columns[i].binding === 'prodClassNm'){
                    columns[i].visible = false;
                }
            }
        }
    };


    //조회옵션 함수
    $scope.displayChg = function () {
        var check = $scope.excelSrchOption;
        var check2 = $scope.excelSrchStockOption;
        var grid = wijmo.Control.getControl("#dayIostockMainExcelGrid");
        var columns = grid.columns;
        var length  = grid.columns.length;

        if(check == '1'){ // 수량
            for(var i=6; i<length; i++){
                var colLength = columns[i].binding.length;
                if(columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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
                        }else if(columns[i].binding.slice(-3) == 'Qty'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else if(check == '2'){ // 금액
            for(var i=6; i<length; i++){
                var colLength = columns[i].binding.length;
                if(columns[i].binding != 'poUnitQty' && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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
                        }else if(columns[i].binding.slice(-3) == 'Tot'){
                            columns[i].visible = true;
                        }
                    }
                }
            }
        }else{ //수량 + 금액
            for(var i=0; i<length; i++){
                if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){
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

}]);
