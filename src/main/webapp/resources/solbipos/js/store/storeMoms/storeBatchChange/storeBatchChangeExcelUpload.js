/****************************************************************
 *
 * 파일명 : storeBatchChangeExcelUpload.js
 * 설  명 : 매장정보엑셀업로드 JavaScript
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
 *  매장목록 샘플양식 조회 그리드 생성
 */
app.controller('storeBatchChangeExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeBatchChangeExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 그룹
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList2, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList2, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList2, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList2, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList2, 'value', 'name'); // 매장그룹5
    };

    // <-- 검색 호출 -->
    $scope.$on("storeBatchChangeExcelUploadCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // <-- 양식다운로드 -->
    $scope.sampleDownload = function(){

        var params = {};
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
            if(momsHqBrandCdComboList[i].value !== null) {
                momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
            }
        }
        params.userBrands = momsHqBrandCd;
        params.listScale = 5000;

        $scope._inquiryMain("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreList.sb", params, function (){
            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	false,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    '매장엑셀업로드_'+getCurDateTime()+'.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });

    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 엑셀업로드 팝업
        $("#storeExcelUpFile").val('');
        $("#storeExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);


/**
 *  매장목록 조회 그리드 생성
 */
app.controller('storeExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeExcelUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 그룹
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList2, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList2, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList2, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList2, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList2, 'value', 'name'); // 매장그룹5

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
    $scope.$on("storeExcelUploadCtrl", function(event, data) {
        $scope.searchStoreExcelUploadProd();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchStoreExcelUploadProd = function() {
        var params = {};

        $scope._inquiryMain("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {

        var params = new Array();
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.excelUploadData"]);	// 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 검증성공 이 아닌 데이터가 1개라도 있으면 저장 안함
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].result !== "검증성공") {
                $scope._popMsg(messages["storeBatchChange.noSaveConfirm"] + (i+1) + messages["storeBatchChange.noSaveConfirm2"]); // 검증성공이 아닌 데이터가 있습니다. <br/> 다시 업로드 해주세요. <br/> 3번째 줄
                return false;
            }

            if(nvl($scope.flex.collectionView.items[i].branchCd,'') !== nvl($scope.flex.collectionView.items[i].oldBranchCd,'')){
                $scope.flex.collectionView.items[i].storeFg = '1';
            }

            if(nvl($scope.flex.collectionView.items[i].momsTeam,'') !== nvl($scope.flex.collectionView.items[i].oldMomsTeam,'')
                || nvl($scope.flex.collectionView.items[i].momsAcShop,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAcShop,'')
                || nvl($scope.flex.collectionView.items[i].momsAreaFg,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAreaFg,'')
                || nvl($scope.flex.collectionView.items[i].momsCommercial,'') !== nvl($scope.flex.collectionView.items[i].oldMomsCommercial,'')
                || nvl($scope.flex.collectionView.items[i].momsShopType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsShopType,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreManageType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreManageType,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreFg01,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg01,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreFg02,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg02,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreFg03,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg03,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreFg04,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg04,'')
                || nvl($scope.flex.collectionView.items[i].momsStoreFg05,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg05,'')) {

                $scope.flex.collectionView.items[i].storeInfoFg = '1';
            }

            if($scope.flex.collectionView.items[i].branchCd !== $scope.flex.collectionView.items[i].oldBranchCd
                || $scope.flex.collectionView.items[i].momsTeam !== $scope.flex.collectionView.items[i].oldMomsTeam
                || $scope.flex.collectionView.items[i].momsAcShop !== $scope.flex.collectionView.items[i].oldMomsAcShop
                || $scope.flex.collectionView.items[i].momsAreaFg !== $scope.flex.collectionView.items[i].oldMomsAreaFg
                || $scope.flex.collectionView.items[i].momsCommercial !== $scope.flex.collectionView.items[i].oldMomsCommercial
                || $scope.flex.collectionView.items[i].momsShopType !== $scope.flex.collectionView.items[i].oldMomsShopType
                || $scope.flex.collectionView.items[i].momsStoreManageType !== $scope.flex.collectionView.items[i].oldMomsStoreManageType
                || $scope.flex.collectionView.items[i].momsStoreFg01 !== $scope.flex.collectionView.items[i].oldMomsStoreFg01
                || $scope.flex.collectionView.items[i].momsStoreFg02 !== $scope.flex.collectionView.items[i].oldMomsStoreFg02
                || $scope.flex.collectionView.items[i].momsStoreFg03 !== $scope.flex.collectionView.items[i].oldMomsStoreFg03
                || $scope.flex.collectionView.items[i].momsStoreFg04 !== $scope.flex.collectionView.items[i].oldMomsStoreFg04
                || $scope.flex.collectionView.items[i].momsStoreFg05 !== $scope.flex.collectionView.items[i].oldMomsStoreFg05) {

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if (params.length <= 0) {
            // 변경사항이 없습니다.
            $scope._popMsg(messages['cmm.not.modify']);
            return false;
        }

        // 그리드가 수정되면 저장 안함
        if($scope.flex.collectionView.itemsEdited.length > 0) {
            $scope._popMsg(messages["storeBatchChange.noSaveConfirm3"]); // 수정된 내역이 있습니다. <br/> 엑셀다운로드 후, 다시 엑셀업로드 해주세요.
            return false;
        }

        // 검증을 통과한 매장정보를 저장하시겠습니까?
        $scope._popConfirm(messages["storeBatchChange.saveConfirm"], function() {
            // 현재 세션ID 와 동일한 데이터 삭제
            $scope.deleteExl(params);
        });
    };

    // 현재 세션ID 와 동일한 데이터 삭제
    $scope.deleteExl = function (data) {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDeleteAll.sb", params, function(){
            // 변경된 값만 저장
            $scope.getDiffValSave(data);
        });
    };

    // 변경된 값만 저장
    $scope.getDiffValSave = function(data) {
        var params = data;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getDiffValSave.sb", params, function(){
            // 매장등록 저장
            $scope.storeExcelUploadSave(data);
        });
    };

    // 매장등록 저장
    $scope.storeExcelUploadSave = function(data) {

        // 파라미터 설정
        var params = new Array();

        // 다운로드 시작이면 작업내역 로딩 팝업 오픈
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        // 전체 데이터 수
        var listSize = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;

        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getTmpStoreList.sb", params, function(response){

            listSize = response.data.data.list[0].totCnt;

            if(listSize === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            };

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(listSize);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = listSize; // 다운로드 파일수
            $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getTmpStoreList.sb", params, function(response){

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        var proCnt = 0;
                        if(((x+1)*10) > listSize){
                            proCnt = listSize;
                        }else{
                            proCnt = (x+1) * 10
                        }
                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(proCnt);

                        // 페이징 50000개씩 지정해 분할 다운로드 진행
                        params.limit = 10 * (x + 1);
                        params.offset = (10 * (x + 1)) - 9;
                        params.storeFg = "";
                        params.storeInfoFg = "";

                        for (var i = 0; i < data.length; i++) {
                            if(data[i].storeFg === "1"){
                                params.storeFg += data[i].storeCd + ",";
                            }
                            if(data[i].storeInfoFg === "1"){
                                params.storeInfoFg += data[i].storeCd + ",";
                            }
                        }

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }


                        // ajax 통신 설정
                        $http({
                            method: 'POST', //방식
                            url: '/store/storeMoms/storeBatchChange/storeBatchChange/getSimpleSave.sb', /* 통신할 URL */
                            params: params, /* 파라메터로 보낼 데이터 */
                            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                        }).then(function successCallback(response) {
                            if ($scope._httpStatusCheck(response, true)) {
                                // this callback will be called asynchronously
                                // when the response is available

                                if(listSize <= ((x+1) * 10)){
                                    $scope._popMsg(messages['cmm.saveSucc']);
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }
                            }else if(response.data.status === 'SERVER_ERROR'){
                                $scope.excelUploadingPopup(false);
                                return false;
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
                                getExcelFile(x+1);
                            }, 1000);
                        });
                        resolve(x);
                    });
                };

                async function getExcelFile(x) {
                    if(listSize > (x * 10)){
                        await delay(x);
                    }else{
                    }
                };

                // 엑셀 분할 다운로드 시작
                getExcelFile(0);

            });
        });
    };

    $scope.deleteTmpStoreList = function (){
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDeleteAll.sb", params, function(){
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.saving'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 저장 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

    // <-- 엑셀다운로드 -->
    $scope.storeExcelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
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
                '매장정보일괄변경_엑셀다운로드_'+getCurDateTime()+'.xlsx',
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
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
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
        $scope._postJSONSave.withOutPopUp("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreExcelUploadCheckDelete.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };
    // <-- //그리드 행 삭제 -->

}]);