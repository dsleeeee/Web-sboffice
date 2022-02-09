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

    // 팝업 닫기
    $scope.close = function() {
        // 발신번호 등록됬는지 확인
        var smsTelNoRegisterScope = agrid.getScope('smsSendCtrl');
        smsTelNoRegisterScope.tellNumChkPop();

        $scope.wjSmsTelNoRegisterLayer.hide();
        event.preventDefault();
    };

    // 값가져오기
    $scope.getVal = function() {
        // 발신번호 등록됬는지 확인
        // $scope._inquirySub("/adi/sms/smsTelNoManage/smsTelNoManage/getVal.sb", null, function (response){});

        $.postJSON("/adi/sms/smsTelNoManage/smsTelNoManage/getVal.sb", null, function(result) {
                var data = result.data;
                console.log(data);
                $("#site_cd").val(data.siteCd);
                $("#web_siteid").val(data.webSiteid);
                $("#gw_url").val(data.gwUrl);
                $("#Ret_URL").val(data.retUrl);
                $("#ordr_idxx").val(data.ordrIdxx);
                $("#up_hash").val(data.upHash);
                $("#sessionId").val(data.sessionId);
            }
        );
    };

    // 일반번호 인증요청
    $scope.smsGeneralNoRequest = function() {
        $scope.wjSmsGeneralNoRegisterLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 일반번호 인증요청 팝업 핸들러 추가
        $scope.wjSmsGeneralNoRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsGeneralNoRegisterCtrl', null);
            }, 50)
        });
    });
}]);