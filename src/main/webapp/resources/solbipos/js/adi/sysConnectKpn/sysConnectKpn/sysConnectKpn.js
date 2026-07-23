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
    // 서버가 접속 시점에 envst1355(KPN 백오피스 URL)를 직접 조회하고, 캐시/신규발급한 토큰을 붙인 접속 URL을 받아 새창으로 연다.
    $scope.sysConnectKpn = function(){

        var params = {};

        // withPopUp : 서버에서 JsonException(SERVER_ERROR)으로 던진 에러 메시지가 _httpStatusCheck에서 자동으로 팝업됨
        $scope._postJSONQuery.withPopUp("/adi/sysConnectKpn/sysConnectKpn/getConnectUrl.sb", params, function(response){

            var data = response.data.data;

            // 정상 응답(status:OK)일 때만 여기로 들어옴 : 서버가 만들어준 URL(token/usr_id 포함)을 새창으로 오픈
            if(data !== undefined && data.url !== undefined && data.url !== ""){
                window.open(data.url);
            } else {
                $scope._popMsg("KPN 접속 URL 생성에 실패했습니다.");
            }
        });
    };

}]);
