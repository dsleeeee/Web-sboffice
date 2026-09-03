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
            //agreeYn = JSON.parse(response.data.data.agreeYn);
            // 네이버 주문연동 여부
            linkYn = JSON.parse(response.data.data.linkYn);
            // 서비스별 활성화 여부
            serviceActiveYn = JSON.parse(response.data.data.serviceActiveYn);
            // 연동 단계
            linkStep = 0;

            // 연동 단계 파악
            // 0 : 네.아.로 로그인 미완료
            // 1 : 네.아.로 로그인 완료, 동의 미완료
            // 2 : 동의 완료, 주문연동 미완료
            // 3 : 주문연동 완료
            if (uniqueId != "" && uniqueId != null) {
                linkStep = 2; //linkStep = 1;

                /*if (agreeYn != null && agreeYn != undefined) {
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
                            linkStep = 2;*/

                            if (linkYn != null && linkYn != undefined) {
                                if (linkYn.status === 200 && linkYn.data) {
                                    linkStep = 3;
                                }
                            }
                        /*}
                    }
                }*/
            }

            console.log("연동단계2 :" + linkStep + " / 네.아.로 아이디 :" + uniqueId /*+ "/ 동의 :" + agreeYn.agreedPlacePrivacyAgreementTypes*/ + " / 주문연동 :" + linkYn.placeId);

            setTimeout(function () {

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
                if (linkStep === 1) {
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
                        $("#divView4").css("display", "none");

                        // 업체리스트 조회
                        getStoreList(0);

                    }else{
                        $scope._popMsg("연동 불가");
                    }
                }*/

                //
                if(linkStep === 3){
                    $("#divView1").css("display", "none");
                    $("#divView2").css("display", "none");
                    $("#divView3").css("display", "");
                    $("#divView4").css("display", "none");

                    // 연동 정보 셋팅
                    $("#storeNm1").val(linkYn.data.name);
                    $("#businessId1").val(linkYn.data.channelShopId);
                    $("#serviceNm1").val(linkYn.data.serviceName);
                    $("#phoneNo1").val(linkYn.data.shopTelNo);
                    $("#addr1").val(linkYn.data.roadAddr ? linkYn.data.roadAddr : "");
                    $("#addrDtl1").val(linkYn.data.addrDetail ? linkYn.data.addrDetail : "");

                    // 주문 유형 정보 셋팅
                    $("input[name='tableOrder1'][value='N']").prop("checked", true);
                    $("input[name='pickupOrder1'][value='N']").prop("checked", true);

                    var serviceList = (serviceActiveYn && serviceActiveYn.data) ? serviceActiveYn.data : [];
                    for (var i = 0; i < serviceList.length; i++) {
                        var serviceItem = serviceList[i];

                        if (serviceItem.service === "TABLE") {
                            if (serviceItem.useFlag) {
                                $("input[name='tableOrder1'][value='Y']").prop("checked", true);
                            } else {
                                $("input[name='tableOrder1'][value='N']").prop("checked", true);
                            }
                        } else if (serviceItem.service === "PICKUP") {
                            if (serviceItem.useFlag) {
                                $("input[name='pickupOrder1'][value='Y']").prop("checked", true);
                            } else {
                                $("input[name='pickupOrder1'][value='N']").prop("checked", true);
                            }
                        }
                    }
                }

            }, 1000);

        });
    };

    // 연동해지
    $scope.btnWithdraw = function () {

        // 네이버 주문 연동을 해지 하시겠습니까?
        $scope._popConfirm(messages["naverOrderLink.withdraw.confirm"], function () {

            var params = {};
            params.channelType = "NAVER";
            params.posShopId = sessionStorage.getItem("storeCd");

            $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverOrderLink/delPlace.sb", params, function (response) {
                var data = response.data.data.list;
                if (data.status === 200) {
                    // 네이버 주문 연동 해지에 성공했습니다.
                    $scope._popMsg(messages["naverOrderLink.withdraw.ok"]);

                    setTimeout(function () {
                        // 재조회
                        location.reload();
                    }, 1000);
                } else {
                    // 네이버 주문 연동 해지에 실패했습니다. 관리자에게 문의 하세요.
                    $scope._popMsg(messages["naverOrderLink.withdraw.error"]);
                    return false;
                }
            });
        });
    };

    // 주문 유형 정보 수정
    $scope.btnEdit = function () {
        $scope.wjNaverOrderInfoLayer.show(true);
        $scope._broadcast('naverOrderInfoCtrl', serviceActiveYn);
        event.preventDefault();
    };

    // 로그아웃 (초기화)
    $scope.btnLogout = function () {

        var params = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd = storeCd;
        params.resrceCd = menuCd;
        params.pathNm = "네이버플레이스-네이버플레이스-네이버주문연동-초기화 팝업";

        $scope.wjNaverPlaceStatusResetLayer.show(true);
        $scope._broadcast('naverPlaceStatusResetCtrl', params);

        var actParams = {};
        actParams.resrceCd = menuCd;
        actParams.pathNm = "네이버플레이스-네이버플레이스-네이버주문연동";
        actParams.contents = "'로그아웃' 버튼 클릭 시";

        $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function (response) {
        });
    };

}]);

// 연동하기
function btnLinkStore(index) {

    // 선택 매장과 연동 하시겠습니까?
    s_alert.popConf(messages["naverOrderLink.link.confirm"], function() {

        // 연동하려는 매장의 정보(네이버 매장정보)
        var item = window.storeList[index];

        var params = {};
        params.posShopId = sessionStorage.getItem("storeCd");
        params.channelShopId = item.channelShopId || "";
        params.channelType = "NAVER";

        var url = '/naverPlace/naverPlace/naverOrderLink/regPlace.sb';
        if (document.getElementsByName("sessionId")[0]) {
            url += '?sid=' + document.getElementsByName("sessionId")[0].value;
        }

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: url,
            data: params,
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.status === "OK") {
                    if (data.data.list.status === 201) {

                        // 연동 매장 정보 셋팅
                        /*var vScope2 = agrid.getScope('naverOrderLinkCtrl');
                        vScope2.$apply(function () {
                            vScope2.btnLink();
                        });*/
                        
                        // 서비스 활성화/비활성화 팝업 띄우기
                        var vScope = agrid.getScope('naverOrderTypeCtrl');
                        vScope.$apply(function () {
                            vScope.wjNaverOrderTypeLayer.show(true);
                            vScope._broadcast('naverOrderTypeCtrl', data.data.list.data.services);
                        });

                    } else {
                        // 연동 실패 div 띄우기
                        $("#divView1").css("display", "none");
                        $("#divView2").css("display", "none");
                        $("#divView3").css("display", "none");
                        $("#divView4").css("display", "");
                    }
                }
            },
            error: function (xhr, status, error) {
                console.log("AJAX 에러:", status, error, xhr.responseText);

                // 실패 div 띄우기
                $("#divView1").css("display", "none");
                $("#divView2").css("display", "none");
                $("#divView3").css("display", "none");
                $("#divView4").css("display", "");
            }
        });
    });

}
