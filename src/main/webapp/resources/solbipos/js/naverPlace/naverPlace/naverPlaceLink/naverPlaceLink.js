/****************************************************************
 *
 * 파일명 : naverPlaceLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버플레이스 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.27     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 네이버플레이스 연동 controller */
app.controller('naverPlaceLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceLinkCtrl', $scope, $http, false));

    $scope.$on("naverPlaceLinkCtrl", function (event, data) {

    });

    // 인증 API Access Token 조회
    $scope.btn1 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getAccessToken.sb", params, function (response) {
            var data = response.data.data.list;
            $scope._popMsg(data.token);
        });
    };

    // 네이버 로그인
    $scope.btn2 = function () {

        // state값 생성
        var state = generateState();

        // state값 DB 저장
        var params = {};
        params.state = state;

        $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/saveNaverState.sb", params, function (response) {

            if (response.data.status === 'OK') {
                // 네이버 로그인 팝업창 오픈
                var clientId = "nEMag45FNxJsZUnX9ywM";
                var redirectUrl = "http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/saveNaverUniqueId.sb";
                var popupUrl = 'https://nid.naver.com/oauth2.0/authorize?' +
                    'response_type=code' +              // 인증과정에 대한 내부 구분값(고정값)
                    '&client_id=' + clientId +          // 발급받은 clientId
                    '&state=' + state +                 // 네이버로그인후 기존세션 확인을 위한 임의값
                    '&redirect_uri=' + redirectUrl;     // 어플케이션에서 등록했던 CallBack URL를 입력

                window.open(popupUrl, "popup", "width=600, height=1000");
            }
        });
    };

    // 동의여부확인 API 호출
    $scope.btn3 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getAgreeYn.sb", params, function (response) {
            var data = response.data.data.list;
            console.log("ownerMemberStatus : " + data.ownerMemberStatus + "\n" +
                "isJoinedMember : " + data.isJoinedMember + "\n" +
                "isWithdrawing : " + data.isWithdrawing + "\n" +
                "agreedPlacePrivacyAgreementTypes : " + data.agreedPlacePrivacyAgreementTypes + "\n" +
                "isMyBizAgreed : " + data.isMyBizAgreed
            );
        });
    };

    // 네이버 동의화면 호출
    $scope.btn4 = function () {
        //var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/viewPop2.sb");
        var redirectURL = encodeURIComponent("https://blog.naver.com");
        var popupUrl = "https://new.smartplace.naver.com/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=600, height=1000");

        if (!popup) {
            alert("팝업 차단을 해제해주세요.");
            return;
        }

        const start = Date.now();
        const timer = setInterval(async () => {
            // 1) 사용자가 팝업을 닫았는지 감지
            if (popup.closed) {
                clearInterval(timer);

                // 2) 서버에 '동의 후 다음 단계' 시도 (권장: 실제 연동 API 호출로 검증)
                // 동의가 안 됐으면 여기서 403/동의필요가 떨어질 테니 다시 팝업 유도하면 됨
                /*const res = await fetch("/api/naver/pos/link-or-verify", {
                    method: "POST",
                    credentials: "include",
                });

                if (res.ok) {
                    // 3) 성공 시 네 페이지로 이동
                    window.location.href = "/pos/link/complete";
                } else {
                    alert("약관 동의가 확인되지 않았어요. 다시 진행해주세요.");
                }*/
            }

            // (선택) 너무 오래 열려있으면 안내
            if (Date.now() - start > 10 * 60 * 1000) {
                // 10분
                // 안내만 하고 계속 감지
            }
        }, 500);
    };

    // 업체목록조회 API 호출
    $scope.btn5 = function () {
        var params = {};
        params.page = 0;
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getPlaceList.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 플레이스 주인권한 변경
    $scope.btn6 = function () {
        //var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/viewPop2.sb");
        var redirectURL = encodeURIComponent("https://blog.naver.com");
        var popupUrl = "https://new.smartplace.naver.com/bizes/lookup?to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=600, height=1000");

        if (!popup) {
            alert("팝업 차단을 해제해주세요.");
            return;
        }

        const start = Date.now();
        const timer = setInterval(async () => {
            // 1) 사용자가 팝업을 닫았는지 감지
            if (popup.closed) {
                clearInterval(timer);

                // 2) 서버에 '동의 후 다음 단계' 시도 (권장: 실제 연동 API 호출로 검증)
                // 동의가 안 됐으면 여기서 403/동의필요가 떨어질 테니 다시 팝업 유도하면 됨
                /*const res = await fetch("/api/naver/pos/link-or-verify", {
                    method: "POST",
                    credentials: "include",
                });

                if (res.ok) {
                    // 3) 성공 시 네 페이지로 이동
                    window.location.href = "/pos/link/complete";
                } else {
                    alert("약관 동의가 확인되지 않았어요. 다시 진행해주세요.");
                }*/
            }

            // (선택) 너무 오래 열려있으면 안내
            if (Date.now() - start > 10 * 60 * 1000) {
                // 10분
                // 안내만 하고 계속 감지
            }
        }, 500);
    };

    // 업체 등록/수정 API 호출
    $scope.btn7 = function () {
        var params = {};
        params.businessName = "";
        params.phone = "";
        params.address = "";
        params.categoryId = "";
        params.brandCategoryId = "";
        params.registrationNumber = "";
        params.placeId = "";
        params.bizDays = "";
        params.registrationCertificate = "";
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/savePlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 연동 추가 API
    $scope.btn8 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/mappingPlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 연동 해지 API
    $scope.btn9 = function () {
        var params = {};
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/unMappingPlace.sb", params, function (response) {
            var data = response.data.data.list;
        });
    };

    // 업종 조회 API
    $scope.btn10 = function () {
        var params = {};
        params.search = "분식";
        //params.categoryIds = "220186";
        //params.brandCategoryIds = "";
        $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverPlaceLink/getBusinessCategory.sb", params, function (response) {
            var data = response.data.data.list;
        });
    }

}]);