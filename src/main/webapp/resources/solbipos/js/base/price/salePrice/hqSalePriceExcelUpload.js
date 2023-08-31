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

// 저장 시 매장 적용 구분
var excelStoreSaveFg = [
    {"name":"전매장적용","value":"all"},
    {"name":"미적용","value":"none"},
    {"name":"전매장적용(제한매장포함)","value":"tot"},
    {"name":"매장선택","value":"choice"}
];

/**
 *  본사판매가관리 엑셀업로드 샘플양식 조회 그리드 생성
 */
app.controller('hqSalePriceExcelUploadSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceExcelUploadSampleCtrl', $scope, $http, false));

    // 일괄변경 체크
    $scope.saleUprcApply = true;
    $scope._setComboData("excelStoreSaveFg", excelStoreSaveFg);

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

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    $scope.checked(item);
                }
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    var vScope = agrid.getScope("hqSalePriceExcelUploadSampleCtrl");
                    if(vScope.saleUprcApply){
                        $scope.saleUprc(item);
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("hqSalePriceExcelUploadCtrl", function(event, data) {
        $scope.searchHqSalePriceExcelUpload();
        event.preventDefault();
    });

    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "choice") {
            $("#excelStoreSaveStore").show();
        }
        else {
            $("#excelStoreSaveStore").hide();
        }
    };

    // 검증결과 조회
    $scope.searchHqSalePriceExcelUpload = function() {
        var params = {};
        params.salePriceOrgnFg = "H";

        $scope._inquiryMain("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        if($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
            if ($("#excelChoiceSaveStoreCd").val() === "") {
                $scope._popMsg("매장을 선택해주세요");
                return false;
            }
        }

        // 전체삭제
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckDeleteAll.sb", params, function(){

            $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
            $scope.progressCnt = 0; // 처리된 숫자

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                // <-- 검증 -->
                var result = "";
                $scope.flex.collectionView.items[i].result = result;
                $scope.flex.collectionView.items[i].salePriceOrgnFg = "H";

                // 잘못된 판매단가값이 복사되어 내점/포장/배달 판매가에 들어가있으면(hidden 상태) 검증과정에서 걸린다.
                if(subPriceFg === "0"){
                    $scope.flex.collectionView.items[i].stinSaleUprc = "";
                    $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                    $scope.flex.collectionView.items[i].packSaleUprc = "";
                }

                $scope.flex.collectionView.items[i].applyFg = $scope.excelStoreSaveFgCombo.selectedValue;
                if($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
                    if($("#excelChoiceSaveStoreCd").val() === ""){
                        $scope._popMsg("매장을 선택해주세요");
                        return false;
                    }
                    $scope.flex.collectionView.items[i].saveStoreCds = $("#excelChoiceSaveStoreCd").val();
                }
                params.push($scope.flex.collectionView.items[i]);
            }

            // 저장
            $scope.saveSave(params);
        });
    };

    // 저장
    $scope.saveSave = function(jsonData) {

        $scope.totalRows = jsonData.length;
        var params = [];
        var msg = '';

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            item.progressCnt = $scope.progressCnt;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/price/salePrice/salePriceExcelUpload/getSalePriceExcelUploadCheckSaveAdd.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 검증결과 조회
                    $scope.searchHqSalePriceExcelUpload();

                    // 검증을 통과한 판매가를 저장하시겠습니까?
                    var msg = messages["hqSalePriceExcelUpload.saveConfirm"];
                    // 전매장적용
                    if ($scope.excelStoreSaveFgCombo.selectedValue !== 'none') {
                        // 전체 매장에, 검증을 통과한 판매가를 저장하시겠습니까?
                        msg = messages["hqSalePriceExcelUpload.saveConfirm2"];
                    }
                    $scope._popConfirm(msg, function() {
                        // 판매가 저장
                        $scope.salePriceExcelUploadSave();
                    });
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.saveSave(jsonData);
            }
        });
    };

    // 판매가 저장
    $scope.salePriceExcelUploadSave = function() {

        $scope.stepCnt = 100;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].applyFg =  $scope.excelStoreSaveFgCombo.selectedValue;
            if($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
                if($("#excelChoiceSaveStoreCd").val() === ""){
                    $scope._popMsg("매장을 선택해주세요");
                    return false;
                }
                $scope.flex.collectionView.items[i].saveStoreCds = $("#excelChoiceSaveStoreCd").val();
            }
            $scope.flex.collectionView.items[i].salePriceOrgnFg = "H";

            // 내점/배달/포장 가격관리 미사용일 때, 내점/포장/배달 판매가는 쿼리에서 판매단가로 대체하여 넣는다.
            if(subPriceFg === "0"){
                $scope.flex.collectionView.items[i].stinSaleUprc = "";
                $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                $scope.flex.collectionView.items[i].packSaleUprc = "";
            }

            params.push($scope.flex.collectionView.items[i]);
        }

        $timeout(function () {
            setTimeout(function () {
                // 저장
                $scope.save2(params);
            }, 500);
        }, 10);
    };

    // 저장
    $scope.save2 = function(orgParams) {

        $scope.totalRows = orgParams.length;    // 체크수
        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/price/salePrice/salePriceExcelUpload/getHqSalePriceExcelUploadSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 검증결과 조회
                    $scope.searchHqSalePriceExcelUpload();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.save2(orgParams);
            }
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

    // 판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

    // 일괄변경
    $scope.saleUprc = function (item){
        item.stinSaleUprc = item.saleUprc;
        item.dlvrSaleUprc = item.saleUprc;
        item.packSaleUprc = item.saleUprc;
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.excelChoiceSaveStoreShow = function () {
        $scope._broadcast('excelChoiceSaveStoreCtrl');
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
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