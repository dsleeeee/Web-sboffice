/****************************************************************
 *
 * 파일명 : storeBatchChangeExcelUploadAdd.js
 * 설  명 : 매장엑셀업로드 팝업 JavaScript
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
 *  매장엑셀업로드 팝업 조회 그리드 생성
 */
app.controller('storeExcelUploadAddCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeExcelUploadAddCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("storeExcelUploadAddCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        // 엑셀업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
        $scope.deleteExl();
    };

    // 현재 세션ID 와 동일한 데이터 삭제
    $scope.deleteExl = function () {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDeleteAll.sb", params, function(){
            // 엑셀 업로드
            $scope.excelUpload();
        });
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자
        $scope.uploadFailFg = false; // (2026.09.02) 분할 업로드 실패 여부

        // 선택한 파일이 있으면
        if ($('#storeExcelUpFile')[0].files[0]) {
            var file          = $('#storeExcelUpFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                // 엑셀 파싱(동기, 대용량 시 오래 걸림) 구간부터 화면 클릭 차단
                //  - 해제는 배치 저장 완료/에러 시 excelUploadingPopup(false)에서 처리됨
                var uploadOverlay = document.getElementById('loadingOverlay');
                if (uploadOverlay) uploadOverlay.classList.add('active');
                document.addEventListener('contextmenu', contextMenuHandler);
                window.addEventListener('beforeunload', beforeUnloadHandler);

                // $timeout(function () {
                //     var flex = $scope.flex;
                //     wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#storeExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                //         , function () {
                //             $timeout(function () {
                //                 // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
                //                 $scope.excelUploadToJsonConvert();
                //             }, 10);
                //         }
                //     );
                // }, 10);

                // (2026.09.02) 파싱 실패 시 정리 : 차단 해제 + 파일선택 초기화 + 그리드/TEMP 정리 + 오류 안내
                //  - 여기서 해제하지 않으면 투명 오버레이가 남아 화면 전체가 클릭 불능이 된다.
                var parseFailClear = function () {
                    $scope.excelUploadingPopup(false);        // 오버레이/우클릭/새로고침 차단 해제 + 팝업 닫기
                    $("#storeExcelUpFile").val('');           // 같은 파일 재선택 가능하도록 input 비움
                    $scope.uploadFailClear();                 // 그리드 로컬 클리어 + TEMP 삭제
                    $scope._popMsg(messages['cmm.saveFail']); // 저장에 실패하였습니다
                };

                // excel file read
                var reader = new FileReader();
                var arr = [];
                reader.onerror = function(){
                    // (2026.09.02) 파일 읽기 자체 실패(파일 잠김 등) 시 정리
                    parseFailClear();
                };
                reader.onload = function(){
                    try {
                        var fileData = reader.result;
                        var wb = XLSX.read(fileData, {type : 'binary'});
                        wb.SheetNames.forEach(function(sheetName) {
                            arr = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);

                            // key명 변경
                            arr.forEach(function(item){
                                // 엑셀 헤더 key 공백 제거
                                Object.keys(item).forEach(function(key){
                                    var trimmedKey = key.trim();
                                    if (trimmedKey !== key) {
                                        item[trimmedKey] = item[key];
                                        delete item[key];
                                    }
                                });

                                renameKey(item, '매장코드', 'storeCd');
                                renameKey(item, '그룹', 'branchCd');
                                renameKey(item, '팀별', 'momsTeam');
                                renameKey(item, 'AC점포별', 'momsAcShop');
                                renameKey(item, '지역구분', 'momsAreaFg');
                                renameKey(item, '상권', 'momsCommercial');
                                renameKey(item, '점포유형', 'momsShopType');
                                renameKey(item, '매장관리타입', 'momsStoreManageType');
                                renameKey(item, '매장그룹', 'momsStoreFg01');
                                renameKey(item, '매장그룹2', 'momsStoreFg02');
                                renameKey(item, '매장그룹3', 'momsStoreFg03');
                                renameKey(item, '매장그룹4', 'momsStoreFg04');
                                renameKey(item, '매장그룹5', 'momsStoreFg05');

                                // 공백, ' 제거
                                Object.keys(item).forEach(function(key){
                                    if (item[key] !== null && item[key] !== undefined && item[key] !== "") {
                                        if (typeof item[key] === 'string') {
                                            item[key] = item[key].trim().replaceAll('\'', '');
                                        }
                                    }
                                });
                            });

                            console.log(arr);
                            //console.log(JSON.stringify(arr, null, 2));

                            $timeout(function () {
                                $scope.save(arr);
                            }, 10);
                        });
                    } catch (e) {
                        // (2026.09.02) 손상 파일 등 파싱 실패 시 정리 (미처리 시 오버레이가 남아 화면 클릭 불능)
                        parseFailClear();
                        return false;
                    }
                };
                reader.readAsBinaryString(file);
            } else {
                $("#storeExcelUpFile").val('');
                $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};

            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue;
                }
            }

            jsonData.push(item);
        }

        $timeout(function () {
            $scope.save(jsonData);
        }, 10);
    };

    // DB에 저장
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
            url    : '/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 저장기능 수행후 재조회
                    $scope._broadcast('storeExcelUploadCtrl');
                }
            } else {
                // (2026.09.02) 응답 상태 실패(세션만료 등) 시 업로드 중단 및 정리
                $scope.uploadFailFg = true;
                $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                $scope.uploadFailClear();
            }
        }, function errorCallback(response) {
            // (2026.09.02) 업로드 실패 시 중단 및 정리
            //  - 그리드에 이전 업로드 내용이 남은 채 TEMP만 바뀐 상태로 저장되는 것 방지
            $scope.uploadFailFg = true;
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            $scope.uploadFailClear();
            // (2026.09.02) 통신 단절 오류는 response.data가 null이므로 가드 (미가드 시 TypeError로 팝업이 안 뜸)
            if (response.data && response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            // (2026.09.02) 실패 시에는 다음 청크를 호출하지 않음 (실패 청크만 누락된 채 계속 적재되는 것 방지)
            if (!$scope.uploadFailFg && parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.save(jsonData);
            }
        });
    };

    // (2026.09.02) 업로드 실패 시 정리
    //  - 그리드는 서버 재조회가 아닌 로컬 클리어로 즉시 비운다 (네트워크 단절 상태에서도 동작).
    //    그리드가 비면 저장 시 "엑셀업로드 된 데이터가 없습니다" 가드에 걸려 저장이 차단된다.
    //  - 부분 적재된 TEMP 삭제는 통신 가능할 때만 수행되는 보조 처리 (실패해도 다음 업로드 시작 시 deleteExl이 재삭제)
    $scope.uploadFailClear = function () {
        // 메인탭 그리드 로컬 클리어 (storeExcelUploadGridClear 리스너)
        $scope._broadcast('storeExcelUploadGridClear');
        // 부분 적재된 TEMP 삭제
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDeleteAll.sb", {}, function(){});
    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {

        // 전체 화면 클릭 차단 오버레이 (저장탭 JSP에 c:import 되어 같은 문서의 #loadingOverlay/핸들러를 공유)
        var overlay = document.getElementById('loadingOverlay');

        if (showFg) {
            // 우클릭 차단 등록
            document.addEventListener('contextmenu', contextMenuHandler);
            // 브라우저 닫기/새로고침 차단 등록
            window.addEventListener('beforeunload', beforeUnloadHandler);
            // 오버레이 활성화 (클릭 차단)
            if (overlay) overlay.classList.add('active');
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            // 우클릭 차단 해제
            document.removeEventListener('contextmenu', contextMenuHandler);
            // 브라우저 닫기/새로고침 차단 해제
            window.removeEventListener('beforeunload', beforeUnloadHandler);
            // 오버레이 비활성화
            if (overlay) overlay.classList.remove('active');
            $scope._loadingPopup.hide(true);
        }
    };

}]);