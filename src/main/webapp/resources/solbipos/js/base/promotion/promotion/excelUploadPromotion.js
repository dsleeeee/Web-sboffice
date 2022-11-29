/****************************************************************
 *
 * 파일명 : excelUploadPromotion.js
 * 설  명 : 프로모션 적용매장 엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('excelUploadPromotionCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('excelUploadPromotionCtrl', $scope, $http, true));

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
            }, '프로모션_적용매장_업로드_양식.xlsx');
        }, 10);
    };

    // 샘플양식생성
    $scope.addRow = function () {

        // 그리드 초기화
        var flex = $scope.flex;
        flex.itemsSource = new wijmo.collections.CollectionView();
        flex.collectionView.trackChanges = true;

        // 샘플양식에 값 넣기
        var params = {};
        params.storeCd = '0000001';
        params.storeNm = '샘플매장명';

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
                $scope._popMsg(messages['promotion.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['promotion.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
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
                    item["status"] = "I";
                    item["promotionCd"] = $("#hdPromotionCd").val();
                    item["verSerNo"] = $("#hdFileNo").val();
                }
            }
            jsonData.push(item);
        }
        $timeout(function () {

            // 기존 데이터 삭제
            var params = {};
            params.promotionCd = $("#hdPromotionCd").val();
            params.verSerNo = $("#hdFileNo").val();
            $scope._postJSONSave.withOutPopUp("/base/promotion/promotion/deletePromotionStoreAll.sb", params, function(){

                setTimeout(function() {
                    // 새 데이터 등록
                    $scope.save(jsonData);
                }, 500);
            });

        }, 10);
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

           // 매장코드 필수값 체크
           if(item.storeCd !== null && item.storeCd != ''){
               params.push(item);
           }
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/promotion/promotion/excelUploadPromotionStore.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {

                    //var parentScope = agrid.getScope($scope.parentCtrl);
                    //parentScope.uploadCallBack();

                    $scope._pageView('promotionSelectStoreGridCtrl', 1);
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
           var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['promotion.excelUploading'] + '</p>';
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

}]);