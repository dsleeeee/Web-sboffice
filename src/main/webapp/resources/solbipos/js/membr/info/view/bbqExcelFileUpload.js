/****************************************************************
 *
 * 파일명 : bbqExcelFileUpload.js
 * 설  명 : 회원엑셀업로드(BBQ) 엑셀파일업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.26    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('bbqExcelFileUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('bbqExcelFileUploadCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    $scope.stepCnt = 1000;  // 한번에 DB에 저장할 숫자 세팅
    $scope.progressCnt = 0; // 처리된 숫자

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#excelUpFile')[0].files[0]) {
            // 엑셀 업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {
        $scope.progressCnt = 0; // 처리된 숫자(초기화)

        // 선택한 파일이 있으면
        if ($('#excelUpFile')[0].files[0]) {
            var file = $('#excelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#excelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function (workbook) {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#excelUpFile").val('');
                $scope._popMsg(messages['empCardInfo.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['empCardInfo.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '').replaceAll(' ', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue.replaceAll('\'', '');
                }
            }
            jsonData.push(item);
        }
        $timeout(function () {

            setTimeout(function() {
                // 새 데이터 등록
                $scope.save(jsonData);
            }, 500);

        }, 10);
    };

    // 텍스트파일이 변경된 경우
    $scope.textFileChanged = function () {
        if ($('#textUpFile')[0].files[0]) {
            // 텍스트 업로드 호출
            $scope.textUpload();
        }
    };

    // 텍스트 업로드
    $scope.textUpload = function () {
       $scope.progressCnt = 0; // 처리된 숫자(초기화)

        // 선택한 파일이 있을 경우
       if ($('#textUpFile')[0].files[0]) {
           var file = $('#textUpFile')[0].files[0];
           var fileName = file.name;
           var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

           // 확장자 체크. 확장자가 txt가 아닌 경우 오류메시지
           if (fileExtension.toLowerCase() !== '.txt') {
               $("#textUpFile").val('');
               $scope._popMsg(messages['excelUploadMPS.not.textFile']); // 텍스트 파일만 업로드 됩니다.(*.txt)
               return false;
           }

           var fr = new FileReader();

           fr.onloadend = function (e) {
           $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
               $timeout(function () {
                   var uploadData = '';
                   var bytes      = new Uint8Array(e.target.result);
                   var decoding   = new TextDecoder("euc-kr").decode(bytes); // Gpos에서 다운받은 회원정보 txt 파일의 인코딩 형식이 'ANSI' 라서 'euc-kr'로 decode ('UTF-8'은 'UTF-8'로 해야함.)
                   var length     = decoding.length;
                   for (var i = 0; i < length; i++) {
                        // 값이 스페이스인 경우 건너뜀.
                        if (decoding[i].charCodeAt(0) === 32) {
                            continue;
                        }

                        // 마지막 데이터인 경우
                        if (i === (length - 1)) {
                            // 마지막 데이터가 엔터가 아닌 경우 json 형식 데이터 만들때 split 해서 쓰기 위해 enter 값을 마지막에 추가해준다.
                            if (decoding[i].charCodeAt(0) !== 13) {
                                uploadData += decoding[i];
                                uploadData += String.fromCharCode(13);
                                // uploadData += '\n';
                            }
                        } else {
                            uploadData += decoding[i];
                        }
                   }
                   /*
                   console.log('### uploadData: \n' + uploadData);
                   console.log('### uploadData: \n' + JSON.stringify($scope.textUploadToJsonConvert(uploadData)) );
                   return;
                   */
                   // 읽어온 파일데이터가 null 이 아닌 경우
                   if (nvl(uploadData, '') !== '') {
                       var jsonData = $scope.textUploadToJsonConvert(uploadData);

                       if (jsonData.length > 0) {
                           $timeout(function () {
                               $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                               console.log(jsonData);
                               $scope.save(jsonData);
                           }, 10);
                       } else {
                           $scope._popMsg(messages['excelUploadMPS.noData'], function () {}); // 업로드 할 데이터가 없습니다.
                           $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                           return false;
                       }
                   } else {
                       $scope._popMsg(messages['excelUploadMPS.noData'], function () {}); // 업로드 할 데이터가 없습니다.
                       $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                       return false;
                   }
               }, 10);
           };

           // r.readAsBinaryString(file);
           fr.readAsArrayBuffer(file);
       }
    };

    // 텍스트 업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.textUploadToJsonConvert = function (uploadData) {
        //var uploadDataArr       = uploadData.split(String.fromCharCode(13));
        var uploadDataArr       = uploadData.split('\n');
        var uploadDataArrLength = uploadDataArr.length;
        var jsonData            = [];
        var item                = {};
        var columnNum           = 16; // 텍스트 업로드시 1줄의 JSON 데이터 컬럼 수 설정

        for (var i = 0; i < uploadDataArrLength; i++) {
            // String.fromCharCode(13) 으로 replace 를 하면 제대로 되지 않음..그래서 \n으로 replace 함..
            if (nvl(uploadDataArr[i].replace(/\n/gi, ''), '') !== '') {
                // if (nvl(uploadDataArr[i].replace(/String.fromCharCode(13)/gi, ''), '') !== '') {
                // var data          = uploadDataArr[i].replace(/String.fromCharCode(13)/gi, '');
                var data          = uploadDataArr[i].replace(/\n/gi, '').trim();

                if(data !== null && data !== undefined && data !== "") {
                    var dataArr       = data.split('|');
                    var dataArrLength = dataArr.length;
                    item = {};

                    // 1줄의 데이터를 , 로 split 한 자료의 길이가 columnNum 보다 작은 경우 수량 없음 오류 메시지
                    if (dataArrLength < columnNum) {
                        $scope._popMsg(messages["product.not.qty"]); // 업로드 데이터 중 없는 데이터가 존재합니다.
                        jsonData = [];
                        break;
                    }

                    for (var j = 0; j < dataArrLength; j++) {
                        var value = nvl(dataArr[j], '').trim();
                        if (value !== '') {
                            //1줄의 데이터가 columnNum 보다 많은 경우 양식이 이상한 것이므로 for문 종료
                            if (j >= columnNum) break;

                            if (j % columnNum === 0) item.remark = value;
                            else if (j % columnNum === 1) item.membrNm = value;
                            else if (j % columnNum === 2) item.memberTelNo = value;
                            else if (j % columnNum === 3) item.memberAddrDtl = value;
                            else if (j % columnNum === 14) item.lastSaleDate = value;
                        }
                    }
                    console.log(item);
                    jsonData.push(item);
                }
            }
        }
        return jsonData;
    };

    // 데이터 저장
    $scope.save = function (jsonData) {
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

            // 전화번호 필수값 체크
            //if(item.telNo !== null && item.telNo != ''){

                item.regStoreCd = $("#bbqMemberExcelUploadStoreCd").val();
                item.membrClassCd = "001"; // 회원등급 [001] : 이전회원용_수정금지
                item.gendrFg = 'N';
                item.weddingYn = 'N';
                item.emailRecvYn = 'N';
                item.smsRecvYn = 'N';
                item.totAdjPoint = 0;
                item.useYn = 'Y';
                item.status = 'I';
                item.lastSaleDate = nvl(item.lastSaleDate, "").replaceAll('-','');

                params.push(item);
            //}
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/membr/info/bbqUpload/bbqUpload/memberExcelSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    var parentScope = agrid.getScope('bbqMemberExcelUploadCtrl');
                    parentScope.uploadCallBack();
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
                $scope.save(jsonData);
            }
        });

    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
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