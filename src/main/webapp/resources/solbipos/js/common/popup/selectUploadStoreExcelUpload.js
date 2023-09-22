/****************************************************************
 *
 * 파일명 : selectUploadStoreExcelUpload.js
 * 설  명 : 공용 업로드매장 팝업 - 엑셀업로드 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  엑셀업로드 팝업 조회 그리드 생성
 */
app.controller('selectUploadStoreExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('selectUploadStoreExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.selectUploadStoreExcelUploadFlex.columns.length; i++) {
            var col = $scope.selectUploadStoreExcelUploadFlex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("selectUploadStoreExcelUploadCtrl", function(event, data) {
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
        $scope._postJSONSave.withOutPopUp("/common/popup/selectStore/getSelectUploadStoreExcelUploadDeleteAll.sb", params, function(){
            // 엑셀 업로드
            $scope.excelUpload();
        });
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#selectUploadStoreExcelUploadFile')[0].files[0]) {
            var file          = $('#selectUploadStoreExcelUploadFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.selectUploadStoreExcelUploadFlex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#selectUploadStoreExcelUploadFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#selectUploadStoreExcelUploadFile").val('');
                $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.selectUploadStoreExcelUploadFlex.rows.length;

        if (rowLength == 0) {
            $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};

            for (var c = 0; c < $scope.selectUploadStoreExcelUploadFlex.columns.length; c++) {
                // alert($scope.selectUploadStoreExcelUploadFlex.columns[c].header); // 매장코드 / null
                // alert($scope.selectUploadStoreExcelUploadFlex.getCellData(r, c, false)); // 00001 / 매장코드
                // alert($scope.colHeaderBind[$scope.selectUploadStoreExcelUploadFlex.columns[c].header]); // storeCd / undefined

                // 빈 로우 추가 업로드시 오류
                if ($scope.selectUploadStoreExcelUploadFlex.columns[c].header !== null) {
                    if ($scope.selectUploadStoreExcelUploadFlex.columns[c].header !== null && $scope.selectUploadStoreExcelUploadFlex.getCellData(r, c, false) !== null) {
                        var colBinding = $scope.colHeaderBind[$scope.selectUploadStoreExcelUploadFlex.columns[c].header];
                        var cellValue  = $scope.selectUploadStoreExcelUploadFlex.getCellData(r, c, false) + '';
                        item[colBinding] = cellValue;
                    }
                }
            }

            // 전체 배열 확인
            // var itemFullChk = JSON.stringify(item);
            // alert(itemFullChk);

            // 배열이 비어있는지 확인
            var itemChk = Object.keys(item).length;
            // alert(itemChk);
            if (itemChk !== 0) {
                jsonData.push(item);
            }
        }
        // alert(jsonData.length);

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
            url    : '/common/popup/selectStore/getSelectUploadStoreExcelUploadSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 저장기능 수행후 재조회
                    var scope = agrid.getScope($("#selectUploadStoreExcelUploadTargetId").text() + 'Ctrl');
                    scope.searchStoreUpload();
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