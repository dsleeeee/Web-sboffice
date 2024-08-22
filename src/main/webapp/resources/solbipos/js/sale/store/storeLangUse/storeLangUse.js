/****************************************************************
 *
 * 파일명 : storeLangUse.js
 * 설  명 : 다국어사용현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.23     김유승      1.0
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

app.controller('storeLangUseCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLangUseCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchDayStartDate", gvStartDate);
    var endDate   = wcombo.genDateVal("#srchDayEndDate", gvEndDate);
    $scope._setComboData("option", optionData);
    $scope.orgnFg        = gvOrgnFg;

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5
    $scope._setComboData("useFgCombo", useFgComboData);      // 매장선택 구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

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
        // dataItem.branchCd       = messages["storeLangUse.branchCd"];
        // dataItem.branchNm       = messages["storeLangUse.branchNm"];
        dataItem.storeCd        = messages["storeLangUse.storeCd"];
        dataItem.storeNm        = messages["storeLangUse.storeNm"];
        dataItem.startDate      = messages["storeLangUse.startDate"];
        dataItem.endDate        = messages["storeLangUse.endDate"];
        dataItem.totCnt         = messages["storeLangUse.totCnt"];
        dataItem.koreanCnt      = messages["storeLangUse.korean"];
        dataItem.koreanPer      = messages["storeLangUse.korean"];
        dataItem.englishCnt     = messages["storeLangUse.english"];
        dataItem.englishPer     = messages["storeLangUse.english"];
        dataItem.chineseCnt     = messages["storeLangUse.chinese"];
        dataItem.chinesePer     = messages["storeLangUse.chinese"];
        dataItem.japaneseCnt    = messages["storeLangUse.japanese"];
        dataItem.japanesePer    = messages["storeLangUse.japanese"];


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
    $scope.$on("storeLangUseCtrl", function (event, data) {
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
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/store/storeLangUse/storeLangUse/getStoreLangUseList.sb", params, function (){

        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
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
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        console.log(params);

        $scope._broadcast('storeLangUseExcelCtrl', params);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeLangUseExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLangUseExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지

        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        // dataItem.branchCd       = messages["storeLangUse.branchCd"];
        // dataItem.branchNm       = messages["storeLangUse.branchNm"];
        dataItem.storeCd        = messages["storeLangUse.storeCd"];
        dataItem.storeNm        = messages["storeLangUse.storeNm"];
        dataItem.startDate      = messages["storeLangUse.startDate"];
        dataItem.endDate        = messages["storeLangUse.endDate"];
        dataItem.totCnt         = messages["storeLangUse.totCnt"];
        dataItem.koreanCnt      = messages["storeLangUse.korean"];
        dataItem.koreanPer      = messages["storeLangUse.korean"];
        dataItem.englishCnt     = messages["storeLangUse.english"];
        dataItem.englishPer     = messages["storeLangUse.english"];
        dataItem.chineseCnt     = messages["storeLangUse.chinese"];
        dataItem.chinesePer     = messages["storeLangUse.chinese"];
        dataItem.japaneseCnt    = messages["storeLangUse.japanese"];
        dataItem.japanesePer    = messages["storeLangUse.japanese"];


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
    $scope.$on("storeLangUseExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/store/storeLangUse/storeLangUse/getStoreLangUseList.sb", params, function (){
            if ($scope.excelFlex.rows.length <= 0) {
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
                    messages["storeLangUse.storeLangUse"]+ '(' + params.startDate + '-' + params.endDate + ')_' + getCurDateTime() +'.xlsx', function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    });
            }, 10);
        });
    };

}]);