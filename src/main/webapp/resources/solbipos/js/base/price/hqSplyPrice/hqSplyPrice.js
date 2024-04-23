/****************************************************************
 *
 * 파일명 : hqSplyPrice.js
 * 설  명 : 기초관리 > 가격관리 > 본사공급가관리 > 본사공급가관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.04     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
    //{"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"}
];
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

// 저장 시 매장 적용 구분
var storeSaveFg = [
    {"name": "전매장적용", "value": "all"},
    {"name": "미적용", "value": "none"},
    {"name": "전매장적용(제한매장포함)", "value": "tot"},
    {"name": "매장선택", "value": "choice"}
];

app.controller('hqSplyPriceCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSplyPriceCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("saleAmtOption", saleAmtOptionFg);
    $scope._setComboData("changeUnit", unitFg);
    $scope._setComboData("changeMode", modeFg);
    $scope._setComboData("storeSaveFg", storeSaveFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "splyUprc") {
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
        var dataItem                  = {};
        dataItem.gChk                 = messages["cmm.chk"];
        dataItem.prodCd               = messages["hqSplyPrice.prodCd"];
        dataItem.prodNm               = messages["hqSplyPrice.prodNm"];
        dataItem.hqSaleUprc           = messages["hqSplyPrice.saleUprc"];
        dataItem.hqSplyUprc           = messages["hqSplyPrice.splyUprc"];
        dataItem.splyUprc             = messages["hqSplyPrice.splyUprc"];
        dataItem.prcCtrlFg            = messages["hqSplyPrice.prcCtrlFg"];

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
    };

    $scope.$on("hqSplyPriceCtrl", function (event, data) {
        
        // 조회
        $scope.searchHqSplyPriceList();
        event.preventDefault();
    });

    // 조회
    $scope.searchHqSplyPriceList = function () {

        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.listScale = $scope.listScaleCombo.text;

        $scope._inquirySub('/base/price/hqSplyPrice/getHqSplyPriceList.sb', params, function () {}, false);
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

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 상품분류 팝업 핸들러 추가
        $scope.prodClassPopUpLayer.shown.addHandler(function (s) {
        });
    });

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.choiceSaveStoreShow = function () {
        $scope._broadcast('choiceSaveStoreCtrl');
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function () {
        if ($("#tblHqChange").css("display") === 'none') {
            $("#tblHqChange").show();
        } else {
            $("#tblHqChange").hide();
        }
    };

    // 공급가 일괄적용 버튼 클릭
    $scope.changeAmt = function () {

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("공급가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

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

        if ($("#inputSaleRate").val() === "") {
            $scope._popMsg("공급가 변경비율을 입력해 주세요.");
            return false;
        }

        var newSaleAmt = 0;

        // 변경 공급가 적용
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            if ($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;
                newSaleAmt = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputSaleRate").val()) / 100);

                $scope.flex.collectionView.items[i].splyUprc = $scope.calChangeAmt(newSaleAmt);
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

    //공급가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

    // 저장
    $scope.saveProdPrice = function(){

        if ($scope.flex.collectionView === undefined) {
            $scope._popMsg("공급가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        if($scope.storeSaveFgCombo.selectedValue === "choice") {
            if ($("#choiceSaveStoreCd").val() === "") {
                $scope._popMsg("매장을 선택해주세요");
                return false;
            }
        }

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if ($scope.flex.collectionView.items[i].hqSplyUprc !== $scope.flex.collectionView.items[i].splyUprc) {

                    // 변경공급가 - 필수 입력값 체크
                    if ($scope.flex.collectionView.items[i].splyUprc === "" || $scope.flex.collectionView.items[i].splyUprc === null) {
                        $scope._popMsg(messages["hqSplyPrice.splyUprcBlank"]); // 변경공급가를 입력하세요.
                        return false;
                    }

                    // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
                    Number.isInteger = Number.isInteger || function(value) {
                        return typeof value === "number" &&
                            isFinite(value) &&
                            Math.floor(value) === value;
                    };

                    // 변경공급가 - 소수점 입력 불가
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].splyUprc)) == false){
                        $scope._popMsg(messages["hqSplyPrice.splyUprcInChk"]); // 변경공급가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 변경공급가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].splyUprc)) {
                        if((numchkexp2.test($scope.flex.collectionView.items[i].splyUprc) == false)){
                            $scope._popMsg(messages["hqSplyPrice.splyUprcInChk"]); // 변경공급가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경공급가 - 1000000000 이상 입력 불가
                    if($scope.flex.collectionView.items[i].splyUprc >= 1000000000){
                        $scope._popMsg(messages["hqSplyPrice.splyUprcInChk"]); //  변경공급가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    //$scope.flex.collectionView.items[i].storeCd = $("#searchStoreCd").val();
                    $scope.flex.collectionView.items[i].applyFg = $scope.storeSaveFgCombo.selectedValue;
                    if($scope.storeSaveFgCombo.selectedValue === "choice") {
                        if($("#choiceSaveStoreCd").val() === ""){
                            $scope._popMsg("매장을 선택해주세요");
                            return false;
                        }
                        $scope.flex.collectionView.items[i].saveStoreCds = $("#choiceSaveStoreCd").val();
                    }
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        if ($scope.storeSaveFgCombo.selectedValue !== "none") {
            $scope._popConfirm( "하위매장에 가격이 적용됩니다. 그래도 저장하시겠습니까?", function(){
                console.log(params);
                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save('/base/price/hqSplyPrice/saveHqSplyPrice.sb', params, function(){
                    // 재조회
                    $scope.searchHqSplyPriceList();
                });
            });
        } else {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/price/hqSplyPrice/saveHqSplyPrice.sb', params, function(){
                // 재조회
                $scope.searchHqSplyPriceList();
            });
        }

    };

    // 저장 적용타입 선택
    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "choice") {
            $("#storeSaveStore").show();
        } else {
            $("#storeSaveStore").hide();
        }
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        //파라미터
        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('hqSplyPriceExcelCtrl', params);
        });
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('hqSplyPriceExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSplyPriceExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem                  = {};
        dataItem.prodCd               = messages["hqSplyPrice.prodCd"];
        dataItem.prodNm               = messages["hqSplyPrice.prodNm"];
        dataItem.hqSaleUprc           = messages["hqSplyPrice.saleUprc"];
        dataItem.hqSplyUprc           = messages["hqSplyPrice.splyUprc"];
        dataItem.prcCtrlFg            = messages["hqSplyPrice.prcCtrlFg"];

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
        };
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqSplyPriceExcelCtrl", function (event, data) {

        // 엑셀 리스트 조회
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/hqSplyPrice/getHqSplyPriceExcelList.sb", params, function() {

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
                        //return column.visible;
                        return column.binding != 'gChk';
                    }
                }, messages["hqSplyPrice.hqSplyPrice"] + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);