/****************************************************************
 *
 * 파일명 : byStoreChgCostPrice.js
 * 설  명 : 기초관리 > 가격관리 > 매장원가임의변경 > 매장별 원가관리 Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.17     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 단위 DropBoxDataMap
var unitFg = [
    {"name": "1원 단위", "value": "1"},
    {"name": "100원 단위", "value": "100"},
    {"name": "1,000원 단위", "value": "1000"}
];
// 반올림 DropBoxDataMap
var modeFg = [
    {"name": "반올림", "value": "0"},
    {"name": "절상", "value": "1"},
    {"name": "절하", "value": "2"}
];


app.controller('byStoreChgCostPriceCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('byStoreChgCostPriceCtrl', $scope, $http, false));

    // 수불년월
    var iostockYm2 = new wijmo.input.InputDate('#iostockYm2', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox2", gvListScaleBoxData);
    $scope._setComboData("costUprcType2", costUprcType);
    $scope._setComboData("saleAmtOption2", saleAmtOptionFg);
    $scope._setComboData("changeUnit", unitFg);
    $scope._setComboData("changeMode", modeFg);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "costUprc") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.prodCd = messages["storeChgCostPrice.prodCd"];
        dataItem.prodNm = messages["storeChgCostPrice.prodNm"];
        dataItem.hqSaleUprc = messages["storeChgCostPrice.saleUprc"];
        dataItem.storeSaleUprc = messages["storeChgCostPrice.saleUprc"];
        dataItem.hqCostUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.storeCostUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.costUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.prcCtrlFg = messages["storeChgCostPrice.prcCtrlFg"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
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

    $scope.$on("byStoreChgCostPriceCtrl", function (event, data) {

        // 조회
        $scope.searchByStoreChgCostPriceList();
        event.preventDefault();
    });

    // 조회
    $scope.searchByStoreChgCostPriceList = function(){

        if (isEmptyObject($("#searchStoreCd").val())) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo2.selectedValue;

        if(params.costUprcType === "0"){
            params.iostockYm = "";
        }else{
            params.iostockYm = wijmo.Globalize.format(iostockYm2.value, 'yyyyMM');
            params.storageCd = "999";
        }

        params.storeCd = $("#searchStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.listScale = $scope.listScale;
        if (brandUseFg === "1" && orgnFg === "HQ") {
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if (params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquirySub('/base/price/storeChgCostPrice/getByStoreChgCostPriceList.sb', params, function () {});
    };

    // 저장
    $scope.saveStoreSplyPrice = function () {

        if( isEmptyObject($("#searchStoreCd").val())) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("원가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
        Number.isInteger = Number.isInteger || function (value) {
            return typeof value === "number" &&
                isFinite(value) &&
                Math.floor(value) === value;
        };

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if ($scope.flex.collectionView.items[i].storeCostUprc !== $scope.flex.collectionView.items[i].costUprc) {

                    // 변경원가 - 필수 입력값 체크
                    if ($scope.flex.collectionView.items[i].costUprc === "" || $scope.flex.collectionView.items[i].costUprc === null) {
                        $scope._popMsg(messages["storeChgCostPrice.costUprcBlank"]); // 변경원가를 입력하세요.
                        return false;
                    }

                    // 변경원가 - 소수점 입력 불가
                    if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].costUprc)) == false) {
                        $scope._popMsg(messages["storeChgCostPrice.costUprcInChk"]); // 변경원가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 변경원가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].costUprc)) {
                        if ((numchkexp2.test($scope.flex.collectionView.items[i].costUprc) == false)) {
                            $scope._popMsg(messages["storeChgCostPrice.costUprcInChk"]); // 변경원가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경원가 - 1000000000 이상 입력 불가
                    if ($scope.flex.collectionView.items[i].costUprc >= 1000000000) {
                        $scope._popMsg(messages["storeChgCostPrice.costUprcInChk"]); // 변경원가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    $scope.flex.collectionView.items[i].storeCd = $("#searchStoreCd").val();

                    $scope.flex.collectionView.items[i].costUprcType = $scope.costUprcTypeCombo2.selectedValue;

                    if($scope.costUprcTypeCombo2.selectedValue === "1"){ // 상품 수불 원가 변경시, 수불년월 포함
                        $scope.flex.collectionView.items[i].iostockYm = wijmo.Globalize.format(iostockYm2.value, 'yyyyMM');
                        $scope.flex.collectionView.items[i].storageCd = "999";	//전체재고용 창고코드 ('001' -> '000' -> '999')
                    }

                    params.push($scope.flex.collectionView.items[i]);

                }
            }
        }

        // console.log('params',params)

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/price/storeChgCostPrice/saveStoreCostPrice.sb', params, function () {
            // 조회
            $scope.searchByStoreChgCostPriceList();
        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 원가 일괄적용 버튼 클릭
    $scope.changeAmt = function () {

        if( isEmptyObject($("#searchStoreCd").val())) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("원가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var saleAmtOption = $scope.saleAmtOption;

        var selectCnt = 0;
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                selectCnt++;
            }
        }

        if (selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if ($("#inputSaleRate2").val() === "") {
            $scope._popMsg("원가 변경비율을 입력해 주세요.");
            return false;
        }

        var newSaleAmt = 0;

        // 변경 원가 적용
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            if ($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;

                if (saleAmtOption === "S") { // 매장판매가
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputSaleRate2").val()) / 100);
                } else if (saleAmtOption === "H") { // 본사판매가
                    newSaleAmt = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputSaleRate2").val()) / 100);
                } else if (saleAmtOption === "SC") { // 매장원가
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeCostUprc) * (Number($("#inputSaleRate2").val()) / 100);
                } else if (saleAmtOption === "HC") { // 본사원가
                    newSaleAmt = Number($scope.flex.collectionView.items[i].hqCostUprc) * (Number($("#inputSaleRate2").val()) / 100);
                }

                $scope.flex.collectionView.items[i].costUprc = $scope.calChangeAmt(newSaleAmt, "1");

            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 변경공급가 계산
    $scope.calChangeAmt = function(amt){

        var ChangeAmt = 0;
        var unit;
        var mode;

        unit = $scope.changeUnit;
        mode = $scope.changeMode;

        if(mode === "0"){ // 반올림
            ChangeAmt = Math.round(amt/(unit*10))*(unit*10);
        }else if(mode === "1"){ //절상
            ChangeAmt = Math.ceil(amt/(unit*10))*(unit*10);
        }else if(mode === "2"){ //절하
            ChangeAmt = Math.floor(amt/(unit*10))*(unit*10);
        }

        return ChangeAmt;
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function () {
        if ($("#tblStoreChange").css("display") === 'none') {
            $("#tblStoreChange").show();
        } else {
            $("#tblStoreChange").hide();
        }
    };

    //공급가 수정시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    // 변경항목 변경시, 수불년월 셋팅
    $scope.selectedIndexChanged2 = function (s) {
        if (s.selectedValue === "0") {
            $("#divIostockYm2").hide();
        } else {
            $("#divIostockYm2").show();
        }
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo2.selectedValue;

        if(params.costUprcType === "0"){
            params.iostockYm = "";
        }else{
            params.iostockYm = wijmo.Globalize.format(iostockYm2.value, 'yyyyMM');
            params.storageCd = "999";
        }

        params.storeCd = $("#searchStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.listScale = $scope.listScale;
        if (brandUseFg === "1" && orgnFg === "HQ") {
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if (params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('byStoreChgCostPriceExcelCtrl', params);
        });
    };

}]);

app.controller('byStoreChgCostPriceExcelCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('byStoreChgCostPriceExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.prodCd = messages["storeChgCostPrice.prodCd"];
        dataItem.prodNm = messages["storeChgCostPrice.prodNm"];
        dataItem.hqSaleUprc = messages["storeChgCostPrice.saleUprc"];
        dataItem.storeSaleUprc = messages["storeChgCostPrice.saleUprc"];
        dataItem.hqCostUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.storeCostUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.costUprc = messages["storeChgCostPrice.costUprc"];
        dataItem.prcCtrlFg = messages["storeChgCostPrice.prcCtrlFg"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
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
    $scope.$on("byStoreChgCostPriceExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/storeChgCostPrice/getByStoreChgCostPriceExcelList.sb", params, function() {

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // 엑셀파일명 셋팅
            var fileNm = messages["storeChgCostPrice.storeChgCostPrice"] + '_' + messages["storeChgCostPrice.byStoreChgCostPrice"] + '_마스터원가_' + getCurDateTime()+'.xlsx';

            if(params.costUprcType === "1"){
                fileNm = messages["storeChgCostPrice.storeChgCostPrice"] + '_' + messages["storeChgCostPrice.byStoreChgCostPrice"] + '_수불원가_' +  params.iostockYm + '_' + getCurDateTime()+'.xlsx';
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        //return column.visible;
                        return column.binding != 'gChk';
                    }
                }, fileNm, function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
}]);