/****************************************************************
 *
 * 파일명 : memberPointMove.js
 * 설  명 : 회원 포인트 이관 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.12.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 포인트 이관 조회 그리드 생성
 */
app.controller('memberPointMoveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberPointMoveCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("memberPointMoveCtrl", function(event, data) {
        event.preventDefault();
    });

    // <-- //검색 호출 -->

    // 보내는 회원
    // 회원 포인트 조회 팝업
    $scope.popUpMemberSend = function() {
        var popUp = $scope.wjSearchMemberPointLayer;
        popUp.show(true, function (s) {
            var scope = agrid.getScope('searchMemberPointCtrl');
            scope._broadcast('searchMemberPointCtrl' , null);
            scope.$apply(function(){
                scope._gridDataInit();
                if( !$.isEmptyObject(scope.getSelectedMember())  ){
                    $scope.memberNmSend = scope.getSelectedMember().membrNm;
                    $scope.memberNoSend = scope.getSelectedMember().membrNo;
                    $scope.pointSend = scope.getSelectedMember().avablPoint;
                }
            });
        });
    };

    // 받는 회원
    // 회원 포인트 조회 팝업
    $scope.popUpMemberReceive = function() {
        var popUp = $scope.wjSearchMemberPointLayer;
        popUp.show(true, function (s) {
            var scope = agrid.getScope('searchMemberPointCtrl');
            scope._broadcast('searchMemberPointCtrl' , null);
            scope.$apply(function(){
                scope._gridDataInit();
                if( !$.isEmptyObject(scope.getSelectedMember())  ){
                    $scope.memberNmReceive = scope.getSelectedMember().membrNm;
                    $scope.memberNoReceive = scope.getSelectedMember().membrNo;
                }
            });
        });
    };

    // 저장
    $("#funcSave").click(function(e) {
        // 보내는 회원을 선택해주세요.
        if (isNull($scope.memberNoSend)) {
            $scope._popMsg(messages["regist.searchMemberPoint.memberNoSendBlank"]);
            return false;
        }
        // 받는 회원을 선택해주세요.
        if (isNull($scope.memberNoReceive)) {
            $scope._popMsg(messages["regist.searchMemberPoint.memberNoReceiveBlank"]);
            return false;
        }
        // 보내는 회원과 받는 회원은 같을 수 없습니다.
        if ($scope.memberNoSend === $scope.memberNoReceive) {
            $scope._popMsg(messages["regist.searchMemberPoint.memberNoSame"]);
            return false;
        }
        // 이관포인트를 입력해주세요.
        if (isNull($scope.pointReceive)) {
            $scope._popMsg(messages["regist.searchMemberPoint.pointReceiveBlank"]);
            return false;
        } else {
            // 이관포인트는 가용포인트 이상으로 이관 불가능합니다.
            if ($scope.pointSend < $scope.pointReceive) {
                $scope._popMsg(messages["regist.searchMemberPoint.pointMoveError"]);
                return false;
            }
        }

        var params = {};
        // 보내는 회원
        params.memberNoSend = $scope.memberNoSend;
        params.memberNmSend = $scope.memberNmSend;
        // params.pointSend = $scope.pointSend;
        // 받는 회원
        params.memberNoReceive = $scope.memberNoReceive;
        params.memberNmReceive = $scope.memberNmReceive;
        params.pointReceive = $scope.pointReceive;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/membr/info/view/base/getMemberPointMoveSave.sb", params, function () { $scope.close() });
    });

    // 팝업 닫기
    $scope.close = function() {
        // 보내는 회원
        $scope.memberNmSend = "";
        $scope.memberNoSend = "";
        $scope.pointSend = "";
        // 받는 회원
        $scope.memberNmReceive = "";
        $scope.memberNoReceive = "";
        $scope.pointReceive = "";

        $scope.wjMemberPointMoveLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('memberCtrl', null);
    };

}]);