/****************************************************************
 *
 * 파일명 : naverPlaceEasyLink.js
 * 설  명 : 네이버플레이스 간편연동 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var inTypeComboData = [
    {"name":"전체","value":""},
    {"name":"링크연동","value":"LYNK"},
    {"name":"간편연동","value":"NAVER"}
];

/**
 *  간편연동업로드 - 사용자현황 그리드
 */
app.controller('naverPlaceEasyLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceEasyLinkCtrl', $scope, $http, false));

    $scope._setComboData("inType", inTypeComboData); // 구분 콤보박스

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태
        $scope.inTypeDataMap = new wijmo.grid.DataMap(inTypeComboData, 'value', 'name'); // 구분
    };

    $scope.$on("naverPlaceEasyLinkCtrl", function (event, data) {

        // 조회
        $scope.getNaverPlaceUserList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.getNaverPlaceUserList = function () {

        var params = {};
        params.inType = $scope.inTypeCombo.selectedValue;
        params.bizNo = $("#srchBizNo").val();

        $scope._inquiryMain("/sys/link/naverPlaceEasyLink/getNaverPlaceUserList.sb", params, function() {
        }, false);
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, '네이버플레이스 간편연동 등록(현황)_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }
    
}]);

/**
 *  간편연동업로드 - 양식다운로드 그리드
 */
app.controller('naverPlaceEasyLinkExcelSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceEasyLinkExcelSampleCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 샘플 1건 추가
        $scope._addRow({
            placeId        : '12345678',
            businessName   : '링크카페',
            businessNumber : '1234567890'
        });
    };

    // 양식다운로드
    $scope.excelSampleDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); //다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, '네이버플레이스 간편연동 등록(양식)_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 간편연동업로드
    $scope.excelUpload = function () {
        // 엑셀업로드 팝업
        $("#excelUpFile").val('');
        $("#excelUpFile").trigger('click');
    };
}]);

/**
 *  간편연동업로드 - 검증결과 조회 그리드
 */
app.controller('naverPlaceEasyLinkExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceEasyLinkExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태

        // 검증결과 셀 스타일(실패 항목 빨간색 표시)
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "result") {
                    var item = s.rows[e.row].dataItem;
                    if (item["result"] !== "검증전" && item["result"] !== "정상") {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });
    };

    $scope.$on("naverPlaceEasyLinkExcelCtrl", function (event, data) {

        // 조회
        $scope.searchNaverPlaceEasyLinkExcel();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 간편연동 업로드(임시저장) 검증결과 조회
    $scope.searchNaverPlaceEasyLinkExcel = function () {

        var params = {};
        $scope._inquiryMain("/sys/link/naverPlaceEasyLink/getNaverPlaceEasyLinkExcelList.sb", params, function () {

        }, false);
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); //다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.binding != 'gChk';
                }
            }, '네이버플레이스 간편연동 등록(업로드내역)_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 간편연동 업로드 임시데이터 개별 삭제
    $scope.delete = function () {
        if ($scope.flex.rows.length <= 0) {
            return false;
        }

        var items = $scope.flex.collectionView.items;
        var params = [];
        for (var i = 0; i < items.length; i++) {
            if (items[i].gChk) {
                params.push(items[i]);
            }
        }

        if (params.length <= 0) {
            $scope._popMsg("선택된 항목이 없습니다.");
            return false;
        }

        $scope._postJSONSave.withOutPopUp("/sys/link/naverPlaceEasyLink/deleteNaverPlaceEasyLinkExcel.sb", params, function () {
            // 삭제기능 수행후 재조회
            $scope._broadcast('naverPlaceEasyLinkExcelCtrl');
        });
    };

    // 간편연동 업로드 정상(검증성공) 데이터 저장
    $scope.save = function () {

        // 검증결과 [정상] 인 정보만 연동 처리됩니다.
        $scope._popConfirm(messages["naverPlaceEasyLink.save.confirm.msg"], function() {

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["prodExcelUpload.saveBlank"]);
                return false;
            }

            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].result === "정상") {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if (params.length <= 0) {
                $scope._popMsg("업로드 할 데이터가 없습니다.");
                return false;
            }

            // 저장 전, 그리드에 있는 내용 엑셀 자동 다운로드
            $scope.excelDownload();

            $scope._postJSONSave.withPopUp("/sys/link/naverPlaceEasyLink/saveNaverPlaceEasyLinkExcel.sb", params, function () {
                // 저장기능 수행후 재조회
                $scope._broadcast('naverPlaceEasyLinkCtrl');
                $scope._broadcast('naverPlaceEasyLinkExcelCtrl');
            });
        });
    };

}]);

/**
 *  간편연동업로드 - 엑셀파일 읽기/임시저장
 */
app.controller('naverPlaceEasyLinkExcelUploadAddCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceEasyLinkExcelUploadAddCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("naverPlaceEasyLinkExcelUploadAddCtrl", function (event, data) {
        event.preventDefault();
    });

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        // 엑셀업로드 전 현재 세션ID 와 동일한 기존 임시데이터를 삭제한다.
        $scope.deleteExl();
    };

    // 현재 세션ID 와 동일한 임시데이터 삭제
    $scope.deleteExl = function () {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/sys/link/naverPlaceEasyLink/deleteNaverPlaceEasyLinkExcelAll.sb", params, function () {
            $scope.excelUpload();
        });
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#excelUpFile')[0].files[0]) {
            var file          = $('#excelUpFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                // excel file read
                var reader = new FileReader();
                var arr = [];
                reader.onload = function () {
                    var fileData = reader.result;
                    var wb = XLSX.read(fileData, {type: 'binary'});
                    wb.SheetNames.forEach(function (sheetName) {
                        arr = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);

                        // key명 변경
                        arr.forEach(function (item) {
                            // 엑셀 헤더 key 공백 제거
                            Object.keys(item).forEach(function (key) {
                                var trimmedKey = key.trim();
                                if (trimmedKey !== key) {
                                    item[trimmedKey] = item[key];
                                    delete item[key];
                                }
                            });

                            renameKey(item, 'place_id[네이버 플레이스ID]', 'placeId');
                            renameKey(item, 'business_name[업체명]', 'businessName');
                            renameKey(item, 'business_number[사업자번호]', 'businessNumber');

                            // 공백, ' 제거
                            Object.keys(item).forEach(function (key) {
                                if (item[key] !== null && item[key] !== undefined && item[key] !== "") {
                                    if (typeof item[key] === 'string') {
                                        item[key] = item[key].trim().replaceAll('\'', '');
                                    } else {
                                        item[key] = item[key] + '';
                                    }
                                }
                            });

                            // 사업자번호 - 숫자 이외 문자(하이픈 등) 제거
                            if (item.businessNumber !== null && item.businessNumber !== undefined) {
                                item.businessNumber = (item.businessNumber + '').replace(/[^0-9]/g, '');
                            }
                        });

                        $timeout(function () {
                            $scope.save(arr);
                        }, 10);
                    });
                };
                reader.readAsBinaryString(file);

            } else {
                $("#excelUpFile").val('');
                $scope._popMsg(messages['prodExcelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 간편연동 업로드 검증 및 임시데이터 저장
    $scope.save = function (jsonData) {

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
            // 서버에서 SEQ 계산 시 사용(진행 갯수 + 반복순번)
            jsonData[i].progressCnt = $scope.progressCnt;
            params.push(jsonData[i]);
        }

        $http({
            method : 'POST',
            url    : '/sys/link/naverPlaceEasyLink/saveNaverPlaceEasyLinkExcelCheck.sb',
            data   : params,
            headers: {'Content-Type': 'application/json; charset=utf-8'}
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 저장기능 수행후 재조회
                    $scope._broadcast('naverPlaceEasyLinkExcelCtrl');
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data && response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                $scope.progressCnt = loopCnt;
                $("#progressCnt").html($scope.progressCnt);
                $scope.save(jsonData);
            } else {
                $scope.progressCnt = 0; // 다음 업로드를 위해 초기화
            }
        });
    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            $scope._loadingPopup.content.innerHTML = innerHtml;
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);
