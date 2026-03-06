/****************************************************************
 *
 * 파일명 : naverPlacePlusLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 플러스 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.02.23     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 네이버플레이스 연동 controller */
app.controller('naverPlacePlusLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlacePlusLinkCtrl', $scope, $http, false));

    $scope.$on("naverPlacePlusLinkCtrl", function (event, data) {

    });

    // 네이버 스마트 플레이스 연동 버튼 클릭
    $scope.btn1 = function () {

        // 네.아.로 로그인 미완료 시
        if (linkStep === 0) {

            // state값 생성
            var state = generateState();

            // state값 DB 저장
            var params = {};
            params.state = state;

            $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverPlacePlusLink/saveNaverState.sb", params, function (response) {

                if (response.data.status === 'OK') {
                    // 네이버 로그인 팝업창 오픈
                    var clientId = "nEMag45FNxJsZUnX9ywM";
                    var redirectUrl = "http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/saveNaverUniqueId.sb";
                    var popupUrl = 'https://nid.naver.com/oauth2.0/authorize?' +
                        'response_type=code' +              // 인증과정에 대한 내부 구분값(고정값)
                        '&client_id=' + clientId +          // 발급받은 clientId
                        '&state=' + state +                 // 네이버로그인후 기존세션 확인을 위한 임의값
                        '&redirect_uri=' + redirectUrl;     // 어플케이션에서 등록했던 CallBack URL를 입력

                    window.open(popupUrl, "popup", "width=600, height=1000");
                }
            });
        }

        // 네.아.로 로그인 완료, 동의 미완료
        if(linkStep === 1){
            var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
            var popupUrl = "https://test-new.smartplace.naver.com/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
            var popup = window.open(popupUrl, "popup", "width=750, height=1000");
        }

        // 동의 완료, 매장연동 미완료 시
        if(linkStep === 2){
            if(uniqueId != ""){
                var popup = window.open("/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId, "popup", "width=750, height=1000");
            }else{
                $scope._popMsg("연동 불가"); 
            }
        }
    };

    // 연동 해지
    $scope.btn2 = function () {

        // 연동을 해지하시겠습니까?
        $scope._popConfirm(messages["naverPlacePlusLink.withdraw.confirm"], function () {

            // 연동 해지 API 호출
            var params = {};
            params.placeId = $("#txtPlaceId").val();
            $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlacePlusLink/unMappingPlace.sb", params, function (response) {
                var data = response.data.data.list;
                if (JSON.stringify(data) === "{}") { // 정상인 경우, 빈값 return
                    // 재조회
                    location.reload();
                } else {
                    // 연동 해지 도중 문제가 발생하였습니다.
                    $scope._popMsg( messages["naverPlacePlusLink.withdraw.error"] + "</br>JSON.stringify(data)");
                }
            });
        });
    };

}]);