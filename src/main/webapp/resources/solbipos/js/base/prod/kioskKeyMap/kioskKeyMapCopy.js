/****************************************************************
 *
 * 파일명 : kioskKeyMapCopy.js
 * 설  명 : 키오스크키맵복사 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgMapEnvComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

// 기준매장포스
app.controller('kioskKeyMapCopyCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapCopyCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd1", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("srchMomsTeam1", momsTeamComboList); // 팀별
    $scope._setComboData("srchMomsAcShop1", momsAcShopComboList); // AC점포별
    $scope._setComboData("srchMomsAreaFg1", momsAreaFgComboList); // 지역구분
    $scope._setComboData("srchMomsCommercial1", momsCommercialComboList); // 상권
    $scope._setComboData("srchMomsShopType1", momsShopTypeComboList); // 점포유형
    $scope._setComboData("srchMomsStoreManageType1", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd1", branchCdComboList); // 그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgMapEnvComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 기준매장포스 매장코드 클릭
                if ( col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var scope = agrid.getScope('kioskKeyMapCopy2Ctrl');

                    // 선택한 기준매장 키오스크 포스 정보 셋팅
                    scope.setOrgStoreInfo(selectedRow);

                    // 적용대상매장포스 조회
                    scope.searchTargetStoreList();
                    event.preventDefault();
                }
            }
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 기준매장 키오스크 포스 조회
    $scope.$on("kioskKeyMapCopyCtrl", function(event, data) {
        // 기준매장 키오스크 포스 조회
        $scope.searchOrgStoreList();
        event.preventDefault();
    });

    // 기준매장 키오스크 포스 조회
    $scope.searchOrgStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd1").val();
        params.storeNm = $("#srchStoreNm1").val();
        params.originalStoreCd = "";
        params.orgPosNo = "";

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeam1Combo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShop1Combo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFg1Combo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercial1Combo.selectedValue;
            params.momsShopType = $scope.srchMomsShopType1Combo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageType1Combo.selectedValue;
            params.branchCd = $scope.srchBranchCd1Combo.selectedValue;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){ // 본사이면서 브랜드사용시만 검색가능

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd1Combo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreCopyKioskPosList.sb", params, function () {
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeCopy = function(){
        if( $("#tblSearchAddShowCopy").css("display") === 'none') {
            $("#tblSearchAddShowCopy").show();
        } else {
            $("#tblSearchAddShowCopy").hide();
        }
    };

}]);

// 적용대상매장포스
app.controller('kioskKeyMapCopy2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('kioskKeyMapCopy2Ctrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd2", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("srchMomsTeam2", momsTeamComboList); // 팀별
    $scope._setComboData("srchMomsAcShop2", momsAcShopComboList); // AC점포별
    $scope._setComboData("srchMomsAreaFg2", momsAreaFgComboList); // 지역구분
    $scope._setComboData("srchMomsCommercial2", momsCommercialComboList); // 상권
    $scope._setComboData("srchMomsShopType2", momsShopTypeComboList); // 점포유형
    $scope._setComboData("srchMomsStoreManageType2", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd2", branchCdComboList); // 그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgMapEnvComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 적용대상매장 키오스크 포스 조회
    $scope.$on("kioskKeyMapCopy2Ctrl", function(event, data) {
        // 적용대상매장 키오스크 포스 조회
        $scope.searchTargetStoreList();
        event.preventDefault();
    });

    // 적용대상매장 키오스크 포스 조회
    $scope.searchTargetStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd2").val();
        params.storeNm = $("#srchStoreNm2").val();
        params.originalStoreCd = $("#hdOrgStorecd").val();
        params.orgPosNo = $("#hdOrgPosNo").val();

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeam2Combo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShop2Combo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFg2Combo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercial2Combo.selectedValue;
            params.momsShopType = $scope.srchMomsShopType2Combo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageType2Combo.selectedValue;
            params.branchCd = $scope.srchBranchCd2Combo.selectedValue;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){ // 본사이면서 브랜드사용시만 검색가능

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd2Combo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreCopyKioskPosList.sb", params, function () {
        });
    };

    // 선택한 기준매장포스 정보 셋팅
    $scope.setOrgStoreInfo = function(data){
        $("#selStore").text("[" + data.storeCd + "] " + data.storeNm);
        $("#hdOrgStorecd").val(data.storeCd);
        $("#hdOrgPosNo").val(data.posNo);
        $("#selKioskPosNo").text(data.posNo);
        $("#lblTuMClsFg").text(data.tuMClsFg === "0" ? "미사용" : "중분류사용");
        $("#hdTuMClsFg").val(data.tuMClsFg);
        $("#selEnv4068").text(data.env4068);
        $("#selEnv4069").text(data.env4069);
    };

    // 복사
    $scope.saveCopyStoreKioskKeyMap = function () {

        if($("#hdOrgStorecd").val() === ""){
            $scope._popMsg(messages["kioskKeyMap.original.storeCd"] + messages["cmm.require.select"]); // 기준매장을 선택해주세요.
            return false;
        }

        if($("#selEnv4068").text() === "" || $("#selEnv4069").text() === "" || $("#selEnv4068").text() === "*" || $("#selEnv4069").text() === "*" ){
            $scope._popMsg(messages["kioskKeyMap.no.keyMapGrp.msg"] ); // 기준매장에 등록된 [매장] 또는 [포장]설정키맵그룹이 없어, 복사가 불가합니다.
            return false;
        }

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        var chkCount = 0;
        var chkTuMClsFg = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) {
                chkCount++;
                if (item.tuMClsFg !== $("#hdTuMClsFg").val()){
                    chkTuMClsFg++;
                }
            }
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeyMap.target.storeCd"] + messages["cmm.require.select"]); // 적용대상매장을(를) 선택하세요.
            return false;
        }

        // 선택한 적용대상매장 포스중 'KIOSK중분류사용'이 기준매장의 'KIOSK중분류사용'과 다른 포스가 있습니다.</br>'KIOSK중분류사용'가 같은 경우만 복사됩니다.
        if(chkTuMClsFg > 0){
            $scope._popMsg(messages["kioskKeyMap.chk.tuMClsFg.msg"]);
            return false;
        }

        // 선택한 설정키맵그룹을 복사하시겠습니까?(적용대상매장의 기존 설정키맵그룹 정보는 모두 삭제됩니다.)
        $scope._popConfirm(messages["kioskKeyMap.copy.keyMapGrp.msg"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].originalStoreCd = $("#hdOrgStorecd").val();
                    $scope.flex.collectionView.items[i].orgPosNo = $("#hdOrgPosNo").val();
                    $scope.flex.collectionView.items[i].envstCd = "4068,4069";
                    $scope.flex.collectionView.items[i].envstVal = $("#selEnv4068").text() + "," + $("#selEnv4069").text();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            /*$scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStoreCopy.sb', params, function() {
                // 적용대상매장 키오스크 포스 재조회
                $scope.searchTargetStoreList();
            });*/

            $timeout(function () {
                setTimeout(function () {
                    // 키오스크키맵복사
                    $scope.save(params);
                }, 500);
            }, 10);

        });

    };

    // 키오스크키맵복사
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
            url    : '/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStoreCopy.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // 적용대상매장 키오스크 포스 재조회
                    $scope.searchTargetStoreList();
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

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeCopy2 = function(){
        if( $("#tblSearchAddShowCopy2").css("display") === 'none') {
            $("#tblSearchAddShowCopy2").show();
        } else {
            $("#tblSearchAddShowCopy2").hide();
        }
    };

}]);