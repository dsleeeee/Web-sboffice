/****************************************************************
 *
 * 파일명 : workStudentKookmin.js
 * 설  명 : 근로학생관리(국민대) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.04     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 근로학생관리 그리드 생성
 */
app.controller('workStudentKookminCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workStudentKookminCtrl', $scope, $http, false));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.getWorkStudentKookminList();

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "studentNo") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 근로학생 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "studentNo") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        // 값 변경시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "studentNm" || col.binding === "department" || col.binding === "mpNo" ||
                    col.binding === "bankNm" || col.binding === "accountNo" || col.binding === "remark") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    $scope.$on("workStudentKookminCtrl", function(event, data) {
        $scope.getWorkStudentKookminList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생관리 그리드 조회
    $scope.getWorkStudentKookminList = function(){

        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/kookmin/workStudent/workStudent/workStudent/getWorkStudentKookminList.sb', params, function(){}, false);

    };

    // 근로학생관리 그리드 행 추가
    $scope.addRow = function() {
        var params = {};
        params.status = "I";
        params.gChk = true;
        $scope._addRow(params);
    };

    // 근로학생관리 그리드 행 삭제
    $scope.delete = function(){
        $scope._popConfirm(messages['workStudentKookmin.msg.delConfirm'], function () {
            var params = [];

            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                item.status = "D";
                if (item.gChk) {
                    params.push(item);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/kookmin/workStudent/workStudent/workStudent/saveWorkStudent.sb', params, function(){ $scope.getWorkStudentKookminList() });
        });
    };


    // 근로학생관리 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            // 입력값 체크
            if($scope.flex.collectionView.itemsEdited[i].studentNo == "" || $scope.flex.collectionView.itemsEdited[i].studentNo == null){
                $scope._popMsg(messages["workStudentKookmin.studentNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].studentNm == "" || $scope.flex.collectionView.itemsEdited[i].studentNm == null){
                $scope._popMsg(messages["workStudentKookmin.studentNm"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].department == "" || $scope.flex.collectionView.itemsEdited[i].department == null){
                $scope._popMsg(messages["workStudentKookmin.department"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].mpNo == "" || $scope.flex.collectionView.itemsEdited[i].mpNo == null){
                $scope._popMsg(messages["workStudentKookmin.mpNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].bankNm == "" || $scope.flex.collectionView.itemsEdited[i].bankNm == null){
                $scope._popMsg(messages["workStudentKookmin.bankNm"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].accountNo == "" || $scope.flex.collectionView.itemsEdited[i].accountNo == null){
                $scope._popMsg(messages["workStudentKookmin.accountNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].coopYn == "" || $scope.flex.collectionView.itemsEdited[i].coopYn == null){
                $scope._popMsg(messages["workStudentKookmin.coopYn"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }else{
                if($scope.flex.collectionView.itemsEdited[i].coopYn !== 'Y' && $scope.flex.collectionView.itemsEdited[i].coopYn !== 'N'){
                    $scope._popMsg(messages["workStudentKookmin.coopYn"] + '은 Y 또는 N만 입력 가능합니다');
                    return false;
                }
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].studentNo, '').getByteLengthForOracle() > 30){
                // 학번은 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNo'] + "은 " + messages["cmm.max30Chk"] + "<br>(학번 : " + $scope.flex.collectionView.itemsEdited[i].studentNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].studentNm, '').getByteLengthForOracle() > 50){
                // 이름은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(이름 : " + $scope.flex.collectionView.itemsEdited[i].studentNm + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].department, '').getByteLengthForOracle() > 100){
                // 학과는 최대 100byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.department'] + "는 " + messages["cmm.max100Chk"] + "<br>(학과 : " + $scope.flex.collectionView.itemsEdited[i].department + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].mpNo, '').getByteLengthForOracle() > 15){
                // 연락처는 최대 15byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.mpNo'] + "는 " + messages["cmm.max15Chk"] + "<br>(연락처 : " + $scope.flex.collectionView.itemsEdited[i].mpNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].bankNm, '').getByteLengthForOracle() > 50){
                // 은행은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.bankNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(은행 : " + $scope.flex.collectionView.itemsEdited[i].bankNm + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].accountNo, '').getByteLengthForOracle() > 50){
                // 계좌번호는 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.accountNo'] + "는 " + messages["cmm.max50Chk"] + "<br>(계좌번호 : " + $scope.flex.collectionView.itemsEdited[i].accountNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].coopYn, '').getByteLengthForOracle() > 1){
                // 조합원여부 최대 1byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.coopYn'] + "는 " + messages["cmm.max1Chk"] + "<br>(조합원여부 : " + $scope.flex.collectionView.itemsEdited[i].coopYn + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsEdited[i].remark, '').getByteLengthForOracle() > 2000){
                // 비고는 최대 2000byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.remark'] + "는 " + messages["cmm.max2000Chk"] + "<br>(비고 : " + $scope.flex.collectionView.itemsEdited[i].remark + ")");
                return;
            }
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            // 입력값 체크
            if($scope.flex.collectionView.itemsAdded[i].studentNo == "" || $scope.flex.collectionView.itemsAdded[i].studentNo == null){
                $scope._popMsg(messages["workStudentKookmin.studentNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].studentNm == "" || $scope.flex.collectionView.itemsAdded[i].studentNm == null){
                $scope._popMsg(messages["workStudentKookmin.studentNm"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].department == "" || $scope.flex.collectionView.itemsAdded[i].department == null){
                $scope._popMsg(messages["workStudentKookmin.department"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].mpNo == "" || $scope.flex.collectionView.itemsAdded[i].mpNo == null){
                $scope._popMsg(messages["workStudentKookmin.mpNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].bankNm == "" || $scope.flex.collectionView.itemsAdded[i].bankNm == null){
                $scope._popMsg(messages["workStudentKookmin.bankNm"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].accountNo == "" || $scope.flex.collectionView.itemsAdded[i].accountNo == null){
                $scope._popMsg(messages["workStudentKookmin.accountNo"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].coopYn == "" || $scope.flex.collectionView.itemsAdded[i].coopYn == null){
                $scope._popMsg(messages["workStudentKookmin.coopYn"] + messages["workStudentKookmin.inputEnv"]);
                return false;
            }else{
                if($scope.flex.collectionView.itemsAdded[i].coopYn !== 'Y' && $scope.flex.collectionView.itemsAdded[i].coopYn !== 'N'){
                    $scope._popMsg(messages["workStudentKookmin.coopYn"] + '은 Y 또는 N만 입력 가능합니다');
                    return false;
                }
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].studentNo, '').getByteLengthForOracle() > 30){
                // 학번은 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNo'] + "은 " + messages["cmm.max30Chk"] + "<br>(학번 : " + $scope.flex.collectionView.itemsAdded[i].studentNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].studentNm, '').getByteLengthForOracle() > 50){
                // 이름은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(이름 : " + $scope.flex.collectionView.itemsAdded[i].studentNm + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].department, '').getByteLengthForOracle() > 100){
                // 학과는 최대 100byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.department'] + "는 " + messages["cmm.max100Chk"] + "<br>(학과 : " + $scope.flex.collectionView.itemsAdded[i].department + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].mpNo, '').getByteLengthForOracle() > 15){
                // 연락처는 최대 15byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.mpNo'] + "는 " + messages["cmm.max15Chk"] + "<br>(연락처 : " + $scope.flex.collectionView.itemsAdded[i].mpNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].bankNm, '').getByteLengthForOracle() > 50){
                // 은행은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.bankNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(은행 : " + $scope.flex.collectionView.itemsAdded[i].bankNm + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].accountNo, '').getByteLengthForOracle() > 50){
                // 계좌번호는 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.accountNo'] + "는 " + messages["cmm.max50Chk"] + "<br>(계좌번호 : " + $scope.flex.collectionView.itemsAdded[i].accountNo + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].coopYn, '').getByteLengthForOracle() > 1){
                // 조합원여부 최대 1byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.coopYn'] + "는 " + messages["cmm.max1Chk"] + "<br>(조합원여부 : " + $scope.flex.collectionView.itemsAdded[i].coopYn + ")");
                return;
            }
            if(nvl($scope.flex.collectionView.itemsAdded[i].remark, '').getByteLengthForOracle() > 2000){
                // 비고는 최대 2000byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.remark'] + "는 " + messages["cmm.max2000Chk"] + "<br>(비고 : " + $scope.flex.collectionView.itemsAdded[i].remark + ")");
                return;
            }

            // 학번 중복 체크
            var check_studentNo_cnt = 0;
            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                if($scope.flex.collectionView.items[j].studentNo == $scope.flex.collectionView.itemsAdded[i].studentNo) {
                    check_studentNo_cnt++;
                }
            }
            if(check_studentNo_cnt > 1){
                $scope._popMsg(messages["workStudentKookmin.studentNoChk"]+' ('+$scope.flex.collectionView.itemsAdded[i].studentNo+')'); // 학번 중복 확인
                return false;
            }

            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // console.log(params);

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/kookmin/workStudent/workStudent/workStudent/saveWorkStudent.sb', params, function(){ $scope.getWorkStudentKookminList() });
    }

    // 양식 다운로드
    $scope.sampleDownload = function () {
        var scope = agrid.getScope('workStudentKookminExcelCtrl');
        scope.sampleDownload();
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
 * 상품설명 양식다운로드 그리드 생성
 */
app.controller('workStudentKookminExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workStudentKookminExcelCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };

    // 양식 다운로드
    $scope.sampleDownload = function () {

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	true,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                messages["workStudentKookmin.workStudentKookmin"] + '_엑셀업로드_양식_' + getCurDateTime() + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
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
                            renameKey(item, '학번', 'studentNo');
                            renameKey(item, '이름', 'studentNm');
                            renameKey(item, '학과', 'department');
                            renameKey(item, '연락처', 'mpNo');
                            renameKey(item, '은행', 'bankNm');
                            renameKey(item, '계좌번호', 'accountNo');
                            renameKey(item, '조합원', 'coopYn');
                            renameKey(item, '비고', 'remark');
                        });

                        // 엔터값 제거
                        arr.forEach(function(item){
                            if (item.studentNo !== null && item.studentNo !== undefined && item.studentNo !== "") {
                                item.studentNo = item.studentNo.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.studentNm !== null && item.studentNm !== undefined && item.studentNm !== "") {
                                item.studentNm = item.studentNm.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.department !== null && item.department !== undefined && item.department !== "") {
                                item.department = item.department.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                            if (item.mpNo !== null && item.mpNo !== undefined && item.mpNo !== "") {
                                item.mpNo = item.mpNo.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.bankNm !== null && item.bankNm !== undefined && item.bankNm !== "") {
                                item.bankNm = item.bankNm.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.accountNo !== null && item.accountNo !== undefined && item.accountNo !== "") {
                                item.accountNo = item.accountNo.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                            if (item.coopYn !== null && item.coopYn !== undefined && item.coopYn !== "") {
                                item.coopYn = item.coopYn.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                            if (item.remark !== null && item.remark !== undefined && item.remark !== "") {
                                item.remark = item.remark.toString().replace(/\r\n|\r|\n/g, ' ');
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
                $("#excelUpFile").val('');
                $scope._popMsg(messages['fnkeyCmNmcd.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['prodLang.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
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
            console.log(Object.keys(item));
            if(item.studentNo == "" || item.studentNo == null){
                $scope._popMsg(messages["workStudentKookmin.studentNo"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.studentNm == "" || item.studentNm == null){
                $scope._popMsg(messages["workStudentKookmin.studentNm"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.department == "" || item.department == null){
                $scope._popMsg(messages["workStudentKookmin.department"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.mpNo == "" || item.mpNo == null){
                $scope._popMsg(messages["workStudentKookmin.mpNo"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.bankNm == "" || item.bankNm == null){
                $scope._popMsg(messages["workStudentKookmin.bankNm"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.accountNo == "" || item.accountNo == null){
                $scope._popMsg(messages["workStudentKookmin.accountNo"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.coopYn == "" || item.coopYn == null){
                $scope._popMsg(messages["workStudentKookmin.coopYn"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }else{
                if(item.coopYn !== 'Y' && item.coopYn !== 'N'){
                    $scope._popMsg(messages["workStudentKookmin.coopYn"] + '은 Y 또는 N만 입력 가능합니다');
                    $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    return false;
                }
            }

            if(nvl(item.studentNo, '').getByteLengthForOracle() > 30){
                // 학번은 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNo'] + "은 " + messages["cmm.max30Chk"] + "<br>(학번 : " + item.studentNo + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.studentNm, '').getByteLengthForOracle() > 50){
                // 이름은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.studentNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(이름 : " + item.studentNm + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.department, '').getByteLengthForOracle() > 100){
                // 학과는 최대 100byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.department'] + "는 " + messages["cmm.max100Chk"] + "<br>(학과 : " + item.department + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.mpNo, '').getByteLengthForOracle() > 15){
                // 연락처는 최대 15byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.mpNo'] + "는 " + messages["cmm.max15Chk"] + "<br>(연락처 : " + item.mpNo + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.bankNm, '').getByteLengthForOracle() > 50){
                // 은행은 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.bankNm'] + "은 " + messages["cmm.max50Chk"] + "<br>(은행 : " + item.bankNm + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.accountNo, '').getByteLengthForOracle() > 50){
                // 계좌번호는 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.accountNo'] + "는 " + messages["cmm.max50Chk"] + "<br>(계좌번호 : " + item.accountNo + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.coopYn, '').getByteLengthForOracle() > 1){
                // 조합원여부 최대 1byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.coopYn'] + "는 " + messages["cmm.max1Chk"] + "<br>(조합원여부 : " + item.coopYn + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
            }
            if(nvl(item.remark, '').getByteLengthForOracle() > 2000){
                // 비고는 최대 2000byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['workStudentKookmin.remark'] + "는 " + messages["cmm.max2000Chk"] + "<br>(비고 : " + item.remark + ")");
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return;
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
            url    : '/kookmin/workStudent/workStudent/workStudent/saveWorkStudentExcelUpload.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (response.data.message !== null) {
                $scope._popMsg(response.data.message);
                return false;
            }
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('workStudentKookminCtrl').getWorkStudentKookminList();
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
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['excelUpload.excelUploading'] + '</p>';
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


