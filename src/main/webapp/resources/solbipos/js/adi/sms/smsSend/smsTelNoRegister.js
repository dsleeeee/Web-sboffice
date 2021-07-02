/****************************************************************
 *
 * 파일명 : smsTelNoRegister.js
 * 설  명 : 발신번호 사전등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  발신번호 사전등록 팝업 조회 그리드 생성
 */
app.controller('smsTelNoRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoRegisterCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoRegisterCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 인증요청
    $scope.telNoRequest = function() {
        alert("준비중");
    };

    // 팝업 닫기
    $scope.close = function() {
        // 발신번호 등록됬는지 확인
        var smsTelNoRegisterScope = agrid.getScope('smsSendCtrl');
        smsTelNoRegisterScope.tellNumChkPop();

        $scope.wjSmsTelNoRegisterLayer.hide();
        event.preventDefault();
    };
}]);