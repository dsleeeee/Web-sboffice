/****************************************************************
 *
 * 파일명 : funcKey.js
 * 설  명 : 기능키적용버전등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var keyVersion = [
    {"name":"NX1","value":"NX1"},
    {"name":"NX2","value":"NX2"},
    {"name":"공용","value":"공용"}
];

/**
 * 기능키적용버전 조회 그리드 생성
 */
app.controller('funcKeyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('funcKeyCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("fnkeyFg", fnkeyFgData); // 기능구분


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.versionDataMap = new wijmo.grid.DataMap(keyVersion, 'value', 'name');

        //기능구분 기본값 '결제메뉴(좌)'로 설정
        $scope.srchFnkeyFgCombo.selectedValue = '6020';

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "fnkeyNoVersion") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

    };

    $scope.$on("funcKeyCtrl", function (event, data) {
        // 조회
        $scope.getFuncKeyList();
        event.preventDefault();
    });

    // 그리드 입력값 변경시, 자동체크
    $scope.checked = function(item){
        item.gChk = true;
    };

    // 조회
    $scope.getFuncKeyList = function () {

        // 파라미터
        var params = {};
        params.fnkeyFg = $scope.srchFnkeyFgCombo.selectedValue;
        params.fnkeyNo = $("#srchFnkeyNo").val();
        params.fnkeyNm = $("#srchFnkeyNm").val();

        // 기능키적용버전 리스트 조회
        $scope._inquiryMain("/sys/admin/funcKey/getFuncKeyList.sb", params);

    };

    //저장
    $scope.saveRow = function(){
        // 파라미터 설정
        var params = [];

        // 조건수량이 수정된 내역이 있는지 체크
        if ($scope.flex.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
        }

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("저장할 " + messages["funcKey.product"] + "의 체크박스" + messages["eventMessage.chk.item"]); // 저장할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

            var item = $scope.flex.collectionView.itemsEdited[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.fnkeyNo = item.fnkeyNo;
                obj.fnkeyNoVersion = item.fnkeyNoVersion;

                if(item.fnkeyNoVersion == null || item.fnkeyNoVersion == ""){
                    $scope._popMsg("(["+item.fnkeyFg+"]" + item.nmcodeNm + " - [" + item.fnkeyNo + "] " + item.fnkeyNm +
                        " " + messages["funcKey.version"] + messages["cmm.require.text"]); // 적용버전을(를) 입력하세요.
                    return false;
                }
                params.push(obj);
            }
        }

        //기능키 적용버전 수정 및 저장
        $scope._save("/sys/admin/funcKey/saveFuncKey.sb", params, function () {
            $scope.getFuncKeyList()
        });

    };

    //삭제
    $scope.deleteRow = function(){

        var params = [];

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["eventMessage.product"] + "의 체크박스" + messages["eventMessage.chk.item"]); // 삭제할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

            var item = $scope.flex.collectionView.itemsEdited[i];

            if(item.gChk === true) {
                var obj = {};
                obj.fnkeyNo = item.fnkeyNo;

                params.push(obj);
            }
        }

        $.postJSONArray("/sys/admin/funcKey/deleteFuncKey.sb", params, function (result) {
            if (result.status === "OK") {
                // $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 혜택상품 목록 재조회
                $scope.getFuncKeyList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });

    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    //  column.visible;
                    return column.binding != 'gChk';
                }
            }, messages["funcKey.funcKey"]  + '_' +  getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 양식다운로드
    $scope.sampleDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var vScope = agrid.getScope('funcKeyExcelDownCtrl');

        // 파라미터
        var params = {};
        params.fnkeyFg = $scope.srchFnkeyFgCombo.selectedValue;
        params.fnkeyNo = $("#srchFnkeyNo").val();
        params.fnkeyNm = $("#srchFnkeyNm").val();

        vScope.sampleDownload(params);
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["funcKey.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#funcKeyExcelUpFile").val('');
            $("#funcKeyExcelUpFile").trigger('click');

        });
    };

}]);

/**
 * 기능키적용버전등록 양식다운로드 그리드 생성
 */
app.controller('funcKeyExcelDownCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('funcKeyExcelDownCtrl', $scope, $http, false));

    // 그리드 링크 효과
    $scope.initGrid = function (s, e) {

    };

    // 양식 다운로드
    $scope.sampleDownload = function (data) {

        var params = {};
        params.fnkeyFg = data.fnkeyFg;
        params.fnkeyNo = data.fnkeyNo;
        params.fnkeyNm = data.fnkeyNm;

        $scope._inquiryMain("/sys/admin/funcKey/getFuncKeyList.sb", params, function (){

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	true,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    messages["funcKey.funcKey"] + '_엑셀업로드_양식_' + getCurDateTime() + '.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });
    };

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#funcKeyExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

}]);

/**
 * 기능키적용버전등록 엑셀업로드 그리드 생성
 */
app.controller('funcKeyExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('funcKeyExcelUploadCtrl', $scope, $http, false));

    //
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
        if ($('#funcKeyExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#funcKeyExcelUpFile')[0].files[0]) {
            var file = $('#funcKeyExcelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#funcKeyExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#funcKeyExcelUpFile").val('');
                $scope._popMsg(messages['funcKey.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.flex.rows.length;

        if (rowLength === 0) {
            $scope._popMsg(messages['funcKey.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue;
                }
            }

            jsonData.push(item);
        }
        $timeout(function () {
            setTimeout(function() {
                // 저장 전 입력값 체크
                $scope.saveRow(jsonData);
            }, 500);

        }, 10);
    };

    // 저장 전 입력값 체크
    $scope.saveRow = function (jsonData) {
        var jsonDataEx =[];

        for (var i = 0; i < jsonData.length; i++) {
            var item = jsonData[i];

            //적용버전 입력된 값만 추가
            if (nvl(item.fnkeyNoVersion, '').toString() === '') {
                // $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // $scope._popMsg(messages["funcKey.version"] + " " + messages["cmm.require.text"]);  // 적용버전을 입력하세요
                // return false;
            }else{
                if(nvl(item.fnkeyNoVersion, '').toString() == 'NX1' || nvl(item.fnkeyNoVersion, '').toString() == 'NX2' || nvl(item.fnkeyNoVersion, '').toString() == '공용' ){
                    jsonDataEx.push(item);
                }
            }
        }

        // 데이터 저장
        $timeout(function () {
            $scope.saveData(jsonDataEx);
        }, 10);
    };


    // 데이터 저장
    $scope.saveData = function (jsonData) {
        $scope.totalRows = jsonData.length;
        var params = [];

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
            url    : '/sys/admin/funcKey/saveFuncKey.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('funcKeyCtrl').getFuncKeyList();
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
                $scope.saveData(jsonData);
            }
        });

    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 적용 중...</div>';
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