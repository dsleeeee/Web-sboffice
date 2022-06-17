/****************************************************************
 *
 * 파일명 : dlvrExcelUpload.js
 * 설  명 : 배달지엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.14     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 배달구분
var dlvrFgData = [
    {"name": "CALL", "value": "1"},
    {"name": "조리", "value": "2"},
    {"name": "배달", "value": "3"},
    {"name": "완료", "value": "4"},
    {"name": "취소", "value": "5"}
];

/**
 *  샘플양식 조회 그리드 생성
 */
app.controller('dlvrExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrExcelUploadCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.dlvrFgDataMap = new wijmo.grid.DataMap(dlvrFgData, 'value', 'name'); // 배달구분

        // 전체삭제
        $scope.delAll();

        // 그리드 셋팅
        $scope.searchDlvrExcelUploadDefault();
    };

    // <-- 검색 호출 -->
    $scope.$on("dlvrExcelUploadCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 그리드 셋팅
    $scope.searchDlvrExcelUploadDefault = function() {
        // 파라미터 설정
        var params = {};
        params.cidCallDt="20220101120000";
        params.cidLineNo="1";
        params.cidTelNo="0200000000";
        params.dlvrAddr="서울특별시 구로구 ";
        params.dlvrAddrDtl="7층";
        params.orderNo="0001";
        params.dlvrFg="CALL";
        params.dlvrMemo="";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 전체삭제
    $scope.delAll = function() {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", params, function(){});
    };

    // <-- 양식다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                '배달지엑셀업로드_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 배달지엑셀업로드 팝업
        $("#dlvrExcelUpFile").val('');
        $("#dlvrExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);

/**
 *  배달지목록 조회 그리드 생성
 */
app.controller('dlvrExcelUploadDlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrExcelUploadDlvrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.dlvrFgDataMap = new wijmo.grid.DataMap(dlvrFgData, 'value', 'name'); // 배달구분

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
    };

    // <-- 검색 호출 -->
    $scope.$on("dlvrExcelUploadDlvrCtrl", function(event, data) {
        $scope.searchDlvrExcelUploadDlvr();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchDlvrExcelUploadDlvr = function() {
        var params = {};

        $scope._inquiryMain("/dlvr/manage/info/dlvrExcelUpload/getDlvrExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {

        // 전체삭제
        var params = {};
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/dlvrExcelUpload/getDlvrExcelUploadDeleteAll.sb", params, function(){
            // 저장
            $scope.saveSave();
        });
    };

    // 저장
    $scope.saveSave = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["dlvrExcelUpload.saveBlank"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            // <-- 검증 -->
            var result = "";

            // 호출일시
            // if($scope.flex.collectionView.items[i].cidCallDt === "" || $scope.flex.collectionView.items[i].cidCallDt === null) {
            // } else {
            //     // 최대길이 체크
            //     if(nvl($scope.flex.collectionView.items[i].cidCallDt, '').getByteLengthForOracle() > 14) { result = messages["dlvrExcelUpload.cidCallDtLengthChk"]; } // 호출일시 길이가 너무 깁니다.
            // }

            // CID라인번호
            // if($scope.flex.collectionView.items[i].cidLineNo === "" || $scope.flex.collectionView.items[i].cidLineNo === null) {
            // } else {
            //     // 최대길이 체크
            //     if(nvl($scope.flex.collectionView.items[i].cidLineNo, '').getByteLengthForOracle() > 1) { result = messages["dlvrExcelUpload.cidLineNoLengthChk"]; } // CID라인번호 길이가 너무 깁니다.
            // }

            // CID전화번호
            if($scope.flex.collectionView.items[i].cidTelNo === "" || $scope.flex.collectionView.items[i].cidTelNo === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].cidTelNo, '').getByteLengthForOracle() > 15) { result = messages["dlvrExcelUpload.cidTelNoLengthChk"]; } // CID전화번호 길이가 너무 깁니다.

                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].cidTelNo)) {
                    $scope.flex.collectionView.items[i].cidTelNo = "";
                    result = messages["dlvrExcelUpload.cidTelNoInChk"]; // CID전화번호 숫자만 입력해주세요.
                }
            }

            // 배달주소
            if($scope.flex.collectionView.items[i].dlvrAddr === "" || $scope.flex.collectionView.items[i].dlvrAddr === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].dlvrAddr, '').getByteLengthForOracle() > 600) { result = messages["dlvrExcelUpload.dlvrAddrLengthChk"]; } // 배달주소 길이가 너무 깁니다.
            }

           // 배달주소상세
            if($scope.flex.collectionView.items[i].dlvrAddrDtl === "" || $scope.flex.collectionView.items[i].dlvrAddrDtl === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].dlvrAddrDtl, '').getByteLengthForOracle() > 600) { result = messages["dlvrExcelUpload.dlvrAddrDtlLengthChk"]; } // 배달주소상세 길이가 너무 깁니다.
            }

           // 주문번호
           //  if($scope.flex.collectionView.items[i].orderNo === "" || $scope.flex.collectionView.items[i].orderNo === null) {
           //  } else {
           //      // 최대길이 체크
           //      if(nvl($scope.flex.collectionView.items[i].orderNo, '').getByteLengthForOracle() > 4) { result = messages["dlvrExcelUpload.orderNolLengthChk"]; } // 주문번호 길이가 너무 깁니다.
           //  }

            // 배달구분
            // if($scope.flex.collectionView.items[i].dlvrFg === "" || $scope.flex.collectionView.items[i].dlvrFg === null) {
            //     $scope.flex.collectionView.items[i].dlvrFg = "1";
            // }

            // 배달메모
            if($scope.flex.collectionView.items[i].dlvrMemo === "" || $scope.flex.collectionView.items[i].dlvrMemo === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].dlvrMemo, '').getByteLengthForOracle() > 500) { result = messages["dlvrExcelUpload.dlvrMemoLengthChk"]; } // 배달메모 길이가 너무 깁니다.
            }

            $scope.flex.collectionView.items[i].result = result;
            // <-- //검증 -->

            params.push($scope.flex.collectionView.items[i]);
        }

        // 검증결과 저장
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/dlvrExcelUpload/getDlvrExcelUploadCheckSaveAdd.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기

            // 검증결과 조회
            $scope.searchDlvrExcelUploadDlvr();

            $scope._popConfirm(messages["dlvrExcelUpload.saveConfirm"], function() {
                // 배달지등록 저장
                $scope.dlvrExcelUploadSave();
            });
        });
    };

    // 배달지등록 저장
    $scope.dlvrExcelUploadSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/dlvr/manage/info/dlvrExcelUpload/getDeliveryTelNoSave.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증완료로 저장된 값 임시테이블에서 삭제
            $scope.dlvrExcelUploadDelete();
        });
    };

    // 검증완료로 저장된 값 임시테이블에서 삭제
    $scope.dlvrExcelUploadDelete = function (){
        // 파라미터 설정
        var params = {};

        $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/dlvrExcelUpload/getDlvrExcelUploadCheckDelete2.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증결과 조회
            $scope.searchDlvrExcelUploadDlvr();
        });
    }

    // <-- 엑셀다운로드 -->
    $scope.prodExcelDownload = function(){
        var column_binding;

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                '배달지엑셀업로드_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //양식다운로드 -->

    // <-- 그리드 행 삭제 -->
    $scope.delete = function(){
        $scope._popConfirm(messages["dlvrExcelUpload.delConfirm"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 삭제
            $scope.deleteSave();
        });
    };

    $scope.deleteSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/dlvrExcelUpload/getDlvrExcelUploadCheckDelete.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };
    // <-- //그리드 행 삭제 -->

}]);