/****************************************************************
 *
 * 파일명 : kioskKeyMapStoreReg.js
 * 설  명 : 키오스크 키맵매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.07     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgStoreRegistComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

app.controller('kioskKeyMapStoreRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('kioskKeyMapStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchSysStatFg", sysStatFg);
    $scope._setComboData("applyTuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록
    $scope._setComboData("tuMClsFgStoreRegist", tuMClsFgStoreRegistComboData); // KIOSK중분류사용
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgStoreRegistComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskKeyMapStoreRegCtrl", function(event, data) {

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;
        params.tuMClsFg = $scope.tuMClsFgStoreRegistCombo.selectedValue;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        if(brandUseFg === "1" && orgnFg === "HQ"){

          // 선택한 매장브랜드가 있을 때
          params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

          // 선택한 매장브랜드가 없을 때('전체' 일때)
          if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
              var userHqBrandCd = "";
              for(var i=0; i < userHqBrandCdComboList.length; i++){
                  if(userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                  }
              }
              params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
          }
        }

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreList.sb", params, function () {

            // 키오스크포스가 없는 매장은 선택 불가
            var grid = wijmo.Control.getControl("#wjGridKioskKeyMapStoreReg");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.kioskPosCnt === 0){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }
        });
    };

    // 조회
    $scope.btnSearchStore = function(){

        // 매장조회
        $scope.searchStore();
    };

    // 적용
    $scope.btnInsertStore = function () {

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
        if ($scope.applyTuClsTypeCombo.selectedValue === "" || $scope.applyTuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        // '01' 키맵그룹을 매장에 적용하시겠습니까?
        // <br/> (중분류 사용 키맵그룹은 중분류 사용 키오스크에만 적용됩니다.)
        $scope._popConfirm("'" + $scope.applyTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreReg.msg"] + messages["kioskKeyMap.kioskTuMClsFg.msg"], function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true && item.kioskPosCnt > 0) {

                    if($("#hdTuMClsFgStoreRegist").val() === item.tuMClsFg) {

                        var obj = {};
                        obj.storeCd = item.storeCd;
                        obj.tuClsType = $scope.applyTuClsTypeCombo.selectedValue;
                        obj.posNo = item.posNo;
                        obj.tuMClsFg = $("#hdTuMClsFgStoreRegist").val(); // 매장 적용시에만 UPDATE / 매장 해당컬럼 사용안함 / 수정 기록 확인용 / 매장은 [4101 KIOSK중분류사용] 사용

                        params.push(obj);
                    }
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            /*$scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStore.sb", params, function () {

                $scope.kioskKeyMapStoreRegLayer.hide(true);

            });*/
            
            $timeout(function () {
                setTimeout(function () {
                    // 키오스크키맵 매장적용
                    $scope.save(params);
                }, 500);
            }, 10);
        });

    };

    // 키오스크키맵 매장적용 저장
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
            url    : '/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStore.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // 키오스크키맵 매장적용 팝업 닫기
                    $scope.kioskKeyMapStoreRegLayer.hide(true);
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

        $("#srchStoreCd").val("");
        $("#srchStoreNm").val("");
        $scope.srchSysStatFgCombo.selectedIndex = 0;
        $scope.applyTuClsTypeCombo.selectedIndex = 0;
    };

    // 적용할 키맵그룹 선택 이벤트
    $scope.setApplyTuClsTypeCombo = function (s){
        var params = {};
        params.tuClsType = s.selectedValue;

        // 키맵그룹에 KIOSK중분류사용 조회
        $scope.kioskKeyMapGroupTuMClsFg(params);
    };

    // 키맵그룹에 KIOSK중분류사용 조회
    $scope.kioskKeyMapGroupTuMClsFg = function(params){
        $scope._postJSONQuery.withOutPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMapGroupTuMClsFg.sb", params, function(response) {
            var list = response.data.data.list;
            if(list.length > 0) {
                var tuMClsFgNm;
                if(list[0].tuMClsFg == 0) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 미사용)";
                } else if(list[0].tuMClsFg == 2) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 중분류사용)";
                }
                $("#lblTuMClsFgStoreRegist").text(tuMClsFgNm);
                $("#hdTuMClsFgStoreRegist").val(list[0].tuMClsFg);
            }
        }, false);
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };
}]);
