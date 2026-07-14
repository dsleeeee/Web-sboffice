/****************************************************************
 *
 * 파일명 : monthProdStoreBenson.js
 * 설  명 : (벤슨) 월별상품매출현황(매장별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.08     이다솜      1.0
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

app.controller('monthProdStoreBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthProdStoreBensonCtrl', $scope, $http, true));

    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("prodOptionCombo", prodOptionComboData); // 상품표시옵션
    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션

    // 그리드 매출구분
    $scope.saleFgMap = new wijmo.grid.DataMap([
        {id: "1", name: messages["todayBillSaleDtl.saleY"]},
        {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
    ], 'id', 'name');

    // 상품분류합산조회
    $scope.chkProdClassSumDisplay = false;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("monthProdStoreBensonCtrl", function (event, data) {
        // 리스트 조회
        $scope.searchMonthProdBensonList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchMonthProdBensonList = function () {
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
        params.chkProdClassSumDisplay = $scope.chkProdClassSumDisplay;
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthProdStoreBensonStoreCd").val();
        params.prodCds = $("#monthProdBensonSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
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
        params.listScale = 500;

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/monthProdStoreBenson/monthProdStoreBenson/getMonthProdStoreBensonList.sb", params, function (){
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

                // 상품분류합산조회
                if(params.chkProdClassSumDisplay === true) {
                    if(columns[j].binding === 'mClassCd' || columns[j].binding === 'mClassNm' || columns[j].binding === 'sClassCd' || columns[j].binding === 'sClassNm'
                        || columns[j].binding === 'prodCd' || columns[j].binding === 'sideProdCd' || columns[j].binding === 'selTypeFg' || columns[j].binding === 'sideProdNm') {
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
    $scope.monthProdStoreBensonSelectShow = function () {
        $scope._broadcast('monthProdStoreBensonSelectCtrl');
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

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {

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
        params.chkProdClassSumDisplay = $scope.chkProdClassSumDisplay;
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthProdStoreBensonStoreCd").val();
        params.prodCds = $("#monthProdBensonSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
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
        params.excelType = excelType;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('monthProdStoreBensonExcelCtrl', params);
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
                "월별상품매출현황(매장별)" + '_' +  wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '_' + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '_' + getCurDateTime()+'.xlsx', function () {
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
app.controller('monthProdStoreBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthProdStoreBensonExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("monthProdStoreBensonExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            // 엑셀다운로드 진행 사용자 현재 인원수 체크
            // 다운로드 구분 (0:간소화화면, 1:상품매출분석, 3:미스터피자_간소화화면, 4:미스터피자_상품매출분석)
            /*if(hqOfficeCd === 'H0614' || hqOfficeCd === 'H0616'){
                data.downloadFg = "4";
            }else {*/
                data.downloadFg = "1";
            /*}*/
            data.resrceCd = menuCd;
            data.resrceNm = menuNm;
            data.downloadUseFg = "2"; // 다운로드 사용기능 (0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드)
            data.downloadNo = "4"; // 다운로드 화면구분번호

            $.postJSON('/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadCntChk.sb', data, function (response) {
                if (response.data.list === 0) {
                } else {
                    var msgCntChk = response.data.list; // 00:0명의 사용자 다운로드 중
                    if(msgCntChk.substr(0, 2) === "00") {
                        $scope.searchExcelDivisionList(data);
                    } else {
                        // 엑셀다운로드 진행 사용자 저장 insert
                        var params2 = data;
                        params2.resrceNm = "실패:" + menuNm;
                        params2.downloadFileCount = 0; // 다운로드 파일수
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params2, function(response){

                        });

                        $scope._popMsg(msgCntChk); // 다운로드 사용량이 초과되어 대기중입니다. 잠시 후 다시 진행하여 주십시오.

                    }
                }
            });
        }
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/prod/monthProdStoreBenson/monthProdStoreBenson/getMonthProdStoreBensonExcelList.sb", params, function(response) {
            var grid = $scope.excelFlex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            if ($scope.excelFlex.rows.length <= 0) {
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

                // 상품분류합산조회
                if(params.chkProdClassSumDisplay === true) {
                    if(columns[j].binding === 'mClassCd' || columns[j].binding === 'mClassNm' || columns[j].binding === 'sClassCd' || columns[j].binding === 'sClassNm'
                        || columns[j].binding === 'prodCd' || columns[j].binding === 'sideProdCd' || columns[j].binding === 'selTypeFg' || columns[j].binding === 'sideProdNm') {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "월별상품매출현황(매장별)_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

    // 분할 엑셀 리스트 조회
    $scope.searchExcelDivisionList = function (data) {

        var params = {};
        var params = data;
        // 다운로드 시작이면 작업내역 로딩 팝업 오픈
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        // 전체 데이터 수
        var listSize = 0;
        // 다운로드 되는 총 엑셀파일 수
        var totFileCnt = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;
        $.postJSON( "/sale/prod/monthProdStoreBenson/monthProdStoreBenson/getMonthProdStoreBensonList.sb", params, function(response){

            listSize = response.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/35000); // 하나의 엑셀파일에 35000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            }

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = totFileCnt; // 다운로드 파일수
            $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params, function(response){
                var seq = response.data.list; // 순번

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        //setTimeout(function() {
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 35000개씩 지정해 분할 다운로드 진행
                        params.limit = 35000 * (x + 1);
                        params.offset = (35000 * (x + 1)) - 34999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // 엑셀다운로드 진행 사용자 저장 update
                        params.seq = seq;
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            // ajax 통신 설정
                            $.postJSON("/sale/prod/monthProdStoreBenson/monthProdStoreBenson/getMonthProdStoreBensonList.sb", params, function(response) {

                                    // <-- 그리드 visible -->
                                    // 선택한 테이블에 따른 리스트 항목 visible
                                    var grid = wijmo.Control.getControl("#wjGridExcelList");
                                    var columns = grid.columns;

                                    if(response.status === "FAIL") {
                                        s_alert.pop(response.message);
                                        grid.itemsSource = new wijmo.collections.CollectionView([]);
                                        return;
                                    }

                                    grid.itemsSource = response.data.list;
                                    grid.itemsSource.trackChanges = true;

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

                                        // 상품분류합산조회
                                        if(params.chkProdClassSumDisplay === true) {
                                            if(columns[j].binding === 'mClassCd' || columns[j].binding === 'mClassNm' || columns[j].binding === 'sClassCd' || columns[j].binding === 'sClassNm'
                                                || columns[j].binding === 'prodCd' || columns[j].binding === 'sideProdCd' || columns[j].binding === 'selTypeFg' || columns[j].binding === 'sideProdNm') {
                                                columns[j].visible = false;
                                            }
                                        }
                                    }
                                    // <-- //그리드 visible -->

                                    wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                        includeColumnHeaders: true,
                                        includeCellStyles: false,
                                        includeColumns: function (column) {
                                            return column.visible;
                                        }
                                    }, "월별상품매출현황(매장별)_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime() + '_' + (x + 1) +'.xlsx', function () {
                                        $timeout(function () {
                                            console.log("Export complete start. _" + (x + 1));
                                            getExcelFile(x + 1);
                                        }, 500);
                                    }, function (reason) { // onError
                                        // User can catch the failure reason in this callback.
                                        console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                        $scope.excelUploadingPopup(false);
                                    });

                                },
                                function(result){
                                    s_alert.pop(result.message);
                                }
                            );
                        });
                        resolve(x);

                    });
                    //}, 3000*x);
                }

                async function getExcelFile(x) {
                    if(totFileCnt > x){
                        await delay(x);
                    }else{
                        $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                    }
                }

                // 엑셀 분할 다운로드 시작
                getExcelFile(0);

            });
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 다운로드 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);
