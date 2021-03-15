/****************************************************************
 *
 * 파일명 : memberPointAdjust.js
 * 설  명 : 회원 포인트 조정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 포인트 조정 조회 그리드 생성
 */
app.controller('memberPointAdjustCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberPointAdjustCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("memberPointAdjustCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 회원 등급 조회 팝업
    $scope.popUpMemberClass = function() {
        var popUp = $scope.wjSearchMemberClassLayer;
        popUp.show(true, function (s) {
            var scope = agrid.getScope('searchMemberClassCtrl');
            scope._broadcast('searchMemberClassCtrl');
            scope.$apply(function () {
                scope._gridDataInit();
                if (!$.isEmptyObject(scope.getSelectedMemberClass())) {
                    $scope.membrNm = scope.getSelectedMemberClass().membrNm;
                    $scope.membrNo = scope.getSelectedMemberClass().membrNo;
                    $scope.pointSaveFg = scope.getSelectedMemberClass().pointSaveFg;
                    $scope.memberCash = scope.getSelectedMemberClass().memberCash;
                    $scope.memberCard = scope.getSelectedMemberClass().memberCard;
                    // 적립률
                    if ($scope.pointSaveFg == "1") {
                        $("#lblCash").text("(적립률 : " + $scope.memberCash + "%)");
                        $("#lblCard").text("(적립률 : " + $scope.memberCard + "%)");
                        // 적립금
                    } else if ($scope.pointSaveFg == "2") {
                        $("#lblCash").text("(적립금 : " + $scope.memberCash + "원당 1Point)");
                        $("#lblCard").text("(적립금 : " + $scope.memberCard + "원당 1Point)");
                    }

                    $scope.cash = "";
                    $scope.card = "";
                    $scope.adjustPoint = "";
                    $scope.remark = "";
                }
            });
        });
    };

    // 저장
    $("#memberPointAdjustFuncSave").click(function(e) {
        // 회원을 선택해주세요.
        if (isNull($scope.membrNo)) {
            $scope._popMsg(messages["regist.memberPointAdjust.membrNoBlank"]);
            return false;
        }

        // 조정포인트를 입력해주세요. <br/><br/> (현금금액, 카드금액 입력시 회원등급에 따라 조정포인트가 계산됩니다.)
        if (isNull($scope.adjustPoint)) {
            $scope._popMsg(messages["regist.memberPointAdjust.adjustPointBlank"]);
            return false;
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test($scope.adjustPoint)) {
                // 음수 가능
                var numChkexp2 = /^-[0-9]/g;
                if (numChkexp2.test($scope.adjustPoint)) {
                    // 그외 문자 불가능
                } else if (numChkexp2.test($scope.adjustPoint) == false) {
                    $scope._popMsg(messages["regist.memberPointAdjust.adjustPointChk"]); // 조정포인트는 숫자만 입력해주세요.
                    return false;
                }
            }
        }

        var params = {};
        params.membrNo = $scope.membrNo;
        params.avablPoint = $scope.adjustPoint;
        params.remark = $scope.remark;
        params.gubun = "";

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/membr/info/view/base/getMemberPointAdjustSave.sb", params, function () { $scope.close() });
    });

    // 현금금액 입력시 이벤트
    $scope.searchCashKeyEvt = function () {
        $scope.keyEvt('cash');
    };

    // 카드금액 입력시 이벤트
    $scope.searchCardKeyEvt = function () {
        $scope.keyEvt('card');
    };

    // 자동계산
    $scope.keyEvt = function(gubunKeyEvt) {
        // 현금금액
        if(gubunKeyEvt == "cash") {
            if (isNull($scope.cash)) {
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.cash)) {
                    $scope._popMsg(messages["regist.memberPointAdjust.cashChk"]); // 현금금액은 숫자만 입력해주세요.
                    return false;
                }

                // 적립률
                if ($scope.pointSaveFg == "1") {
                    var cashAmt = $scope.cash * $scope.memberCash / 100;
                    var cardAmt = $scope.card * $scope.memberCard / 100;
                    $scope.adjustPoint = Math.round(cashAmt) + Math.round(cardAmt);

                    // 적립금
                } else if ($scope.pointSaveFg == "2") {
                    var cashAmt = $scope.cash / $scope.memberCash;
                    var cardAmt = $scope.card / $scope.memberCard;
                    $scope.adjustPoint = Math.round(cashAmt) + Math.round(cardAmt);
                }
            }

        // 카드금액
        } else if(gubunKeyEvt == "card") {
            if (isNull($scope.card)) {
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.card)) {
                    $scope._popMsg(messages["regist.memberPointAdjust.cardChk"]); // 카드금액은 숫자만 입력해주세요.
                    return false;
                }

                // 적립률
                if ($scope.pointSaveFg == "1") {
                    var cashAmt = $scope.cash * $scope.memberCash / 100;
                    var cardAmt = $scope.card * $scope.memberCard / 100;
                    $scope.adjustPoint = Math.round(cashAmt) + Math.round(cardAmt);

                    // 적립금
                } else if ($scope.pointSaveFg == "2") {
                    var cashAmt = $scope.cash / $scope.memberCash;
                    var cardAmt = $scope.card / $scope.memberCard;
                    $scope.adjustPoint = Math.round(cashAmt) + Math.round(cardAmt);
                }
            }
        }
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.membrNm = "";
        $scope.membrNo = "";
        $scope.pointSaveFg = "";
        $scope.memberCash = "";
        $scope.memberCard = "";

        $scope.cash = "";
        $("#lblCash").text("");
        $scope.card = "";
        $("#lblCard").text("");
        $scope.adjustPoint = "";
        $scope.remark = "";

        $scope.wjMemberPointAdjustLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('memberCtrl', null);
    };

}]);