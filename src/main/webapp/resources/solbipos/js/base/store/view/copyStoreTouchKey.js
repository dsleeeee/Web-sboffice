/****************************************************************
 *
 * 파일명 : copyStoreTouchKey.js
 * 설  명 : 매장 판매터치키복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.29     이다솜      1.0
 *
 * **************************************************************/

app.controller('copyStoreTouchKeyCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyStoreTouchKeyCtrl', $scope, $http, false));

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

                // 기준매장 매장코드 클릭
                if ( col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var scope = agrid.getScope('copyStoreTouchKey2Ctrl');

                    // 선택한 기준매장 정보 및 터치키 그룹 셋팅
                    scope.setOrgStoreInfo(selectedRow);

                    // 적용대상매장 조회
                    scope.searchTargetStoreList();

                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("copyStoreTouchKeyCtrl", function (event, data) {

        // 기준매장 조회
        $scope.searchOrgStoreList();
        event.preventDefault();
    });
    
    // 기준매장 조회
    $scope.searchOrgStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd1").val();
        params.storeNm = $("#srchStoreNm1").val();
        params.originalStoreCd = "";
        params.momsEnvstVal = momsEnvstVal;
        if(momsEnvstVal === "1") {
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
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < brandList.length; i++) {
                    if (brandList[i].value !== null) {
                        userHqBrandCd += brandList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        } else if(brandUseFg === "1"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/view/view/getStoreList.sb", params);
    };

    // 닫기
    $scope.closePop = function () {

        // 초기화
        $("#srchStoreCd1").val("");
        $("#srchStoreNm1").val("");
        $("#srchStoreCd2").val("");
        $("#srchStoreNm2").val("");
        $("#selStore").text("");
        $("#hdOrgStorecd").val("");
        $("#selTouchKeyGrp").css("display", "none");

        var scope = agrid.getScope('copyStoreTouchKey2Ctrl');
        scope._gridDataInit();

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

app.controller('copyStoreTouchKey2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('copyStoreTouchKey2Ctrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo2", brandList); // 매장브랜드
    $scope._setComboData("momsTeamCombo2", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo2", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo2", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo2", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo2", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo2", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo2", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo2", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo2", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo2", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo2", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo2", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("copyStoreTouchKey2Ctrl", function (event, data) {
        // 적용대상매장 조회
        $scope.searchTargetStoreList();
        event.preventDefault();
    });

    // 적용대상매장 조회
    $scope.searchTargetStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd2").val();
        params.storeNm = $("#srchStoreNm2").val();
        params.originalStoreCd = $("#hdOrgStorecd").val();
        params.momsEnvstVal = momsEnvstVal;
        if(momsEnvstVal === "1") {
            params.momsTeam = $scope.momsTeam2;
            params.momsAcShop = $scope.momsAcShop2;
            params.momsAreaFg = $scope.momsAreaFg2;
            params.momsCommercial = $scope.momsCommercial2;
            params.momsShopType = $scope.momsShopType2;
            params.momsStoreManageType = $scope.momsStoreManageType2;
            params.branchCd = $scope.branchCd2;
            params.momsStoreFg01 = $scope.momsStoreFg012;
            params.momsStoreFg02 = $scope.momsStoreFg022;
            params.momsStoreFg03 = $scope.momsStoreFg032;
            params.momsStoreFg04 = $scope.momsStoreFg042;
            params.momsStoreFg05 = $scope.momsStoreFg052;

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo2.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < brandList.length; i++) {
                    if (brandList[i].value !== null) {
                        userHqBrandCd += brandList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        } else if(brandUseFg === "1"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo2.selectedValue;
        }
        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/view/view/getStoreList.sb", params);
    };

    // 선택한 기준매장 정보 및 터치키 그룹 셋팅
    $scope.setOrgStoreInfo = function(data){

        // 선택한 기준매장 셋팅
        $("#selStore").text("[" + data.storeCd + "] " + data.storeNm);
        $("#hdOrgStorecd").val(data.storeCd);

        // 선택한 기준매장의 터치키그룹 셋팅
        var comboParams = {};
        comboParams.storeCd = data.storeCd;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : "/base/store/view/view/getStoreTouchKeyGrpCombo.sb", /* 통신할 URL */
            params : comboParams, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    if(list.length > 0) {
                        comboData.name = messages["cmm.all"];
                        comboData.value = "";
                        comboArray.push(comboData);

                        // 계정 콤보박스 셋팅
                        for (var i = 0; i < list.length; i++) {
                            comboData = {};
                            comboData.name = list[i].name;
                            comboData.value = list[i].value;
                            comboArray.push(comboData);
                        }

                    }else{
                        comboData.name = "-";
                        comboData.value = "-";
                        comboArray.push(comboData);
                    }

                    $scope._setComboData("tuKeyGrpCombo", comboArray);

                    // 터치키 그룹 콤보박스 보여주기
                    $("#selTouchKeyGrp").css("display", "");
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };

    // 복사
    $scope.saveCopyStoreTouchKey = function () {

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자
        
        if($("#hdOrgStorecd").val() === ""){
            $scope._popMsg(messages["storeView.original.storeCd"] + messages["cmm.require.select"]); // 기준매장을 선택해주세요.
            return false;
        }

        if($scope.tuKeyGrpCombo.selectedValue === "-"){
            $scope._popMsg(messages["storeView.no.touchKey.grp.msg"] ); // 기준매장에 등록된 터치키그룹이 없어, 복사가 불가합니다.
            return false;
        }

        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["storeView.target.storeCd"] + messages["cmm.require.select"]); // 적용대상매장을(를) 선택하세요.
            return false;
        }

        // 선택한 터치키그룹을 복사하시겠습니까?(적용대상매장의 기존 터치키그룹 정보는 모두 삭제됩니다.)
        $scope._popConfirm(messages["storeView.copy.touchKey.grp.msg1"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {

                    $scope.flex.collectionView.items[i].originalStoreCd = $("#hdOrgStorecd").val();
                    $scope.flex.collectionView.items[i].targetStoreCd = $scope.flex.collectionView.items[i].storeCd;
                    $scope.flex.collectionView.items[i].tukeyGrpCd = $scope.tuKeyGrpCombo.selectedValue;

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            $timeout(function () {
              setTimeout(function () {
                  // 터치키 매장복사
                  $scope.save(params);
              }, 500);
            }, 10);
        });
    };

    // 터치키 매장복사
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
            url    : '/base/store/view/view/copyStoreTouchKeyGrp.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // 터치키 매장복사 팝업 닫기
                    $scope.copyStoreTouchKeyLayer.hide(true);
                    // 초기화
                    $("#srchStoreCd1").val("");
                    $("#srchStoreNm1").val("");
                    $("#srchStoreCd2").val("");
                    $("#srchStoreNm2").val("");
                    $("#selStore").text("");
                    $("#hdOrgStorecd").val("");
                    $("#selTouchKeyGrp").css("display", "none");
                    $scope._gridDataInit();
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