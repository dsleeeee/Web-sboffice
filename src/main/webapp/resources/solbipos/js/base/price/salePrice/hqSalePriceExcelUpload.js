/****************************************************************
 *
 * 파일명 : hqSalePriceExcelUpload.js
 * 설  명 : 본사판매가관리 엑셀업로드 JavaScript
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
 *  본사판매가관리 엑셀업로드 샘플양식 조회 그리드 생성
 */
app.controller('hqSalePriceExcelUploadSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceExcelUploadSampleCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("hqSalePriceExcelUploadSampleCtrl", function(event, data) {
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
        $("#hqSalePriceExcelUpFile").val('');
        $("#hqSalePriceExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);


/**
 *  본사판매가관리 엑셀업로드 조회 그리드 생성
 */
app.controller('hqSalePriceExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceExcelUploadCtrl', $scope, $http, false));

    // 전매장적용
    $scope.applyFg = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
    $scope.$on("hqSalePriceExcelUploadCtrl", function(event, data) {
        $scope.searchHqSalePriceExcelUpload();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchHqSalePriceExcelUpload = function() {
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
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
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
            $scope.searchHqSalePriceExcelUpload();

            // 검증을 통과한 판매가를 저장하시겠습니까?
            var msg = messages["hqSalePriceExcelUpload.saveConfirm"];
            // 전매장적용
            if ($scope.applyFg) {
                // 전체 매장에, 검증을 통과한 판매가를 저장하시겠습니까?
                msg = messages["hqSalePriceExcelUpload.saveConfirm2"];
            }
            $scope._popConfirm(msg, function() {
                // 판매가 저장
                $scope.salePriceExcelUploadSave();
            });
        });
    };

    // 판매가 저장
    $scope.salePriceExcelUploadSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].applyFg = $scope.applyFg;
            $scope.flex.collectionView.items[i].salePriceOrgnFg = "H";
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/base/price/salePrice/salePriceExcelUpload/getHqSalePriceExcelUploadSave.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증결과 조회
            $scope.searchHqSalePriceExcelUpload();
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