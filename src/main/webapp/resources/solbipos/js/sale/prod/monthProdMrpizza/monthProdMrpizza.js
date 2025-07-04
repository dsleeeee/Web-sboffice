/****************************************************************
 *
 * 파일명 : monthProdMrpizza.js
 * 설  명 : 미스터피자 > 상품매출분석 > 월별상품매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 상품표시옵션
var prodOptionComboData = [
    {"name":"단품/세트","value":"1"},
    {"name":"단품/구성","value":"2"},
    {"name":"단품/세트/구성","value":"3"},
    {"name":"모두표시","value":"4"}
];
// 일자표시옵션
var dayOptionComboData = [
    {"name":"월별","value":"1"},
    {"name":"기간합","value":"2"}
];
// 금액보정
var saleAmtFgComboData = [
    {"name":"정상","value":"0"}
    ,   {"name":"보정1","value":"1"}
// ,   {"name":"보정2","value":"2"}
// ,   {"name":"보정3","value":"3"}
];

/**
 *  월별상품매출현황 그리드 생성
 */
app.controller('monthProdMrpizzaCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthProdMrpizzaCtrl', $scope, $http, false));

    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 셋팅
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("prodOptionCombo", prodOptionComboData); // 상품표시옵션
    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션
    $scope._setComboData("saleAmtFgCombo", saleAmtFgComboData); // 금액보정

    // 그리드 매출구분
    $scope.saleFgMap = new wijmo.grid.DataMap([
        {id: "1", name: messages["todayBillSaleDtl.saleY"]},
        {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };
    
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("monthProdMrpizzaCtrl", function (event, data) {
        
        // 월별상품매출현황 리스트 조회
        $scope.searchMonthProdList();
        
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 월별상품매출현황 리스트 조회
    $scope.searchMonthProdList = function () {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달 제한
        if (diffMonth > 3) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        //params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthProdMrpizzaStoreCd").val();
        params.prodCds = $("#monthProdMrpizzaSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        /*params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;*/
        // '전체' 일때
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.saleAmtFg = $scope.saleAmtFg;
        /*params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;*/
        params.listScale = 500;

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/monthProdMrpizza/monthProdMrpizza/getMonthProdMrpizzaList.sb", params, function (){
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 상품표시옵션
                if(params.prodOption === "1"){  // 단품+세트
                    if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "2"){  // 단품+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "3"){  // 단품+세트+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
                        columns[j].visible = false;
                    }
                }

                // 일자표시옵션
                if(params.dayOption === "1"){  // 일자별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo") {
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleYm") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->
        });
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.monthProdMrpizzaSelectShow = function () {
        $scope._broadcast('monthProdMrpizzaSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {

        // 선택취소 값 전달
        $scope._broadcast('prodClassCheckPopUpCtrl', {
            selectCancelFg: $("#_selectCancelFg").val()
        });
        var popUp = $scope.prodClassCheckPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassCheckPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd[0];
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/treePopup/getProdClassCdNmCheck.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = (isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : "");
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
        $("#_selectCancelFg").val("Y");
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1달 제한
        if (diffMonth > 0) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        //params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthProdMrpizzaStoreCd").val();
        params.prodCds = $("#monthProdMrpizzaSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        /*params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;*/
        // '전체' 일때
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.saleAmtFg = $scope.saleAmtFg;
        /*params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;*/

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('monthProdMrpizzaExcelCtrl', params);
        });
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1달 제한
        if (diffMonth > 0) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
         wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
           includeColumnHeaders: true,
           includeCellStyles: false,
           includeColumns: function (column) {
             return column.visible;
           }
         },
             "월별상품매출현황_" + wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '_' + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '_' + getCurDateTime()+'.xlsx', function () {
           $timeout(function () {
             $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
           }, 10);
         });
        }, 10);
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('monthProdMrpizzaExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthProdMrpizzaExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("monthProdMrpizzaExcelCtrl", function (event, data) {

        // 엑셀 리스트 조회
        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/monthProdMrpizza/monthProdMrpizza/getMonthProdMrpizzaExcelList.sb", params, function() {
            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridExcelList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 상품표시옵션
                if(params.prodOption === "1"){  // 단품+세트
                    if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "2"){  // 단품+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "3"){  // 단품+세트+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
                        columns[j].visible = false;
                    }
                }

                // 일자표시옵션
                if(params.dayOption === "1"){  // 일자별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo") {
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleYm") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "월별상품매출현황" + '_' + params.startMonth + '_' + params.endMonth + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
}]);