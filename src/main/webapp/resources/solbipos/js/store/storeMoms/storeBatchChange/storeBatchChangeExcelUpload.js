/****************************************************************
 *
 * 파일명 : storeBatchChangeExcelUpload.js
 * 설  명 : 매장정보엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.18     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장목록 샘플양식 조회 그리드 생성
 */
app.controller('storeBatchChangeExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeBatchChangeExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 지사
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입
    };

    // <-- 검색 호출 -->
    $scope.$on("storeBatchChangeExcelUploadCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // <-- 양식다운로드 -->
    $scope.sampleDownload = function(){

        var params = {};
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
            if(momsHqBrandCdComboList[i].value !== null) {
                momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
            }
        }
        params.userBrands = momsHqBrandCd;
        params.listScale = 5000;

        $scope._inquiryMain("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreList.sb", params, function (){
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
                    '매장엑셀업로드_'+getCurDateTime()+'.xlsx',
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
        $("#storeExcelUpFile").val('');
        $("#storeExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);


/**
 *  매장목록 조회 그리드 생성
 */
app.controller('storeExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 지사
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입

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
    $scope.$on("storeExcelUploadCtrl", function(event, data) {
        $scope.searchStoreExcelUploadProd();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchStoreExcelUploadProd = function() {
        var params = {};

        $scope._inquiryMain("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        // 검증성공 이 아닌 데이터가 1개라도 있으면 저장 안함
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].result !== "검증성공") {
                $scope._popMsg(messages["storeBatchChange.noSaveConfirm"] + (i+1) + messages["storeBatchChange.noSaveConfirm2"]); // 검증성공이 아닌 데이터가 있습니다. <br/> 검증실패 항목은 수정 또는 삭제 후 진행해주세요. <br/> 3번째 줄
                return false;
            }
        }

        // 그리드가 수정되면 저장 안함
        if($scope.flex.collectionView.itemsEdited.length > 0) {
            $scope._popMsg(messages["storeBatchChange.noSaveConfirm3"]); // 수정된 내역이 있습니다. <br/> 엑셀다운로드 후, 다시 엑셀업로드 해주세요.
            return false;
        }

        // 검증을 통과한 매장정보를 저장하시겠습니까?
        $scope._popConfirm(messages["storeBatchChange.saveConfirm"], function() {
            // 매장등록 저장
            $scope.storeExcelUploadSave();
        });
    };

    // 매장등록 저장
    $scope.storeExcelUploadSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/store/storeMoms/storeBatchChange/storeBatchChange/getSimpleSave.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증결과 조회
            $scope.searchStoreExcelUploadProd();
        });
    };

    // <-- 엑셀다운로드 -->
    $scope.storeExcelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
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
                '매장정보일괄변경_엑셀다운로드_'+getCurDateTime()+'.xlsx',
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
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDelete.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };
    // <-- //그리드 행 삭제 -->

}]);