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
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

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
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
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

        // 본사 또는 단독매장에서 매장사용터치키설정 사용시
        if(orgnFg === "HQ" || (orgnFg === "STORE" && hqOfficeCd === "00000")) {

            // 매장사용터치키 설정을 하시겠습니까?<br/>(선택매장이 많은경우, 오래걸릴 수 있습니다.)
            $scope._popConfirm(messages["touchKey.touchKeyEnvToStore.msg"], function () {

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if (item.gChk === true) {
                        if (item.posNo !== null && item.posNo !== "") {
                            item.envstVal = $scope.touchKeyEnvCombo.selectedValue;

                            // 본사 터치키 매장적용 여부
                            if ($("#chkApplyStore").is(":checked") && orgnFg === "HQ") {
                                item.chkApplyStore = "Y";
                                item.tukeyGrpCd = $scope.touchKeyEnvCombo.selectedValue;
                            } else {
                                item.chkApplyStore = "N";
                            }
                            params.push(item);
                        }
                    }
                }
                console.log(params);

                // 적용할 대상이 없습니다.
                if (params.length <= 0) {
                    s_alert.pop(messages["touchKey.msg.select"]);
                    return false;
                }

                // 매장사용터치키설정 매장 데이터량 체크하여 선작업 후 저장
                $scope.applyDataChk(params);
            });
        }

        // 프랜차이즈 매장에서 매장사용터치키설정 사용시
        if (orgnFg === "STORE" && hqOfficeCd !== "00000") {

            // 매장사용터치키 설정을 하시겠습니까?<br/>(선택매장이 많은경우, 오래걸릴 수 있습니다.)
            $scope._popConfirm(messages["touchKey.touchKeyEnvToStore.msg"], function () {

                // 본사의 터치키그룹 정보도 수신하시겠습니까?
                var id = s_alert.randomString(5);
                var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);

                pop.find("p").html(messages["touchKey.touchKeyEnvToStore2.msg"] + "<br/>"
                                              + "[확인] 클릭시, 터치키설정 적용 & 본사 터치키그룹 '" + $scope.touchKeyEnvCombo.text + "' 매장 적용<br/>"
                                              + "[취소] 클릭시, 터치키설정만 적용");

                // 팝업 띄우기
                $("#_alertTent").show();
                pop.show();

                // 확인 버튼 클릭시, 매장사용터치키 환경설정값 & 본사 터치키 정보 매장적용 UPDATE
                pop.find("a.btn_blue.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();

                    setTimeout(function () {
                        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                            var item = $scope.flex.collectionView.items[i];
                            if (item.gChk === true) {
                                if (item.posNo !== null && item.posNo !== "") {
                                    item.envstVal = $scope.touchKeyEnvCombo.selectedValue;

                                    // 본사 터치키 매장적용 여부
                                    item.chkApplyStore = "Y";
                                    item.tukeyGrpCd = $scope.touchKeyEnvCombo.selectedValue;
                                    params.push(item);
                                }
                            }
                        }
                        console.log(params);

                        // 적용할 대상이 없습니다.
                        if (params.length <= 0) {
                            s_alert.pop(messages["touchKey.msg.select"]);
                            return false;
                        }

                        // 매장사용터치키설정 매장 데이터량 체크하여 선작업 후 저장
                        $scope.applyDataChk(params);

                    }, 50);

                    return false;
                });

                // 취소 버튼 클릭시, 매장사용터치키 환경설정값만 UPDATE
                pop.find("a.btn_gray.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();

                    setTimeout(function () {
                        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                            var item = $scope.flex.collectionView.items[i];
                            if (item.gChk === true) {
                                if (item.posNo !== null && item.posNo !== "") {
                                    item.envstVal = $scope.touchKeyEnvCombo.selectedValue;

                                    // 본사 터치키 매장적용 여부
                                    item.chkApplyStore = "N";
                                    params.push(item);
                                }
                            }
                        }
                        console.log(params);

                        // 적용할 대상이 없습니다.
                        if (params.length <= 0) {
                            s_alert.pop(messages["touchKey.msg.select"]);
                            return false;
                        }

                        // 매장사용터치키설정 매장 데이터량 체크하여 선작업 후 저장
                        $scope.applyDataChk(params);

                    }, 50);

                     return false;
                });
            });
        }

    };

    // 매장사용터치키설정 매장 데이터량 체크하여 선작업 후 저장
    $scope.applyDataChk = function(params){

        // 매장적용 500개 이상 적용시
        if (params.length >= 500) {

            // 대량 적용 알림 문자 발송
            var smsParams = {};
            smsParams.subject = "[대량적용알림]매장사용터치키설정";
            smsParams.smsMsg = "매장사용터치키설정을 시작하였습니다. 본사코드 : " + hqOfficeCd + ", 적용매장수 : " + params.length;
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
                    // 매장사용터치키 설정 저장
                    $scope.save(params);
                }, 500);
            }, 10);
        }
    };

    // 매장사용터치키설정 저장
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
                    // 터치키 재조회
                    document.getElementById('btnSrchTouchKey').click();
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

    // 매장사용터치키설정 저장(500개 이상 매장용)
    $scope.save2 = function (orgParams, seq) {

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
        $scope._postJSONQuery.withOutPopUp("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params2, function(response){

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
                        // 터치키 재조회
                        document.getElementById('btnSrchTouchKey').click();
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
                },messages["touchKey.touchKey"] + '_' + messages["touchKey.storeUseTouchKey"]+ '_'+ getCurDateTime() +'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);