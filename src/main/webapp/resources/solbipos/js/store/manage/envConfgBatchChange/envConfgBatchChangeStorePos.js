/****************************************************************
 *
 * 파일명 : envConfgBatchChangeStorePos.js
 * 설  명 : 환경변수일괄변경 > 매장포스환경탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 등록여부
var useYnComboData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

// 환경변수값
var envstValComboData = [
    {"name":"선택","value":""}
];

/**
 * 매장포스환경탭 그리드 생성
 */
app.controller('envConfgBatchChangeStorePosCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('envConfgBatchChangeStorePosCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnCombo", useYnComboData); // 등록여부
    $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
    $scope._setComboData("posUseFg", posUseFgComboList); // 환경변수값
    $scope._setComboData("posMainFg", posMainFgComboList); // 환경변수값

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValComboData, 'value', 'name'); // 환경설정값코드
    };

    // <-- 검색 호출 -->
    $scope.$on("envConfgBatchChangeStorePosCtrl", function(event, data) {
        $scope.searchEnvConfgBatchChangeStorePos();
        event.preventDefault();
    });

    $scope.searchEnvConfgBatchChangeStorePos = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        if($scope.envstCd === "" || $scope.envstCd === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.storePos.envstCdBlank"]); // 환경설정을 선택해주세요.
            return false;
        }

        // H:본사전용, C:공통(매장에서변경가능), N:No, S:매장전용, X:공통(매장에서사용하지만_본사기준), Z:본사+단독매장
        if($scope.targtFg === "Z") {
            // 는 단독매장만 검색가능합니다.
            var msg = "'" + $scope.envstNm + "'" +messages["envConfgBatchChange.storePos.targtFgAlert"];
            $scope._popMsg(msg);
        }

        var params = {};
        params.targtFg = $scope.targtFg;
        params.posUseFg = $scope.posUseFgCombo.selectedValue;
        params.posMainFg = $scope.posMainFgCombo.selectedValue;

        $scope._inquiryMain("/store/manage/envConfgBatchChange/envConfgBatchChangeStorePos/getEnvConfgBatchChangeStorePosList.sb", params, function() {}, false);

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridEnvConfgBatchChangeStorePosList");
        var columns = grid.columns;
        var start = 0;
        var end = 0;

        if($scope.dirctInYn === "Y") { // 직접
            start = 9;
            end = 9;
        } else if($scope.dirctInYn  === "N") { // 선택
            start = 10;
            end = 10;
        }

        for(var i = 9; i <= 10; i++) {
            if(i >= start && i <= end) {
                columns[i].visible = true;
            } else {
                columns[i].visible = false;
            }
        }
    };
    // <-- //검색 호출 -->

    // 환경설정 조회 팝업
    $scope.popUpEnvstCdStorePos = function() {
        // 환경설정 선택취소
        $scope.delEnvstCdStorePos();

        // 환경설정 조회 팝업시 구분하려고
        var params = {};
        params.gubun = "STORE_POS";
        var scope = agrid.getScope('envConfgBatchChangeHqCtrl');
        scope.setSelectedStore(params);

        var popup = $scope.wjSearchEnvConfgLayer;
        popup.show(true, function (s) {
            var popScope = agrid.getScope('searchEnvConfgCtrl');
            popScope.$apply(function () {
                popScope._gridDataInit();
                if (!$.isEmptyObject(popScope.getSelectedEnvConfg())) {
                    $scope.envstCd = popScope.getSelectedEnvConfg().envstCd;
                    $scope.envstNm = popScope.getSelectedEnvConfg().envstNm;
                    $scope.dirctInYn = popScope.getSelectedEnvConfg().dirctInYn;
                    $scope.targtFg = popScope.getSelectedEnvConfg().targtFg;

                    // 선택한 환결설정 값 보여주기
                    $("#lblEnvstCdStorePos").text("[ " + $scope.envstCd + " ] ");
                    $("#lblEnvstNmStorePos").text($scope.envstNm);

                    // 환경변수값 콤보박스
                    $scope.envstCdComboStorePos();

                    // 시스템패스워드 비우기
                    $scope.systemPw = "";

                    // 그리드 지우기
                    var agencyScope1 = agrid.getScope('envConfgBatchChangeStorePosCtrl');
                    agencyScope1._gridDataInit();
                }
            });
        });
    };

    // 환경변수값 콤보박스
    $scope.envstCdComboStorePos = function() {
        if($scope.dirctInYn === "N") {
            // 환경변수값
            $("#divComboEnvstValStorePos").css('display', '');
            $("#divTextEnvstValStorePos").css('display', 'none');
            $scope.envstVal = ""; // 텍스트용 환경변수값

            var params = {};
            params.envstCd = $scope.envstCd;

            $scope._postJSONQuery.withOutPopUp('/store/manage/envConfgBatchChange/envConfgBatchChangeHq/getEnvstValComboList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var envstValList = response.data.data.list;
                    $scope._setComboData("envstValCombo", envstValList); // 환경변수값
                    $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValList, 'value', 'name'); // 환경설정값코드

                } else {
                    $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
                    $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValComboData, 'value', 'name'); // 환경설정값코드
                }
            });

        } else {
            // 환경변수값
            $("#divComboEnvstValStorePos").css('display', 'none');
            $("#divTextEnvstValStorePos").css('display', '');
            $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
            $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValComboData, 'value', 'name'); // 환경설정값코드
            $scope.envstVal = ""; // 텍스트용 환경변수값
        }
    };

    // 환경설정 선택취소
    $scope.delEnvstCdStorePos = function(){
        $scope.envstCd = "";
        $scope.envstNm = "";

        // 선택한 환결설정 값 보여주기
        $("#lblEnvstCdStorePos").text("");
        $("#lblEnvstNmStorePos").text("");

        // 환경변수값
        $("#divComboEnvstValStorePos").css('display', '');
        $("#divTextEnvstValStorePos").css('display', 'none');
        $scope.dirctInYn = ""; // Y:직접 N:선택
        $scope.targtFg = ""; // H:본사전용, C:공통(매장에서변경가능), N:No, S:매장전용, X:공통(매장에서사용하지만_본사기준), Z:본사+단독매장
        $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
        $scope.envstVal = ""; // 텍스트용 환경변수값

        // 시스템패스워드 비우기
        $scope.systemPw = "";

        // 그리드 지우기
        var agencyScope1 = agrid.getScope('envConfgBatchChangeStorePosCtrl');
        agencyScope1._gridDataInit();
    };

    // 일괄변경
    $scope.batchChangeStorePos = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        var batchEnvstVal= "";

        if($scope.envstCd === "" || $scope.envstCd === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.storePos.envstCdBlank"]); // 환경설정을 선택해주세요.
            return false;

        } else {
            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }

            // Y:직접 N:선택
            if($scope.dirctInYn === "N") {
                batchEnvstVal = $scope.envstValCombo;
            } else {
                batchEnvstVal = $scope.envstVal;
            }
        }

        // 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // $scope.flex.collectionView.items[i].gChk = false;
                $scope.flex.collectionView.items[i].envstVal = batchEnvstVal;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        $scope.flex.collectionView.refresh();
    };

    // 저장
    $scope.saveStorePos = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.storePos.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
            return false;

        } else {
            var date = new Date();
            var year = String(date.getFullYear());
            var month = String(date.getMonth()+1);
            month = month.length <= 1 ? "0"+month : month;
            var day = String(date.getDate());
            day = day.length <= 1 ? "0"+day : day;
            var hh = String(date.getHours());
            hh = hh < 10 ? "0"+hh : hh;
            var pw = userId + year + month + day + hh;

            if($scope.systemPw !== pw) {
                $scope._popMsg(messages["envConfgBatchChange.storePos.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
                return false;
            }
        }

        // 저장 하시겠습니까?
        var msg = messages["cmm.choo.save"];

        $scope._popConfirm(msg, function() {
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.dirctInYn === "Y") { // 직접
                    if($scope.flex.collectionView.itemsEdited[i].envstVal == "") {
                        $scope._popMsg( "[" + $scope.flex.collectionView.itemsEdited[i].storeCd + "]" + $scope.flex.collectionView.itemsEdited[i].storeNm + messages["envConfgBatchChange.storePos.saveAlert"]); // 에 공백은 저장할 수 없습니다.
                        return false;
                    }
                }
            }

            // 파라미터 설정
            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                $scope.flex.collectionView.itemsEdited[i].dirctInYn = $scope.dirctInYn;
                $scope.flex.collectionView.itemsEdited[i].targtFg = $scope.targtFg;
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/envConfgBatchChange/envConfgBatchChangeStorePos/getEnvConfgBatchChangeStorePosSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchEnvConfgBatchChangeStorePos();
    };

    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    //return column.visible;
                    return column.binding != 'gChk'; //선택
                }
            },
                "환경변수일괄변경_" + messages['envConfgBatchChange.storePos'] + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);