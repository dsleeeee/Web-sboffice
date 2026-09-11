/****************************************************************
 *
 * 파일명 : storeLangUseBenson.js
 * 설  명 : (벤슨) 다국어사용현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var optionData = [
    {"name":"전체","value":"all"},
    {"name":"매장별","value":"store"}
];

// 매장선택 전체/선택 구분
var useFgComboData = [
    {"name":"매장선택","value":"S"},
    {"name":"전체","value":"A"}
];

app.controller('storeLangUseBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLangUseBensonCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchDayStartDate", gvStartDate);
    var endDate   = wcombo.genDateVal("#srchDayEndDate", gvEndDate);
    $scope._setComboData("option", optionData);
    $scope.orgnFg        = gvOrgnFg;

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("useFgCombo", useFgComboData);      // 매장선택 구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

        $scope.option = 'store';
        $(".multiLangStore").show();

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지

        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        // dataItem.branchCd       = messages["storeLangUseBenson.branchCd"];
        // dataItem.branchNm       = messages["storeLangUseBenson.branchNm"];
        dataItem.storeCd        = messages["storeLangUseBenson.storeCd"];
        dataItem.storeNm        = messages["storeLangUseBenson.storeNm"];
        dataItem.startDate      = messages["storeLangUseBenson.startDate"];
        dataItem.endDate        = messages["storeLangUseBenson.endDate"];
        dataItem.totCnt         = messages["storeLangUseBenson.totCnt"];
        dataItem.koreanCnt      = messages["storeLangUseBenson.korean"];
        dataItem.koreanPer      = messages["storeLangUseBenson.korean"];
        dataItem.englishCnt     = messages["storeLangUseBenson.english"];
        dataItem.englishPer     = messages["storeLangUseBenson.english"];
        dataItem.chineseCnt     = messages["storeLangUseBenson.chinese"];
        dataItem.chinesePer     = messages["storeLangUseBenson.chinese"];
        dataItem.japaneseCnt    = messages["storeLangUseBenson.japanese"];
        dataItem.japanesePer    = messages["storeLangUseBenson.japanese"];


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

        // 비율컬럼 %처리
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "koreanPer" || col.binding === "englishPer"
                    || col.binding === "chinesePer" || col.binding === "japanesePer") {
                    if(e.cell.innerText !== null && e.cell.innerText !== "") {
                        e.cell.innerHTML = e.cell.innerText + "%";
                    }
                }
            }
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeLangUseBensonCtrl", function (event, data) {
        $scope.searchMultiLangUseList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 일별종합 리스트 조회
    $scope.searchMultiLangUseList = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
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
        var params       = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        if($scope.option === "store"){
            params.storeCds   = $("#multiLangStoreCd").val();
        } else {
            params.storeCds = '';
        }

        params.hqOfficeCd = hqOfficeCd;
        params.option = $scope.option;
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/storeLangUseBenson/storeLangUseBenson/getStoreLangUseBensonList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.flex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };

    // 옵션(그룹별/매장별)에 따라 매장선택 숨김/보임
    $scope.changeOption = function (s){
        if(s.selectedValue === "all") {
            $(".multiLangStore").hide();
        }else if(s.selectedValue === "branch"){
            $(".multiLangStore").hide();
        } else if(s.selectedValue === "store"){
            $(".multiLangStore").show();
        }
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
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
        var params       = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        if($scope.option === "store"){
            params.storeCds   = $("#multiLangStoreCd").val();
        } else {
            params.storeCds = '';
        }

        params.hqOfficeCd = hqOfficeCd;
        params.option = $scope.option;
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        console.log(params);

        $scope._broadcast('storeLangUseBensonExcelCtrl', params);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeLangUseBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLangUseBensonExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지

        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        // dataItem.branchCd       = messages["storeLangUseBenson.branchCd"];
        // dataItem.branchNm       = messages["storeLangUseBenson.branchNm"];
        dataItem.storeCd        = messages["storeLangUseBenson.storeCd"];
        dataItem.storeNm        = messages["storeLangUseBenson.storeNm"];
        dataItem.startDate      = messages["storeLangUseBenson.startDate"];
        dataItem.endDate        = messages["storeLangUseBenson.endDate"];
        dataItem.totCnt         = messages["storeLangUseBenson.totCnt"];
        dataItem.koreanCnt      = messages["storeLangUseBenson.korean"];
        dataItem.koreanPer      = messages["storeLangUseBenson.korean"];
        dataItem.englishCnt     = messages["storeLangUseBenson.english"];
        dataItem.englishPer     = messages["storeLangUseBenson.english"];
        dataItem.chineseCnt     = messages["storeLangUseBenson.chinese"];
        dataItem.chinesePer     = messages["storeLangUseBenson.chinese"];
        dataItem.japaneseCnt    = messages["storeLangUseBenson.japanese"];
        dataItem.japanesePer    = messages["storeLangUseBenson.japanese"];


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

        // 비율컬럼 %처리
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "koreanPer" || col.binding === "englishPer"
                    || col.binding === "chinesePer" || col.binding === "japanesePer") {
                    if(e.cell.innerText !== null && e.cell.innerText !== "") {
                        e.cell.innerHTML = e.cell.innerText + "%";
                    }
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeLangUseBensonExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/storeLangUseBenson/storeLangUseBenson/getStoreLangUseBensonList.sb", params, function(response) {
            var grid = $scope.excelFlex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            if (grid.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },
                    messages["storeLangUseBenson.storeLangUseBenson"]+ '(' + params.startDate + '-' + params.endDate + ')_' + getCurDateTime() +'.xlsx', function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    });
            }, 10);
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.excelFlex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };

}]);
