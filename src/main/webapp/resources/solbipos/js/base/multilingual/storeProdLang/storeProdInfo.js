/****************************************************************
 *
 * 파일명 : storeProdInfo.js
 * 설  명 : 다국어관리(상품)(매장) - 상품설명 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 상품설명 조회 그리드 생성
 */
app.controller('storeProdInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdInfoCtrl', $scope, $http, true));

    $scope._setComboData("useYn2", useYn); // 사용여부 콤보박스 set

    //
    $scope.initGrid = function (s, e) {

        var contentColumn = s.columns.getColumn("prodInfo");
        contentColumn.multiLine = true;
        contentColumn.wordWrap = true;

        var contentEnColumn = s.columns.getColumn("prodEnInfo");
        contentEnColumn.multiLine = true;
        contentEnColumn.wordWrap = true;

        var contentCnColumn = s.columns.getColumn("prodCnInfo");
        contentCnColumn.multiLine = true;
        contentCnColumn.wordWrap = true;

        var contentJpColumn = s.columns.getColumn("prodJpInfo");
        contentJpColumn.multiLine = true;
        contentJpColumn.wordWrap = true;

        // 상품코드, 상품설명 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            var dataItem = s.rows[e.row].dataItem;
            if (col.binding === "prodCd" || col.binding === "prodInfo") {
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    e.cancel = true;
                }
            }
        });

        // alt + Enter 이벤트. 예제 필드에만.
        s.addEventListener(s.hostElement, 'keydown', function(e) {
            var rowNum = s.selection.row;
            var colNum = s.selection.col;
            var col = s.columns[colNum];
            if (col.binding === "prodInfo" || col.binding === "prodEnInfo" || col.binding === "prodCnInfo" || col.binding === "prodJpInfo") {
                if (e.altKey) {
                    if (e.keyCode === wijmo.Key.Enter) {
                        s.finishEditing(false);
                        setTimeout(function () {
                            s.startEditing(true, rowNum, colNum, true);
                        },10);
                    }
                }
            }
        });

        // cell 에디트 종료시 42글자 포멧팅. 예제 필드에만.
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if(col.binding === "prodInfo" || col.binding === "prodEnInfo" || col.binding === "prodCnInfo" || col.binding === "prodJpInfo") {

                    // 값 변경시 체크박스 체크
                    $scope.checked(item);

                    var val = s.getCellData(e.row, e.col);
                    var lines = val.split("\n");
                    if ( lines != null ) {
                        var newValues = new Array();
                        var newLine = 0;
                        var splitStr = "";
                        for (var i = 0; i < lines.length; i++) {
                            if (lines[i].getByteLength() <= 42) {
                                newValues[newLine++] = lines[i];
                            } else {
                                splitStr = lines[i].splitByteLen(42);
                                for (var n = 0; n < splitStr.length; n++) {
                                    newValues[newLine++] = splitStr[n];
                                }
                            }
                        }
                        val = newValues.join("\n");
                        s.setCellData(e.row, e.col, val);
                        s.rows[e.row].height = 20 + ( newValues.length * 14 );
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 그리드 Row 사이즈 정렬
    $scope.autoSizeVisibleRows = function(flex) {
        for ( var r = 0; r < flex.itemsSource.itemCount; r++ ) {
            if((flex.rows[r]._data.prodInfo && flex.rows[r]._data.prodInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodEnInfo && flex.rows[r]._data.prodEnInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodCnInfo && flex.rows[r]._data.prodCnInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodJpInfo && flex.rows[r]._data.prodJpInfo.split("\n").length > 1)){
                flex.autoSizeRow(r, false);
            }
        }
    };

    $scope.$on("storeProdInfoCtrl", function (event, data) {
        // 조회
        $scope.getProdInfoList();
        event.preventDefault();
    });

    // 조회
    $scope.getProdInfoList = function () {

        // 파라미터
        var params = {};
        params.prodCd = $("#srchProdCd2").val();
        params.prodNm = $("#srchProdNm2").val();
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.srchUseYn2Combo.selectedValue;

        // 상품설명 탭 리스트 조회
        $scope._inquiryMain("/base/multilingual/storeProdLang/getStoreProdInfoList.sb", params, function() {
            $scope.autoSizeVisibleRows($scope.flex);
        });
    };

    // 저장
    $scope.saveRow = function(){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            for (var i = 0; i < params.length; i++) {
                var item = params[i];

                if (nvl(item.prodEnInfo + '', '').getByteLengthForOracle() > 2000) {
                    $scope._popMsg(messages["storeProdLang.en"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 영문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }

                if (nvl(item.prodCnInfo + '', '').getByteLengthForOracle() > 2000) {
                    $scope._popMsg(messages["storeProdLang.cn"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 중문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }

                if (nvl(item.prodJpInfo + '', '').getByteLengthForOracle() > 2000) {
                    $scope._popMsg(messages["storeProdLang.jp"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 일문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }
            }

            $scope._save("/base/multilingual/storeProdLang/saveStoreProdInfo.sb", params, function(result) {
                // 재조회
                $scope.getProdInfoList();
            });
        });
    };

    // 그리드 입력값 변경시, 자동체크
    $scope.checked = function(item){
        item.gChk = true;
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
                        return column.visible;
                    }
                }, messages["storeProdLang.prodInfo"]  + '_' +  getCurDateTime() + '.xlsx', function () {
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

        var vScope = agrid.getScope('storeProdInfoExcelCtrl');

        // 파라미터
        var params = {};
        params.prodCd = $("#srchProdCd2").val();
        params.prodNm = $("#srchProdNm2").val();
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.srchUseYn2Combo.selectedValue;

        vScope.sampleDownload(params);
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["storeProdLang.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#prodInfoExcelUpFile").val('');
            $("#prodInfoExcelUpFile").trigger('click');

        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope          = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd    = scope.getSelectedClass();
                var params         = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd   = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

}]);

/**
 * 상품설명 양식다운로드 그리드 생성
 */
app.controller('storeProdInfoExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdInfoExcelCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

        var contentColumn = s.columns.getColumn("prodInfo");
        contentColumn.multiLine = true;
        contentColumn.wordWrap = true;

        var contentEnColumn = s.columns.getColumn("prodEnInfo");
        contentEnColumn.multiLine = true;
        contentEnColumn.wordWrap = true;

        var contentCnColumn = s.columns.getColumn("prodCnInfo");
        contentCnColumn.multiLine = true;
        contentCnColumn.wordWrap = true;

        var contentJpColumn = s.columns.getColumn("prodJpInfo");
        contentJpColumn.multiLine = true;
        contentJpColumn.wordWrap = true;
    };

    // 그리드 Row 사이즈 정렬
    /*$scope.autoSizeVisibleRows = function(flex) {
        for ( var r = 0; r < flex.itemsSource.itemCount; r++ ) {
            if((flex.rows[r]._data.prodInfo && flex.rows[r]._data.prodInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodEnInfo && flex.rows[r]._data.prodEnInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodCnInfo && flex.rows[r]._data.prodCnInfo.split("\n").length > 1) ||
               (flex.rows[r]._data.prodJpInfo && flex.rows[r]._data.prodJpInfo.split("\n").length > 1)){
                flex.autoSizeRow(r, false);
            }
        }
    };*/

    // 양식 다운로드
    $scope.sampleDownload = function (data) {

        var params = {};
        params.prodCd = data.prodCd;
        params.prodNm = data.prodNm;
        params.prodClassCd = data.prodClassCd;
        params.useYn = data.useYn;

        $scope._inquiryMain("/base/multilingual/storeProdLang/getStoreProdInfoList.sb", params, function (){

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            //$scope.autoSizeVisibleRows($scope.flex);

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
                    messages["storeProdLang.prodInfo"] + '_엑셀업로드_양식_' + getCurDateTime() + '.xlsx',
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
        if ($('#prodInfoExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#prodInfoExcelUpFile')[0].files[0]) {
            var file = $('#prodInfoExcelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                /*$timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#prodInfoExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);*/

                // excel file read
                var reader = new FileReader();
                var arr = [];
                reader.onload = function(){
                    var fileData = reader.result;
                    var wb = XLSX.read(fileData, {type : 'binary'});
                    wb.SheetNames.forEach(function(sheetName) {
                        arr = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);

                        // key명 변경
                        arr.forEach(function(item){
                            renameKey(item, '상품분류코드', 'prodClassCd');
                            renameKey(item, '상품분류명', 'prodClassNm');
                            renameKey(item, '상품코드', 'prodCd');
                            renameKey(item, '상품명', 'prodNm');
                            renameKey(item, '상품설명', 'prodInfo');
                            renameKey(item, '상품설명(영문)', 'prodEnInfo');
                            renameKey(item, '상품설명(중문)', 'prodCnInfo');
                            renameKey(item, '상품설명(일문)', 'prodJpInfo');
                        });

                        // 숫자만 입력시, script 오류 발생하기 때문에 toString() 변환
                        arr.forEach(function(item){
                            if (item.prodEnInfo !== null && item.prodEnInfo !== undefined && item.prodEnInfo !== "") {
                                item.prodEnInfo = item.prodEnInfo.toString();
                            }

                            if (item.prodCnInfo !== null && item.prodCnInfo !== undefined && item.prodCnInfo !== "") {
                                item.prodCnInfo = item.prodCnInfo.toString();
                            }

                            if (item.prodJpInfo !== null && item.prodJpInfo !== undefined && item.prodJpInfo !== "") {
                                item.prodJpInfo = item.prodJpInfo.toString();
                            }
                        });
                        console.log(arr);
                    })
                };
                reader.readAsBinaryString(file);

                $timeout(function () {
                    setTimeout(function() {
                        // 저장 전 입력값 체크
                        $scope.saveRow(arr);
                    }, 500);

                }, 10);

            } else {
                $("#prodInfoExcelUpFile").val('');
                $scope._popMsg(messages['storeProdLang.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['storeProdLang.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
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
        $scope.totalRows = jsonData.length;

        for (var i = 0; i < $scope.totalRows; i++) {
            var item = jsonData[i];

            if (nvl(item.prodEnInfo + '', '').getByteLengthForOracle() > 2000) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeProdLang.en"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 영문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.prodCnInfo + '', '').getByteLengthForOracle() > 2000) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeProdLang.cn"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 중문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.prodJpInfo + '', '').getByteLengthForOracle() > 2000) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeProdLang.jp"] + " " + messages["storeProdLang.valueSize.chk.msg"]);  // 일문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }
        }

        // 데이터 저장
        $timeout(function () {
            $scope.saveData(jsonData);
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
            url    : '/base/multilingual/storeProdLang/saveStoreProdInfo.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('storeProdInfoCtrl').getProdInfoList();
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