/****************************************************************
 *
 * 파일명 : dailyTableKwu.js
 * 설  명 : 일일일계표2 (광운대 아이스링크) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.05     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var arrDispSeq	 = new Array();		//[영업일보 구성] 정렬 순서 ([i][0]:cfgCd, [i][1]:cfgDispSeq, [i][2]:cfgSelYn)
arrDispSeq[0] = new Array('sl',1,'Y');
arrDispSeq[1] = new Array('prodClass',2,'Y');
arrDispSeq[2] = new Array('pay',3,'Y');
arrDispSeq[3] = new Array('rtn',4,'Y');

/**
 *  일일일계표 그리드 생성
 */
app.controller('dailyTableKwuCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableKwuCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableKwuCtrl", function(event, data) {
        $scope.searchDailyTableKwu();
        event.preventDefault();
    });

    $scope.searchDailyTableKwu = function(){
        if($scope.orgnFg != "S") {
            if( $("#dailyTableKwuSelectStoreCd").val() == "" ){
                s_alert.pop( messages["dailyReport.alert.selectStore"] );	//선택된 매장이 없습니다. 매장을 선택해 주십시오.
                return;
            }
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.storeCd = $("#dailyTableKwuSelectStoreCd").val();

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

        $scope._postJSONQuery.withOutPopUp ( "/excclc/excclc/dailyTableKwu/dailyTableKwu/getDailyTableKwuList.sb",	//영업일보 구성 조회
            params,
            function(response)	{
                //데이터 setting > 영업일보
                var dailyTableKwuCtrl_sl       	= agrid.getScope("dailyTableKwuCtrl_sl");      	dailyTableKwuCtrl_sl.flex.itemsSource		 = response.data.data.sl;        // 매출종합
                var dailyTableKwuCtrl_prodClass	= agrid.getScope("dailyTableKwuCtrl_prodClass");	dailyTableKwuCtrl_prodClass.flex.itemsSource = response.data.data.prodClass; // 분류
                var dailyTableKwuCtrl_pay      	= agrid.getScope("dailyTableKwuCtrl_pay");     	dailyTableKwuCtrl_pay.flex.itemsSource		 = response.data.data.pay;       // 결제수단
                var dailyTableKwuCtrl_rtn     	= agrid.getScope("dailyTableKwuCtrl_rtn");    	dailyTableKwuCtrl_rtn.flex.itemsSource		 = response.data.data.rtn;       // 반품출납

                $scope.$broadcast('loadingPopupInactive');	//'데이터 처리 중입니다.' 중지
            }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dailyTableKwuSelectStoreShow = function () {
        $scope._broadcast('dailyTableKwuSelectStoreCtrl');
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){
        var dailyTableKwuCtrl_sl = agrid.getScope("dailyTableKwuCtrl_sl"); // 매출종합
        if (dailyTableKwuCtrl_sl.flex.rows.length <= 0) {
            s_alert.pop( messages["excelUpload.not.downloadData"] );	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var dateFrom = wijmo.Globalize.format(startDate.value, 'yyyy.MM.dd');
        var excelFileName = '일일일계표(' + dateFrom + ')' + getCurDateTime() +'.xlsx';

        var dailyTableKwuCtrl = agrid.getScope("dailyTableKwuCtrl");
        dailyTableKwuCtrl.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.

        $timeout(function()	{
            // var dailyTableKwuCtrl_sl        = agrid.getScope("dailyTableKwuCtrl_sl");        // 매출종합
            var dailyTableKwuCtrl_prodClass = agrid.getScope("dailyTableKwuCtrl_prodClass"); // 분류
            var dailyTableKwuCtrl_pay       = agrid.getScope("dailyTableKwuCtrl_pay");       // 결제수단
            var dailyTableKwuCtrl_rtn       = agrid.getScope("dailyTableKwuCtrl_rtn");       // 반품출납

            var sl		   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableKwuCtrl_sl.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTableKwu.sl"]});              // 매출종합
            var prodClass = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableKwuCtrl_prodClass.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTableKwu.prodClass"]}); // 분류
            var pay 	   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableKwuCtrl_pay.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTableKwu.pay"]});             // 결제수단
            var rtn    	   = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableKwuCtrl_rtn.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTableKwu.rtn"]});             // 반품출납

            var firstSheetYN = "N";
            var firstSheet;

            for(var i=0; i<4; i++){
                if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "Y"){
                    eval( 'firstSheet.sheets.push('+ arrDispSeq[i][0] + '.sheets[0]);' );
                }
                if(arrDispSeq[i][2] == "Y"   &&   firstSheetYN == "N"){
                    firstSheetYN = "Y";
                    firstSheet 	 = eval('wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(dailyTableKwuCtrl_' + arrDispSeq[i][0] + '.flex, {includeColumnHeaders: true, includeCellStyles: true, sheetIndex: 0, sheetName: messages["dailyTableKwu.' + arrDispSeq[i][0] + '"]});');
                }
            }
            firstSheet.saveAsync(excelFileName);

            dailyTableKwuCtrl.$broadcast('loadingPopupInactive');

        }, 1000);	//건수가 많아서 1000으로 했음 (현재 1년치 정도가 500ms 미만임)
    };

    // 첫째장 인쇄
    $scope.print = function(printNum){
        if($scope.orgnFg != "S") {
            if( $("#dailyTableKwuSelectStoreCd").val() == "" ){
                s_alert.pop( messages["dailyReport.alert.selectStore"] );	//선택된 매장이 없습니다. 매장을 선택해 주십시오.
                return;
            }
        }

        var params = {};
        params.printNum = printNum;
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.storeCd = $("#dailyTableKwuSelectStoreCd").val();
        $scope.setSelectedDailyTableKwu(params);
        $scope.wjDailyTableReportKwuLayer.show(true);
    };

    // 선택
    $scope.selectedDailyTableKwu;
    $scope.setSelectedDailyTableKwu = function(store) {
        $scope.selectedDailyTableKwu = store;
    };
    $scope.getSelectedDailyTableKwu = function() {
        return $scope.selectedDailyTableKwu;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 일일일계표 인쇄 결과 팝업 핸들러 추가
        $scope.wjDailyTableReportKwuLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('dailyTableReportKwuCtrl', $scope.getSelectedDailyTableKwu());
            }, 50)
        });
    });
}]);


/**
 *  일일일계표 - 매출종합 그리드 생성
 */
app.controller('dailyTableKwuCtrl_sl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableKwuCtrl_sl', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableKwuCtrl_sl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);


/**
 *  일일일계표 - 분류 그리드 생성
 */
app.controller('dailyTableKwuCtrl_prodClass', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableKwuCtrl_prodClass', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableKwuCtrl_prodClass", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);


/**
 *  일일일계표 - 결제수단 그리드 생성
 */
app.controller('dailyTableKwuCtrl_pay', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableKwuCtrl_pay', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableKwuCtrl_pay", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);


/**
 *  일일일계표 - 반품출납 그리드 생성
 */
app.controller('dailyTableKwuCtrl_rtn', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableKwuCtrl_rtn', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableKwuCtrl_rtn", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);