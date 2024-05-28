/****************************************************************
 *
 * 파일명 : storeChgCostPriceHistory.js
 * 설  명 : 매장원가변경History JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.24     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var procFgData = [
    {"name": "입력", "value": "I"},
    {"name": "수정", "value": "U"},
    {"name": "삭제", "value": "D"}
];

// 원가 변경항목 DropBoxDataMap
var costUprcType = [
    {"name": "마스터원가", "value": "0"},
    {"name": "수불원가", "value": "1"}
];

/**
 * 매장원가변경History 그리드 생성
 */
app.controller('storeChgCostPriceHistoryCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeChgCostPriceHistoryCtrl', $scope, $http, false));

    // 수불년월
    var iostockYm = new wijmo.input.InputDate('#iostockYm', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("costUprcType", costUprcType);
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#startDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 입력구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["storeChgCostPriceHistory.storeCd"];
        dataItem.storeNm = messages["storeChgCostPriceHistory.storeNm"];
        dataItem.prodCd = messages["storeChgCostPriceHistory.prodCd"];
        dataItem.prodNm = messages["storeChgCostPriceHistory.prodNm"];
        dataItem.bCostUprc = messages["storeChgCostPriceHistory.costUprc"];
        dataItem.aCostUprc = messages["storeChgCostPriceHistory.costUprc"];
        dataItem.procFg = messages["storeChgCostPriceHistory.procFg"];
        dataItem.procDt = messages["storeChgCostPriceHistory.procDt"];
        dataItem.modId = messages["storeChgCostPriceHistory.modId"];

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

    // <-- 검색 호출 -->
    $scope.$on("storeChgCostPriceHistoryCtrl", function(event, data) {
        // 매장원가변경History 그리드 조회
        $scope.searchStoreChgCostPriceHistoryList();
        event.preventDefault();
    });

    // 매장원가변경History 그리드 조회
    $scope.searchStoreChgCostPriceHistoryList = function () {
        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo.selectedValue;

        if(params.costUprcType === "0"){
            params.iostockYm = "";
        }else{
            params.iostockYm = wijmo.Globalize.format(iostockYm.value, 'yyyyMM');
            params.storageCd = "999";
        }
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#storeChgCostPriceHistoryStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
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

        $scope._inquiryMain('/base/price/storeChgCostPriceHistory/getStoreChgCostPriceHistoryList.sb', params);
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

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 변경항목 변경시, 수불년월 셋팅
    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "0") {
            $("#divIostockYm").hide();
        } else {
            $("#divIostockYm").show();
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeChgCostPriceHistoryStoreShow = function () {
        $scope._broadcast('storeChgCostPriceHistoryStoreCtrl');
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.costUprcType = $scope.costUprcTypeCombo.selectedValue;

        if (params.costUprcType === "0") {
            params.iostockYm = "";
        } else {
            params.iostockYm = wijmo.Globalize.format(iostockYm.value, 'yyyyMM');
            params.storageCd = "999";
        }
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#storeChgCostPriceHistoryStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
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

        $scope._broadcast('storeChgCostPriceHistoryExcelCtrl', params);
    }

}]);

/**
 * 매장원가변경History 엑셀다운로드 그리드 생성
 */
app.controller('storeChgCostPriceHistoryExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeChgCostPriceHistoryExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 열람구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["storeChgCostPriceHistory.storeCd"];
        dataItem.storeNm = messages["storeChgCostPriceHistory.storeNm"];
        dataItem.prodCd = messages["storeChgCostPriceHistory.prodCd"];
        dataItem.prodNm = messages["storeChgCostPriceHistory.prodNm"];
        dataItem.bCostUprc = messages["storeChgCostPriceHistory.costUprc"];
        dataItem.aCostUprc = messages["storeChgCostPriceHistory.costUprc"];
        dataItem.procFg = messages["storeChgCostPriceHistory.procFg"];
        dataItem.procDt = messages["storeChgCostPriceHistory.procDt"];
        dataItem.modId = messages["storeChgCostPriceHistory.modId"];

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
    $scope.$on("storeChgCostPriceHistoryExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (data) {

        // 파라미터
        var params = data;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/storeChgCostPriceHistory/getStoreChgCostPriceHistoryExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // 엑셀파일명 셋팅
            var fileNm = messages["storeChgCostPriceHistory.storeChgCostPriceHistory"] + '_마스터원가_' + getCurDateTime()+'.xlsx';

            if(params.costUprcType === "1"){
                fileNm = messages["storeChgCostPriceHistory.storeChgCostPriceHistory"] + '_수불원가_' +  params.iostockYm + '_' + getCurDateTime()+'.xlsx';
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
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