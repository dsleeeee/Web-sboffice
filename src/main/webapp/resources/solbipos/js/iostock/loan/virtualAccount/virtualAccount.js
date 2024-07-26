/****************************************************************
 *
 * 파일명 : virtualAccount.js
 * 설  명 : 가상계좌내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.24     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  가상계좌내역 그리드 생성
 */
app.controller('virtualAccountCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualAccountCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("virtualAccountCtrl", function(event, data) {
        $scope.searchVirtualAccount();
        event.preventDefault();
    });

    $scope.searchVirtualAccount = function(){

    };
    // <-- //검색 호출 -->

    // 가상계좌 입금 생성
    $scope.loanVirtualAccount = function(){
        // if ($("#lblUseLoanAmt").text() == "") {
        //     $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
        //     return false;
        // }
        //
        // var params = {};
        // params.va_mny = $("#lblUseLoanAmt").text(); // 여신사용액
        // $scope.setSelectedStore(params);

        $scope.wjVirtualAccountRegisterLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 가상계좌 입금 생성 팝업 핸들러 추가
        $scope.wjVirtualAccountRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('virtualAccountRegisterCtrl', null);
            }, 50)
        });
    });

}]);