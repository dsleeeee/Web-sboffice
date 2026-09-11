/****************************************************************
 *
 * 파일명 : storeSoldOutHistoryBenson.js
 * 설  명 : (벤슨) 매장분석 > 매장품절현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장품절현황 그리드 생성
 */
app.controller('storeSoldOutHistoryBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSoldOutHistoryBensonCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeSoldOutHistoryBensonCtrl", function (event, data) {
        $scope.getSearchSoldOutHistory();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.getSearchSoldOutHistory = function () {
        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3일 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        // params.prodClassCd = $scope.prodClassCd;
        var prodClassCd = '';
        if($scope.prodClassCd !== '' && $scope.prodClassCd !== null && $scope.prodClassCd !== undefined) {
            for (var i = 0; i < $scope.prodClassCd.length; i++) {
                prodClassCd += (i == $scope.prodClassCd.length - 1) ? $scope.prodClassCd[i] : $scope.prodClassCd[i] + ',';
            }
        }
        params.prodClassCd = prodClassCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#storeSoldOutHistoryBensonStoreCd").val();
        params.prodCds = $("#storeSoldOutSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson/getSearchSoldOutHistory.sb", params, function (){

        });
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeSoldOutSelectShow = function () {
        $scope._broadcast('storeSoldOutSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {

        // 선택취소 값 전달
        $scope._broadcast('prodClassCheckPopUpCtrl', {
            selectCancelFg: $("#_selectCancelFg").val()
        });
        var popUp = $scope.prodClassCheckPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassCheckPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd[0];
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/treePopup/getProdClassCdNmCheck.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = (isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : "");
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
        $("#_selectCancelFg").val("Y");
    };

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 31일 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        // params.prodClassCd = $scope.prodClassCd;
        var prodClassCd = '';
        if($scope.prodClassCd !== '' && $scope.prodClassCd !== null && $scope.prodClassCd !== undefined) {
            for (var i = 0; i < $scope.prodClassCd.length; i++) {
                prodClassCd += (i == $scope.prodClassCd.length - 1) ? $scope.prodClassCd[i] : $scope.prodClassCd[i] + ',';
            }
        }
        params.prodClassCd = prodClassCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#storeSoldOutHistoryBensonStoreCd").val();
        params.prodCds = $("#storeSoldOutSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.excelType = excelType;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('storeSoldOutHistoryBensonExcelCtrl', params);
        });
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1일 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "매장품절현황" + '_' + wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeSoldOutHistoryBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSoldOutHistoryBensonExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeSoldOutHistoryBensonExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            // // 엑셀다운로드 진행 사용자 현재 인원수 체크
            // // 다운로드 구분 (0:간소화화면, 1:상품매출분석, 3:미스터피자_간소화화면, 4:미스터피자_상품매출분석)
            // if(hqOfficeCd === 'H0614' || hqOfficeCd === 'H0616'){
            //     data.downloadFg = "4";
            // }else {
            //     data.downloadFg = "1";
            // }
            // data.resrceCd = menuCd;
            // data.resrceNm = menuNm;
            // data.downloadUseFg = "2"; // 다운로드 사용기능 (0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드)
            // data.downloadNo = "3"; // 다운로드 화면구분번호
            //
            // $.postJSON('/sale/benson/prodSaleDayStoreBenson/prodSaleDayStoreBenson/getDivisionExcelDownloadCntChk.sb', data, function (response) {
            //     if (response.data.list === 0) {
            //     } else {
            //         var msgCntChk = response.data.list; // 00:0명의 사용자 다운로드 중
            //         if(msgCntChk.substr(0, 2) === "00") {
            //             $scope.searchExcelDivisionList(data);
            //         } else {
            //             // 엑셀다운로드 진행 사용자 저장 insert
            //             var params2 = data;
            //             params2.resrceNm = "실패:" + menuNm;
            //             params2.downloadFileCount = 0; // 다운로드 파일수
            //             $.postJSON("/sale/benson/prodSaleDayStoreBenson/prodSaleDayStoreBenson/getDivisionExcelDownloadSaveInsert.sb", params2, function(response){});
            //
            //             $scope._popMsg(msgCntChk); // 다운로드 사용량이 초과되어 대기중입니다. 잠시 후 다시 진행하여 주십시오.
            //
            //         }
            //     }
            // });

            // 분할 엑셀 리스트 조회
            $scope.searchExcelDivisionList(data);
        }
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson/getStoreSoldOutHistoryBensonExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "매장품절현황_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

    // 분할 엑셀 리스트 조회
    $scope.searchExcelDivisionList = function (data) {

        var params = {};
        var params = data;
        // 다운로드 시작이면 작업내역 로딩 팝업 오픈
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        // 전체 데이터 수
        var listSize = 0;
        // 다운로드 되는 총 엑셀파일 수
        var totFileCnt = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;
        $.postJSON( "/sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson/getSearchSoldOutHistory.sb", params, function(response){

            listSize = response.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/50000); // 하나의 엑셀파일에 50000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            }

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = totFileCnt; // 다운로드 파일수
            $.postJSON("/sale/benson/prodSaleDayStoreBenson/prodSaleDayStoreBenson/getDivisionExcelDownloadSaveInsert.sb", params, function(response){
                var seq = response.data.list; // 순번

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        //setTimeout(function() {
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 50000개씩 지정해 분할 다운로드 진행
                        params.limit = 50000 * (x + 1);
                        params.offset = (50000 * (x + 1)) - 49999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // 엑셀다운로드 진행 사용자 저장 update
                        params.seq = seq;
                        $.postJSON("/sale/benson/prodSaleDayStoreBenson/prodSaleDayStoreBenson/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            // ajax 통신 설정
                            $.postJSON("/sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson/getSearchSoldOutHistory.sb", params, function(response) {

                                    // <-- 그리드 visible -->
                                    // 선택한 테이블에 따른 리스트 항목 visible
                                    var grid = wijmo.Control.getControl("#wjGridExcelList");
                                    var columns = grid.columns;

                                    if(response.status === "FAIL") {
                                        s_alert.pop(response.message);
                                        grid.itemsSource = new wijmo.collections.CollectionView([]);
                                        return;
                                    }


                                    grid.itemsSource = response.data.list;
                                    grid.itemsSource.trackChanges = true;

                                    // var list = response.data.list;
                                    // var data = new wijmo.collections.CollectionView(list);
                                    // data.trackChanges = true;
                                    // $scope.data = data;

                                    wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                        includeColumnHeaders: true,
                                        includeCellStyles: false,
                                        includeColumns: function (column) {
                                            return column.visible;
                                        }
                                    }, "매장품절현황_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
                                        $timeout(function () {
                                            console.log("Export complete start. _" + (x + 1));
                                            getExcelFile(x + 1);
                                        }, 500);
                                    }, function (reason) { // onError
                                        // User can catch the failure reason in this callback.
                                        console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                        $scope.excelUploadingPopup(false);
                                    });

                                },
                                function(result){
                                    s_alert.pop(result.message);
                                }
                            );
                        });
                        resolve(x);

                    });
                    //}, 3000*x);
                }

                async function getExcelFile(x) {
                    if(totFileCnt > x){
                        await delay(x);
                    }else{
                        $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                    }
                }

                // 엑셀 분할 다운로드 시작
                getExcelFile(0);

            });
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 다운로드 진행 중...</div>';
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
