/****************************************************************
 *
 * 파일명 : sysConnectKpn.js
 * 설  명 : KPN시스템접속 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.15     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  KPN시스템접속
 */
app.controller('sysConnectKpnCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sysConnectKpnCtrl', $scope, $http, false));

    $scope.init = function (s, e) {
        $scope._broadcast('sysConnectKpnCtrl');
    };

    // <-- 검색 호출 -->
    $scope.$on("sysConnectKpnCtrl", function(event, data) {
        $scope.sysConnectKpn();
        event.preventDefault();
    });

    // 시스템 접속
    // 서버가 접속 시점에 envst1355(KPN 백오피스 URL)를 직접 조회하고, 캐시/신규발급한 토큰을 붙인 접속 URL을 받는다.
    // 호출 주체(posCallYn)가 POS면 현재 탭을 접속 URL로 이동하고, 백오피스면 새창으로 연다.
    $scope.sysConnectKpn = function(){

        var params = {};
        // 가상로그인시 세션활용
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // _postJSONQuery는 정상 응답에도 공통 메시지("조회 데이터가 없습니다")를 팝업하므로 직접 호출
        $scope.$broadcast('loadingPopupActive', messages['cmm.loading']);
        $http({
            method : 'POST',
            url    : "/adi/sysConnectKpn/sysConnectKpn/getConnectUrl.sb",
            params : params,
            headers: {'Content-Type': 'application/json; charset=utf-8'}
        }).then(function (response) {
            $scope.$broadcast('loadingPopupInactive');

            // 서버에서 JsonException(SERVER_ERROR)으로 던진 에러 메시지·세션만료는 공통 상태체크에서 팝업
            if (!$scope._httpStatusCheck(response, true)) {
                return;
            }

            var data = response.data.data;

            // 정상 응답(status:OK) : 서버가 만들어준 URL(token/usr_id 포함)로 이동
            if (data !== undefined && data !== null && data.url !== undefined && data.url !== "") {
                if (data.posCallYn === "Y") {
                    // POS에서 호출 : 팝업 없이 현재 탭을 KPN 화면으로 이동
                    location.href = data.url;
                } else {
                    // 백오피스에서 호출 : 새창
                    window.open(data.url);
                }
            } else {
                $scope._popMsg("KPN 접속 URL 생성에 실패했습니다.");
            }
        }, function (response) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(messages['cmm.error']);
        });
    };

}]);
