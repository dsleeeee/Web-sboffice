/****************************************************************
 *
 * 파일명 : naverOrderLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverOrderLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverOrderLinkCtrl', $scope, $http, false));

    $scope.$on("naverOrderLinkCtrl", function (event, data) {

    });

    // 네이버 주문연동 버튼 클릭
    $scope.btnLink = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp('/naverPlace/naverPlace/naverOrderLink/getStatus.sb', params, function (response) {

            // 네.아.로 uniqueId
            uniqueId = response.data.data.uniqueId;
            // 약관동의 여부
            agreeYn = JSON.parse(response.data.data.agreeYn);
            // 네이버 주문연동 여부
            linkYn = JSON.parse(response.data.data.linkYn);
            // 연동 단계
            linkStep = 0;

            // 연동 단계 파악
            // 0 : 네.아.로 로그인 미완료
            // 1 : 네.아.로 로그인 완료, 동의 미완료
            // 2 : 동의 완료, 주문연동 미완료
            // 3 : 주문연동 완료
            if (uniqueId != "" && uniqueId != null) {
                linkStep = 1;

                if (agreeYn != null && agreeYn != undefined) {
                    if (agreeYn.ownerMemberStatus == "REGULAR" && agreeYn.isJoinedMember == true) {
                        var arr = agreeYn.agreedPlacePrivacyAgreementTypes;
                        var cnt = 0;

                        // 동의 항목 파악
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i] === "SMARTPLACE_INTEGRATED_TERMS") {
                                cnt++;
                            }
                            if (arr[i] === "SMARTPLACE_BUSINESS_TERMS") {
                                cnt++;
                            }
                        }

                        if (cnt => 2) {
                            linkStep = 2;

                            if (linkYn != null && linkYn != undefined) {
                                if (linkYn.status === 200 && linkYn.data) {
                                    linkStep = 3;
                                }
                            }
                        }
                    }
                }
            }

            console.log("연동단계2 :" + linkStep + " / 네.아.로 아이디 :" + uniqueId + "/ 동의 :" + agreeYn.agreedPlacePrivacyAgreementTypes + " / 주문연동 :" + linkYn.placeId);

            setTimeout(function() {

                // 네.아.로 로그인 미완료 시
                if (linkStep === 0) {

                    // state값 생성
                    var state = generateState();

                    // state값 DB 저장
                    var params = {};
                    params.state = state;
                    params.callbackPage = "naverPlace/naverPlace/naverOrderLink/popup/naverOrderPop";

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
                    //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverOrderLink/naverOrderPop.sb");
                    var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverOrderLink/naverOrderPop.sb");
                    var popupUrl = popUrl + "/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
                    var popup = window.open(popupUrl, "popup", "width=750, height=1000");
                }

                // 동의 완료, 주문연동 미완료 시
                /*if(linkStep === 2){
                    if(uniqueId != ""){
                        $("#divView1").css("display", "none");
                        $("#divView2").css("display", "");
                        $("#divView3").css("display", "none");

                    }else{
                        $scope._popMsg("연동 불가");
                    }
                }*/

            }, 1000);

        });
    };

    // 수정
    $scope.btnEdit = function () {

        var params = {};
        params.boardCd = "01";
        params.boardSeqNo = 11;
        params.userId = "PASS"; // 읽기만가능(PASS 명칭 의미없음)

        $scope.wjNaverOrderInfoLayer.show(true);
        $scope._broadcast('naverOrderInfoCtrl', params);
        //$scope.wjNaverOrderTypeLayer.show(true);
        //$scope._broadcast('naverOrderTypeCtrl', params);
        event.preventDefault();
    };

    // 연동해지
    $scope.btnWithdraw = function () {

        $scope._popConfirm(messages["naverOrderLink.withdraw.confirm"], function () {

            var params = {};
            params.placeId = $("#txtPlaceId").val();
            $scope._postJSONQuery.withOutPopUp("/naverPlace/naverPlace/naverOrderLink/unMappingPlace.sb", params, function (response) {
                var data = response.data.data.list;
                if (JSON.stringify(data) === "{}") {
                    location.reload();
                } else {
                    $scope._popMsg(messages["naverOrderLink.withdraw.error"] + "<br/>" + JSON.stringify(data));
                }
            });
        });
    };

    // 로그아웃 (초기화)
    $scope.btnLogout = function () {

        var params = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd = storeCd;
        params.resrceCd = menuCd;
        params.pathNm = "네이버플레이스-네이버플레이스-네이버 주문연동-초기화 팝업";

        $scope.wjNaverPlaceStatusResetLayer.show(true);
        $scope._broadcast('naverPlaceStatusResetCtrl', params);

        var actParams = {};
        actParams.resrceCd = menuCd;
        actParams.pathNm = "네이버플레이스-네이버플레이스-네이버 주문연동";
        actParams.contents = "'로그아웃' 버튼 클릭 시";

        $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function (response) {
        });
    };

}]);

// 연동하기
function btnLinkStore(channelShopId) {
    alert(channelShopId);
}
