/****************************************************************
 *
 * 파일명 : splyPriceExcepUpload.js
 * 설  명 : 기초관리 > 가격관리 > 공급가관리 > 공급가관리 엑셀업로드 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.04     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 양식다운로드
 */
app.controller('splyPriceExcelUploadSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('splyPriceExcelUploadSampleCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("excelStoreSaveFg", storeSaveFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("splyPriceExcelUploadSampleCtrl", function (event, data) {
        event.preventDefault();
    });

    // 양식다운로드
    $scope.sampleDownload = function () {
        var params = {};

        $scope._inquiryMain("/base/price/splyPrice/getHqSplyPriceExcelUploadSampleList.sb", params, function () {
            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex,
                    {
                        includeColumnHeaders: true,
                        includeCellStyles: false,
                        includeColumns: function (column) {
                            return column.visible;
                        }
                    },
                    messages["splyPrice.splyPrice"] + '_' + messages["splyPrice.splyPriceExcelUpload"] + '_양식_' + getCurDateTime() + '.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        // 엑셀업로드 팝업
        $("#splyPriceExcelUpFile").val('');
        $("#splyPriceExcelUpFile").trigger('click');
    };

}]);

/**
 *  공급가관리 엑셀업로드 그리드
 */
app.controller('splyPriceExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('splyPriceExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 검증결과
                if (col.binding === "result") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (item[("result")] !== '검증전' && item[("result")] !== '검증성공') {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "splyUprc") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("splyPriceExcelUploadCtrl", function (event, data) {
        // 공급가 업로드 임시테이블 데이터 조회
        $scope.searchSplyPriceExcelUpload();
        event.preventDefault();
    });

    // 공급가 업로드 임시테이블 데이터 조회
    $scope.searchSplyPriceExcelUpload = function () {
        var params = {};

        $scope._inquiryMain("/base/price/splyPrice/getSplyPriceExcelUploadCheckList.sb", params, function () {}, false);
    };

    // 저장 적용타입 선택
    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "choice") {
            $("#excelStoreSaveStore").show();
        } else {
            $("#excelStoreSaveStore").hide();
        }
    };

    // 저장버튼 클릭하여 검증
    $scope.save = function () {

        if ($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
            if ($("#excelChoiceSaveStoreCd").val() === "") {
                $scope._popMsg("매장을 선택해주세요");
                return false;
            }
        }

        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/splyPrice/deleteSplyPriceExcelUploadCheckAll.sb", params, function () {

            $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
            $scope.progressCnt = 0; // 처리된 숫자

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                // <-- 검증 -->
                var result = "";
                $scope.flex.collectionView.items[i].result = result;
                $scope.flex.collectionView.items[i].applyFg = $scope.excelStoreSaveFgCombo.selectedValue;
                if ($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
                    if ($("#excelChoiceSaveStoreCd").val() === "") {
                        $scope._popMsg("매장을 선택해주세요");
                        return false;
                    }
                    $scope.flex.collectionView.items[i].saveStoreCds = $("#excelChoiceSaveStoreCd").val();
                }
                params.push($scope.flex.collectionView.items[i]);
            }

            // 데이터 검증
            $scope.saveSave(params);
        });
    };

    // 데이터 검증
    $scope.saveSave = function (jsonData) {

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
        if (document.getElementsByName('sessionId')[0]) {
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/base/price/splyPrice/saveSplyPriceExcelUploadCheckResult.sb', /* 통신할 URL */
            data: params, /* 파라메터로 보낼 데이터 : @requestBody */
            params: sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 검증결과 조회
                    $scope.searchSplyPriceExcelUpload();

                    // 검증을 통과한 공급가를 저장하시겠습니까?
                    var msg = messages["splyPrice.saveConfirm"];

                    // 전매장적용
                    if ($scope.excelStoreSaveFgCombo.selectedValue !== 'none') {
                        // 전체 매장에, 검증을 통과한 공급가를 저장하시겠습니까?
                        msg = messages["splyPrice.saveConfirm2"];
                    }

                    $scope._popConfirm(msg, function () {
                        // 공급가 저장
                        $scope.splyPriceExcelUploadSave();
                    });
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
                $scope.saveSave(jsonData);
            }
        });
    };

    // 공급가 저장
    $scope.splyPriceExcelUploadSave = function () {

        $scope.stepCnt = 100;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].applyFg = $scope.excelStoreSaveFgCombo.selectedValue;
            if ($scope.excelStoreSaveFgCombo.selectedValue === "choice") {
                if ($("#excelChoiceSaveStoreCd").val() === "") {
                    $scope._popMsg("매장을 선택해주세요");
                    return false;
                }
                $scope.flex.collectionView.items[i].saveStoreCds = $("#excelChoiceSaveStoreCd").val();
            }

            params.push($scope.flex.collectionView.items[i]);
        }

        $timeout(function () {
            setTimeout(function () {
                // 저장
                $scope.save2(params);
            }, 500);
        }, 10);
    };

    // 저장
    $scope.save2 = function (orgParams) {

        $scope.totalRows = orgParams.length;    // 체크수
        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
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
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if (document.getElementsByName('sessionId')[0]) {
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/base/price/splyPrice/saveHqSplyPriceExcelUpload.sb', /* 통신할 URL */
            data: params, /* 파라메터로 보낼 데이터 : @requestBody */
            params: sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 검증결과 조회
                    $scope.searchSplyPriceExcelUpload();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
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
                $scope.save2(orgParams);
            }
        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex,
                {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                messages["splyPrice.splyPrice"] + '_' + messages["splyPrice.splyPriceExcelUpload"] + '_' + getCurDateTime() + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

    // 삭제
    $scope.delete = function () {
        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];

                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 삭제
            $scope.deleteSave();
        });
    };

    // 삭제
    $scope.deleteSave = function () {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/splyPrice/deleteSplyPriceExcelUploadCheck.sb", params, function () {
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };

    // 공급가 수정시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.excelChoiceSaveStoreShow = function () {
        $scope._broadcast('excelChoiceSaveStoreCtrl');
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
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

/**
 * 엑셀업로드
 */
app.controller('splyPriceExcelUploadAddCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('splyPriceExcelUploadAddCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    $scope.$on("splyPriceExcelUploadAddCtrl", function (event, data) {
        event.preventDefault();
    });

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        // 엑셀업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
        $scope.deleteExl();
    };

    // 현재 세션ID 와 동일한 데이터 삭제
    $scope.deleteExl = function () {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/price/splyPrice/deleteSplyPriceExcelUploadCheckAll.sb", params, function () {
            // 엑셀 업로드
            $scope.excelUpload();
        });
    };

    // 공급가 업로드 임시테이블 저장
    $scope.excelUpload = function () {

        $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#splyPriceExcelUpFile')[0].files[0]) {
            var file          = $('#splyPriceExcelUpFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#splyPriceExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#splyPriceExcelUpFile").val('');
                $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData = [];
        var item = {};
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
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '').replaceAll(' ', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue.replaceAll('\'', '');
                }
            }

            jsonData.push(item);
        }

        $timeout(function () {
            $scope.save(jsonData);
        }, 10);
    };

    // 임시테이블 DB에 저장
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
            url    : '/base/price/splyPrice/saveSplyPriceExcelUploadCheck.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 저장기능 수행후 재조회
                    $scope._broadcast('splyPriceExcelUploadCtrl');
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