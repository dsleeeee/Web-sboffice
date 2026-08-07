/****************************************************************
 *
 * 파일명 : dayTemporaryDtlSale.js
 * 설  명 : 맘스터치 > 매출분석2 > 일별 가승인 상세매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.05     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일별 가승인 상세매출 그리드 생성
 */
app.controller('dayTemporaryDtlSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속
    angular.extend(this, new RootController('dayTemporaryDtlSaleCtrl', $scope, $http, false));

    // 접속 사용자의 권한
    $scope.userOrgnFg = gvOrgnFg;

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    if($scope.userOrgnFg == "H"){

        $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);             // 매장브랜드
        $scope._setComboData("momsTeamCombo", momsTeamComboList);                       // 팀별
        $scope._setComboData("momsAcShopCombo", momsAcShopComboList);                   // AC점포별
        $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);                   // 지역구분
        $scope._setComboData("momsCommercialCombo", momsCommercialComboList);           // 상권
        $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);               // 점포유형
        $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
        $scope._setComboData("branchCdCombo", branchCdComboList);                       // 그룹
        $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
        $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
        $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
        $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
        $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("dayTemporaryDtlSaleCtrl", function (event, data) {

        // 조회
        $scope.searchDayTemporaryDtlSaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchDayTemporaryDtlSaleList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayTemporaryDtlSaleStoreCd").val();
        params.listScale = 500;

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/dayTemporaryDtlSale/getDayTemporaryDtlSaleList.sb", params, function () {

        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayTemporaryDtlSaleStoreCd").val();

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }
        params.excelType = excelType;

        if(params.excelType === '1') {
            // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
            $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                $scope._broadcast('dayTemporaryDtlSaleExcelCtrl', params);
            });
        }else{
            // 분할 엑셀다운로드 사용자 제한 체크
            $.postJSON('/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadUserIdChk.sb', params, function (response) {
                if (response.data.list === 0) {
                    $scope._popMsg(messages["prodSaleDayStoreMoms.userIdChkAlert"]); // 사용권한이 없습니다.
                    return;
                } else {
                    // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
                    $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                        $scope._broadcast('dayTemporaryDtlSaleExcelCtrl', params);
                    });
                }
            });
        }
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
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
                "일별 가승인 상세매출_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
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
app.controller('dayTemporaryDtlSaleExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTemporaryDtlSaleExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("dayTemporaryDtlSaleExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            $scope.searchExcelDivisionList(data);
        }
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
    };

    // 분할 엑셀 리스트 조회
    $scope.searchExcelDivisionList = function (params) {

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

        $.postJSON("/sale/anals/dayTemporaryDtlSale/getDayTemporaryDtlSaleList.sb", params, function(response){

            listSize = response.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/5000); // 하나의 엑셀파일에 5000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            };

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = totFileCnt; // 다운로드 파일수
            $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params, function(response){
                var seq = response.data.list; // 순번

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 5000개씩 지정해 분할 다운로드 진행
                        params.limit = 5000 * (x + 1);
                        params.offset = (5000 * (x + 1)) - 4999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // 엑셀다운로드 진행 사용자 저장 update
                        params.seq = seq;
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            // ajax 통신 설정
                            $.postJSON('/sale/anals/dayTemporaryDtlSale/getDayTemporaryDtlSaleList.sb', params, function(response) {

                                if(response.status === "FAIL") {
                                    s_alert.pop(response.message);
                                    $scope.excelFlex.itemsSource = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return;
                                }

                                var list = response.data.list;
                                if (list.length === undefined || list.length === 0) {
                                    $scope.data = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }

                                $scope.excelFlex.itemsSource = list;
                                $scope.excelFlex.itemsSource.trackChanges = true;

                                var data = new wijmo.collections.CollectionView(list);
                                data.trackChanges = true;
                                $scope.data = data;

                                setTimeout(function() {
                                    if ($scope.excelFlex.rows.length <= 0) {
                                        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                                        $scope.excelUploadingPopup(false);
                                        return false;
                                    }

                                    wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                        includeColumnHeaders: true,
                                        includeCellStyles: false,
                                        includeColumns: function (column) {
                                            return column.visible;
                                        }
                                    }, "일별 가승인 상세매출_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
                                        $timeout(function () {
                                            console.log("Export complete start. _" + (x + 1));
                                            getExcelFile(x + 1);
                                        }, 500);
                                    }, function (reason) { // onError
                                        // User can catch the failure reason in this callback.
                                        console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                        $scope.excelUploadingPopup(false);
                                    });

                                }, 1000);
                            });
                            resolve(x);

                        });
                    });
                };

                async function getExcelFile(x) {
                    if(totFileCnt > x){
                        await delay(x);
                    }else{
                        $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                    }
                };

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
