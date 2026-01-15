/****************************************************************
 *
 * 파일명 : dlvrAgencyLink.js
 * 설  명 : 배달관리 - 배달정보 - 배달대행사 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 주문 연동 활성화 DropBoxDataMap
var useYnFg = [
    {"name": "미사용", "value": false},
    {"name": "사용", "value": true}
];

app.controller('dlvrAgencyLinkCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAgencyLinkCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFg);

    // 이용권상태 상태값
    $scope.subscriptionStatus = "";

    // 배달앱 연동 정보 상태값
    $scope.basePlatformInfo = null;

    // 주문 중개 서비스 사용여부 콤보박스 선택값 변경 여부 확인을 위해
    $scope.orgUseYn = "";

    $scope.initGrid = function (s, e) {

        // 유저 상태 조회(이용권상태, 주문 중개 서비스 사용여부, 배달앱 연동 정보)
        $scope.getOmsUserStatus();

        // 그리드 데이터 형태에 따른 표기 변환
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "deposit") {
                    e.cell.innerHTML = addComma(e.cell.innerText);
                }

                if (col.binding === "mappingDateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });
    };

    $scope.$on("dlvrAgencyLinkCtrl", function (event, data) {
        
        // 배달대행사 연동 현황 조회
        $scope.searchStatus();
        event.preventDefault();
    });
    
    // 배달대행사 연동 현황 조회
    $scope.searchStatus = function () {

        if ($scope.subscriptionStatus !== "ACTIVE") {
            // 오더킷 서비스 구독 상태를 확인해주세요.
            $scope._popMsg(messages['dlvrAgencyLink.status.save.msg1']);
            return;
        }

        var params = {};

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getAgencyLink.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.status === 200) {

                var list = data.data;

                list.forEach(item => {
                    item.gChk = false; // 체크박스 선택을 위해 추가
                });

                var grid = wijmo.Control.getControl("#wjGridMain");
                grid.itemsSource = new wijmo.collections.CollectionView(list);
                grid.collectionView.trackChanges = true;
            }
        });
    };

    // 배달대행사 연동 팝업 호출
    $scope.btnAdd = function () {
        $scope.wjDlvrAgencyRegLayer.show(true);
        $scope._broadcast('dlvrAgencyRegCtrl');
    };

    // 해제
    $scope.btnClear = function () {

        var chkCnt = 0;

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                chkCnt++;
            }
        }

        if (1 > chkCnt) {
            $scope._popMsg(messages["dlvrAgencyLink.link.del.chk.msg1"]); // 선택한 배달대행사가 없습니다.
            return false;
        }


        if (chkCnt > 1) {
            $scope._popMsg(messages["dlvrAgencyLink.link.del.chk.msg2"]); // 배달대행사는 1개만 선택하세요.
            return false;
        }

        // 파라미터 설정
        var params = {};
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                params.linkType = "006";
                params.riderName = item.riderName;
                params.mappingSequence = item.id;

            }
        }

        /*var params = {};
        params.linkType = "006";
        params.riderName = "모아라인";
        params.mappingSequence = "20251105497150"; // 모아라인*/

        console.log(params);

        // 배달대행사를 해제하시겠습니까?
        $scope._popConfirm(params.riderName + messages["dlvrAgencyLink.link.clear.msg"], function () {

            $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/deleteAgencyLink.sb", params, function (response) {

                var data = response.data.data.list;

                if (data.code === "0000") {

                    $scope._popMsg(data.message);

                    // 배달대행사 연동 현황 재조회
                    $scope.searchStatus();

                } else {
                    $scope._popMsg(data.message + "</br>" + data.data);
                    return;
                }
            });

        });
    };

    // 주문 중개 서비스 변경
    $scope.btnSave = function () {

        if ($scope.subscriptionStatus !== "ACTIVE") {
            // 오더킷 서비스 구독 상태를 확인해주세요.
            $scope._popMsg(messages['dlvrAgencyLink.status.save.msg1']);
            return;
        } else {
            if ($scope.basePlatformInfo === null) {
                // 오더킷 배달앱 연동 상태를 확인해주세요.
                $scope._popMsg(messages['dlvrAgencyLink.status.save.msg2']);
                return;
            }
        }

        if ($scope.useYnCombo.selectedValue === $scope.orgUseYn) {
            // 변경 사항이 없습니다.
            $scope._popMsg(messages['dlvrAgencyLink.status.save.msg3']);
            return;
        }

        var params = {};
        params.use_orderandrider = $scope.useYnCombo.selectedValue;

        $scope._postJSONQuery.withOutPopUp('/dlvr/manage/info/dlvrAgencyLink/saveOrderAndRider.sb', params, function (response) {

            var data = response.data.data.list;

            if (data.status === "success" && data.status_code === 200) {

                // 저장 되었습니다.
                $scope._popMsg(messages['cmm.saveSucc']);

                // 주문 연동 활성화 재셋팅
                $scope.useYnCombo.selectedValue = data.data.useOrderAndRider;

                // 기존 주문 연동 활성화 콤보박스 선택값 갖고 있기
                $scope.orgUseYn = $scope.useYnCombo.selectedValue;
            }
        });
    };

    // 오더킷 바로가기
    $scope.orderkitGoto = function () {

        var params = {};
        var redirectUrl = "";
        var url = "https://test.orderkit.co.kr"; // 개발
        //var url = "https://orderkit.co.kr"; // 운영

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getOmsUserStatus.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.status === "success" && data.status_code === 200) {

                if (data.data.subscriptionStatus == "EXPIRED" || data.data.subscriptionStatus == "CANCELLED") { // 만료, 해지완료
                    redirectUrl = "/app/dashboard";
                } else if (data.data.subscriptionStatus == "UNPAID") { // 결제 이전
                    redirectUrl = "/app/payment/pay";
                } else if (data.data.subscriptionStatus == "ACTIVE" || data.data.subscriptionStatus == "GRACE" || data.data.subscriptionStatus == "REQ_CANCEL") { // 활성화, 유예, 해지요청
                    if (data.data.base_platform_info === null) { // 배달앱 미연동
                        redirectUrl = "/app/setting/platform";
                    }
                    if (data.data.base_platform_info !== null) { // 정상 연동
                        redirectUrl = "/app/dashboard";
                    }
                } else {
                    redirectUrl = ""
                }

            } else { // data.status === "error" && data.status_code === 500 인 상태
                redirectUrl = ""
            }

            params.redirectUrl = redirectUrl;
            $scope._postJSONQuery.withOutPopUp('/orderkit/orderkit/orderkitRecpOrigin/orderkitGoto.sb', params, function (response) {

                // jwtToken
                var jwtToken = response.data.data;

                if (redirectUrl !== "") {
                    console.log("url : " + url + "/auth/pos/url?token=" + jwtToken);
                    window.open(url + "/auth/pos/url?token=" + jwtToken, 'newWindow');
                } else {
                    console.log("url : " + url + "/auth/pos?token=" + jwtToken);
                    window.open(url + "/auth/pos?token=" + jwtToken, 'newWindow');
                }

            });

        });
    };

    // 유저 상태 조회(이용권상태, 주문 중개 서비스 사용여부, 배달앱 연동 정보)
    $scope.getOmsUserStatus = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getOmsUserStatus.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.status === "success" && data.status_code === 200) {

                // 주문 연동 활성화 셋팅
                $scope.useYnCombo.selectedValue = data.data.useOrderAndRider;

                // 기존 주문 연동 활성화 콤보박스 선택값 갖고 있기
                $scope.orgUseYn = $scope.useYnCombo.selectedValue;

                // 이용권상태 상태값 갖고있기
                $scope.subscriptionStatus = data.data.subscriptionStatus;

                // 배달앱 연동 정보 상태값 갖고있기
                $scope.basePlatformInfo = data.data.base_platform_info;

                if (data.data.subscriptionStatus == "UNPAID" || data.data.subscriptionStatus == "EXPIRED" || data.data.subscriptionStatus == "GRACE" || data.data.subscriptionStatus == "REQ_CANCEL" || data.data.subscriptionStatus == "CANCELLED") { // 결제 이전, 만료, 유예, 해지요청, 해지완료

                    // 주문 연동 활성화 변경 불가
                    $scope.useYnCombo.isDisabled = true;

                    // 오더킷 배달앱 연동 안내 문구 셋팅
                    $("#lblText").text(messages['dlvrAgencyLink.status.msg1']); // ※ 서비스 구독이 완료되지 않았습니다. 오더킷에서 플랜 결제 후 바로 이용하실 수 있습니다.
                    $("#lblText").css('color', 'red');

                    // 배달대행사 연동 영역 hidden
                    $("#divInstructions").css("display", "none");
                    $("#divLeft").css("display", "none");
                    $("#divRight").css("display", "none");
                }

                if (data.data.subscriptionStatus == "ACTIVE") { // 활성화

                    if (data.data.base_platform_info === null) {

                        // 주문 연동 활성화 변경 불가
                        $scope.useYnCombo.isDisabled = true;

                        // 오더킷 배달앱 연동 안내 문구 셋팅
                        $("#lblText").text(messages['dlvrAgencyLink.status.msg2']); // ※ 오더킷과 배달앱 연동이 완료되지 않았습니다. 오더킷에서 운영 중인 배달앱을 등록해주세요.
                        $("#lblText").css('color', 'red');
                    }

                    if (data.data.base_platform_info !== null) {

                        // 주문 연동 활성화 변경 가능
                        $scope.useYnCombo.isDisabled = false;

                        // 오더킷 배달앱 연동 안내 문구 셋팅
                        $("#lblText").text(messages['dlvrAgencyLink.status.msg3']); // ※ 배달대행사를 연동할 수 있습니다.
                        $("#lblText").css('color', 'black');
                    }

                    // 배달대행사 연동 영역 show
                    $("#divInstructions").css("display", "");
                    $("#divLeft").css("display", "");
                    $("#divRight").css("display", "");
                }

            } else { // data.status === "error" && data.status_code === 500 인 상태
                $scope.useYnCombo.selectedValue = false;
                $scope.useYnCombo.isDisabled = true;

                // 기존 주문 연동 활성화 콤보박스 선택값 갖고 있기(없음)
                $scope.orgUseYn = "";

                // 이용권상태 상태값 갖고있기(없음)
                $scope.subscriptionStatus = "";

                // 배달앱 연동 정보 상태값 갖고있기(없음)
                $scope.basePlatformInfo = null;

                $("#lblText").text("※" + " " + messages['dlvrAgencyLink.status.save.msg1']); // ※ 오더킷 서비스 구독 상태를 확인해주세요.
                $("#lblText").css('color', 'red');
                // 배달대행사 연동 영역 hidden
                $("#divInstructions").css("display", "none");
                $("#divLeft").css("display", "none");
                $("#divRight").css("display", "none");
            }
        });
    };
}]);