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
    $scope._setComboData("applyTuClsType", kioskTuClsTypeList00); // 키오스크용 키맵그룹 목록
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
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

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

        // 맘스터치는 터치키그룹 '[00]사용중인키맵매장적용' 으로 고정
        /*if(hqOfficeCd === "DS034" || hqOfficeCd === "H0393" || hqOfficeCd === "DS021") {
            $scope.applyTuClsTypeCombo.selectedIndex = 0;
            //$("#applyTuClsType").css('background-color', '#F0F0F0');
            $("#applyTuClsType").attr("disabled", true);
        }else{
            //$("#applyTuClsType").css('background-color', '#FFFFFF');
            $("#applyTuClsType").attr("disabled", false);
        }*/

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.storeCd = $scope.srchStoreCd;
        params.storeNm = $scope.srchStoreNm;
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;
        params.tuMClsFg = $scope.tuMClsFgStoreRegistCombo.selectedValue;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;

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
            /*var grid = wijmo.Control.getControl("#wjGridKioskKeyMapStoreReg");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.kioskPosCnt === 0){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }*/
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
        var msg = "'" + $scope.applyTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreReg.msg"] + messages["kioskKeyMap.kioskTuMClsFg.msg"];
        // '[00]사용중인키맵매장적용'으로 선택하시면 자동으로 각 매장에 설정된 키맵 키 값이 적용됩니다.
        msg += "<br><br><p style='color:red;'>'[00]사용중인키맵매장적용'</p>으로 선택하시면 자동으로 각 매장에 설정된 키맵 키 값이 적용됩니다.(중분류사용여부체크안함)";

        if(orgnFg === "STORE" && (hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062')){
            msg += "<br>" + messages["kioskKeyMap.keyMapStoreRegAll.msg"]; // 저장 시 전체 키오스크의 정보가 변경됩니다.
        }

        $scope._popConfirm(msg, function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true) {

                    // 선택한 키맵그룹이 [00]사용중인키맵매장적용 이거나 또는 선택한 키맵그룹의 중분류사용여부가 선택한 포스의 KIOSK중분류사용과 동일한 경우 적용 진행
                    if($scope.applyTuClsTypeCombo.selectedValue === "00" || ($("#hdTuMClsFgStoreRegist").val() === item.tuMClsFg)) {

                        var obj = {};
                        obj.storeCd = item.storeCd;
                        obj.tuClsType = $scope.applyTuClsTypeCombo.selectedValue;
                        obj.posNo = item.posNo;
                        //obj.tuMClsFg = $("#hdTuMClsFgStoreRegist").val(); // 매장 적용시에만 UPDATE / 매장 해당컬럼 사용안함 / 수정 기록 확인용 / 매장은 [4101 KIOSK중분류사용] 사용

                        params.push(obj);
                    }
                }
            }

            // 매장적용 500개 이상 적용시
            if (params.length >= 500) {

                // 대량 적용 알림 문자 발송
                var smsParams = {};
                smsParams.subject = "[대량적용알림]키맵매장적용";
                smsParams.smsMsg = "키맵매장적용을 시작하였습니다. 본사코드 : " + hqOfficeCd + ", 적용매장수 : " + params.length;
                smsParams.msgFg = "S01_0002"; // [전송구분] S01_0001:테스트용, S01_0002:터치키/키오스크 매장적용 시
                smsParams.nmcodeItem1 = "터치키/키오스크 매장적용";
                $scope._postJSONSave.withOutPopUp("/base/prod/touchKey/touchKey/notiSmsSend.sb", smsParams, function () {

                    // 사용자 현재 인원수 체크
                    var data = {};
                    data.downloadFg = "2";    // [다운로드 구분] 0:간소화화면, 1:상품매출분석 2: 터치키/키오스크 매장적용
                    data.resrceCd = menuCd;
                    data.resrceNm = menuNm;
                    data.downloadUseFg = "3"; // [다운로드 사용기능] 0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드 3:터치키/키오스크 매장적용
                    data.downloadNo = "0";    // 다운로드 화면구분번호

                    $scope._postJSONQuery.withOutPopUp('/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadCntChk.sb', data, function (response) {
                        if (response.data.data.list === 0) {
                        } else {
                            var msgCntChk = response.data.data.list; // '00:0건의 대량 적용 진행중' 메시지면 작업가능
                            var params2 = data;

                            if (msgCntChk.substr(0, 2) === "00") { // 작업가능 시

                                // 대량 적용 진행 사용자 저장 insert
                                params2.downloadFileCount = params.length; // 대량 적용 매장수
                                $scope._postJSONQuery.withOutPopUp("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params2, function (response) {
                                    var seq = response.data.data.list; // 순번
                                    // 매장사용터치키 설정 저장(500개 이상 매장용)
                                    $scope.save2(params, seq);
                                });

                            } else { // 작업불가 시

                                // 대량 적용 진행 사용자 저장 insert
                                params2.resrceNm = "실패:" + menuNm;
                                params2.downloadFileCount = 0; // 대량 적용 매장수
                                $scope._postJSONQuery.withOutPopUp("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params2, function (response) {
                                });

                                $scope._popMsg(msgCntChk); // 매장적용 사용량이 초과되어 대기중입니다. 잠시 후 다시 진행하여 주십시오.
                                return;
                            }
                        }
                    });

                });

            } else {
                $timeout(function () {
                    setTimeout(function () {
                        // 키오스크키맵 매장적용
                        $scope.save(params);
                    }, 500);
                }, 10);
            }
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

    // 키오스크키맵 매장적용 저장(500개 이상 매장용)
    $scope.save2 = function(orgParams, seq){

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

        // 진행 시간 저장
        var params2 = {};
        params2.seq = seq;
        $scope._postJSONQuery.withOutPopUp("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params2, function(response) {

            //가상로그인 session 설정
            var sParam = {};
            if (document.getElementsByName('sessionId')[0]) {
                sParam['sid'] = document.getElementsByName('sessionId')[0].value;
            }

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStore.sb', /* 통신할 URL */
                data: params, /* 파라메터로 보낼 데이터 : @requestBody */
                params: sParam,
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
                    $scope.save2(orgParams, seq);
                }
            });
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
        $scope.srchStoreCd = "";
        $scope.srchStoreNm = "";
        $scope.srchSysStatFgCombo.selectedIndex = 0;
        $scope.applyTuClsTypeCombo.selectedIndex = 0;
    };

    // 적용할 키맵그룹 선택 이벤트
    $scope.setApplyTuClsTypeCombo = function (s){

        if(s.selectedValue === "00"){ // '[00]사용중인키맵매장적용' 선택시 빈값으로 셋팅
            $("#lblTuMClsFgStoreRegist").text("");
            $("#hdTuMClsFgStoreRegist").val(0);
        }else {
            var params = {};
            params.tuClsType = s.selectedValue;

            // 키맵그룹에 KIOSK중분류사용 조회
            $scope.kioskKeyMapGroupTuMClsFg(params);
        }
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
                },messages["kioskKeyMap.storeReg"]+ '_'+ getCurDateTime() +'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);
