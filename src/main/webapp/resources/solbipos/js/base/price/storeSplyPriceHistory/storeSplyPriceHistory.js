/****************************************************************
 *
 * 파일명 : storeSplyPriceHistory.js
 * 설  명 : 매장공급가History JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.24     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var procFgData = [
    {"name": "입력", "value": "I"},
    {"name": "수정", "value": "U"},
    {"name": "삭제", "value": "D"}
];
var app = agrid.getApp();

/**
 * 매장공급가History 그리드 생성
 */
app.controller('storeSplyPriceHistoryCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSplyPriceHistoryCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
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
        dataItem.storeCd = messages["storeSplyPriceHistory.storeCd"];
        dataItem.storeNm = messages["storeSplyPriceHistory.storeNm"];
        dataItem.prodCd = messages["storeSplyPriceHistory.prodCd"];
        dataItem.prodNm = messages["storeSplyPriceHistory.prodNm"];
        dataItem.bSplyUprc = messages["storeSplyPriceHistory.splyUprc"];
        dataItem.aSplyUprc = messages["storeSplyPriceHistory.splyUprc"];
        dataItem.procFg = messages["storeSplyPriceHistory.procFg"];
        dataItem.procDt = messages["storeSplyPriceHistory.procDt"];
        dataItem.modId = messages["storeSplyPriceHistory.modId"];

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

    $scope.$on("storeSplyPriceHistoryCtrl", function (event, data) {
        // 매장공급가 History 그리드 조회
        $scope.searchStoreSplyPriceHistoryList();
        event.preventDefault();
    });

    // 매장공급가 History 그리드 조회
    $scope.searchStoreSplyPriceHistoryList = function () {
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#storeSplyPriceHistoryStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.listScale = $scope.listScale;

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquiryMain('/base/price/storeSplyPriceHistory/getStoreSplyPriceHistoryList.sb', params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeSplyPriceHistoryStoreShow = function () {
        $scope._broadcast('storeSplyPriceHistoryStoreCtrl');
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

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#storeSplyPriceHistoryStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.listScale = $scope.listScale;

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._broadcast('storeSplyPriceHistoryExcelCtrl', params);
    }

}]);

/**
 * 매장공급가History 엑셀다운로드
 */
app.controller('storeSplyPriceHistoryExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSplyPriceHistoryExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 입력구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["storeSplyPriceHistory.storeCd"];
        dataItem.storeNm = messages["storeSplyPriceHistory.storeNm"];
        dataItem.prodCd = messages["storeSplyPriceHistory.prodCd"];
        dataItem.prodNm = messages["storeSplyPriceHistory.prodNm"];
        dataItem.bSplyUprc = messages["storeSplyPriceHistory.splyUprc"];
        dataItem.aSplyUprc = messages["storeSplyPriceHistory.splyUprc"];
        dataItem.procFg = messages["storeSplyPriceHistory.procFg"];
        dataItem.procDt = messages["storeSplyPriceHistory.procDt"];
        dataItem.modId = messages["storeSplyPriceHistory.modId"];

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
    $scope.$on("storeSplyPriceHistoryExcelCtrl", function (event, data) {

        // 엑셀 리스트 조회
        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (data) {

        // 파라미터
        var params = data;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/storeSplyPriceHistory/getStoreSplyPriceHistoryExcelList.sb", params, function() {
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
                }, messages["storeSplyPriceHistory.storeSplyPriceHistory"] + '_' +getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
}]);