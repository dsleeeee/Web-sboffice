/****************************************************************
 *
 * 파일명 : excelUploadDlvrProdMultiNm.js
 * 설  명 : 상품명칭 엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.30     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  엑셀업로드 그리드 생성
 */
app.controller('excelUploadDlvrProdMultiNmCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('excelUploadDlvrProdMultiNmCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // 엑셀양식 다운로드
    $scope.excelFormDownload = function () {
        // 샘플양식생성
        $scope.addRow();

        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders : true,
                includeCellStyles : true,
                includeColumns : function (column) {
                    return column.visible;
                }
            }, '상품명칭양식.xlsx');
        }, 10);
    };

    // 샘플양식생성
    $scope.addRow = function () {

        // 그리드 초기화
        var flex = $scope.flex;
        flex.itemsSource = new wijmo.collections.CollectionView();
        flex.collectionView.trackChanges = true;

        // 배달앱 구분코드
        var dlvrCd = $("#hdDlvrCol").val().split(',');
        // 샘플양식 예시 값(대충 20개만 생성)
        var exNm = new Array(20);
        exNm[0] = '커피';
        exNm[1] = 'Coffee';
        exNm[2] = 'COFFEE';
        for(var i=3; i<=20; i++){ exNm[i] = ""; }

        // 샘플양식에 값 넣기
        var params = {};
        params.prodCd = '0000000000001';
        for(var i=0; i<dlvrCd.length; i++){
            eval("params.dlvrProdNm" + dlvrCd[i] + "='" + exNm[i] + "'");
        }

        var newRow = flex.collectionView.addNew();
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        flex.collectionView.commitNew();
    };

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#excelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.excelTextFg = 'excel'; // 업로드 progress 관련 기본값 세팅
        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

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

        // 배달의민족앱[3] 상품명칭 입력값 전체 체크
        // var str = "";
        // for (var r = 0; r < rowLength; r++) {
        //     str = "";
        //     for (var c = 0; c < $scope.flex.columns.length; c++) {
        //         if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
        //             var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
        //             var cellValue  = $scope.flex.getCellData(r, c, false);
        //
        //             if(colBinding !== "prodCd" && cellValue !== null && cellValue !== undefined && cellValue != ""){
        //                 str +=  "[채널사: ("+ colBinding + ")/" + cellValue + "]";
        //             }
        //
        //         }
        //     }
        //
        //     if(str != null && str != undefined && str != ""){
        //         if(str.indexOf("[채널사: (dlvrProdNm3)/") === -1 ){
        //             $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
        //
        //             // 배달의민족앱[3] 입력값이 없는 상품이 있습니다. <br>필수입력값이므로, 임의데이터라도 입력하세요.
        //             $scope._popMsg(messages["dlvrProdMulti.baemin.chk.msg"]);
        //             return false;
        //         }
        //     }
        //  }

        var str = ""
        var strProdNm = ""; // prodCd 유효성 검사 확인용
        var strProdCd = ""
        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            vProdCd = "";
            str = "";
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                item = {};
                if ($scope.flex.columns[c].header !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                    var cellValue  = $scope.flex.getCellData(r, c, false);

                    if(colBinding === "prodCd"){
                        if(cellValue !== null && cellValue !== "") {
                            vProdCd = cellValue.toString().replaceAll("'", "").replaceAll(" ", "");
                            // if(strProdCd.indexOf(vProdCd) === -1) {
                            //     strProdCd += (strProdCd === '' ? '' : ',') + vProdCd;
                            // }
                        }else{ continue; }
                    }else{
                        if(vProdCd !== null && vProdCd !== "") {
                            var prodNmVal ="";
                            item["prodCd"] = vProdCd;
                            item["dlvrNameCd"] = colBinding.replaceAll("dlvrProdNm", "");
                            if(cellValue != null && cellValue != "") {
                                prodNmVal = cellValue.toString().trim();
                                item["dlvrProdNm"] = prodNmVal.substr(0, 30);
                            }else{
                                prodNmVal = cellValue;
                                item["dlvrProdNm"] = prodNmVal;
                            }
                            jsonData.push(item);
                            if(prodNmVal != null && prodNmVal != ''){
                                str += prodNmVal
                            }
                        }
                    }
                }
            }
            // 배달시스템 값 전체가 null인 값 제외
            if(str != null && str != ''){
                if(strProdNm.indexOf(vProdCd) === -1) {
                    strProdNm += (strProdNm === '' ? '' : ',') + vProdCd;
                }
            }
            if(strProdCd.indexOf(vProdCd) === -1) {
                strProdCd += (strProdCd === '' ? '' : ',') + vProdCd;
            }
        }

        $timeout(function () {
            // 유효한 상품코드인지 체크
            $scope.chkProdCd(strProdCd,strProdNm, jsonData);
        }, 10);
    };

    // 유효한 상품코드인지 체크
    $scope.chkProdCd = function(strProdCd, strProdNm, jsonData){
        // 파라미터
        var params = {};
        params.prodCdCol = strProdCd;

        $scope._postJSONQuery.withOutPopUp( "/base/prod/dlvrProdMulti/dlvrProdMulti/chkDlvrProdMulti.sb", params, function(response){

            var cnt = response.data.data;

            if(strProdCd.split(",").length === cnt){ // 매핑할 상품코드가 상품마스터에 존재할 경우,
                // 데이터 임시 저장
                $scope.dupChk(strProdNm, jsonData);

            }else {
                var msg = messages["dlvrProdMulti.not.match.prodCd"]; // 등록되지 않은 상품코드가 존재합니다. 상품코드를 확인해주세요.
                $scope.valueCheckErrPopup(msg);
                return false;
            }
        });
    };

    // 데이터 임시 저장
    // $scope.prodCdSave = function(strProdNm, jsonData){
    //     // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    //     $scope._postJSONSave.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getDlvrProdCdSaveInsert.sb", jsonData, function (response) {
    //         // 입력값 확인
    //         $scope.chkNull(strProdNm,jsonData);
    //     });
    //
    // }

    // 데이터 중복 체크
    $scope.dupChk = function (strProdNm, jsonData) {

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getDlvrProdMultiNmMappingChk.sb", jsonData, function (response) {
            var result = response.data.data;

            if(result === null || result === "") {
                // 데이터 삭제
                $scope.chkNull(strProdNm, jsonData);
            } else {
                $scope._popMsg(result + " 명칭이 중복됩니다.");
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                return false;
            }
        });
    };

    // 입력값 확인
    $scope.chkNull = function(strProdNm, jsonData){

        var params = {};
        params.prodCdCol = strProdNm;
        params.mappFg = $("#mappFg").val();

        $scope._postJSONQuery.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getChkProdCdChk.sb", params, function(response) {
            var cnt = response.data.data;

            if (cnt == 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기

                // 배달의민족앱[3] 입력값이 없는 상품이 있습니다. <br>필수입력값이므로, 임의데이터라도 입력하세요.
                $scope._popMsg(messages["dlvrProdMulti.baemin.chk.msg"]);
                return false;
            }else{
                //데이터 중복 체크
                $scope.delete(jsonData);
            }
        });
    }

    // 데이터 삭제
    $scope.delete = function (jsonData){

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
        for (var i = $scope.progressCnt; i < $scope.totalRows; i++) {
            var item = jsonData[i];

            // 필수값 및 길이 체크
            // 상품코드
            if (nvl(item.prodCd, '') === '') {
                msg = messages["dlvrProdMulti.prodCd"] + messages["dlvrProdMulti.require.data"]; // 상품코드/바코드(이)가 없는 데이터가 존재합니다.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 배달앱별 상품명칭 길이 체크하여 30자 이상이면 문자 자르기
            // if(nvl(item.dlvrProdNm, '') !== '' && nvl(item.dlvrProdNm.toString(), '').getByteLengthForOracle() > 100) {
            //     item.dlvrProdNm = item.dlvrProdNm.substr(0, 30);
            // }
            item.mappFg = $("#mappFg").val();

            params.push(item);
        }

        $scope._postJSONSave.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/excelDeleteDlvrProdNm.sb", params, function (response) {
            // 데이터 저장
            $scope.save(jsonData);
        });
    }

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

            // 필수값 및 길이 체크
            // 상품코드
            if (nvl(item.prodCd, '') === '') {
                msg = messages["dlvrProdMulti.prodCd"] + messages["dlvrProdMulti.require.data"]; // 상품코드/바코드(이)가 없는 데이터가 존재합니다.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 배달앱별 상품명칭 길이 체크하여 30자 이상이면 문자 자르기
            // if(nvl(item.dlvrProdNm, '') !== '' && nvl(item.dlvrProdNm.toString(), '').getByteLengthForOracle() > 100) {
            //     item.dlvrProdNm = item.dlvrProdNm.substr(0, 30);
            // }

            item.mappFg = $("#mappFg").val();

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
            url    : '/base/prod/dlvrProdMulti/dlvrProdMulti/excelUploadsave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (nvl($scope.parentCtrl, '') !== '') {
                    var parentScope = agrid.getScope($scope.parentCtrl);
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

    // 엑셀업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['dlvrProdMulti.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><!--<span class="bk" id="progressCnt">0</span> / --><span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

    // 업로드 한 데이터 값체크 중 에러시 에러팝업 띄우기 및 엑셀업로딩 팝업 닫기
    $scope.valueCheckErrPopup = function (msg) {
        $scope._popMsg(msg, function () {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
        });
    };

}]);