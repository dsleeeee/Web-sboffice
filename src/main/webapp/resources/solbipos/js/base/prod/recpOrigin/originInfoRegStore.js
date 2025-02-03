/****************************************************************
 *
 * 파일명 : originInfoRegStore.js
 * 설  명 : 원산지정보 - 정보입력 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.01.21     김유승      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 구분(판매가변경제한매장)
var storeChgNotComboData = [
    {"name": "전체", "value": ""},
    {"name": "판매가변경제한매장", "value": "1"},
    {"name": "판매가변경제한매장외", "value": "2"}
];
app.controller('originInfoRegStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('originInfoRegStoreCtrl', $scope, $http, false));


    $scope._setComboData("storeChgNotCombo", storeChgNotComboData); // 구분(판매가변경제한매장)

    // 브랜드 콤보박스 셋팅
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

    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("originInfoRegStoreCtrl", function(event, key) {

        $scope.searchMomsBrand();
        event.preventDefault();

    });

    $scope.searchMomsBrand = function (){
        var params = {};
        $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectBrandMomsList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var list = response.data.data.list;
                $scope._setComboData("storeHqBrandCdCombo", list);
                // 매장브랜드 콤보박스 항목 저장시 쓰려고
                momsHqBrandCdComboList = list;
            }

            $scope.searchOriginCdList();
        });
    };

    // 관리코드 조회
    $scope.searchOriginCdList = function (){

        var params = {};
        $scope._postJSONQuery.withOutPopUp('/base/prod/recpOrigin/recpOrigin/getSelectOriginCdList.sb', params, function (response) {
            var list = response.data.data.list;
            $scope._setComboData("originCdListCombo", list); // 관리코드리스트

            $scope.searchStore();
        });

    };

    // 매장조회
    $scope.searchStore = function () {
        // 파라미터
        var params = {};
        params.storeCd = $scope.srchStoreCd;
        params.storeNm = $scope.srchStoreNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.storeChgNot      = $scope.storeChgNot;
        params.momsStoreFg01    = $scope.momsStoreFg01;
        params.momsStoreFg02    = $scope.momsStoreFg02;
        params.momsStoreFg03    = $scope.momsStoreFg03;
        params.momsStoreFg04    = $scope.momsStoreFg04;
        params.momsStoreFg05    = $scope.momsStoreFg05;

        $scope._inquirySub("/base/prod/recpOrigin/recpOrigin/getOriginInfoStoreList.sb", params, function () {
            $scope.searchFg = "Y";
        });
    };

    // 적용
    $scope.originInfoRegStore = function () {

        if($scope.originCdList === null || $scope.originCdList === '' || $scope.originCdList === undefined){
            $scope._popMsg(messages["recpOriginInfo.originCd"] + messages["cmm.require.select"]);
            return false;
        }

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 파라미터
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk === true) {
                $scope.flex.collectionView.items[i].originCd = $scope.originCdList;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.require.selectStore"]);
            return;
        }



        $scope.save(params);

        // $scope._save("/base/prod/recpOrigin/recpOrigin/getOriginInfoRegStore.sb", params, function () {
        //
        // });
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
            url    : '/base/prod/recpOrigin/recpOrigin/getOriginInfoRegStore.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope.searchStore();
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
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.saving'] + '</p>';
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