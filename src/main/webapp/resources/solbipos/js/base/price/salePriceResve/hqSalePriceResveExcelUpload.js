/****************************************************************
 *
 * 파일명 : hqSalePriceResveExcelUpload.js
 * 설  명 : 가격예약(본사판매가) 엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  가격예약(본사판매가) 엑셀업로드 샘플양식 조회 그리드 생성
 */
app.controller('hqSalePriceResveExcelUploadSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceResveExcelUploadSampleCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("hqSalePriceResveExcelUploadSampleCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // <-- 양식다운로드 -->
    $scope.sampleDownload = function(){
        var params = {};
        params.salePriceOrgnFg = "H";
        params.listScale = 5000;

        $scope._inquiryMain("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadSampleList.sb", params, function (){
            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	false,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    '본사판매가_엑셀업로드_'+getCurDateTime()+'.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });
    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 엑셀업로드 팝업
        $("#hqSalePriceResveExcelUpFile").val('');
        $("#hqSalePriceResveExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);


/**
 *  가격예약(본사판매가) 엑셀업로드 조회 그리드 생성
 */
app.controller('hqSalePriceResveExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceResveExcelUploadCtrl', $scope, $http, false));

    // 전매장적용
    $scope.applyFg = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 예약일시 날짜셋팅
        $scope.excelUploadStartDateCombo.value = getTomorrow('-');
        $scope.excelUploadEndDateCombo.value = "9999-12-31";

        // 그리드 DataMap 설정
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 검증결과
                if (col.binding === "result") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (item[("result")] !== '검증전' && item[("result")] !== '검증성공') {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("hqSalePriceResveExcelUploadCtrl", function(event, data) {
        $scope.searchHqSalePriceResveExcelUpload();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchHqSalePriceResveExcelUpload = function() {
        var params = {};
        params.salePriceOrgnFg = "H";

        $scope._inquiryMain("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        // 전체삭제
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckDeleteAll.sb", params, function(){
            // 저장
            $scope.saveSave();
        });
    };

    // 저장
    $scope.saveSave = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            // <-- 검증 -->
            var result = "";
            $scope.flex.collectionView.items[i].result = result;
            $scope.flex.collectionView.items[i].salePriceOrgnFg = "H";

            params.push($scope.flex.collectionView.items[i]);
        }

        // 검증결과 저장
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckSaveAdd.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기

            // 검증결과 조회
            $scope.searchHqSalePriceResveExcelUpload();

            // 검증을 통과한 판매가를 저장하시겠습니까?
            var msg = messages["hqSalePriceResveExcelUpload.saveConfirm"];
            // 전매장적용
            if ($scope.applyFg) {
                // 전체 매장에, 검증을 통과한 판매가를 저장하시겠습니까?
                msg = messages["hqSalePriceResveExcelUpload.saveConfirm2"];
            }
            var startDate = wijmo.Globalize.format($scope.excelUploadStartDateCombo.value, 'yyyy-MM-dd');
            var endDate = wijmo.Globalize.format($scope.excelUploadEndDateCombo.value, 'yyyy-MM-dd');
            msg = msg + "<br/>(예약일시 : "  + startDate + " ~ " + endDate + ")";
            $scope._popConfirm(msg, function() {
                // 판매가 저장
                $scope.salePriceResveExcelUploadSave();
            });
        });
    };

    // 판매가 저장
    $scope.salePriceResveExcelUploadSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 예약날짜 체크
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
            month = "0" + month;
        }
        if(day.length == 1){
            day = "0" + day;
        }
        var now = year + "" + month + "" + day;
        var vStartDate = wijmo.Globalize.format($scope.excelUploadStartDateCombo.value, 'yyyyMMdd');
        var vEndDate = wijmo.Globalize.format($scope.excelUploadEndDateCombo.value, 'yyyyMMdd');

        if(Number(now) >= Number(vStartDate)) {
            $scope._popMsg(messages["hqSalePriceResveExcelUpload.startDate"] + "는 " + messages["hqSalePriceResveExcelUpload.resveDate.chk.msg"]);
            return false;
        }
        if(Number(now) >= Number(vEndDate)){
            $scope._popMsg(messages["hqSalePriceResveExcelUpload.endDate"] + "는 " + messages["hqSalePriceResveExcelUpload.resveDate.chk.msg"]);
            return false;
        }

        if(Number(vStartDate) > Number(vEndDate)){
            $scope._popMsg(messages["hqSalePriceResveExcelUpload.resveDate"] + messages["hqSalePriceResveExcelUpload.resveDate.chk.msg2"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].applyFg = $scope.applyFg;
            $scope.flex.collectionView.items[i].startDate = wijmo.Globalize.format($scope.excelUploadStartDateCombo.value, 'yyyyMMdd');
            $scope.flex.collectionView.items[i].endDate = wijmo.Globalize.format($scope.excelUploadEndDateCombo.value, 'yyyyMMdd');
            $scope.flex.collectionView.items[i].salePriceOrgnFg = "H";
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/base/price/salePriceResve/salePriceResveExcelUpload/getHqSalePriceResveExcelUploadSave.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증결과 조회
            $scope.searchHqSalePriceResveExcelUpload();
        });
    };

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                '본사판매가_엑셀업로드_'+getCurDateTime()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //양식다운로드 -->

    // <-- 그리드 행 삭제 -->
    $scope.delete = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 삭제
            $scope.deleteSave();
        });
    };

    $scope.deleteSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].salePriceOrgnFg = "H";
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckDelete.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };
    // <-- //그리드 행 삭제 -->

}]);