/****************************************************************
 *
 * 파일명 : naverPlaceStatusReset.js
 * 설  명 : 네이버플레이스 연동 초기화 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.10     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *
 */
app.controller('naverPlaceStatusResetCtrl', ['$scope', '$http', function ($scope, $http) {

    // 선택매장정보
    $scope.selectedStore;

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceStatusResetCtrl', $scope, $http, false));

    $scope.$on("naverPlaceStatusResetCtrl", function(event, data) {

        // 선택매장정보 셋팅
        $scope.selectedStore = data;
        event.preventDefault();

    });

    // 초기화
    $scope.statusReset = function () {

        // 사용자 행위 기록
        var actParams = {};
        actParams.resrceCd = $scope.selectedStore.resrceCd;
        actParams.pathNm = $scope.selectedStore.pathNm;
        actParams.contents = "[초기화] 버튼 클릭 시";

        $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});

        if($scope.selectedStore.hqOfficeCd === null || $scope.selectedStore.hqOfficeCd === undefined|| $scope.selectedStore.hqOfficeCd === "" ||
            $scope.selectedStore.storeCd === null || $scope.selectedStore.storeCd === undefined|| $scope.selectedStore.storeCd === "") {
            $scope._popMsg(messages["naverPlaceStatus.store.Chk.msg"]); // 매장정보가 없습니다.
            return false;
        }

        if($("#resetPwd").val() === ""){
            $scope._popMsg(messages["naverPlaceStatus.resetPwd.msg"]); // 비밀번호를 입력하세요.
            return false;
        }

        // 비밀번호 체크(오늘날짜(8) + user_id)
        var chkPwd = getCurDate() + userId;
        if($("#resetPwd").val() !== chkPwd){
            $scope._popMsg(messages["naverPlaceStatus.resetPwd.Chk.msg"]); // 비밀번호가 일치하지 않습니다. 다시 입력하세요.
            return false;
        }

        // 네이버플레이스 연동정보를 초기화 하시겠습니까?
        $scope._popConfirm(messages["naverPlaceStatus.statusReset.msg"], function() {

            var params = {};
            params.hqOfficeCd = $scope.selectedStore.hqOfficeCd;
            params.storeCd = $scope.selectedStore.storeCd;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/sys/link/naverPlaceStatus/naverPlaceStatusReset.sb", params, function () {

                // 1) 네이버플레이스현황
                if ($scope.selectedStore.resrceCd === "006479" || $scope.selectedStore.resrceCd === "006358") {

                    // 부모창 재조회
                    var scope = agrid.getScope('naverPlaceStatusCtrl');
                    scope.searchNaverplaceStatus();
                }

                // 2) 네이버플레이스 플러스 연동
                if ($scope.selectedStore.resrceCd === "006459" || $scope.selectedStore.resrceCd === "006318") {

                    setTimeout(function() {
                        // 부모창 재조회
                        const bc = new BroadcastChannel('refresh_channel');
                        bc.postMessage('refresh');
                    }, 1000);
                }

                // 팝업 닫기
                $scope.wjNaverPlaceStatusResetLayer.hide(true);
            });
        });
    };

}]);