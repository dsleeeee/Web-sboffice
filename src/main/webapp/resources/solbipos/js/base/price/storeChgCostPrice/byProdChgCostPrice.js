/****************************************************************
 *
 * 파일명 : byProdChgCostPrice.js
 * 설  명 : 기초관리 > 가격관리 > 매장원가임의변경 > 상품별 원가관리 Tab JavaScript
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

// 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장원가","value":"SC"},
    {"name":"본사원가","value":"HC"}
];

// 원가 변경항목 DropBoxDataMap
var costUprcType = [
    {"name": "마스터원가", "value": "0"},
    {"name": "수불원가", "value": "1"}
];
app.controller('byProdChgCostPriceCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('byProdChgCostPriceCtrl', $scope, $http, false));

    // 본사상품의 가격정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 수불년월
    var iostockYm = new wijmo.input.InputDate('#iostockYm', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("costUprcType", costUprcType);
    $scope._setComboData("saleAmtOption", saleAmtOptionFg);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);             // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList);                       // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList);           // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList);                       // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList);             // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList);             // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList);             // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList);             // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList);             // 매장그룹5

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
        dataItem.storeCd = messages["storeChgCostPrice.storeCd"];
        dataItem.storeNm = messages["storeChgCostPrice.storeNm"];
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

    $scope.$on("byProdChgCostPriceCtrl", function (event, data) {

        // 조회
        $scope.searchByProdChgCostPriceList();
        event.preventDefault();
    });

    // 조회
    $scope.searchByProdChgCostPriceList = function () {

        //if($scope.costUprcTypeCombo.selectedValue === "0") {
            if (isEmptyObject($("#prodCd").val())) {
                $scope._popMsg("상품을 선택해주세요.");
                return false;
            }
        //}

        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo.selectedValue;

        if(params.costUprcType === "0"){
            params.iostockYm = "";
        }else{
            params.iostockYm = wijmo.Globalize.format(iostockYm.value, 'yyyyMM');
            params.storageCd = "999";
        }
        params.prodCd = $("#prodCd").val();
        params.listScale = $scope.listScale;

        // 본사상품의 가격정보 조회 - 본사상품이 있는지 여부 및 가격정보를 가지고 있다가 원가 일괄적용시에 사용
        $scope._postJSONQuery.withOutPopUp('/base/price/storeChgCostPrice/getProdPriceInfo.sb', params, function (response) {

            if ($.isEmptyObject(response.data.data)) {
                $scope._gridDataInit();   // 조회결과가 null이면 그리드 초기화(위에 그리드 정보를 가지고 있음)
                $scope._popMsg(messages["cmm.empty.data"]);
                return false;
            }

            if (!isEmptyObject(response.data.data)) {

                // 본사상품 가격정보 set
                $scope.setProdInfo(response.data.data);

                // 상품별 원가 조회
                params.storeCd = $("#storeCd").val();
                if (brandUseFg === "1" && orgnFg === "HQ") {
                    // 선택한 매장브랜드가 있을 때
                    params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
                    // 선택한 매장브랜드가 없을 때('전체' 일때)
                    if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                        var userHqBrandCd = "";
                        for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                            if (userHqBrandCdComboList[i].value !== null) {
                                userHqBrandCd += userHqBrandCdComboList[i].value + ","
                            }
                        }
                        params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
                    }
                }

                if (momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
                    params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
                    params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
                    params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
                    params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
                    params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
                    params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
                    params.branchCd = $scope.srchBranchCdCombo.selectedValue;
                    params.momsStoreFg01 = $scope.momsStoreFg01;
                    params.momsStoreFg02 = $scope.momsStoreFg02;
                    params.momsStoreFg03 = $scope.momsStoreFg03;
                    params.momsStoreFg04 = $scope.momsStoreFg04;
                    params.momsStoreFg05 = $scope.momsStoreFg05;
                }

                // console.log(params);

                $scope._inquirySub('/base/price/storeChgCostPrice/getByProdChgCostPriceList.sb', params, function () {
                });

            } else {
                $scope._popMsg(messages["cmm.error"]);
                return false;
            }
        });
    };

    // 저장
    $scope.saveProdCostPrice = function () {

        if (isEmptyObject($("#prodCd").val())) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("원가를 일괄적용할 매장을 조회해주세요.");
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

                    $scope.flex.collectionView.items[i].prodCd = $("#prodCd").val();

                    $scope.flex.collectionView.items[i].costUprcType = $scope.costUprcTypeCombo.selectedValue;

                    if($scope.costUprcTypeCombo.selectedValue === "1"){ // 상품 수불 원가 변경시, 수불년월 포함
                        $scope.flex.collectionView.items[i].iostockYm = wijmo.Globalize.format(iostockYm.value, 'yyyyMM');
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
            $scope.searchByProdChgCostPriceList();
        });
    };

    //  원가 일괄적용 콤보박스 선택 이벤트
    $scope.inputSaleAmtReadOnly = false;
    $scope.setSelectedSaleAmtOption = function (s) {
        if (s.selectedValue === 'S') {
            $scope.inputSaleAmtReadOnly = false;
        } else {
            $scope.inputSaleAmtReadOnly = true;
        }
    };

    // 원가 일괄적용
    $scope.changeAmt = function () {

        if (isEmptyObject($("#prodCd").val())) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("원가를 일괄적용할 매장을 조회해주세요.");
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
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if (saleAmtOption === 'S') {
            if ($scope.inputSaleAmt === undefined || $scope.inputSaleAmt === '') {
                $scope._popMsg("일괄적용할 원가를 입력해주세요.");
                return false;
            }
        }

        var newSaleAmt = 0;

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            if ($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;

                if (saleAmtOption === "S") { // 매장판매가
                    newSaleAmt = Number($scope.inputSaleAmt);
                } else if (saleAmtOption === "H") { // 본사판매가
                    newSaleAmt = Number($scope.prodInfo.saleUprc);
                } else if (saleAmtOption === "SC") { // 매장원가
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeCostUprc);
                } else if (saleAmtOption === "HC") { // 본사원가
                    newSaleAmt = Number($scope.prodInfo.costUprc);
                }

                $scope.flex.collectionView.items[i].costUprc = newSaleAmt;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 상품선택 모듈 팝업 사용시 정의 (상품찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodShow = function () {
        $scope._broadcast('prodCtrl');
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function () {
        if ($("#tblProdChange").css("display") === 'none') {
            $("#tblProdChange").show();
        } else {
            $("#tblProdChange").hide();
        }
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };
    
    //원가 수정시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    // 변경항목 변경시, 수불년월 셋팅
    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "0") {
            $("#divIostockYm").hide();
        } else {
            $("#divIostockYm").show();
        }
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo.selectedValue;

        if(params.costUprcType === "0"){
            params.iostockYm = "";
        }else{
            params.iostockYm = wijmo.Globalize.format(iostockYm.value, 'yyyyMM');
            params.storageCd = "999";
        }
        params.prodCd = $("#prodCd").val();
        params.storeCd = $("#storeCd").val();
        if (brandUseFg === "1" && orgnFg === "HQ") {
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        if (momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }


        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('byProdChgCostPriceExcelCtrl', params);
        });
    };

}]);

app.controller('byProdChgCostPriceExcelCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('byProdChgCostPriceExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.storeCd = messages["storeChgCostPrice.storeCd"];
        dataItem.storeNm = messages["storeChgCostPrice.storeNm"];
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
    $scope.$on("byProdChgCostPriceExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/storeChgCostPrice/getByProdChgCostPriceExcelList.sb", params, function() {

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // 엑셀파일명 셋팅
            var fileNm = messages["storeChgCostPrice.storeChgCostPrice"] + '_' + messages["storeChgCostPrice.byProdChgCostPrice"] + '_마스터원가_' + getCurDateTime()+'.xlsx';

            if(params.costUprcType === "1"){
                fileNm = messages["storeChgCostPrice.storeChgCostPrice"] + '_' + messages["storeChgCostPrice.byProdChgCostPrice"] + '_수불원가_' +  params.iostockYm + '_' + getCurDateTime()+'.xlsx';
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