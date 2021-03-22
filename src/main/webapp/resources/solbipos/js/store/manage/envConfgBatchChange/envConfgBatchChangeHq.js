/****************************************************************
 *
 * 파일명 : envConfgBatchChangeHq.js
 * 설  명 : 환경변수일괄변경 > 본사환경탭 JavaScript
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
 * 본사환경탭 그리드 생성
 */
app.controller('envConfgBatchChangeHqCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('envConfgBatchChangeHqCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnCombo", useYnComboData); // 등록여부
    $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValComboData, 'value', 'name'); // 환경설정값코드
    };

    // <-- 검색 호출 -->
    $scope.$on("envConfgBatchChangeHqCtrl", function(event, data) {
        $scope.searchEnvConfgBatchChangeHq();
        event.preventDefault();
    });

    $scope.searchEnvConfgBatchChangeHq = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        if($scope.envstCd === "" || $scope.envstCd === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.hq.envstCdBlank"]); // 환경설정을 선택해주세요.
            return false;
        }

        var params = {};

        $scope._inquiryMain("/store/manage/envConfgBatchChange/envConfgBatchChangeHq/getEnvConfgBatchChangeHqList.sb", params, function() {}, false);

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridEnvConfgBatchChangeHqList");
        var columns = grid.columns;
        var start = 0;
        var end = 0;

        if($scope.dirctInYn === "Y") { // 직접
            start = 3;
            end = 3;
        } else if($scope.dirctInYn  === "N") { // 선택
            start = 4;
            end = 4;
        }

        for(var i = 3; i <= 4; i++) {
            if(i >= start && i <= end) {
                columns[i].visible = true;
            } else {
                columns[i].visible = false;
            }
        }
    };
    // <-- //검색 호출 -->

    // 환경설정 조회 팝업
    $scope.popUpEnvstCd = function() {
        // 환경설정 선택취소
        $scope.delEnvstCd();

        // 환경설정 조회 팝업시 구분하려고
        var params = {};
        params.gubun = "HQ";
        $scope.setSelectedStore(params);

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
                    $("#lblEnvstCd").text("[ " + $scope.envstCd + " ] ");
                    $("#lblEnvstNm").text($scope.envstNm);

                    // 환경변수값 콤보박스
                    $scope.envstCdCombo();

                    // 시스템패스워드 비우기
                    $scope.systemPw = "";

                    // 그리드 지우기
                    var agencyScope1 = agrid.getScope('envConfgBatchChangeHqCtrl');
                    agencyScope1._gridDataInit();
                }
            });
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 환경설정 조회 팝업 핸들러 추가
        $scope.wjSearchEnvConfgLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('searchEnvConfgCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(member) {
        $scope.selectedStore = member;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 환경변수값 콤보박스
    $scope.envstCdCombo = function() {
        if($scope.dirctInYn === "N") {
            // 환경변수값
            $("#divComboEnvstVal").css('display', '');
            $("#divTextEnvstVal").css('display', 'none');
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
            $("#divComboEnvstVal").css('display', 'none');
            $("#divTextEnvstVal").css('display', '');
            $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
            $scope.envstValCdDataMap = new wijmo.grid.DataMap(envstValComboData, 'value', 'name'); // 환경설정값코드
            $scope.envstVal = ""; // 텍스트용 환경변수값
        }
    };

    // 환경설정 선택취소
    $scope.delEnvstCd = function(){
        $scope.envstCd = "";
        $scope.envstNm = "";

        // 선택한 환결설정 값 보여주기
        $("#lblEnvstCd").text("");
        $("#lblEnvstNm").text("");

        // 환경변수값
        $("#divComboEnvstVal").css('display', '');
        $("#divTextEnvstVal").css('display', 'none');
        $scope.dirctInYn = ""; // Y:직접 N:선택
        $scope.targtFg = ""; // H:본사전용, C:공통(매장에서변경가능), N:No, S:매장전용, X:공통(매장에서사용하지만_본사기준), Z:본사+단독매장
        $scope._setComboData("envstValCombo", envstValComboData); // 환경변수값
        $scope.envstVal = ""; // 텍스트용 환경변수값

        // 시스템패스워드 비우기
        $scope.systemPw = "";

        // 그리드 지우기
        var agencyScope1 = agrid.getScope('envConfgBatchChangeHqCtrl');
        agencyScope1._gridDataInit();
    };

    // 일괄변경
    $scope.batchChange = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        var batchEnvstVal= "";

        if($scope.envstCd === "" || $scope.envstCd === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.hq.envstCdBlank"]); // 환경설정을 선택해주세요.
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
        var params = new Array();
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
    $scope.save = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.hq.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
            return false;

        } else {
            var date = new Date();
            var year = new String(date.getFullYear());
            var month = new String(date.getMonth()+1);
            month = month.length <= 1 ? "0"+month : month;
            var day = new String(date.getDate());
            day = day.length <= 1 ? "0"+day : day;
            var hh = new String(date.getHours());
            hh = hh.length <= 1 ? "0"+hh : "0"+(hh-12);
            var pw = userId + year + month + day + hh;

            if($scope.systemPw !== pw) {
                $scope._popMsg(messages["envConfgBatchChange.hq.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
                return false;
            }
        }

        // 저장 하시겠습니까?
        var msg = messages["cmm.choo.save"];

        // H:본사전용, C:공통(매장에서변경가능), N:No, S:매장전용, X:공통(매장에서사용하지만_본사기준), Z:본사+단독매장
        if($scope.targtFg === "X") {
            // 는 본사와 모든 매장에 적용됩니다. <br/> 저장하시겠습니까?
            msg = "'" + $scope.envstNm + "'" +messages["envConfgBatchChange.hq.targtFgConfirm"];
        }

        $scope._popConfirm(msg, function() {
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.dirctInYn === "Y") { // 직접
                    if($scope.flex.collectionView.itemsEdited[i].envstVal == "") {
                        $scope._popMsg( "[" + $scope.flex.collectionView.itemsEdited[i].hqOfficeCd + "]" + $scope.flex.collectionView.itemsEdited[i].hqOfficeNm + messages["envConfgBatchChange.hq.saveAlert"]); // 에 공백은 저장할 수 없습니다.
                        return false;
                    }
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                $scope.flex.collectionView.itemsEdited[i].dirctInYn = $scope.dirctInYn;
                $scope.flex.collectionView.itemsEdited[i].targtFg = $scope.targtFg;
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/envConfgBatchChange/envConfgBatchChangeHq/getEnvConfgBatchChangeHqSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchEnvConfgBatchChangeHq();
    };

}]);