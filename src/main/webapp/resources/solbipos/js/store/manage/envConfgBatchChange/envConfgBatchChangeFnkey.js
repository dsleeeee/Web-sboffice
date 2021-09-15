/****************************************************************
 *
 * 파일명 : envConfgBatchChangeFnkey.js
 * 설  명 : 환경변수일괄변경 > 기능키명칭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.14     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 기능키명칭탭 그리드 생성
 */
app.controller('envConfgBatchChangeFnkeyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('envConfgBatchChangeFnkeyCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("envConfgBatchChangeFnkeyCtrl", function(event, data) {
        $scope.searchEnvConfgBatchChangeFnkey();
        event.preventDefault();
    });

    $scope.searchEnvConfgBatchChangeFnkey = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        if($scope.fnkeyNo === "" || $scope.fnkeyNo === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.fnkey.fnkeyBlank"]); // 기능키를 선택해주세요.
            return false;
        }

        var params = {};
        params.fnkeyNo = $scope.fnkeyNo;

        $scope._inquiryMain("/store/manage/envConfgBatchChange/envConfgBatchChangeFnkey/getEnvConfgBatchChangeFnkeyList.sb", params, function() {}, false);

    };
    // <-- //검색 호출 -->

    // 기능키 조회 팝업
    $scope.popUpFnkey = function() {
        // 기능키 선택취소
        $scope.delFnkey();

        // 기능키 조회 팝업시 구분하려고
        var params = {};
        params.gubun = "STORE";
        var scope = agrid.getScope('envConfgBatchChangeHqCtrl');
        scope.setSelectedStore(params);

        var popup = $scope.wjSearchFnkeyLayer;
        popup.show(true, function (s) {
            var popScope = agrid.getScope('searchFnkeyCtrl');
            popScope.$apply(function () {
                popScope._gridDataInit();
                if (!$.isEmptyObject(popScope.getSelectedFnkey())) {
                    $scope.fnkeyNo = popScope.getSelectedFnkey().fnkeyNo;
                    $scope.fnkeyNm = popScope.getSelectedFnkey().fnkeyNm;

                    // 선택한 기능키 값 보여주기
                    $("#lblFnkeyNo").text("[ " + $scope.fnkeyNo + " ] ");
                    $("#lblFnkeyNm").text($scope.fnkeyNm);

                    // 시스템패스워드 비우기
                    $scope.systemPw = "";

                    // 그리드 지우기
                    var agencyScope1 = agrid.getScope('envConfgBatchChangeFnkeyCtrl');
                    agencyScope1._gridDataInit();
                }
            });
        });
    };

    // 기능키 선택취소
    $scope.delFnkey = function(){
        $scope.fnkeyNo = "";
        $scope.fnkeyNm = "";

        // 선택한 기능키 값 보여주기
        $("#lblFnkeyNo").text("");
        $("#lblFnkeyNm").text("");

        // 시스템패스워드 비우기
        $scope.systemPw = "";

        // 그리드 지우기
        var agencyScope1 = agrid.getScope('envConfgBatchChangeFnkeyCtrl');
        agencyScope1._gridDataInit();
    };

    // 일괄변경
    $scope.batchChangeFnkey = function() {
        // 시스템패스워드 비우기
        $scope.systemPw = "";

        var batchFnkeyVal= "";

        if($scope.fnkeyNo === "" || $scope.fnkeyNo === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.fnkey.fnkeyBlank"]); // 환경설정을 선택해주세요.
            return false;

        } else {
            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }

            batchFnkeyVal = $scope.fnkeyVal;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // $scope.flex.collectionView.items[i].gChk = false;
                $scope.flex.collectionView.items[i].fnkeyNm = $scope.fnkeyVal;
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
    $scope.saveFnkey = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.store.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
            return false;

        } else {
            var date = new Date();
            var year = new String(date.getFullYear());
            var month = new String(date.getMonth()+1);
            month = month.length <= 1 ? "0"+month : month;
            var day = new String(date.getDate());
            day = day.length <= 1 ? "0"+day : day;
            var hh = new String(date.getHours());
            hh = hh < 10 ? "0"+hh : hh;
            var pw = userId + year + month + day + hh;

            if($scope.systemPw !== pw) {
                $scope._popMsg(messages["envConfgBatchChange.store.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
                return false;
            }
        }

        // 저장 하시겠습니까?
        var msg = messages["cmm.choo.save"];

        $scope._popConfirm(msg, function() {
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].fnkeyVal == "") {
                    $scope._popMsg( "[" + $scope.flex.collectionView.itemsEdited[i].storeCd + "]" + $scope.flex.collectionView.itemsEdited[i].storeNm + messages["envConfgBatchChange.store.saveAlert"]); // 에 공백은 저장할 수 없습니다.
                    return false;
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                $scope.flex.collectionView.itemsEdited[i].fnkeyNo = $scope.fnkeyNo;
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/envConfgBatchChange/envConfgBatchChangeFnkey/getEnvConfgBatchChangeFnkeySave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchEnvConfgBatchChangeFnkey();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 기능키 조회 팝업 핸들러 추가
        $scope.wjSearchFnkeyLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('searchFnkeyCtrl');
            }, 50)
        });
    });
}]);