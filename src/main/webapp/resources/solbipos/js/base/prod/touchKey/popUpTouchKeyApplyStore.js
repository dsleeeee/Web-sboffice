/****************************************************************
 *
 * 파일명 : popUpTouchKeyApplyStore.js
 * 설  명 : 출력물샘플 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.28     노현수      1.0
 *
 * **************************************************************/

// 팝업 그리드 생성
app.controller('popUpApplyStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('popUpApplyStoreCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchSysStatFgCombo", sysStatFgComboData);
    $scope._setComboData("srchClsFgCombo", clsFgComboData);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", brandList); // 매장브랜드
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
        // // ReadOnly 효과설정 : checkbox disabled
        // s.formatItem.addHandler(function (s, e) {
        //   // 전체선택 사용불가 설정
        //   if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //     var col = s.columns[e.col];
        //     if (col.binding === 'gChk' || col.format === 'checkBox' || col.format === 'checkBoxText') {
        //       e.cell.children[0].disabled = true;
        //     }
        //   }
        // });
        // // grid 수정 이벤트
        // s.beginningEdit.addHandler(function (s, e) {
        //   var col = s.columns[e.col];
        //   if (col.binding === "gChk") {
        //     var dataItem = s.rows[e.row].dataItem;
        //     setTimeout(function () {
        //       if ( dataItem.gChk === true ) {
        //         var chk = 0;
        //         for (var i = 0; i < s.itemsSource.items.length; i++) {
        //           if ( s.itemsSource.items[i].gChk === true ) {
        //             chk++;
        //           }
        //         }
        //         if ( chk > 10 ) {
        //           $scope._popMsg("매장적용은 10개 매장까지만 선택 가능합니다.");
        //           s.setCellData(e.row, "gChk", false);
        //         }
        //       }
        //     }, 10);
        //   }
        // });
    };
    // 팝업 그리드 조회
    $scope.$on("popUpApplyStoreCtrl", function (event, data) {

        // 맘스터치는 터치키그룹 '[00]사용중인터치키매장적용' 으로 고정
        /*if(hqOfficeCd === "DS034" || hqOfficeCd === "H0393" || hqOfficeCd === "DS021") {
            $scope.applyTouchKeyGrpCombo.selectedIndex = 0;
            //$("#applyTouchKeyGrpCombo").css('background-color', '#F0F0F0');
            $("#applyTouchKeyGrpCombo").attr("disabled", true);
        }else{
            //$("#applyTouchKeyGrpCombo").css('background-color', '#FFFFFF');
            $("#applyTouchKeyGrpCombo").attr("disabled", false);
        }*/

        // 파라미터
        var params = {};
        params.momsEnvstVal = momsEnvstVal;
        if (momsEnvstVal === "1") {
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
        } else if (brandUseFg === "1") {
            params.storeHqBrandCd = $scope.storeHqBrandCd;
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/touchKey/touchKey/storeList.sb", params, function () {

        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 적용버튼 클릭
    $scope.btnTouchKeyApplyStore = function () {

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 저장 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flexLayer.collectionView.items.length; i++) {
            if ($scope.flexLayer.itemsSource.items[i].gChk === true) {
                $scope.flexLayer.itemsSource.items[i].tukeyGrpCd = $scope.applyTouchKeyGrp;
                params.push($scope.flexLayer.itemsSource.items[i]);
            }
        }

        // 적용할 대상이 없습니다.
        if (params.length <= 0) {
            s_alert.pop(messages["touchKey.msg.select"]);
            return false;
        }

        // 터치키 매장적용을 하시겠습니까?(선택매장이 많은경우, 오래걸릴수 있습니다.)
        var msg = messages["touchKey.touchKeyApplyStore.msg"];
        // '[00]사용중인터치키매장적용'으로 선택하시면 자동으로 각 매장에 설정된 터치키 키 값이 적용됩니다.
        msg += "<br><br><p style='color:red;'>'[00]사용중인터치키매장적용'</p>으로 선택하시면 자동으로 각 매장에 설정된 터치키 키 값이 적용됩니다.";

        $scope._popConfirm(msg , function () {

            // 매장적용 500개 이상 적용시
            if (params.length >= 500) {

                // 대량 적용 알림 문자 발송
                var smsParams = {};
                smsParams.subject = "[대량적용알림]터치키매장적용";
                smsParams.smsMsg = "터치키매장적용을 시작하였습니다. 본사코드 : " + hqOfficeCd + ", 적용매장수 : " + params.length;
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
                                    // 터치키 매장적용 저장(500개 이상 매장용)
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
                        // 터치키 매장적용 저장
                        $scope.save(params);
                    }, 500);
                }, 10);
            }
        });
    };

    // 터치키 매장적용 저장
    $scope.save = function (orgParams) {

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
        if (document.getElementsByName('sessionId')[0]) {
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/base/prod/touchKey/touchKey/applyStore.sb', /* 통신할 URL */
            data: params, /* 파라메터로 보낼 데이터 : @requestBody */
            params: sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // 터치키 매장적용 팝업 닫기
                    $scope.popUpApplyStoreLayer.hide(true);
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

    // 터치키 매장적용 저장(500개 이상 매장용)
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
            if (document.getElementsByName('sessionId')[0]) {
                sParam['sid'] = document.getElementsByName('sessionId')[0].value;
            }

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/base/prod/touchKey/touchKey/applyStore.sb', /* 통신할 URL */
                data: params, /* 파라메터로 보낼 데이터 : @requestBody */
                params: sParam,
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if ($scope._httpStatusCheck(response, true)) {
                    if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                        // 작업내역 로딩 팝업 닫기
                        $scope.excelUploadingPopup(false);
                        // 터치키 매장적용 팝업 닫기
                        $scope.popUpApplyStoreLayer.hide(true);
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

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function () {
        if ($("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 엑셀다운로드 -->
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex,
                {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                }, messages["touchKey.applyStore"] + '_' + getCurDateTime() + '.xlsx',
                function () {
                    console.log($scope.storeHqBrandCd);
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);

