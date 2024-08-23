/****************************************************************
 *
 * 파일명 : orderTimeTrackingMenu.js
 * 설  명 : 주문시간트레킹-메뉴별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 수락구분
var acceptedFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

// 완료구분
var completedFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

// 취소구분
var canceledFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

/**
 *  주문시간트레킹 생성
 */
app.controller('orderTimeTrackingMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderTimeTrackingMenuCtrl', $scope, $http, false));

    $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.acceptedFgDataMap = new wijmo.grid.DataMap(acceptedFg, 'value', 'name');   // 수락구분
        $scope.completedFgDataMap = new wijmo.grid.DataMap(completedFg, 'value', 'name'); // 완료구분
        $scope.canceledFgDataMap = new wijmo.grid.DataMap(canceledFg, 'value', 'name');   // 취소구분
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("orderTimeTrackingMenuCtrl", function (event, data) {
        $scope.srchOrderTimeTrackingMenuList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    //
    $scope.srchOrderTimeTrackingMenuList = function () {

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.storeCds   = $("#orderTimeTrackingMenuStoreCd").val();
        params.listScale = 500;

        if(orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var momsHqBrandCd = "";
                for(var i=0; i < momsHqBrandCdComboList.length; i++){
                    if(momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu/getOrderTimeTrackingMenuList.sb", params, function (){});
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.storeCds   = $("#orderTimeTrackingMenuStoreCd").val();

        if(orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var momsHqBrandCd = "";
                for(var i=0; i < momsHqBrandCdComboList.length; i++){
                    if(momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        console.log(params);

        $scope._broadcast('orderTimeTrackingMenuExcelCtrl',params);
    };

}]);


/**
 *  주문시간트레킹 엑셀다운로드 생성
 */

app.controller('orderTimeTrackingMenuExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderTimeTrackingMenuExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.acceptedFgDataMap = new wijmo.grid.DataMap(acceptedFg, 'value', 'name');   // 수락구분
        $scope.completedFgDataMap = new wijmo.grid.DataMap(completedFg, 'value', 'name'); // 완료구분
        $scope.canceledFgDataMap = new wijmo.grid.DataMap(canceledFg, 'value', 'name');   // 취소구분
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5
    };

    // 다른 컨트롤러의 orderTimeTrackingMenuExcelCtrl 받기
    $scope.$on("orderTimeTrackingMenuExcelCtrl", function (event, data) {
        $scope.searchExcelDivisionList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

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

        $scope._postJSONQuery.withOutPopUp("/sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu/getOrderTimeTrackingMenuList.sb", params, function(response){

            listSize = response.data.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/20000); // 하나의 엑셀파일에 50000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            };

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = totFileCnt; // 다운로드 파일수
            $scope._postJSONQuery.withOutPopUp("/sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu/getOrderTimeTrackingMenuList.sb", params, function(response){

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 50000개씩 지정해 분할 다운로드 진행
                        params.limit = 20000 * (x + 1);
                        params.offset = (20000 * (x + 1)) - 19999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }


                        // ajax 통신 설정
                        $http({
                            method: 'POST', //방식
                            url: '/sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu/getOrderTimeTrackingMenuList.sb', /* 통신할 URL */
                            params: params, /* 파라메터로 보낼 데이터 */
                            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                        }).then(function successCallback(response) {
                            if ($scope._httpStatusCheck(response, true)) {
                                // this callback will be called asynchronously
                                // when the response is available
                                var list = response.data.data.list;
                                if (list.length === undefined || list.length === 0) {
                                    $scope.data = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }

                                var data = new wijmo.collections.CollectionView(list);
                                data.trackChanges = true;
                                $scope.data = data;
                            }
                        }, function errorCallback(response) {
                            // 로딩팝업 hide
                            $scope.excelUploadingPopup(false);
                            // called asynchronously if an error occurs
                            // or server returns response with an error status.
                            if (response.data.message) {
                                $scope._popMsg(response.data.message);
                            } else {
                                $scope._popMsg(messages['cmm.error']);
                            }
                            return false;
                        }).then(function () {
                            // 'complete' code here
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
                                }, "주문시간트레킹-메뉴별_" + params.startDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
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

    // 엑셀 다운로드
    // $scope.srchOrderTimeTrackingMenuExcelList = function (params) {
    //
    //     // 조회 수행 : 조회URL, 파라미터, 콜백함수
    //     $scope._inquiryMain("/sale/anals/orderTimeTrackingMenu/orderTimeTrackingMenu/getOrderTimeTrackingMenuExcelList.sb", params, function() {
    //         if ($scope.excelFlex.rows.length <= 0) {
    //             $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
    //             return false;
    //         }
    //
    //         $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    //         $timeout(function () {
    //             wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
    //                 includeColumnHeaders: true,
    //                 includeCellStyles   : false,
    //                 includeColumns      : function (column) {
    //                     return column.visible;
    //                 }
    //             }, messages["orderTimeTracking.orderTimeTrackingMenu"] + '_' + params.startDate + '_' + getCurDateTime()+'.xlsx', function () {
    //                 $timeout(function () {
    //                     $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
    //                 }, 10);
    //             });
    //         }, 10);
    //     });
    // };


}]);