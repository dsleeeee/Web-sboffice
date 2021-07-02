/****************************************************************
 *
 * 파일명 : smsSendViewPop.js
 * 설  명 : SMS전송 팝업JavaScript
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
 *  SMS전송 팝업 조회 그리드 생성
 */
app.controller('smsSendViewPopCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsSendViewPopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsSendViewPopCtrl", function(event, data) {
        // 페이지 로드시 호출
        $scope.searchSmsSendViewPop(data, "N");
        event.preventDefault();
    });

    $scope.$on("smsSendViewPopYCtrl", function(event, data) {
        // 페이지 로드시 호출
        $scope.searchSmsSendViewPop(data, "Y");
        event.preventDefault();
    });

    // 페이지 로드시 호출
    $scope.searchSmsSendViewPop = function(data, pageGubun) {
        var smsSendViewPopScope = agrid.getScope('smsSendCtrl');
        smsSendViewPopScope.initPageSmsSend(data, pageGubun);
        var smsSendViewPopSmsPopupScope = agrid.getScope("smsPopupCtrl");
        smsSendViewPopSmsPopupScope._broadcast('smsPopupCtrl');
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function() {
        // 초기화
        var smsSendViewPopScope = agrid.getScope('smsSendCtrl');
        smsSendViewPopScope._gridDataInit();
        smsSendViewPopScope.title = "";
        smsSendViewPopScope.messageContent = "";
        $("#lblTxtByte").text("0");

        $scope.wjSmsSendViewPopLayer.hide();
        event.preventDefault();
    };
}]);