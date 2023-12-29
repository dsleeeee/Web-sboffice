/****************************************************************
 *
 * 파일명 : popUpTouchKeyEnv.js
 * 설  명 : 터치키 그룹코드 적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.08     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('popUpTouchKeyEnvCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('popUpTouchKeyEnvCtrl', $scope, $http, false));

    $scope._setComboData("srchEnvSysStatFg", sysStatFg);
    $scope._setComboData("touchKeyEnvCombo", touchKeyGrpData);
    $scope._setComboData("tukeyGrpCombo2", tukeyGrpData);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.grpDataMap = new wijmo.grid.DataMap(touchKeyGrpData, 'value', 'name');
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("popUpTouchKeyEnvCtrl", function(event, key) {

        $scope._setComboData("touchKeyEnvCombo", touchKeyGrpData);

        // 매장 포스 터치키 그룹 코드 조회
        $scope.searchPos();
        event.preventDefault();

    });

    // 매장 포스 터치키 그룹 코드 조회
    $scope.searchPos = function(){

        var params = {};
        params.storeCd = $("#srchEnvStoreCd").val();
        params.storeNm = $("#srchEnvStoreNm").val();
        params.momsEnvstVal = momsEnvstVal;
        params.tukeyGrpCd = $scope.tukeyGrp;
        if(momsEnvstVal === "1" && orgnFg === "HQ") {
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.storeHqBrandCd = $scope.storeHqBrandCd;
            // '전체' 일때
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var momsHqBrandCd = "";
                for (var i = 0; i < brandList.length; i++) {
                    if (brandList[i].value !== null) {
                        momsHqBrandCd += brandList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
        } else if(brandUseFg === "1" && orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.storeHqBrandCd;
        }
        console.log(params);

        $scope._inquirySub("/base/prod/touchKey/touchKey/getTouchKeyEnv.sb", params, function () {});
    };

    // 적용
    $scope.btnInsertEnv = function () {

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        // 키맵그룹이 없습니다.
        if ($scope.touchKeyEnvCombo.selectedValue === "" || $scope.touchKeyEnvCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }
        // 키맵그룹이 없습니다.
        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk === true) {
                if(item.posNo !== null && item.posNo !== "") {
                    item.envstVal = $scope.touchKeyEnvCombo.selectedValue;

                    // 키맵매장적용 여부
                    if($("#chkApplyStore").is(":checked") && orgnFg === "HQ"){
                        item.chkApplyStore = "Y";
                        item.tukeyGrpCd = $scope.touchKeyEnvCombo.selectedValue;
                    }else{
                        item.chkApplyStore = "N";
                    }
                    params.push(item);
                }
            }
        }
        console.log(params);

        // 사용터치키 설정을 하시겠습니까?(선택매장이 많은경우, 오래걸릴수 있습니다.)
        $scope._popConfirm(messages["touchKey.touchKeyEnvToStore.msg"], function() {
            $timeout(function () {
                setTimeout(function () {
                    // 매장사용터치키 설정 저장
                    $scope.save(params);
                }, 500);
            }, 10);
        });
    };

    // 매장사용터치키 설정 저장
    $scope.save = function(orgParams){

        $scope.totalRows = orgParams.length;    // 체크 매장수
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
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/prod/touchKey/touchKey/saveTouchKeyEnv.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // 매장사용터치키설정 팝업 닫기
                    $scope.popUpTouchKeyEnvLayer.hide(true);
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
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
                $scope.save(orgParams);
            }
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['touchKey.loading.msg'] + '</p>';
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

    // 닫기
    $scope.close = function () {
        if(orgnFg === "HQ"){
            $("#srchEnvStoreCd").val("");
            $("#srchEnvStoreNm").val("");
            $scope.srchEnvSysStatFgCombo.selectedIndex = 0;
        }
        $scope.touchKeyEnvCombo.selectedIndex = 0;
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeEnv = function(){
        if( $("#tblSearchAddShowEnv").css("display") === 'none') {
            $("#tblSearchAddShowEnv").show();
        } else {
            $("#tblSearchAddShowEnv").hide();
        }
    };

    // 엑셀다운로드 -->
    $scope.excelDownload = function(){
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
                },messages["touchKey.storeUseTouchKey"]+ '_'+ getCurDateTime() +'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);