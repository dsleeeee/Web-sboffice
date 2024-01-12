/****************************************************************
 *
 * 파일명 : captionMsg.js
 * 설  명 : 다국어관리(기능키/메시지) - 기능키/메시지 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.09     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 기능키, 메시지 구분
var captionMsgGb = [
    {"name":"버튼","value":"BTN"},
    {"name":"라벨","value":"LBL"},
    {"name":"콤보박스","value":"CBO"},
    {"name":"메시지","value":"MSG"},
    {"name":"그리드","value":"GRD"}
];

/**
 * 기능키/메시지 조회 그리드 생성
 */
app.controller('captionMsgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('captionMsgCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("captionMsgGrp", captionMsgGrpList);

    //
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.captionMsgGbDataMap = new wijmo.grid.DataMap(captionMsgGb, 'value', 'name');

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "captionMsgId" || col.binding === "captionMsgGb" || col.binding === "captionMsgNm" ||
                    col.binding === "captionMsgEnNm" || col.binding === "captionMsgCnNm" || col.binding === "captionMsgJpNm") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "captionMsgId") {
                    var item = s.rows[e.row].dataItem;
                        if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 기능키 or 메시지코드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'captionMsgId') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });
    };

    $scope.$on("captionMsgCtrl", function (event, data) {
        $scope.getCaptionMsgList();
        event.preventDefault();
    });

    // 조회
    $scope.getCaptionMsgList = function(){
        // 파라미터
        var params = {};
        params.captionImgCd = $scope.srchCaptionMsgGrpCombo.selectedValue;

        // 화면구분 이미지 조회
        $scope._postJSONQuery.withOutPopUp("/base/multilingual/captionMsg/getCaptionMsgGrpDtl.sb", params, function (response) {

            var fileInfo = response.data.data;

            // 이미지 셋팅
            $("#imgCaptionMsgGrpView").attr("src", "http://" + window.location.host + "/Media/" + fileInfo.fileNm);

            // 화면구분 선택에 따른 기능키/메시지 탭 리스트 조회
            $scope._inquiryMain("/base/multilingual/captionMsg/getCaptionMsgList.sb", params);
        });
    };

    // 추가
    $scope.addRow = function () {
        // 파라미터 설정
        var params = {};
        params.gChk = true;
        params.captionMsgId = "";
        params.captionMsgGb = "BTN";
        params.captionMsgNm = "";
        params.captionMsgEnNm = "";
        params.captionMsgCnNm = "";
        params.captionMsgJpNm = "";
        params.captionImgCd = $scope.srchCaptionMsgGrpCombo.selectedValue;
        
        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 삭제
    $scope.delRow = function () {

        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = new Array();

            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    if(item.captionMsgId  === null || item.captionMsgId  === undefined || item.captionMsgId  === "" || item.status === "I"){
                        $scope.flex.collectionView.removeAt(i);
                    }else{
                        params.push(item);
                    }
                }
            }

            $scope._save('/base/multilingual/captionMsg/deleteCaptionMsg.sb', params, function(){
                // 재조회
                $scope.getCaptionMsgList();
            });
        });
    };

    // 저장 전 입력값 체크
    $scope.saveRow = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if($scope.flex.collectionView.itemsEdited[u].captionMsgId == ""){
                    $scope._popMsg(messages["captionMsg.captionMsgId"] + messages["cmm.require.text"]); // 기능키 or 메시지코드(을)를 입력하세요.
                    return false;
                }

                if($scope.flex.collectionView.itemsEdited[u].captionMsgNm == ""){
                    $scope._popMsg(messages["captionMsg.captionMsgNm"] + messages["cmm.require.text"]); // 기능키 or 메시지명(을)를 입력하세요.
                    return false;
                }

                if($scope.flex.collectionView.itemsEdited[u].captionMsgEnNm == "" && $scope.flex.collectionView.itemsEdited[u].captionMsgCnNm == "" && $scope.flex.collectionView.itemsEdited[u].captionMsgJpNm == ""){
                    $scope._popMsg(messages["captionMsg.multilingual.chk.msg"]); // 영문, 중문, 일문을 입력하세요.(영문, 중문, 일문 중 하나는 입력해야 합니다)
                    return false;
                }

                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].captionMsgId == ""){
                    $scope._popMsg(messages["captionMsg.captionMsgId"] + messages["cmm.require.text"]); // 기능키 or 메시지코드(을)를 입력하세요.
                    return false;
                }

                if($scope.flex.collectionView.itemsAdded[i].captionMsgNm == ""){
                    $scope._popMsg(messages["captionMsg.captionMsgNm"] + messages["cmm.require.text"]); // 기능키 or 메시지명(을)를 입력하세요.
                    return false;
                }

                if($scope.flex.collectionView.itemsAdded[i].captionMsgEnNm == "" && $scope.flex.collectionView.itemsAdded[i].captionMsgCnNm == "" && $scope.flex.collectionView.itemsAdded[i].captionMsgJpNm == ""){
                    $scope._popMsg(messages["captionMsg.multilingual.chk.msg"]); // 영문, 중문, 일문을 입력하세요.(영문, 중문, 일문 중 하나는 입력해야 합니다)
                    return false;
                }

                $scope.flex.collectionView.itemsAdded[i].status = 'I';
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            // 기능키 or 메시지코드 중복체크
            var arrCaptionMsgId = [];
            var arrDuplicate = [];
            var strCaptionMsgId = "";
            var vParams = {};

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                arrCaptionMsgId.push($scope.flex.collectionView.itemsAdded[i].captionMsgId);
                strCaptionMsgId += $scope.flex.collectionView.itemsAdded[i].captionMsgId + ",";
            }

            // 새로 입력한(신규) 기능키 or 메시지코드 중 중복되는 코드가 있는지 확인
            for(var i=0; i<arrCaptionMsgId.length; i++){
                for(var j=i+1; j<arrCaptionMsgId.length; j++){
                    if(arrCaptionMsgId[i] == arrCaptionMsgId[j]){
                        if(!arrDuplicate.includes(arrCaptionMsgId[i])){
                            arrDuplicate.push(arrCaptionMsgId[i]);
                        }
                    }
                }
            }

            if(arrDuplicate.length > 0){
                // 기능키 or 메시지코드 '' 가 중복됩니다. 다른 기능키 or 메시지코드를 입력하세요.
                $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + arrDuplicate.toString() + messages["captionMsg.captionMsgId.duplicate"]);
                return false;
            }

            // 새로 입력한(신규) 기능키 or 메시지코드와 기존에 입력되어있는 기능키 or 메시지코드 중 중복되는 코드가 있는지 확인
            if (strCaptionMsgId.length > 0) {
                vParams.captionMsgId = strCaptionMsgId.substr(0, strCaptionMsgId.length - 1);
                $scope._postJSONQuery.withOutPopUp('/base/multilingual/captionMsg/chkCaptionMsgId.sb', vParams, function (response) {
                    if (!isEmptyObject(response.data.data)) {
                        // 기능키 or 메시지코드 '' 은(는) 이미 등록되어 있습니다. 다른 기능키 or 메시지코드를 입력하세요.
                        $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + response.data.data + messages["captionMsg.captionMsgId.duplicate2"]);
                        return false;
                    }else{
                        $scope.saveData(params);
                    }
                });
            }else{
                $scope.saveData(params);
            }
        });
    };

    // 저장
    $scope.saveData = function(params){
        $scope._save("/base/multilingual/captionMsg/saveCaptionMsg.sb", params, function(result) {
            // 재조회
            $scope.getCaptionMsgList();
        });
    };
    
    // 그리드 입력값 변경시, 자동체크
    $scope.checked = function(item){
        item.gChk = true;
    };

    // 화면구분등록 탭에서 화면구분정보 변경시, 기능키/메시지 탭의 화면구분 콤보박스 재조회
    $scope.setCaptionMsgGrpCombo = function(){

        var url = '/base/multilingual/captionMsg/getCaptionMsgGrpCombo.sb';
        var params = {};

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    for (var i = 0; i < list.length; i++) {
                        comboData = {};
                        comboData.name  = list[i].name;
                        comboData.value = list[i].value;
                        comboArray.push(comboData);
                    }

                    // 화면구분 검색조건 콤보박스 셋팅
                    $scope._setComboData("captionMsgGrp", comboArray);
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            $timeout(function () {
            }, 10);
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
                        return column.visible;
                    }
                }, messages["captionMsg.captionMsg"]  + '_' +  getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
    };

    // 양식다운로드
    $scope.sampleDownload = function () {
        var vScope = agrid.getScope('captionMsgExcelCtrl');
        vScope.sampleDownload();
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["captionMsg.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');

        });
    };

}]);

/**
 * 기능키/메시지 엑셀업로드 그리드 생성
 */
app.controller('captionMsgExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('captionMsgExcelCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // 양식 다운로드
    $scope.sampleDownload = function () {

        // 샘플데이터
        $scope.addRow();

        $timeout(function () {
           wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
               includeColumnHeaders : true,
               includeCellStyles : true,
               includeColumns : function (column) {
                   return column.visible;
               }
           }, messages["captionMsg.captionMsg"] + '_엑셀업로드_양식_' +  getCurDateTime() + '.xlsx');
         }, 10);
    };

    // 샘플데이터
    $scope.addRow = function(){

        // 그리드 초기화
        var flex = $scope.flex;
        flex.itemsSource = new wijmo.collections.CollectionView();
        flex.collectionView.trackChanges = true;

        // 샘플양식에 값 넣기
        var params = {};
        params.gChk = true;
        params.captionMsgId = "BTN_001";
        params.captionMsgGb = "버튼";
        params.captionMsgNm = "결제하기";
        params.captionMsgEnNm = "Pay";
        params.captionMsgCnNm = "付款";
        params.captionMsgJpNm = "お支払い";

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

        var vScope  = agrid.getScope('captionMsgCtrl');

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
                /*$timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#excelUpFile')[0].files[0], {includeColumnHeaders: true}
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
                            renameKey(item, '기능키 or 메시지코드', 'captionMsgId');
                            renameKey(item, '구분', 'captionMsgGb');
                            renameKey(item, '기능키 or 메시지명', 'captionMsgNm');
                            renameKey(item, '영문', 'captionMsgEnNm');
                            renameKey(item, '중문', 'captionMsgCnNm');
                            renameKey(item, '일문', 'captionMsgJpNm');
                        });

                        // 엔터값 제거
                        arr.forEach(function(item){

                            if (item.captionMsgId !== null && item.captionMsgId !== undefined && item.captionMsgId !== "") {
                                item.captionMsgId = item.captionMsgId.replace(/\r\n|\r|\n|\s/g, '');
                            }

                            if (item.captionMsgGb !== null && item.captionMsgGb !== undefined && item.captionMsgGb !== "") {
                                item.captionMsgGb = $scope.getCaptionMsgGbCode(item.captionMsgGb); // 구분 코드값으로 변환
                            }

                            if (item.captionMsgNm !== null && item.captionMsgNm !== undefined && item.captionMsgNm !== "") {
                                item.captionMsgNm = item.captionMsgNm.replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.captionMsgEnNm !== null && item.captionMsgEnNm !== undefined && item.captionMsgEnNm !== "") {
                                item.captionMsgEnNm = item.captionMsgEnNm.replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.captionMsgCnNm !== null && item.captionMsgCnNm !== undefined && item.captionMsgCnNm !== "") {
                                item.captionMsgCnNm = item.captionMsgCnNm.replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.captionMsgJpNm !== null && item.captionMsgJpNm !== undefined && item.captionMsgJpNm !== "") {
                                item.captionMsgJpNm = item.captionMsgJpNm.replace(/\r\n|\r|\n/g, ' ');
                            }
                            
                            // 화면구분 추가
                            item.captionImgCd = vScope.srchCaptionMsgGrpCombo.selectedValue;

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
                $("#excelUpFile").val('');
                $scope._popMsg(messages['captionMsg.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.flex.rows.length;
        var vScope  = agrid.getScope('captionMsgCtrl');

        if (rowLength === 0) {
            $scope._popMsg(messages['captionMsg.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';

                    if(colBinding === "captionMsgGb"){
                        item[colBinding] = $scope.getCaptionMsgGbCode(cellValue); // 구분 코드값으로 변환
                    }else{
                        item[colBinding] = cellValue.replaceAll('\'', '');
                    }

                    item["captionImgCd"] = vScope.srchCaptionMsgGrpCombo.selectedValue;
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

            if (nvl(item.captionMsgId, '').toString() === '') {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + messages["cmm.require.text"]);  // 기능키 or 메시지코드 을(를) 입력하세요.
                return false;
            }

            if (nvl(item.captionMsgGb, '').toString() === '') {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.captionMsgGb"] + " " + messages["cmm.require.text"]);  // 구분 을(를) 입력하세요.
                return false;
            }

            if (nvl(item.captionMsgNm, '').toString() === '') {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.captionMsgNm"] + " " + messages["cmm.require.text"]);  // 기능키 or 메시지명 을(를) 입력하세요.
                return false;
            }

            if (nvl(item.captionMsgEnNm, '').toString() === '' && nvl(item.captionMsgCnNm, '').toString() === '' && nvl(item.captionMsgJpNm, '').toString() === '') {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.multilingual.chk.msg"]);  // 영문, 중문, 일문을 입력하세요.(영문, 중문, 일문 중 하나는 입력해야 합니다)
                return false;
            }

            if (nvl(item.captionMsgId + '', '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + messages["captionMsg.valueSize.chk.msg"]);  // 기능키 or 메시지코드 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.captionMsgNm + '', '').getByteLengthForOracle() > 500) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.captionMsgNm"] + " " + messages["captionMsg.valueSize.chk.msg"]);  // 기능키 or 메시지명 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.captionMsgEnNm + '', '').getByteLengthForOracle() > 500) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.en"] + " " + messages["captionMsg.valueSize.chk.msg"]);  // 영문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.captionMsgCnNm + '', '').getByteLengthForOracle() > 500) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.cn"] + " " + messages["captionMsg.valueSize.chk.msg"]);  // 중문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }

            if (nvl(item.captionMsgJpNm + '', '').getByteLengthForOracle() > 500) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                $scope._popMsg(messages["captionMsg.jp"] + " " + messages["captionMsg.valueSize.chk.msg"]);  // 일문 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.
                return false;
            }
        }

        // 기능키 or 메시지코드 중복체크
        var arrCaptionMsgId = [];
        var arrDuplicate = [];
        var strCaptionMsgId = "";
        var vParams = {};

        for (var i = 0; i < $scope.totalRows; i++) {
            var item = jsonData[i];
            arrCaptionMsgId.push(item.captionMsgId.toString());
            strCaptionMsgId += item.captionMsgId.toString() + ",";
        }

        // 새로 입력한(신규) 기능키 or 메시지코드 중 중복되는 코드가 있는지 확인
        for(var i=0; i<arrCaptionMsgId.length; i++){
            for(var j=i+1; j<arrCaptionMsgId.length; j++){
                if(arrCaptionMsgId[i] == arrCaptionMsgId[j]){
                    if(!arrDuplicate.includes(arrCaptionMsgId[i])){
                        arrDuplicate.push(arrCaptionMsgId[i]);
                    }
                }
            }
        }

        if(arrDuplicate.length > 0){
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
            // 기능키 or 메시지코드 '' 가 중복됩니다. 다른 기능키 or 메시지코드를 입력하세요.
            $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + arrDuplicate.toString() + messages["captionMsg.captionMsgId.duplicate"]);
            return false;
        }

        // 새로 입력한(신규) 기능키 or 메시지코드와 기존에 입력되어있는 기능키 or 메시지코드 중 중복되는 코드가 있는지 확인
        vParams.captionMsgId = strCaptionMsgId.substr(0, strCaptionMsgId.length - 1);
        $scope._postJSONQuery.withOutPopUp('/base/multilingual/captionMsg/chkCaptionMsgId.sb', vParams, function (response) {
            if (!isEmptyObject(response.data.data)) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 기능키 or 메시지코드 '' 은(는) 이미 등록되어 있습니다. 다른 기능키 or 메시지코드를 입력하세요.
                $scope._popMsg(messages["captionMsg.captionMsgId"] + " " + response.data.data + messages["captionMsg.captionMsgId.duplicate2"]);
                return false;
            }else{
                // 데이터 저장
                $scope.saveData(jsonData);
            }
        });
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
            url    : '/base/multilingual/captionMsg/saveCaptionMsg.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('captionMsgCtrl').getCaptionMsgList();
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

    // 구분 코드값으로 변환
    $scope.getCaptionMsgGbCode = function (type) {
        if(type === "버튼"){
            return "BTN";
        }else if (type === "라벨"){
            return "LBL";
        }else if (type === "콤보박스"){
            return "CBO";
        }else if (type === "메시지"){
            return "MSG";
        }else if (type === "그리드"){
            return "GRD";
        }else{
            return "";
        }
    };

}]);