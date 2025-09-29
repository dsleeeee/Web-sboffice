/****************************************************************
 *
 * 파일명 : storeSideSdselGrp.js
 * 설  명 : 다국어관리(키오스크/사이드/옵션)(매장) - 사이드(선택그룹명) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 세트구분
var sdselTypeFgDataMap = new wijmo.grid.DataMap([
    {id: "C", name: "세트"},
    {id: "S", name: "싱글세트"}
], 'id', 'name');

// 세트구분
var sdselTypeFgData = [
    {"name":"전체","value":""},
    {"name":"세트","value":"C"},
    {"name":"싱글세트","value":"S"}
];

/**
 * 사이드(선택그룹명) 조회 그리드 생성
 */
app.controller('storeSideSdselGrpCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSideSdselGrpCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("sdselTypeFg", sdselTypeFgData); // 세트구분

    //
    $scope.initGrid = function (s, e) {
        $scope.sdselTypeFgDataMap = sdselTypeFgDataMap;

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "sdselGrpEnNm" || col.binding === "sdselGrpCnNm" || col.binding === "sdselGrpJpNm") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("storeSideSdselGrpCtrl", function (event, data) {
        $scope.getSideSdselGrpList();
        event.preventDefault();
    });

    // 조회
    $scope.getSideSdselGrpList = function () {

        // 파라미터
        var params = {};
        params.sdselTypeFg = $scope.srchSdselTypeFgCombo.selectedValue;
        params.sdselGrpCd = $("#srchSdselGrpCd").val();
        params.sdselGrpNm = $("#srchSdselGrpNm").val();

        // 사이드(선택그룹명) 탭 리스트 조회
        $scope._inquiryMain("/base/multilingual/storeKioskSideOption/getStoreSideSdselGrpList.sb", params);
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

                if (nvl(item.sdselGrpEnNm + '', '').getByteLengthForOracle() > 50) {
                    $scope._popMsg(messages["storeKioskSideOption.en"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 영문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }

                if (nvl(item.sdselGrpCnNm + '', '').getByteLengthForOracle() > 50) {
                    $scope._popMsg(messages["storeKioskSideOption.cn"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 중문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }

                if (nvl(item.sdselGrpJpNm + '', '').getByteLengthForOracle() > 50) {
                    $scope._popMsg(messages["storeKioskSideOption.jp"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 일문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                    return false;
                }
            }

            $scope._save("/base/multilingual/storeKioskSideOption/saveStoreSideSdselGrp.sb", params, function(result) {
                // 재조회
                $scope.getSideSdselGrpList();
            });
        });
    };

    // 그리드 입력값 변경시, 자동체크
    $scope.checked = function (item) {
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
                        //return column.visible;
                        return column.binding != 'gChk'; //선택
                    }
                }, messages["storeKioskSideOption.sideSdselGrp"]  + '_' +  getCurDateTime() + '.xlsx', function () {
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

        var vScope = agrid.getScope('storeSideSdselGrpExcelCtrl');

        // 파라미터
        var params = {};
        params.sdselTypeFg = $scope.srchSdselTypeFgCombo.selectedValue;
        params.sdselGrpCd = $("#srchSdselGrpCd").val();
        params.sdselGrpNm = $("#srchSdselGrpNm").val();

        vScope.sampleDownload(params);
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["storeKioskSideOption.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#grpExcelUpFile").val('');
            $("#grpExcelUpFile").trigger('click');

        });
    };

}]);

/**
 * 사이드(선택그룹명) 양식다운로드 그리드 생성
 */
app.controller('storeSideSdselGrpExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSideSdselGrpExcelCtrl', $scope, $http, false))

    //
    $scope.initGrid = function (s, e) {

        $scope.sdselTypeFgDataMap = sdselTypeFgDataMap;
    };

    // 양식 다운로드
    $scope.sampleDownload = function (data) {

        var params = {};
        params.sdselTypeFg = data.sdselTypeFg;
        params.sdselGrpCd = data.sdselGrpCd;
        params.sdselGrpNm = data.sdselGrpNm;

        $scope._inquiryMain("/base/multilingual/storeKioskSideOption/getStoreSideSdselGrpList.sb", params, function (){

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
                    messages["storeKioskSideOption.sideSdselGrp"] + '_엑셀업로드_양식_' + getCurDateTime() + '.xlsx',
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
        if ($('#grpExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#grpExcelUpFile')[0].files[0]) {
            var file = $('#grpExcelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                /*$timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#grpExcelUpFile')[0].files[0], {includeColumnHeaders: true}
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
                            renameKey(item, '세트구분', 'sdselTypeFg');
                            renameKey(item, '선택그룹코드', 'sdselGrpCd');
                            renameKey(item, '선택그룹명', 'sdselGrpNm');
                            renameKey(item, '선택그룹명(영문)', 'sdselGrpEnNm');
                            renameKey(item, '선택그룹명(중문)', 'sdselGrpCnNm');
                            renameKey(item, '선택그룹명(일문)', 'sdselGrpJpNm');
                        });

                        // 엔터값 제거
                        arr.forEach(function(item){
                            if (item.sdselGrpEnNm !== null && item.sdselGrpEnNm !== undefined && item.sdselGrpEnNm !== "") {
                                item.sdselGrpEnNm = item.sdselGrpEnNm.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.sdselGrpCnNm !== null && item.sdselGrpCnNm !== undefined && item.sdselGrpCnNm !== "") {
                                item.sdselGrpCnNm = item.sdselGrpCnNm.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.sdselGrpJpNm !== null && item.sdselGrpJpNm !== undefined && item.sdselGrpJpNm !== "") {
                                item.sdselGrpJpNm = item.sdselGrpJpNm.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                        });
                        console.log(arr);
                        //console.log(JSON.stringify(arr, null, 2));
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
                $("#grpExcelUpFile").val('');
                $scope._popMsg(messages['storeKioskSideOption.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['storeKioskSideOption.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
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

            if (nvl(item.sdselGrpEnNm + '', '').getByteLengthForOracle() > 50) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeKioskSideOption.en"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 영문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.sdselGrpCnNm + '', '').getByteLengthForOracle() > 50) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeKioskSideOption.cn"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 중문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.sdselGrpJpNm + '', '').getByteLengthForOracle() > 50) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["storeKioskSideOption.jp"] + " " + messages["storeKioskSideOption.valueSize.chk.msg"]);  // 일문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
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
            url    : '/base/multilingual/storeKioskSideOption/saveStoreSideSdselGrp.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('storeSideSdselGrpCtrl').getSideSdselGrpList();
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