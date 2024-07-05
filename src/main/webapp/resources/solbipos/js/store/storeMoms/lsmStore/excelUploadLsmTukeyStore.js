/****************************************************************
 *
 * 파일명 : excelUploadLsmTukeyStore.js
 * 설  명 : LSM사용매장조회 > 터치키탭 엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  엑셀업로드 그리드 생성
 */
app.controller('excelUploadLsmTukeyStoreCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('excelUploadLsmTukeyStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {

        if ($('#lsmTukeyStoreExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.excelTextFg = 'excel'; // 업로드 progress 관련 기본값 세팅
        $scope.stepCnt = 1; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#lsmTukeyStoreExcelUpFile')[0].files[0]) {
            var file = $('#lsmTukeyStoreExcelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#lsmTukeyStoreExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function (workbook) {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#lsmTukeyStoreExcelUpFile").val('');
                $scope._popMsg(messages['dlvrProdMulti.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item = {};
        var vProdCd = "";
        var rowLength = $scope.flex.rows.length;

        if (rowLength === 0) {
            $scope._popMsg(messages['dlvrProdMulti.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';

                    item[colBinding] = cellValue.replaceAll('\'', '');
                }
            }
            jsonData.push(item);
        }

        $timeout(function () {
            // 데이터 임시 저장
            $scope.tukeyTempInsert(jsonData);
        }, 10);
    };

    // 데이터 임시 저장
    $scope.tukeyTempInsert = function (jsonData) {

        $scope.totalRows = jsonData.length;
        var params = [];
        if ($scope.progressCnt === 0) {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
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
            url    : '/store/storeMoms/lsmStore/lsmStore/getTukeyTempInsert.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false);
                    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                    // 엑셀 업로드 데이터 중 미사용/단종 상품 유무 확인
                    $scope.getTukeyChkUseYn();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false);
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
                $scope.tukeyTempInsert(jsonData);
            }
        });
    };

    // 엑셀 업로드 데이터 중 미사용/단종 상품 유무 확인
    $scope.getTukeyChkUseYn = function(){
        // 파라미터
        var params = {};

        // 엑셀 업로드 데이터 중 미사용/단종 상품 유무 확인
        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/lsmStore/lsmStore/getTukeyChkUseYn.sb", params, function(response){
            if (response.data.data.list === 0 || response.data.data.list === null) {
            } else {
                var popMsg = response.data.data.list; //
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                $scope._popMsg(messages["lsmStore.excelUpload.useYnMsg"] +popMsg); // 단종/미사용 상품이 존재합니다.
                return false;
            }

            // 상품 수 확인
            $scope.getTukeyChkProdCnt();
        });
    };

    // 엑셀 업로드 데이터 중 분류별 상품수 확인
    $scope.getTukeyChkProdCnt = function(){
        // 파라미터
        var params = {};

        // 엑셀 업로드 데이터 중 분류별 상품수 확인
        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/lsmStore/lsmStore/getTukeyChkProdCnt.sb", params, function(response){
            if (response.data.data.list === 0 || response.data.data.list === null) {
            } else {
                var popMsg = response.data.data.list; //
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                $scope._popMsg(popMsg + messages["lsmStore.excelUpload.prodCntOver"]); // 초과 메세지
                return false;
            }

            // 터치키 분류코드 전체 DELETE
            $scope.deleteTukey();
        });
    };

    // 터치키 분류코드 전체 DELETE
    $scope.deleteTukey = function(){
        // 파라미터
        var params = {};

        // 터치키 분류코드 전체 DELETE
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/lsmStore/lsmStore/getDeleteTukey.sb", params, function(){

            // 데이터 저장
            $scope.getInsertTukey();
        });
    };

    // 엑셀 업로드 데이터 중 LSM '사용'인 데이터 수 조회
    // $scope.getTukeyCnt = function(){
    //     // 파라미터
    //     var params = {};
    //
    //     // 엑셀 업로드 데이터 중 LSM '사용'인 데이터 수 조회
    //     $scope._postJSONSave.withOutPopUp("/store/storeMoms/lsmStore/lsmStore/getTukeyCnt.sb", params, function(response){
    //         var result = response.data.data;
    //
    //         // 데이터 저장
    //         $scope.getInsertTukey(result);
    //     });
    // };

    // 데이터 저장
    $scope.getInsertTukey = function () {

        //가상로그인 session 설정
        var params = {};
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/store/storeMoms/lsmStore/lsmStore/getInsertTukey.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {

                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기

                $scope._popMsg(messages['lsmStore.excelUploadMsg'] + messages['lsmStore.excelUpload.succMsg']);
                var parentScope = agrid.getScope('lsmStoreCtrl');
                parentScope.uploadCallBack();

            }
        }, function errorCallback(response) {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        })
    };

    // 엑셀업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['excelUpload.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span> / <span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
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